package cn.com.yuyang.service;


import cn.com.yuyang.bean.YanZhengMaBean;
import cn.com.yuyang.pojo.Denglu;
import cn.com.yuyang.pojo.DengluMapper;
import cn.com.yuyang.util.SessionKey;
import cn.com.yuyang.util.ToKen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DengLuService {
    private final DengluMapper dengluMapper;



    @Autowired
    public DengLuService(DengluMapper dengluMapper){
        this.dengluMapper = dengluMapper;
    }

    //此方法根据手机号码或者工号查询用户是否存在,true代表存在，false不存在
    public Denglu loginByShoujiHaoMa(YanZhengMaBean yanZhengMaBean ){
        return dengluMapper.loginByShoujiHaoMa(yanZhengMaBean);
    }
    //此方法是为了判断用户登录成功之后登录次数与登录时间更新是否成功
    public void updateLogin(Denglu denglu){
        dengluMapper.updateLogin(denglu);
    }
    //此方法是根据ID查询人员档案，跟职务信息
    public  Denglu selectChaXun1(Denglu denglu){
        return  dengluMapper.selectChaXun1(denglu);
    }

    //修改登录错误的次数
    public void updateCuoWuCiShu(Denglu denglu){
        dengluMapper.updateCuoWuCiShu(denglu);
    }

    //此方法是更改用户登录的状态 1位可登陆 0 为冻结 -1位停用
    public  void updateDongJie(Denglu denglu){
        dengluMapper.updateDongJie(denglu);
    }

    //解冻此方法 ，参数为登录表
    public  void  updateJieDong(Denglu denglu){
        dengluMapper.updateJieDong(denglu);

    }

    //根据手机号码查询对应的验证码
    public  Denglu selectByYanZhengMa(YanZhengMaBean yanZhengMaBean){

        return  dengluMapper.selectByYanZhengMa(yanZhengMaBean);
    }


    /**
     * 此方法为登录成功，将该人员的权限存入session
     * @param denglu1
     * @param request
     */
    public void setQuanXian(Denglu denglu1, HttpServletRequest request){
        HttpSession session = request.getSession(); // 得到session
        session.setAttribute(SessionKey.DENGLUID, denglu1.getId());
        session.setAttribute(SessionKey.DANGANID, denglu1.getDangAnId());// 登录成功后传入session的登录id
        session.setAttribute(SessionKey.CHAKANKAOQIN, denglu1.getRenyuandangan().getZhiwubiao().getChaKanKaoQin());
        session.setAttribute(SessionKey.QINGJIASHENPI, denglu1.getRenyuandangan().getZhiwubiao().getQinJiaShenPi());
        session.setAttribute(SessionKey.QUANXIANGUANLI, denglu1.getRenyuandangan().getZhiwubiao().getQuanXianGuanLi());
        session.setAttribute(SessionKey.CHAKANYUANGONG, denglu1.getRenyuandangan().getZhiwubiao().getChaKanYuanGong());
        session.setAttribute(SessionKey.CAOZUOYUANGONG, denglu1.getRenyuandangan().getZhiwubiao().getCaoZuoYuanGong());
        session.setAttribute(SessionKey.FABUXIUGAIGONGGAO, denglu1.getRenyuandangan().getZhiwubiao().getFaBuXiuGaiGongGao());
        session.setAttribute(SessionKey.DAIBANSHIXIANG, denglu1.getRenyuandangan().getZhiwubiao().getDaiBanShiXiang());
        session.setAttribute(SessionKey.TOKEN, ToKen.toKen());
        session.setAttribute(SessionKey.XINGMING, denglu1.getRenyuandangan().getXingMing());
        session.setAttribute(SessionKey.BUMENID, denglu1.getRenyuandangan().getBuMenId());
        session.setAttribute(SessionKey.BUMENGUANLI, denglu1.getRenyuandangan().getZhiwubiao().getBuMenGuanLi());
    }

    public DengluMapper getDengluMapper() {
        return dengluMapper;
    }

}
