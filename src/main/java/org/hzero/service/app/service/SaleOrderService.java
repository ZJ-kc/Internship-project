package org.hzero.service.app.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.dom4j.DocumentException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import org.hzero.service.api.dto.SaleOrderDTO;
import org.hzero.service.domain.entity.SaleOrder;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
/**
 * 应用服务
 *
 * @author yuji.sun@zknow.com 2022-08-20 14:22:57
 */
public interface SaleOrderService {

    /**
     * 分页查询销售订单
     * @param organizationId
     * @param saleOrder
     * @param pageRequest
     * @param startDate
     * @param endDate
     * @return
     */
    Page<SaleOrder> list(Long organizationId,String keyword, SaleOrder saleOrder, PageRequest pageRequest, LocalDate startDate, LocalDate endDate);

    /**
     * 根据id查询销售订单
     * @param organizationId
     * @param saleOrderId
     * @return
     */
    ResponseEntity<SaleOrder> getSaleOrderByOrderId(Long organizationId, Long saleOrderId);

    /**
     * 创建销售订单
     * @param organizationId
     * @param saleOrder
     * @return
     */
    ResponseEntity<SaleOrder> addSaleOrder(Long organizationId, SaleOrder saleOrder);

    /**
     * 提交销售订单（改变状态）
     * @param organizationId
     * @param saleOrderId
     * @return
     */
    ResponseEntity<?> updateOrderState(Long organizationId, Long saleOrderId);

    /**
     * 删除销售订单
     * @param organizationId
     * @param saleOrderId
     * @return
     */
    ResponseEntity<?> deleteSaleOrder(Long organizationId, Long saleOrderId);

    /**
     * 导出销售订单
     * @param saleOrderIds
     * @return
     */
    List<SaleOrderDTO> exportSaleOrder(Integer[] saleOrderIds);


    /**
     * 导入销售订单
     * @param file
     * @return
     */
    ResponseEntity<?> importSaleOrder(MultipartFile file);

    /**
     * 打印销售订单
     * @param organizationId
     * @param saleOrderId
     */
    void exportSalePdf(Long organizationId, Long saleOrderId, HttpServletResponse response) throws IOException, DocumentException, com.itextpdf.text.DocumentException;
}
