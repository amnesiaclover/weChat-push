package com.itCar.base.test;

import com.itCar.base.tools.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * @ClassName: TestFile 
 * @Description: TODO
 * @author: liuzg
 * @Date: 2022/8/17 9:16
 * @Week: 星期三
 * @Version: v1.0
 */
public class TestFile {

    public static void main(String[] args) {
        String separator = FileUtils.getSeparator();
        System.out.println(separator);
        String path = "D:" + separator + "44" + separator + "防爆波活门1.txt";
        File file = new File(path);
        if (file.exists()) {
            try {
                // 使用stream 流按行读取文件
                // 读取文件内容到Stream流中，按行读取
//                Stream<String> lines = Files.lines(Paths.get(path));

//                // 第一种：随机行顺序进行数据处理
//                lines.forEach(line ->{
//                    System.out.println(line);
//                });

                // forEach获取Stream流中的行数据不能保证顺序，但速度快。如果你想按顺序去处理文件中的行数据，可以使用forEachOrdered，但处理效率会下降。
                // 第二种：按文件行顺序进行处理
//                lines.forEachOrdered(System.out::println);

                // 或者利用CPU多和的能力，进行数据的并行处理parallel()，适合比较大的文件。
                // 第三种：按文件行顺序进行处理
//                lines.parallel().forEachOrdered(System.out::println);


                // 经典的管道流的方式
                // 带缓冲的流读取，默认缓冲区8k
//                try (BufferedReader br = new BufferedReader(new FileReader(path))){
//                    String line;
//                    while ((line = br.readLine()) != null) {
//                        System.out.println(line);
//                    }
//                }

                //java 8中这样写也可以
//                try (BufferedReader br = Files.newBufferedReader(Paths.get(path))){
//                    String line;
//                    while ((line = br.readLine()) != null) {
//                        System.out.println(line);
//                    }
//                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println(path + " (文件不存在)");
        }
    }

}
