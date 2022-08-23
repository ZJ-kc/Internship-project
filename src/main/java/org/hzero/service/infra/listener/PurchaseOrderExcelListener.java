package org.hzero.service.infra.listener;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSONObject;
import org.hzero.excel.service.ExcelReadListener;
import org.hzero.service.app.service.impl.PurchaseOrderServiceImpl;
import org.hzero.service.domain.entity.Purchase;
import org.hzero.service.domain.entity.PurchaseOrder;
import org.hzero.service.domain.entity.Store;
import org.hzero.service.domain.entity.Supplier;
import org.hzero.service.domain.repository.*;
import org.hzero.service.infra.util.PurchaseInfoImportConstantUtils;
import org.hzero.service.infra.util.PurchaseOrderImportConstantUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : SunYuji
 * @create 2022/8/22 7:36
 */
public class PurchaseOrderExcelListener implements ExcelReadListener {

    private static final Logger logger = LoggerFactory.getLogger(PurchaseOrderExcelListener.class);

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final SupplierRepository supplierRepository;
    private final StoreRepository storeRepository;
    private final PurchaseRepository purchaseRepository;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    public PurchaseOrderExcelListener(PurchaseOrderRepository purchaseOrderRepository,
                                      SupplierRepository supplierRepository,
                                      StoreRepository storeRepository,
                                      PurchaseRepository purchaseRepository) {
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.supplierRepository = supplierRepository;
        this.storeRepository = storeRepository;
        this.purchaseRepository = purchaseRepository;
    }

    @Override
    public void invoke(JSONObject object) {
        Set<Map.Entry<String, Object>> entries = object.entrySet();
        System.out.println(entries);
        Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setOrganizationId(355L);
        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            String key = entry.getKey();
            if(PurchaseOrderImportConstantUtils.FIELD_PURCHASE_ORDER_DATE.equals(key)) {
                LocalDate dateTime = LocalDate.parse(entry.getValue().toString(), DateTimeFormatter.ofPattern("yyyy/MM/dd"));
                purchaseOrder.setPurchaseOrderDate(dateTime);
                continue;
            }
            if(PurchaseOrderImportConstantUtils.FIELD_STORE_ADDRESS.equals(key)) {
                purchaseOrder.setStoreAddress((String) entry.getValue());
                continue;
            }
            if(PurchaseOrderImportConstantUtils.FIELD_PURCHASE_ORDER_NUMBER.equals(key)) {
                purchaseOrder.setPurchaseOrderNumber((String) entry.getValue());
                continue;
            }
            if(PurchaseOrderImportConstantUtils.FIELD_SUPPLIER_NAME.equals(key)) {
                Supplier supplier = supplierRepository.select(Supplier.FIELD_SUPPLIER_NAME, (String) entry.getValue()).get(0);
                purchaseOrder.setSupplierId(supplier.getSupplierId());
                continue;
            }
            if(PurchaseOrderImportConstantUtils.FIELD_CURRENCY_SYMBOL.equals(key)) {
                purchaseOrder.setCurrency((String) entry.getValue());
                continue;
            }
            if(PurchaseOrderImportConstantUtils.FIELD_STORE_NAME.equals(key)) {
                Store store = storeRepository.select(Store.FIELD_STORE_NAME, (String) entry.getValue()).get(0);
                purchaseOrder.setStoreId(store.getStoreId());
                continue;
            }
            if(PurchaseOrderImportConstantUtils.FIELD_PURCHASE_ORDER_SUM_PRICE.equals(key)) {
                double parseDouble = Double.parseDouble((String) entry.getValue());
                purchaseOrder.setPurchaseOrderSumPrice(BigDecimal.valueOf(parseDouble));
                continue;
            }
            if(PurchaseOrderImportConstantUtils.FIELD_PURCHASE_NAME.equals(key)) {
                Purchase purchase = purchaseRepository.select(Purchase.FIELD_PURCHASE_NAME, (String) entry.getValue()).get(0);
                purchaseOrder.setPurchaseId(purchase.getPurchaseId());
                continue;
            }
            if(PurchaseOrderImportConstantUtils.FIELD_PURCHASE_ORDER_STATE.equals(key)) {
                purchaseOrder.setPurchaseOrderState("未提交".equals((String)entry.getValue()) ? 0 : 1);
                continue;
            }
        }
        purchaseOrderRepository.insertSelective(purchaseOrder);
    }

    @Override
    public void onStart() {
        logger.info("----开始导入采购订单excel----");
    }

    @Override
    public void onFinish() {
        logger.info("----导入采购订单完成----");
    }
}
