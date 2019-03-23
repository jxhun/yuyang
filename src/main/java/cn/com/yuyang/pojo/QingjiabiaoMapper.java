package cn.com.yuyang.pojo;

import cn.com.yuyang.bean.GongGaoBean;
import cn.com.yuyang.pojo.Qingjiabiao;

public interface QingjiabiaoMapper {
    /**
     *
     * 根据人员ID查询为处理的请假消息为几条
     * @param gongGaoBean
     * @return
     */
    int  countQingJia(GongGaoBean gongGaoBean);

}