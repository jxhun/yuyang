package cn.com.yuyang.controller;

import cn.com.yuyang.bean.BirthdayBean;
import cn.com.yuyang.bean.Bumenbean;
import cn.com.yuyang.bean.YuanGongBean;
import cn.com.yuyang.pojo.Bumen;
import cn.com.yuyang.pojo.Denglu;
import cn.com.yuyang.pojo.Zhiwubiao;
import cn.com.yuyang.service.YuanGongGuanLiService;
import cn.com.yuyang.util.AsymmetricEncryption;
import cn.com.yuyang.util.FileUpload;
import cn.com.yuyang.util.MyJavaMail;
import cn.com.yuyang.util.SessionKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
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


    /**
     * 查询所有员工的员工档案
     *
     * @param yuanGongBean
     * @param request
     * @return
     */
    @RequestMapping(value = {"/quanchaxun"}, method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> quanchaxun(@RequestBody(required = false) YuanGongBean yuanGongBean, HttpServletRequest request) {
        String token = (String) request.getSession().getAttribute(SessionKey.TOKEN);
        Map<String, Object> map = new HashMap<>();
        if (yuanGongBean.getToken() == null || !yuanGongBean.getToken().equals(token)) {
            map.put("returnCode", 408);
            map.put("msg", "登录超时！");
        } else if (Integer.parseInt(String.valueOf(request.getSession().getAttribute(SessionKey.CHAKANYUANGONG))) != 1) {
            map.put("returnCode", -2);
            map.put("msg", "你没有这个权限！");
        } else {
            List<Denglu> list = yuanGongGuanLiService.quanCha();
            map.put("returnCode", 200);
            map.put("msg", "成功");
            map.put("data", list);
        }
        return map;
    }

    /**
     * 条件查询员工的员工档案
     *
     * @param yuanGongBean
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"/tiaojianchaxun"}, method = RequestMethod.POST)
    public Map<String, Object> tiaojianchaxun(@RequestBody(required = false) YuanGongBean yuanGongBean, HttpServletRequest request) {
        String token = (String) request.getSession().getAttribute(SessionKey.TOKEN);
        Integer quanxian = (Integer) request.getSession().getAttribute(SessionKey.CHAKANYUANGONG);
        Map<String, Object> map = new HashMap<>();
        if (!yuanGongBean.getToken().equals(token)) {
            map.put("returnCode", 408);
            map.put("msg", "登录超时！");
        } else if (quanxian != 1) {
            map.put("returnCode", -2);
            map.put("msg", "你没有这个权限！");
        } else {
            List<Denglu> list = yuanGongGuanLiService.tiaoJainCha(yuanGongBean);
            map.put("returnCode", 200);
            map.put("msg", "成功");
            map.put("data", list);
        }
        return map;
    }

    /**
     * 查询指定的员工所有信息
     *
     * @param yuanGongBean
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"/mingxi"})
    public Map<String, Object> mingxi(@RequestBody(required = false) YuanGongBean yuanGongBean, HttpServletRequest request) {
        String token = (String) request.getSession().getAttribute(SessionKey.TOKEN);
        Integer quanxian = (Integer) request.getSession().getAttribute(SessionKey.CHAKANYUANGONG);
        Map<String, Object> map = new HashMap<>();
        if (!yuanGongBean.getToken().equals(token)) {
            map.put("returnCode", 408);
            map.put("msg", "登录超时！");
        } else if (quanxian != 1) {
            map.put("returnCode", -2);
            map.put("msg", "你没有这个权限！");
        } else {
            List<Denglu> list = yuanGongGuanLiService.tiaoJainCha(yuanGongBean);
            map.put("returnCode", 200);
            map.put("msg", "成功");
            map.put("data", list);
        }
        return map;
    }

    /**
     * 修改员工的信息
     *
     * @param yuanGongBean
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"/update"}, method = RequestMethod.POST)
    public Map<String, Object> update(@RequestBody YuanGongBean yuanGongBean, HttpServletRequest request) {
        String token = (String) request.getSession().getAttribute(SessionKey.TOKEN);
        Integer quanxian = (Integer) request.getSession().getAttribute(SessionKey.CAOZUOYUANGONG);
        Map<String, Object> map = new HashMap<>();
        if (yuanGongBean.getToken() == null || !yuanGongBean.getToken().equals(token)) {
            map.put("returnCode", 408);
            map.put("msg", "登录超时！");
        } else if (quanxian != 1) {
            map.put("returnCode", -2);
            map.put("msg", "你没有这个权限！");
        } else {
            map = yuanGongGuanLiService.bianji(yuanGongBean, request);
            yuanGongGuanLiService.minGanXinZeng(request); // 调用方法，存入用户操作记录
        }
        return map;
    }

    /**
     * 删除员工的员工档案（假删除）
     *
     * @param yuanGongBean
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"/delete"}, method = RequestMethod.POST)
    public Map<String, Object> delete(@RequestBody(required = false) YuanGongBean yuanGongBean, HttpServletRequest request) {
        String token = (String) request.getSession().getAttribute(SessionKey.TOKEN);
        Integer quanxian = (Integer) request.getSession().getAttribute(SessionKey.CAOZUOYUANGONG);
        Map<String, Object> map = new HashMap<>();
        if (!yuanGongBean.getToken().equals(token)) {
            map.put("returnCode", 408);
            map.put("msg", "登录超时！");
        } else if (quanxian != 1) {
            map.put("returnCode", -2);
            map.put("msg", "你没有这个权限！");
        } else {
            map = yuanGongGuanLiService.shanchu(yuanGongBean, request);
            yuanGongGuanLiService.minGanXinZeng(request); // 调用方法，存入用户操作记录
        }
        return map;
    }

    /**
     * 新增员工
     *
     * @param yuanGongBean
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"/xinzeng"})
    public Map<String, Object> xinzeng(@RequestBody YuanGongBean yuanGongBean, HttpServletRequest request) {
        String token = (String) request.getSession().getAttribute(SessionKey.TOKEN);
        Integer quanxian = (Integer) request.getSession().getAttribute(SessionKey.CAOZUOYUANGONG);
        Map<String, Object> map = new HashMap<>();
        if (!yuanGongBean.getToken().equals(token)) {
            map.put("returnCode", 408);
            map.put("msg", "登录超时！");
        } else if (quanxian != 1) {
            map.put("returnCode", -2);
            map.put("msg", "你没有这个权限！");
        } else {
            map = yuanGongGuanLiService.xinzeng(yuanGongBean, request);
            yuanGongGuanLiService.minGanXinZeng(request); // 调用方法，存入用户操作记录
        }
        return map;
    }

    /**
     * 查询所有部门以及所有职务的名称和id
     *
     * @param
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"/xuandan"}, method = RequestMethod.POST)
    public Map<String, Object> xuandan(YuanGongBean yuanGongBean, HttpServletRequest request) {
        String token = (String) request.getSession().getAttribute(SessionKey.TOKEN);
        Map<String, Object> map = new HashMap<>();
//        if (!yuanGongBean.getToken().equals(token)) {
//            map.put("Returncode", -1);
//            map.put("msg", "登录超时！");
//        } else {
        List<Bumen> bumen = yuanGongGuanLiService.bumenxuandan();
        List<Zhiwubiao> zhiwu = yuanGongGuanLiService.zhiwuxuandan();
        List<Zhiwubiao> zhiwuquan = yuanGongGuanLiService.zhiwuxuandanQuan();
        map.put("returnCode", 200);
        map.put("msg", "成功,新增调用data2，修改调用data3");
        map.put("data", bumen);
        map.put("data2", zhiwu);
        map.put("data3", zhiwuquan);
//        }
        return map;
    }


    @ResponseBody
    @RequestMapping(value = {"/ChongZhi"}, method = RequestMethod.POST)
    public Map<String, Object> updateChongZhiMiMa(@RequestBody(required = false) YuanGongBean yuanGongBean, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        String token = (String) request.getSession().getAttribute(SessionKey.TOKEN);
        if (yuanGongBean.getToken().equals(token)) {
            map.put("returnCode", -1);
            try {
                yuanGongBean.setMiMa("123456");
                AsymmetricEncryption asymmetricEncryption = new AsymmetricEncryption();
                byte[] bytes = asymmetricEncryption.jiaMi("123456", request);
                yuanGongBean.setMiMa2(bytes);
                yuanGongBean.setSiYao((String) request.getSession().getAttribute(SessionKey.SIYAO));
                yuanGongGuanLiService.updateChongZhiMiMa(yuanGongBean);
                map.put("returnCode", 200);
                map.put("msg", "重置密码成功");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            map.put("returnCode", 408);
            map.put("msg", "登录超时！");
        }

        return map;
    }


    @ResponseBody
    @RequestMapping(value = {"/shangchuan"}, method = RequestMethod.POST)
    public Map<String, Object> shangchuan(@RequestBody MultipartFile articleFile, HttpServletRequest request) {
        FileUpload fileUpload = new FileUpload();
        String imgURl = fileUpload.executeImport(articleFile, request);
        request.getSession().setAttribute(SessionKey.FJDZ, imgURl);// 存入地址
        Map<String, Object> map = new HashMap<>();
        map.put("returnCode", 200);
        return map;
    }


    @Scheduled(cron = "0 0 10 * * ?")
    public void run() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd");

        //今天的年月
        String day = "%" + dateFormat.format(new Date()) + "%";
        List<BirthdayBean> lists = yuanGongGuanLiService.selectShengRi(day);
        if (lists != null) {
            for (BirthdayBean birthdayBean : lists) {
                new Thread(new MyJavaMail(birthdayBean.getYouXiang(), birthdayBean.getXingMing())).start();
            }

        }

    }

}
