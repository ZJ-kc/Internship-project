package org.hzero.service.app.service.impl;

import org.hzero.core.base.BaseAppService;

import org.hzero.service.app.service.UserService;
import org.hzero.service.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 应用服务默认实现
 *
 * @author yuji.sun@zknow.com 2022-08-25 14:40:10
 */
@Service
public class UserServiceImpl extends BaseAppService implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


}
