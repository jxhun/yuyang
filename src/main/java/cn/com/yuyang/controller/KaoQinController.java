package cn.com.yuyang.controller;

import cn.com.yuyang.pojo.Kaoqin;
import cn.com.yuyang.service.KaoQinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Wong
 * Date: 2019/03/18
 * Description:
 * Version: V1.0
 */
@Controller
@RequestMapping("/kaoqing")
public class KaoQinController {
    private KaoQinService kaoQinService;
    @Autowired
    public KaoQinController(KaoQinService kaoQinService){
        this.kaoQinService = kaoQinService;
    }
    public KaoQinService getKaoQinService() {
        return kaoQinService;
    }
    @RequestMapping(value = "select",method = RequestMethod.POST)
    public Map<String,String> select(Kaoqin kaoqin){

    }


}
