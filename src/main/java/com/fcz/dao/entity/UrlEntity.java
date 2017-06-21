package com.fcz.dao.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 功能说明：url
 *
 * @return <br/>
 * 修改历史：<br/>
 * 1.[2017年06月21日上午22:48] 创建方法 by hw
 */
@Table(name = "url", catalog = "weixin")
@Entity
public class UrlEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "url", length = 100, nullable = false, unique = true)
    private String url;

    @Column(name = "accountId", length = 10)
    private int accountId;

    @Column(name = "count", length = 10)
    private int count;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
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
