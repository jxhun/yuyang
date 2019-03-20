package cn.com.yuyang.pojo;


import java.util.List;

public interface BumenMapper {

    /**
     * 查询部门是否存在
     * @param bumen
     * @return
     */
    Integer selectOne(Bumen bumen);

    /**
     * 插入新增部门
     * @param bumen
     * @return
     */
    Integer insertBuMen(Bumen bumen);

    /**
     * 修改部门
     * @param bumen
     * @return
     */
    Integer updateBuMen(Bumen bumen);

    /**
     * 删除部门
     * @param id
     * @return
     */
    long deleteBuMen(long id);

}