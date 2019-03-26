package cn.com.yuyang.service;

import cn.com.yuyang.bean.Bumenbean;
import cn.com.yuyang.pojo.*;
import cn.com.yuyang.util.SessionKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
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

    @Autowired
    public BuMenGuanLiService(MingancaozuoMapper mingancaozuoMapper, BumenMapper bumenMapper, RenyuandanganMapper renyuandanganMapper) {
        this.mingancaozuoMapper = mingancaozuoMapper;
        this.bumenMapper = bumenMapper;
        this.renyuandanganMapper = renyuandanganMapper;
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
        if (buMenMingCheng != null && buMenMingCheng.trim().equals("")) {
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

    public String xinzeng(Bumenbean bumen) {

        //查询负责人的信息，如果getId方法不能取到值，则该员工不存在
        Renyuandangan renyuandangan = renyuandanganMapper.selectGeRenChaXun(bumen);
        String msg = "";
        if (renyuandangan != null && renyuandangan.getId() != 0) {

            //查看员工是否是部门负责人
            if (bumenMapper.selectFuZeRen(bumen) != null) {
                msg = "该员工是其他部门的负责人！";
                return msg;
            }
            //通过部门名称查询部门是否存在，不存在时再新增部门
            if (bumenMapper.selectOne(bumen) == null || bumenMapper.selectOne(bumen) == 0) {
                bumen.setDangAnId(renyuandangan.getId());
                bumen.setChuangJianShiJian(new Timestamp(System.currentTimeMillis()));
                Integer i = bumenMapper.insertBuMen(bumen);
                if (i == 0) {
                    msg = "部门添加失败！";
                } else {
                    msg = "部门添加成功！";
                }
            } else {
                msg = "部门已存在！";
            }
        } else {
            msg = "负责人不存在！";
        }

        return msg;
    }


    public String bianji(Bumenbean bumen) {
        String buMenJianJie = bumen.getBuMenJianJie();
        //查询负责人的信息，如果getId方法不能取到值，则该员工不存在
        Renyuandangan renyuandangan = renyuandanganMapper.selectGeRenChaXun(bumen);
        String msg = "";
        if (renyuandangan != null && renyuandangan.getId() != 0) {
            //查看员工是否是部门负责人
            if (bumenMapper.selectFuZeRen(bumen) != null) {
                msg = "该员工是其他部门的负责人！";
                return msg;
            }
            //通过部门名称和部门id查询部门要修改的名称是否存在，不存在时再修改部门
            if (bumenMapper.selectOne(bumen) == null || bumenMapper.selectOne(bumen) == 0) {
                bumen.setDangAnId(renyuandangan.getId());
                Integer i = bumenMapper.updateBuMen(bumen);
                if (i == 0) {
                    msg = "部门编辑失败！";
                } else {
                    msg = "部门编辑成功！";
                }
            } else {
                msg = "部门已存在！";
            }
        } else {
            msg = "负责人不存在！";
        }

        return msg;
    }

    public String shanchu(Bumenbean bum) {
        String msg = "";
        //判断前端是否传入id
        long id = bum.getId();
        if (id == 0) {
            msg = "删除失败!!";
            return msg;
        }

        long i = bumenMapper.deleteBuMen(id);
        if (i == 0) {
            msg = "删除失败";
        } else {
            msg = "删除成功";
        }
        return msg;
    }

    /**
     * 这个方法用来新增操作记录
     *
     * @param request 用来获取session
     */
    public void minGanXinZeng(HttpServletRequest request) {
        mingancaozuoMapper.xinZeng((Mingancaozuo) request.getSession().getAttribute(SessionKey.MGSJ)); // 调用方法，传入对象
    }

}
