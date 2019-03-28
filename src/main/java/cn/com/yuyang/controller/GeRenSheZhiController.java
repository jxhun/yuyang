package cn.com.yuyang.controller;

import cn.com.yuyang.bean.YuanGongBean;
import cn.com.yuyang.pojo.Denglu;
import cn.com.yuyang.pojo.Renyuandangan;
import cn.com.yuyang.service.GeRenSheZhiService;
import cn.com.yuyang.util.AsymmetricEncryption;
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

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
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
    }

    @RequestMapping(value = {"/xinxi"})
    @ResponseBody
    public Map<String, Object> xinxi(@RequestBody(required = false) YuanGongBean yuanGongBean, HttpServletRequest request) {
        String token = (String) request.getSession().getAttribute(SessionKey.TOKEN);    // 得到token
        // 如果token不为空,说明用户已经登录,并且前端的token必须和我session的token相同
//        if (yuanGongBean != null && token != null && token.equals(yuanGongBean.getToken())) {
//            yuanGongBean = new YuanGongBean();
//            yuanGongBean.setDangAnId(1);
        Map<String, Object> returnMap = new HashMap<>();
        Renyuandangan renyuandangan = geRenSheZhiService.xinxiChaXun(yuanGongBean);
        returnMap.put("returncode", 200);
        returnMap.put("msg", "200,查询成功,注意为0的和为null的数据都是未查询的数据");
        returnMap.put("data", renyuandangan);
//        returnMap.put("token", token);  // 传出token
//        } else {
//            returnMap.put("returncode", -1);
//            returnMap.put("msg", "登录超时，请从新登录");
//        }
        return returnMap;
    }


    @RequestMapping(value = {"/updateXinXi"})
    @ResponseBody
    public Map<String, Object> updateXinXi(@RequestBody(required = false) YuanGongBean yuanGongBean, HttpServletRequest request) {
        String token = (String) request.getSession().getAttribute(SessionKey.TOKEN);    // 得到token
        Map<String, Object> returnMap = new HashMap<>();
        if(yuanGongBean!=null && token!=null && token.trim().equals(yuanGongBean.getToken())){
            geRenSheZhiService.updateXinXi(yuanGongBean);
            //代表旧密码不为空，则判断旧密码是否输入正确
            if(yuanGongBean.getShouJiHaoMa()!=null && !yuanGongBean.getShouJiHaoMa().trim().equals("")){
                geRenSheZhiService.updateXinXi2(yuanGongBean,request);

            }else {
                if(yuanGongBean.getJiuMiMa()!=null && !yuanGongBean.getJiuMiMa().trim().equals("")){
                    //此方法代表根据前端传过来的旧密码查询后台如果大于0则代表旧密码输入成功
                    String s = geRenSheZhiService.selectMiMa2(yuanGongBean);
                    if(s.equals(yuanGongBean.getJiuMiMa())){
                        //如果旧密码输入成功则更新密码
                        geRenSheZhiService.updateXinXi2(yuanGongBean,request);
                        //更新了数据之后再进行查询用工信息
//                    Renyuandangan renyuandangan = geRenSheZhiService.xinxiChaXun(yuanGongBean);
//                    returnMap.put("returncode", 200);
//                    returnMap.put("msg", "更新成功，");
//                    returnMap.put("data", renyuandangan);

                    }else{
                        returnMap.put("returncode", -1);
                        returnMap.put("msg", "旧密码输入错误");
                    }

                }

            }
            Renyuandangan renyuandangan = geRenSheZhiService.xinxiChaXun(yuanGongBean);
            returnMap.put("returncode", 200);
            returnMap.put("msg", "更新成功，");
            returnMap.put("data", renyuandangan);

        }else{
            returnMap.put("returncode", -1);
            returnMap.put("msg", "登录超时，");
        }
        return  returnMap;
    }


    @RequestMapping(value = {"/shangChuanTouXian"})
    @ResponseBody
    public Map<String, Object> shangChuanTouXian(@RequestBody(required = false) MultipartFile articleFile, HttpServletRequest request, YuanGongBean yuanGongBean) {
        String token = (String) request.getSession().getAttribute(SessionKey.TOKEN);    // 得到token
        Map<String, Object> returnMap = new HashMap<>();
        // 如果token不为空,说明用户已经登录,并且前端的token必须和我session的token相同
        if(  token!=null && token.trim().equals(yuanGongBean.getToken())) {
            FileUpload fileUpload = new FileUpload();
            String imgURl = fileUpload.executeImport(articleFile, request);
            if (imgURl != null && !imgURl.trim().equals("")) {
                yuanGongBean.setTouXiang(imgURl);
                geRenSheZhiService.updateTouXiang(yuanGongBean);
                returnMap.put("returncode", 200);
                returnMap.put("msg", "更新成功55555");
                returnMap.put("data", imgURl);
            } else {
                returnMap.put("returncode", -100);
                returnMap.put("msg", "文件过大，请传递小余10M的文件");
            }
            returnMap.put("token", token);  // 传出token
        }else{
            returnMap.put("returncode", -1);
            returnMap.put("msg", "登录超时，");
        }


//        } else {
//            returnMap.put("returncode", -1);
//            returnMap.put("msg", "登录超时，请从新登录");
//        }

        return returnMap;
    }


}
