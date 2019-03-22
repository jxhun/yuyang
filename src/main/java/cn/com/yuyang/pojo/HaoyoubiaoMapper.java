package cn.com.yuyang.pojo;

import cn.com.yuyang.bean.IdBean;
import cn.com.yuyang.pojo.Haoyoubiao;

import java.util.List;

public interface HaoyoubiaoMapper {

    /**
     * 查询登录人的通讯录
     *
     * @param idBean 前端传入的bean
     * @return 返回Haoyoubiao类型的list集合
     */
    List<Haoyoubiao> chaXunTongXunLu(IdBean idBean);

    /**
     * 这个方法查看单个好友的具体信息
     *
     * @param idBean
     * @return
     */
    Haoyoubiao selectHaoYou(IdBean idBean);

    /**
     * 这个方法用来搜索好友
     * @param idBean 前端传入的bean
     * @return
     */
    List<Haoyoubiao> souSuo(IdBean idBean);
}