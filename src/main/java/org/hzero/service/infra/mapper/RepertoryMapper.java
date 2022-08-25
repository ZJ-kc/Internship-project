package org.hzero.service.infra.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.hzero.service.annotation.OpenDataSourceAnnotation;
import org.hzero.service.domain.entity.Repertory;
import io.choerodon.mybatis.common.BaseMapper;
import org.hzero.service.domain.vo.RepertoryParam;
import org.springframework.stereotype.Repository;

/**
 * Mapper
 *
 * @author yuji.sun@zknow.com 2022-08-20 14:22:57
 */
@Repository
@OpenDataSourceAnnotation(value = "hff_service")
public interface RepertoryMapper extends BaseMapper<Repertory> {

    /**
     * 分页查询库存信息
     * @param repertoryParam
     * @return
     */
    List<Repertory> getRepertoryPage(@Param("repertoryParam") RepertoryParam repertoryParam);
}
