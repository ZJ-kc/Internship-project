package org.hzero.service.domain.repository;

import java.util.List;

import org.springframework.http.ResponseEntity;

import org.hzero.mybatis.base.BaseRepository;
import org.hzero.service.domain.entity.PurchaseInfo;
import org.hzero.service.domain.entity.PurchaseOrder;
import org.hzero.service.domain.entity.Repertory;
import org.hzero.service.domain.entity.SaleOrder;
import org.hzero.service.domain.vo.RepertoryParam;

/**
 * 资源库
 *
 * @author yuji.sun@zknow.com 2022-08-20 14:22:57
 */
public interface RepertoryRepository extends BaseRepository<Repertory> {

    /**
     * 物料入库
     * @param purchaseOrderId
     * @param purchaseInfoIds
     */
    void addStorage(Long purchaseOrderId, Long[] purchaseInfoIds);

    /**
     * 分页查询库存信息
     * @param repertoryParam
     * @return
     */
    List<Repertory> getRepertoryPage(RepertoryParam repertoryParam);

    /**
     * 物料发货/出库
     * @param saleOrderId
     * @param saleInfoIds
     * @return
     */
    ResponseEntity<?> outStorage(Long saleOrderId, Long[] saleInfoIds);
}
