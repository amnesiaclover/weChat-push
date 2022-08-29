package com.itCar.base.api.wxApi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itCar.base.api.wxApi.entity.UcenterMember;
import org.apache.ibatis.annotations.Mapper;

/**
 * @ClassName: UcenterMemberMapper 
 * @Description: TODO
 * @author: liuzg
 * @Date: 2021/7/30 9:56
 * @Version: v1.0
 */
@Mapper
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {

    // 查询某一天注册人数
    Integer countRegisterDay(String day);
}
