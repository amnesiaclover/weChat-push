package com.itCar.base.tools;

import java.io.File;
/**
 * @ClassName: FileUtils 
 * @Description: TODO
 * @author: liuzg
 * @Date: 2022/8/17 9:56
 * @Week: 星期三
 * @Version: v1.0
 */
public class FileUtils {

    private static final String separator = "";

    // 获取当前系统环境 文件的分隔符 win // linux \
    public static String getSeparator(){
        return File.separator;
    }

}
