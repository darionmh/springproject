package com.scarlatti.demo;

import com.scarlatti.demo.annotations.Bean;
import com.scarlatti.demo.annotations.Component;
import com.scarlatti.demo.annotations.Config;
import com.scarlatti.demo.models.BeanDefinition;
import com.scarlatti.demo.models.BeanInstanceStore;
import com.scarlatti.demo.runners.CommandLineRunner;
import com.scarlatti.demo.utils.AnnotationUtils;
import com.scarlatti.demo.utils.BeanUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * ``_  _ _``_`_|_ _``_ |_  _``_
 * `(_|| (_|| | | (_)|_)| |(/_|
 * ` _|````````````` | tso7938
 *
 * @author Grant Bradshaw
 * @since 9/11/2018
 */
public class Demo implements Runnable {

    private BeanInstanceStore beanInstanceStore;
    private Map<String, BeanDefinition> beanDefinitionStore = new HashMap<>();

    public Demo() {
        beanInstanceStore = new BeanInstanceStore();
    }

    public static void main(String[] args) {
        new Demo().run();
    }

    @Override
    public void run() {
        // this particular demo is a baby version of Spring's @Bean with CommandLineRunner
        scanForComponents();
        getBeanFactoryMethods();
        processBeanDefs(0);
        processBeanDefs(1);
        runCommandLineRunners();
        System.out.println("Done! :)");
    }

    private void runCommandLineRunners(){
        beanInstanceStore.getValues().filter(o -> o instanceof CommandLineRunner).forEach(o -> {
            ((CommandLineRunner) o).run();
        });
    }

    private void scanForComponents() {
        AnnotationUtils.getClasses(this.getClass().getPackage().getName(), Component.class)
                .forEach(componentClass -> {
                    if (componentClass.getConstructors().length > 1) {
                        throw new RuntimeException("Construct for " + componentClass.getName() + " shouldn't have more than one constructor. We can't deal with those yet.");
                    } else if (componentClass.getConstructors().length == 1) {
                        Constructor constructor = componentClass.getConstructors()[0];

                        if(constructor.getParameterCount() == 0) {
                            try {
                                beanInstanceStore.addBean(componentClass.getSimpleName(), componentClass.newInstance());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }else{
                            throw new RuntimeException("No args in constructors please. ");
                        }
                    }
                });
    }

    private void getBeanFactoryMethods() {
        beanInstanceStore
                .getValues()
                .filter(component -> component.getClass().isAnnotationPresent(Config.class))
                .forEach(config -> AnnotationUtils.getMethods(config, Bean.class)
                        .stream()
                        .map(bean -> Collections.singletonMap(bean.getName(), BeanUtils.createBeanDefinition(bean, config)))
                        .forEach(beanMap -> beanDefinitionStore.putAll(beanMap))
                );
    }

    private void processBeanDefs(int dependencies){
        List<String> defsToBeRemoved = new ArrayList<>();

        beanDefinitionStore.forEach((key, val) -> {
            if(val.getDependencies().isEmpty()){
                try {
                    beanInstanceStore.addBean(key, val.getFactoryMethod().invoke(val.getParentObject()));
                    defsToBeRemoved.add(key);
                } catch (Exception e) {
                    throw new RuntimeException("Error creating bean of type "+val.getFactoryMethod().getReturnType(), e);
                }
            }else{
                if(dependencies > 0){
                    List<Object> params = new ArrayList<>();
                    val.getDependencies().forEach(beanDependency -> {
                        Object o = beanInstanceStore.findBeanOfType(beanDependency.getType());

                        if(o == null){
                            throw new RuntimeException("Could not find bean of type "+beanDependency.getType());
                        }

                        params.add(o);
                    });
                    try{
                        beanInstanceStore.addBean(key, val.getFactoryMethod().invoke(val.getParentObject(), params.toArray()));
                        defsToBeRemoved.add(key);
                    } catch (Exception e){
                        throw new RuntimeException("Error creating bean of type "+val.getFactoryMethod().getReturnType(), e);
                    }
                }
            }
        });

        defsToBeRemoved.forEach(def -> beanDefinitionStore.remove(def));
    }
}
