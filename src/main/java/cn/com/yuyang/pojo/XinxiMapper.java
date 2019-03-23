package cn.com.yuyang.pojo;

import cn.com.yuyang.bean.*;

import java.util.List;

public interface XinxiMapper {
    List<XinXiBean> selectAll(Integer id);
    List<XinXiBean> selectByParam(XinXiSouSuoBean xinXiSouSuoBean);
    XinXiBean selecetXiangQing(XiangXiXinXiBean xiangXiXinXiBean);
    void delectXinXi(Integer id, Integer xinXiId, Integer zhuangTai);
    XinXiShanChuChaXunBean chaXun(Integer xinXiId);
    XinXiShouCangChaXunBean xinXiShouCangChaXun(Integer xinXiId);
    void shouXinRenShouCangXinXi(XinXiShouCangBean xinXiShouCangBean);
    void faXinRenShouCangXinXi(XinXiShouCangBean xinXiShouCangBean);
    List<XinXiBean> selectYiFaXinXi(IdBean idBean);
    List<XinXiBean> yiFaXinXiSouSuo(XinXiSouSuoBean xinXiSouSuoBean);
    List<XinXiBean> selectXinXiShouCang(IdBean idBean);
    List<XinXiBean> xinXiShouCangSouSuo(XinXiSouSuoBean xinXiSouSuoBean);
    List<XinXiBean> xinXiCaoGaoXiang(IdBean idBean);
    void  caoGaoXiangShanChu(XinXiShanChuBean xinXiShanChuBean);
    List<XinXiBean> xinXiCaoGaoXiangSouSuo(XinXiSouSuoBean xinXiSouSuoBean);
    void yiDu(XiangXiXinXiBean xiangXiXinXiBean);
    void faSongXinXi(XinXiCunChuBean xinXiCunChuBean);
    void cunRuCaoGao(XinXiCunChuBean xinXiCunChuBean);
    List<ShouJianRenBean> selectShouJianRen(IdBean idBean);
    void faSongCaoGao(XinXiCunChuBean xinXiCunChuBean);

}