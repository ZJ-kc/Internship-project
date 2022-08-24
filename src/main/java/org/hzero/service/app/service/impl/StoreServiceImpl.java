package org.hzero.service.app.service.impl;

import java.time.LocalDate;
import java.util.List;

import io.choerodon.mybatis.pagehelper.PageHelper;
import org.hzero.core.base.BaseAppService;

import org.hzero.core.util.Results;
import org.hzero.mybatis.helper.SecurityTokenHelper;
import org.hzero.service.app.service.StoreService;
import org.hzero.service.domain.entity.PurchaseOrder;
import org.hzero.service.domain.entity.Store;
import org.hzero.service.domain.repository.StoreRepository;
import org.hzero.service.domain.vo.RepertoryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;

/**
 * 应用服务默认实现
 *
 * @author yuji.sun@zknow.com 2022-08-21 10:41:27
 */
@Service
public class StoreServiceImpl extends BaseAppService implements StoreService {

    private final StoreRepository storeRepository;

    @Autowired
    public StoreServiceImpl(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    @Override
    public ResponseEntity<List<Store>> list() {
        return Results.success(storeRepository.selectAll());
    }
}
