package com.springproject.models;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Darion Higgins on 9/21/2018
 * TSO2438
 */
public class BeanDependency {
    private final static Map<Class<?>, Class<?>> primitives = new HashMap<>();
    static {
        primitives.put(boolean.class, Boolean.class);
        primitives.put(byte.class, Byte.class);
        primitives.put(short.class, Short.class);
        primitives.put(char.class, Character.class);
        primitives.put(int.class, Integer.class);
        primitives.put(long.class, Long.class);
        primitives.put(float.class, Float.class);
        primitives.put(double.class, Double.class);
    }

    private String name;
    private Class type;
    private List<Annotation> annotationList;

    public BeanDependency() {
    }

    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = primitives.getOrDefault(type, type);
    }

    public List<Annotation> getAnnotationList() {
        return annotationList;
    }

    public void setAnnotationList(List<Annotation> annotationList) {
        this.annotationList = annotationList;
    }
}
