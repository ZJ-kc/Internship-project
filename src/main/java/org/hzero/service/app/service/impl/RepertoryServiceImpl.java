package org.hzero.service.app.service.impl;

import io.choerodon.mybatis.pagehelper.PageHelper;
import org.hzero.core.base.BaseAppService;

import org.hzero.mybatis.helper.SecurityTokenHelper;
import org.hzero.service.app.service.RepertoryService;
import org.hzero.service.domain.entity.Repertory;
import org.hzero.service.domain.repository.RepertoryRepository;
import org.hzero.service.domain.vo.RepertoryParam;
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
public class RepertoryServiceImpl extends BaseAppService implements RepertoryService {

    private final RepertoryRepository repertoryRepository;

    @Autowired
    public RepertoryServiceImpl(RepertoryRepository repertoryRepository) {
        this.repertoryRepository = repertoryRepository;
    }

    @Override
    public Page<Repertory> list(Long tenantId, RepertoryParam repertoryParam, PageRequest pageRequest) {
        return PageHelper.doPageAndSort(pageRequest, () -> repertoryRepository.getRepertoryPage(repertoryParam));
    }
}
