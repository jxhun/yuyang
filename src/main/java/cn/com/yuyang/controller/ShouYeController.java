package cn.com.yuyang.controller;


import cn.com.yuyang.bean.GongGaoBean;
import cn.com.yuyang.bean.ShouYeBean;
import cn.com.yuyang.bean.YuanGongBean;
import cn.com.yuyang.pojo.*;
import cn.com.yuyang.service.GongGaoService;
import cn.com.yuyang.service.ShouYeService;
import cn.com.yuyang.util.SessionKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/shouye")
@Scope(value = "prototype")
public class ShouYeController {

    private  final ShouYeService shouYeGongHao;


    @Autowired
    public ShouYeController(ShouYeService shouYeGongHao) {
        this.shouYeGongHao = shouYeGongHao;
    }


    @RequestMapping(value = {"/shouye"})
    @ResponseBody
    public Map<String,Object> updateXinXi(@RequestBody(required = false) GongGaoBean gongGaoBean, HttpServletRequest request) {
        Map<String,Object> map = new HashMap<>();
        List<Gonggao> gonggaos = shouYeGongHao.selectGongGao(gongGaoBean);

        List<Gongzuojilu> gongzuojilus = shouYeGongHao.shouYeRiZhi(gongGaoBean);

        int countQingJia = shouYeGongHao.countQingJia(gongGaoBean);//代办事项条数
        int countXinXi = shouYeGongHao.countXinXi(gongGaoBean);//未阅读的信息条数
        int coutGongGao=shouYeGongHao.coutGongGao(gongGaoBean);//获取最新的一天的公告
        int countRiZhi=shouYeGongHao.countRiZhi(gongGaoBean);//获取为审阅的日志
        Kaoqin kaoqin = shouYeGongHao.selectShiJian(gongGaoBean);//获取上班或者下班时间


        map.put("gongGao",gonggaos);
        map.put("riZhi",gongzuojilus);
        map.put("countQingJia",countQingJia);
        map.put("countXinXi",countXinXi);
        map.put("countGongGao",coutGongGao);
        map.put("countRiZhi",countRiZhi);
        map.put("kaoqin",kaoqin);
        Map<String,Object> returnMap = new HashMap<>();


        returnMap.put("returncode", 200);
        returnMap.put("msg", "更新成功，");
        returnMap.put("data",map);
        return returnMap;
    }

    @RequestMapping(value = {"/updateGongGaoShiJian"})
    @ResponseBody
    public Map<String,Object> updateGongGaoShiJian(@RequestBody(required = false) GongGaoBean gongGaoBean, HttpServletRequest request) {
        Map<String,Object> returnMap = new HashMap<>();
        gongGaoBean.setDangAnId(1);
        shouYeGongHao.updateGongGaoDianJi(gongGaoBean);//如果点击了公告则更新
        returnMap.put("returncode", 200);
        returnMap.put("msg", "更新成功，");
        return returnMap;
    }


}
