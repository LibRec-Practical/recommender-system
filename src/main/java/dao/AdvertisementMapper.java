package dao;

import pojo.Advertisement;

public interface AdvertisementMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table librecmall_ad
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table librecmall_ad
     *
     * @mbggenerated
     */
    int insert(Advertisement record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table librecmall_ad
     *
     * @mbggenerated
     */
    int insertSelective(Advertisement record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table librecmall_ad
     *
     * @mbggenerated
     */
    Advertisement selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table librecmall_ad
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(Advertisement record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table librecmall_ad
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(Advertisement record);
}