package org.example.notesapp.infrastructure;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.notesapp.MainController;
import org.example.notesapp.infrastructure.annotations.*;
import org.example.notesapp.infrastructure.reflection.PackageScanner;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

public class DispatcherServlet extends HttpServlet {

    private final ApplicationContext context = new ApplicationContext();
    private final PackageScanner packageScanner = new PackageScanner();
    private final List<Class<?>> controllers;

    public DispatcherServlet() {
        this.controllers = packageScanner
                .findClassesWithAnnotations(
                        Controller.class,
                        "org.example.notesapp");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        for (Class<?> controller : controllers) {
            for (Method m : controller.getDeclaredMethods()) {
                String address = getUriFromMethod(req, m);
                if (address==null) continue;

                String addr = req.getContextPath()+address;
                if (addr.equalsIgnoreCase(req.getRequestURI())) {
                    invokeController(controller,m,req,resp);
                }
            }
        }
        resp.setStatus(404);
        resp.getWriter().write("NOT FOUND!");
        resp.getWriter().flush();
    }

    private void invokeController (Class controller, Method m, HttpServletRequest req, HttpServletResponse resp ) {
        Object instance = context.getBeanByType(controller);
        try {
            m.invoke(instance,req,resp);
            return;
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }


    private String getUriFromMethod(HttpServletRequest req, Method m) {
        String address = null;
        if (
                req.getMethod().equalsIgnoreCase("get")
                        && m.isAnnotationPresent(GetMapping.class)
        ) {
            address = m.getAnnotation(GetMapping.class).value();
        }
        if (
                req.getMethod().equalsIgnoreCase("post")
                        && m.isAnnotationPresent(PostMapping.class)
        ) {
            address = m.getAnnotation(PostMapping.class).value();
        }
        if (
                req.getMethod().equalsIgnoreCase("put")
                        && m.isAnnotationPresent(PutMapping.class)
        ) {
            address = m.getAnnotation(PutMapping.class).value();
        }
        if (
                req.getMethod().equalsIgnoreCase("delete")
                        && m.isAnnotationPresent(DeleteMapping.class)
        ) {
            address = m.getAnnotation(DeleteMapping.class).value();
        }
        return address;
    }
}
