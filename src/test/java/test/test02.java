package test;

import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

/**
 * @ClassName: test02 
 * @Description: TODO
 * @author: liuzg
 * @Date: 2022/3/21 11:17
 * @Version: v1.0
 */
@SpringBootTest
public class test02 {

    public static void main(String[] args) {
        ArrayList<Map<String, Object>> list1 = new ArrayList<>();
        HashMap<String, Object> map1 = new HashMap<>();
        map1.put("类型1", 1);
        map1.put("mark", 0);
        HashMap<String, Object> map2 = new HashMap<>();
        map2.put("类型2", 2);
        map2.put("mark", 0);
        list1.add(map1);
        list1.add(map2);

        ArrayList<Map<String, Object>> list2 = new ArrayList<>();
        HashMap<String, Object> map3 = new HashMap<>();
        map3.put("类型1", 3);
        map3.put("mark", 0);
        HashMap<String, Object> map4 = new HashMap<>();
        map4.put("类型2", 4);
        map4.put("mark", 0);
        list2.add(map3);
        list2.add(map4);


        List<List<Map<String, Object>>> resultMap = new ArrayList<>();
        resultMap.add(list1);
        resultMap.add(list2);


        //开始
        //先定义一个list存放所有结果集里的map
        List<Map<String, Object>> custom = new ArrayList<>();
        //遍历结果集  放进自定义的list
        for (List<Map<String, Object>> list : resultMap) {
            for (Map<String, Object> map : list) {
                //先把没用的mark移除 方便后面遍历
                map.remove("mark");
                custom.add(map);
            }
        }

        //遍历自定义list
        for (int i = 0; i < custom.size(); i++) {
            Map<String, Object> map = custom.get(i);
            Iterator<Map<String, Object>> iterator = custom.iterator();
            while (iterator.hasNext()) {
                Map<String, Object> next = iterator.next();
                if (map == next) {
                    continue;
                }
                for (Map.Entry<String, Object> entry : next.entrySet()) {
                    if (map.containsKey(entry.getKey())) {
                        map.put(entry.getKey(), Integer.parseInt(map.get(entry.getKey()).toString()) + Integer.parseInt(entry.getValue().toString()));
                        iterator.remove();
                    }
                }
            }
        }

        System.out.println(custom);
    }

}
