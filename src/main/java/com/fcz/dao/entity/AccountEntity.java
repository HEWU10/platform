package com.fcz.dao.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 功能说明：account
 *
 * @return <br/>
 * 修改历史：<br/>
 * 1.[2017年06月21日上午23:00] 创建方法 by hw
 */
@Entity
@Table(name = "account",catalog = "weixin")
public class AccountEntity implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "name", length = 40)
    private String name;

    @Column(name = "status", length = 2)
    private short status;

    @Column(name = "type", length = 2)
    private short type;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    public short getType() {
        return type;
    }

    public void setType(short type) {
        this.type = type;
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
