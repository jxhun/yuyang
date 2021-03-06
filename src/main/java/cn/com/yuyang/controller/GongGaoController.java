package cn.com.yuyang.controller;

import cn.com.yuyang.bean.GongGaoBean;
import cn.com.yuyang.pojo.Bumen;
import cn.com.yuyang.pojo.Gonggao;
import cn.com.yuyang.pojo.Zhiwubiao;
import cn.com.yuyang.service.GongGaoService;
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
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;
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


    private final GongGaoService gongGaoService;

    @Autowired
    public GongGaoController(GongGaoService gongGaoService) {
        this.gongGaoService = gongGaoService;
    }

    /**
     * 点击公告，查询数据库公告(GongGao)表，查询所有公告，进行分页每页3条公告，公告按照时间先后顺序排序。
     * 注意：前台需要做分页，所以后台返回时，需要返回总条数。
     * 前端传入操作人id
     *
     * @param gongGaoBean 得到前端传入的token
     * @return 成功返回成功的json字符串，map格式，json中存入了返回码
     */
    @RequestMapping(value = {"/seleceAll"}, method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> selectAll(@RequestBody(required = false) GongGaoBean gongGaoBean, HttpServletRequest request) {
        HttpSession session = request.getSession(); // 得到session
        Map<String, Object> returnMap = new HashMap<>();  // 这个map存结果
        String token = (String) session.getAttribute(SessionKey.TOKEN);
        if (gongGaoBean == null || !gongGaoBean.getToken().equals(token)) {  // 如果前端传入的token和后台session中的token不匹配
            returnMap.put("returnCode", 408);
            returnMap.put("msg", "登录超时！");
        } else { // 如果有权限token也能对应
            long buMenId = gongGaoBean.getBuMenId();// 得到登录人到的部门id
            int dangAnId = Integer.parseInt(String.valueOf(request.getSession().getAttribute(SessionKey.DANGANID)));// 得到登录人的档案id
            // 调用查询公告方法，传入登录人的档案id以及他的部门id，他能查看得到他自己发布的和他本部门能查看的公告

//        Integer buMenId = gongGaoService.chaXunBuMenId(gongGaoBean);  // 查询 部门id
            if (buMenId != 0) { // 查询得到的部门id如果为0，那么说明
                gongGaoBean.setBuMenId(buMenId);  // 登录人的部门id存入bean对象
                gongGaoBean.setDangAnId(dangAnId);//登录人的档案id存入bean对象
                List<Gonggao> list = gongGaoService.selectGongGao(gongGaoBean);  // 调用查询公告方法传入 公告bean对象
                returnMap.put("yiFaBuTiaoShu", gongGaoService.selectGongGaoFaBu(gongGaoBean.getDangAnId(), 1));  // 得到已发布的条数,并且存入map
                returnMap.put("weiFaBuTiaoShu", gongGaoService.selectGongGaoFaBu(gongGaoBean.getDangAnId(), 0));  // 得到未发布的条数,并且存入map
                returnMap.put("returnCode", 200);   // 成功状态码200
                returnMap.put("msg", "成功,注意里面的id是每条公告的id，需要隐藏存储在前端每条公告里面，如果用户点击公告需传入这个id和浏览数来修改浏览次数");     // 消息提示成功
                int zongYeShu = list.size() / 3 + 1; // 数据库中查到公告的总条数除以每页条数 + 1 为总页数
                if (list.size() % 3 == 0) {         //  如果总条数刚好是3的倍数
                    zongYeShu = list.size() / 3;   // 总页数就是总条数 / 37
                }
                returnMap.put("ZongYeShu", zongYeShu);
                returnMap.put("data", list);   // data数据中将list封装进去
//
            } else {
                returnMap.put("returnCode", -2);   // 成功状态码200
                returnMap.put("msg", "登录人没有部门，请检查该人员是否入职");     // 消息提示成功
            }
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
    @RequestMapping(value = {"/newInsert"}, method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> newInsert(@RequestBody(required = false) GongGaoBean gongGaoBean, HttpServletRequest request) {


        HttpSession session = request.getSession(); // 得到session
        String token = (String) session.getAttribute(SessionKey.TOKEN);    // 得到token
//        gongGaoBean = new GongGaoBean();
        Map<String, Object> returnMap = new HashMap<>();
        // 如果token不为空,说明用户已经登录,并且前端的token必须和我session的token相同,并且这个用户有发布修改公告的权限
        if (gongGaoBean != null && token != null && token.equals(gongGaoBean.getToken()) &&
                (int) session.getAttribute(SessionKey.FABUXIUGAIGONGGAO) == 1) {
            int dangAnId = Integer.parseInt(String.valueOf(session.getAttribute(SessionKey.DANGANID))); // 得到档案id
            gongGaoBean.setDangAnId(dangAnId);  // 档案id存入公告bean对象
            String fuJian = (String) session.getAttribute(SessionKey.FJDZ); // 得到附件地址
            gongGaoBean.setFuJianDiZhi(fuJian);   // 传入文件地址
            gongGaoService.newInsertGongGao(gongGaoBean);  // 调用新增方法
            returnMap.put("returnCode", 200);   // 成功状态码200
            returnMap.put("msg", "新增成功");     // 消息提示上传成功
            session.removeAttribute(SessionKey.FJDZ); // 用了就清理掉
        } else {
            returnMap.put("returnCode", 408);   // 成功状态码-1
            returnMap.put("msg", "登录状态已超时或者前端未传数据");     // 消息提示如果比对不成功，那么说明要么登录超时，session已到时
        }
        return returnMap;
    }


    /**
     * 这个方法用来查询公告的详情，前端用户点击公告进入详情查看页面，传递公告id，公告当前浏览数到后端
     *
     * @param gongGaoBean 接收前端传入到的参数
     * @param request     用来获取session
     * @return 返回json
     */
    @RequestMapping(value = {"/gongGaoXiangQing"}, method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> gongGaoXiangQing(@RequestBody(required = false) GongGaoBean gongGaoBean, HttpServletRequest request) {
        HttpSession session = request.getSession(); // 得到session
        String token = (String) session.getAttribute(SessionKey.TOKEN);    // 得到token
        Map<String, Object> returnMap = new HashMap<>();
//         如果token不为空,说明用户已经登录,并且前端的token必须和我session的token相同
        if (gongGaoBean != null && token != null && token.equals(gongGaoBean.getToken())) {
            gongGaoService.xiuGaiLiuLanShu(gongGaoBean); // 调用修改浏览数方法，用户点击查看公告详情，那么就直接增加浏览数
            Map<String, Object> map = gongGaoService.xiangQing(gongGaoBean); // 调用查询方法，传入公告bean
            returnMap.put("returnCode", 200);   // 成功状态码200
            returnMap.put("msg", "查询成功，注意，如果查询得到的dangAnId和登录用户的id相同，那么不显示编辑删除按钮，" +
                    "如果buMenMingCheng为null，那么接收人就是全体员工");     // 消息提示上传成功
            returnMap.put("data", map);    // 查询结果存入data
        } else {
            returnMap.put("returnCode", 408);   // 成功状态码-1
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
    @RequestMapping(value = {"/zhuangTai"}, method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> shanChu(@RequestBody(required = false) GongGaoBean gongGaoBean, HttpServletRequest request) {
        HttpSession session = request.getSession(); // 得到session
        Map<String, Object> returnMap = new HashMap<>();  // 这个map存结果
        String token = (String) session.getAttribute(SessionKey.TOKEN);
        if (gongGaoBean == null || !gongGaoBean.getToken().equals(token)) {  // 如果前端传入的token和后台session中的token不匹配
            returnMap.put("returnCode", 408);
            returnMap.put("msg", "登录超时！");
        } else if (String.valueOf(session.getAttribute(SessionKey.FABUXIUGAIGONGGAO)).equals("0")) {                    // 如果该人员没有权限
            returnMap.put("returnCode", -2);
            returnMap.put("msg", "你没有这个权限！");
        } else { // 如果有权限token也能对应

            Integer action = gongGaoService.shanChu(gongGaoBean); // 得到删除结果
            if (action == 1) {  // 如果修改条数为1
                returnMap.put("returnCode", 200);   // 成功状态码200
                returnMap.put("msg", "删除成功");     // 消息提示上传成功
            } else { // 如果删除条数不为1
                returnMap.put("returnCode", 0);   // 成功状态码200
                returnMap.put("msg", "删除失败，登录人的档案id和发布人档案id不匹配");     // 消息提示上传成功
            }
        }
        return returnMap;
    }

    /**
     * 这个方法发布查询已发布或者未发布的公告
     *
     * @param gongGaoBean 公告bean，用来接收前端传入的参数
     * @param request     用来获取session
     * @return 返回json
     */
    @RequestMapping(value = {"/geRenGongGaoZhuangTai"}, method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> geRenGongGaoZhuangTai(@RequestBody(required = false) GongGaoBean gongGaoBean, HttpServletRequest request) {
        HttpSession session = request.getSession(); // 得到session
        Map<String, Object> returnMap = new HashMap<>();  // 这个map存结果
        String token = (String) session.getAttribute(SessionKey.TOKEN);
        if (gongGaoBean == null || !gongGaoBean.getToken().equals(token)) {  // 如果前端传入的token和后台session中的token不匹配
            returnMap.put("returnCode", 408);
            returnMap.put("msg", "登录超时！");
        } else { // 如果有权限token也能对应
            List<Gonggao> list = gongGaoService.geRenGongGaoZhuangTai(gongGaoBean); // 调用查询方法，传入公告bean
            returnMap.put("returnCode", 200);   // 成功状态码200
            returnMap.put("msg", "成功,注意里面的id是每条公告的id，需要隐藏存储在前端每条公告里面，如果用户点击公告需传入这个id和浏览数来修改浏览次数");     // 消息提示成功
            int zongYeShu = list.size() % 3 == 0 ? (list.size() / 3) : (list.size() / 3 + 1); // 计算总页数
            returnMap.put("ZongYeShu", zongYeShu); //传出总页数
            returnMap.put("data", list);   // data数据中将list封装进去
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
    @RequestMapping(value = {"/update"}, method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateGongGao(@RequestBody(required = false) GongGaoBean gongGaoBean, HttpServletRequest request) {
        HttpSession session = request.getSession(); // 得到session
        Map<String, Object> returnMap = new HashMap<>();  // 这个map存结果
        String token = (String) session.getAttribute(SessionKey.TOKEN);
        if (gongGaoBean == null || !gongGaoBean.getToken().equals(token)) {  // 如果前端传入的token和后台session中的token不匹配
            returnMap.put("returnCode", 408);
            returnMap.put("msg", "登录超时！");
        } else if (String.valueOf(session.getAttribute(SessionKey.FABUXIUGAIGONGGAO)).equals("0")) {                    // 如果该人员没有权限
            returnMap.put("returnCode", -2);
            returnMap.put("msg", "你没有这个权限！");
        } else { // 如果有权限token也能对应
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
                returnMap.put("returnCode", 200);   // 成功状态码200
                returnMap.put("msg", "200修改成功");     // 消息提示上传成功
            } else {
                returnMap.put("returnCode", 0);   // 成功状态码200
                returnMap.put("msg", "0修改失败，登录人的id和发布人多的id不匹配");     // 消息提示上传成功
            }
        }
        return returnMap;
    }

    /**
     * 这个方法用来查询所有的部门
     *
     * @param gongGaoBean 得到token
     * @return
     */
    @RequestMapping(value = {"/chaXunBuMen"}, method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> chaXunBuMen(@RequestBody GongGaoBean gongGaoBean, HttpServletRequest request) {
        HttpSession session = request.getSession(); // 得到session
        Map<String, Object> returnMap = new HashMap<>();  // 这个map存结果
        String token = (String) session.getAttribute(SessionKey.TOKEN);
        if (gongGaoBean == null || !gongGaoBean.getToken().equals(token)) {  // 如果前端传入的token和后台session中的token不匹配
            returnMap.put("returnCode", 408);
            returnMap.put("msg", "登录超时！");
        } else { // 如果有权限token也能对应
            List<Bumen> list = gongGaoService.chaXunBuMen(); // 查询得到所有的部门名称和部门id
            returnMap.put("data", list);
            returnMap.put("returnCode", 200);   // 成功状态码200
            returnMap.put("msg", "得到部门名称和部门id，新增传递部门的时候传递部门id，全体员工部门id传0，部门id（buMenId）");     // 消息提示上传成功
        }
        return returnMap;
    }

    /**
     * 这个方法用来上传文件
     *
     * @param articleFile 文件
     * @return
     */
    @RequestMapping(value = {"/shangChuan"}, method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> shangChuan(@RequestBody MultipartFile articleFile, HttpServletRequest request) {
        Map<String, Object> returnMap = new HashMap<>();  // 这个map存结果
        if (articleFile != null) {
            String fuJian = FileUpload.executeImport(articleFile, request); // 得到附件地址
            if (fuJian != null) {
                request.getSession().setAttribute(SessionKey.FJDZ, fuJian); // 传入附件地址
                returnMap.put("returnCode", 200);   // 成功状态码20
                returnMap.put("msg", "上传成功");     // 消息提示上传成功
            } else {
                returnMap.put("returnCode", -100);   // 成功状态码200
                returnMap.put("msg", "文件过大，请传递小余10M的文件");     // 消息提示上传成功
            }
        } else {
            returnMap.put("returnCode", 100);   // 成功状态码100
            returnMap.put("msg", "未上传文件");     // 消息提示未上传文件
        }
        return returnMap;
    }

    /**
     * 这个方法用来下载
     *
     * @param fileName 下载地址
     * @param request
     * @param response
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



    @RequestMapping(value = {"/updateGongGaoDianJi"})
    @ResponseBody
    public Map<String, Object> updateGongGaoDianJi(@RequestBody GongGaoBean gongGaoBean, HttpServletRequest request) {
        HttpSession session = request.getSession(); // 得到session
        Map<String, Object> returnMap = new HashMap<>();  // 这个map存结果
        System.out.println("================" + gongGaoBean.getDangAnId());
        String token = (String) session.getAttribute(SessionKey.TOKEN);
        Integer quanxian = (Integer) session.getAttribute(SessionKey.FABUXIUGAIGONGGAO);
        if (gongGaoBean != null && token.trim().equals(gongGaoBean.getToken())) {
            gongGaoService.updateGongGaoDianJi(gongGaoBean);
            returnMap.put("returnCode", 200);
            returnMap.put("msg", "点击事件更新成功");
        }

        return returnMap;
    }

}
