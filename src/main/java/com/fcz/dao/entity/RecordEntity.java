package com.fcz.dao.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * 功能说明：record
 *
 * @return <br/>
 * 修改历史：<br/>
 * 1.[2017年06月21日上午22:58] 创建方法 by hw
 */
@Entity
@Table(name = "record",catalog = "weixin")
public class RecordEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "userId",length = 50)
    private String userId;

    @Column(name = "url", length = 100, nullable = false)
    private int urlId;

    @Column(name = "createTime", length = 10)
    private Date createTime;

    @Column(name = "modifyTime")
    private Date modifyTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getUrlId() {
        return urlId;
    }

    public void setUrlId(int urlId) {
        this.urlId = urlId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}
