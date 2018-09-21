package com.scarlatti.demo.runners;

import com.scarlatti.demo.annotations.Component;

/**
 * Created by Darion Higgins on 9/21/2018
 * TSO2438
 */
@Component
public class RunnerTest extends CommandLineRunner {

    @Override
    public void run() {
        System.out.println("Hello from the other side");
    }
}
