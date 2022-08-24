package org.hzero.service.app.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import org.hzero.service.domain.entity.Client;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
/**
 * 应用服务
 *
 * @author yuji.sun@zknow.com 2022-08-20 14:22:57
 */
public interface ClientService {

    /**
     * 查询所有客户
     * @return
     */
    ResponseEntity<List<Client>> list();
}
