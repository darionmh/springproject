package com.springproject.configs;

import com.springproject.runners.CommandLineRunner;
import com.springproject.annotations.Bean;
import com.springproject.annotations.Config;

/**
 * ``_  _ _``_`_|_ _``_ |_  _``_
 * `(_|| (_|| | | (_)|_)| |(/_|
 * ` _|````````````` | tso7938
 *
 * @author Grant Bradshaw
 * @since 9/11/2018
 */
@Config("Config")
public class BeanConfig {

    @Bean("Runner1")
    public CommandLineRunner getRunnable1(){
        return new CommandLineRunner() {
            @Override
            public void run() {
                System.out.println("Hello 1");
            }
        };
    }

    @Bean
    public CommandLineRunner getRunnable2(){
        return new CommandLineRunner() {
            @Override
            public void run() {
                System.out.println("Hello 2");
            }
        };
    }

    @Bean
    public CommandLineRunner getRunnable3(){
        return new CommandLineRunner() {
            @Override
            public void run() {
                System.out.println("Hello 3");
            }
        };
    }

    @Bean
    public CommandLineRunner getRunnable4(){
        return new CommandLineRunner() {
            @Override
            public void run() {
                System.out.println("Hello 4");
            }
        };
    }
}
