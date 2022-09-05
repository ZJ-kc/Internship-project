package org.hzero.service.infra.repository.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hzero.mybatis.base.impl.BaseRepositoryImpl;
import org.hzero.mybatis.domian.Condition;
import org.hzero.mybatis.util.Sqls;
import org.hzero.service.api.dto.PurchaseInfoDTO;
import org.hzero.service.api.dto.PurchaseOrderDTO;
import org.hzero.service.domain.entity.*;
import org.hzero.service.domain.repository.*;
import org.hzero.service.infra.mapper.SaleOrderMapper;

import org.hzero.service.api.dto.SaleInfoDTO;
import org.hzero.service.api.dto.SaleOrderDTO;
import org.apache.commons.lang.StringUtils;

import org.springframework.stereotype.Component;

/**
 *  资源库实现
 *
 * @author yuji.sun@zknow.com 2022-08-20 14:22:57
 */
@Component
public class SaleOrderRepositoryImpl extends BaseRepositoryImpl<SaleOrder> implements SaleOrderRepository {


    private final SaleOrderMapper saleOrderMapper;

    private final SaleInfoRepository saleInfoRepository;

    private final MaterialRepository materialRepository;

    private final ClientRepository clientRepository;
    private final SaleRepository saleRepository;
    private final CompanyRepository companyRepository;

    public SaleOrderRepositoryImpl(SaleOrderMapper saleOrderMapper, SaleInfoRepository saleInfoRepository, MaterialRepository materialRepository, ClientRepository clientRepository, SaleRepository saleRepository, CompanyRepository companyRepository) {
        this.saleOrderMapper = saleOrderMapper;
        this.saleInfoRepository = saleInfoRepository;
        this.materialRepository = materialRepository;
        this.clientRepository = clientRepository;
        this.saleRepository = saleRepository;
        this.companyRepository = companyRepository;
    }


    @Override
    public List<SaleOrder> getSaleOrderPage(Long tenantId, String keyword, SaleOrder saleOrder, LocalDate startDate, LocalDate endDate) {
        List<SaleOrder> saleOrderPage = this.saleOrderMapper.getSaleOrderPage(tenantId, keyword, saleOrder, startDate, endDate);
        for(SaleOrder order: saleOrderPage) {
            Company company= companyRepository.selectByPrimaryKey(order.getCompanyId());
            Client client=clientRepository.selectByPrimaryKey(order.getClientId());
//            List<Client> clients = clientRepository.select(Client.FIELD_COMPANY_ID, company.getCompanyId());
//            company.setChildren(clients);
            company.setClient(client);
            Sale sale = saleRepository.selectByPrimaryKey(order.getSaleId());
            order.setCompany(company);
//            order.setClient(client);
            order.setSale(sale);
        }
        return saleOrderPage;
    }

    @Override
    public void addSaleOrder(Long tenantId, SaleOrder saleOrder) {

        if(null != saleOrder.getSaleOrderId()){
            // 更新订单信息
            SaleOrder saleOrder1 = this
                    .selectByPrimaryKey(saleOrder.getSaleOrderId());

            if(null != saleOrder.getStoreId()) {
                saleOrder1.setStoreId(saleOrder.getStoreId());
            }

            //计算总金额
            if(null != saleOrder.getSaleOrderSumPrice()){
                saleOrder1.setSaleOrderSumPrice(saleOrder.getSaleOrderSumPrice());
            }

            if(null != saleOrder.getSaleAddress()) {
                saleOrder1.setSaleAddress(saleOrder.getSaleAddress());
            }

            this.updateByPrimaryKey(saleOrder1);

            // 删除订单详情信息
            saleInfoRepository.batchDelete(saleInfoRepository
                    .selectByCondition(Condition.builder(SaleInfo.class)
                            .andWhere(Sqls.custom()
                                    .andEqualTo("saleOrderId", saleOrder.getSaleOrderId()))
                            .build()));
        } else {
            saleOrder.setSaleOrderDate(LocalDate.now());
            saleOrder.setOrganizationId(tenantId);
            this.insertSelective(saleOrder);
        }
        if(saleOrder.getChildren() != null && saleOrder.getChildren().size() > 0) {
            for(SaleInfo saleInfo: saleOrder.getChildren()) {
                saleInfo.setSaleOrderId(saleOrder.getSaleOrderId());
                saleInfoRepository.insertSelective(saleInfo);
            }
        }

    }

    @Override
    public List<SaleOrderDTO> exportSaleOrder(Integer[] saleOrderIds) {
        List<SaleOrderDTO> saleOrderDTOList = new ArrayList<>();
        List<SaleOrder> saleOrderList;

        String ids = StringUtils.join(saleOrderIds, ",");
        if(null == saleOrderIds || 0 == saleOrderIds.length) {
            saleOrderList = this.selectAll();
        } else {
            saleOrderList = this.selectByIds(ids);
        }
        for(SaleOrder order: saleOrderList) {
            SaleOrderDTO saleOrderDTO = new SaleOrderDTO();
            List<SaleInfoDTO> saleInfoDTOList = new ArrayList<>();

            List<SaleInfo> details = saleInfoRepository.select("saleOrderId", order.getSaleOrderId());
            long i = 1;
            for(SaleInfo detail: details) {
                SaleInfoDTO saleInfoDTO = new SaleInfoDTO();

                Material material = materialRepository.select(Material.FIELD_MATERIAL_ID, detail.getMaterialId()).get(0);

                saleInfoDTO.setMaterialCode(material.getMaterialCode());
                saleInfoDTO.setMaterialPrice(order.getCurrency() + material.getMaterialPrice());
                saleInfoDTO.setSaleInfoRemark(detail.getSaleInfoRemark());

                //excel导出金额栏拼接
                saleInfoDTO.setSaleInfoSumPrice(order.getCurrency() + detail.getSaleInfoSumPrice());
                saleInfoDTO.setSaleLineNumber(i++);

                saleInfoDTO.setSaleNumber(detail.getSaleNumber());

                saleInfoDTOList.add(saleInfoDTO);
            }

            Sale sale = saleRepository.select(Sale.FIELD_SALE_ID, order.getSaleId()).get(0);
            Company company = companyRepository.select(Company.FIELD_COMPANY_ID, order.getCompanyId()).get(0);
            Client client = clientRepository.select(Client.FIELD_CLIENT_ID, order.getClientId()).get(0);


            //销售员姓名
            saleOrderDTO.setSaleName(sale.getSaleName());


            saleOrderDTO.setSaleOrderNumber(order.getSaleOrderNumber());
            saleOrderDTO.setCompanyName(company.getCompanyName());
            saleOrderDTO.setClientName(client.getClientName());

            //订单日期
            saleOrderDTO.setSaleOrderDate(order.getSaleOrderDate());

            saleOrderDTO.setSaleInfoList(saleInfoDTOList);

            Integer state=order.getSaleOrderState();
            if(0 == state) {
                saleOrderDTO.setSaleOrderState("未提交");
            } else if(1 == state) {
                saleOrderDTO.setSaleOrderState("待审批");
            } else if(2 == state) {
                saleOrderDTO.setSaleOrderState("审批通过");
            } else if(3 == state) {
                saleOrderDTO.setSaleOrderState("审批拒绝");
            }

            System.out.println(saleOrderDTO);

            saleOrderDTOList.add(saleOrderDTO);
        }
        System.out.println(saleOrderDTOList);
        return saleOrderDTOList;
    }

}
