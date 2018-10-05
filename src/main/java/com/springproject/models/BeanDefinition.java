package com.springproject.models;

import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by Darion Higgins on 9/21/2018
 * TSO2438
 */
public class BeanDefinition {
    private Executable factoryMethod;
    private Object parentObject;
    private List<BeanDependency> dependencies;

    public BeanDefinition() {
    }

    public Executable getFactoryMethod() {
        return factoryMethod;
    }

    public void setFactoryMethod(Executable factoryMethod) {
        this.factoryMethod = factoryMethod;
    }

    public List<BeanDependency> getDependencies() {
        return dependencies;
    }

    public void setDependencies(List<BeanDependency> dependencies) {
        this.dependencies = dependencies;
    }

    public Object getParentObject() {
        return parentObject;
    }

    public void setParentObject(Object parentObject) {
        this.parentObject = parentObject;
    }
}
