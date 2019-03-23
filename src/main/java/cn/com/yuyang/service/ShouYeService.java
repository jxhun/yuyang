package cn.com.yuyang.service;

import cn.com.yuyang.bean.GongGaoBean;
import cn.com.yuyang.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShouYeService {

    @Autowired
    private final ZuoxishijianMapper zuoxishijianMapper;  // 用于公告操作


    @Autowired
    private final XinxiMapper xinxiMapper;  // 用于公告操作


    @Autowired
    private final GonggaoMapper gonggaoMapper;  // 用于公告操作

    @Autowired
    private final QingjiabiaoMapper qingjiabiaoMapper;  // 用于公告操作


    @Autowired
    private final RenyuandanganMapper renyuandanganMapper;  // 用于公告操作

    @Autowired
    private final GongzuojiluMapper gongzuojiluMapper;  // 用于公告操作

    public ShouYeService(XinxiMapper xinxiMapper, GonggaoMapper gonggaoMapper, QingjiabiaoMapper qingjiabiaoMapper, RenyuandanganMapper renyuandanganMapper, GongzuojiluMapper gongzuojiluMapper,ZuoxishijianMapper zuoxishijianMapper) {
        this.xinxiMapper = xinxiMapper;
        this.gonggaoMapper = gonggaoMapper;
        this.qingjiabiaoMapper = qingjiabiaoMapper;
        this.renyuandanganMapper = renyuandanganMapper;
        this.gongzuojiluMapper = gongzuojiluMapper;
        this.zuoxishijianMapper=zuoxishijianMapper;
    }


    /**
     * 这个方法用来查询当前登录人部门权限能看到的所有公告
     *
     * @param gongGaoBean 公告bean，里面存储的有部门的id，档案登录人的档案id
     * @return
     */
    public List<Gonggao> selectGongGao(GongGaoBean gongGaoBean) {
        return gonggaoMapper.selectGongGao(gongGaoBean);   // 调用查询方法，返回查询结果
    }

    public List<Gongzuojilu> shouYeRiZhi(GongGaoBean gongGaoBean){

        return renyuandanganMapper.shouYeRiZhi(gongGaoBean);
    }
     public int countQingJia(GongGaoBean gongGaoBean){
        return  qingjiabiaoMapper.countQingJia(gongGaoBean);
     }

    public int countXinXi(GongGaoBean gongGaoBean){
        return  xinxiMapper.countXinXi(gongGaoBean);
    }


    public int coutGongGao(GongGaoBean gongGaoBean){
        return  gonggaoMapper.coutGongGao(gongGaoBean);
    }


    public void updateGongGaoDianJi(GongGaoBean gongGaoBean){

    }

    public int countRiZhi(GongGaoBean gongGaoBean){
            return  gongzuojiluMapper.countRiZhi(gongGaoBean);
    }

    public Kaoqin selectShiJian(GongGaoBean gongGaoBean){
        return  zuoxishijianMapper.selectShiJian(gongGaoBean);
    }





}
