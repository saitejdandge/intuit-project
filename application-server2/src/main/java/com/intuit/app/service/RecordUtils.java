package com.intuit.app.service;

import com.intuit.app.entity.Product;
import com.intuit.app.entity.Record;
import com.intuit.app.entity.Subscription;
import com.intuit.app.requests.UpdateBusinessProfileRequest;
import com.intuit.app.entity.business_profile.ApprovalStatus;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class RecordUtils {
    private final ProductService productService;

    public RecordUtils(ProductService productService) {
        this.productService = productService;
    }


    public Record createRecord(UpdateBusinessProfileRequest apiRequest, Subscription subscriptionsOfCustomer) {
        HashMap<String, Record.ProductApproval> map = new HashMap<>();
        if (subscriptionsOfCustomer != null)
            for (Subscription.ProductSub productSub : subscriptionsOfCustomer.getProductsSubscribed()) {
                /*Getting the product from the productId*/
                Product product = productService.findOneById(productSub.getProductId()).block();
                if (product != null && product.isActive())
                    map.put(productSub.getProductId(), new Record.ProductApproval(product.getId(), product.getName(), product.getServiceName(), ApprovalStatus.PROGRESS, new Date().getTime()));
            }
        return new Record(null, apiRequest.getCustomerId(), ApprovalStatus.PROGRESS, map);
    }

    public ApprovalStatus getOverallApprovalStatus(Record record) {
        /*Overall Status*/
        ApprovalStatus overallApprovalStatus = ApprovalStatus.APPROVED;
        if (record.getProductApprovals() != null)
            for (Map.Entry<String, Record.ProductApproval> entry : record.getProductApprovals().entrySet())
                if (!(entry.getValue().getApprovalStatus() == ApprovalStatus.APPROVED)) {
                    overallApprovalStatus = ApprovalStatus.DENIED;
                    break;
                }
        return overallApprovalStatus;
    }

}
