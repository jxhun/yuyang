package cn.com.yuyang.service;

import cn.com.yuyang.bean.Bumenbean;
import cn.com.yuyang.pojo.Bumen;
import cn.com.yuyang.pojo.BumenMapper;
import cn.com.yuyang.pojo.Renyuandangan;
import cn.com.yuyang.pojo.RenyuandanganMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        Bumenbean bumen = new Bumenbean();

        return bumenMapper.selectCount(bumen);
    }

    /** 条件查询指定部门信息
     *
     * @param bumen
     * @return
     */
    public List<Bumenbean> chaxun(Bumenbean bumen) {
        System.out.println("Service--------method------shouye");
        String buMenMingCheng = bumen.getBuMenMingCheng();
        if (buMenMingCheng != null && !buMenMingCheng.trim().equals("")) {
            buMenMingCheng = "%" + buMenMingCheng + "%";
            bumen.setBuMenMingCheng(buMenMingCheng);
        }

        return bumenMapper.selectCount(bumen);
    }

    /** 新增部门
     *
     * @param bumen
     * @return
     */

    public Map<String,Object> xinzeng(Bumenbean bumen) {
        System.out.println("Service--------method-----xinzeng");
        String msg = "部门添加失败!!!";
        int Returncode = -1;
        //查询负责人的信息，如果getId方法不能取到值，则该员工不存在
        Renyuandangan renyuandangan = renyuandanganMapper.selectGeRenChaXun(bumen);
        if (renyuandangan!=null && renyuandangan.getId()!= 0) {
            bumen.setDangAnId(renyuandangan.getId());
            //查看员工是否是其他部门负责人
            if(bumenMapper.selectFuZeRen(bumen)!=null){
                msg = "该员工是其他部门的负责人！";
                Returncode = -22;
            }
            //通过部门名称查询部门是否存在，不存在时再新增部门
            else if (bumenMapper.selectOne(bumen)==null || bumenMapper.selectOne(bumen)==0) {
                bumen.setDangAnId(renyuandangan.getId());
                bumen.setChuangJianShiJian(new Timestamp(System.currentTimeMillis()));
                Integer i = bumenMapper.insertBuMen(bumen);
                if (i != null) {
                    //将新增部门的ID传入bean中，再将部门负责人的部门ID改为新部门的ID
                    bumen.setId(bumenMapper.selectOne(bumen));
                    if(renyuandanganMapper.updateYuanGongBuMen(bumen)!=null) {
                        msg = "部门添加成功！";
                        Returncode = 200;
                    }
                    else{
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
        Map<String,Object> map = new HashMap<>();
        map.put("Returncode",Returncode);
        map.put("msg",msg);
        return map;
    }


    public Map<String,Object> bianji(Bumenbean bumen) {
        System.out.println("Service--------method-----xinzeng");
        String msg = "部门编辑失败!!!";
        int Returncode = -1;
        //查询负责人的信息，如果getId方法不能取到值，则该员工不存在
        Renyuandangan renyuandangan = renyuandanganMapper.selectGeRenChaXun(bumen);
        if (renyuandangan!=null && renyuandangan.getId()!= 0) {
            //判断负责人是否改变
            if(renyuandangan.getId()!=bumen.getDangAnId()){
                bumen.setDangAnId(renyuandangan.getId());
                //如果负责人没有改变，则查看员工是否是部门负责人
                if(bumenMapper.selectFuZeRen(bumen)!=null ){
                    msg = "该员工是其他部门的负责人！";
                    Returncode = -22;
                }
            }
            //通过部门名称和部门id查询部门要修改的名称是否存在，不存在时再修改部门
            if (bumenMapper.selectOne(bumen)==null || bumenMapper.selectOne(bumen)==0) {
                bumen.setDangAnId(renyuandangan.getId());
                if (bumenMapper.updateBuMen(bumen) != null && renyuandanganMapper.updateYuanGongBuMen(bumen)!=null) {

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
        Map<String,Object> map = new HashMap<>();
        map.put("Returncode",Returncode);
        map.put("msg",msg);
        return map;
    }

    public Map<String,Object> shanchu(Bumenbean bum){
        String msg = "删除失败!!";
        int Returncode = -1;
        //判断前端是否传入id
        long id = bum.getId();
        if(id==0){
            msg = "没有传入部门ID!!";
            Returncode = -25;
        }
        //查找该部门的人数,d当人数为0时，才能执行删除
        if(bumenMapper.selectCount(bum)!=null && bumenMapper.selectCount(bum).get(0).getCount()==1) {
            long i = bumenMapper.deleteBuMen(id);
            bum.setId(-1);
            long i2 = renyuandanganMapper.updateYuanGongBuMen(bum);
            if (i != 0 && i2!=0) {
                msg = "删除成功";
                Returncode = 200;
            }
        }else{
            msg = "该部门还有员工";
            Returncode = -26;
        }
        Map<String,Object> map = new HashMap<>();
        map.put("Returncode",Returncode);
        map.put("msg",msg);
        return map;
    }


}
