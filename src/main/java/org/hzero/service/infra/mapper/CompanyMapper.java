package org.hzero.service.infra.mapper;

import org.hzero.service.annotation.OpenDataSourceAnnotation;
import org.hzero.service.domain.entity.Company;
import io.choerodon.mybatis.common.BaseMapper;

/**
 * Mapper
 *
 * @author yuji.sun@zknow.com 2022-08-20 14:22:57
 */
@OpenDataSourceAnnotation(value = "hff_service")
public interface CompanyMapper extends BaseMapper<Company> {

}
