package cn.com.yuyang.controller;

import cn.com.yuyang.bean.Bumenbean;
import cn.com.yuyang.pojo.Bumen;
import cn.com.yuyang.service.BuMenGuanLiService;
import cn.com.yuyang.util.SessionKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
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

    private final BuMenGuanLiService buMenGuanLiService;

    @Autowired
    public BuMenGuanLiController(BuMenGuanLiService buMenGuanLiService) {
        this.buMenGuanLiService = buMenGuanLiService;
    }

    /**
     * 查看所有部门的信息
     *
     * @param bumen
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"/shouye"})
    public Map<String, Object> shouye(@RequestBody(required = false) Bumenbean bumen, HttpServletRequest request) {
        String token = (String) request.getSession().getAttribute(SessionKey.TOKEN);
        Integer quanxian = (Integer) request.getSession().getAttribute(SessionKey.QUANXIANGUANLI);
        Map<String, Object> map = new HashMap<>();
        if (!bumen.getToken().equals(token)) {
            map.put("Returncode", -1);
            map.put("msg", "登录超时！");
        } else if (quanxian != 1) {
            map.put("Returncode", -1);
            map.put("msg", "你没有这个权限！");
        } else {
            List<Bumenbean> list = buMenGuanLiService.shouye();
            map.put("Returncode", 200);
            map.put("msg", "成功！");
            map.put("data", list);
        }
        return map;
    }

    /**
     * 条件查看所有部门的信息
     *
     * @param bumen
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"/chaxun"}, method = RequestMethod.POST)
    public Map<String, Object> chaxun(@RequestBody Bumenbean bumen, HttpServletRequest request) {
        String token = (String) request.getSession().getAttribute(SessionKey.TOKEN);
        Integer quanxian = (Integer) request.getSession().getAttribute(SessionKey.QUANXIANGUANLI);
        Map<String, Object> map = new HashMap<>();
        if (!bumen.getToken().equals(token)) {
            map.put("Returncode", -1);
            map.put("msg", "登录超时！");
        } else if (quanxian != 1) {
            map.put("Returncode", -1);
            map.put("msg", "你没有这个权限！");
        } else {
            List<Bumenbean> list = buMenGuanLiService.chaxun(bumen);
            map.put("Returncode", 200);
            map.put("msg", "成功");
            map.put("data", list);
        }
        return map;
    }

    /**
     * 新增部门
     *
     * @param bumen
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"/xinzeng"}, method = RequestMethod.POST)
    public Map<String, Object> xinzeng(@RequestBody Bumenbean bumen, HttpServletRequest request) {
        String token = (String) request.getSession().getAttribute(SessionKey.TOKEN);
        Integer quanxian = (Integer) request.getSession().getAttribute(SessionKey.QUANXIANGUANLI);
        Map<String, Object> map = new HashMap<>();
        if (!bumen.getToken().equals(token)) {
            map.put("Returncode", -1);
            map.put("msg", "登录超时！");
        } else if (quanxian != 1) {
            map.put("Returncode", -1);
            map.put("msg", "你没有这个权限！");
        } else {
            String msg = buMenGuanLiService.xinzeng(bumen);
            buMenGuanLiService.minGanXinZeng(request); // 调用方法，存入用户操作记录
            map.put("Returncode", 200);
            map.put("msg", msg);
        }
        return map;
    }

    /**
     * 编辑部门的信息
     *
     * @param bumen
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"/bianji"}, method = RequestMethod.POST)
    public Map<String, Object> bianji(@RequestBody Bumenbean bumen, HttpServletRequest request) {
        String token = (String) request.getSession().getAttribute(SessionKey.TOKEN);
        Integer quanxian = (Integer) request.getSession().getAttribute(SessionKey.QUANXIANGUANLI);
        Map<String, Object> map = new HashMap<>();
        if (!bumen.getToken().equals(token)) {
            map.put("Returncode", -1);
            map.put("msg", "登录超时！");
        } else if (quanxian != 1) {
            map.put("Returncode", -1);
            map.put("msg", "你没有这个权限！");
        } else {
            String msg = buMenGuanLiService.bianji(bumen);
            buMenGuanLiService.minGanXinZeng(request); // 调用方法，存入用户操作记录
            map.put("Returncode", 200);
            map.put("msg", msg);
        }
        return map;
    }

    /**
     * 删除部门
     *
     * @param bumen
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"/shanchu"}, method = RequestMethod.POST)
    public Map<String, Object> shanchu(@RequestBody Bumenbean bumen, HttpServletRequest request) {
        String token = (String) request.getSession().getAttribute(SessionKey.TOKEN);
        Integer quanxian = (Integer) request.getSession().getAttribute(SessionKey.QUANXIANGUANLI);
        Map<String, Object> map = new HashMap<>();
        if (!bumen.getToken().equals(token)) {
            map.put("Returncode", -1);
            map.put("msg", "登录超时！");
        } else if (quanxian != 1) {
            map.put("Returncode", -1);
            map.put("msg", "你没有这个权限！");
        } else {
            String msg = buMenGuanLiService.shanchu(bumen);
            buMenGuanLiService.minGanXinZeng(request); // 调用方法，存入用户操作记录
            map.put("Returncode", 200);
            map.put("msg", msg);
        }
        return map;
    }

}
