package com.zjy.log;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Administrator on 2019/6/3.
 */
public class LogbackTest {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public static void main(String[] args) {
        LogbackTest logback = new LogbackTest();
        logback.testLog();
    }

    private void testLog() {
        logger.debug("print debug log.");
        logger.info("print info log.");
        logger.error("print error log.",new Exception(" This My Error"));
    }
}
