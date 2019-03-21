package cn.com.yuyang.controller;

import cn.com.yuyang.bean.IdBean;
import cn.com.yuyang.bean.YuanGongBean;
import cn.com.yuyang.pojo.RenyuandanganMapper;
import cn.com.yuyang.service.GeRenSheZhiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Jxhun
 * Date: 2019/03/21
 * Description: 个人设置
 * Version: V1.0
 */
@Controller
@RequestMapping("/geren")
public class GeRenSheZhiController {

    @Autowired
    private final GeRenSheZhiService geRenSheZhiService; // 个人设置service

    public GeRenSheZhiController(GeRenSheZhiService geRenSheZhiService) {
        this.geRenSheZhiService = geRenSheZhiService;
        System.out.println("------GeRenSheZhiController()构造函数------");
    }

    @RequestMapping(value = {"/xinxi"})
    @ResponseBody
    public Map<String, Object> xinxi(@RequestBody(required = false) YuanGongBean yuanGongBean, HttpServletRequest request) {
//        String token = (String) request.getSession().getAttribute(SessionKey.TOKEN);    // 得到token
        Map<String, Object> returnMap = new HashMap<>();
        // 如果token不为空,说明用户已经登录,并且前端的token必须和我session的token相同
//        if (idBean != null && token != null && token.equals(idBean.getToken())) {
        return returnMap;
    }
}
