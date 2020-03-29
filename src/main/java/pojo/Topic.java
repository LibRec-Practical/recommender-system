package pojo;

import java.math.BigDecimal;
import java.util.Date;

public class Topic {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column librecmall_topic.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column librecmall_topic.title
     *
     * @mbggenerated
     */
    private String title;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column librecmall_topic.subtitle
     *
     * @mbggenerated
     */
    private String subtitle;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column librecmall_topic.price
     *
     * @mbggenerated
     */
    private BigDecimal price;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column librecmall_topic.read_count
     *
     * @mbggenerated
     */
    private String readCount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column librecmall_topic.pic_url
     *
     * @mbggenerated
     */
    private String picUrl;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column librecmall_topic.sort_order
     *
     * @mbggenerated
     */
    private Integer sortOrder;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column librecmall_topic.goods
     *
     * @mbggenerated
     */
    private String goods;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column librecmall_topic.add_time
     *
     * @mbggenerated
     */
    private Date addTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column librecmall_topic.update_time
     *
     * @mbggenerated
     */
    private Date updateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column librecmall_topic.deleted
     *
     * @mbggenerated
     */
    private Boolean deleted;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column librecmall_topic.content
     *
     * @mbggenerated
     */
    private String content;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column librecmall_topic.id
     *
     * @return the value of librecmall_topic.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column librecmall_topic.id
     *
     * @param id the value for librecmall_topic.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column librecmall_topic.title
     *
     * @return the value of librecmall_topic.title
     *
     * @mbggenerated
     */
    public String getTitle() {
        return title;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column librecmall_topic.title
     *
     * @param title the value for librecmall_topic.title
     *
     * @mbggenerated
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column librecmall_topic.subtitle
     *
     * @return the value of librecmall_topic.subtitle
     *
     * @mbggenerated
     */
    public String getSubtitle() {
        return subtitle;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column librecmall_topic.subtitle
     *
     * @param subtitle the value for librecmall_topic.subtitle
     *
     * @mbggenerated
     */
    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle == null ? null : subtitle.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column librecmall_topic.price
     *
     * @return the value of librecmall_topic.price
     *
     * @mbggenerated
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column librecmall_topic.price
     *
     * @param price the value for librecmall_topic.price
     *
     * @mbggenerated
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column librecmall_topic.read_count
     *
     * @return the value of librecmall_topic.read_count
     *
     * @mbggenerated
     */
    public String getReadCount() {
        return readCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column librecmall_topic.read_count
     *
     * @param readCount the value for librecmall_topic.read_count
     *
     * @mbggenerated
     */
    public void setReadCount(String readCount) {
        this.readCount = readCount == null ? null : readCount.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column librecmall_topic.pic_url
     *
     * @return the value of librecmall_topic.pic_url
     *
     * @mbggenerated
     */
    public String getPicUrl() {
        return picUrl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column librecmall_topic.pic_url
     *
     * @param picUrl the value for librecmall_topic.pic_url
     *
     * @mbggenerated
     */
    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl == null ? null : picUrl.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column librecmall_topic.sort_order
     *
     * @return the value of librecmall_topic.sort_order
     *
     * @mbggenerated
     */
    public Integer getSortOrder() {
        return sortOrder;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column librecmall_topic.sort_order
     *
     * @param sortOrder the value for librecmall_topic.sort_order
     *
     * @mbggenerated
     */
    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column librecmall_topic.goods
     *
     * @return the value of librecmall_topic.goods
     *
     * @mbggenerated
     */
    public String getGoods() {
        return goods;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column librecmall_topic.goods
     *
     * @param goods the value for librecmall_topic.goods
     *
     * @mbggenerated
     */
    public void setGoods(String goods) {
        this.goods = goods == null ? null : goods.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column librecmall_topic.add_time
     *
     * @return the value of librecmall_topic.add_time
     *
     * @mbggenerated
     */
    public Date getAddTime() {
        return addTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column librecmall_topic.add_time
     *
     * @param addTime the value for librecmall_topic.add_time
     *
     * @mbggenerated
     */
    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column librecmall_topic.update_time
     *
     * @return the value of librecmall_topic.update_time
     *
     * @mbggenerated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column librecmall_topic.update_time
     *
     * @param updateTime the value for librecmall_topic.update_time
     *
     * @mbggenerated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column librecmall_topic.deleted
     *
     * @return the value of librecmall_topic.deleted
     *
     * @mbggenerated
     */
    public Boolean getDeleted() {
        return deleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column librecmall_topic.deleted
     *
     * @param deleted the value for librecmall_topic.deleted
     *
     * @mbggenerated
     */
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column librecmall_topic.content
     *
     * @return the value of librecmall_topic.content
     *
     * @mbggenerated
     */
    public String getContent() {
        return content;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column librecmall_topic.content
     *
     * @param content the value for librecmall_topic.content
     *
     * @mbggenerated
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}