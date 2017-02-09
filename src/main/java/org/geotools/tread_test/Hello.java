package org.geotools.tread_test;

/**
 * Created by jiao.xue on 2017/02/09.
 */
class Hello extends Thread{
    private String name;
    public Hello(){}
    public Hello(String name){
        this.name = name;
    }
    public void run(){
        for(int i=0;i<100;i++){
            System.out.println(this.name + i);
        }
    }
    public static void main(String[] args){
        Hello h1 = new Hello("A");
        Hello h2 = new Hello("B");
        h1.start();
        h2.start();
    }
}