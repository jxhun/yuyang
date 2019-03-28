package cn.com.yuyang.pojo;


import cn.com.yuyang.bean.Bumenbean;
import cn.com.yuyang.bean.IdBean;
import cn.com.yuyang.bean.KaoQinGuanLiBean;
import cn.com.yuyang.bean.QingJiaShenHeBean;

import java.util.List;

public interface BumenMapper {

    /**
     * 查询部门是否存在
     *
     * @param bumenbean
     * @return
     */
    Integer selectOne(Bumenbean bumenbean);

    /**
     * 插入新增部门
     *
     * @param bumenbean
     * @return
     */
    Integer insertBuMen(Bumenbean bumenbean);

    /**
     * 修改部门
     *
     * @param bumenbean
     * @return
     */
    Integer updateBuMen(Bumenbean bumenbean);

    /**
     * 删除部门
     *
     * @param id
     * @return
     */
    long deleteBuMen(long id);

    List<Bumen> selectBuMen();

    /**
     * 通信录用来查询人员信息
     *
     * @param idBean 传入部门id
     * @return 返回一个部门对象
     */
    Bumen selectRenYuan(IdBean idBean);

    /**
     * 查询所有部门名称及ID
     *
     * @return
     */
    List<Bumen> selectBumen();

    /**
     * 查询部门人数,以及部门信息
     *
     * @param bum
     * @return
     */
    List<Bumenbean> selectCount(Bumenbean bum);

    Bumen selectBuMenJiBie(QingJiaShenHeBean qingJiaShenHeBean);

    Bumen selectBuMenJiBie1(int buMenId);

    List<Bumen> selectShangCengShenPiRen(Integer buMenJiBie);

    /**
     * 查看员工是否是某个部门的负责人
     *
     * @param bumenbean
     * @return
     */
    Integer selectFuZeRen(Bumenbean bumenbean);

}