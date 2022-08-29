package com.itCar.base.tools;

import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName: StringUtil 
 * @Description: TODO
 * @author: liuzg
 * @Date: 2022/8/11 9:16
 * @Week: 星期四
 * @Version: v1.0
 */
public class StringUtil extends StringUtils {


    public static String replaceSpecialCharacters(String inStr) {
        String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(inStr);
        return matcher.replaceAll("");
    }
}

