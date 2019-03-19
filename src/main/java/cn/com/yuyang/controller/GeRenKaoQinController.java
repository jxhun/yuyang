package cn.com.yuyang.controller.wong;

import cn.com.yuyang.pojo.Kaoqin;
import cn.com.yuyang.service.GeRenKaoQinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Date;
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
    private GeRenKaoQinService geRenKaoQinService;
    @Autowired
    public GeRenKaoQinController(GeRenKaoQinService kaoQinService){
        this.geRenKaoQinService = kaoQinService;
    }
    public GeRenKaoQinService getKaoQinService() {
        return geRenKaoQinService;
    }

    @RequestMapping(value = "select",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> select(@RequestBody int dangAnId,
                                     @RequestBody(required = false) String startDate,
                                     @RequestBody(required = false) String endDate,
                                     @RequestBody(required = false) String date){
        Map<String,Object> map = new HashMap<>();
        map.put("returnCode",-1);
        try {
            List<Kaoqin> list = geRenKaoQinService.selectUser(dangAnId,startDate,endDate,date);
            map.put("msg","lol");
            map.put("data",list);
            map.put("returnCode",200);
        }catch (Exception e){
            e.printStackTrace();
        }
        return map;
    }


}
