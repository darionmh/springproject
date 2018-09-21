package com.scarlatti.demo.models;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Created by Darion Higgins on 9/21/2018
 * TSO2438
 */
public class BeanInstanceStore{

    private Map<String, Object> beanInstanceStore = new HashMap<>();

    public void addBean(String val, Object o){
        beanInstanceStore.put(val, o);
    }

    public void addBeans(HashMap<String, Object> map){
        beanInstanceStore.putAll(map);
    }

    public Stream<Object> getValues(){
        return beanInstanceStore.values().stream();
    }

    public Object findBeanOfType(Class clazz){
        return getValues().filter(o -> o.getClass().equals(clazz)).findFirst().orElse(null);
    }
}
