package com.fcz.dao;

import com.timevale.realname.dao.model.tbldisc.IDaoModel;
import com.timevale.realname.entity.generic.IEntity;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.persistence.Table;
import java.util.List;

/**
 * Created by zhangsan on 2015/8/8.
 */
public abstract class DaoGeneric<T extends IEntity> extends DaoSupport<T>
        implements IDaoModel {

    // private String table;
    //
    // private String hqlTable;

    private String hqlDelete;

    private String hqlGetAll;

    private static final Logger LOGGER = LoggerFactory
            .getLogger(DaoGeneric.class);

    public DaoGeneric(Class<T> entityClass) {

        super(entityClass);

        // this.table = parseTable(entityClass);
        // this.hqlTable = entityClass.getSimpleName();
        LOGGER.info("parsed table for the dao. hql:{}, sql:{}", hqlTable(),
                table());

        initHqlStatements();
    }

    public List<T> getAll() {
        return getAll(null, 0, false);
    }

    public List<T> getAll(String orderBy, int limit, boolean desc) {

        StringBuilder hql = new StringBuilder(hqlGetAll);
        if (!StringUtils.isEmpty(orderBy)) {
            hql.append(" order by ").append(orderBy)
                    .append(desc ? " desc" : "");
        }

        if (limit > 0) {
            hql.append(" limit ").append(limit);
        }

        Query q = getCurrentSession().createQuery(hql.toString());

        @SuppressWarnings("unchecked")
        List<T> r = q.list();

        return r;
    }

    public int updateById(String id, T entity) {
        return updateByUniqueId(TBL_COLUMN_NAME_ID, id, entity);
    }

    public int updateById(T entity) {
        return updateById(entity.getId(), entity);
    }

    public void delete(T t) {
        this.getCurrentSession().delete(t);
    }

    public int delete(String id) {

        Query q = getCurrentSession().createQuery(hqlDelete);
        q.setString(TBL_COLUMN_NAME_ID, id);
        return q.executeUpdate();
    }

    public static <T> String parseTable(Class<T> entityClass) {

        Table anno = entityClass.getAnnotation(Table.class);
        if (null == anno) {
            return "";
        }

        return anno.name();
    }

    private void initHqlStatements() {

        hqlDelete = new StringBuilder().append("delete from ")
                .append(hqlTable()).append(" where id = :id").toString();

        hqlGetAll = new StringBuilder().append("from ").append(hqlTable())
                .toString();
    }

}
