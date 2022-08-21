package org.hzero.service.app.service.impl;

import org.hzero.core.base.BaseAppService;

import org.hzero.mybatis.helper.SecurityTokenHelper;
import org.hzero.service.app.service.SaleInfoService;
import org.hzero.service.domain.entity.SaleInfo;
import org.hzero.service.domain.repository.SaleInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;

/**
 * 应用服务默认实现
 *
 * @author yuji.sun@zknow.com 2022-08-20 14:22:57
 */
@Service
public class SaleInfoServiceImpl extends BaseAppService implements SaleInfoService {

    private final SaleInfoRepository saleInfoRepository;

    @Autowired
    public SaleInfoServiceImpl(SaleInfoRepository saleInfoRepository) {
        this.saleInfoRepository = saleInfoRepository;
    }


}
