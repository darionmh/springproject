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
@Config("BeanConfig2")
public class BeanConfig2 {

    public BeanConfig2(){}

    @Bean
    public CommandLineRunner printMessage(String val, String val2, int num){
        return new CommandLineRunner() {
            @Override
            public void run() {
                System.out.println(val + " " + num + " " + val2);
            }
        };
    }

    @Bean
    public int count(String val){
        return val.length();
    }

    @Bean
    public String getMessage(){
        return "this is a message";
    }
}
