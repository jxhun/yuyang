package cn.com.yuyang.controller;

import cn.com.yuyang.bean.GeRenKaoQinBean;
import cn.com.yuyang.service.KaoQinService;
import cn.com.yuyang.util.SessionKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
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
    public GeRenKaoQinController(KaoQinService kaoQinService) {
        this.kaoQinService = kaoQinService;
    }

    public KaoQinService getKaoQinService() {
        return kaoQinService;
    }

    /**
     * 考勤模块的全部、正常、早退、迟到、旷工查询功能（支持总查询，时间区间查询，指定月份查询）
     *
     * @param geRenKaoQinBean 接收前端传入的json
     * @param request         核实操作人员身份
     * @return 返回查询后的结果
     */
    @RequestMapping(value = "select", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> select(@RequestBody(required = false) GeRenKaoQinBean geRenKaoQinBean, HttpServletRequest request) {
        // 先创建map并将returnCode为-1放入，一旦之后的代码报错直接return
        Map<String, Object> map = new HashMap<>();
        map.put("returnCode", -1);
//        判断服务器session上存储的token与请求的token是否对应，避免恶意查看他人考勤
        if (geRenKaoQinBean != null && request.getSession().getAttribute(SessionKey.TOKEN).equals(geRenKaoQinBean.getToken())) {
            try {
                // 获取从service层获取的处理后的list
                List<Map<String, Object>> list = kaoQinService.selectUser(geRenKaoQinBean);
                Map<String, Integer> count = kaoQinService.selectCount(geRenKaoQinBean.getDangAnId());
                map.put("msg", "查询成功");
                map.put("data", list);
                map.put("chiDao", count.get("chidao"));
                map.put("zaoTui", count.get("zaotui"));
                map.put("queQin", count.get("kuanggong"));
                map.put("qingJia", count.get("qingjia"));
                // 覆盖之前的-1值
                map.put("returnCode", 200);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            map.put("msg", "登录超时");
        }
        return map;
    }

    /**
     * 考勤模块的病假查询功能（支持总查询，时间区间查询，指定月份查询）
     *
     * @param geRenKaoQinBean 接收前端传入的json
     * @param request         核实操作人员身份
     * @return 返回查询后的结果
     */
    @RequestMapping(value = "selectBingJia", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> selectBingJia(@RequestBody(required = false) GeRenKaoQinBean geRenKaoQinBean, HttpServletRequest request) {
        // 先创建map并将returnCode为-1放入，一旦之后的代码报错直接return
        Map<String, Object> map = new HashMap<>();
        map.put("returnCode", -1);
//        判断服务器session上存储的token与请求的token是否对应，避免恶意查看他人考勤
        if (geRenKaoQinBean != null && request.getSession().getAttribute(SessionKey.TOKEN).equals(geRenKaoQinBean.getToken())) {
            try {
                // 接收从service中获取的处理后的list
                List<Map<String, Object>> list = kaoQinService.selectBing(geRenKaoQinBean);
                map.put("msg", "查询成功");
                map.put("data", list);
                // 覆盖之前的-1值
                map.put("returnCode", 200);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            map.put("msg", "登录超时");
        }
        return map;
    }
}