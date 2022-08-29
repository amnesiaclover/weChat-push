package com.itCar.base.test;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Tags;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @ClassName: IppcTpUrlController 
 * @Description: TODO
 * @author: liuzg
 * @Date: 2022/7/19 11:09
 * @Week: 星期二
 * @Version: v1.0
 */
@Slf4j
@RestController
@RequestMapping("/tpzhurl")
@Api(tags = "图片路径转文件流")
public class IppcTpUrlController {

    @GetMapping(value = "/urlZh")
    @Transactional
    public void urlZh(@RequestParam(value = "url") String url, HttpServletResponse response) throws IOException {
        OutputStream os = null;
        try {
            url=url.replaceAll("%", "%25")//先将地址本身带有的%转为%25
                    .replaceAll(" ", "%20");//再将空格转换为%20
            URL urls = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) urls.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(50 * 1000);
            conn.setReadTimeout(50 * 1000);
            InputStream inStream = conn.getInputStream();//通过输入流获取图片数据
            byte data[] = readInputStream(inStream);
            response.setContentType("image/jpg"); //设置返回的文件类型
            os = response.getOutputStream();
            os.write(data);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != os){
                os.flush();
                os.close();
            }
        }
    }


    public static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[2048];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        return outStream.toByteArray();
    }
}
