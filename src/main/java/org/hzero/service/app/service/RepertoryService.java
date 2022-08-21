package org.hzero.service.app.service;

import org.hzero.service.domain.entity.PurchaseOrder;
import org.hzero.service.domain.entity.Repertory;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.hzero.service.domain.vo.RepertoryParam;

/**
 * 应用服务
 *
 * @author yuji.sun@zknow.com 2022-08-20 14:22:57
 */
public interface RepertoryService {
    /**
     * 分页查询库存信息
     * @param tenantId
     * @param repertoryParam
     * @param pageRequest
     * @return
     */
    Page<Repertory> list(Long tenantId, RepertoryParam repertoryParam, PageRequest pageRequest);
}
