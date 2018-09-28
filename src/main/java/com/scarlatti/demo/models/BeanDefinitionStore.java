package com.scarlatti.demo.models;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Created by Darion Higgins on 9/21/2018
 * TSO2438
 */
public class BeanDefinitionStore {

    private Map<String, BeanDefinition> beanDefinitionStore = new HashMap<>();

    public void addBean(String val, BeanDefinition o){
        beanDefinitionStore.put(val, o);
    }

    public void addBeans(Map<String, BeanDefinition> map){
        beanDefinitionStore.putAll(map);
    }

    public HashMap<String, BeanDefinition> getValues(){
        return (HashMap<String, BeanDefinition>) beanDefinitionStore;
    }

    public String findBeanDefinition(Class clazz){
        return beanDefinitionStore.keySet().stream().filter(key -> beanDefinitionStore.get(key).getFactoryMethod().getReturnType().equals(clazz)).findFirst().orElse(null);
    }

    public String first(){
        return beanDefinitionStore.keySet().toArray(new String[]{})[0];
    }
}
