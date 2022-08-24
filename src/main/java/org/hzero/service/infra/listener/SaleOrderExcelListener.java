package org.hzero.service.infra.listener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.hzero.excel.service.ExcelReadListener;
import org.hzero.mybatis.domian.Condition;
import org.hzero.mybatis.util.Sqls;
import org.hzero.service.domain.entity.*;
import org.hzero.service.domain.repository.*;
import org.hzero.service.infra.util.PurchaseOrderImportConstantUtils;
import org.hzero.service.infra.util.SaleOrderImportConstantUtils;

/**
 * @Author: zj
 * @Date: 2022/8/23
 * @Time: 0:29
 */
public class SaleOrderExcelListener implements ExcelReadListener {

    private static final Logger logger = LoggerFactory.getLogger(SaleOrderExcelListener.class);

    private final SaleOrderRepository saleOrderRepository;
    private final CompanyRepository companyRepository;
    private final StoreRepository storeRepository;
    private final SaleRepository saleRepository;

    private final ClientRepository clientRepository;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    public SaleOrderExcelListener(SaleOrderRepository saleOrderRepository,
                                      CompanyRepository companyRepository,
                                      StoreRepository storeRepository,
                                  SaleRepository saleRepository,
                                  ClientRepository clientRepository) {
        this.saleOrderRepository = saleOrderRepository;
        this.companyRepository = companyRepository;
        this.storeRepository = storeRepository;
        this.saleRepository =saleRepository;
        this.clientRepository=clientRepository;
    }

    @Override
    public void invoke(JSONObject object) {
        Set<Map.Entry<String, Object>> entries = object.entrySet();
        String companyName = "";
        for(Map.Entry<String, Object> entry: entries) {
            if(SaleOrderImportConstantUtils.FIELD_COMPANY_NAME.equals(entry.getKey())) {
                companyName = (String) entry.getValue();
                break;
            }
        }
//        System.out.println(entries);
        Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
        SaleOrder saleOrder = new SaleOrder();
        saleOrder.setOrganizationId(355L);
        Long companyId = -1L;
        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            String key = entry.getKey();
            if(SaleOrderImportConstantUtils.FIELD_SALE_ORDER_DATE.equals(key)) {
                LocalDate dateTime = LocalDate.parse(entry.getValue().toString(), DateTimeFormatter.ofPattern("yyyy/MM/dd"));
                saleOrder.setSaleOrderDate(dateTime);
                continue;
            }
            if(SaleOrderImportConstantUtils.FIELD_SALE_ADDRESS.equals(key)) {
                saleOrder.setSaleAddress((String) entry.getValue());
                continue;
            }
            if(SaleOrderImportConstantUtils.FIELD_SALE_ORDER_NUMBER.equals(key)) {
                saleOrder.setSaleOrderNumber((String) entry.getValue());
                continue;
            }
            if(SaleOrderImportConstantUtils.FIELD_COMPANY_NAME.equals(key)) {
                List<Company> select = companyRepository.select(Company.FIELD_COMPANY_NAME, (String) entry.getValue());
                Company company = select.get(0);
                companyId = company.getCompanyId();
                saleOrder.setCompanyId(company.getCompanyId());
                continue;
            }

            if(SaleOrderImportConstantUtils.FIELD_CLIENT_NAME.equals(key)) {
                Company company = companyRepository.select(Company.FIELD_COMPANY_NAME, companyName).get(0);
                Client client = clientRepository.selectByCondition(
                        Condition.builder(Client.class)
                                .andWhere(
                                        Sqls.custom()
                                                .andEqualTo(Client.FIELD_COMPANY_ID, company.getCompanyId())
                                                .andEqualTo(Client.FIELD_CLIENT_NAME, (String) entry.getValue())
                                )
                                .build()
                ).get(0);
                System.out.println(client);
//                Client client = clientRepository.select(Client.FIELD_CLIENT_NAME, (String) entry.getValue()).get(0);
                saleOrder.setClientId(client.getClientId());
                continue;
            }

            if(SaleOrderImportConstantUtils.FIELD_CURRENCY_SYMBOL.equals(key)) {
                saleOrder.setCurrency((String) entry.getValue());
                continue;
            }
            if(SaleOrderImportConstantUtils.FIELD_STORE_NAME.equals(key)) {
                Store store = storeRepository.select(Store.FIELD_STORE_NAME, (String) entry.getValue()).get(0);
                saleOrder.setStoreId(store.getStoreId());
                continue;
            }
            if(SaleOrderImportConstantUtils.FIELD_SALE_ORDER_SUM_PRICE.equals(key)) {
                double parseDouble = Double.parseDouble((String) entry.getValue());
                saleOrder.setSaleOrderSumPrice(BigDecimal.valueOf(parseDouble));
                continue;
            }
            if(SaleOrderImportConstantUtils.FIELD_SALE_NAME.equals(key)) {
                Sale sale = saleRepository.select(Sale.FIELD_SALE_NAME, (String) entry.getValue()).get(0);
                saleOrder.setSaleId(sale.getSaleId());
                continue;
            }
            if(SaleOrderImportConstantUtils.FIELD_SALE_ORDER_STATE.equals(key)) {
                String orderState = (String)entry.getValue();
                int state = -1;
                if("未提交".equals(orderState)) {
                    state = 0;
                } else if("待审批".equals(orderState)) {
                    state = 1;
                } else if("审批通过".equals(orderState)) {
                    state = 2;
                } else if("审批拒绝".equals(orderState)) {
                    state = 3;
                }
                saleOrder.setSaleOrderState(state);
            }
        }
        saleOrderRepository.insertSelective(saleOrder);
    }

    @Override
    public void onStart() {
        logger.info("----开始导入销售订单excel----");
    }

    @Override
    public void onFinish() {
        logger.info("----导入销售订单完成----");
    }
}
