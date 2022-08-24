package org.hzero.service.infra.listener;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.hzero.excel.service.ExcelReadListener;
import org.hzero.service.domain.entity.*;
import org.hzero.service.domain.repository.*;
import org.hzero.service.infra.util.PurchaseInfoImportConstantUtils;
import org.hzero.service.infra.util.SaleInfoImportConstantUtils;

/**
 * @Author: zj
 * @Date: 2022/8/23
 * @Time: 0:48
 */
public class SaleInfoExcelListener implements ExcelReadListener {

    private static final Logger logger = LoggerFactory.getLogger(SaleInfoExcelListener.class);

    private final MaterialRepository materialRepository;
    private final SaleInfoRepository saleInfoRepository;
    private final SaleOrderRepository saleOrderRepository;

    public SaleInfoExcelListener(MaterialRepository materialRepository, SaleInfoRepository saleInfoRepository, SaleOrderRepository saleOrderRepository) {
        this.materialRepository = materialRepository;
        this.saleInfoRepository = saleInfoRepository;
        this.saleOrderRepository = saleOrderRepository;
    }


    @Override
    public void invoke(JSONObject object) {
        Set<Map.Entry<String, Object>> entries = object.entrySet();
        Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
        SaleInfo saleInfo = new SaleInfo();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            String key = entry.getKey();
            if(SaleInfoImportConstantUtils.FIELD_MATERIAL_CODE.equals(key)) {
                Material material = materialRepository.select(Material.FIELD_MATERIAL_CODE, (String) entry.getValue()).get(0);
                saleInfo.setMaterialId(material.getMaterialId());
            }
            if(SaleInfoImportConstantUtils.FIELD_MATERIAL_PRICE.equals(key)) {

            }
            if(SaleInfoImportConstantUtils.FIELD_MATERIAL_UNIT.equals(key)) {

            }
            if(SaleInfoImportConstantUtils.FIELD_SALE_INFO_LINE_NUMBER.equals(key)) {
                saleInfo.setSaleLineNumber(Long.parseLong((String) entry.getValue()));
            }
            if(SaleInfoImportConstantUtils.FIELD_SALE_INFO_REMARK.equals(key)) {
                saleInfo.setSaleInfoRemark((String) entry.getValue());
            }
            if(SaleInfoImportConstantUtils.FIELD_SALE_INFO_SUM_PRICE.equals(key)) {
                double parseDouble = Double.parseDouble((String) entry.getValue());
                saleInfo.setSaleInfoSumPrice(BigDecimal.valueOf(parseDouble));
            }
            if(SaleInfoImportConstantUtils.FIELD_SALE_NUMBER.equals(key)) {
                saleInfo.setSaleNumber(Long.parseLong((String) entry.getValue()));
            }
            if(SaleInfoImportConstantUtils.FIELD_DELIVERY_STATE.equals(key)) {
                saleInfo.setDeliveryState("未发货".equals((String) entry.getValue()) ? 0 : 1);
            }
            if(SaleInfoImportConstantUtils.FIELD_SALE_ORDER_NUMBER.equals(key)) {
                SaleOrder saleOrder= saleOrderRepository.select(SaleOrder.FIELD_SALE_ORDER_NUMBER, entry.getValue()).get(0);
                saleInfo.setSaleOrderId(saleOrder.getSaleOrderId());
            }
        }
        saleInfoRepository.insertSelective(saleInfo);
    }

    @Override
    public void onStart() {
        logger.info("----开始导入销售订单详情----");
    }

    @Override
    public void onFinish() {
        logger.info("----导入销售订单详情完成----");
    }
}
