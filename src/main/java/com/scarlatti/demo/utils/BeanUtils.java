package com.scarlatti.demo.utils;

import com.scarlatti.demo.models.BeanDefinition;
import com.scarlatti.demo.models.BeanDependency;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Darion Higgins on 9/21/2018
 * TSO2438
 */
public class BeanUtils {
    public static BeanDefinition createBeanDefinition(Method factoryMethod, Object parent) {
        if(factoryMethod.getReturnType().isAssignableFrom(void.class)){
            throw new RuntimeException("Beans cannot return void.. "+factoryMethod.toString());
        }
        BeanDefinition def = new BeanDefinition();
        def.setFactoryMethod(factoryMethod);
        def.setDependencies(extractDependencies(factoryMethod));
        def.setParentObject(parent);
        return def;
    }

    private static List<BeanDependency> extractDependencies(Method factoryMethod) {
        return Arrays.stream(factoryMethod.getParameters())
                .map(param -> {
                    BeanDependency dependency = new BeanDependency();
                    dependency.setType(param.getType());
                    dependency.setAnnotationList(Arrays.asList(param.getAnnotations()));
                    return dependency;
                })
                .collect(Collectors.toList());
    }
}
