package org.hzero.service.infra.repository.impl;

import org.hzero.mybatis.base.impl.BaseRepositoryImpl;
import org.hzero.service.domain.entity.User;
import org.hzero.service.domain.repository.UserRepository;
import org.springframework.stereotype.Component;

/**
 *  资源库实现
 *
 * @author yuji.sun@zknow.com 2022-08-25 14:40:10
 */
@Component
public class UserRepositoryImpl extends BaseRepositoryImpl<User> implements UserRepository {


}
