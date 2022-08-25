package org.hzero.service.infra.mapper;

import org.hzero.service.annotation.OpenDataSourceAnnotation;
import org.hzero.service.domain.entity.Card;
import io.choerodon.mybatis.common.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * Mapper
 *
 * @author jiao.zhao@zknown.com 2022-08-23 17:39:12
 */
@Repository
@OpenDataSourceAnnotation(value = "hff_service")
public interface CardMapper extends BaseMapper<Card> {

}
