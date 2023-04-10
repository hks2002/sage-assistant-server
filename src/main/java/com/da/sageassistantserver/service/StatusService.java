/*********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                            *
 * @CreatedDate           : 2022-03-26 17:57:00                                                                      *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                            *
 * @LastEditDate          : 2023-06-20 16:50:34                                                                      *
 * @FilePath              : src/main/java/com/da/sage/assistant/service/StatusService.java                           *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                          *
 ********************************************************************************************************************/

package com.da.sageassistantserver.service;

import com.da.sageassistantserver.dao.StatusMapper;
import com.da.sageassistantserver.model.TobeClosedWO;
import com.da.sageassistantserver.model.TobeDealWithOrderLine;
import com.da.sageassistantserver.model.TobeDelivery;
import com.da.sageassistantserver.model.TobePurchaseBom;
import com.da.sageassistantserver.model.TobeReceive;
import com.da.sageassistantserver.model.TobeTrackingBOMLine;
import com.da.sageassistantserver.model.TobeTrackingPurchaseOrderLine;
import com.da.sageassistantserver.model.TobeTrackingQALine;
import com.da.sageassistantserver.model.TobeTrackingReceiptLine;
import com.da.sageassistantserver.model.TobeTrackingSalesOrderLine;
import com.da.sageassistantserver.utils.Utils;
import java.math.BigDecimal;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatusService {

    private static final Logger log = LogManager.getLogger();

    @Autowired
    private StatusMapper statusMapper;

    @Autowired
    private CurrencyService currencyService;

    public List<TobeDelivery> findTobeDeliveryBySite(String Site) {
        List<TobeDelivery> listPage = statusMapper.findTobeDeliveryBySite(Site);

        for (TobeDelivery o : listPage) {
            // log.debug("ooo:" + o.toString());
            String key = o.getCurrency() + "USD" + Utils.formatDate(o.getOrderDate());
            // log.debug("key:" + key);
            try {
                o.setRate(Float.parseFloat(currencyService.cache.get(key)));
                // log.debug("Rate:" + o.getRate());
            } catch (NumberFormatException e) {
                log.error(e.getMessage());
            }
            // o.setUSD(o.getNetPrice().multiply(new BigDecimal(o.getRate())));
        }

        return listPage;
    }

    public List<TobeReceive> findTobeReceiveBySite(String Site) {
        List<TobeReceive> listPage = statusMapper.findTobeReceiveBySite(Site);

        for (TobeReceive o : listPage) {
            String key = o.getCurrency() + "USD" + Utils.formatDate(o.getOrderDate());
            // log.debug("key:" + key);
            try {
                o.setRate(Float.parseFloat(currencyService.cache.get(key)));
                // log.debug("Rate:" + o.getRate());
            } catch (NumberFormatException e) {
                log.error(e.getMessage());
            }
            o.setUSD(o.getNetPrice().multiply(new BigDecimal(o.getRate())));
        }

        return listPage;
    }

    public List<TobeDealWithOrderLine> findTobeDealWithOrderLineBySite(String Site) {
        List<TobeDealWithOrderLine> listPage = statusMapper.findTobeDealWithOrderLineBySite(Site);

        return listPage;
    }

    public List<TobePurchaseBom> findTobePurchaseBomBySite(String Site) {
        List<TobePurchaseBom> listPage = statusMapper.findTobePurchaseBomBySite(Site);

        return listPage;
    }

    public List<TobeClosedWO> findTobeClosedWOBySite(String Site) {
        List<TobeClosedWO> listPage = statusMapper.findTobeClosedWOBySite(Site);

        return listPage;
    }

    public Integer findTobeTrackingSalesOrderLineCnt(
        String Site,
        String OrderType,
        String CustomerCode,
        String VendorCode
    ) {
        Integer cnt = statusMapper.findTobeTrackingSalesOrderLineCntBySite(Site, OrderType, CustomerCode, VendorCode);

        return cnt;
    }

    public List<TobeTrackingSalesOrderLine> findTobeTrackingSalesOrderLine(
        String Site,
        String OrderType,
        String CustomerCode,
        String VendorCode,
        String OrderBy,
        String Descending,
        Integer Offset,
        Integer Limit
    ) {
        List<TobeTrackingSalesOrderLine> listPage = statusMapper.findTobeTrackingSalesOrderLineBySite(
            Site,
            OrderType,
            CustomerCode,
            VendorCode,
            OrderBy,
            Descending,
            Offset,
            Limit
        );

        return listPage;
    }

    public List<TobeTrackingBOMLine> findTobeTrackingBOMLine(
        String Site,
        String OrderType,
        String CustomerCode,
        String VendorCode,
        String OrderBy,
        String Descending,
        Integer Offset,
        Integer Limit
    ) {
        List<TobeTrackingBOMLine> listPage = statusMapper.findTobeTrackingBOMLineBySite(
            Site,
            OrderType,
            CustomerCode,
            VendorCode,
            OrderBy,
            Descending,
            Offset,
            Limit
        );

        return listPage;
    }

    public List<TobeTrackingPurchaseOrderLine> findTobeTrackingPurchaseOrderLine(
        String Site,
        String OrderType,
        String CustomerCode,
        String VendorCode,
        String OrderBy,
        String Descending,
        Integer Offset,
        Integer Limit
    ) {
        List<TobeTrackingPurchaseOrderLine> listPage = statusMapper.findTobeTrackingPurchaseOrderLineBySite(
            Site,
            OrderType,
            CustomerCode,
            VendorCode,
            OrderBy,
            Descending,
            Offset,
            Limit
        );

        return listPage;
    }

    public List<TobeTrackingReceiptLine> findTobeTrackingReceiptLine(
        String Site,
        String OrderType,
        String CustomerCode,
        String VendorCode,
        String OrderBy,
        String Descending,
        Integer Offset,
        Integer Limit
    ) {
        List<TobeTrackingReceiptLine> listPage = statusMapper.findTobeTrackingReceiptLineBySite(
            Site,
            OrderType,
            CustomerCode,
            VendorCode,
            OrderBy,
            Descending,
            Offset,
            Limit
        );

        return listPage;
    }

    public List<TobeTrackingQALine> findTobeTrackingQALine(
        String Site,
        String OrderType,
        String CustomerCode,
        String VendorCode,
        String OrderBy,
        String Descending,
        Integer Offset,
        Integer Limit
    ) {
        List<TobeTrackingQALine> listPage = statusMapper.findTobeTrackingQALineBySite(
            Site,
            OrderType,
            CustomerCode,
            VendorCode,
            OrderBy,
            Descending,
            Offset,
            Limit
        );

        return listPage;
    }
}
