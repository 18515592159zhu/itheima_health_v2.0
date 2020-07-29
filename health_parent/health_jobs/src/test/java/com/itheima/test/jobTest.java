package com.itheima.test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Martin MYZ
 * @create 2019-07-15-10:33
 */
public class jobTest {

    public static void main(String[] args) {

        new ClassPathXmlApplicationContext("applicationContext-jobs.xml");
    }
}
