package cn.com.yuyang.service;

import cn.com.yuyang.bean.*;
import cn.com.yuyang.pojo.Xinxi;
import cn.com.yuyang.pojo.XinxiMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.List;

@Service
public class MessageService {
    @Autowired
    public XinxiMapper xinxiMapper;

    public XinxiMapper getXinxiMapper() {
        return xinxiMapper;
    }

    public void setXinxiMapper(XinxiMapper xinxiMapper) {
        this.xinxiMapper = xinxiMapper;
    }

    //通过档案id，查询该用户下所有信息
    public List<XinXiBean> selectAll(int id){
        List<XinXiBean> list = xinxiMapper.selectAll(id);   //返回所有信息的list

        return list;
    }
    //通过XinXiBean对象，按条件查询信息
    public List<XinXiBean> selectByParam(XinXiSouSuoBean xinXiSouSuoBean){
        List<XinXiBean> list = xinxiMapper.selectByParam(xinXiSouSuoBean);
        return list;
    }
    //通过XiangXiXinXiBean里的id和xinXiId，返回该条信息的详细信息
    public XinXiBean selectXiangQing(XiangXiXinXiBean xiangXiXinXiBean){
        XinXiBean xinXiBean = xinxiMapper.selecetXiangQing(xiangXiXinXiBean);
        return xinXiBean;
    }
    //通过XinXiShanChuBean的id和xinXiId，修改需要修改的每条信息的状态为0，表示删除
    public void delectXinXi(XinXiShanChuBean xinXiShanChuBean){
        int  id =xinXiShanChuBean.getId();
        int[] shanChuId=xinXiShanChuBean.getXinXiId();
        for (int xinXiId: shanChuId
             ) {
            XinXiShanChuChaXunBean xinXiShanChuChaXunBean =xinxiMapper.chaXun(xinXiId);
            int shouXinId= xinXiShanChuChaXunBean.getShouXinId();
            int faXinId= xinXiShanChuChaXunBean.getFaXinId();
            int zhuangTai= xinXiShanChuChaXunBean.getZhuangTai();
            if(zhuangTai !=0) {
                xinxiMapper.delectXinXi(id, xinXiId,-1);
            }
            else if (shouXinId ==xinXiShanChuBean.getId()){
                xinxiMapper.delectXinXi(id, xinXiId,2);
            }
            else if(faXinId ==xinXiShanChuBean.getId()){
                xinxiMapper.delectXinXi(id,xinXiId,1);
            }
        }

    }
    /*根据传来的XinXiShouCangBean中的登陆者档案id，
    * 需要修改的xinXiId，信息当前收藏状态shouCangZhuangTai，修改该条信息的收藏状态
    */
    public void shouCangXinXi(XinXiShouCangBean xinXiShouCangBean){
        XinXiShouCangChaXunBean xinXiShouCangChaXunBean = xinxiMapper.xinXiShouCangChaXun(xinXiShouCangBean.getXinXiId());
        if (xinXiShouCangChaXunBean.getFaXinId()==xinXiShouCangBean.getId()){
            xinxiMapper.faXinRenShouCangXinXi(xinXiShouCangBean);
        }
        else{
            xinxiMapper.shouXinRenShouCangXinXi(xinXiShouCangBean);
        };
    }

    //根据传来的IdBean中的登陆者档案id，查询该用户的所有已发送信息

    public List<XinXiBean> selectYiFaXinXi(IdBean idBean){
        List<XinXiBean> list = xinxiMapper.selectYiFaXinXi(idBean);
        return list;
    }
    //根据XinXiShouSuoBean的登陆者档案id，输入的搜索条件，按条件搜索已发信息并返回
    public List<XinXiBean> yiFaXinXiSouSuo(XinXiSouSuoBean xinXiSouSuoBean){
        List<XinXiBean> list =xinxiMapper.yiFaXinXiSouSuo(xinXiSouSuoBean);
        return list;
    }
    //根据IdBean中的登陆者id，返回该登陆者所有的未删除的收藏信息，
    public  List<XinXiBean> selectXinXiShouCang(IdBean idBean){
        List<XinXiBean> list = xinxiMapper.selectXinXiShouCang(idBean);
        return list;
    }
    //根据XinXiSouSuoBean中的登陆者id，输入的搜索条件，按条件搜索收藏信息
    public List<XinXiBean> xinXiShouCangSouSuo(XinXiSouSuoBean xinXiSouSuoBean){
        List<XinXiBean> list =xinxiMapper.xinXiShouCangSouSuo(xinXiSouSuoBean);
        return list;
    }

    //根据IdBean中的登陆者id，查询并返回该用户下所有草稿箱信息
    public List<XinXiBean> xinXiCaoGaoXiang(IdBean idBean){
        List<XinXiBean> list = xinxiMapper.xinXiCaoGaoXiang(idBean);
        return list;
    }
    //根据XinXiShanChuBean中的xinXiId，登陆者档案id，从数据库中删除草稿箱的信息
    public  void caoGaoXiangShanChu(XinXiShanChuBean xinXiShanChuBean){
        xinxiMapper.caoGaoXiangShanChu(xinXiShanChuBean);
    }

    //根据XinXiSouSuoBean中的登陆者档案id，日期，关键词等搜索条件，查找草稿箱中的信息
    public List<XinXiBean> xinXiCaoGaoXiangSouSuo(XinXiSouSuoBean xinXiSouSuoBean){
        List<XinXiBean> list = xinxiMapper.xinXiCaoGaoXiangSouSuo(xinXiSouSuoBean);
        return  list;
    }
    //当登陆者点击信息进入详情页面，修改信息的已读状态为已读
    public void yiDu(XiangXiXinXiBean xiangXiXinXiBean){
        xinxiMapper.yiDu(xiangXiXinXiBean);
    }
    //调用此方法，将登陆者要发送的信息存入数据库。


    //调用此方法，将要发送的信息存入数据库
    public void faSongXinXi(XinXiFaSongBean xinXiFaSongBean, HttpSession session){
        String luJing =(String) session.getAttribute("luJing");
        XinXiCunChuBean xinXiCunChuBean = new XinXiCunChuBean();
        xinXiCunChuBean.setFaXinId(xinXiFaSongBean.getId());
        xinXiCunChuBean.setShouXinId(xinXiFaSongBean.getShouJianId());
        if(xinXiFaSongBean.getXinXiZhuTi()!=null&&!xinXiFaSongBean.getXinXiZhuTi().equals("")){
            xinXiCunChuBean.setXinXiBiaoTi(xinXiFaSongBean.getXinXiZhuTi());
        }
        if (xinXiFaSongBean.getXinXiNeiRong()!=null&&!xinXiFaSongBean.getXinXiNeiRong().equals("")){
            xinXiCunChuBean.setXinXiNeiRong(xinXiFaSongBean.getXinXiNeiRong());
        }
        xinXiCunChuBean.setZhuangTai(0);
        xinXiCunChuBean.setXinXiShiJian(new Timestamp(System.currentTimeMillis()));

        if (luJing!=null && !luJing.equals("")){
            xinXiCunChuBean.setFuJianDiZhi(luJing);
            session.removeAttribute("luJing");
        }
        else{
            xinXiCunChuBean.setFuJianDiZhi(null);
        }
        xinXiCunChuBean.setFaSongZhuangTai(1);
        xinXiCunChuBean.setShouJianRenShouCangZhuangTai(0);
        xinXiCunChuBean.setFaJianRenShouCangZhuangTai(0);
        xinXiCunChuBean.setYiDuZhuangTai(0);
        xinxiMapper.faSongXinXi(xinXiCunChuBean);
    }

    //调用此方法，将信件存入草稿箱
    public void cunRuCaoGao(XinXiFaSongBean xinXiFaSongBean){
        XinXiCunChuBean xinXiCunChuBean = new XinXiCunChuBean();
        xinXiCunChuBean.setFaXinId(xinXiFaSongBean.getId());
        xinXiCunChuBean.setShouXinId(xinXiFaSongBean.getShouJianId());
        if(xinXiFaSongBean.getXinXiZhuTi()!=null&&!xinXiFaSongBean.getXinXiZhuTi().equals("")){
            xinXiCunChuBean.setXinXiBiaoTi(xinXiFaSongBean.getXinXiZhuTi());
        }
        if (xinXiFaSongBean.getXinXiNeiRong()!=null &&!xinXiFaSongBean.getXinXiNeiRong().equals("")){
            xinXiCunChuBean.setXinXiNeiRong(xinXiFaSongBean.getXinXiNeiRong());
        }
        xinXiCunChuBean.setZhuangTai(0);
        xinXiCunChuBean.setFaSongZhuangTai(0);
        xinXiCunChuBean.setShouJianRenShouCangZhuangTai(0);
        xinXiCunChuBean.setFaJianRenShouCangZhuangTai(0);
        xinXiCunChuBean.setYiDuZhuangTai(0);
        xinxiMapper.cunRuCaoGao(xinXiCunChuBean);
    }

    //查询并返回所有收件人的姓名，工号，档案id
    public List<ShouJianRenBean> selectShouJianRen(IdBean idBean){
        List<ShouJianRenBean> list = xinxiMapper.selectShouJianRen(idBean);
        return list;
    }

    //调用此方法，修改信息发送时间，发送状态，更新信件内容，标题等
    public void faSongCaoGao(CaoGaoFaSongBean caoGaoFaSongBean, HttpSession session){
        String luJing =(String) session.getAttribute("luJing");
        XinXiCunChuBean xinXiCunChuBean = new XinXiCunChuBean();
        xinXiCunChuBean.setId(caoGaoFaSongBean.getXinXiId());
        xinXiCunChuBean.setFaXinId(caoGaoFaSongBean.getId());
        xinXiCunChuBean.setShouXinId(caoGaoFaSongBean.getShouJianRenId());
        if(caoGaoFaSongBean.getZhuTi()!=null&&!caoGaoFaSongBean.getZhuTi().equals("")){
            xinXiCunChuBean.setXinXiBiaoTi(caoGaoFaSongBean.getZhuTi());
        }
        if (caoGaoFaSongBean.getNeiRong()!=null &&caoGaoFaSongBean.getNeiRong()!=""){
            xinXiCunChuBean.setXinXiNeiRong(caoGaoFaSongBean.getNeiRong());
        }
        xinXiCunChuBean.setXinXiShiJian(new Timestamp(System.currentTimeMillis()));
        if (luJing!=null && !luJing.equals("")){
            xinXiCunChuBean.setFuJianDiZhi(luJing);
            session.removeAttribute("luJing");
        }
        else{
            xinXiCunChuBean.setFuJianDiZhi(null);
        }
        xinXiCunChuBean.setFaSongZhuangTai(1);
        xinxiMapper.faSongCaoGao(xinXiCunChuBean);
    }

}
