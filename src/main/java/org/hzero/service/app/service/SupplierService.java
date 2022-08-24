package org.hzero.service.app.service;

import java.util.List;

import org.hzero.service.domain.entity.Supplier;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.springframework.http.ResponseEntity;

/**
 * 应用服务
 *
 * @author yuji.sun@zknow.com 2022-08-21 10:41:27
 */
public interface SupplierService {

    /**
     * 查询所有供应商
     * @return
     */
    ResponseEntity<List<Supplier>> list();
}
