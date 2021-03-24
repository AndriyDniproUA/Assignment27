package org.example.notesapp.infrastructure;

import org.example.notesapp.infrastructure.annotations.AutoWired;
import org.example.notesapp.infrastructure.annotations.Component;
import org.example.notesapp.infrastructure.annotations.Controller;
import org.example.notesapp.infrastructure.reflection.PackageScanner;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ApplicationContext {
    Map<Class, Object> beans = new HashMap<>();
    private final PackageScanner packageScanner = new PackageScanner();

    public ApplicationContext() {
        createBeans();
    }

    private void createBeans() {
        List<Class> componentsClasses = getComponentClasses();
        for (Class componentClass : componentsClasses) {
            createBean(componentClass);
        }

        for (Class componentClass : componentsClasses) {
            postProcessBean(beans.get(componentClass));
        }
    }

    private void postProcessBean(Object bean) {
        List<Field> fields = Arrays.stream(bean.getClass().getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(AutoWired.class))
                .collect(Collectors.toList());

        for (Field field : fields) {
            field.setAccessible(true);
            Class type = field.getType();

            Object value = getBeanByType(type);

            try {
                field.set(bean,value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public Object getBeanByType(Class type) {
        Object value = beans.keySet().stream().filter(type::isAssignableFrom)
                .findFirst()
        .map(cls->beans.get(cls))
        .orElse(null);
        return value;
    }

    private void createBean(Class componentClass) {
        try {
            Object object = componentClass.getConstructor().newInstance();
            beans.put(componentClass, object);

        } catch (Exception e) {
            throw new RuntimeException("ERROR CREATING BEAN " + componentClass.getSimpleName());
        }
    }

    private List<Class> getComponentClasses() {
        String packkage = "org.example.notesapp";
        List<Class<?>> list1 = packageScanner.findClassesWithAnnotations(Controller.class, packkage);
        List<Class<?>> list2 = packageScanner.findClassesWithAnnotations(Component.class, packkage);

        return Stream.concat(list1.stream(), list2.stream()).collect(Collectors.toList());
    }
}
