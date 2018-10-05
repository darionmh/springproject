package com.springproject.predicates;

import com.springproject.annotations.Config;

import java.util.Arrays;
import java.util.function.Predicate;

public class ConfigPredicate implements Predicate<Class> {
    @Override
    public boolean test(Class clazz) {
        if(clazz.getAnnotation(Config.class) != null){
            return Arrays.stream(clazz.getConstructors()).allMatch(constructor -> constructor.getParameterCount() == 0);
        }

        return false;
    }
}
