package cn.com.yuyang.pojo;

import cn.com.yuyang.bean.*;

import java.util.List;

public interface XinxiMapper {

    List<XinXiBean> selectAll(Integer id);
    List<XinXiBean> selectByParam(XinXiSouSuoBean xinXiSouSuoBean);
    XinXiBean selecetXiangQing(XiangXiXinXiBean xiangXiXinXiBean);
    void delectXinXi(Integer id,Integer xinXiId,Integer zhuangTai);
    XinXiShanChuChaXunBean chaXun(Integer xinXiId);
    void shouCangXinXi(XinXiShouCangBean xinXiShouCangBean);
    List<XinXiBean> selectYiFaXinXi(IdBean idBean);
}