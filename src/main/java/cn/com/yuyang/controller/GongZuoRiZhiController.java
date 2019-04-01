package cn.com.yuyang.controller;

import cn.com.yuyang.bean.GeRenKaoQinBean;
import cn.com.yuyang.bean.GongZuoJiLuBean;
import cn.com.yuyang.bean.GongZuoRiZhiBean;
import cn.com.yuyang.bean.RiZhiPingLunBean;
import cn.com.yuyang.pojo.Gongzuojilu;
import cn.com.yuyang.service.GongZuoRiZhiService;
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
 * Date: 2019/03/22
 * Description:
 * Version: V1.0
 */
@Controller
@RequestMapping("/rizhi")
public class GongZuoRiZhiController {
    private GongZuoRiZhiService gongZuoRiZhiService;

    @Autowired
    public GongZuoRiZhiController(GongZuoRiZhiService gongZuoRiZhiService) {
        this.gongZuoRiZhiService = gongZuoRiZhiService;
    }

    public GongZuoRiZhiService gongZuoRiZhiService() {
        return gongZuoRiZhiService;
    }

//    /**
//     * 查看及查询工作日志与日志草稿
//     * @param gongZuoRiZhiBean 内部存个人id检索相关日志，以及查询条件
//     * @return 查询到的结果集
//     */
//    @RequestMapping(value = "selectRiZhi",method = RequestMethod.POST)
//    @ResponseBody
//    public Map<String,Object> selectRiZhi(@RequestBody(required = false) GongZuoRiZhiBean gongZuoRiZhiBean){
//        Map<String,Object> map = new HashMap<>();
//        map.put("returnCode",-1);
//        try{
//            List<Gongzuojilu> list = gongZuoRiZhiService.selectRiZhiByDangAnId(gongZuoRiZhiBean);
//            map.put("data",list);
//            map.put("msg","查询成功");
//            map.put("returnCode",200);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return map;
//    }

    public Map<String, Object> selectBuMenRiZhi(@RequestBody(required = false) GongZuoRiZhiBean gongZuoRiZhiBean) {
        Map<String, Object> map = new HashMap<>();
        map.put("returnCode", -1);
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 更新草稿内容
     *
     * @param gongZuoJiLuBean
     * @return
     */
    @RequestMapping(value = "/updateCaoGao", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateCaoGao(@RequestBody(required = false) GongZuoJiLuBean gongZuoJiLuBean, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        String token = (String) request.getSession().getAttribute(SessionKey.TOKEN);    // 得到token
        if (gongZuoJiLuBean == null || token.trim().equals(gongZuoJiLuBean.getToken())) {
            map.put("returnCode", -1);
            try {
                gongZuoRiZhiService.updateCaoGao(gongZuoJiLuBean);
                map.put("msg", "更新成功");
                map.put("returnCode", 200);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            map.put("msg", "登录超时");
            map.put("returnCode", 408);
        }
        return map;
    }


    /**
     * 插入草稿
     *
     * @param gongZuoJiLuBean
     * @return
     */
    @RequestMapping(value = "/insertCaoGao", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> insertCaoGao(@RequestBody(required = false) GongZuoJiLuBean gongZuoJiLuBean, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        String token = (String) request.getSession().getAttribute(SessionKey.TOKEN);    // 得到token
        if (gongZuoJiLuBean == null || token.trim().equals(gongZuoJiLuBean.getToken())) {
            map.put("returnCode", -1);
            try {
                gongZuoRiZhiService.insertCaoGao(gongZuoJiLuBean);
                map.put("msg", "插入草稿成功");
                map.put("returnCode", 200);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            map.put("msg", "登录超时");
            map.put("returnCode", 408);
        }

        return map;
    }


    /**
     * 此方法是点击草稿 查看草稿
     *
     * @param gongZuoJiLuBean
     * @return
     */
    @RequestMapping(value = "/caoGao", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> selectCaoGao(@RequestBody(required = false) GongZuoJiLuBean gongZuoJiLuBean, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        String token = (String) request.getSession().getAttribute(SessionKey.TOKEN);    // 得到token
        if (gongZuoJiLuBean == null || token.trim().equals(gongZuoJiLuBean.getToken())) {
            map.put("returnCode", -1);
            try {
                gongZuoJiLuBean.setZhuangTai(-1);
                List<HashMap<String, Object>> list = gongZuoRiZhiService.selectCaoGao(gongZuoJiLuBean);
                map.put("msg", "查询草稿成功");
                map.put("returnCode", 200);
                map.put("data", list);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            map.put("msg", "登录超时");
            map.put("returnCode", 408);
        }


        return map;
    }

    /**
     * 此方法根据草稿ID删除草稿
     *
     * @param gongZuoJiLuBean
     * @return
     */
    @RequestMapping(value = "/deleteCaoGao", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> deleteCaoGao(@RequestBody(required = false) GongZuoJiLuBean gongZuoJiLuBean, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        String token = (String) request.getSession().getAttribute(SessionKey.TOKEN);    // 得到token
        if (gongZuoJiLuBean == null || token.trim().equals(gongZuoJiLuBean.getToken())) {
            map.put("returnCode", -1);
            try {
                gongZuoRiZhiService.deleteCaoGao(gongZuoJiLuBean);
                map.put("msg", "删除草稿成功");
                map.put("returnCode", 200);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            map.put("msg", "登录超时");
            map.put("returnCode", -1);
        }

        return map;
    }

    @RequestMapping(value = "/updateZhuangTai", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateZhuangTai(@RequestBody(required = false) GongZuoJiLuBean gongZuoJiLuBean, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        String token = (String) request.getSession().getAttribute(SessionKey.TOKEN);    // 得到token
        if (gongZuoJiLuBean == null || token.trim().equals(gongZuoJiLuBean.getToken())) {
            map.put("returnCode", -1);
            try {
                gongZuoRiZhiService.updateZhuangTai(gongZuoJiLuBean);
                map.put("msg", "草稿发表成功");
                map.put("returnCode", 200);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            map.put("msg", "登录超时");
            map.put("returnCode", -1);
        }

        return map;
    }

    /**
     * 点击日志，进入日志首页，获取90天内所有日志信息
     *
     * @param gongZuoRiZhiBean 内部存个人id检索相关日志
     * @return 查询到的结果集
     */
    @RequestMapping(value = "/shouye", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> chaKanRiZhi(@RequestBody(required = false) GongZuoRiZhiBean gongZuoRiZhiBean, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();

        map.put("returnCode", -1);
        map.put("msg", "查询失败");
        if (gongZuoRiZhiBean == null || gongZuoRiZhiBean.getToken() == null || !gongZuoRiZhiBean.getToken().equals(request.getSession().getAttribute(SessionKey.TOKEN))) {
            map.put("msg", "没有权限");
        } else {
            try {
                List<GongZuoJiLuBean> list = gongZuoRiZhiService.selectRiZhiByDangAnId(gongZuoRiZhiBean);
                map.put("data", zuZhuang(list));
                map.put("msg", "查询成功");
                map.put("returnCode", 200);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return map;
    }

    /**
     * 点击搜索，按条件搜素日志
     *
     * @param gongZuoRiZhiBean 内部存个人id检索相关日志
     * @return 查询到的结果集
     */
    @RequestMapping(value = "/sousuo", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> selectRiZhi(@RequestBody(required = false) GongZuoRiZhiBean gongZuoRiZhiBean, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        map.put("returncode", -1);
        map.put("msg", "查询失败");
        if (gongZuoRiZhiBean == null || gongZuoRiZhiBean.getToken() == null || !gongZuoRiZhiBean.getToken().equals(request.getSession().getAttribute(SessionKey.TOKEN))) {
            map.put("msg", "没有权限");
        } else {
            try {
                List<GongZuoJiLuBean> list = gongZuoRiZhiService.selectRiZhi(gongZuoRiZhiBean);
                map.put("data", zuZhuang(list));
                map.put("msg", "查询成功");
                map.put("returnCode", 200);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return map;
    }

    /**
     * 点击审阅，进入审阅首页，获取90天内所有提交给自己的日志
     *
     * @param gongZuoRiZhiBean 内部存个人id检索相关日志
     * @return 查询到的结果集
     */
    @RequestMapping(value = "/shenyue", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> shenYue(@RequestBody(required = false) GongZuoRiZhiBean gongZuoRiZhiBean, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        map.put("returnCode", -1);
        map.put("msg", "查询失败");
        if (gongZuoRiZhiBean == null || gongZuoRiZhiBean.getToken() == null || !gongZuoRiZhiBean.getToken().equals(request.getSession().getAttribute(SessionKey.TOKEN))) {
            map.put("msg", "没有权限");
        } else {
            try {
                List<GongZuoJiLuBean> list = gongZuoRiZhiService.shenYueRiZhi(gongZuoRiZhiBean);
                map.put("data", zuZhuang(list));
                map.put("msg", "查询成功");
                map.put("returnCode", 200);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return map;
    }

    /**
     * 点击某条待审阅信息，获取审阅详情
     *
     * @param gongZuoJiLuBean 内部存个人id检索相关日志
     * @return 查询到的结果集
     */
    @RequestMapping(value = "/shenyuexiangqing", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> shenYueXiangQing(@RequestBody(required = false) GongZuoJiLuBean gongZuoJiLuBean, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        map.put("returnCode", -1);
        map.put("msg", "查询失败");
        if (gongZuoJiLuBean == null || gongZuoJiLuBean.getToken() == null || !gongZuoJiLuBean.getToken().equals(request.getSession().getAttribute(SessionKey.TOKEN))) {
            map.put("msg", "没有权限");
        } else {
            try {
                List<GongZuoJiLuBean> list = gongZuoRiZhiService.shenYueXiangQing(gongZuoJiLuBean);
                map.put("data", zuZhuang(list));
                map.put("msg", "查询成功");
                map.put("returnCode", 200);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return map;
    }

    /**
     * 点击已审阅按钮，修改审阅状态为已审阅
     *
     * @param gongZuoJiLuBean 内部存个人id检索相关日志
     * @return 查询到的结果集
     */
    @RequestMapping(value = "/yishenyue", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> yiShenYue(@RequestBody(required = false) GongZuoJiLuBean gongZuoJiLuBean, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        map.put("returnCode", -1);
        map.put("msg", "修改失败");
        if (gongZuoJiLuBean == null || gongZuoJiLuBean.getToken() == null || !gongZuoJiLuBean.getToken().equals(request.getSession().getAttribute(SessionKey.TOKEN))) {
            map.put("msg", "没有权限");
        } else {
            try {
                gongZuoRiZhiService.yiShenYue(gongZuoJiLuBean);
                map.put("msg", "修改成功");
                map.put("returnCode", 200);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return map;
    }

    /**
     * 点击提交，提交某条工作日到数据库
     *
     * @param gongZuoJiLuBean 内部存个人id,日志类型，日志内容，接收人id等信息用于插入到数据库
     * @return 查询到的结果集
     */
    @RequestMapping(value = "/tijiao", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> tiJiaoRiZhi(@RequestBody(required = false) GongZuoJiLuBean gongZuoJiLuBean, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        map.put("returnCode", -1);
        map.put("msg", "提交失败");
        if (gongZuoJiLuBean == null || gongZuoJiLuBean.getToken() == null || !gongZuoJiLuBean.getToken().equals(request.getSession().getAttribute(SessionKey.TOKEN))) {
            map.put("msg", "没有权限");
        } else {
            try {
                gongZuoRiZhiService.tiJiao(gongZuoJiLuBean);
                map.put("msg", "提交成功");
                map.put("returnCode", 200);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return map;
    }

    /**
     * 点击提交，提交某条日志评论到数据库
     *
     * @param riZhiPingLunBean 内部存个人id,评论内容，被评论的日志的id等信息用于插入到数据库
     * @return 查询到的结果集
     */
    @RequestMapping(value = "/shenyuepinglun", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> shenYuePingLun(@RequestBody(required = false) RiZhiPingLunBean riZhiPingLunBean, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        map.put("returnCode", -1);
        map.put("msg", "提交失败");
        if (riZhiPingLunBean == null || riZhiPingLunBean.getToken() == null || !riZhiPingLunBean.getToken().equals(request.getSession().getAttribute(SessionKey.TOKEN))) {
            map.put("msg", "没有权限");
        } else {
            try {
                gongZuoRiZhiService.pingLun(riZhiPingLunBean);
                map.put("msg", "提交成功");
                map.put("returnCode", 200);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return map;
    }

    /**
     * 点击搜索，用于日志评论的多条件搜索
     *
     * @param gongZuoRiZhiBean 内部存个人id,时间，关键词等信息用于搜索数据库
     * @return 查询到的结果集
     */
    @RequestMapping(value = "/shenyuesousuo", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> shenYueSouSuo(@RequestBody(required = false) GongZuoRiZhiBean gongZuoRiZhiBean, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        map.put("returnCode", -1);
        map.put("msg", "查询失败");
        if (gongZuoRiZhiBean == null || gongZuoRiZhiBean.getToken() == null || !gongZuoRiZhiBean.getToken().equals(request.getSession().getAttribute(SessionKey.TOKEN))) {
            map.put("msg", "没有权限");
        } else {
            try {
                List<GongZuoJiLuBean> list = gongZuoRiZhiService.shenYueSouSuo(gongZuoRiZhiBean);
                map.put("data", list);
                map.put("msg", "查询成功");
                map.put("returnCode", 200);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return map;
    }

    public List zuZhuang(List<GongZuoJiLuBean> list) {
        List l = new ArrayList();
        for (GongZuoJiLuBean g : list
        ) {
            Map map = new HashMap();
            map.put("id", g.getId());
            map.put("neiRong", g.getNeiRong());
            map.put("dangAnId", g.getDangAnId());
            map.put("shiJian", g.getShiJian().toString());
            map.put("xingMing", g.getXingMing());
            map.put("zhuangTai", g.getZhuangTai());
            map.put("jieShouRenId", g.getJieShouRenId());
            map.put("leiXing", g.getLeiXing());

            if (g.getRiZhiPingLunId() != 0) {
                map.put("riZhiPingLunId", g.getRiZhiPingLunId());
            }
            if (g.getPingLunNeiRong() != null && !g.getPingLunNeiRong().equals("")) {
                map.put("pingLunNeiRong", g.getPingLunNeiRong());
            }
            if (g.getRiQi() != null && !g.getRiQi().equals("")) {
                map.put("pingLunRiQi", g.getRiQi().toString());
            }
            map.put("pingLunRen", g.getPingLunRen());
            l.add(map);
        }
        return l;
    }
}
