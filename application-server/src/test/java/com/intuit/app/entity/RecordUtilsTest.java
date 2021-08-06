package com.intuit.app.entity;

import com.intuit.app.entity.business_profile.Address;
import com.intuit.app.entity.business_profile.ApprovalStatus;
import com.intuit.app.entity.business_profile.BusinessProfile;
import com.intuit.app.requests.UpdateBusinessProfileRequest;
import com.intuit.app.service.ProductService;
import com.intuit.app.service.RecordUtils;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@ContextConfiguration(classes = {ProductService.class, RecordUtils.class})
class RecordUtilsTest {

    @Autowired
    RecordUtils recordUtils;

    @MockBean
    ProductService productService;

    @Test
    public void updateOverallStatusEmptyRecordsTest() {
        Record mockRecord = new Record();
        /*Empty Records*/
        HashMap<String, Record.ProductApproval> productApprovalHashMap = new HashMap<>();
        mockRecord.setProductApprovals(productApprovalHashMap);
        mockRecord.setOverallStatus(recordUtils.getOverallApprovalStatus(mockRecord));
        assertEquals(mockRecord.getOverallStatus(), ApprovalStatus.APPROVED);
    }

    @Test
    public void updateOverallStatusNullRecordsTest() {
        Record mockRecord = new Record();
        /*Null records*/
        mockRecord.setOverallStatus(recordUtils.getOverallApprovalStatus(mockRecord));
        assertEquals(mockRecord.getOverallStatus(), ApprovalStatus.APPROVED);
    }

    @Test
    public void updateOverallStatusFailedTest() {
        Record mockRecord = new Record();
        /*With One of the denied records*/
        HashMap<String, Record.ProductApproval> productApprovalHashMap = new HashMap<>();
        productApprovalHashMap.put("test", new Record.ProductApproval("", "", "", ApprovalStatus.APPROVED, 0));
        productApprovalHashMap.put("test2", new Record.ProductApproval("", "", "", ApprovalStatus.APPROVED, 0));
        productApprovalHashMap.put("test3", new Record.ProductApproval("", "", "", ApprovalStatus.DENIED, 0));
        productApprovalHashMap.put("test4", new Record.ProductApproval("", "", "", ApprovalStatus.APPROVED, 0));
        mockRecord.setProductApprovals(productApprovalHashMap);

        mockRecord.setOverallStatus(recordUtils.getOverallApprovalStatus(mockRecord));
        assertEquals(mockRecord.getOverallStatus(), ApprovalStatus.DENIED);


        productApprovalHashMap.clear();
        /*With One of the progress records*/
        productApprovalHashMap.put("test", new Record.ProductApproval("", "", "", ApprovalStatus.APPROVED, 0));
        productApprovalHashMap.put("test2", new Record.ProductApproval("", "", "", ApprovalStatus.PROGRESS, 0));
        productApprovalHashMap.put("test3", new Record.ProductApproval("", "", "", ApprovalStatus.APPROVED, 0));
        productApprovalHashMap.put("test4", new Record.ProductApproval("", "", "", ApprovalStatus.APPROVED, 0));
        mockRecord.setProductApprovals(productApprovalHashMap);

        mockRecord.setOverallStatus(recordUtils.getOverallApprovalStatus(mockRecord));
        assertEquals(mockRecord.getOverallStatus(), ApprovalStatus.DENIED);


        productApprovalHashMap.clear();
        /*With One of the unreachable  record*/
        productApprovalHashMap.put("test", new Record.ProductApproval("", "", "", ApprovalStatus.APPROVED, 0));
        productApprovalHashMap.put("test2", new Record.ProductApproval("", "", "", ApprovalStatus.NOT_REACHABLE, 0));
        productApprovalHashMap.put("test3", new Record.ProductApproval("", "", "", ApprovalStatus.APPROVED, 0));
        productApprovalHashMap.put("test4", new Record.ProductApproval("", "", "", ApprovalStatus.APPROVED, 0));
        mockRecord.setProductApprovals(productApprovalHashMap);

        mockRecord.setOverallStatus(recordUtils.getOverallApprovalStatus(mockRecord));
        assertEquals(mockRecord.getOverallStatus(), ApprovalStatus.DENIED);


    }

    @Test
    public void updateOverallStatusSuccessTest() {
        Record mockRecord = new Record();
        /*With all the successful records*/
        HashMap<String, Record.ProductApproval> productApprovalHashMap = new HashMap<>();
        productApprovalHashMap.put("test", new Record.ProductApproval("", "", "", ApprovalStatus.APPROVED, 0));
        productApprovalHashMap.put("test2", new Record.ProductApproval("", "", "", ApprovalStatus.APPROVED, 0));
        productApprovalHashMap.put("test3", new Record.ProductApproval("", "", "", ApprovalStatus.APPROVED, 0));
        productApprovalHashMap.put("test4", new Record.ProductApproval("", "", "", ApprovalStatus.APPROVED, 0));

        mockRecord.setProductApprovals(productApprovalHashMap);

        mockRecord.setOverallStatus(recordUtils.getOverallApprovalStatus(mockRecord));
        assertEquals(mockRecord.getOverallStatus(), ApprovalStatus.APPROVED);

    }

    @Test
    public void createRecordTest() {

        BusinessProfile businessProfile = new BusinessProfile.BusinessProfileBuilder()
                .address(new Address())
                .companyName("companyName")
                .email("email")
                .website("website")
                .build();

        UpdateBusinessProfileRequest updateBusinessProfileRequest = new UpdateBusinessProfileRequest("customerId", businessProfile);

        Subscription subscription = new Subscription();
        List<Subscription.ProductSub> productSubList = List.of(
                new Subscription.ProductSub("id1", new Date().getTime()),
                new Subscription.ProductSub("id2", new Date().getTime()),
                new Subscription.ProductSub("id3", new Date().getTime())
        );

        when(productService.findOneById((ArgumentMatchers.eq("id1"))))
                .thenReturn(Mono.just(new Product("id1", "id1", "id1", "id1", true)));

        when(productService.findOneById((ArgumentMatchers.eq("id2"))))
                .thenReturn(Mono.just(new Product("id2", "id2", "id2", "id2", false)));

        when(productService.findOneById((ArgumentMatchers.eq("id3"))))
                .thenReturn(Mono.just(new Product("id3", "id3", "id3", "id3", true)));

        subscription.setProductsSubscribed(productSubList);

        Record actualRecord = recordUtils.createRecord(updateBusinessProfileRequest, subscription);

        assertEquals(actualRecord.getOverallStatus(), ApprovalStatus.PROGRESS);
        assertEquals(actualRecord.getCustomerId(), updateBusinessProfileRequest.getCustomerId());
        assertEquals(actualRecord.getProductApprovals().size(), (int) productSubList.stream().filter(new Predicate<Subscription.ProductSub>() {
            @Override
            public boolean test(Subscription.ProductSub productSub) {
                return productSub != null && Objects.requireNonNull(productService.findOneById(productSub.getProductId()).block()).isActive();
            }
        }).count());


    }
}