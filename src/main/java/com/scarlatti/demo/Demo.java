package com.scarlatti.demo;

/**
 * ``_  _ _``_`_|_ _``_ |_  _``_
 * `(_|| (_|| | | (_)|_)| |(/_|
 * ` _|````````````` | tso7938
 *
 * @author Grant Bradshaw
 * @since 9/11/2018
 */
public class Demo implements Runnable {

    public static void main(String[] args) {
        new Demo().run();
    }

    @Override
    public void run() {
        // this particular demo is a baby version of Spring's @Bean with CommandLineRunner

        // need to read the Config class and find all the @Beans and if they implement runnable, run them.
    }
}
