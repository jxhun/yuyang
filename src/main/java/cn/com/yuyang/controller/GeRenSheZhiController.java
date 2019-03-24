package cn.com.yuyang.controller;

import cn.com.yuyang.bean.YuanGongBean;
import cn.com.yuyang.pojo.Denglu;
import cn.com.yuyang.pojo.Renyuandangan;
import cn.com.yuyang.service.GeRenSheZhiService;
import cn.com.yuyang.util.FileUpload;
import cn.com.yuyang.util.SessionKey;
import org.apache.commons.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
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
@Scope(value = "prototype")
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
        String token = (String) request.getSession().getAttribute(SessionKey.TOKEN);    // 得到token
        Map<String, Object> returnMap = new HashMap<>();
        // 如果token不为空,说明用户已经登录,并且前端的token必须和我session的token相同
//        if (yuanGongBean != null && token != null && token.equals(yuanGongBean.getToken())) {
//            yuanGongBean = new YuanGongBean();
//            yuanGongBean.setDangAnId(1);
        Renyuandangan renyuandangan = geRenSheZhiService.xinxiChaXun(yuanGongBean);
        returnMap.put("returncode", 200);
        returnMap.put("msg", "200,查询成功,注意为0的和为null的数据都是未查询的数据");
        returnMap.put("data", renyuandangan);
//        } else {
//            returnMap.put("returncode", -1);
//            returnMap.put("msg", "查询失败");
//        }
        return returnMap;
    }


    @RequestMapping(value = {"/updateXinXi"})
    @ResponseBody
    public Map<String, Object> updateXinXi(@RequestBody(required = false) YuanGongBean yuanGongBean, HttpServletRequest request) {
        String token = (String) request.getSession().getAttribute(SessionKey.TOKEN);    // 得到token
        System.out.println("备注" + yuanGongBean.getBeiZhu());
        yuanGongBean.setDangAnId(1);
        Map<String, Object> returnMap = new HashMap<>();
        geRenSheZhiService.updateXinXi(yuanGongBean);
        geRenSheZhiService.updateXinXi2(yuanGongBean);
        System.out.println("=======" + yuanGongBean.getShouJiHaoMa());
        //更新了数据之后再进行查询用工信息
        Renyuandangan renyuandangan = geRenSheZhiService.xinxiChaXun(yuanGongBean);
        returnMap.put("returncode", 200);
        returnMap.put("msg", "更新成功，");
        returnMap.put("data", renyuandangan);
        return returnMap;
    }


    @RequestMapping(value = {"/shangChuanTouXian"})
    @ResponseBody
    public Map<String, Object> shangChuanTouXian(@RequestBody(required = false) MultipartFile articleFile, HttpServletRequest request, Renyuandangan renyuandangan) {
        String token = (String) request.getSession().getAttribute(SessionKey.TOKEN);    // 得到token
        Map<String, Object> returnMap = new HashMap<>();
        FileUpload fileUpload = new FileUpload();
        String imgURl = fileUpload.executeImport(articleFile, request);
        System.out.println("99999" + imgURl);
        if (imgURl != null && !imgURl.trim().equals("")) {
            renyuandangan.setTouXiang(imgURl);
            geRenSheZhiService.updateTouXiang(renyuandangan);
        }
        returnMap.put("returncode", 200);
        returnMap.put("msg", "更新成功，");
        returnMap.put("data", imgURl);
        return returnMap;
    }


}
