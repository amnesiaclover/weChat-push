package test;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: EasyExcelTest 
 * @Description: TODO
 * @author: liuzg
 * @Date: 2022/3/4 9:40
 * @Version: v1.0
 */
@SpringBootTest
public class EasyExcelTest {

    public static void main(String[] args) {
        // 实现excel写操作
        // 1 设置写入文件夹地址和excel文件名称
        String filename = "D:\\aaa\\学生信息.xlsx";
        // 2 调用easyexcel里面的方法实现写操作
        // write方法两个参数：第一个参数文件路径名称，第二个参数实体类class
        EasyExcel.write(filename, DemoData.class).sheet("学生信息").doWrite(getData());
    }


    // 创建假数据用于导出
    public static List<DemoData> getData() {
        List<DemoData> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData();
            data.setName("张" + i);
            data.setAge(i);
            data.setGander(i + "班级");
            list.add(data);
        }
        return list;
    }
}

//编写entity对象类
@Data
class DemoData {

    @ExcelProperty("姓名")
    private String name;
    private Integer age;
    private String gander;
}
