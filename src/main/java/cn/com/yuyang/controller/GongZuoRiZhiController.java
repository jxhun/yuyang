package cn.com.yuyang.controller;

import cn.com.yuyang.bean.GeRenKaoQinBean;
import cn.com.yuyang.bean.GongZuoJiLuBean;
import cn.com.yuyang.bean.GongZuoRiZhiBean;
import cn.com.yuyang.pojo.Gongzuojilu;
import cn.com.yuyang.service.GongZuoRiZhiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Wong
 * Date: 2019/03/22
 * Description:
 * Version: V1.0
 */
@Controller
@RequestMapping("/gongzuorizhi")
public class GongZuoRiZhiController {
    private GongZuoRiZhiService gongZuoRiZhiService;
    @Autowired
    public GongZuoRiZhiController(GongZuoRiZhiService gongZuoRiZhiService){
        this.gongZuoRiZhiService = gongZuoRiZhiService;
    }
    public GongZuoRiZhiService gongZuoRiZhiService() {
        return gongZuoRiZhiService;
    }

    /**
     * 查看及查询工作日志与日志草稿
     * @param gongZuoRiZhiBean 内部存个人id检索相关日志，以及查询条件
     * @return 查询到的结果集
     */
    @RequestMapping(value = "selectRiZhi",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> selectRiZhi(@RequestBody(required = false) GongZuoRiZhiBean gongZuoRiZhiBean){
        Map<String,Object> map = new HashMap<>();
        map.put("returnCode",-1);
        try{
            List<Gongzuojilu> list = gongZuoRiZhiService.selectRiZhiByDangAnId(gongZuoRiZhiBean);
            map.put("data",list);
            map.put("msg","查询成功");
            map.put("returnCode",200);
        }catch (Exception e){
            e.printStackTrace();
        }
        return map;
    }

    public Map<String,Object> selectBuMenRiZhi(@RequestBody(required = false) GongZuoRiZhiBean gongZuoRiZhiBean){
        Map<String,Object> map = new HashMap<>();
        map.put("returnCode",-1);
        try{

        }catch (Exception e){
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 更新草稿内容
     * @param gongZuoJiLuBean
     * @return
     */
    @RequestMapping(value = "/updateCaoGao",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> updateCaoGao(@RequestBody(required = false) GongZuoJiLuBean gongZuoJiLuBean){
        Map<String,Object> map = new HashMap<>();
        map.put("returnCode",-1);
        try{
            gongZuoRiZhiService.updateCaoGao(gongZuoJiLuBean);
            map.put("msg","更新成功");
            map.put("returnCode",200);
        }catch (Exception e){
            e.printStackTrace();
        }
        return map;
    }


    /**
     * 插入草稿
     * @param gongZuoJiLuBean
     * @return
     */
    @RequestMapping(value = "/insertCaoGao",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> insertCaoGao(@RequestBody(required = false) GongZuoJiLuBean gongZuoJiLuBean){
        Map<String,Object> map = new HashMap<>();
        map.put("returnCode",-1);
        try{
            gongZuoRiZhiService.insertCaoGao(gongZuoJiLuBean);
            map.put("msg","插入草稿成功");
            map.put("returnCode",200);
        }catch (Exception e){
            e.printStackTrace();
        }
        return map;
    }



    /**
     * 此方法是点击草稿 查看草稿
     * @param gongZuoJiLuBean
     * @return
     */
    @RequestMapping(value = "/caoGao",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> selectCaoGao(@RequestBody(required = false) GongZuoJiLuBean gongZuoJiLuBean){
        Map<String,Object> map = new HashMap<>();
        map.put("returnCode",-1);
        try{
            gongZuoJiLuBean.setZhuangTai(-1);
            List<HashMap<String,Object>> list=gongZuoRiZhiService.selectCaoGao(gongZuoJiLuBean);
            map.put("msg","查询草稿成功");
            map.put("returnCode",200);
            map.put("data",list);
        }catch (Exception e){
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 此方法根据草稿ID删除草稿
     * @param gongZuoJiLuBean
     * @return
     */
    @RequestMapping(value = "/deleteCaoGao",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> deleteCaoGao(@RequestBody(required = false) GongZuoJiLuBean gongZuoJiLuBean){
        Map<String,Object> map = new HashMap<>();
        map.put("returnCode",-1);
        try{
            gongZuoRiZhiService.deleteCaoGao(gongZuoJiLuBean);
            map.put("msg","删除草稿成功");
            map.put("returnCode",200);
        }catch (Exception e){
            e.printStackTrace();
        }
        return map;
    }

    @RequestMapping(value = "/updateZhuangTai",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> updateZhuangTai(@RequestBody(required = false) GongZuoJiLuBean gongZuoJiLuBean){
        Map<String,Object> map = new HashMap<>();
        map.put("returnCode",-1);
        try{
            gongZuoRiZhiService.updateZhuangTai(gongZuoJiLuBean);
            map.put("msg","草稿发表成功");
            map.put("returnCode",200);
        }catch (Exception e){
            e.printStackTrace();
        }
        return map;
    }
}
