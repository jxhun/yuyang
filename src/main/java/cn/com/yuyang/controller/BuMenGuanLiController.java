package cn.com.yuyang.controller;

import cn.com.yuyang.service.BuMenShouYeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

    private BuMenShouYeService buMenShouYeService;

    @RequestMapping(value = {"/shouye"}, method = {RequestMethod.GET,RequestMethod.POST})
    public Map<String, Object> shouye(){
        buMenShouYeService.shouye();
        return null;
    }


}
