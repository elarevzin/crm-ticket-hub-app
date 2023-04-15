package com.erevzin.crmtickethub.logic;

import com.erevzin.crmtickethub.api.PaginatedResponse;
import com.erevzin.crmtickethub.datamodel.*;
import com.erevzin.crmtickethub.logic.aggregation.CrmTicketAggregator;
import com.erevzin.crmtickethub.logic.aggregation.ExternalToCrmTicketConverter;
import com.erevzin.crmtickethub.logic.externalfetch.CrmExternalTicketFetcher;
import com.erevzin.crmtickethub.logic.utils.RefreshUtils;
import com.erevzin.crmtickethub.repository.CrmTicketAggregatesRepository;
import com.erevzin.crmtickethub.repository.CrmTicketRepository;
import com.erevzin.crmtickethub.repository.RefreshTimestampRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CrmTicketService {

    public static final String STATUS = "status";
    public static final String PROVIDER_NAME = "providerName";
    public static final String ERROR_CODE = "errorCode";
    private final List<CrmExternalTicketFetcher> externalTicketsFetchers;
    private final ExternalToCrmTicketConverter externalToCrmTicketConverter;
    private final CrmTicketAggregator crmTicketAggregator;
    private final CrmTicketAggregatesRepository crmTicketAggregatesRepository;

    private final CrmTicketRepository crmTicketRepository;
    private final RefreshTimestampRepository refreshTimestampRepository;

    @Autowired
    public CrmTicketService(List<CrmExternalTicketFetcher> externalTicketsFetchers,
                            ExternalToCrmTicketConverter externalToCrmTicketConverter,
                            CrmTicketAggregator crmTicketAggregator,
                            CrmTicketAggregatesRepository crmTicketAggregatesRepository, CrmTicketRepository crmTicketRepository, RefreshTimestampRepository refreshTimestampRepository) {
        this.externalTicketsFetchers = externalTicketsFetchers;
        this.externalToCrmTicketConverter = externalToCrmTicketConverter;
        this.crmTicketAggregator = crmTicketAggregator;
        this.crmTicketAggregatesRepository = crmTicketAggregatesRepository;
        this.crmTicketRepository = crmTicketRepository;
        this.refreshTimestampRepository = refreshTimestampRepository;
    }


    @PostConstruct
    @Scheduled(cron = "${loadCrmTickets.cron}")
    public void loadCrmTickets() {
        List<ExternalCrmTicket> externalCrmTickets = new ArrayList<>();
        for (CrmExternalTicketFetcher externalTicketsFetcher :externalTicketsFetchers) {
            externalCrmTickets.addAll(externalTicketsFetcher.fetchCrmTickets());
        }
        List<CrmTicket> crmTickets = externalCrmTickets.stream()
                .map(externalToCrmTicketConverter).collect(Collectors.toList());
        crmTicketRepository.saveAll(crmTickets);
        List<CrmTicketAggregate> crmTicketAggregates = crmTicketAggregator.apply(crmTickets);
        crmTicketAggregatesRepository.saveAll(crmTicketAggregates);
        refreshTimestampRepository.save(RefreshTimestamp.builder().id(1L).lastRefreshTimestamp(new Date()).build());
    }

    public PaginatedResponse<CrmTicketAggregate> refreshAndFindAll(Pageable pageable) {
        Date lastRefreshTime = refreshTimestampRepository.findById(1L).orElse(RefreshTimestamp.builder().id(1L).lastRefreshTimestamp(RefreshUtils.getDatePlusMinutes(new Date(), -16)).build()).getLastRefreshTimestamp();
        if(RefreshUtils.isValidForRefresh(lastRefreshTime)) {
            loadCrmTickets();
        }
        Page<CrmTicketAggregate> crmTicketAggregatesRepositoryAll = crmTicketAggregatesRepository.findAll(pageable);
        return new PaginatedResponse(crmTicketAggregatesRepositoryAll.getContent(),
                pageable,
                crmTicketAggregatesRepositoryAll.getTotalElements());
    }

    public PaginatedResponse<CrmTicketAggregate> findAll(Pageable pageable) {
        Page<CrmTicketAggregate> crmTicketAggregatesRepositoryAll = crmTicketAggregatesRepository.findAll(pageable);
        return new PaginatedResponse(crmTicketAggregatesRepositoryAll.getContent(),
                pageable,
                crmTicketAggregatesRepositoryAll.getTotalElements());
    }

    public PaginatedResponse<CrmTicketAggregate> searchByParameters(Integer errorCode, String providerName, CrmTicketStatus status, Pageable pageable) {
        Specification<CrmTicketAggregate> spec = Specification.where(null);

        if (errorCode != null) {
            spec = spec.and(hasErrorCode(errorCode));
        }

        if (providerName != null) {
            spec = spec.and(hasProviderName(providerName));
        }

        if (status != null) {
            spec = spec.and(hasStatus(status));
        }

        Page<CrmTicketAggregate> crmTicketAggregatesRepositoryAll = crmTicketAggregatesRepository.findAll(spec, pageable);
        return new PaginatedResponse(crmTicketAggregatesRepositoryAll.getContent(),
                pageable,
                crmTicketAggregatesRepositoryAll.getTotalElements());
    }

    private Specification<CrmTicketAggregate> hasStatus(CrmTicketStatus status) {
        return (root, query, cb) -> cb.equal(root.get(STATUS), status);
    }

    private Specification<CrmTicketAggregate> hasProviderName(String providerName) {
        return (root, query, cb) -> cb.equal(root.get(PROVIDER_NAME), providerName);
    }

    private static Specification<CrmTicketAggregate> hasErrorCode(Integer errorCode) {
        return (root, query, cb) -> cb.equal(root.get(ERROR_CODE), errorCode);
    }

}
