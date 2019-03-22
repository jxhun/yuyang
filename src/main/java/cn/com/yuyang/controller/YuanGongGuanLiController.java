package cn.com.yuyang.controller;

import cn.com.yuyang.bean.Bumenbean;
import cn.com.yuyang.bean.YuanGongBean;
import cn.com.yuyang.pojo.Denglu;
import cn.com.yuyang.service.YuanGongGuanLiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: huxiaoyi
 * Date: 2019/03/19
 * Description:
 * Version: V1.0
 */
@Controller("/renshi")
public class YuanGongGuanLiController {


    @Autowired
    private YuanGongGuanLiService yuanGongGuanLiService;

    @ResponseBody
    @RequestMapping(value = {"/quanchaxun"}, method = RequestMethod.POST)
    public Map<String, Object> quanchaxun(YuanGongBean yuanGongBean, HttpServletRequest request){
        List<Denglu> list = yuanGongGuanLiService.quanCha();
        Map<String,Object> map = new HashMap<>();
        map.put("Returncode",200);
        map.put("msg","成功");
        map.put("data",list);
        map.put("token",yuanGongBean.getToken());
        return map;
    }


    @ResponseBody
    @RequestMapping(value = {"/tiaojiaochaxun"}, method = RequestMethod.POST)
    public Map<String, Object> tiaojianchaxun(YuanGongBean yuanGongBean,HttpServletRequest request){
        List<Denglu> list = yuanGongGuanLiService.tiaoJainCha(yuanGongBean);
        Map<String,Object> map = new HashMap<>();
        map.put("Returncode",200);
        map.put("msg","成功");
        map.put("data",list);
        map.put("token",yuanGongBean.getToken());
        return map;
    }
    @ResponseBody
    @RequestMapping(value = {"/mingxi"}, method = RequestMethod.POST)
    public Map<String, Object> mingxi(YuanGongBean yuanGongBean,HttpServletRequest request){
        List<Denglu> list = yuanGongGuanLiService.tiaoJainCha(yuanGongBean);
        Map<String,Object> map = new HashMap<>();
        map.put("Returncode",200);
        map.put("msg","成功");
        map.put("data",list);
        map.put("token",yuanGongBean.getToken());
        return map;
    }

    @ResponseBody
    @RequestMapping(value = {"/update"}, method = RequestMethod.POST)
    public Map<String, Object> update(YuanGongBean yuanGongBean,HttpServletRequest request){
        String msg = yuanGongGuanLiService.bianji(yuanGongBean);
        Map<String,Object> map = new HashMap<>();
        map.put("Returncode",200);
        map.put("msg",msg);
        map.put("data","");
        map.put("token",yuanGongBean.getToken());
        return map;
    }

    @ResponseBody
    @RequestMapping(value = {"/delete"}, method = RequestMethod.POST)
    public Map<String, Object> delete(YuanGongBean yuanGongBean,HttpServletRequest request){
        String msg = yuanGongGuanLiService.shanchu(yuanGongBean);
        Map<String,Object> map = new HashMap<>();
        map.put("Returncode",200);
        map.put("msg",msg);
        map.put("data","");
        map.put("token",yuanGongBean.getToken());
        return map;
    }
    @ResponseBody
    @RequestMapping(value = {"/xinzeng"}, method = RequestMethod.POST)
    public Map<String, Object> xinzeng(YuanGongBean yuanGongBean,HttpServletRequest request){
        String msg = yuanGongGuanLiService.xinzeng(yuanGongBean);
        Map<String,Object> map = new HashMap<>();
        map.put("Returncode",200);
        map.put("msg",msg);
        map.put("data","");
        map.put("token",yuanGongBean.getToken());
        return map;
    }

}
