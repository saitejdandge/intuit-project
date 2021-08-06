package com.intuit.app.service;

import com.intuit.app.Constants;
import com.intuit.app.entity.Record;
import com.intuit.app.entity.business_profile.ApprovalStatus;
import com.intuit.app.entity.business_profile.ValidateResponse;
import com.intuit.app.exceptions.BaseException;
import com.intuit.app.exceptions.ErrorCodes;
import com.intuit.app.repository.RecordRepository;
import com.intuit.app.requests.UpdateBusinessProfileRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RecordService {
    private final RecordRepository recordRepository;
    private final WebClient.Builder webClientBuilder;
    private final RecordUtils recordUtils;

    public RecordService(RecordRepository recordRepository, WebClient.Builder webClientBuilder, RecordUtils recordUtils) {
        this.recordRepository = recordRepository;
        this.webClientBuilder = webClientBuilder;
        this.recordUtils = recordUtils;
    }

    public Mono<Record> save(Record record) {
        return recordRepository.save(record);
    }

    public Record requestForProductApprovals(Record record, UpdateBusinessProfileRequest updateBusinessProfileRequest) {
        /*Calling multiple products validate API*/
        List<String> endPoints = record.getProductApprovals().values().stream().map(Record.ProductApproval::getProductServiceName).collect(Collectors.toList());
        Optional<Flux<ValidateResponse>> finalFlux =
                endPoints.stream()
                        .map(serviceName -> webClientBuilder
                                .build()
                                .post()
                                .uri(Constants.buildValidationUrl(serviceName))
                                .body(BodyInserters.fromValue(updateBusinessProfileRequest))
                                .retrieve()
                                .onStatus(HttpStatus::isError, response -> Mono.error(new BaseException(serviceName + ErrorCodes.PRODUCT_SERVICE_DOWN.getMessage(), ErrorCodes.PRODUCT_SERVICE_DOWN.getOpStatus(), ErrorCodes.PRODUCT_SERVICE_DOWN.getStatusCode())))
                                .bodyToFlux(ValidateResponse.class))
                        .reduce(Flux::merge);
        if (finalFlux.isPresent()) {
            Flux<ValidateResponse> responseFlux = finalFlux.get();
            for (ValidateResponse i : responseFlux.log().toIterable()) {

                ApprovalStatus approvalStatus;
                approvalStatus = i.getApprovalStatus() != null ? i.getApprovalStatus() : ApprovalStatus.NOT_REACHABLE;
                Record.ProductApproval productApproval = record.getProductApprovals().get(i.getProductId());
                productApproval.setApprovalStatus(approvalStatus);
                record.getProductApprovals().put(i.getProductId(), productApproval);
            }
        }
        record.setOverallStatus(recordUtils.getOverallApprovalStatus(record));
        return record;
    }


}
