package cn.com.yuyang.controller;


import cn.com.yuyang.pojo.Denglu;
import cn.com.yuyang.service.DengLuService;
import cn.com.yuyang.util.SessionKey;
import cn.com.yuyang.util.ToKen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/login")
public class DengLuController {
    private final DengLuService dengLuService;


    @Autowired
    public DengLuController(DengLuService dengLuService){
        this.dengLuService=dengLuService;
    }

    @RequestMapping(value = "/login")
    @ResponseBody
    public Map<String,Object> loginByShoujiHaoMa(@RequestBody(required = false) Denglu denglu, HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
            if (denglu!=null&&(denglu.getShouJiHaoMa() != null || denglu.getGongHao() != null)) {
                String shouJiHaoMa = denglu.getShouJiHaoMa();
                String gongHao = denglu.getGongHao();
                denglu.setShouJiHaoMa(shouJiHaoMa);
                denglu.setGongHao(shouJiHaoMa);
                //手机号码不为null并且去掉前后空格不为空则代表是用手机号码登录，或者工号登录
                if (shouJiHaoMa != null && !shouJiHaoMa.trim().equals("") || gongHao != null && !gongHao.equals("")) {
                    Denglu denglu1 = dengLuService.loginByShoujiHaoMa(denglu);
                    //条件为true代表手机号码或者工号存在，条件为false代表手机号码或者工号不存在
                    if(denglu1!=null&&denglu1.getId()>0 ){
                        //登录状态为1代表可以登录，0位账户冻结，-1为停用该账户
                        if(denglu1.getZhuangTai()==1){
                            //根据用户名判断密码是否正确
                            String miMa = denglu.getMiMa();
                            //比较密码是否相等，若相等则登录成功
                            if(denglu.getMiMa()!=null && !denglu.getMiMa().equals("")&& denglu.getMiMa().equals(denglu1.getMiMa())){
                                //每一次登录成功，登录成功次数加1
                                denglu1.setDengLuCiShu(denglu1.getDengLuCiShu()+1);
                                //根据ID查询人员档案信息和职务信息
                                Denglu denglu2 = dengLuService.selectChaXun(denglu1);
                                request.getSession().setAttribute(SessionKey.DANGANID,denglu2.getDangAnId());// 登录成功后传入session的登录id
                                request.getSession().setAttribute(SessionKey.DENGLUID,denglu2.getId());
                                request.getSession().setAttribute(SessionKey.CHAKANKAOQIN,denglu2.getRenyuandangan().getZhiwubiao().getChaKanKaoQin());
                                request.getSession().setAttribute(SessionKey.QINGJIASHENPI,denglu2.getRenyuandangan().getZhiwubiao().getQinJiaShenPi());
                                request.getSession().setAttribute(SessionKey.QUANXIANGUANLI,denglu2.getRenyuandangan().getZhiwubiao().getQuanXianGuanLi());
                                request.getSession().setAttribute(SessionKey.CHAKANYUANGONG,denglu2.getRenyuandangan().getZhiwubiao().getChaKanYuanGong());
                                request.getSession().setAttribute(SessionKey.CAOZUOYUANGONG,denglu2.getRenyuandangan().getZhiwubiao().getCaoZuoYuanGong());
                                request.getSession().setAttribute(SessionKey.FABUXIUGAIGONGGAO,denglu2.getRenyuandangan().getZhiwubiao().getFaBuXiuGaiGongGao());
                                request.getSession().setAttribute(SessionKey.DAIBANSHIXIANG,denglu2.getRenyuandangan().getZhiwubiao().getDaiBanShiXiang());
                                request.getSession().setAttribute(SessionKey.TOKEN,ToKen.toKen());
                                request.getSession().setAttribute(SessionKey.XINGMING,denglu2.getRenyuandangan().getXingMing());
                                request.getSession().setAttribute(SessionKey.BUMENID,denglu2.getRenyuandangan().getBuMenId());
                                //根据ID，更新用户的登录次数和最后一次登录时间
                                dengLuService.updateLogin(denglu1);
                                map.put("returncode", "200");
                                map.put("msg", "登录成功");
                                map.put("data",denglu2);
                                map.put("toKen",ToKen.toKen());
                                return map;
                            }
                            //代表密码错误，则增加错误次数
                            else if(denglu.getMiMa()!=null && (!denglu.getMiMa().equals("") || !denglu.getMiMa().equals(denglu1.getMiMa()))){
                                //密码错误增加错误次数
                                denglu1.setCuoWuCiShu(denglu1.getCuoWuCiShu()+1);
                                dengLuService.updateCuoWuCiShu(denglu1);
                                    //如果错误次数能被3整除则冻结账号3分钟 3*1000*60
                                    if(denglu1.getCuoWuCiShu()%3==0 || denglu1.getCuoWuCiShu()%6==0 ||denglu1.getCuoWuCiShu()%9==0 ){
                                        denglu1.setZhuangTai(0);
                                        //该方法为更新账号的状态0位冻结 1为可登陆 -1为停用,并记录冻结时间
                                        dengLuService.updateDongJie(denglu1);
                                        //根据ID查询冻结时间
                                        Denglu denglu2 = dengLuService.selectByDongJiShiJiaan(denglu1);
                                        long dongJieShiJian = denglu2.getDongJieShiJian().getTime();
                                        long time = System.currentTimeMillis();
                                        long t = time-dongJieShiJian;
                                        if((denglu1.getCuoWuCiShu()%3==0&&t>=(3*1000*60)) || (denglu1.getCuoWuCiShu()%6==0&&t>=(6*1000*60)) || (denglu1.getCuoWuCiShu()%9==0&&t>=(9*1000*60))  ){
                                            //根据ID解冻
                                            dengLuService.updateJieDong(denglu2);
                                        }
                                    }
                            }
                        }
                        else if (denglu1.getZhuangTai()==0){
                            //该用户被冻结
                            //如果错误次数能被3整除则冻结账号3分钟 3*1000*60
                            if(denglu1.getCuoWuCiShu()%3==0 || denglu1.getCuoWuCiShu()%6==0 ||denglu1.getCuoWuCiShu()%9==0 ){
                                denglu1.setZhuangTai(0);
                                //该方法为更新账号的状态0位冻结 1为可登陆 -1为停用,并记录冻结时间
                               // dengLuService.updateDongJie(denglu1);
                                //根据ID查询冻结时间
                                Denglu denglu2 = dengLuService.selectByDongJiShiJiaan(denglu1);
                                long dongJieShiJian = denglu2.getDongJieShiJian().getTime();
                                long time = System.currentTimeMillis();
                                long t = time-dongJieShiJian;
                                if((denglu1.getCuoWuCiShu()%3==0&&t>=(3*1000*60)) || (denglu1.getCuoWuCiShu()%6==0&&t>=(6*1000*60)) || (denglu1.getCuoWuCiShu()%9==0&&t>=(9*1000*60))  ){
                                    //根据ID解冻
                                    denglu2.setZhuangTai(1);
                                    dengLuService.updateJieDong(denglu2);
                                }

                             }
                        }
                        else if (denglu1.getZhuangTai()==-1){
                            //该用户被停用
                            map.put("returncode", "-1");
                            map.put("msg", "该账户被停用了");
                        }

                    }else{
                        //该手机号码或者工号不存在
                        map.put("returncode", "-1");
                        map.put("msg", "没有工号输入手机号码或者");
                    }
                }else{
                    map.put("returncode", "-1");
                    map.put("msg", "请输入正确的手机号码或者工号");
                }
            } else {
                map.put("returncode", "-1");
                map.put("msg", "请使用正确的访问方式访问");
            }
        return map;
    }
    public DengLuService getDengLuService() {
        return dengLuService;
    }
}
