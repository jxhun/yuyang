package cn.com.yuyang.pojo;

import cn.com.yuyang.bean.XinXiBean;
import cn.com.yuyang.bean.XinXiSouSuoBean;

import java.util.List;

public interface XinxiMapper {

    List<XinXiBean> selectAll(Integer id);
    List<XinXiBean> selectByParam(XinXiSouSuoBean xinXiSouSuoBean);
}