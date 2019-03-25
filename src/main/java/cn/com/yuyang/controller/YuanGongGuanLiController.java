package cn.com.yuyang.controller;

import cn.com.yuyang.bean.Bumenbean;
import cn.com.yuyang.bean.YuanGongBean;
import cn.com.yuyang.pojo.Bumen;
import cn.com.yuyang.pojo.Denglu;
import cn.com.yuyang.pojo.Zhiwubiao;
import cn.com.yuyang.service.YuanGongGuanLiService;
import cn.com.yuyang.util.FileUpload;
import cn.com.yuyang.util.SessionKey;
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


    /** 查询所有员工的员工档案
     *
     * @param yuanGongBean
     * @param request
     * @return
     */
    @RequestMapping(value = {"/quanchaxun"}, method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> quanchaxun(@RequestBody(required = false) YuanGongBean yuanGongBean, HttpServletRequest request) {
        String token = (String) request.getSession().getAttribute(SessionKey.TOKEN);
        Integer quanxian = (Integer) request.getSession().getAttribute(SessionKey.CHAKANYUANGONG);
        Map<String,Object> map = new HashMap<>();
        if(!yuanGongBean.getToken().equals(token)){
            map.put("Returncode",-1);
            map.put("msg","登录超时！");
        }
        else if(quanxian!=1){
            map.put("Returncode",-1);
            map.put("msg","你没有这个权限！");
        }
        else {
            List<Denglu> list = yuanGongGuanLiService.quanCha();
            map.put("Returncode", 200);
            map.put("msg", "成功");
            map.put("data", list);
        }
        return map;
    }

    /** 条件查询员工的员工档案
     *
     * @param yuanGongBean
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"/tiaojiaochaxun"}, method = RequestMethod.POST)
    public Map<String, Object> tiaojianchaxun(@RequestBody(required = false) YuanGongBean yuanGongBean, HttpServletRequest request) {
        String token = (String) request.getSession().getAttribute(SessionKey.TOKEN);
        Integer quanxian = (Integer) request.getSession().getAttribute(SessionKey.CHAKANYUANGONG);
        Map<String,Object> map = new HashMap<>();
        if(!yuanGongBean.getToken().equals(token)){
            map.put("Returncode",-1);
            map.put("msg","登录超时！");
        }
        else if(quanxian!=1){
            map.put("Returncode",-1);
            map.put("msg","你没有这个权限！");
        }
        else {
            List<Denglu> list = yuanGongGuanLiService.tiaoJainCha(yuanGongBean);
            map.put("Returncode", 200);
            map.put("msg", "成功");
            map.put("data", list);
        }
        return map;
    }
    /** 查询指定的员工所有信息
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
        Map<String,Object> map = new HashMap<>();
        if(!yuanGongBean.getToken().equals(token)){
            map.put("Returncode",-1);
            map.put("msg","登录超时！");
        }
        else if(quanxian!=1){
            map.put("Returncode",-1);
            map.put("msg","你没有这个权限！");
        }
        else {
            List<Denglu> list = yuanGongGuanLiService.tiaoJainCha(yuanGongBean);
            map.put("Returncode", 200);
            map.put("msg", "成功");
            map.put("data", list);
        }
        return map;
    }
    /** 修改员工的信息
     *
     * @param yuanGongBean
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"/update"}, method = RequestMethod.POST)
    public Map<String, Object> update(YuanGongBean yuanGongBean, HttpServletRequest request) {
        String token = (String) request.getSession().getAttribute(SessionKey.TOKEN);
        Integer quanxian = (Integer) request.getSession().getAttribute(SessionKey.CAOZUOYUANGONG);
        Map<String,Object> map = new HashMap<>();
        if(!yuanGongBean.getToken().equals(token)){
            map.put("Returncode",-1);
            map.put("msg","登录超时！");
        }
        else if(quanxian!=1){
            map.put("Returncode",-1);
            map.put("msg","你没有这个权限！");
        }
        else {
            String msg = yuanGongGuanLiService.bianji(yuanGongBean);
            map.put("Returncode", 200);
            map.put("msg", msg);
        }
        return map;
    }

    /** 删除员工的员工档案（假删除）
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
        Map<String,Object> map = new HashMap<>();
        if(!yuanGongBean.getToken().equals(token)){
            map.put("Returncode",-1);
            map.put("msg","登录超时！");
        }
        else if(quanxian!=1){
            map.put("Returncode",-1);
            map.put("msg","你没有这个权限！");
        }
        else {
            String msg = yuanGongGuanLiService.shanchu(yuanGongBean);
            map.put("Returncode", 200);
            map.put("msg", msg);
        }
        return map;
    }

    /** 新增员工
     *
     * @param yuanGongBean
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"/xinzeng"}, method = RequestMethod.POST)
    public Map<String, Object> xinzeng(@RequestBody(required = false)MultipartFile articleFile, YuanGongBean yuanGongBean, HttpServletRequest request) {
        String token = (String) request.getSession().getAttribute(SessionKey.TOKEN);
        Integer quanxian = (Integer) request.getSession().getAttribute(SessionKey.CAOZUOYUANGONG);
        Map<String,Object> map = new HashMap<>();
        if(!yuanGongBean.getToken().equals(token)){
            map.put("Returncode",-1);
            map.put("msg","登录超时！");
        }
        else if(quanxian!=1){
            map.put("Returncode",-1);
            map.put("msg","你没有这个权限！");
        }
        else {
            String touXiang = FileUpload.executeImport(articleFile, request);
            yuanGongBean.setTouXiang(touXiang);
            System.out.println("头像地址" + touXiang);
            String msg = yuanGongGuanLiService.xinzeng(yuanGongBean, request);
            map.put("Returncode", 200);
            map.put("msg", msg);
        }
        return map;
    }

    /** 查询所有部门以及所有职务的名称和id
     *
     * @param
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"/xuandan"}, method = RequestMethod.POST)
    public Map<String, Object> xuandan(YuanGongBean yuanGongBean,HttpServletRequest request) {
        String token = (String) request.getSession().getAttribute(SessionKey.TOKEN);
        Map<String,Object> map = new HashMap<>();
        if(!yuanGongBean.getToken().equals(token)){
            map.put("Returncode",-1);
            map.put("msg","登录超时！");
        }
        else {
            List<Bumen> bumen = yuanGongGuanLiService.bumenxuandan();
            List<Zhiwubiao> zhiwu = yuanGongGuanLiService.zhiwuxuandan();
            System.out.println(bumen);
            System.out.println(zhiwu);
            map.put("Returncode", 200);
            map.put("msg", "成功");
            map.put("data", bumen);
            map.put("data2", zhiwu);
        }
        return map;
    }
}
