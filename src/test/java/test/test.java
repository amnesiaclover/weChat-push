package test;

import com.itCar.base.tools.DateUtil;

import java.util.Date;
import java.util.UUID;

/**
 * @ClassName: test 
 * @Description: TODO
 * @author: liuzg
 * @Date: 2021/7/29 10:03
 * @Version: v1.0
 */
public class test {

    public static void main(String[] args) {
        System.out.println(DateUtil.getDate(6));
        String replace = UUID.randomUUID().toString().replace("-", "").toUpperCase();
        System.out.println(replace.toUpperCase());
    }


    public static void test1() {
        System.out.println(new Date(System.currentTimeMillis() + 6 * 60 * 60 * 1000));
    }


}