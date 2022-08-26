package org.hzero.service.app.service.impl;

import org.hzero.core.base.BaseAppService;

import org.hzero.mybatis.helper.SecurityTokenHelper;
import org.hzero.service.app.service.MemberRoleService;
import org.hzero.service.domain.entity.MemberRole;
import org.hzero.service.domain.repository.MemberRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;

/**
 * 应用服务默认实现
 *
 * @author yuji.sun@zknow.com 2022-08-25 16:06:28
 */
@Service
public class MemberRoleServiceImpl extends BaseAppService implements MemberRoleService {

    private final MemberRoleRepository memberRoleRepository;

    @Autowired
    public MemberRoleServiceImpl(MemberRoleRepository memberRoleRepository) {
        this.memberRoleRepository = memberRoleRepository;
    }


}
