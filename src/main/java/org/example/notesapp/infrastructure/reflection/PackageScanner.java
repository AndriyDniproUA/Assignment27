package org.example.notesapp.infrastructure.reflection;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PackageScanner {

    private List<Class<?>> findClasses(File directory, String prefix) throws ClassNotFoundException {
        List<Class<?>> result = new ArrayList<>();
        for (File f : directory.listFiles()) {
            if (f.isDirectory()) {
                result.addAll(findClasses(f, prefix + "." + f.getName()));
            } else if (f.isFile() && f.getName().endsWith(".class")) {

                String fileName = f.getName();
                String className = prefix + "."+fileName.substring(0, fileName.length() - 6);
                result.add(Class.forName(className));
            }
        }
        return result;
    }

    public List<Class<?>> scanPackage(String packageName) {
        List<Class<?>> result = new ArrayList<>();
        try {
            String path = packageName.replace(".", "/");
            URL url = getClass().getClassLoader().getResource(path);
            File file = new File(url.toURI());
            return findClasses(file, packageName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public List<Class<?>> findClassesWithAnnotations(Class annotation, String packageName) {
        return scanPackage(packageName).stream()
                .filter(c->c.isAnnotationPresent(annotation))
                .collect(Collectors.toList());
    }

}
