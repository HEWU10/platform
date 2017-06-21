package com.fcz.dao;

import com.timevale.realname.dao.model.tbldisc.IDaoServiceModel;
import com.timevale.realname.dao.support.DaoServiceable;
import com.timevale.realname.entity.ServiceEntity;
import org.springframework.stereotype.Repository;

/**
 * 功能说明：实名认证 服务dao
 * @return <br/>
 *         修改历史：<br/>
 *         1.[2016年08月13日上午16:34] 创建方法 by hw
 */
@Repository
public class ServiceDAO extends DaoServiceable<ServiceEntity>
        implements IDaoServiceModel {

    // private String hqlGetSchedule = "new " + hqlTable() + "("
    // + TBL_COLUMN_NAME_ID + "," + TBL_COLUMN_NAME_SCHEDULE + ")";

    public ServiceDAO() {
        super(ServiceEntity.class);
    }


}
