package org.hzero.service.app.service.impl;

import org.hzero.core.base.BaseAppService;

import org.hzero.service.app.service.RoleService;
import org.hzero.service.domain.entity.Role;
import org.hzero.service.domain.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 应用服务默认实现
 *
 * @author yuji.sun@zknow.com 2022-08-25 14:40:10
 */
@Service
public class RoleServiceImpl extends BaseAppService implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


}
