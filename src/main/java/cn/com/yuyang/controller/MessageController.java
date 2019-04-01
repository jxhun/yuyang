package cn.com.yuyang.controller;

import cn.com.yuyang.bean.*;
import cn.com.yuyang.service.MessageService;
import cn.com.yuyang.util.FileUpload;
import cn.com.yuyang.util.SessionKey;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/msg")
public class MessageController {
    private MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    //调用此接口，获取用户收件箱所有未删除信息
    @RequestMapping(value = {"/dingshihuoqu"}, produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> dingShiHuoQu(String method, @RequestBody IdBean idBean, HttpServletRequest request) {

        Long did = (Long) request.getSession().getAttribute(SessionKey.DANGANID);  //从session中获取登陆者的id
        String token = (String) request.getSession().getAttribute(SessionKey.TOKEN);
        String uri = request.getRequestURI();
        int id = idBean.getId();
        Map<String, Object> map = new HashMap<>();
        map.put("returnCode", -1);
        map.put("msg", "没有权限");
        try {
            if (id == did && idBean.getToken() != null && idBean.getToken().equals(token)) {          //如果档案id和前台传过来的id相同，则如下组装map
                map.put("returnCode", 200);
                map.put("msg", "正常请求");
                List<XinXiBean> list = messageService.selectAll(id);//获取该用户的所有信息
                ArrayList arrayList = zuZhuang(list, id, uri); //传入获取的信息，登陆者档案id，和前端访问路径uri，组装ArrayList
                map.put("data", arrayList);
            } else {                        //如果该用户档案id和前台传来的id不同，则返回错误信息；
                map.put("returnCode", 408);
                map.put("msg", "访问超时，请重新登陆");
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
//

        return map;
    }

    //调用此接口，返回用户收件箱满足搜索条件的未删除信息
    @RequestMapping(value = {"/sousuo"}, produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> souSuo(@RequestBody XinXiSouSuoBean xinXiSouSuoBean, HttpServletRequest request) {
        Long did = (Long) request.getSession().getAttribute(SessionKey.DANGANID);  //从session中获取登陆者的id
        String token = (String) request.getSession().getAttribute(SessionKey.TOKEN);      //从session中获取token
        int id = xinXiSouSuoBean.getId();
        String uri = request.getRequestURI();
        Map<String, Object> map = new HashMap<>();
        map.put("returnCode", -1);
        map.put("msg", "没有权限");
        try {
            if (id == did && xinXiSouSuoBean.getToken() != null && xinXiSouSuoBean.getToken().equals(token)) {          //如果档案id和前台传过来的id相同，则如下组装map
                map.put("returnCode", 200);
                map.put("msg", "正常请求");
                List<XinXiBean> list = messageService.selectByParam(xinXiSouSuoBean);//获取该用户按条件搜索的信息
                ArrayList arrayList = zuZhuang(list, id, uri);   //传入获取的信息，登陆者档案id，和前端访问路径uri，组装ArrayList
                map.put("data", arrayList);
            } else {                        //如果该用户档案id和前台传来的id不同，则返回错误信息；
                map.put("returnCode", 408);
                map.put("msg", "访问超时，请重新登陆");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//

        return map;
    }

    //调用此接口，获取该条信息的详细情况
    @RequestMapping(value = {"/xiangxixinxi"}, produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> xiangXiXinXi(@RequestBody XiangXiXinXiBean xiangXiXinXiBean, HttpServletRequest request) {
        Long did = (Long) request.getSession().getAttribute(SessionKey.DANGANID);  //从session中获取登陆者的id
        String token = (String) request.getSession().getAttribute(SessionKey.TOKEN);      //从session中获取token
        int id = xiangXiXinXiBean.getId();
        Map<String, Object> map = new HashMap<>();
        map.put("returnCode", -1);
        map.put("msg", "没有权限");
        try {
            if (id == did && xiangXiXinXiBean.getToken() != null && xiangXiXinXiBean.getToken().equals(token)) {          //如果档案id和前台传过来的id相同，则如下组装map
                map.put("returnCode", 200);
                map.put("msg", "正常请求");
                XinXiBean xinXiBean = messageService.selectXiangQing(xiangXiXinXiBean);//获取该条信息的所有信息
                Map<String, Object> map1 = new HashMap<>();
                map1.put("id", xinXiBean.getId());
                map1.put("faXinId", xinXiBean.getFaXinId());
                map1.put("faJianRenXingMing", xinXiBean.getFaJianRenXingMing());
                map1.put("shouXinId", xinXiBean.getShouXinId());
                map1.put("shouJianRenXingMing", xinXiBean.getShouJianRenXingMing());
                map1.put("xinXiBiaoTi", xinXiBean.getXinXiBiaoTi());
                map1.put("xinXiNeiRong", xinXiBean.getXinXiNeiRong());
                map1.put("xinXiShiJian", xinXiBean.getXinXiShiJian().toString());
                map1.put("fuJianDiZhi", xinXiBean.getFuJianDiZhi());
                map1.put("faSongZhuangTai", xinXiBean.getFaSongZhuangTai());
                map1.put("shouCangZhuangTai", xinXiBean.getShouJianRenShouCangZhuangTai());
                messageService.yiDu(xiangXiXinXiBean);
                map.put("data", map1);
            } else {                        //如果该用户档案id和前台传来的id不同，则返回错误信息；
                map.put("returnCode", 408);
                map.put("msg", "访问超时，请重新登陆");
            }
        } catch (Exception e) {
//            e.printStackTrace();
        }
//

        return map;
    }

    //调用此接口，从用户收件箱或者发件箱批量或者单独删除信息
    @RequestMapping(value = {"/shanchu"}, produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> shanChu(@RequestBody XinXiShanChuBean xinXiShanChuBean, HttpServletRequest request) {
        Long did = (Long) request.getSession().getAttribute(SessionKey.DANGANID);  //从session中获取登陆者的id
        String token = (String) request.getSession().getAttribute(SessionKey.TOKEN);      //从session中获取token
        int id = xinXiShanChuBean.getId();
//        for (int i: xinXiShanChuBean.getXinXiId()
//             ) {
//        }
        Map<String, Object> map = new HashMap<>();
        map.put("returnCode", -1);
        map.put("msg", "没有权限");
        try {
            if (id == did && xinXiShanChuBean.getToken() != null && xinXiShanChuBean.getToken().equals(token)) {          //如果档案id和前台传过来的id相同，则如下组装map
                messageService.delectXinXi(xinXiShanChuBean);            //根据前台传来的登录人档案id，信息的id，修改信息的状态为0
                map.put("returnCode", 200);
                map.put("msg", "正常删除");

            } else {                        //如果该用户档案id和前台传来的id不同，则返回错误信息；
                map.put("returnCode", 408);
                map.put("msg", "访问超时，请重新登陆");
            }
        } catch (Exception e) {
//            e.printStackTrace();
        }


        return map;
    }

    //调用此接口，收藏或者取消收藏此条信息
    @RequestMapping(value = {"/shoucang"}, produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> shouCang(@RequestBody XinXiShouCangBean xinXiShouCangBean, HttpServletRequest request) {

        Long did = (Long) request.getSession().getAttribute(SessionKey.DANGANID);  //从session中获取登陆者的id
        String token = (String) request.getSession().getAttribute(SessionKey.TOKEN);      //从session中获取token
        int id = xinXiShouCangBean.getId();
        Map<String, Object> map = new HashMap<>();
        map.put("returnCode", -1);
        map.put("msg", "没有权限");
        try {
            if (id == did && xinXiShouCangBean.getToken() != null && xinXiShouCangBean.getToken().equals(token)) {          //如果档案id和前台传过来的id相同，则如下组装map
                messageService.shouCangXinXi(xinXiShouCangBean);    //调用收藏方法，修改收藏状态
                map.put("returnCode", 200);
                map.put("msg", "正常处理收藏请求");

            } else {                        //如果该用户档案id和前台传来的id不同，则返回错误信息；
                map.put("returnCode", 408);
                map.put("msg", "访问超时，请重新登陆");
            }
        } catch (Exception e) {
//            e.printStackTrace();
        }
//

        return map;
    }


    //调用此接口，回复信息,或者发送信息，并将发送的信息存入数据库
    @RequestMapping(value = {"/huifu", "/fasongxinxi"}, produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> huiFu(@RequestBody XinXiFaSongBean xinXiFaSongBean, HttpServletRequest request) {
        Long did = (Long) request.getSession().getAttribute(SessionKey.DANGANID);  //从session中获取登陆者的id
        String token = (String) request.getSession().getAttribute(SessionKey.TOKEN);      //从session中获取token
        int id = xinXiFaSongBean.getId();
        Map<String, Object> map = new HashMap<>();
        map.put("returnCode", -1);
        map.put("msg", "没有权限");
        try {
            if (id == did && xinXiFaSongBean.getToken() != null && xinXiFaSongBean.getToken().equals(token)) {          //如果档案id和前台传过来的id相同，调用信息发送方法，并如下组装map
                messageService.faSongXinXi(xinXiFaSongBean, request.getSession());   //
                map.put("returnCode", 200);
                map.put("msg", "正常请求");

            } else {                        //如果该用户档案id和前台传来的id不同，则返回错误信息；
                map.put("returnCode", 408);
                map.put("msg", "访问超时，请重新登陆");
            }
        } catch (Exception e) {
//            e.printStackTrace();
        }
//

        return map;

    }

    //调用此接口，获取用户所有已发送的未删除信息
    @RequestMapping(value = {"/yifaxinxi"}, produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> yiFaXinXi(@RequestBody IdBean idBean, HttpServletRequest request) {
        Long did = (Long) request.getSession().getAttribute(SessionKey.DANGANID);  //从session中获取登陆者的id
        String token = (String) request.getSession().getAttribute(SessionKey.TOKEN);      //从session中获取token
        int id = idBean.getId();
        Map<String, Object> map = new HashMap<>();
        map.put("returnCode", -1);
        map.put("msg", "没有权限");
        try {
            if (id == did && idBean.getToken() != null && idBean.getToken().equals(token)) {          //如果档案id和前台传过来的id相同，则如下组装map
                map.put("returnCode", 200);
                map.put("msg", "正常请求");
                List<XinXiBean> list = messageService.selectYiFaXinXi(idBean);//获取该用户的所有已发送的信息
                ArrayList arrayList = zuZhuang(list, idBean.getId(), request.getRequestURI());   //传入获取的信息，登陆者档案id，和前端访问路径uri，组装ArrayList
                map.put("data", arrayList);
            } else {                        //如果该用户档案id和前台传来的id不同，则返回错误信息；
                map.put("returnCode", 408);
                map.put("msg", "访问超时，请重新登陆");
            }
        } catch (Exception e) {
//            e.printStackTrace();
//
        }
//

        return map;
    }

    //访问此接口，返回满足搜索条件的所有已发的未删除信息
    @RequestMapping(value = {"/yifaxinxisousuo"}, produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> yiFaXinXiSouSuo(@RequestBody XinXiSouSuoBean xinXiSouSuoBean, HttpServletRequest request) {
        Long did = (Long) request.getSession().getAttribute(SessionKey.DANGANID);  //从session中获取登陆者的id
        String token = (String) request.getSession().getAttribute(SessionKey.TOKEN);      //从session中获取token
        int id = xinXiSouSuoBean.getId();
        Map<String, Object> map = new HashMap<>();
        map.put("returnCode", -1);
        map.put("msg", "没有权限");
        try {
            if (id == did && xinXiSouSuoBean.getToken() != null && xinXiSouSuoBean.getToken().equals(token)) {          //如果档案id和前台传过来的id相同，则如下组装map
                map.put("returnCode", 200);
                map.put("msg", "正常请求");
                List<XinXiBean> list = messageService.yiFaXinXiSouSuo(xinXiSouSuoBean);//获取该用户的按条件搜索出来的已发送的信息
                ArrayList arrayList = zuZhuang(list, xinXiSouSuoBean.getId(), request.getRequestURI());   //传入获取的信息，登陆者档案id，和前端访问路径uri，组装ArrayList
                map.put("data", arrayList);
            } else {                        //如果该用户档案id和前台传来的id不同，则返回错误信息；
                map.put("returnCode", 408);
                map.put("msg", "访问超时，请重新登陆");
            }
        } catch (Exception e) {
//            e.printStackTrace();
        }
//

        return map;
    }

    //调用此接口，返回该用户所有收藏的未删除信息
    @RequestMapping(value = {"/xinxishoucang"}, produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> xinXiShouCang(@RequestBody IdBean idBean, HttpServletRequest request) {
        Long did = (Long) request.getSession().getAttribute(SessionKey.DANGANID);  //从session中获取登陆者的id
        String token = (String) request.getSession().getAttribute(SessionKey.TOKEN);      //从session中获取token
        int id = idBean.getId();
        Map<String, Object> map = new HashMap<>();
        map.put("returnCode", -1);
        map.put("msg", "没有权限");
        try {
            if (id == did && idBean.getToken() != null && idBean.getToken().equals(token)) {          //如果档案id和前台传过来的id相同，则如下组装map
                map.put("returnCode", 200);
                map.put("msg", "正常请求");
                List<XinXiBean> list = messageService.selectXinXiShouCang(idBean);//获取该用户收藏的信息
                ArrayList arrayList = zuZhuang(list, idBean.getId(), request.getRequestURI());   // //传入获取的信息，登陆者档案id，和前端访问路径uri，组装ArrayList
                map.put("data", arrayList);
            } else {                        //如果该用户档案id和前台传来的id不同，则返回错误信息；
                map.put("returnCode", 408);
                map.put("msg", "访问超时，请重新登陆");
            }
        } catch (Exception e) {
//            e.printStackTrace();
        }
//

        return map;
    }

    //调用此接口，按条件搜索该用户收藏的未删除信息，并返回
    @RequestMapping(value = {"/xinxishoucangsousuo"}, produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> xinXiShouCangSouSuo(@RequestBody XinXiSouSuoBean xinXiSouSuoBean, HttpServletRequest request) {
        Long did = (Long) request.getSession().getAttribute(SessionKey.DANGANID);  //从session中获取登陆者的id
        String token = (String) request.getSession().getAttribute(SessionKey.TOKEN);      //从session中获取token
        int id = xinXiSouSuoBean.getId();
        Map<String, Object> map = new HashMap<>();
        map.put("returnCode", -1);
        map.put("msg", "没有权限");
        try {
            if (id == did && xinXiSouSuoBean.getToken() != null && xinXiSouSuoBean.getToken().equals(token)) {          //如果档案id和前台传过来的id相同，则如下组装map
                map.put("returnCode", 200);
                map.put("msg", "正常请求");
                List<XinXiBean> list = messageService.xinXiShouCangSouSuo(xinXiSouSuoBean);//获取该用户的按条件搜索出来的收藏的信息
                ArrayList arrayList = zuZhuang(list, xinXiSouSuoBean.getId(), request.getRequestURI());   // //传入获取的信息，登陆者档案id，和前端访问路径uri，组装ArrayList
                map.put("data", arrayList);
            } else {                        //如果该用户档案id和前台传来的id不同，则返回错误信息；
                map.put("returnCode", 408);
                map.put("msg", "访问超时，请重新登陆");
            }
        } catch (Exception e) {
//            e.printStackTrace();
        }
//

        return map;
    }

    //调用此接口，返回该用户所有草稿箱信息
    @RequestMapping(value = {"/xinxicaogaoxiang"}, produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> xinXiCaoGaoXiang(@RequestBody IdBean idBean, HttpServletRequest request) {
        Long did = (Long) request.getSession().getAttribute(SessionKey.DANGANID);  //从session中获取登陆者的id
        String token = (String) request.getSession().getAttribute(SessionKey.TOKEN);      //从session中获取token
        int id = idBean.getId();
        Map<String, Object> map = new HashMap<>();
        map.put("returnCode", -1);
        map.put("msg", "没有权限");
        try {
            if (id == did && idBean.getToken() != null && idBean.getToken().equals(token)) {          //如果后台验证用的档案id和前台传过来的id相同，则如下组装map
                map.put("returnCode", 200);
                map.put("msg", "正常请求");
                List<XinXiBean> list = messageService.xinXiCaoGaoXiang(idBean);//获取该用户的草稿箱的信息
                ArrayList arrayList = zuZhuang(list, idBean.getId(), request.getRequestURI());   // //传入获取的信息，登陆者档案id，和前端访问路径uri，组装ArrayList
                map.put("data", arrayList);
            } else {                        //如果该用户档案id和前台传来的id不同，则返回错误信息；
                map.put("returnCode", 408);
                map.put("msg", "访问超时，请重新登陆");
            }
        } catch (Exception e) {
//            e.printStackTrace();
        }
//

        return map;
    }

    //调用此接口，删除草稿箱的信件
    @RequestMapping(value = {"/caogaoxiangshanchu"}, produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> caoGaoXiangShanChu(@RequestBody XinXiShanChuBean xinXiShanChuBean, HttpServletRequest request) {
        Long did = (Long) request.getSession().getAttribute(SessionKey.DANGANID);  //从session中获取登陆者的id
        String token = (String) request.getSession().getAttribute(SessionKey.TOKEN);      //从session中获取token
        int id = xinXiShanChuBean.getId();
        Map<String, Object> map = new HashMap<>();
        map.put("returnCode", -1);
        map.put("msg", "没有权限");
        try {
            if (id == did && xinXiShanChuBean.getToken() != null && xinXiShanChuBean.getToken().equals(token)) {          //如果后台验证用的档案id和前台传过来的id相同，则如下组装map
                map.put("returnCode", 200);
                map.put("msg", "正常请求");
                messageService.caoGaoXiangShanChu(xinXiShanChuBean);//获取该用户的草稿箱的信息
            } else {                        //如果该用户档案id和前台传来的id不同，则返回错误信息；
                map.put("returnCode", 408);
                map.put("msg", "访问超时，请重新登陆");
            }
        } catch (Exception e) {
//            e.printStackTrace();
        }

//

        return map;
    }

    //调用此接口，按条件搜索草稿箱内容
    @RequestMapping(value = {"/xinxicaogaoxiangsousuo"}, produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> xinXiCaoGaoXiangSouSuo(@RequestBody XinXiSouSuoBean xinXiSouSuoBean, HttpServletRequest request) {
        Long did = (Long) request.getSession().getAttribute(SessionKey.DANGANID);  //从session中获取登陆者的id
        String token = (String) request.getSession().getAttribute(SessionKey.TOKEN);      //从session中获取token
        int id = xinXiSouSuoBean.getId();
        Map<String, Object> map = new HashMap<>();
        map.put("returnCode", -1);
        map.put("msg", "没有权限");
        try {
            if (id == did && xinXiSouSuoBean.getToken() != null && xinXiSouSuoBean.getToken().equals(token)) {          //如果后台验证用的档案id和前台传过来的id相同，则如下组装map
                map.put("returnCode", 200);
                map.put("msg", "正常请求");
                List<XinXiBean> list = messageService.xinXiCaoGaoXiangSouSuo(xinXiSouSuoBean);//获取该用户的按条件搜索的草稿箱的信息
                ArrayList arrayList = zuZhuang(list, xinXiSouSuoBean.getId(), request.getRequestURI());   //传入获取的信息，登陆者档案id，和前端访问路径uri，组装ArrayList，
                map.put("data", arrayList);
            } else {                        //如果该用户档案id和前台传来的id不同，则返回错误信息；
                map.put("returnCode", 408);
                map.put("msg", "访问超时，请重新登陆");
            }
        } catch (Exception e) {
//            e.printStackTrace();
        }
//

        return map;
    }

    //调用此接口，将信件放入草稿箱
    @RequestMapping(value = {"/caogao"}, produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> caoGao(@RequestBody XinXiFaSongBean xinXiFaSongBean, HttpServletRequest request) {
        String token = (String) request.getSession().getAttribute(SessionKey.TOKEN);      //从session中获取token
        Long did = (Long) request.getSession().getAttribute(SessionKey.DANGANID);  //从session中获取登陆者的id
        int id = xinXiFaSongBean.getId();
        Map<String, Object> map = new HashMap<>();
        map.put("returnCode", -1);
        map.put("msg", "没有权限");
        try {
            if (id == did && xinXiFaSongBean.getToken() != null && xinXiFaSongBean.getToken().equals(token)) {          //如果档案id和前台传过来的id相同，调用信息发送方法，并如下组装map
                messageService.cunRuCaoGao(xinXiFaSongBean);
                map.put("returnCode", 200);
                map.put("msg", "正常请求");

            } else {                        //如果该用户档案id和前台传来的id不同，则返回错误信息；
                map.put("returnCode", 408);
                map.put("msg", "访问超时，请重新登陆");
            }
        } catch (Exception e) {
//            e.printStackTrace();
        }
//

        return map;
    }

    //获取公司所有人的姓名，工号，档案id
    @RequestMapping(value = {"/shoujianren"}, produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> shouJianRen(@RequestBody IdBean idBean, HttpServletRequest request) {
        Long did = (Long) request.getSession().getAttribute(SessionKey.DANGANID);  //从session中获取登陆者的id
        String token = (String) request.getSession().getAttribute(SessionKey.TOKEN);      //从session中获取token
        int id = idBean.getId();
        Map<String, Object> map = new HashMap<>();
        map.put("returnCode", -1);
        map.put("msg", "没有权限");

        try {
            if (id == did && idBean.getToken() != null && idBean.getToken().equals(token)) {          //如果档案id和前台传过来的id相同，调用信息发送方法，并如下组装map
                List<ShouJianRenBean> list = messageService.selectShouJianRen(idBean);  //以bean对象形式返回所有收件人的姓名，工号，档案id
                map.put("returnCode", 200);
                map.put("msg", "正常请求");
                map.put("data", list);

            } else {                        //如果该用户档案id和前台传来的id不同，则返回错误信息；
                map.put("returnCode", 408);
                map.put("msg", "访问超时，请重新登陆");
            }
        } catch (Exception e) {
//            e.printStackTrace();
        }
//

        return map;
    }


    @RequestMapping(value = {"/caogaoxiangfasong"}, produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> caoGaoXiangFaSong(@RequestBody CaoGaoFaSongBean caoGaoFaSongBean, HttpServletRequest request) {
        Long did = (Long) request.getSession().getAttribute(SessionKey.DANGANID);  //从session中获取登陆者的id
        String token = (String) request.getSession().getAttribute(SessionKey.TOKEN);      //从session中获取token
        int id = caoGaoFaSongBean.getId();
        Map<String, Object> map = new HashMap<>();
        map.put("returnCode", -1);
        map.put("msg", "没有权限");
        try {
            if (id == did && caoGaoFaSongBean.getToken() != null && caoGaoFaSongBean.getToken().equals(token)) {          //如果档案id和前台传过来的id相同，调用信息发送方法，并如下组装map
                messageService.faSongCaoGao(caoGaoFaSongBean, request.getSession());   //
                map.put("returnCode", 200);
                map.put("msg", "正常请求");

            } else {                        //如果该用户档案id和前台传来的id不同，则返回错误信息；
                map.put("returnCode", 408);
                map.put("msg", "访问超时，请重新登陆");
            }
        } catch (Exception e) {
//            e.printStackTrace();
        }
//

        return map;
    }

    @RequestMapping(value = {"/caogaoxiangxiangqing"}, produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> caoGaoXiangXiangQing(@RequestBody XiangXiXinXiBean xiangXiXinXiBean, HttpServletRequest request) {
        Long did = (Long) request.getSession().getAttribute(SessionKey.DANGANID);  //从session中获取登陆者的id
        String token = (String) request.getSession().getAttribute(SessionKey.TOKEN);      //从session中获取token
        int id = xiangXiXinXiBean.getId();
        Map<String, Object> map = new HashMap<>();
        map.put("returnCode", -1);
        map.put("msg", "没有权限");
        try {
            if (id == did && xiangXiXinXiBean.getToken() != null && xiangXiXinXiBean.getToken().equals(token)) {          //如果档案id和前台传过来的id相同，调用信息发送方法，并如下组装map
                XinXiBean xinXiBean = messageService.caoGaoXiangXiangQing(xiangXiXinXiBean);   //
                map.put("returnCode", 200);
                map.put("msg", "正常请求");
                map.put("data", xinXiBean);
            } else {                        //如果该用户档案id和前台传来的id不同，则返回错误信息；
                map.put("returnCode", 408);
                map.put("msg", "访问超时，请重新登陆");
            }

        } catch (Exception e) {
//            e.printStackTrace();
        }
//
        return map;
    }


    @RequestMapping(value = {"/fileupload"}, method = RequestMethod.POST)
    @ResponseBody
    public String executeImport(@RequestBody MultipartFile articleFile, HttpServletRequest request, IdBean idBean) throws Exception {
        if (articleFile == null || articleFile.getSize() > 10485760) {  // 如果前端传入文件失败或者文件大小超过设置大小，那么直接返回null
            return null;
        }
        String luJing = FileUpload.executeImport(articleFile, request);
        request.getSession().setAttribute("luJing", luJing);
        return "true";
//
//        String originalFilename = articleFile.getOriginalFilename();
//        String luJingHouZui = System.currentTimeMillis() + originalFilename.substring(originalFilename.lastIndexOf("."));// 文件存储路径后缀
//        String jueDuiLuJing = request.getSession().getServletContext().getRealPath(File.separator) + File.separator;  // 得到文件绝对路径
//        File diroffice = new File(jueDuiLuJing + "office");
//        if (!diroffice.exists() && !diroffice.isDirectory()) {  // 如果offie目录不存在
//            diroffice.mkdir();  // 创建目录
//        }
//        File dirimg = new File(jueDuiLuJing + "img");
//        if (!dirimg.exists() && !dirimg.isDirectory()) {  // 如果img目录不存在
//            dirimg.mkdir();  // 创建目录
//        }
//        String luJingQianZui = "img" + File.separator;  // 图片上传路径前缀
//        String luJing = "/img/" + luJingHouZui;
//        if (originalFilename != null && (originalFilename.endsWith(".doc") || originalFilename.endsWith(".docx") ||
//                originalFilename.endsWith(".xls") || originalFilename.endsWith(".xlsx"))) {
//            luJingQianZui = "office" + File.separator;  // office文件上传路径前缀
//            luJing = "/office/" + luJingHouZui;  // 得到文件路径
//        }
//        File file = new File(jueDuiLuJing + luJingQianZui + luJingHouZui);  // 组装上传路径得到文件路径
//        request.getSession().setAttribute("luJing",jueDuiLuJing + luJingQianZui + luJingHouZui);
//        try {
//            articleFile.transferTo(file);      // 上传
//            return luJing;   // 上传成功return地址路径
//        } catch (Exception e) {
////            e.printStackTrace();
//            return null;   // 文件上传失败返回null
//        }
    }


    //  调用此方法，根据请求路径，组装data
    public ArrayList zuZhuang(List<XinXiBean> list, int id, String uri) {
        ArrayList arrayList = new ArrayList();
        if (uri.equals("/msg/dingshihuoqu") || uri.equals("/msg/sousuo")) {
            for (XinXiBean xinXiBean : list
            ) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", xinXiBean.getId());
                map.put("faXinId", xinXiBean.getFaXinId());
                map.put("shouXinId", xinXiBean.getShouXinId());
                map.put("xinXiBiaoTi", xinXiBean.getXinXiBiaoTi());
                map.put("xinXiNeiRong", xinXiBean.getXinXiNeiRong());
                map.put("xinXiShiJian", xinXiBean.getXinXiShiJian().toString());
                map.put("fuJianDiZhi", xinXiBean.getFuJianDiZhi());
                map.put("faSongZhuangTai", xinXiBean.getFaSongZhuangTai());
                map.put("shouCangZhuangTai", xinXiBean.getShouJianRenShouCangZhuangTai());
                map.put("yiDuZhuangTai", xinXiBean.getYiDuZhuangTai());
                map.put("faJianRenXingMing", xinXiBean.getFaJianRenXingMing());
                map.put("shouJianRenXingMing", xinXiBean.getShouJianRenXingMing());
                arrayList.add(map);
            }
        } else if (uri.equals("/msg/yifaxinxi") || uri.equals("/msg/yifaxinxisousuo")) {
            for (XinXiBean xinXiBean : list
            ) {

                Map<String, Object> map = new HashMap<>();
                map.put("id", xinXiBean.getId());
                map.put("faXinId", xinXiBean.getFaXinId());
                map.put("xinXiBiaoTi", xinXiBean.getXinXiBiaoTi());
                map.put("shouXinId", xinXiBean.getShouXinId());
                map.put("xinXiNeiRong", xinXiBean.getXinXiNeiRong());
                map.put("xinXiShiJian", xinXiBean.getXinXiShiJian().toString());
                map.put("fuJianDiZhi", xinXiBean.getFuJianDiZhi());
                map.put("shouCangZhuangTai", xinXiBean.getFaJianRenShouCangZhuangTai());
                map.put("faJianRenXingMing", xinXiBean.getFaJianRenXingMing());
                map.put("shouJianRenXingMing", xinXiBean.getShouJianRenXingMing());
                arrayList.add(map);
            }
        } else if (uri.equals("/msg/xinxishoucang") || uri.equals("/msg/xinxishoucangsousuo")) {
            for (XinXiBean xinXiBean : list
            ) {

                Map<String, Object> map = new HashMap<>();
                map.put("id", xinXiBean.getId());
                map.put("faXinId", xinXiBean.getFaXinId());
                map.put("shouXinId", xinXiBean.getShouXinId());
                map.put("faJianRenXingMing", xinXiBean.getFaJianRenXingMing());
                map.put("shouJianRenXingMing", xinXiBean.getShouJianRenXingMing());
                map.put("xinXiBiaoTi", xinXiBean.getXinXiBiaoTi());
                map.put("xinXiNeiRong", xinXiBean.getXinXiNeiRong());
                if (xinXiBean.getXinXiShiJian() != null) {
                    map.put("xinXiShiJian", xinXiBean.getXinXiShiJian().toString());
                }
                map.put("fuJianDiZhi", xinXiBean.getFuJianDiZhi());
                map.put("faSongZhuangTai", xinXiBean.getFaSongZhuangTai());
                if (id == xinXiBean.getShouXinId()) {
                    map.put("shouCangZhuangTai", xinXiBean.getShouJianRenShouCangZhuangTai());
                } else {
                    map.put("shouCangZhuangTai", xinXiBean.getFaJianRenShouCangZhuangTai());
                }

                if (id == xinXiBean.getShouXinId()) {
                    map.put("yiDuZhuangTai", xinXiBean.getYiDuZhuangTai());
                }

                arrayList.add(map);
            }
        } else if (uri.equals("/msg/xinxicaogaoxiang") || uri.equals("/msg/xinxicaogaoxiangsousuo")) {
            for (XinXiBean xinXiBean : list
            ) {

                Map<String, Object> map = new HashMap<>();
                map.put("id", xinXiBean.getId());
                map.put("faXinId", xinXiBean.getFaXinId());
                map.put("shouXinId", xinXiBean.getShouXinId());
                map.put("xinXiBiaoTi", xinXiBean.getXinXiBiaoTi());
                map.put("xinXiNeiRong", xinXiBean.getXinXiNeiRong());
                map.put("fuJianDiZhi", xinXiBean.getFuJianDiZhi());
                map.put("shouCangZhuangTai", xinXiBean.getFaJianRenShouCangZhuangTai());
                map.put("faJianRenXingMing", xinXiBean.getFaJianRenXingMing());
                map.put("shouJianRenXingMing", xinXiBean.getShouJianRenXingMing());
                arrayList.add(map);
            }
        }

        return arrayList;
    }

    /**
     * 这个方法用来下载
     *
     * @param fileName 下载地址
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = {"/filedownload"})
    @ResponseBody
    public void download(String fileName, HttpServletRequest request, HttpServletResponse response) {
        // 注意，在实际开发中，尽量把服务器的下载目录通过filter屏蔽掉，因为在没有屏蔽之前，用户可以通过手动请求路径获取文件信息
//        String fileName = request.getParameter("fileName");
//        String userID = request.getParameter("userID");
        if(fileName != null && !fileName.trim().equals("")) {
            // 查找服务器物理路径所在位置
            String muLiLuJing = request.getServletContext().getRealPath(File.separator);
            // E:\IdeaProjects\zonghe\out\artifacts\zonghe_war_exploded\myfile\PLC_Server.rar
            File file = new File(muLiLuJing + File.separator +
                    fileName);
//            System.out.println("file=" + file);
            if(file.exists()){
                try {
                    FileInputStream fis = new FileInputStream(file);
                    String filename = URLEncoder.encode(file.getName(),"utf-8"); // 解决中文文件名下载后乱码的问题
                    byte[] b = new byte[fis.available()];
                    fis.read(b);
                    response.setCharacterEncoding("UTF-8");
                    response.setHeader("Content-Disposition","attachment; filename=" + filename + "");
                    //获取响应报文输出流对象
                    ServletOutputStream out = response.getOutputStream();
                    //输出
                    out.write(b);
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
