package com.springproject;

import com.springproject.annotations.Bean;
import com.springproject.annotations.Component;
import com.springproject.annotations.Config;
import com.springproject.models.BeanDefinition;
import com.springproject.models.BeanDefinitionStore;
import com.springproject.models.BeanInstanceStore;
import com.springproject.predicates.ComponentPredicate;
import com.springproject.predicates.ConfigPredicate;
import com.springproject.runners.CommandLineRunner;
import com.springproject.utils.AnnotationUtils;
import com.springproject.utils.BeanUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

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
    private BeanDefinitionStore beanDefinitionStore;
    private HashMap<String, Class> componentStore;

    public Demo() {
        beanInstanceStore = new BeanInstanceStore();
        beanDefinitionStore = new BeanDefinitionStore();
        componentStore = new HashMap<>();
    }

    public static void main(String[] args) {
        new Demo().run();
    }

    @Override
    public void run() {
        // this particular demo is a baby version of Spring's @Bean with CommandLineRunner
        // Not quite a baby anymore... O.O
        scanForComponents();

        while (!beanDefinitionStore.getValues().isEmpty()) {
            String key = beanDefinitionStore.first();
            BeanDefinition beanDefinition = beanDefinitionStore.getValues().get(key);
            initializeBean(key, beanDefinition);
        }

        runCommandLineRunners();
        System.out.println("Done! :)");
    }

    private void runCommandLineRunners() {
        beanInstanceStore.getValues().filter(o -> o instanceof CommandLineRunner).forEach(o -> ((CommandLineRunner) o).run());
    }

    private void scanForComponents() {
        AnnotationUtils.getClasses(this.getClass().getPackage().getName(), Component.class)
                .forEach(componentClass -> {
                    try {
                        String name;

                        if(new ConfigPredicate().test(componentClass)){
                            name = componentClass.getAnnotation(Config.class).value();
                            name = name.isEmpty() ? componentClass.getSimpleName() : name;
                            Object instance = componentClass.newInstance();
                            beanInstanceStore.addBean(name, instance);
                            AnnotationUtils.getMethods(instance, Bean.class)
                                    .stream()
                                    .map(bean -> Collections.singletonMap(bean.getName(), BeanUtils.createBeanDefinition(bean, instance)))
                                    .forEach(beanMap -> beanDefinitionStore.addBeans(beanMap));
                        }else if(new ComponentPredicate().test(componentClass)){
                            name = componentClass.getAnnotation(Component.class).value();
                            name = name.isEmpty() ? componentClass.getSimpleName() : name;
                            if(componentClass.getDeclaredConstructors().length > 1){
                                throw new RuntimeException("Cannot have more than 1 constructor in a component. "+componentClass);
                            }else if(componentClass.getDeclaredConstructors().length == 0){
                                throw new RuntimeException("You need a constructor in a component. "+componentClass);
                            }
                            beanDefinitionStore.addBean(name, BeanUtils.createBeanDefinition(componentClass.getDeclaredConstructors()[0]));
                        }
                    }catch(Exception e){
                        throw new RuntimeException(e);
                    }
                });
    }

    private Object initializeBean(String name, BeanDefinition beanDefinition) {
        List<Object> dependencies = new ArrayList<>();
        beanDefinition.getDependencies().forEach(beanDependency -> {
            Object o = beanInstanceStore.findBeanOfType(beanDependency.getType());
            String dependencyDefName = beanDefinitionStore.findBeanDefinition(beanDependency.getType());

            if(o != null && dependencyDefName != null){
                throw new RuntimeException("Found multiple instances of bean with type "+beanDependency.getType()+". Use bean names.");
            }

            if (o == null) {
                BeanDefinition dependencyDef = beanDefinitionStore.getValues().get(dependencyDefName);
                if (dependencyDef == null) {
                    throw new RuntimeException("Error creating bean of type: " + beanDefinition.getFactoryMethod().getAnnotatedReturnType() + "\n" +
                            "Could not find bean of type: " + beanDependency.getType());
                }
                o = initializeBean(dependencyDefName, dependencyDef);
                beanDefinitionStore.getValues().remove(dependencyDefName);
            }

            dependencies.add(o);
        });

        try {
            Executable executable = beanDefinition.getFactoryMethod();
            boolean isConstructor = Constructor.class.isAssignableFrom(executable.getClass());

            Object o;

            if(isConstructor){
                o = ((Constructor) beanDefinition.getFactoryMethod()).newInstance(dependencies.toArray());
            }else{
                o = ((Method) beanDefinition.getFactoryMethod()).invoke(beanDefinition.getParentObject(), dependencies.toArray());
            }

            beanInstanceStore.addBean(name, o);
            beanDefinitionStore.getValues().remove(name);
            return o;
        } catch (Exception e) {
            throw new RuntimeException("Error creating bean of type " + ((Method) beanDefinition.getFactoryMethod()).getReturnType(), e);
        }
    }
}
