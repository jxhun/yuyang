package cn.com.yuyang.service;

import cn.com.yuyang.bean.Bumenbean;
import cn.com.yuyang.pojo.Bumen;
import cn.com.yuyang.pojo.BumenMapper;
import cn.com.yuyang.pojo.Renyuandangan;
import cn.com.yuyang.pojo.RenyuandanganMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public BuMenGuanLiService() {
        System.out.println("Service");
    }

    @Autowired
    private BumenMapper bumenMapper;

    @Autowired
    private RenyuandanganMapper renyuandanganMapper;

    public RenyuandanganMapper getRenyuandanganMapper() {
        return renyuandanganMapper;
    }

    public BumenMapper getBumenMapper() {
        return bumenMapper;
    }


    /** 查询所有部门信息
     *
     * @return 部门信息
     */
    public List<Bumenbean> shouye() {
        System.out.println("Service--------method------shouye");
        Bumen bumen = new Bumen();

        return renyuandanganMapper.selectCount(bumen);
    }

    /** 条件查询指定部门信息
     *
     * @param bum
     * @return
     */
    public List<Bumenbean> chaxun(Bumen bum) {
        System.out.println("Service--------method------shouye");
        Bumen bumen = new Bumen();
        String buMenMingCheng = bum.getBuMenMingCheng();
        if (buMenMingCheng != null && buMenMingCheng.trim().equals("")) {
            buMenMingCheng = "%" + buMenMingCheng + "%";
            bumen.setBuMenMingCheng(buMenMingCheng);
        }

        return renyuandanganMapper.selectCount(bumen);
    }

    /** 新增部门
     *
     * @param bum
     * @return
     */

    public String xinzeng(Bumenbean bum) {
        System.out.println("Service--------method-----xinzeng");

        String buMenMingCheng = bum.getBuMenMingCheng();
        String buMenJianJie = bum.getBuMenJianJie();
        //查询负责人的信息，如果getId方法不能取到值，则该员工不存在
        Renyuandangan renyuandangan = renyuandanganMapper.selectGeRenChaXun(bum);
        String msg = "";
        if (renyuandangan.getId() != 0) {
            Bumen bumen = new Bumen();
            bumen.setBuMenMingCheng(buMenMingCheng);
            //通过部门名称查询部门是否存在，不存在时再新增部门
            Integer inte = bumenMapper.selectOne(bumen);
            if (inte != 0) {
                bumen.setBuMenJianJie(buMenJianJie);
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


    public String bianji(Bumenbean bum) {
        System.out.println("Service--------method-----xinzeng");
        long id = bum.getId();
        String buMenMingCheng = bum.getBuMenMingCheng();
        String buMenJianJie = bum.getBuMenJianJie();
        //查询负责人的信息，如果getId方法不能取到值，则该员工不存在
        Renyuandangan renyuandangan = renyuandanganMapper.selectGeRenChaXun(bum);
        String msg = "";
        if (renyuandangan.getId() != 0) {
            Bumen bumen = new Bumen();
            bumen.setId(id);
            bumen.setBuMenMingCheng(buMenMingCheng);
            //通过部门名称和部门id查询部门要修改的名称是否存在，不存在时再修改部门
            Integer inte = bumenMapper.selectOne(bumen);
            if (inte != 0) {
                bumen.setBuMenJianJie(buMenJianJie);
                bumen.setDangAnId(renyuandangan.getId());
                bumen.setChuangJianShiJian(new Timestamp(System.currentTimeMillis()));
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

    public String shanchu(Bumenbean bum){
        String msg ="";
        //判断前端是否传入id
        long id = bum.getId();
        if(id==0){
            msg = "删除失败!!";
            return msg;
        }

        long i = bumenMapper.deleteBuMen(id);
        System.out.println(i);
        if(i==0){
            msg="删除失败";
        }else{
            msg="删除成功";
        }
        return msg;
    }


}
