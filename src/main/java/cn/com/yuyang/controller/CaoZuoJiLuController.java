package cn.com.yuyang.controller;

import cn.com.yuyang.bean.Bumenbean;
import cn.com.yuyang.bean.CaoZuoJiLuBean;
import cn.com.yuyang.pojo.MingancaozuoMapper;
import cn.com.yuyang.service.CaoZuoJiLuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Jxhun
 * Date: 2019/03/30
 * Description:
 * Version: V1.0
 */
@Controller
@RequestMapping("/caozuo")
public class CaoZuoJiLuController {

    private final CaoZuoJiLuService caoZuoJiLuService;

    @Autowired
    public CaoZuoJiLuController(CaoZuoJiLuService caoZuoJiLuService) {
        this.caoZuoJiLuService = caoZuoJiLuService;
    }

    /**
     * 这个方法用来导出。
     *
     * @param caoZuoJiLuBean
     * @param response
     * @return
     */
    @RequestMapping(value = {"/daoChu"})
    @ResponseBody
    public String shouye(@RequestBody(required = false) CaoZuoJiLuBean caoZuoJiLuBean, HttpServletResponse response) {

        try {
            caoZuoJiLuService.daoChu(caoZuoJiLuBean, response);
        } catch (IOException e) {
//            e.printStackTrace();
            return "导出失败，请检查时间格式是否为“yyyy-MM-dd”";
        }
        return "导出成功";
    }

}
