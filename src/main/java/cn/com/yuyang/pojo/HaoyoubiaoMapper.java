package cn.com.yuyang.pojo;

import cn.com.yuyang.pojo.Haoyoubiao;

import java.util.List;

public interface HaoyoubiaoMapper {

    /**
     * 查询登录人的通讯录
     * @param dangAnId 登录人的档案id
     * @return  返回Haoyoubiao类型的list集合
     */
    List<Haoyoubiao> chaXunTongXunLu(Integer dangAnId);
}