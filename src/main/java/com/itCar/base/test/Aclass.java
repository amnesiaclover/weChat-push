package com.itCar.base.test;

import java.util.concurrent.TimeoutException;

/**
 * @ClassName: Aclass 
 * @Description: TODO
 * @author: liuzg
 * @Date: 2022/2/10 15:29
 * @Version: v1.0
 */
public class Aclass {

    public void method() {
        throw new RuntimeException();
    }



    public void method(int i){
        method();
           }
//    private static int x = 100;
//    public static void main(String[] args) {
//        Aclass a1 = new Aclass();
//        a1.x++;
//        Aclass a2 = new Aclass();
//        a2.x++;
//        a1 = new Aclass();
//        a1.x++;
//        Aclass.x--;
//        System.out.println(x);
//    }



//    void go(){
//        System.out.println("Aclass");
//    }
//}
//class Bclass extends Aclass{
//    void go(){
//        System.out.println("Bclass");
//    }
//
//    public static void main(String args[]){
//        Aclass a = new Aclass();
//        Aclass b = new Bclass();
//        a.go();
//        b.go();
//    }
}
