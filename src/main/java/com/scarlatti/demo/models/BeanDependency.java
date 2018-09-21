package com.scarlatti.demo.models;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Darion Higgins on 9/21/2018
 * TSO2438
 */
public class BeanDependency {
    private Class type;
    private List<Annotation> annotationList;

    public BeanDependency() {
    }

    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

    public List<Annotation> getAnnotationList() {
        return annotationList;
    }

    public void setAnnotationList(List<Annotation> annotationList) {
        this.annotationList = annotationList;
    }
}
