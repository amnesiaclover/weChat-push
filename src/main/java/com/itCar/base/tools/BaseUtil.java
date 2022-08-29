package com.itCar.base.tools;

import cn.hutool.core.io.IoUtil;
import com.alibaba.fastjson.JSON;
import com.itCar.base.config.result.ResultBody;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.UUID;

/**
 * @ClassName: BaseUtil 
 * @Description: TODO 导出文件 controller 继承
 * @author: liuzg
 * @Date: 2022/8/11 9:10
 * @Week: 星期四
 * @Version: v1.0
 */
@Slf4j
@SuppressWarnings("all")
public class BaseUtil {

    @Resource
    private HttpServletResponse response;

    @Resource
    private HttpServletRequest request;

    private PrintWriter writer = null;

    private ServletOutputStream out = null;


    public void getTemplate(InputStream template) {
        try {
            if (null == template) {
                response.setContentType("application/json;charset=utf-8");
                response.setHeader("Access-Control-Expose-Headers", "Content-Disposition"); // 20211013
                writer = response.getWriter();
                ResultBody error = new ResultBody("400", "未读取到模板");
                //转成字符串以便输出
                writer.write(JSON.toJSONString(error));
                writer.flush();
                writer.close();
            } else {
                response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                response.setHeader("content-disposition", "attachment;filename=" + UUID.randomUUID().toString() + ".xlsx");
                response.setHeader("Access-Control-Expose-Headers", "Content-Disposition"); // 20211013
                out = response.getOutputStream();
                int line = 0;
                byte[] buffer = new byte[2048];
                while ((line = template.read(buffer)) != -1) {
                    out.write(buffer, 0, line);
                }
                out.flush();
                out.close();
            }
        } catch (IOException e) {
            log.error("模板下载失败: {}", e.getMessage());
        } finally {
            IoUtil.close(template);
        }
    }


    public void write(Workbook workbook) {
        try {
            if (null == workbook) {
                response.setContentType("application/json;charset=utf-8");
                response.setHeader("Access-Control-Expose-Headers", "Content-Disposition"); // 20211013
                writer = response.getWriter();
                ResultBody error = new ResultBody("400", "生成失败,请查看日志");
                //转成字符串以便输出
                writer.write(JSON.toJSONString(error));
                writer.flush();
                writer.close();
            } else {
                String sheetName = workbook.getSheetName(0);
                if (workbook instanceof XSSFWorkbook || workbook instanceof SXSSFWorkbook) {
                    response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                    response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(sheetName, "UTF-8") + ".xlsx");
                    response.setHeader("Access-Control-Expose-Headers", "Content-Disposition"); // 20211013
                } else if (workbook instanceof HSSFWorkbook) {
                    response.setContentType("application/vnd.ms-excel");
                    response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(sheetName, "UTF-8") + ".xls");
                    response.setHeader("Access-Control-Expose-Headers", "Content-Disposition"); // 20211013
                }
                out = response.getOutputStream();
                workbook.write(out);
                out.flush();
                out.close();
            }
        } catch (IOException e) {
            log.error("输出文件失败: " + e.getMessage());
        } finally {
            IoUtil.close(workbook);
        }

    }

    public ServletOutputStream getServletOutputStream(String fileName) {
        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8") + ".xlsx");
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition"); // 20211013
            out = response.getOutputStream();
        } catch (Exception e) {
            return null;
        }
        return out;

    }
}
