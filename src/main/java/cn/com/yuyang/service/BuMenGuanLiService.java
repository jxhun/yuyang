package cn.com.yuyang.service;

import cn.com.yuyang.bean.Bumenbean;
import cn.com.yuyang.pojo.*;
import cn.com.yuyang.util.SessionKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: huxiaoyi
 * Date: 2019/03/18
 * Description:
 * Version: V1.0
 */
@Service
public class BuMenGuanLiService {


    private final MingancaozuoMapper mingancaozuoMapper;

    private final BumenMapper bumenMapper;

    private final RenyuandanganMapper renyuandanganMapper;
    private final ZhiwubiaoMapper zhiwubiaoMapper;

    @Autowired
    public BuMenGuanLiService(MingancaozuoMapper mingancaozuoMapper, BumenMapper bumenMapper,
                              RenyuandanganMapper renyuandanganMapper, ZhiwubiaoMapper zhiwubiaoMapper) {
        this.bumenMapper = bumenMapper;
        this.mingancaozuoMapper = mingancaozuoMapper;
        this.renyuandanganMapper = renyuandanganMapper;
        this.zhiwubiaoMapper = zhiwubiaoMapper;
    }


    /**
     * 查询所有部门信息
     *
     * @return 部门信息
     */
    public List<Bumenbean> shouye() {
        Bumenbean bumen = new Bumenbean();

        return bumenMapper.selectCount(bumen);
    }

    /**
     * 条件查询指定部门信息
     *
     * @param bumen
     * @return
     */
    public List<Bumenbean> chaxun(Bumenbean bumen) {
        String buMenMingCheng = bumen.getBuMenMingCheng();
        if (buMenMingCheng != null && !buMenMingCheng.trim().equals("")) {
            buMenMingCheng = "%" + buMenMingCheng + "%";
            bumen.setBuMenMingCheng(buMenMingCheng);
        }

        return bumenMapper.selectCount(bumen);
    }

    /**
     * 新增部门
     *
     * @param bumen
     * @return
     */

    public Map<String, Object> xinzeng(Bumenbean bumen, HttpServletRequest request) {
        String msg = "部门添加失败!!!";
        int Returncode = -1;
        //查询负责人的信息，如果getId方法不能取到值，则该员工不存在
        Renyuandangan renyuandangan = renyuandanganMapper.selectGeRenChaXun(bumen);
        if (renyuandangan != null && renyuandangan.getId() != 0) {
            bumen.setDangAnId(renyuandangan.getId());
            //查看员工是否是其他部门负责人
            if (bumenMapper.selectFuZeRen(bumen) != null) {
                msg = "该员工是其他部门的负责人！";
                Returncode = -22;
            }
            //通过部门名称查询部门是否存在，不存在时再新增部门
            else if (bumenMapper.selectOne(bumen) == null || bumenMapper.selectOne(bumen) == 0) {
                bumen.setDangAnId(renyuandangan.getId());
                bumen.setChuangJianShiJian(new Timestamp(System.currentTimeMillis()));
                Integer i = bumenMapper.insertBuMen(bumen);
                if (i != null) {
                    //生成新的部门经理职务
                    Zhiwubiao zhiwubiao = new Zhiwubiao();
                    zhiwubiao.setZhiWuMingCheng(bumen.getBuMenMingCheng() + "经理");
                    zhiwubiao.setZhiWuJianJie("没有简介");
                    zhiwubiao.setCaoZuoYuanGong(0);
                    zhiwubiao.setChaKanKaoQin(0);
                    zhiwubiao.setFaBuXiuGaiGongGao(0);
                    zhiwubiao.setDaiBanShiXiang(0);
                    zhiwubiao.setQinJiaShenPi(0);
                    zhiwubiao.setQuanXianGuanLi(0);
                    zhiwubiao.setChuangJianShiJian(new Timestamp(System.currentTimeMillis()));
                    zhiwubiaoMapper.insertZhiWu(zhiwubiao);
                    Integer id = zhiwubiaoMapper.selectId(zhiwubiao);
                    //将修改后的职务ID和部门ID传入bean中，再将部门负责人的部门ID改为新部门的ID
                    bumen.setZhiWuId(id);
                    bumen.setId(bumenMapper.selectOne(bumen));
                    ;
                    if (renyuandanganMapper.updateYuanGongBuMen(bumen) != null) {
                        msg = "部门添加成功！";
                        Returncode = 200;
                    } else {
                        //失败则删除之前添加的部门
                        bumenMapper.deleteBuMen(bumen.getId());
                    }
                }
            } else {
                msg = "部门已存在！";
                Returncode = -23;
            }
        } else {
            msg = "负责人不存在！";
            Returncode = -21;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("returnCode", Returncode);
        map.put("msg", msg);
        return map;
    }


    public Map<String, Object> bianji(Bumenbean bumen, HttpServletRequest request) {
        String msg = "部门编辑失败!!!";
        int Returncode = -1;
        Map<String, Object> map = new HashMap<>();
        //查询负责人的信息，如果getId方法不能取到值，则该员工不存在
        Renyuandangan renyuandangan = renyuandanganMapper.selectGeRenChaXun(bumen);
        if (renyuandangan != null && renyuandangan.getId() != 0) {
            //判断负责人是否改变
            if (renyuandangan.getId() != bumen.getDangAnId()) {
                bumen.setDangAnId(renyuandangan.getId());
                //如果负责人没有改变，则查看员工是否是部门负责人
                if (bumenMapper.selectFuZeRen(bumen) != null) {
                    msg = "该员工是其他部门的负责人！";
                    Returncode = -22;
                    map.put("returnCode", Returncode);
                    map.put("msg", msg);
                    return map;
                }
            }
            //通过部门名称和部门id查询部门要修改的名称是否存在，不存在时再修改部门
            if (bumenMapper.selectOne(bumen) == null || bumenMapper.selectOne(bumen) == 0) {
                //修改部门经理的职务名字
                Zhiwubiao zhiwubiao = new Zhiwubiao();
                zhiwubiao.setId(renyuandangan.getZhiWuId());
                zhiwubiao.setZhiWuMingCheng(bumen.getXingMing() + "经理");
                zhiwubiaoMapper.updateZhiWuMingCheng(zhiwubiao);


                Bumenbean bum = new Bumenbean();
                //查出部门之前的负责人，将其职务ID改为0
                Integer ing = bumenMapper.selectFuZeRen2(Integer.parseInt(String.valueOf(bumen.getId())));
                bum.setDangAnId(ing);
                bum.setZhiWuId(0);
                bum.setId(bumen.getId());
                renyuandanganMapper.updateYuanGongBuMen(bum);

                bumen.setDangAnId(renyuandangan.getId());
                if (bumenMapper.updateBuMen(bumen) != null && renyuandanganMapper.updateYuanGongBuMen(bumen) != null) {

                    msg = "部门编辑成功！";
                    Returncode = 200;
                }
            } else {
                msg = "部门已存在！";
                Returncode = -23;
            }
        } else {
            msg = "负责人不存在！";
            Returncode = -21;
        }
        map.put("Returncode", Returncode);
        map.put("msg", msg);
        return map;
    }

    public Map<String, Object> shanchu(Bumenbean bum, HttpServletRequest request) {
        String msg = "删除失败!!";
        int Returncode = -1;
        Map<String, Object> map = new HashMap<>();
        //判断前端是否传入id
        long id = bum.getId();
        if (id == 0) {
            msg = "没有传入部门ID!!";
            Returncode = -25;
        } else if (id == 1 || id == 2) {
            msg = "不能删除董事会和人事部!!";
            Returncode = -27;
        }
        //查找该部门的人数,d当人数为0时，才能执行删除
        else if (bumenMapper.selectCount(bum) != null && bumenMapper.selectCount(bum).get(0).getCount() == 1) {
            long i = bumenMapper.deleteBuMen(id);
            bum.setId(-1);
            long i2 = renyuandanganMapper.updateYuanGongBuMen(bum);
            if (i != 0 && i2 != 0) {
                msg = "删除成功";
                Returncode = 200;
            }
        } else {
            msg = "该部门还有员工";
            Returncode = -26;
        }
        map.put("returnCode", Returncode);
        map.put("msg", msg);
        return map;
    }

    /**
     * 这个方法用来新增操作记录
     *
     * @param request 用来获取session
     */
    public void minGanXinZeng(HttpServletRequest request) {
        HttpSession session = request.getSession(); // 得到session
        Mingancaozuo mingancaozuo = (Mingancaozuo) session.getAttribute(SessionKey.MGSJ);  // 得到AOP方法中传出的对象
        if (mingancaozuo != null) {
            String fangFaMing = mingancaozuo.getFangFaMingCheng();  // 得到方法名称
            mingancaozuo.setXingMing((String) session.getAttribute(SessionKey.XINGMING)); // 得到姓名存入对象
            mingancaozuo.setDangAnId((Long) session.getAttribute(SessionKey.DANGANID)); // 得到姓名存入对象
            if (fangFaMing.equals("xinzeng")) {  // 如果方法名是新增，那么就传入方法名称为新增部门
                mingancaozuo.setFangFaMingCheng("新增部门");
            } else if (fangFaMing.equals("shanchu")) {  // 如果方法名是删除，那么就传入方法名称为删除部门
                mingancaozuo.setFangFaMingCheng("删除部门");
            } else if (fangFaMing.equals("bianji")) {  // 如果方法名是编辑，那么就传入方法名称为编辑部门
                mingancaozuo.setFangFaMingCheng("编辑部门");
            }
            mingancaozuoMapper.xinZeng(mingancaozuo); // 调用方法，传入对象
        }
    }


}
