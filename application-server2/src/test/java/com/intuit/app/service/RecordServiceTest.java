package com.intuit.app.service;

import com.intuit.app.entity.Record;
import com.intuit.app.repository.RecordRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ContextConfiguration(classes = {RecordService.class, RecordRepository.class, WebClient.Builder.class, RecordUtils.class})
class RecordServiceTest {

    @Autowired
    RecordService recordService;

    @MockBean
    RecordRepository mockRepo;

    @MockBean
    WebClient.Builder webClientBuilder;

    @MockBean
    RecordUtils recordUtils;

    @Test
    void saveRecordTest() {
        Record record = new Record();
        when(mockRepo.save(any())).thenReturn(Mono.just(record));

        StepVerifier.create(recordService.save(record))
                .expectNext(record)
                .verifyComplete();
    }


}