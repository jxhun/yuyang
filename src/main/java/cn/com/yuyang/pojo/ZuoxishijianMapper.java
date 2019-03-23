package cn.com.yuyang.pojo;

import cn.com.yuyang.bean.GongGaoBean;
import cn.com.yuyang.pojo.Zuoxishijian;

public interface ZuoxishijianMapper {
    /**
     * 此方法根据档案ID查询上班时间跟下班时间
     * @param gongGaoBean
     * @return
     */
    Kaoqin selectShiJian(GongGaoBean gongGaoBean);

}