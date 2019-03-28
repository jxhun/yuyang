package cn.com.yuyang.service;

import cn.com.yuyang.bean.YuanGongBean;
import cn.com.yuyang.pojo.Denglu;
import cn.com.yuyang.pojo.Renyuandangan;
import cn.com.yuyang.pojo.RenyuandanganMapper;
import cn.com.yuyang.util.AsymmetricEncryption;
import cn.com.yuyang.util.SessionKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * User: Jxhun
 * Date: 2019/03/21
 * Description: 个人设置service
 * Version: V1.0
 */
@Service
public class GeRenSheZhiService {

    @Autowired
    private final RenyuandanganMapper renyuandanganMapper; // 人员档案mapper

    /**
     * 注入renyuandanganMapper
     * @param renyuandanganMapper 人员档案mapper
     */
    public GeRenSheZhiService(RenyuandanganMapper renyuandanganMapper) {
        this.renyuandanganMapper = renyuandanganMapper;
    }

    /**
     * 这个方法用来查询当前登录人的具体信息
     * @param yuanGongBean 存入了前端传入的登录人id
     * @return 返回Renyuandangan对象，封装有 登录人的个人信息
     */
    public Renyuandangan xinxiChaXun(YuanGongBean yuanGongBean){
        return renyuandanganMapper.selectHaoYou(yuanGongBean); // 调用查询方法
    }

    /**
     * 此方法用来更新人员信息
     * @param yuanGongBean 存入了前端传入的人员ID
     * @return  返回Renyuandangan对象，封装有 登录人的个人信息
     */
    public  void updateXinXi(YuanGongBean yuanGongBean){
       renyuandanganMapper.updateXinXi(yuanGongBean);

    }

    /**
     * 此方法用来更新人员信息
     * @param yuanGongBean session中的登录ID
     * @return 返回对应的登录对象
     */
    public void updateXinXi2(YuanGongBean yuanGongBean, HttpServletRequest request){
        if(yuanGongBean.getShouJiHaoMa()==null ){
            AsymmetricEncryption asymmetricEncryption = new AsymmetricEncryption();
            byte[] bytes = asymmetricEncryption.jiaMi(yuanGongBean.getMiMa(), request);
            yuanGongBean.setMiMa2(bytes);
            String attribute = (String) request.getSession().getAttribute(SessionKey.SIYAO);
            yuanGongBean.setSiYao(attribute);
        }

        renyuandanganMapper.updateXinXi2(yuanGongBean);

    }


    public void updateTouXiang(YuanGongBean yuanGongBean){
        renyuandanganMapper.updateTouXiang(yuanGongBean);
    }


    /**
     * 此方法根据传过来是旧密码判断改密码是否正确
     * @param yuanGongBean
     * @return
     */
    public Integer selectMiMa(YuanGongBean yuanGongBean){
        return  renyuandanganMapper.selectMiMa(yuanGongBean);
    }


    public String selectMiMa2(YuanGongBean yuanGongBean){
        AsymmetricEncryption asymmetricEncryption = new AsymmetricEncryption();

        yuanGongBean=renyuandanganMapper.selectMiMa2(yuanGongBean);



        return   asymmetricEncryption.jieMi(yuanGongBean.getMiMa2(),yuanGongBean.getSiYao());
    }




}
