package com.springproject.models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        List<String> beanDefs = beanDefinitionStore.keySet().stream().filter(key -> beanDefinitionStore.get(key).getFactoryMethod().getAnnotatedReturnType().getType().equals(clazz)).collect(Collectors.toList());

        if(beanDefs.size() > 1){
            throw new RuntimeException("Found "+beanDefs.size()+" beans of type "+clazz+". Consider using @Qualifier to get a specific bean.");
        }

        if(beanDefs.size() == 0)
            return null;

        return beanDefs.get(0);
    }

    public BeanDefinition findBeanDefinition(String name){
        if(!beanDefinitionStore.containsKey(name)){
            return null;
        }

        return beanDefinitionStore.get(name);
    }

    public String first(){
        return beanDefinitionStore.keySet().toArray(new String[]{})[0];
    }
}
