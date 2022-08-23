package org.hzero.service.app.service;

import java.time.LocalDate;
import java.util.List;

import org.hzero.service.domain.entity.PurchaseOrder;
import org.hzero.service.domain.entity.Store;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.hzero.service.domain.vo.RepertoryParam;
import org.springframework.http.ResponseEntity;

/**
 * 应用服务
 *
 * @author yuji.sun@zknow.com 2022-08-21 10:41:27
 */
public interface StoreService {

    /**
     * 查询所有仓库
     * @return
     */
    ResponseEntity<List<Store>> list();
}
