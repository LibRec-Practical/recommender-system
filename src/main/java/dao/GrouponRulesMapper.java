package dao;

import pojo.GrouponRules;

public interface GrouponRulesMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table librecmall_groupon_rules
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table librecmall_groupon_rules
     *
     * @mbggenerated
     */
    int insert(GrouponRules record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table librecmall_groupon_rules
     *
     * @mbggenerated
     */
    int insertSelective(GrouponRules record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table librecmall_groupon_rules
     *
     * @mbggenerated
     */
    GrouponRules selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table librecmall_groupon_rules
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(GrouponRules record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table librecmall_groupon_rules
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(GrouponRules record);
}