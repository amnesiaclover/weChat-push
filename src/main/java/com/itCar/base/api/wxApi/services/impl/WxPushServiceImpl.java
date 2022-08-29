package com.itCar.base.api.wxApi.services.impl;

import com.alibaba.fastjson.JSONObject;
import com.itCar.base.api.wxApi.services.WxPushService;
import com.itCar.base.config.result.ResultBody;
import com.itCar.base.tools.DateUtil;
import com.itCar.base.tools.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.*;

/**
 * @ClassName: WxPushServiceImpl 
 * @Description: TODO
 * @author: liuzg
 * @Date: 2022/8/29 9:51
 * @Week: 星期一
 * @Version: v1.0
 */
@Slf4j
@Service
public class WxPushServiceImpl implements WxPushService {

    @Value("${wx.config.openid}")
    private String openid;
    @Value("${wx.config.templateId}")
    private String templateId;
    @Value("${weather.config.appid}")
    private String weatherAppId;
    @Value("${weather.config.appSecret}")
    private String weatherAppSecret;
    @Value("${message.config.togetherDate}")
    private String togetherDate;
    @Value("${message.config.birthday}")
    private String birthday;
    @Value("${message.config.message}")
    private String message;


    /**
     * 发送微信消息
     *
     * @return
     */
    @Override
    public ResultBody sendWeChatMsg(String accessToken) {

        String[] openIds = openid.split(",");
        List<JSONObject> errorList = new ArrayList();
        for (String opedId : openIds) {
            JSONObject templateMsg = new JSONObject(new LinkedHashMap<>());
            String date = DateUtil.formatDate(new Date(), "yyyy-MM-dd");

            // 获取当前日期
            JSONObject first = getCurrentDay(date);

            // 获取当前城市信息
            JSONObject cityInfo = getCurrentCityInfo();

            JSONObject city = new JSONObject();
            city.put("value", cityInfo.getString("address"));
            city.put("color", "#60AEF2");

            String weather = cityInfo.getString("weatherStatus") + ", 温度：" +
                    cityInfo.getString("tem_night") + " ~ " + cityInfo.getString("tem_day");

            JSONObject temperatures = new JSONObject();
            temperatures.put("value", weather);
            temperatures.put("color", "#44B549");

            // 计算生日
            JSONObject birthDate = getBirthday(date);
            JSONObject birth = getBirthday(DateUtil.addDate(date, -9));

            // 计算在一起时间
            JSONObject togetherDate = getTogetherDate(date);

            // 组合信息
            JSONObject data = new JSONObject(new LinkedHashMap<>());
            data.put("first", first);
            data.put("city", city);
            data.put("temperature", temperatures);
            data.put("togetherDate", togetherDate);
            data.put("birthDate", birthDate);
            data.put("birth", birth);
            data.put("message", getMessage());
//            data.put("message", "傻儿子想什么呢");

            // 将组合信息放入模板中进行赋值推送
            templateMsg.put("touser", opedId);
            templateMsg.put("template_id", templateId);
            templateMsg.put("data", data);
            // 发送
            errorList = sendWxPush(accessToken, templateMsg, opedId, errorList);
        }

        if (0 >= errorList.size() && errorList.isEmpty()) {
            return ResultBody.success("推送成功");
        } else {
            return ResultBody.error("服务错误");
        }
    }

    /**
     * 发送微信推送
     *
     * @param accessToken
     * @param templateMsg
     * @param opedId
     * @param errorList
     */
    public List<JSONObject> sendWxPush(String accessToken, JSONObject templateMsg, String opedId, List<JSONObject> errorList) {
        String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + accessToken;

        String sendPost = HttpUtil.sendPost(url, templateMsg.toJSONString());
        JSONObject WeChatMsgResult = JSONObject.parseObject(sendPost);
        if (!"0".equals(WeChatMsgResult.getString("errcode"))) {
            JSONObject error = new JSONObject();
            error.put("openid", opedId);
            error.put("errorMessage", WeChatMsgResult.getString("errmsg"));
            errorList.add(error);
        }
        log.info("sendPost=" + sendPost);
        return errorList;
    }

    /**
     * 获取当前日期
     *
     * @return
     */
    public JSONObject getCurrentDay(String date) {
        JSONObject first = new JSONObject();
        String week = DateUtil.getWeekOfDate(new Date());
        String day = date + " " + week;
        first.put("value", day);
        first.put("color", "#EED016");
        return first;
    }

    /**
     * 获取当前城市信息(市级、天气)
     *
     * @return
     */
    public JSONObject getCurrentCityInfo() {
        String TemperatureUrl = "https://www.yiketianqi.com/free/day?appid=" + weatherAppId + "&appsecret=" + weatherAppSecret + "&unescape=1";
        String currentCityInfo = HttpUtil.sendGet(TemperatureUrl, null);
        String address = ""; // 城市
        String tem_day = ""; // 最高温度
        String tem_night = ""; // 最低温度
        String weatherStatus = ""; // 天气
        JSONObject temperature = JSONObject.parseObject(currentCityInfo);
        if (temperature.getString("city") != null) {
            tem_day = temperature.getString("tem_day") + "°";
            tem_night = temperature.getString("tem_night") + "°";
            address = temperature.getString("city");
            weatherStatus = temperature.getString("wea");
        }
        JSONObject obj = new JSONObject();
        obj.put("tem_day", tem_day);
        obj.put("tem_night", tem_night);
        obj.put("address", address);
        obj.put("weatherStatus", weatherStatus);
        return obj;
    }

    /**
     * 计算还有多久生日
     *
     * @param date
     * @return
     */
    public JSONObject getBirthday(String date) {
        JSONObject birthDate = new JSONObject();
        String birthDay = "";
        try {
            Calendar calendar = Calendar.getInstance();
            String newD = calendar.get(Calendar.YEAR) + "-" + birthday;
            birthDay = DateUtil.daysBetween(date, newD);
            if (Integer.parseInt(birthDay) < 0) {
                Integer newBirthDay = Integer.parseInt(birthDay) + 365;
                birthDay = newBirthDay + "天";
            } else {
                birthDay = birthDay + "天";
            }
        } catch (ParseException e) {
            log.error("togetherDate获取失败" + e.getMessage());
        }
        birthDate.put("value", birthDay);
        birthDate.put("color", "#6EEDE2");
        return birthDate;
    }

    /**
     * 计算在一起时间
     *
     * @param date
     * @return
     */
    public JSONObject getTogetherDate(String date) {
        JSONObject togetherDateObj = new JSONObject();
        String togetherDay = "";
        try {
            togetherDay = "第" + DateUtil.daysBetween(togetherDate, date) + "天";
        } catch (ParseException e) {
            log.error("togetherDate获取失败" + e.getMessage());
        }
        togetherDateObj.put("value", togetherDay);
        togetherDateObj.put("color", "#FEABB5");

        return togetherDateObj;
    }

    /**
     * 获取配置中的信息说明
     *
     * @return
     */
    public JSONObject getMessage() {
        JSONObject messageObj = new JSONObject();
        messageObj.put("value", message);
        messageObj.put("color", "#C79AD0");
        return messageObj;
    }
}
