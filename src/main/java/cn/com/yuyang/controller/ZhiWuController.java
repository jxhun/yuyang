package cn.com.yuyang.controller;

import cn.com.yuyang.bean.ZhiWuBean;
import cn.com.yuyang.pojo.Zhiwubiao;
import cn.com.yuyang.service.ZhiWuService;
import cn.com.yuyang.util.SessionKey;
import com.mysql.jdbc.SQLError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Wong
 * Date: 2019/03/20
 * Description:
 * Version: V1.0
 */
@Controller
@RequestMapping("/zhiwu")
public class ZhiWuController {
    private ZhiWuService zhiWuService;
    @Autowired
    public ZhiWuController(ZhiWuService zhiWuService){
        this.zhiWuService = zhiWuService;
    }
    public ZhiWuService getZhiWuService() {
        return zhiWuService;
    }

    /**
     * 职务模块的查询功能
     * @param zhiWuBean 获取前端传入的dangAnId与查询职务名称
     * @param request 核实操作人员身份
     * @return 返回查询后的结果
     */
    @RequestMapping(value = "select",method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public Map<String,Object> select(@RequestBody(required = false) ZhiWuBean zhiWuBean, HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        map.put("returnCode","-1");
//        // 判断服务器session上存储的操作员档案id与访问权限功能的id是否对应，避免恶意查看访问权限功能
//        if (request.getSession().getAttribute(SessionKey.DANGANID).equals(zhiWuBean.getDangAnId())){
//            // 确认此操作员是否有权限
//            if ((Integer) request.getSession().getAttribute(SessionKey.QUANXIANGUANLI) == 1){
                try {
                    // 调用service层的查询方法
                    List<Zhiwubiao> list = zhiWuService.selectZhiWu(zhiWuBean);
                    map.put("data",list);
                    map.put("returnCode",200);
                    map.put("msg","执行成功");
                }catch (Exception e){
                    e.printStackTrace();
                }
        return map;
    }

    /**
     * 职务模块的删除功能
     * @param zhiWuBean bean中存入选择的职务id与档案id
     * @param request 检验是否为本人操作
     * @return 删除执行的状态
     */
    @RequestMapping(value = "delete",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public Map<String,String> delete(@RequestBody(required = false) ZhiWuBean zhiWuBean,HttpServletRequest request){
        Map<String,String> map = new HashMap<>();
        map.put("returnCode","-1");
        map.put("msg","该职务有员工存在");
//        // 判断服务器session上存储的操作员档案id与访问权限功能的id是否对应，避免恶意查看访问权限功能
//        if (request.getSession().getAttribute(SessionKey.DANGANID).equals(zhiWuBean.getDangAnId())){
//            // 确认此操作员是否有权限
//            if ((Integer) request.getSession().getAttribute(SessionKey.QUANXIANGUANLI) == 1){
                // 将职务id放入方法中查询此职务下面是否还有员工绑定
                try{
                    if(zhiWuService.selectZhiWuRen(zhiWuBean.getZhiWuId()) == 0){
                        // 此职务符合删除条件执行删除功能
                        zhiWuService.deleteZhiWu(zhiWuBean.getZhiWuId());
                        map.put("returnCode","200");
                        map.put("msg","删除成功");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
        return map;
    }

    /**
     * 职务模块的新增与更新
     * @param zhiwubiao 将两种功能的内容自动封装入POJO
     * @param method 将路径后缀的method值封装入形参用于判断申请的功能
     * @return 执行的状态
     */
    @RequestMapping(value = "change/{method}",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> change(@RequestBody(required = false) Zhiwubiao zhiwubiao,@PathVariable("method") String method){
        Map<String,String> map = new HashMap<>();
        map.put("returnCode","-1");
//        // 判断服务器session上存储的操作员档案id与访问权限功能的id是否对应，避免恶意查看访问权限功能
//        if (request.getSession().getAttribute(SessionKey.DANGANID).equals(zhiWuBean.getDangAnId())){
//            // 确认此操作员是否有权限
//            if ((Integer) request.getSession().getAttribute(SessionKey.QUANXIANGUANLI) == 1){
                try{
                    // 调用insert执行添加功能，如果成功则返回200及返回执行成功，如果添加失败会报SQLException进入catch返回职务名存在
                    if (method.equals("insert")){
                        zhiWuService.insertZhiWu(zhiwubiao);
                    // 调用update执行添加功能，如果成功则返回200及返回执行成功，如果添加失败会报SQLException进入catch返回职务名存在
                    }else if (method.equals("update")){
                        zhiWuService.updateZhiWu(zhiwubiao);
                    }
                    map.put("returnCode","200");
                    map.put("msg","执行成功");
                } catch (Exception e){
                    map.put("msg","职务名已存在");
                    e.printStackTrace();
                }
//            }
//        }
        return map;
    }
}
