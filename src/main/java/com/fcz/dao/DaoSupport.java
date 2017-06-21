package com.fcz.dao;

import com.timevale.realname.dao.DaoBase;
import com.timevale.realname.entity.generic.IEntityTime;

import java.util.Date;

/**
 * Created by zhangsan on 2015/8/8.
 */
public abstract class DaoSupport<T extends IEntityTime> extends DaoBase<T> {

    public DaoSupport(Class<T> entityClass) {
        super(entityClass);
    }

    @Override
    public T insert(T t) {

        if (null == t.getCreateTime()) {
            t.setCreateTime(new Date());
        }

        return super.insert(t);
    }

}
