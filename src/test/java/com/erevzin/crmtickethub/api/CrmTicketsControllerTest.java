package com.erevzin.crmtickethub.api;

import com.erevzin.crmtickethub.datamodel.CrmTicketAggregate;
import com.erevzin.crmtickethub.datamodel.CrmTicketStatus;
import com.erevzin.crmtickethub.logic.CrmTicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class CrmTicketsControllerTest {

    private CrmTicketsController crmTicketsController;

    @Mock
    private CrmTicketService crmTicketService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        crmTicketsController = new CrmTicketsController(crmTicketService);
    }

    @Test
    void testFindAll() {
        List<CrmTicketAggregate> crmTicketAggregates = new ArrayList<>();
        crmTicketAggregates.add(new CrmTicketAggregate());
        crmTicketAggregates.add(new CrmTicketAggregate());
        PaginatedResponse<CrmTicketAggregate> crmTicketAggregatePage = new PaginatedResponse<>(crmTicketAggregates);
        when(crmTicketService.findAll(PageRequest.of(0, 10))).thenReturn(crmTicketAggregatePage);

        ResponseEntity<PaginatedResponse<CrmTicketAggregate>> responseEntity = crmTicketsController.findAll(0, 10);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(crmTicketAggregatePage, responseEntity.getBody());
    }

    @Test
    void testRefreshAndFindAll() {
        List<CrmTicketAggregate> crmTicketAggregates = new ArrayList<>();
        crmTicketAggregates.add(new CrmTicketAggregate());
        crmTicketAggregates.add(new CrmTicketAggregate());
        PaginatedResponse<CrmTicketAggregate> crmTicketAggregatePage = new PaginatedResponse<>(crmTicketAggregates);
        when(crmTicketService.refreshAndFindAll(PageRequest.of(0, 10))).thenReturn(crmTicketAggregatePage);

        ResponseEntity<PaginatedResponse<CrmTicketAggregate>> responseEntity = crmTicketsController.refreshAndFindAll(0, 10);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(crmTicketAggregatePage, responseEntity.getBody());
    }

    @Test
    void testSearch() {
        List<CrmTicketAggregate> crmTicketAggregates = new ArrayList<>();
        crmTicketAggregates.add(new CrmTicketAggregate());
        crmTicketAggregates.add(new CrmTicketAggregate());
        PaginatedResponse<CrmTicketAggregate> crmTicketAggregatePage = new PaginatedResponse<>(crmTicketAggregates);
        when(crmTicketService.searchByParameters(123, "provider", CrmTicketStatus.OPEN, PageRequest.of(0, 10))).thenReturn(crmTicketAggregatePage);

        ResponseEntity<PaginatedResponse<CrmTicketAggregate>> responseEntity = crmTicketsController.search(123, "provider", CrmTicketStatus.OPEN, 0, 10);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(crmTicketAggregatePage, responseEntity.getBody());
    }
}