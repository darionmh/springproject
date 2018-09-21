package com.scarlatti.demo.utils;

import com.scarlatti.demo.configs.BeanConfig;
import com.scarlatti.demo.annotations.Bean;
import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Darion Higgins on 9/21/2018
 * TSO2438
 */
public class AnnotationUtils {
    public static List<Method> getMethods(Object config, Class<? extends Annotation> annotation) {
        return Arrays.stream(config.getClass().getMethods())
                .filter(method -> method.isAnnotationPresent(annotation))
                .collect(Collectors.toList());
    }

    public static List<Class<?>> getClasses(String packageName, Class<? extends Annotation> annotation){
        Reflections reflections = new Reflections(packageName);

        return new ArrayList<>(reflections.getTypesAnnotatedWith(annotation));
    }
}
