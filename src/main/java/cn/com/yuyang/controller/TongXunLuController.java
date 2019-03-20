package cn.com.yuyang.controller;

import cn.com.yuyang.bean.GongGaoBean;
import cn.com.yuyang.bean.IdBean;
import cn.com.yuyang.pojo.Haoyoubiao;
import cn.com.yuyang.service.TongXunLuService;
import cn.com.yuyang.util.SessionKey;
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
 * Date: 2019/03/20
 * Description: 通讯录
 * Version: V1.0
 */
@Controller
@RequestMapping("/TongXunLu")
public class TongXunLuController {

    @Autowired
    private final TongXunLuService tongXunLuService;

    /**
     * 通讯录service注入Controller
     *
     * @param tongXunLuService 通讯录service对象
     */
    public TongXunLuController(TongXunLuService tongXunLuService) {
        this.tongXunLuService = tongXunLuService;
        System.out.println("------TongXunLuController-------");
    }

    @RequestMapping(value = {"/chaXun"})
    @ResponseBody
    public Map<String, Object> chaXun(@RequestBody(required = false) IdBean idBean, HttpServletRequest request) {
//        String token = (String) request.getSession().getAttribute(SessionKey.TOKEN);    // 得到token
        Map<String, Object> returnMap = new HashMap<>();
        // 如果token不为空,说明用户已经登录,并且前端的token必须和我session的token相同
//        if (token != null && token.equals(idBean.getToken())) {
//        System.out.println(idBean.getDangAnId());
        List<Map<String, Object>> list = tongXunLuService.chaXunTongXunLu(1);
        returnMap.put("returncode", 200);
        returnMap.put("msg", "查询成功");
        returnMap.put("data", list);
//        }

        return returnMap;
    }
}
