package cn.com.yuyang.controller;

import cn.com.yuyang.bean.*;
import cn.com.yuyang.service.MessageService;
import cn.com.yuyang.util.SessionKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/msg")
public class MessageController {
    private MessageService messageService;
    @Autowired
    public MessageController(MessageService messageService){
        this.messageService = messageService;
    }
    //调用此接口，获取用户收件箱所有未删除信息
    @RequestMapping(value = {"/dingshihuoqu"})
    @ResponseBody
    public Map<String, Object> dingShiHuoQu(String method,@RequestBody IdBean idBean, HttpServletRequest request) {
        Integer did=(Integer) request.getSession().getAttribute(SessionKey.DANGANID);  //从session中获取登陆者的id
        String uri=request.getRequestURI();
        int id = idBean.getId();
        Map<String, Object> map = new HashMap<>();
//        if(id==did){          //如果档案id和前台传过来的id相同，则如下组装map
            map.put("returncode", 200);
            map.put("msg","正常请求");


            List<XinXiBean> list = messageService.selectAll(id);//获取该用户的所有信息
           ArrayList arrayList = zuZhuang(list,id,uri); //传入获取的信息，登陆者档案id，和前端访问路径uri，组装ArrayList
            map.put("data",arrayList);
//        }
//        else {                        //如果该用户档案id和前台传来的id不同，则返回错误信息；
//            map.put("returncode", -1);
//            map.put("msg","访问超时，请重新登陆");
//        }

        return map;
    }

    //调用此接口，返回用户收件箱满足搜索条件的未删除信息
    @RequestMapping(value = {"/sousuo"})
    @ResponseBody
    public Map<String, Object> souSuo(@RequestBody XinXiSouSuoBean xinXiSouSuoBean, HttpServletRequest request) {
        Integer did=(Integer) request.getSession().getAttribute(SessionKey.DANGANID);  //从session中获取登陆者的id
        int id = xinXiSouSuoBean.getId();
        String uri=request.getRequestURI();
        Map<String, Object> map = new HashMap<>();
//        if(id==did){          //如果档案id和前台传过来的id相同，则如下组装map
        map.put("returncode", 200);
        map.put("msg","正常请求");
        List<XinXiBean> list = messageService.selectByParam(xinXiSouSuoBean);//获取该用户按条件搜索的信息
        ArrayList arrayList = zuZhuang(list,id,uri);   //传入获取的信息，登陆者档案id，和前端访问路径uri，组装ArrayList
        map.put("data",arrayList);
//        }
//        else {                        //如果该用户档案id和前台传来的id不同，则返回错误信息；
//            map.put("returncode", -1);
//            map.put("msg","访问超时，请重新登陆");
//        }

        return map;
    }
    //调用此接口，获取该条信息的详细情况
    @RequestMapping(value = {"/xiangxixinxi"})
    @ResponseBody
    public Map<String, Object> xiangXiXinXi(@RequestBody XiangXiXinXiBean xiangXiXinXiBean, HttpServletRequest request) {
        Integer did=(Integer) request.getSession().getAttribute(SessionKey.DANGANID);  //从session中获取登陆者的id
        int id = xiangXiXinXiBean.getId() ;
        Map<String, Object> map = new HashMap<>();
//        if(id==did){          //如果档案id和前台传过来的id相同，则如下组装map
        map.put("returncode", 200);
        map.put("msg","正常请求");
        XinXiBean xinXiBean = messageService.selectXiangQing(xiangXiXinXiBean);//获取该条信息的所有信息
        Map map1 = new HashMap();
        map1.put("id",xinXiBean.getId());
        map1.put("faXinId",xinXiBean.getFaXinId());
        map1.put("faJianRenXingMing",xinXiBean.getFaJianRenXingMing());
        map1.put("shouXinId",xinXiBean.getShouXinId());
        map1.put("shouJianRenXingMing",xinXiBean.getShouJianRenXingMing());
        map1.put("xinXiBiaoTi",xinXiBean.getXinXiBiaoTi());
        map1.put("xinXiNeiRong",xinXiBean.getXinXiNeiRong());
        map1.put("xinXiShiJian",xinXiBean.getXinXiShiJian().toString());
        map1.put("fuJianDiZhi",xinXiBean.getFuJianDiZhi());
        map1.put("faSongZhuangTai",xinXiBean.getFaSongZhuangTai());
        map1.put("shouCangZhuangTai",xinXiBean.getShouJianRenShouCangZhuangTai());
        messageService.yiDu(xiangXiXinXiBean);
        map.put("data",map1);
//        }
//        else {                        //如果该用户档案id和前台传来的id不同，则返回错误信息；
//            map.put("returncode", -1);
//            map.put("msg","访问超时，请重新登陆");
//        }

        return map;
    }

    //调用此接口，从用户收件箱或者发件箱批量或者单独删除信息
    @RequestMapping(value = {"/shanchu"})
    @ResponseBody
    public Map<String, Object> shanChu(@RequestBody XinXiShanChuBean xinXiShanChuBean, HttpServletRequest request) {
        Integer did=(Integer) request.getSession().getAttribute(SessionKey.DANGANID);  //从session中获取登陆者的id
        int id = xinXiShanChuBean.getId() ;
        Map<String, Object> map = new HashMap<>();
//        if(id==did){          //如果档案id和前台传过来的id相同，则如下组装map
        messageService.delectXinXi(xinXiShanChuBean);            //根据前台传来的登录人档案id，信息的id，修改信息的状态为0
        map.put("returncode", 200);
        map.put("msg","正常删除");

//        }
//        else {                        //如果该用户档案id和前台传来的id不同，则返回错误信息；
//            map.put("returncode", -1);
//            map.put("msg","访问超时，请重新登陆");
//        }

        return map;
    }
    //调用此接口，收藏或者取消收藏此条信息
    @RequestMapping(value = {"/shoucang"})
    @ResponseBody
    public Map<String, Object> shouCang(@RequestBody XinXiShouCangBean xinXiShouCangBean, HttpServletRequest request) {
        Integer did=(Integer) request.getSession().getAttribute(SessionKey.DANGANID);  //从session中获取登陆者的id
        int id = xinXiShouCangBean.getId() ;
        Map<String, Object> map = new HashMap<>();
//        if(id==did){          //如果档案id和前台传过来的id相同，则如下组装map
        messageService.shouCangXinXi(xinXiShouCangBean);    //调用收藏方法，修改收藏状态
        map.put("returncode", 200);
        map.put("msg","正常处理收藏请求");
//        XinXiBean xinXiBean = messageService.delectXinXi(xinXiShanChuBean);//获取该用户的所有信息

//        }
//        else {                        //如果该用户档案id和前台传来的id不同，则返回错误信息；
//            map.put("returncode", -1);
//            map.put("msg","访问超时，请重新登陆");
//        }

        return map;
    }
    //调用此接口，回复信息
    @RequestMapping(value = {"/huifu"})
    @ResponseBody
    public Map<String, Object> huiFu(@RequestBody XinXiFaSongBean xinXiFaSongBean, HttpServletRequest request) {
        Integer did=(Integer) request.getSession().getAttribute(SessionKey.DANGANID);  //从session中获取登陆者的id
        Map map=new HashMap();
//        if(id==did){          //如果档案id和前台传过来的id相同，调用信息发送方法，并如下组装map
        messageService.faSongXinXi(xinXiFaSongBean,request.getSession());
        map.put("returncode", 200);
        map.put("msg","正常请求");

//        }
//        else {                        //如果该用户档案id和前台传来的id不同，则返回错误信息；
//            map.put("returncode", -1);
//            map.put("msg","访问超时，请重新登陆");
//        }

        return map;
    }

    //调用此接口，获取用户所有已发送的未删除信息
    @RequestMapping(value = {"/yifaxinxi"})
    @ResponseBody
    public Map<String, Object> yiFaXinXi(@RequestBody IdBean idBean, HttpServletRequest request) {
        Integer did=(Integer) request.getSession().getAttribute(SessionKey.DANGANID);  //从session中获取登陆者的id
        Map<String, Object> map = new HashMap<>();
//        if(id==did){          //如果档案id和前台传过来的id相同，则如下组装map
        map.put("returncode", 200);
        map.put("msg","正常请求");
        List<XinXiBean> list = messageService.selectYiFaXinXi(idBean);//获取该用户的所有已发送的信息
        ArrayList arrayList = zuZhuang(list,idBean.getId(),request.getRequestURI());   //传入获取的信息，登陆者档案id，和前端访问路径uri，组装ArrayList
        map.put("data",arrayList);
//        }
//        else {                        //如果该用户档案id和前台传来的id不同，则返回错误信息；
//            map.put("returncode", -1);
//            map.put("msg","访问超时，请重新登陆");
//        }

        return map;
    }

    //访问此接口，返回满足搜索条件的所有已发的未删除信息
    @RequestMapping(value = {"/yifaxinxisousuo"})
    @ResponseBody
    public Map<String, Object> yiFaXinXiSouSuo(@RequestBody XinXiSouSuoBean xinXiSouSuoBean, HttpServletRequest request) {
        Integer did=(Integer) request.getSession().getAttribute(SessionKey.DANGANID);  //从session中获取登陆者的id
        Map<String, Object> map = new HashMap<>();
//        if(id==did){          //如果档案id和前台传过来的id相同，则如下组装map
        map.put("returncode", 200);
        map.put("msg","正常请求");
        List<XinXiBean> list = messageService.yiFaXinXiSouSuo(xinXiSouSuoBean);//获取该用户的按条件搜索出来的已发送的信息
        ArrayList arrayList = zuZhuang(list,xinXiSouSuoBean.getId(),request.getRequestURI());   //传入获取的信息，登陆者档案id，和前端访问路径uri，组装ArrayList
        map.put("data",arrayList);
//        }
//        else {                        //如果该用户档案id和前台传来的id不同，则返回错误信息；
//            map.put("returncode", -1);
//            map.put("msg","访问超时，请重新登陆");
//        }

        return map;
    }
    //调用此接口，返回该用户所有收藏的未删除信息
    @RequestMapping(value = {"/xinxishoucang"})
    @ResponseBody
    public Map<String, Object> xinXiShouCang(@RequestBody IdBean idBean, HttpServletRequest request) {
        Integer did=(Integer) request.getSession().getAttribute(SessionKey.DANGANID);  //从session中获取登陆者的id
        Map<String, Object> map = new HashMap<>();
//        if(id==did){          //如果档案id和前台传过来的id相同，则如下组装map
        map.put("returncode", 200);
        map.put("msg","正常请求");
        List<XinXiBean> list = messageService.selectXinXiShouCang(idBean);//获取该用户收藏的信息
        ArrayList arrayList = zuZhuang(list,idBean.getId(),request.getRequestURI());   // //传入获取的信息，登陆者档案id，和前端访问路径uri，组装ArrayList
        map.put("data",arrayList);
//        }
//        else {                        //如果该用户档案id和前台传来的id不同，则返回错误信息；
//            map.put("returncode", -1);
//            map.put("msg","访问超时，请重新登陆");
//        }

        return map;
    }

    //调用此接口，按条件搜索该用户收藏的未删除信息，并返回
    @RequestMapping(value = {"/xinxishoucangsousuo"})
    @ResponseBody
    public Map<String, Object> xinXiShouCangSouSuo(@RequestBody XinXiSouSuoBean xinXiSouSuoBean, HttpServletRequest request) {
        Integer did=(Integer) request.getSession().getAttribute(SessionKey.DANGANID);  //从session中获取登陆者的id
        Map<String, Object> map = new HashMap<>();
//        if(id==did){          //如果档案id和前台传过来的id相同，则如下组装map
        map.put("returncode", 200);
        map.put("msg","正常请求");
        List<XinXiBean> list = messageService.xinXiShouCangSouSuo(xinXiSouSuoBean);//获取该用户的按条件搜索出来的收藏的信息
        ArrayList arrayList = zuZhuang(list,xinXiSouSuoBean.getId(),request.getRequestURI());   // //传入获取的信息，登陆者档案id，和前端访问路径uri，组装ArrayList
        map.put("data",arrayList);
//        }
//        else {                        //如果该用户档案id和前台传来的id不同，则返回错误信息；
//            map.put("returncode", -1);
//            map.put("msg","访问超时，请重新登陆");
//        }

        return map;
    }
    //调用此接口，返回该用户所有草稿箱信息
    @RequestMapping(value = {"/xinxicaogaoxiang"})
    @ResponseBody
    public Map<String, Object> xinXiCaoGaoXiang(@RequestBody IdBean idBean, HttpServletRequest request) {
        Integer did=(Integer) request.getSession().getAttribute(SessionKey.DANGANID);  //从session中获取登陆者的id
        Map<String, Object> map = new HashMap<>();
//        if(id==did){          //如果后台验证用的档案id和前台传过来的id相同，则如下组装map
        map.put("returncode", 200);
        map.put("msg","正常请求");
        List<XinXiBean> list = messageService.xinXiCaoGaoXiang(idBean);//获取该用户的草稿箱的信息
        ArrayList arrayList = zuZhuang(list,idBean.getId(),request.getRequestURI());   // //传入获取的信息，登陆者档案id，和前端访问路径uri，组装ArrayList
        map.put("data",arrayList);
//        }
//        else {                        //如果该用户档案id和前台传来的id不同，则返回错误信息；
//            map.put("returncode", -1);
//            map.put("msg","访问超时，请重新登陆");
//        }

        return map;
    }
    //调用此方法，删除草稿箱的信件
    @RequestMapping(value = {"/caogaoxiangshanchu"})
    @ResponseBody
    public Map<String, Object> caoGaoXiangShanChu(@RequestBody XinXiShanChuBean xinXiShanChuBean, HttpServletRequest request) {
        Integer did=(Integer) request.getSession().getAttribute(SessionKey.DANGANID);  //从session中获取登陆者的id
        Map<String, Object> map = new HashMap<>();
//        if(id==did){          //如果后台验证用的档案id和前台传过来的id相同，则如下组装map
        map.put("returncode", 200);
        map.put("msg","正常请求");
        messageService.caoGaoXiangShanChu(xinXiShanChuBean);//获取该用户的草稿箱的信息
//        }
//        else {                        //如果该用户档案id和前台传来的id不同，则返回错误信息；
//            map.put("returncode", -1);
//            map.put("msg","访问超时，请重新登陆");
//        }

        return map;
    }

    //调用此方法，按条件搜索草稿箱内容
    @RequestMapping(value = {"/xinxicaogaoxiangsousuo"})
    @ResponseBody
    public Map<String, Object> xinXiCaoGaoXiangSouSuo(@RequestBody XinXiSouSuoBean xinXiSouSuoBean, HttpServletRequest request) {
        Integer did=(Integer) request.getSession().getAttribute(SessionKey.DANGANID);  //从session中获取登陆者的id
        Map<String, Object> map = new HashMap<>();
//        if(id==did){          //如果后台验证用的档案id和前台传过来的id相同，则如下组装map
        map.put("returncode", 200);
        map.put("msg","正常请求");
        List<XinXiBean> list = messageService.xinXiCaoGaoXiangSouSuo(xinXiSouSuoBean);//获取该用户的按条件搜索的草稿箱的信息
        ArrayList arrayList = zuZhuang(list,xinXiSouSuoBean.getId(),request.getRequestURI());   //传入获取的信息，登陆者档案id，和前端访问路径uri，组装ArrayList，
        map.put("data",arrayList);
//        }
//        else {                        //如果该用户档案id和前台传来的id不同，则返回错误信息；
//            map.put("returncode", -1);
//            map.put("msg","访问超时，请重新登陆");
//        }

        return map;
    }








    @RequestMapping(value = {"/fileupload"}, method = RequestMethod.POST)
    @ResponseBody
    public String executeImport(MultipartFile articleFile,HttpServletRequest request) throws Exception {
        String originalFilename = articleFile.getOriginalFilename();
        System.out.println("name      ="+originalFilename);
        String luJing = "C:/Users/macan/IdeaProjects/yuyang/src/main/webapp/img/";  // 图片上传路径
        if (originalFilename != null || originalFilename.endsWith(".doc") || originalFilename.endsWith(".docx") ||
                originalFilename.endsWith(".xls") || originalFilename.endsWith(".xlsx")) {
            luJing = "C:/Users/macan/IdeaProjects/yuyang/src/main/webapp/office/";  // office文件上传路径
        }
        String pathname ="C:/" + System.currentTimeMillis() + originalFilename.substring(originalFilename.indexOf("."));
        request.getSession().setAttribute("luJing",pathname);
        File file = new File(pathname);
        try {
            articleFile.transferTo(file);
            return "true";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "false";
    }


    //  调用此方法，根据请求路径，组装data
    public ArrayList zuZhuang(List<XinXiBean> list,int id,String uri){
        ArrayList arrayList = new ArrayList();
        if (uri.equals("/msg/dingshihuoqu") ||uri.equals("/msg/sousuo")){
            for (XinXiBean xinXiBean:list
            ) {
                Map map = new HashMap();
                map.put("id",xinXiBean.getId());
                map.put("faXinId",xinXiBean.getFaXinId());
                map.put("shouXinId",xinXiBean.getShouXinId());
                map.put("xinXiBiaoTi",xinXiBean.getXinXiBiaoTi());
                map.put("xinXiNeiRong",xinXiBean.getXinXiNeiRong());
                map.put("xinXiShiJian",xinXiBean.getXinXiShiJian().toString());
                map.put("fuJianDiZhi",xinXiBean.getFuJianDiZhi());
                map.put("faSongZhuangTai",xinXiBean.getFaSongZhuangTai());
                map.put("shouCangZhuangTai",xinXiBean.getShouJianRenShouCangZhuangTai());
                map.put("yiDuZhuangTai", xinXiBean.getYiDuZhuangTai());
                map.put("faJianRenXingMing",xinXiBean.getFaJianRenXingMing());
                map.put("shouJianRenXingMing",xinXiBean.getShouJianRenXingMing());
                arrayList.add(map);
            }
        }
        else if (uri.equals("/msg/yifaxinxi")||uri.equals("yifaxinxisousuo")){
            for (XinXiBean xinXiBean:list
            ) {

                Map map = new HashMap();
                map.put("id",xinXiBean.getId());
                map.put("faXinId",xinXiBean.getFaXinId());
                map.put("xinXiBiaoTi",xinXiBean.getXinXiBiaoTi());
                map.put("shouXinId",xinXiBean.getShouXinId());
                map.put("xinXiNeiRong",xinXiBean.getXinXiNeiRong());
                map.put("xinXiShiJian",xinXiBean.getXinXiShiJian().toString());
                map.put("fuJianDiZhi",xinXiBean.getFuJianDiZhi());
                map.put("shouCangZhuangTai",xinXiBean.getFaJianRenShouCangZhuangTai());
                map.put("faJianRenXingMing",xinXiBean.getFaJianRenXingMing());
                map.put("shouJianRenXingMing",xinXiBean.getShouJianRenXingMing());
                arrayList.add(map);
            }
        }
        else if (uri.equals("/msg/xinxishoucang")||uri.equals("/msg/xinxishoucangsousuo")){
            for (XinXiBean xinXiBean:list
            ) {

                Map map = new HashMap();
                map.put("id",xinXiBean.getId());
                map.put("faXinId",xinXiBean.getFaXinId());
                map.put("shouXinId",xinXiBean.getShouXinId());
                map.put("faJianRenXingMing",xinXiBean.getFaJianRenXingMing());
                map.put("shouJianRenXingMing",xinXiBean.getShouJianRenXingMing());
                map.put("xinXiBiaoTi",xinXiBean.getXinXiBiaoTi());
                map.put("xinXiNeiRong",xinXiBean.getXinXiNeiRong());
                map.put("xinXiShiJian",xinXiBean.getXinXiShiJian().toString());
                map.put("fuJianDiZhi",xinXiBean.getFuJianDiZhi());
                map.put("faSongZhuangTai",xinXiBean.getFaSongZhuangTai());
                if (id==xinXiBean.getShouXinId()){
                    map.put("yiDuZhuangTai",xinXiBean.getYiDuZhuangTai());
                }

                arrayList.add(map);
            }
        }
        else if (uri.equals("/msg/xinxicaogaoxiang")||uri.equals("/msg/xinxicaogaoxiangsousuo")){
            for (XinXiBean xinXiBean:list
            ) {

                Map map = new HashMap();
                map.put("id",xinXiBean.getId());
                map.put("faXinId",xinXiBean.getFaXinId());
                map.put("shouXinId",xinXiBean.getShouXinId());
                map.put("xinXiBiaoTi",xinXiBean.getXinXiBiaoTi());
                map.put("xinXiNeiRong",xinXiBean.getXinXiNeiRong());
                map.put("xinXiShiJian",xinXiBean.getXinXiShiJian().toString());
                map.put("fuJianDiZhi",xinXiBean.getFuJianDiZhi());
                map.put("shouCangZhuangTai",xinXiBean.getFaJianRenShouCangZhuangTai());
                map.put("faJianRenXingMing",xinXiBean.getFaJianRenXingMing());
                map.put("shouJianRenXingMing",xinXiBean.getShouJianRenXingMing());
                arrayList.add(map);
            }
        }

        return arrayList;
    }

}
