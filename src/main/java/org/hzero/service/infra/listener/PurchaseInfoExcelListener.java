package org.hzero.service.infra.listener;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSONObject;
import org.hzero.excel.service.ExcelReadListener;
import org.hzero.service.domain.entity.Material;
import org.hzero.service.domain.entity.PurchaseInfo;
import org.hzero.service.domain.entity.PurchaseOrder;
import org.hzero.service.domain.repository.MaterialRepository;
import org.hzero.service.domain.repository.PurchaseInfoRepository;
import org.hzero.service.domain.repository.PurchaseOrderRepository;
import org.hzero.service.infra.util.PurchaseInfoImportConstantUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : SunYuji
 * @create 2022/8/22 8:14
 */
public class PurchaseInfoExcelListener implements ExcelReadListener {

    private static final Logger logger = LoggerFactory.getLogger(PurchaseInfoExcelListener.class);

    private final MaterialRepository materialRepository;
    private final PurchaseInfoRepository purchaseInfoRepository;
    private final PurchaseOrderRepository purchaseOrderRepository;

    public PurchaseInfoExcelListener(MaterialRepository materialRepository,
                                     PurchaseInfoRepository purchaseInfoRepository, PurchaseOrderRepository purchaseOrderRepository) {
        this.materialRepository = materialRepository;
        this.purchaseInfoRepository = purchaseInfoRepository;
        this.purchaseOrderRepository = purchaseOrderRepository;
    }

    @Override
    public void invoke(JSONObject object) {
        Set<Map.Entry<String, Object>> entries = object.entrySet();
        Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
        PurchaseInfo purchaseInfo = new PurchaseInfo();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            String key = entry.getKey();
            if(PurchaseInfoImportConstantUtils.FIELD_MATERIAL_CODE.equals(key)) {
                Material material = materialRepository.select(Material.FIELD_MATERIAL_CODE, (String) entry.getValue()).get(0);
                purchaseInfo.setMaterialId(material.getMaterialId());
            }
            if(PurchaseInfoImportConstantUtils.FIELD_MATERIAL_PRICE.equals(key)) {

            }
            if(PurchaseInfoImportConstantUtils.FIELD_MATERIAL_UNIT.equals(key)) {

            }
            if(PurchaseInfoImportConstantUtils.FIELD_PURCHASE_INFO_LINE_NUMBER.equals(key)) {
                purchaseInfo.setPurchaseLineNumber(Long.parseLong((String) entry.getValue()));
            }
            if(PurchaseInfoImportConstantUtils.FIELD_PURCHASE_INFO_REMARK.equals(key)) {
                purchaseInfo.setPurchaseInfoRemark((String) entry.getValue());
            }
            if(PurchaseInfoImportConstantUtils.FIELD_PURCHASE_INFO_SUM_PRICE.equals(key)) {
                double parseDouble = Double.parseDouble((String) entry.getValue());
                purchaseInfo.setPurchaseInfoSumPrice(BigDecimal.valueOf(parseDouble));
            }
            if(PurchaseInfoImportConstantUtils.FIELD_PURCHASE_NUMBER.equals(key)) {
                purchaseInfo.setPurchaseNumber(Long.parseLong((String) entry.getValue()));
            }
            if(PurchaseInfoImportConstantUtils.FIELD_STORE_STATE.equals(key)) {
                purchaseInfo.setStorageState("未入库".equals((String) entry.getValue()) ? 0 : 1);
            }
            if(PurchaseInfoImportConstantUtils.FIELD_PURCHASE_ORDER_NUMBER.equals(key)) {
                PurchaseOrder purchaseOrder = purchaseOrderRepository.select(PurchaseOrder.FIELD_PURCHASE_ORDER_NUMBER, entry.getValue()).get(0);
                purchaseInfo.setPurchaseOrderId(purchaseOrder.getPurchaseOrderId());
            }
        }
        purchaseInfoRepository.insertSelective(purchaseInfo);
    }

    @Override
    public void onStart() {
        logger.info("----开始导入采购订单详情----");
    }

    @Override
    public void onFinish() {
        logger.info("----导入采购订单详情完成----");
    }
}
