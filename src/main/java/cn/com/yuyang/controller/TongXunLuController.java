package cn.com.yuyang.controller;

import cn.com.yuyang.bean.IdBean;
import cn.com.yuyang.service.TongXunLuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
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
@Scope(value = "prototype")
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

    /**
     * 这个方法用来用户进入通讯录就查看所有的好友
     * @param idBean 里面存储有前端传入的登录人档案id,或者存储有前端传入的好友姓名或者手机号码
     * @param request request对象用来拿到session
     * @return
     */
    @RequestMapping(value = {"/chaXun"})
    @ResponseBody
    public Map<String, Object> chaXun(@RequestBody(required = false) IdBean idBean, HttpServletRequest request) {
//        String token = (String) request.getSession().getAttribute(SessionKey.TOKEN);    // 得到token
        Map<String, Object> returnMap = new HashMap<>();
        // 如果token不为空,说明用户已经登录,并且前端的token必须和我session的token相同
//        if (idBean != null && token != null && token.equals(idBean.getToken())) {
//        System.out.println(idBean.getDangAnId());
//        idBean = new IdBean();
//        idBean.setXingMing("先生");
        List<Map<String, Object>> list = tongXunLuService.chaXunTongXunLu(idBean);
        returnMap.put("returncode", 200);
        returnMap.put("msg", "200查询成功,注意id是好友的档案id，查看好友详情需要将这个id传给后台");
        returnMap.put("data", list);
//    } else { returnMap.put("returncode", -1);// 失败返回状态码-1
//        returnMap.put("msg", "-1,登录超时，token验证失败"); // 提示失败}

        return returnMap;
    }

    /**
     * 查看好友详情方法，前端需要传入好友的id
     *
     * @param idBean  id bean对象
     * @param request request对象用来拿到session
     * @return 成功返回结果map
     */
    @RequestMapping(value = {"/xiangQing"})
    @ResponseBody
    public Map<String, Object> xiangQing(@RequestBody(required = false) IdBean idBean, HttpServletRequest request) {
//        String token = (String) request.getSession().getAttribute(SessionKey.TOKEN);    // 得到token
        Map<String, Object> returnMap = new HashMap<>();
        // 如果token不为空,说明用户已经登录,并且前端的token必须和我session的token相同
//        if (idBean != null && token != null && token.equals(idBean.getToken())) {
        idBean = new IdBean();
        idBean.setDangAnId(1);
        System.out.println("---idBean-----" + idBean.getDangAnId());
        Map<String, Object> map = tongXunLuService.selectHaoYou(idBean);
        returnMap.put("returncode", 200);
        returnMap.put("msg", "200,查询成功");
        returnMap.put("data", map);
//    } else { returnMap.put("returncode", -1);// 失败返回状态码-1
//        returnMap.put("msg", "-1,登录超时，token验证失败"); // 提示失败}

        return returnMap;
    }
}
