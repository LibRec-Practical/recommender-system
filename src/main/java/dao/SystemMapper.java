package dao;

import pojo.System;

public interface SystemMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table librecmall_system
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table librecmall_system
     *
     * @mbggenerated
     */
    int insert(System record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table librecmall_system
     *
     * @mbggenerated
     */
    int insertSelective(System record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table librecmall_system
     *
     * @mbggenerated
     */
    System selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table librecmall_system
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(System record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table librecmall_system
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(System record);
}