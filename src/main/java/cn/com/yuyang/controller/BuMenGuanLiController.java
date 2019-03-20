package cn.com.yuyang.controller;

import cn.com.yuyang.bean.Bumenbean;
import cn.com.yuyang.pojo.Bumen;
import cn.com.yuyang.service.BuMenGuanLiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: huxiaoyi
 * Date: 2019/03/18
 * Description:
 * Version: V1.0
 */
@Controller
@RequestMapping("/bumenguanli")
public class BuMenGuanLiController {

    @Autowired
    private BuMenGuanLiService buMenGuanLiService;

    @ResponseBody
    @RequestMapping(value = {"/shouye"}, method = {RequestMethod.GET,RequestMethod.POST})
    public Map<String, Object> shouye(){
        List<Bumenbean> list = buMenGuanLiService.shouye();
        Map<String,Object> map = new HashMap<>();
        map.put("Returncode",200);
        map.put("msg","成功");
        map.put("data",list);
        return map;
    }


    @ResponseBody
    @RequestMapping(value = {"/chaxun"}, method = {RequestMethod.GET,RequestMethod.POST})
    public Map<String, Object> chaxun(@RequestBody Bumen bumen){
        List<Bumenbean> list = buMenGuanLiService.chaxun(bumen);
        Map<String,Object> map = new HashMap<>();
        map.put("Returncode",200);
        map.put("msg","成功");
        map.put("data",list);
        return map;
    }


    @ResponseBody
    @RequestMapping(value = {"/xinzeng"}, method = {RequestMethod.GET,RequestMethod.POST})
    public Map<String, Object> xinzeng(@RequestBody Bumenbean bum){
        String msg = buMenGuanLiService.xinzeng(bum);
        Map<String,Object> map = new HashMap<>();
        map.put("Returncode",200);
        map.put("msg",msg);
        map.put("data","");
        return map;
    }

    @ResponseBody
    @RequestMapping(value = {"/bianji"}, method = {RequestMethod.GET,RequestMethod.POST})
    public Map<String, Object> bianji(@RequestBody Bumenbean bum){
        String msg = buMenGuanLiService.bianji(bum);
        Map<String,Object> map = new HashMap<>();
        map.put("Returncode",200);
        map.put("msg",msg);
        map.put("data","");
        return map;
    }


    @ResponseBody
    @RequestMapping(value = {"/shanchu"}, method = {RequestMethod.GET,RequestMethod.POST})
    public Map<String, Object> shanchu(@RequestBody Bumenbean bum){
        String msg = buMenGuanLiService.shanchu(bum);
        Map<String,Object> map = new HashMap<>();
        map.put("Returncode",200);
        map.put("msg",msg);
        map.put("data","");
        return map;
    }

}
