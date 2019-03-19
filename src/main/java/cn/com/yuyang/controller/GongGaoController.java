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
        Map<String, Object> returnMap = new HashMap<>();
        // 如果token不为空,说明用户已经登录,并且前端的token必须和我session的token相同
        if (token != null && token.equals(gongGaoBean.getToken())) {
            int buMenId = (int) request.getSession().getAttribute(SessionKey.BUMENID); // 得到部门id
            int dangAnId = (int) request.getSession().getAttribute(SessionKey.DANGANID); // 得到档案id
            // 调用查询公告方法，传入登录人的档案id以及他的部门id，他能查看得到他自己发布的和他本部门能查看的公告
            List<Gonggao> list = gongGaoService.selectGongGao(buMenId, dangAnId);
            returnMap.put("yiFaBuTiaoShu", gongGaoService.selectGongGaoFaBu(dangAnId, 1));  // 得到已发布的条数,并且存入map
            returnMap.put("weiFaBuTiaoShu", gongGaoService.selectGongGaoFaBu(dangAnId, 0));  // 得到未发布的条数,并且存入map
            returnMap.put("returncode", 200);   // 成功状态码200
            returnMap.put("msg", "成功");     // 消息提示成功
            int zongYeShu = list.size() / 3 + 1; // 数据库中查到公告的总条数除以每页条数 + 1 为总页数
            if (list.size() % 3 == 0) {         //  如果总条数刚好是3的倍数
                zongYeShu = list.size() / 3;   // 总页数就是总条数 / 37
            }
            returnMap.put("ZongYeShu", zongYeShu);
            returnMap.put("data", list);   // data数据中将list封装进去
        } else {
            returnMap.put("returncode", -1);   // 成功状态码-1
            returnMap.put("msg", "登录状态已超时，请从新登录");     // 消息提示如果比对不成功，那么说明要么登录超时，session已到时
        }
        return returnMap;
    }

    /**
     * 新建公告，保存并且发布，或者只是保存，前端传入发布人的档案id，发布栏目，接收部门名称，
     * 公告名称，公告内容，发布时间，发布状态，如果用户点击保存那么状态为0，如果用户保存并发布那么状态为1
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
        if (token != null && token.equals(gongGaoBean.getToken()) &&
                (Integer) request.getSession().getAttribute(SessionKey.FABUXIUGAIGONGGAO) == 1) {
            int dangAnId = (int) request.getSession().getAttribute(SessionKey.DANGANID); // 得到档案id
            gongGaoBean.setDangAnId(1);  // 档案id存入公告bean对象
            System.out.println(gongGaoService.selectBuMenId(gongGaoBean));
            gongGaoBean.setBuMenId(gongGaoService.selectBuMenId(gongGaoBean));  // 调用查询部门id方法，得到部门id并存入gongGaoBean对象
            gongGaoService.newInsertGongGao(gongGaoBean);  // 调用新增方法


        } else {
            returnMap.put("returncode", -1);   // 成功状态码-1
            returnMap.put("msg", "登录状态已超时，请从新登录");     // 消息提示如果比对不成功，那么说明要么登录超时，session已到时
        }
        return returnMap;
    }
}
