package com.scarlatti.demo.configs;

import com.scarlatti.demo.runners.CommandLineRunner;
import com.scarlatti.demo.annotations.Bean;
import com.scarlatti.demo.annotations.Config;

/**
 * ``_  _ _``_`_|_ _``_ |_  _``_
 * `(_|| (_|| | | (_)|_)| |(/_|
 * ` _|````````````` | tso7938
 *
 * @author Grant Bradshaw
 * @since 9/11/2018
 */
@Config
public class BeanConfig2 {

    @Bean
    public CommandLineRunner printMessage(String val){
        return new CommandLineRunner() {
            @Override
            public void run() {
                System.out.println(val);
            }
        };
    }

    @Bean
    public String getMessage(){
        return "this is a message";
    }
}
