package com.fcz.dao.hql;

import java.util.List;

import org.hibernate.Session;
import org.springframework.util.StringUtils;

import com.timevale.realname.dao.pager.Pager;
import com.timevale.realname.dao.support.hql.impl.Property;
import com.timevale.realname.dao.support.hql.impl.Queryer;

public class HqlQueryer {

    private String table;
    // private Session session;
    private Queryer queryer;
    // private boolean closeSession;
    private HQLHelper helper;
    private HQLHelper shelper;

    public HqlQueryer(Session session, String table) {
        this(session, table, false);
    }

    public HqlQueryer(Session session, String table, boolean closeSession) {
        this.table = table;
        this.queryer = new Queryer(null);
        this.helper = new HQLHelper(session, closeSession);
        this.shelper = new HQLHelper(session);
    }

    public Queryer getQueryer() {
        return queryer;
    }

    public class HqlQueryBuilder {

        private DatebaseHelper query;
        private DatebaseHelper statistics;

        public HqlQueryBuilder(DatebaseHelper query,
                DatebaseHelper statistics) {
            this.query = query;
            this.statistics = statistics;
        }

        public <T> List<T> query() {
            return query.query();
        }

        public <T> List<T> query(int index, int count) {
            return query.query(index, count);
        }

        public long statistics() {
            return statistics.statistics();
        }

    }

    public HqlQueryBuilder build(String constructor) {

        String conditionHql = queryer.buildQuery();
        List<Property> properties = queryer.buildProperties();

        DatebaseHelper query = buildQuery(constructor, conditionHql,
                properties);
        DatebaseHelper statistics = buildStatistics(conditionHql, properties);

        return new HqlQueryBuilder(query, statistics);
    }

    public HqlQueryBuilder build() {
        return build("");
    }

    private DatebaseHelper buildQuery(String constructor, String conditionHql,
            List<Property> properties) {

        return build(helper, constructor, conditionHql, properties);
    }

    private DatebaseHelper buildStatistics(String conditionHql,
            List<Property> properties) {
        return build(shelper, "count(id)", conditionHql, properties);
    }

    private DatebaseHelper build(HQLHelper helper, String header,
            String conditionHql, List<Property> properties) {
        StringBuilder hql = new StringBuilder();

        if (!StringUtils.isEmpty(header)) {
            hql.append("select ").append(header).append(' ');
        }

        hql.append("from ").append(table);
        if (conditionHql.length() > 0) {
            hql.append(" where ");
            hql.append(conditionHql);
        }

        return helper.init(hql.toString()).addParameters(properties);
    }

    // private void addParameters(Query query, List<Property> properties) {
    //
    // for (Property item : properties) {
    //
    // if (null == item.val()) {
    // continue;
    // }
    //
    // query.setParameter(item.alia(), item.val());
    // }
    // }

    /***
     * 计算分页
     * @param count
     * @param page
     * @return
     */
    public static String solvePage(int count, int page) {
        StringBuilder builder = new StringBuilder();
        if (page == 0) {
            return builder.append(count).toString();
        } else {
            builder.append(page * count);
            builder.append("," + count);
        }
        return builder.toString();
    }

    public static String solvePage(Pager<?> page) {

        StringBuilder builder = new StringBuilder();

        builder.append("limit ");
        builder.append(page.getPage() * page.getCount());
        builder.append("," + page.getCount());

        return builder.toString();
    }

}
