package com.springproject.models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
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
        List<Object> beanInstances = getValues().filter(o -> o.getClass().equals(clazz)).collect(Collectors.toList());

        if(beanInstances.size() > 1){
            throw new RuntimeException("Found "+beanInstances.size()+" beans of type "+clazz+". Consider using @Qualifier to specify bean name");
        }

        if(beanInstances.size() == 0)
            return null;

        return beanInstances.get(0);
    }

    public Object findBeanWithName(String name){
        if(!beanInstanceStore.containsKey(name)){
            return null;
        }

        return beanInstanceStore.get(name);
    }
}
