package cn.com.yuyang.service;

import cn.com.yuyang.bean.Bumenbean;
import cn.com.yuyang.bean.YuanGongBean;
import cn.com.yuyang.pojo.*;
import cn.com.yuyang.util.AsymmetricEncryption;
import cn.com.yuyang.util.SessionKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: huxiaoyi
 * Date: 2019/03/19
 * Description:
 * Version: V1.0
 */
@Service
public class YuanGongGuanLiService {

    private final MingancaozuoMapper mingancaozuoMapper;

    private DengluMapper dengluMapper;

    private final RenyuandanganMapper renyuandanganMapper;

    private final BumenMapper bumenMapper;

    private final ZhiwubiaoMapper zhiwubiaoMapper;

    @Autowired
    public YuanGongGuanLiService(MingancaozuoMapper mingancaozuoMapper,DengluMapper dengluMapper, RenyuandanganMapper renyuandanganMapper,
                                 BumenMapper bumenMapper, ZhiwubiaoMapper zhiwubiaoMapper) {
        this.mingancaozuoMapper = mingancaozuoMapper;
        this.bumenMapper = bumenMapper;
        this.dengluMapper = dengluMapper;
        this.renyuandanganMapper = renyuandanganMapper;
        this.zhiwubiaoMapper = zhiwubiaoMapper;
    }

    /**
     * 查询全部员工的信息
     *
     * @return
     */
    public List<Denglu> quanCha() {
        return dengluMapper.selectChaXun(0, 0, null, null, null, null);
    }


    /**
     * 条件查询员工的信息
     *
     * @param yuanGongBean 前端传递数据封装的bean对象
     * @return
     */
    public List<Denglu> tiaoJainCha(YuanGongBean yuanGongBean) {

        String gongHao = yuanGongBean.getGongHao();
        String xingMing = yuanGongBean.getXingMing();
        String kaiShiShiJian = yuanGongBean.getKaiShiShiJian();
        String jieShuShiJian = yuanGongBean.getJieShuShiJian();
        long buMenId = yuanGongBean.getBuMenId();
        long dangAnId = yuanGongBean.getDangAnId();
        if (dangAnId != 0) {
            return dengluMapper.selectChaXun(dangAnId, 0, null, null, null, null);
        }
        if (buMenId != 0) {
            return dengluMapper.selectChaXun(0, buMenId, null, null, null, null);
        }
        //去掉员工工号中的空格为空时，为工号赋值null
        if (gongHao != null && gongHao.trim().equals("")) {
            gongHao = null;
        }
        //拼接百分号到员工姓名两边
        if (xingMing != null) {
            if (xingMing.trim().equals("")) {
                xingMing = null;
            } else {
                xingMing = "%" + xingMing + "%";
            }
        }
        //当两个时间为空时，赋值为null
        if (kaiShiShiJian != null && kaiShiShiJian.trim().equals("")) {
            kaiShiShiJian = null;
        }
        if (jieShuShiJian != null && jieShuShiJian.trim().equals("")) {
            jieShuShiJian = null;
        }
        String startTime = "";
        String endTime = "";
        //当开始时间或者结束时间有一个不为null时，为其设定一个默认值，当两个都不为null时，
        //将两个时间赋值给startTime和endTime
        if (kaiShiShiJian == null && jieShuShiJian != null) {
            startTime = "1970-01-01";
            endTime = jieShuShiJian;

        } else if (kaiShiShiJian != null && jieShuShiJian == null) {
            startTime = kaiShiShiJian;
            endTime = (new Timestamp(System.currentTimeMillis())).toString();
        } else if (kaiShiShiJian != null && jieShuShiJian != null) {
            startTime = kaiShiShiJian;
            endTime = jieShuShiJian;
        }

        return dengluMapper.selectChaXun(dangAnId, 0, xingMing, gongHao, startTime, endTime);
    }


    /**
     * 修改员工信息
     *
     * @param yuanGongBean
     * @return
     */
    public String bianji(YuanGongBean yuanGongBean, HttpServletRequest request) {
        String msg = "修改失败!!!";
        if (renyuandanganMapper.updateYuanGong(yuanGongBean) != null &&
                renyuandanganMapper.updateDenglu(yuanGongBean) != null) {
            msg = "修改!!!";
        }
        return msg;
    }

    /**
     * 删除员工信息，假删除，将登录表的状态改为-1，将档案表的在职状态改为2
     *
     * @param yuanGongBean
     * @return
     */
    public String shanchu(YuanGongBean yuanGongBean, HttpServletRequest request) {
        String msg = "删除失败!!!";
        if (renyuandanganMapper.deleteDenglu(yuanGongBean) != null &&
                renyuandanganMapper.deleteRenyuandangan(yuanGongBean) != null) {
            msg = "删除成功!!!";
        }
        return msg;
    }


    /**
     * 新增员工
     *
     * @param yuanGongBean
     * @return
     */
    public String xinzeng(YuanGongBean yuanGongBean, HttpServletRequest request) {
        String msg = "添加失败!!!";
        if (yuanGongBean.getRuZhiShiJian() == null) {
            yuanGongBean.setRuZhiShiJian(new Date(System.currentTimeMillis()));
        }
        int i = 0;
        try {
            i = renyuandanganMapper.insertRenyuandangan(yuanGongBean);
        } catch (Exception e) {
            msg = "添加失败，请核实信息是否属实！";
            return msg;
        }

        if (i != 0) {
            yuanGongBean.setGongHao(gongHao(yuanGongBean.getBuMenId()));
            try {
                AsymmetricEncryption asymmetricEncryption = new AsymmetricEncryption();
                yuanGongBean.setMiMa2(asymmetricEncryption.jiaMi(yuanGongBean.getShouJiHaoMa().substring(5, 11), request)); // 密码加密存入
                yuanGongBean.setSiYao((String) request.getSession().getAttribute(SessionKey.SIYAO)); // 得到私钥
            } catch (Exception e) {
                msg = "手机格式错误!";
                renyuandanganMapper.shanchuRenyuandangan(yuanGongBean);
                return msg;
            }
            long dangAnId = renyuandanganMapper.selectId(yuanGongBean);
            yuanGongBean.setDangAnId(dangAnId);
            Integer denglu = dengluMapper.insertDenglu(yuanGongBean);
            if (denglu == 0) {
                msg = "添加失败，请仔细核对信息是否正确！";
                renyuandanganMapper.shanchuRenyuandangan(yuanGongBean);
                return msg;
            } else {
                msg = "添加成功!!!";
            }
        }
        return msg;
    }


    /**
     * 生成员工工号
     *
     * @return
     */
    public String gongHao(long buMenId) {
        String gongHao = "xm" + (new Timestamp(System.currentTimeMillis())).toString().substring(0, 4);
        Bumenbean bum = new Bumenbean();
        bum.setId(buMenId);
        //查询部门信息，部门负责人和部门人数
        List<Bumenbean> list = bumenMapper.selectCount(bum);
        String count = "" + list.get(0).getCount();
        while (count.length() < 4) {
            count = "0" + count;
        }
        gongHao = gongHao + count;
        return gongHao;
    }

    /**
     * 查找所有部门的名字和对应的id
     *
     * @return
     */
    public List<Bumen> bumenxuandan() {
        List<Bumen> list = bumenMapper.selectBumen();
        return list;
    }

    /**
     * 查找所有职务的名称和对应的id
     *
     * @return
     */
    public List<Zhiwubiao> zhiwuxuandan() {
        List<Zhiwubiao> list = zhiwubiaoMapper.selectZhiwu2();
        return list;
    }

    /**
     * 这个方法用来新增操作记录
     * @param request 用来获取session
     */
    public void minGanXinZeng(HttpServletRequest request){
        mingancaozuoMapper.xinZeng((Mingancaozuo) request.getSession().getAttribute(SessionKey.MGSJ)); // 调用方法，传入对象
    }


    /**
     * 重置密码
     * @param yuanGongBean
     */
    public  void updateChongZhiMiMa(YuanGongBean yuanGongBean){
        dengluMapper.updateChongZhiMiMa(yuanGongBean);
    }

}
