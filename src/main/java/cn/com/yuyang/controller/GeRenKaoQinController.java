package cn.com.yuyang.controller;

import cn.com.yuyang.bean.GeRenKaoQinBean;
import cn.com.yuyang.pojo.Kaoqin;
import cn.com.yuyang.service.GeRenKaoQinService;
import cn.com.yuyang.service.KaoQinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Wong
 * Date: 2019/03/18
 * Description:
 * Version: V1.0
 */
@Controller
@RequestMapping("/kaoqin")
public class GeRenKaoQinController {
    private KaoQinService kaoQinService;
    @Autowired
    public GeRenKaoQinController(KaoQinService kaoQinService){
        this.kaoQinService = kaoQinService;
    }
    public KaoQinService getKaoQinService() {
        return kaoQinService;
    }

    @RequestMapping(value = "select",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public Map<String,Object> select(@RequestBody(required = false) GeRenKaoQinBean geRenKaoQinBean, HttpServletRequest request){
        // 先创建map并将returnCode为-1放入，一旦之后的代码报错直接return
        Map<String,Object> map = new HashMap<>();
        map.put("returnCode",-1);
        // 判断服务器session上存储的操作员档案id与请求个人考勤的目标档案id是否对应，避免恶意查看他人考勤
//        if (request.getSession().getAttribute(SessionKey.DANGANID).equals(dangAnId)){
            try {
                List<Kaoqin> list = kaoQinService.selectUser(geRenKaoQinBean);
                List<Map<String,Object>> mapList = new ArrayList<>();
                for(Kaoqin kaoqin:list){
                    Map<String,Object> map1 = new HashMap<>();
                    map1.put("dangTianRiQi",kaoqin.getDangTianRiQi().toString());
                    if (kaoqin.getShangWuShangBan() != null){
                        map1.put("shangWuShangBan",kaoqin.getShangWuShangBan().toString());
                    }
                    else {
                        map1.put("shangWuShangBan","/");
                    }
                    if (kaoqin.getXiaWuXiaBan() != null){
                        map1.put("xiaWuXiaBan",kaoqin.getXiaWuXiaBan().toString());
                    }
                    else {
                        map1.put("xiaWuXiaBan","/");
                    }
                    map1.put("zhuangTai",kaoqin.getZhuangTai());
                    map1.put("qingJiaZhuangTai",kaoqin.getQingJiaZhuangTai());
                    mapList.add(map1);
                }
                map.put("msg","正常考勤用id=1，迟到考勤用id=3，早退考勤用id=5，旷班考勤用id=7，请假考勤用id=8");
                map.put("data",mapList);
                // 覆盖之前的-1值
                map.put("returnCode",200);
            }catch (Exception e){
                e.printStackTrace();
            }
//        }
        return map;
    }

    @RequestMapping(value = "selectBingJia",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public Map<String,Object> selectBingJia(@RequestBody(required = false) GeRenKaoQinBean geRenKaoQinBean, HttpServletRequest request){
        // 先创建map并将returnCode为-1放入，一旦之后的代码报错直接return
        Map<String,Object> map = new HashMap<>();
        map.put("returnCode",-1);
        // 判断服务器session上存储的操作员档案id与请求个人考勤的目标档案id是否对应，避免恶意查看他人考勤
//        if (request.getSession().getAttribute(SessionKey.DANGANID).equals(dangAnId)){
        try {
            List<Kaoqin> list = kaoQinService.selectBing(geRenKaoQinBean);
            List<Map<String,Object>> mapList = new ArrayList<>();
            for(Kaoqin kaoqin:list){
                Map<String,Object> map1 = new HashMap<>();
                map1.put("dangTianRiQi",kaoqin.getDangTianRiQi().toString());
                map1.put("qingJiaShiYou",kaoqin.getQingjiabiao().getQingJiaShiYou());
                map1.put("zhuangTai",kaoqin.getQingjiabiao().getZhuangTai());
                mapList.add(map1);
            }
            map.put("msg","正常考勤用id=1，迟到考勤用id=3，早退考勤用id=5，旷班考勤用id=7，请假考勤用id=8");
            map.put("data",mapList);
            // 覆盖之前的-1值
            map.put("returnCode",200);
        }catch (Exception e){
            e.printStackTrace();
        }
//        }
        return map;
    }
}
