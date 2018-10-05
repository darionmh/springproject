package com.springproject.runners;

import com.springproject.annotations.Component;

@Component
public class RunnerTest2 extends CommandLineRunner {

    private String string;

    public RunnerTest2(String string){
        this.string = string;
    }

    @Override
    public void run() {
        System.out.println("Printing autowired string: "+string);
    }
}
