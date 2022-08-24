package org.hzero.service.app.service;

import java.util.List;

import org.hzero.service.domain.entity.Material;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.springframework.http.ResponseEntity;

/**
 * 应用服务
 *
 * @author yuji.sun@zknow.com 2022-08-20 14:22:57
 */
public interface MaterialService {

    /**
     * 查询所有物料信息
     * @return
     */
    ResponseEntity<List<Material>> list();
}
