package cn.com.yuyang.controller;

import cn.com.yuyang.bean.Bumenbean;
import cn.com.yuyang.bean.YuanGongBean;
import cn.com.yuyang.pojo.Bumen;
import cn.com.yuyang.pojo.Denglu;
import cn.com.yuyang.pojo.Zhiwubiao;
import cn.com.yuyang.service.YuanGongGuanLiService;
import cn.com.yuyang.util.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: huxiaoyi
 * Date: 2019/03/19
 * Description:
 * Version: V1.0
 */
@Controller
@RequestMapping("/renshi")
public class YuanGongGuanLiController {


    @Autowired
    private YuanGongGuanLiService yuanGongGuanLiService;

    @RequestMapping(value = {"/quanchaxun"}, method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> quanchaxun(@RequestBody(required = false) YuanGongBean yuanGongBean, HttpServletRequest request) {
        List<Denglu> list = yuanGongGuanLiService.quanCha();
        Map<String, Object> map = new HashMap<>();
        map.put("Returncode", 200);
        map.put("msg", "成功");
        map.put("data", list);
        return map;
    }


    @ResponseBody
    @RequestMapping(value = {"/tiaojiaochaxun"}, method = RequestMethod.POST)
    public Map<String, Object> tiaojianchaxun(@RequestBody(required = false) YuanGongBean yuanGongBean, HttpServletRequest request) {
        List<Denglu> list = yuanGongGuanLiService.tiaoJainCha(yuanGongBean);
        Map<String, Object> map = new HashMap<>();
        map.put("Returncode", 200);
        map.put("msg", "成功");
        map.put("data", list);
        return map;
    }

    @ResponseBody
    @RequestMapping(value = {"/mingxi"})
    public Map<String, Object> mingxi(@RequestBody(required = false) YuanGongBean yuanGongBean, HttpServletRequest request) {
        System.out.println("~~~~~~mingxi~~~~~~~~~~~");
        List<Denglu> list = yuanGongGuanLiService.tiaoJainCha(yuanGongBean);
        Map<String, Object> map = new HashMap<>();
        map.put("Returncode", 200);
        map.put("msg", "成功");
        map.put("data", list);
//        map.put("token",yuanGongBean.getToken());
        return map;
    }

    @ResponseBody
    @RequestMapping(value = {"/update"}, method = RequestMethod.POST)
    public Map<String, Object> update(YuanGongBean yuanGongBean, HttpServletRequest request) {
        String msg = yuanGongGuanLiService.bianji(yuanGongBean);
        Map<String, Object> map = new HashMap<>();
        map.put("Returncode", 200);
        map.put("msg", msg);
        map.put("data", "");
        return map;
    }

    @ResponseBody
    @RequestMapping(value = {"/delete"}, method = RequestMethod.POST)
    public Map<String, Object> delete(@RequestBody(required = false) YuanGongBean yuanGongBean, HttpServletRequest request) {
        String msg = yuanGongGuanLiService.shanchu(yuanGongBean);
        Map<String, Object> map = new HashMap<>();
        map.put("Returncode", 200);
        map.put("msg", msg);
        map.put("data", "");
        return map;
    }

    @ResponseBody
    @RequestMapping(value = {"/xinzeng"}, method = RequestMethod.POST)
    public Map<String, Object> xinzeng(@RequestBody(required = false)MultipartFile articleFile, YuanGongBean yuanGongBean, HttpServletRequest request) {
        String touXiang = FileUpload.executeImport(articleFile, request);
        yuanGongBean.setTouXiang(touXiang);
        System.out.println("头像地址" + touXiang);
        String msg = yuanGongGuanLiService.xinzeng(yuanGongBean, request);
        Map<String, Object> map = new HashMap<>();
        map.put("Returncode", 200);
        map.put("msg", msg);
        map.put("data", "");
        return map;
    }

    @ResponseBody
    @RequestMapping(value = {"/xuandan"}, method = RequestMethod.POST)
    public Map<String, Object> xuandan(HttpServletRequest request) {
        System.out.println("xuandan~~~~~~");
        List<Bumen> bumen = yuanGongGuanLiService.bumenxuandan();
        List<Zhiwubiao> zhiwu = yuanGongGuanLiService.zhiwuxuandan();
        Map<String, Object> map = new HashMap<>();
        System.out.println(bumen);
        System.out.println(zhiwu);
        map.put("Returncode", 200);
        map.put("msg", "成功");
        map.put("data", bumen);
        map.put("data2", zhiwu);
        return map;
    }
}
