package cn.com.yuyang.pojo;

import cn.com.yuyang.pojo.Rizhipinglun;

public interface RizhipinglunMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rizhipinglun
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rizhipinglun
     *
     * @mbg.generated
     */
    int insert(Rizhipinglun record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rizhipinglun
     *
     * @mbg.generated
     */
    int insertSelective(Rizhipinglun record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rizhipinglun
     *
     * @mbg.generated
     */
    Rizhipinglun selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rizhipinglun
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(Rizhipinglun record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rizhipinglun
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(Rizhipinglun record);
}