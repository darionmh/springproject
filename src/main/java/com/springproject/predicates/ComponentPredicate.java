package com.springproject.predicates;

import com.springproject.annotations.Component;

import java.util.Arrays;
import java.util.function.Predicate;

public class ComponentPredicate implements Predicate<Class> {
    @Override
    public boolean test(Class clazz) {
        if(clazz.isInterface())
            return false;

        return clazz.getAnnotation(Component.class) != null;

    }
}
