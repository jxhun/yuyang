package cn.com.yuyang.controller;

import cn.com.yuyang.bean.JiHuoJinYongBean;
import cn.com.yuyang.bean.QuanXianChaXunBean;
import cn.com.yuyang.pojo.Denglu;
import cn.com.yuyang.pojo.Kaoqin;
import cn.com.yuyang.service.QuanXianService;
import cn.com.yuyang.util.SessionKey;
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
 * Date: 2019/03/19
 * Description:
 * Version: V1.0
 */
@Controller
@RequestMapping("/quanxian")
public class QuanXianController {
    private QuanXianService quanXianService;

    @Autowired
    public QuanXianController(QuanXianService quanXianService) {
        this.quanXianService = quanXianService;
    }

    public QuanXianService getQuanXianService() {
        return quanXianService;
    }

    /**
     * 首次进入以及指定字段查询
     *
     * @param quanXianChaXunBean bean中的档案id用于判断权限，其余的属性放入mybatis判断拼接查询
     * @param request            负责判断操作员是否本人
     * @return 返回returnCode
     */
    @RequestMapping(value = "select", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> select(@RequestBody(required = false) QuanXianChaXunBean quanXianChaXunBean, HttpServletRequest request) {
        // 先创建map并将returnCode为-1放入，一旦之后的代码报错直接return
        Map<String, Object> map = new HashMap<>();
        map.put("returnCode", -1);
        String token = (String) request.getSession().getAttribute(SessionKey.TOKEN); // 得到token
//        判断服务器session上存储的token与请求的token是否对应，避免恶意查看他人考勤
        if (quanXianChaXunBean != null && token != null && token.equals(quanXianChaXunBean.getToken())) {
            int quanxian = Integer.parseInt(String.valueOf(request.getSession().getAttribute(SessionKey.BUMENGUANLI)));
            // 确认此操作员是否有权限
            if (quanxian == 1) {
                try {
                    // 从service层获取处理后的json格式的list
                    List<Map<String, Object>> list = quanXianService.selectQuangXian(quanXianChaXunBean);
                    map.put("msg", "查询成功");
                    map.put("data", list);
                    // 覆盖之前的-1值
                    map.put("returnCode", 200);
                } catch (Exception e) {
                    e.printStackTrace();
                    map.put("msg", "后端报错");
                }
            } else {
                map.put("msg", "权限不足");
            }
        } else {
            map.put("msg", "不是本人");
        }
        return map;
    }

    /**
     * 批量处理激活与禁用的功能
     *
     * @param jiHuoJinYongBean 用于接收批量处理的id数组与指定的操作
     * @param request          用于判断是否为本人操作
     * @return 执行成功将返会value为200的json，反之为-1
     */
    @RequestMapping(value = "update", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, String> jiHuo(@RequestBody(required = false) JiHuoJinYongBean jiHuoJinYongBean, HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();
        map.put("returnCode", "-1");
        map.put("msg", "存在不符合要求的人");
        String token = (String) request.getSession().getAttribute(SessionKey.TOKEN); // 得到token
//        判断服务器session上存储的token与请求的token是否对应，避免恶意查看他人考勤
        if (jiHuoJinYongBean != null && token != null && token.equals(jiHuoJinYongBean.getToken())){
            int quanxian = Integer.parseInt(String.valueOf(request.getSession().getAttribute(SessionKey.BUMENGUANLI)));
            // 确认此操作员是否有权限
            if (quanxian == 1) {
                try {
                    // 如果不符合条件的对象集长度为0，说明全部符合条件，并进入判断执行批量更新
                    if (quanXianService.selectYaoQiu(jiHuoJinYongBean) == 0) {
                        // 执行批量更新
                        quanXianService.updateZhuangTai(jiHuoJinYongBean);
                        map.put("returnCode", "200");
                        map.put("msg", "修改状态执行成功");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    map.put("msg", "后端报错");
                }
            } else {
                map.put("msg", "权限不足");
            }
        } else{
            map.put("msg", "不是本人");
        }
        return map;
    }
}
