package com.resourcetest;

public class SpringResourceTestImpl extends SpringResourceTest{


    public void change(){

        System.out.println(picUrl);
        System.out.println(httpUrl);
        System.out.println(clientId);
        System.out.println(clientKey);

    }

    public static void main(String[] args) {
        SpringResourceTestImpl s = new SpringResourceTestImpl();
        s.change();
    }
}
