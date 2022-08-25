package org.hzero.service.infra.mapper;

import org.hzero.service.annotation.OpenDataSourceAnnotation;
import org.hzero.service.domain.entity.Supplier;
import io.choerodon.mybatis.common.BaseMapper;

/**
 * Mapper
 *
 * @author yuji.sun@zknow.com 2022-08-21 10:41:27
 */
@OpenDataSourceAnnotation(value = "hff_service")
public interface SupplierMapper extends BaseMapper<Supplier> {

}
