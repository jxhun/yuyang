package cn.com.yuyang.controller;


import cn.com.yuyang.bean.YanZhengMaBean;
import cn.com.yuyang.pojo.Denglu;
import cn.com.yuyang.service.DengLuService;
import cn.com.yuyang.util.SendSMS;
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
    public DengLuController(DengLuService dengLuService) {
        this.dengLuService = dengLuService;
    }

    @RequestMapping(value = "/login")
    @ResponseBody
    public Map<String, Object> loginByShoujiHaoMa(@RequestBody(required = false) YanZhengMaBean yanZhengMaBean, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        System.out.println("手机号码" + yanZhengMaBean.getMiMa());
        //yanZhengMaBean存在
        if (yanZhengMaBean != null) {
            //根据前台传过来的手机号码或者工号判断该手机号查询是否存在
            Denglu denglu1 = dengLuService.loginByShoujiHaoMa(yanZhengMaBean);
            long t = 0;
            if (denglu1.getDongJieShiJian() != null) {
                //获取冻结时间
                long dongJieShiJian = denglu1.getDongJieShiJian().getTime();
                //获取当前时间
                long time = System.currentTimeMillis();
                t = time - dongJieShiJian;
            }
            //如果登录ID>0代表该手机号码存在
            if (denglu1 != null && denglu1.getId() > 0) {
                //登录状态为1代表可以登录，0位账户冻结，-1为停用该账户,yanZhengMaBean中验证码不为空不为null则代表该用户使用的是验证码登录
                if (yanZhengMaBean.getYanZhengMa() != null && !yanZhengMaBean.getYanZhengMa().trim().equals("") && request.getSession().getAttribute("yanZhengMa") != null && !request.getSession().getAttribute("yanZhengMa").toString().trim().equals("")) {
                    //如果前台传过来的验证码跟session中一样则代表登录成功
                    if (yanZhengMaBean.getYanZhengMa().trim().equals(request.getSession().getAttribute("yanZhengMa"))) {
                        //每一次登录成功，登录成功次数加1
                        denglu1.setDengLuCiShu(denglu1.getDengLuCiShu() + 1);
                        //根据ID查询人员档案信息和职务信息
                        denglu1 = dengLuService.selectChaXun1(denglu1);
                        //设置权限在session中
                        map = dengLuService.setQuanXian(denglu1, request);
                        //根据ID，更新用户的登录次数和最后一次登录时间
                        dengLuService.updateLogin(denglu1);
                        map.put("returncode", "200");
                        map.put("msg", "登录成功");
                        map.put("data", denglu1);
                        map.put("toKen", ToKen.toKen());
                        return map;

                    } else {
                        map.put("returncode", "-1");
                        map.put("msg", "验证码错误");
                    }
                }
                //状态不为-1代表状态可以为0 或者1  如果状态为0 冻结时间大于解冻时间则可以进行登录
                else if (denglu1.getZhuangTai() != -1 && ((denglu1.getCuoWuCiShu() % 3 == 0 && t >= (3 * 1000 * 60)) || (denglu1.getCuoWuCiShu() % 6 == 0 && t >= (6 * 1000 * 60)) || (denglu1.getCuoWuCiShu() % 9 == 0 && t >= (9 * 1000 * 60)))) {
                    //如果状态为0 则进行解冻
                    if (denglu1.getZhuangTai() == 0) {
                        //根据ID解冻
                        denglu1.setZhuangTai(1);
                        dengLuService.updateJieDong(denglu1);
                    }
                    String miMa = denglu1.getMiMa();
                    //前端传过来的密码跟得到的密码进行比较，，如果一样则登录成功
                    if (yanZhengMaBean.getMiMa().trim().equals(miMa)) {
                        //每一次登录成功，登录成功次数加1
                        denglu1.setDengLuCiShu(denglu1.getDengLuCiShu() + 1);
                        //根据ID查询人员档案信息和职务信息
                        denglu1 = dengLuService.selectChaXun1(denglu1);
                        System.out.println("=======" + denglu1);
                        //此方法代表将所有需要设置的权限设置在session中，
                        map = dengLuService.setQuanXian(denglu1, request);
                        //根据ID，更新用户的登录次数和最后一次登录时间
                        dengLuService.updateLogin(denglu1);
                        map.put("returncode", "200");
                        map.put("msg", "登录成功");
                        map.put("data", denglu1);
                        map.put("toKen", ToKen.toKen());
                        return map;
                    } else {
                        //如果密码输错，增加错误次数
                        denglu1.setCuoWuCiShu(denglu1.getCuoWuCiShu() + 1);
                        dengLuService.updateCuoWuCiShu(denglu1);
                        //如果错误次数能够被3 6 9 整除则冻结
                        if (denglu1.getCuoWuCiShu() % 3 == 0 || denglu1.getCuoWuCiShu() % 6 == 0 || denglu1.getCuoWuCiShu() % 9 == 0) {
                            denglu1.setZhuangTai(0);
                            dengLuService.updateDongJie(denglu1);
                            //冻结时间大于3分钟 6分钟 9分钟进行解冻
                            if ((denglu1.getCuoWuCiShu() % 3 == 0 && t >= (3 * 1000 * 60)) || (denglu1.getCuoWuCiShu() % 6 == 0 && t >= (6 * 1000 * 60)) || (denglu1.getCuoWuCiShu() % 9 == 0 && t >= (9 * 1000 * 60))) {
                                //根据ID解冻
                                denglu1.setZhuangTai(1);
                                dengLuService.updateJieDong(denglu1);
                            }

                        } else {
                            map.put("returncode", "-1");
                            map.put("msg", "该账户被冻结");
                        }
                        map.put("returncode", "-1");
                        map.put("msg", "密码错误");
                    }

                } else if (denglu1.getZhuangTai() == -1) {
                    //该用户被停用
                    map.put("returncode", "-1");
                    map.put("msg", "该账户被停用了");
                }

            } else {
                map.put("returncode", "-1");
                map.put("msg", "该手机号码不存在，请重新输入");
            }
        }
        return map;
    }

    /**
     * 此方法是获取验证码
     *
     * @return 返回一个map
     */
    @RequestMapping(value = "/huoQuYanZhengMa")
    @ResponseBody
    public Map<String, Object> huoQuYanZhengMa(@RequestBody(required = false) YanZhengMaBean yanZhengMaBean, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        //手机号码不为空也不为null代表手机号码存在才进行验证码验证
        if (yanZhengMaBean != null && yanZhengMaBean.getShouJiHaoMa() != null) {
            Denglu denglu1 = dengLuService.selectByYanZhengMa(yanZhengMaBean);
            request.getSession().setAttribute(SessionKey.DENGLUID, denglu1.getId());
            //验证码存在，手机号存在，且验证码不为null
            if (denglu1 != null && denglu1.getId() > 0) {
                String sendsms = SendSMS.sendsms(yanZhengMaBean.getShouJiHaoMa());
                request.getSession().setAttribute("yanZhengMa", sendsms);
                map.put("returncode", "200");
                map.put("msg", "验证码发送成功");
            } else {
                map.put("returncode", "-1");
                map.put("msg", "请输入正确的验证码");
            }
        } else {
            map.put("returncode", "-1");
            map.put("msg", "该手机号码不存在，请重新输入");
        }
        return map;
    }


    @RequestMapping(value = "/wangJiMiMa")
    @ResponseBody
    public Map<String, Object> wangJiMiMa(@RequestBody(required = false) YanZhengMaBean yanZhengMaBean, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        //手机号码不为空也不为null代表手机号码存在才进行验证码验证
        if (yanZhengMaBean != null && yanZhengMaBean.getShouJiHaoMa() != null) {
            //代表手机号码或者工号存在
            Denglu denglu1 = dengLuService.selectByYanZhengMa(yanZhengMaBean);

            Denglu denglu = dengLuService.selectChaXun1(denglu1);
            System.out.println(denglu.getRenyuandangan().getXingMing() + denglu.getRenyuandangan().getYouXiang());

//            new Thread(new MyJavaMail(denglu.getRenyuandangan().getXingMing(),denglu.getRenyuandangan().getYouXiang(),"haha")).start();

            request.getSession().setAttribute(SessionKey.DENGLUID, denglu1.getId());

        }
        return map;
    }


    public DengLuService getDengLuService() {
        return dengLuService;
    }
}
