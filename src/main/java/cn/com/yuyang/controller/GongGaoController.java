package cn.com.yuyang.controller;

import cn.com.yuyang.bean.GongGaoBean;
import cn.com.yuyang.pojo.Gonggao;
import cn.com.yuyang.pojo.Zhiwubiao;
import cn.com.yuyang.service.GongGaoService;
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
 * User: Jxhun
 * Date: 2019/03/18
 * Description:
 * Version: V1.0
 */
@Controller
@RequestMapping("/GongGao")
public class GongGaoController {

    @Autowired
    private final GongGaoService gongGaoService;

    public GongGaoController(GongGaoService gongGaoService) {
        this.gongGaoService = gongGaoService;
        System.out.println("-----GongGaoController()-------");
    }

    /**
     * 点击公告，查询数据库公告(GongGao)表，查询所有公告，进行分页每页3条公告，公告按照时间先后顺序排序。
     * 注意：前台需要做分页，所以后台返回时，需要返回总条数。
     * 前端传入操作人id
     *
     * @param gongGaoBean 得到前端传入的token
     * @return 成功返回成功的json字符串，map格式，json中存入了返回码
     */
    @RequestMapping(value = {"/seleceAll"})
    @ResponseBody
    public Map<String, Object> selectAll(@RequestBody(required = false) GongGaoBean gongGaoBean, HttpServletRequest request) {
        String token = (String) request.getSession().getAttribute(SessionKey.TOKEN);    // 得到token
//        request.getSession().setAttribute(SessionKey.BUMENID, 1);
//        request.getSession().setAttribute(SessionKey.DANGANID, 1);
        Map<String, Object> returnMap = new HashMap<>();
        // 如果token不为空,说明用户已经登录,并且前端的token必须和我session的token相同
        if (gongGaoBean != null && token != null && token.equals(gongGaoBean.getToken())) {
            int buMenId = (int) request.getSession().getAttribute(SessionKey.BUMENID); // 得到部门id
            int dangAnId = (int) request.getSession().getAttribute(SessionKey.DANGANID); // 得到档案id
            // 调用查询公告方法，传入登录人的档案id以及他的部门id，他能查看得到他自己发布的和他本部门能查看的公告
            gongGaoBean.setBuMenId(buMenId);  // 登录人的部门id存入bean对象
            gongGaoBean.setDangAnId(dangAnId);//登录人的档案id存入bean对象
            List<Gonggao> list = gongGaoService.selectGongGao(gongGaoBean);  // 调用查询公告方法传入 公告bean对象
            returnMap.put("yiFaBuTiaoShu", gongGaoService.selectGongGaoFaBu(dangAnId, 1));  // 得到已发布的条数,并且存入map
            returnMap.put("weiFaBuTiaoShu", gongGaoService.selectGongGaoFaBu(dangAnId, 0));  // 得到未发布的条数,并且存入map
            returnMap.put("returncode", 200);   // 成功状态码200
            returnMap.put("msg", "成功,注意里面的id是每条公告的id，需要隐藏存储在前端每条公告里面，如果用户点击公告需传入这个id和浏览数来修改浏览次数");     // 消息提示成功
            int zongYeShu = list.size() / 3 + 1; // 数据库中查到公告的总条数除以每页条数 + 1 为总页数
            if (list.size() % 3 == 0) {         //  如果总条数刚好是3的倍数
                zongYeShu = list.size() / 3;   // 总页数就是总条数 / 37
            }
            returnMap.put("ZongYeShu", zongYeShu);
            returnMap.put("data", list);   // data数据中将list封装进去
        } else {
            returnMap.put("returncode", -1);   // 成功状态码-1
            returnMap.put("msg", "登录状态已超时或者前端未传数据");     // 消息提示如果比对不成功，那么说明要么登录超时，session已到时
        }
        return returnMap;
    }

    /**
     * 新建公告，保存并且发布，或者只是保存，前端传入发布人的档案id，发布栏目，接收部门名称，
     * 公告名称，公告内容，发布时间，发布状态，如果用户点击保存那么状态为0，如果用户保存并发布那么状态为1
     * 首先使用部门名称查询得到部门id
     * 然后将前端传入的数据存入数据库
     *
     * @param gongGaoBean 得到前端传入的数据
     * @return 成功返回成功的json字符串，map格式，json中存入了返回码
     */
    @RequestMapping(value = {"/newInsert"})
    @ResponseBody
    public Map<String, Object> newInsert(@RequestBody(required = false) GongGaoBean gongGaoBean, HttpServletRequest request) {
        String token = (String) request.getSession().getAttribute(SessionKey.TOKEN);    // 得到token
//        gongGaoBean = new GongGaoBean();
        Map<String, Object> returnMap = new HashMap<>();
        // 如果token不为空,说明用户已经登录,并且前端的token必须和我session的token相同,并且这个用户有发布修改公告的权限
        if (gongGaoBean != null && token != null && token.equals(gongGaoBean.getToken()) &&
                (Integer) request.getSession().getAttribute(SessionKey.FABUXIUGAIGONGGAO) == 1) {
            int dangAnId = (int) request.getSession().getAttribute(SessionKey.DANGANID); // 得到档案id
            gongGaoBean.setDangAnId(dangAnId);  // 档案id存入公告bean对象
            if (!gongGaoBean.getBuMenMingCheng().equals("全体员工")) {  // 如果前端传入的部门名称不为全体员工，那么就调用查询部门id方法得到部门id
                Integer buMenId = gongGaoService.selectBuMenId(gongGaoBean);
                if (buMenId != -1) {  // 如果查询结果不为-1，那么说明查询成功
                    gongGaoBean.setBuMenId(buMenId);  // 调用查询部门id方法，得到部门id并存入gongGaoBean对象
                } else {  // 如果查询结果为-1，那么直接返回状态码-100提醒前端数据库出错
                    returnMap.put("returncode", -100);   // 成功状态码200
                    returnMap.put("msg", "数据库异常，数据库存在多个相同的部门名称");     // 消息提示上传成功
                    return returnMap;
                }
            }
            gongGaoService.newInsertGongGao(gongGaoBean);  // 调用新增方法
            returnMap.put("returncode", 200);   // 成功状态码200
            returnMap.put("msg", "200上传成功");     // 消息提示上传成功
        } else {
            returnMap.put("returncode", -1);   // 成功状态码-1
            returnMap.put("msg", "登录状态已超时或者前端未传数据");     // 消息提示如果比对不成功，那么说明要么登录超时，session已到时
        }
        return returnMap;
    }

    /**
     * 这个方法用来修改浏览条数，用户每次点击公告进行查看，那么前端就调用该接口传入公告的id进行浏览条数新增
     *
     * @param gongGaoBean 公告bean，用来接收前端传入的参数
     * @param request
     * @return
     */
    @RequestMapping(value = {"/xiuGaiLiuLanShu"})
    @ResponseBody
    public Map<String, Object> xiuGaiLiuLanShu(@RequestBody(required = false) GongGaoBean gongGaoBean, HttpServletRequest request) {
        String token = (String) request.getSession().getAttribute(SessionKey.TOKEN);    // 得到token
        Map<String, Object> returnMap = new HashMap<>();
        // 如果token不为空,说明用户已经登录,并且前端的token必须和我session的token相同,并且这个用户有发布修改公告的权限
        if (gongGaoBean != null && token != null && token.equals(gongGaoBean.getToken())) {
            gongGaoService.xiuGaiLiuLanShu(gongGaoBean); // 调用修改浏览数方法
            returnMap.put("returncode", 200);   // 成功状态码200
            returnMap.put("msg", "修改成功");     // 消息提示上传成功
        } else {
            returnMap.put("returncode", -1);   // 成功状态码-1
            returnMap.put("msg", "登录状态已超时或者前端未传数据");     // 消息提示如果比对不成功，那么说明要么登录超时，session已到时
        }
        return returnMap;
    }

    /**
     * 这个方法用来查询公告的详情，前端用户点击公告进入详情查看页面，传递公告id到后端
     *
     * @param gongGaoBean 接收前端传入到的参数
     * @param request     用来获取session
     * @return 返回json
     */
    @RequestMapping(value = {"/gongGaoXiangQing"})
    @ResponseBody
    public Map<String, Object> gongGaoXiangQing(@RequestBody(required = false) GongGaoBean gongGaoBean, HttpServletRequest request) {
        String token = (String) request.getSession().getAttribute(SessionKey.TOKEN);    // 得到token
        Map<String, Object> returnMap = new HashMap<>();
        // 如果token不为空,说明用户已经登录,并且前端的token必须和我session的token相同,并且这个用户有发布修改公告的权限
        if (gongGaoBean != null && token != null && token.equals(gongGaoBean.getToken())) {
            Gonggao gonggao = gongGaoService.xiangQing(gongGaoBean); // 调用查询方法，传入公告bean
            returnMap.put("returncode", 200);   // 成功状态码200
            returnMap.put("msg", "查询成功，注意，如果查询得到的dangAnId和登录用户的id相同，那么不显示编辑删除按钮，" +
                    "如果buMenMingCheng为null，那么接收人就是全体员工");     // 消息提示上传成功
            returnMap.put("data", gonggao);    // 查询结果存入data
        } else {
            returnMap.put("returncode", -1);   // 成功状态码-1
            returnMap.put("msg", "登录状态已超时或者前端未传数据");     // 消息提示如果比对不成功，那么说明要么登录超时，session已到时
        }
        return returnMap;
    }

    /**
     * 这个方法用来删除公告，用户点击删除按钮调用此接口
     *
     * @param gongGaoBean 公告bean，用来接收前端传入的参数
     * @param request     用来获取session
     * @return 返回json
     */
    @RequestMapping(value = {"/zhuangTai"})
    @ResponseBody
    public Map<String, Object> shanChu(@RequestBody(required = false) GongGaoBean gongGaoBean, HttpServletRequest request) {
        String token = (String) request.getSession().getAttribute(SessionKey.TOKEN);    // 得到token
        Map<String, Object> returnMap = new HashMap<>();
        // 如果token不为空,说明用户已经登录,并且前端的token必须和我session的token相同,并且这个用户有发布修改公告的权限
        if (gongGaoBean != null && token != null && token.equals(gongGaoBean.getToken())) {

            Integer action = gongGaoService.shanChu(gongGaoBean); // 调用修改浏览数方法,得到删除结果
            if (action == 1) {
                returnMap.put("returncode", 200);   // 成功状态码200
                returnMap.put("msg", "删除成功");     // 消息提示上传成功
            } else {
                returnMap.put("returncode", 0);   // 成功状态码200
                returnMap.put("msg", "删除失败，登录人的档案id和发布人档案id不匹配");     // 消息提示上传成功
            }
        } else {
            returnMap.put("returncode", -1);   // 成功状态码-1
            returnMap.put("msg", "登录状态已超时或者前端未传数据");     // 消息提示如果比对不成功，那么说明要么登录超时，session已到时
        }
        return returnMap;
    }

    /**
     * 这个方法发布已保存的公告，前端传入登录人档案id
     *
     * @param gongGaoBean 公告bean，用来接收前端传入的参数
     * @param request     用来获取session
     * @return 返回json
     */
    @RequestMapping(value = {"/geRenGongGaoZhuangTai"})
    @ResponseBody
    public Map<String, Object> geRenGongGaoZhuangTai(@RequestBody(required = false) GongGaoBean gongGaoBean, HttpServletRequest request) {
        String token = (String) request.getSession().getAttribute(SessionKey.TOKEN);    // 得到token
        Map<String, Object> returnMap = new HashMap<>();
        // 如果token不为空,说明用户已经登录,并且前端的token必须和我session的token相同,并且这个用户有发布修改公告的权限
        if (gongGaoBean != null && token != null && token.equals(gongGaoBean.getToken())) {
            List<Gonggao> list = gongGaoService.geRenGongGaoZhuangTai(gongGaoBean); // 调用查询方法，传入公告bean
            returnMap.put("returncode", 200);   // 成功状态码200
            returnMap.put("msg", "成功,注意里面的id是每条公告的id，需要隐藏存储在前端每条公告里面，如果用户点击公告需传入这个id和浏览数来修改浏览次数");     // 消息提示成功
            int zongYeShu = list.size() % 3 == 0 ? (list.size() / 3) : (list.size() / 3 + 1); // 计算总页数
            returnMap.put("ZongYeShu", zongYeShu); //传出总页数
            returnMap.put("data", list);   // data数据中将list封装进去
        } else {
            returnMap.put("returncode", -1);   // 成功状态码-1
            returnMap.put("msg", "登录状态已超时或者前端未传数据");     // 消息提示如果比对不成功，那么说明要么登录超时，session已到时
        }
        return returnMap;
    }

    /**
     * 修改公告，保存并且发布，或者只是保存，前端传入发布人的档案id，发布栏目，接收部门名称，
     * 公告名称，公告内容，发布时间，发布状态，如果用户点击保存那么状态为0，如果用户保存并发布那么状态为1
     * 首先使用部门名称查询得到部门id
     * 然后将前端传入的数据存入数据库
     *
     * @param gongGaoBean 得到前端传入的参数
     * @return 返回json字符串，map格式，json中存入了返回码
     */
    @RequestMapping(value = {"/update"})
    @ResponseBody
    public Map<String, Object> updateGongGao(@RequestBody(required = false) GongGaoBean gongGaoBean, HttpServletRequest request) {
        String token = (String) request.getSession().getAttribute(SessionKey.TOKEN);    // 得到token
//        gongGaoBean = new GongGaoBean();
        Map<String, Object> returnMap = new HashMap<>();
        // 如果token不为空,说明用户已经登录,并且前端的token必须和我session的token相同,并且这个用户有发布修改公告的权限
        if (gongGaoBean != null && token != null && token.equals(gongGaoBean.getToken()) &&
                (Integer) request.getSession().getAttribute(SessionKey.FABUXIUGAIGONGGAO) == 1) {
            int dangAnId = (int) request.getSession().getAttribute(SessionKey.DANGANID); // 得到档案id
        if (!gongGaoBean.getBuMenMingCheng().equals("全体员工")) {  // 如果前端传入的部门名称不为全体员工，那么就调用查询部门id方法得到部门id
            Integer buMenId = gongGaoService.selectBuMenId(gongGaoBean);
            if (buMenId != -1) {  // 如果查询结果不为-1，那么说明查询成功
                gongGaoBean.setBuMenId(buMenId);  // 调用查询部门id方法，得到部门id并存入gongGaoBean对象
            } else {  // 如果查询结果为-1，那么直接返回状态码-100提醒前端数据库出错
                returnMap.put("returncode", -100);   // 成功状态码200
                returnMap.put("msg", "数据库异常，数据库存在多个相同的部门名称");     // 消息提示上传成功
                return returnMap;
            }
        }
        if (gongGaoService.updateGongGao(gongGaoBean) == 1) { // 调用修改方法并判断，如果结果为1那么就代表修改成功
            returnMap.put("returncode", 200);   // 成功状态码200
            returnMap.put("msg", "200修改成功");     // 消息提示上传成功
        } else {
            returnMap.put("returncode", 0);   // 成功状态码200
            returnMap.put("msg", "0修改失败，登录人的id和发布人多的id不匹配");     // 消息提示上传成功
        }
        } else {
            returnMap.put("returncode", -1);   // 成功状态码-1
            returnMap.put("msg", "登录状态已超时或者前端未传数据");     // 消息提示如果比对不成功，那么说明要么登录超时，session已到时
        }
        return returnMap;
    }
}
