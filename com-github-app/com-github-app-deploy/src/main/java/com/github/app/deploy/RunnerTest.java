package com.github.app.deploy;

import com.github.app.utils.Runner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RunnerTest implements Runner {
    private static final Logger logger = LoggerFactory.getLogger(RunnerTest.class);

    @Override
    public String name() {
        return "test";
    }

    @Override
    public void usage(StringBuilder builder) {
        builder.append("\t-name test\n");
        builder.append("\t\t just for test \n");
    }

    @Override
    public void start(String[] args) {
        new Thread(new Worker()).start();
    }

    public static class Worker implements Runnable {

        @Override
        public void run() {
            long counter = 0;
            while (true) {
                Integer v = Integer.valueOf(3);
                logger.info("Have new Integer counter:{}", Long.valueOf(counter));
                try {
                    Thread.sleep(800);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                counter ++;
            }
        }
    }
}
