package test;

import org.junit.jupiter.api.Test;

import java.io.InputStream;


/**
 * @ClassName: Test1 
 * @Description: TODO
 * @author: liuzg
 * @Date: 2022/2/10 9:47
 * @Version: v1.0
 */
public class Test1 {

    @Test
    void test6(){
        String _a= "";
        String $A= "";
        System.out.println($A);

//        String str = "Abcddefg";
//        String substring = str.substring(1, 4);
//        int indexOf = str.substring(1, 4).indexOf("d");
//        System.out.println(substring+","+indexOf);

//        int i = 9;
//        int j = 9;
//        boolean flag = j==i++;
//        System.out.println("i="+i);
//        System.out.println("j="+j);
//        System.out.println(flag);
//        int a = 0;
//        for (int k = 0; k < 99; k++) {
//            a = a ++;
//        }
//        System.out.println(a);
//        int b = 0;
//        for (int n = 0; n < 99; n++) {
//            b = ++ b;
//        }
//        System.out.println(b);
//        System.out.println("-------------");

//        Integer a = 0;
//        int b = 0;
//        for (int l = 0; l < 99; l++) {
//            a = a ++;
//            b = a ++;
//        }
//        System.out.println(a);
//        System.out.println(b);
    }

    @Test
    void test5(){
        int n = 0;
        for (int i = 1; i < 5; i++) {
            for (int j = 1; j < 5; j++) {
                for (int k = 1; k < 5; k++) {
                    if (i!=k&&i!=j&&j!=k){
                        System.out.println(i+","+j+","+k);
                        n++;
                    }
                }
            }
        }
        System.out.println(n);
    }

    @Test
    void test4(){
        String s = "1,2,3,4,6,7,9,10";
        String arr[] = s.split(",");
        for (String s1 : arr) {
            System.out.println(s1);
        }
    }

    @Test
    void test3(){
        String s = "hello";
        s = s + "world";
        System.out.println(s);

        int arr[] = {1,2,3};
        System.out.println(arr.length); // 属性
        System.out.println(s.length()); // 方法
    }

    @Test // 二维数组中查找某个数字
    void test1(){
        int arr[][] = {
                {1,2,3},
                {4,5,6,7},
                {9}};
        boolean found = false;
        for (int i = 0; i < arr.length && !found; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                System.out.println("i=" + i + "j=" + j);
                System.out.println("="+arr[i][j]);
//                if (arr[i][j] == 5){
//                    found = true;
//                    break;
//                }
            }
        }
    }

    @Test
    void test2(){
        int[][] a = {{1, 2, 3}, {2, 3, 4}};
        //行数
        System.out.println(a.length);
        // 第一行的列数
        System.out.println(a[0].length);
    }

    @Test
    void t(){
        short s1 = 1;
        s1 += 1;
        System.out.println(s1); // =2

        int i = 2 << 3;
        System.out.println("用最有效率算出2乘以8等于几：" +i);
    }

    @Test
    void t1(){
        String a = new String("f");
        String b = new String("f");
        if (a==b){
            System.out.println("1");
        }else {
            System.out.println("2");
        }
    }

    @Test
    void t2(){
        String username = null;
        if ("zxx".equals(username)){

        }
    }

    @Test
    boolean t3(){
        int x= 1;
        return x==1;
    }















}
