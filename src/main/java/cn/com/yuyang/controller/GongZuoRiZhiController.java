package cn.com.yuyang.controller;

import cn.com.yuyang.bean.GeRenKaoQinBean;
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
            System.out.println(gongZuoRiZhiBean.getType());
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
}
