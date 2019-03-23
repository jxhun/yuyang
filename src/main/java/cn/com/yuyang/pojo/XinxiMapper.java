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


    /**
     * 根据公告bean对象里面的人员ID查询为阅读的信息
     * @param gongGaoBean
     * @return
     */
   int  countXinXi(GongGaoBean gongGaoBean);
}