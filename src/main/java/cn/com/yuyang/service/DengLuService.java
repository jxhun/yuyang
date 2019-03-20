package cn.com.yuyang.service;


import cn.com.yuyang.pojo.Denglu;
import cn.com.yuyang.pojo.DengluMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DengLuService {
    private final DengluMapper dengluMapper;


    @Autowired
    public DengLuService(DengluMapper dengluMapper){
        System.out.println("---XsxxService构造函数---");
        this.dengluMapper = dengluMapper;
    }

    //此方法根据手机号码或者工号查询用户是否存在,true代表存在，false不存在
    public Denglu loginByShoujiHaoMa(Denglu denglu ){
        return dengluMapper.loginByShoujiHaoMa(denglu);
    }
    //此方法是为了判断用户登录成功之后登录次数与登录时间更新是否成功
    public void updateLogin(Denglu denglu){
        dengluMapper.updateLogin(denglu);
    }
    //此方法是根据ID查询人员档案，跟职务信息
    public  Denglu selectChaXun(Denglu denglu){
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

    public  Denglu selectByDongJiShiJiaan(Denglu denglu){
        return  dengluMapper.selectByDongJiShiJiaan(denglu);
    }

    public  void  updateJieDong(Denglu denglu){
        System.out.println("000000000000============"+denglu.getZhuangTai());
        dengluMapper.updateJieDong(denglu);

    }

    public DengluMapper getDengluMapper() {
        return dengluMapper;
    }
}
