package com.erevzin.crmtickethub.logic;

import com.erevzin.crmtickethub.datamodel.CrmTicket;
import com.erevzin.crmtickethub.datamodel.CrmTicketAggregate;
import com.erevzin.crmtickethub.datamodel.ExternalCrmTicket;
import com.erevzin.crmtickethub.datamodel.RefreshTimestamp;
import com.erevzin.crmtickethub.logic.aggregation.CrmTicketAggregator;
import com.erevzin.crmtickethub.logic.aggregation.ExternalToCrmTicketConverter;
import com.erevzin.crmtickethub.logic.externalfetch.CrmExternalTicketFetcher;
import com.erevzin.crmtickethub.logic.utils.RefreshUtils;
import com.erevzin.crmtickethub.repository.CrmTicketAggregatesRepository;
import com.erevzin.crmtickethub.repository.CrmTicketRepository;
import com.erevzin.crmtickethub.repository.RefreshTimestampRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CrmTicketServiceTest {

    @Mock
    private CrmTicketAggregatesRepository crmTicketAggregatesRepository;

    @Mock
    private CrmTicketRepository crmTicketRepository;

    @Mock
    private RefreshTimestampRepository refreshTimestampRepository;
    @Mock
    private CrmExternalTicketFetcher externalTicketsFetcher;
    @Mock
    private ExternalToCrmTicketConverter externalToCrmTicketConverter;
    @Mock
    private CrmTicketAggregator crmTicketAggregator;

    @Mock
    private RefreshTimestamp timeStamp;

    @Mock
    private Iterable<CrmTicketAggregate> crmTicketAggregatesList;

    private CrmTicketService crmTicketService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        List<CrmExternalTicketFetcher> externalTicketsFetchers = Collections.singletonList(externalTicketsFetcher);
        crmTicketService = new CrmTicketService(
                externalTicketsFetchers,
                externalToCrmTicketConverter,
                crmTicketAggregator,
                crmTicketAggregatesRepository,
                crmTicketRepository, refreshTimestampRepository
        );
    }

    @Test
    void testLoadCrmTickets() {
        // Arrange
        List<CrmTicketAggregate> crmTicketAggregates = Collections.singletonList(new CrmTicketAggregate());
        when(externalTicketsFetcher.fetchCrmTickets()).thenReturn(Collections.singletonList(new ExternalCrmTicket()));
        when(externalToCrmTicketConverter.apply(any(ExternalCrmTicket.class))).thenReturn(new CrmTicket());
        when(crmTicketAggregator.apply(anyList())).thenReturn(crmTicketAggregates);
        when(crmTicketAggregatesRepository.saveAll(crmTicketAggregates)).thenReturn(crmTicketAggregatesList);
        when(refreshTimestampRepository.save(any(RefreshTimestamp.class))).thenReturn(timeStamp);

        // Act
        crmTicketService.loadCrmTickets();

        // Assert
        verify(externalTicketsFetcher, times(1)).fetchCrmTickets();
        verify(externalToCrmTicketConverter, times(1)).apply(any(ExternalCrmTicket.class));
        verify(crmTicketAggregator, times(1)).apply(anyList());
        verify(crmTicketAggregatesRepository, times(1)).saveAll(crmTicketAggregates);
        verify(refreshTimestampRepository, times(1)).save(any(RefreshTimestamp.class));
    }

    @Test
    public void testRefreshAndFindAll_WithoutRefresh() {
        Date currentDate = new Date();
        RefreshTimestamp refreshTimestamp = RefreshTimestamp.builder().id(1L).lastRefreshTimestamp(currentDate).build();
        when(refreshTimestampRepository.findById(any())).thenReturn(java.util.Optional.of(refreshTimestamp));
        when(crmTicketAggregatesRepository.findAll((Pageable) any())).thenReturn(new PageImpl<>(Collections.singletonList(CrmTicketAggregate.builder().build())));

        PageRequest pageRequest = PageRequest.of(0, 10);
        List<CrmTicketAggregate> crmTicketAggregates = crmTicketService.refreshAndFindAll(pageRequest).getContent();

        assertFalse(crmTicketAggregates.isEmpty());
        verify(refreshTimestampRepository, times(1)).findById(1L);
        verify(crmTicketAggregatesRepository, times(1)).findAll(pageRequest);
    }

    @Test
    public void testRefreshAndFindAll_WithRefresh() {
        Date currentDate = new Date();
        RefreshTimestamp refreshTimestamp = RefreshTimestamp.builder().id(1L).lastRefreshTimestamp(RefreshUtils.getDatePlusMinutes(currentDate, -16)).build();
        when(refreshTimestampRepository.findById(any())).thenReturn(java.util.Optional.of(refreshTimestamp));
        when(crmTicketAggregatesRepository.findAll((Pageable) any())).thenReturn(new PageImpl<>(Collections.singletonList(CrmTicketAggregate.builder().build())));

        PageRequest pageRequest = PageRequest.of(0, 10);
        List<CrmTicketAggregate> crmTicketAggregates = crmTicketService.refreshAndFindAll(pageRequest).getContent();

        assertFalse(crmTicketAggregates.isEmpty());
        verify(refreshTimestampRepository, times(1)).findById(1L);
        verify(crmTicketAggregatesRepository, times(1)).findAll(pageRequest);
    }


}