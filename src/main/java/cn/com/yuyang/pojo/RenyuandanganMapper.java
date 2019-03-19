package cn.com.yuyang.pojo;


import cn.com.yuyang.bean.Bumenbean;

import java.util.List;

public interface RenyuandanganMapper {
    /**
     * 这个方法用来查询权限
     *
     * @param id
     * @return
     */
    List<Renyuandangan> selectQuanXian(long id);

    /**
     * 查询这个人是否存在
     * @param xingMing
     * @return
     */
    Renyuandangan selectGeRenChaXun(String xingMing);

    /**
     * 查询部门人数
     * @param bumen
     * @return
     */
    List<Bumenbean> selectCount(Bumen bumen);
}