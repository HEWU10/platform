package com.fcz.dao;

import com.timevale.realname.dao.support.hql.HqlUpdater;
import com.timevale.realname.dao.support.hql.Match;
import com.timevale.realname.dao.support.hql.impl.Queryer;
import esign.utils.exception.SuperException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

/**
 * Created by zhangsan on 2015/8/8.
 */
public abstract class DaoBase<T> {

    @Autowired
    private SessionFactory sessionFactory;

    private String table;

    private String hqlTable;

    private Class<T> entityClass;

    private static final Logger LOGGER = LoggerFactory.getLogger(DaoBase.class);

    /**
     * 使用实体类型进行构造
     * @param entityClass
     */
    public DaoBase(Class<T> entityClass) {

        this.entityClass = entityClass;
        this.table = parseTable(entityClass);
        this.hqlTable = entityClass.getSimpleName();
        LOGGER.info("parsed table for the dao. hql:{}, sql:{}", hqlTable,
                table);
    }

    /**
     * 功能说明：获取当前session
     * @return <br/>
     *         修改历史：<br/>
     *         1.[2016年9月28日下午9:15:45] 创建方法 by Administrator
     */
    protected Session getCurrentSession() {
        Session session = sessionFactory.getCurrentSession();
        return session;
    }

    /**
     * 功能说明：从列表中获取首个元素
     * @param input
     * @return <br/>
     *         修改历史：<br/>
     *         1.[2016年9月28日下午9:15:53] 创建方法 by Administrator
     */
    protected T filter(List<T> input) {
        if (null == input || input.isEmpty()) {
            return null;
        }

        return input.get(0);
    }

    /**
     * 功能说明：插入数据
     * @param t
     * @return <br/>
     *         修改历史：<br/>
     *         1.[2016年9月28日下午9:16:28] 创建方法 by Administrator
     */
    public T insert(T t) {

        this.getCurrentSession().save(t);

        return t;
    }

    /**
     * 功能说明：使用唯一ID更新数据,仅更新实体中非空字段,字段为NULL不更新
     * @param columnName
     * @param uniqueId
     * @param entity
     * @return <br/>
     *         修改历史：<br/>
     *         1.[2016年9月28日下午9:16:34] 创建方法 by Administrator
     */
    public int updateByUniqueId(String columnName, Serializable uniqueId,
            T entity) {

        if (StringUtils.isEmpty(uniqueId) || null == entity) {
            return 0;
        }

        HqlUpdater updater = new HqlUpdater(getCurrentSession(), hqlTable());

        Queryer query = updater.getQueryer();
        query.addFinder(Match.EXACT, columnName, uniqueId);

        try {
            updater.addProperty(entity);
        } catch (SuperException e) {
            LOGGER.error("update entity failed. table:{}, service:{}",
                    hqlTable(), uniqueId);
            LOGGER.error("exception:", e);
            return 0;
        }

        getCurrentSession().flush();

        return checkUpdate(updater.build().excute(), uniqueId);
    }

    /**
     * 功能说明：根据主键ID获取数据
     * @param id
     * @return <br/>
     *         修改历史：<br/>
     *         1.[2016年9月28日下午9:17:12] 创建方法 by Administrator
     */
    public T get(Serializable id) {

        @SuppressWarnings("unchecked")
        T t = (T) this.getCurrentSession().get(entityClass, id);
        return t;
    }

    /**
     * 功能说明：根据多个主键ID获取数据
     * @param ids
     * @return <br/>
     *         修改历史：<br/>
     *         1.[2016年9月28日下午9:17:29] 创建方法 by Administrator
     */
    public List<T> get(List<? extends Serializable> ids) {

        if (null == ids || ids.isEmpty()) {
            return null;
        }

        StringBuilder hql = new StringBuilder();
        hql.append("from ").append(hqlTable).append(" where id in (");

        for (int i = 0; i < ids.size(); i++) {
            hql.append(ids.get(i));

            if (i != ids.size() - 1) {
                hql.append(", ");
            }
        }

        hql.append(')');

        @SuppressWarnings("unchecked")
        List<T> r = this.getCurrentSession().createQuery(hql.toString()).list();
        return r;
    }

    /**
     * 功能说明：根据实体类解析表名,注意必须是hibernate注解定义的实体类
     * @param entityClass
     * @return <br/>
     *         修改历史：<br/>
     *         1.[2016年9月28日下午9:17:43] 创建方法 by Administrator
     */
    public static <T> String parseTable(Class<T> entityClass) {

        Table anno = entityClass.getAnnotation(Table.class);
        if (null == anno) {
            return "";
        }

        return anno.name();
    }

    /**
     * 功能说明：获取HQL描述的表名,即类名
     * @return <br/>
     *         修改历史：<br/>
     *         1.[2016年9月28日下午9:18:12] 创建方法 by Administrator
     */
    public String hqlTable() {
        return hqlTable;
    }

    /**
     * 功能说明：获取SQL描述的表名
     * @return <br/>
     *         修改历史：<br/>
     *         1.[2016年9月28日下午9:18:29] 创建方法 by Administrator
     */
    public String table() {
        return table;
    }

    /**
     * 功能说明：检查更新结果,并对更新结果检查和打印输出
     * @param result
     * @param id
     * @return <br/>
     *         修改历史：<br/>
     *         1.[2016年9月28日下午9:18:38] 创建方法 by Administrator
     */
    protected int checkUpdate(int result, Serializable id) {

        switch (result) {
        case -1:
            LOGGER.warn(
                    "no data need to update because data is empty. table:{}, service:{}",
                    hqlTable(), id);
            break;
        case 0:
            LOGGER.warn(
                    "no data update success because data can not find. table:{}, service:{}",
                    hqlTable(), id);
            break;
        // > 0
        default:
            this.getCurrentSession().clear();
            break;
        }

        return result;
    }
}
