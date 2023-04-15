package com.erevzin.crmtickethub;

import com.erevzin.crmtickethub.api.PaginatedResponse;
import com.erevzin.crmtickethub.datamodel.CrmTicketAggregate;
import com.erevzin.crmtickethub.datamodel.CrmTicketStatus;
import com.erevzin.crmtickethub.repository.CrmTicketAggregatesRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class CrmTicketsHubIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CrmTicketAggregatesRepository crmTicketAggregatesRepository;

    private List<CrmTicketAggregate> crmTickets;

    @BeforeEach
    void setUp() {
        // Prepare test data
        crmTickets = Arrays.asList(
                CrmTicketAggregate.builder()
                        .errorCode(1)
                        .providerName("Provider1")
                        .relatedCaseIds(Arrays.asList("1", "2"))
                        .affectedProducts(Arrays.asList("Product1", "Product2"))
                        .affectedCustomers(Arrays.asList(1, 2))
                        .status(CrmTicketStatus.NEW)
                        .lastUpdateDate(new Date())
                        .build(),
                CrmTicketAggregate.builder()
                        .errorCode(2)
                        .providerName("Provider2")
                        .relatedCaseIds(Arrays.asList("3", "4"))
                        .affectedProducts(Arrays.asList("Product3", "Product4"))
                        .affectedCustomers(Arrays.asList(3, 4))
                        .status(CrmTicketStatus.NEW)
                        .lastUpdateDate(new Date())
                        .build()
        );
        crmTicketAggregatesRepository.saveAll(crmTickets);
    }

    @AfterEach
    void tearDown() {
        crmTicketAggregatesRepository.deleteAll();
    }

    @Test
    void testFindAll() {
        ParameterizedTypeReference<PaginatedResponse<CrmTicketAggregate>> responseType = new ParameterizedTypeReference<>() { };
        ResponseEntity<PaginatedResponse<CrmTicketAggregate>> result = restTemplate.exchange("/api/crm-tickets/", HttpMethod.GET, null, responseType);
        List<CrmTicketAggregate> crmTicketAggregateList = result.getBody().getContent();
        assertTrue(crmTicketAggregateList.containsAll(crmTickets));
    }

    @Test
    void testRefreshAndFindAll() {
        ParameterizedTypeReference<PaginatedResponse<CrmTicketAggregate>> responseType = new ParameterizedTypeReference<>() { };
        ResponseEntity<PaginatedResponse<CrmTicketAggregate>> result = restTemplate.exchange("/api/crm-tickets/refresh", HttpMethod.GET, null, responseType);
        List<CrmTicketAggregate> crmTicketAggregateList = result.getBody().getContent();
        assertTrue(crmTicketAggregateList.containsAll(crmTickets));
    }

    @Test
    void testSearchByParameters() {
        ParameterizedTypeReference<PaginatedResponse<CrmTicketAggregate>> responseType = new ParameterizedTypeReference<>() { };
        ResponseEntity<PaginatedResponse<CrmTicketAggregate>> result = restTemplate.exchange("/api/crm-tickets/search?errorCode=1&providerName=Provider1&status=NEW", HttpMethod.GET, null, responseType);
        List<CrmTicketAggregate> crmTicketAggregateList = Objects.requireNonNull(result.getBody()).getContent();
        assertTrue(crmTicketAggregateList.contains(crmTickets.get(0)));
        assertFalse(crmTicketAggregateList.contains(crmTickets.get(1)));
    }

}