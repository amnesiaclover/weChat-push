package com.itCar.base.api.security.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itCar.base.config.result.ResultBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName: ResponseUtil 
 * @Description: TODO
 * @author: liuzg
 * @Date: 2021/7/27 10:33
 * @Version: v1.0
 */
public class ResponseUtil {

    public static void out(HttpServletResponse response, ResultBody r) {
        ObjectMapper mapper = new ObjectMapper();
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        try {
            mapper.writeValue(response.getWriter(), r);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
