package com.erevzin.crmtickethub.logic.aggregation;

import com.erevzin.crmtickethub.datamodel.CrmTicket;
import com.erevzin.crmtickethub.datamodel.CrmTicketAggregate;
import com.erevzin.crmtickethub.datamodel.CrmTicketStatus;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CrmTicketAggregator implements Function<List<CrmTicket>, List<CrmTicketAggregate>> {


    @Override
    public List<CrmTicketAggregate> apply(List<CrmTicket> crmTickets) {
        // Aggregate the data based on error code and provider id
        Map<Pair<Integer, String>, List<CrmTicket>> crmTicketsByErrorCodeAndProviderId =
                crmTickets.stream()
                        .collect(Collectors.groupingBy(
                                ticket -> Pair.of(ticket.getErrorCode() , ticket.getProviderName())));

        // Create the aggregated data objects
        List<CrmTicketAggregate> crmTicketAggregates = new ArrayList<>();
        crmTicketsByErrorCodeAndProviderId.forEach((key, value) -> {
            crmTicketAggregates.add(CrmTicketAggregate.builder()
                    .errorCode(key.getFirst())
                    .providerName(key.getSecond())
                    .relatedCaseIds(getRelatedCaseIds(value))
                    .affectedProducts(getAffectedProducts(value))
                    .status(getStatus(value).orElse(CrmTicketStatus.NOT_DEFINED))
                    .lastUpdateDate(getLastModifiedDate(value).orElse(new Date()))
                    .affectedCustomers(getAffectedCustomers(value))
                    .build());
        });

        return crmTicketAggregates;
    }

    private Optional<CrmTicketStatus> getStatus(List<CrmTicket> value) {
        return value.stream().map(ticket -> Pair.of(ticket.getStatus(), ticket.getLastModifiedDate()))
                .max(Comparator.comparing(Pair::getSecond)).map(Pair::getFirst);
    }

    private Optional<Date> getLastModifiedDate(List<CrmTicket> value) {
        return value.stream().map(CrmTicket::getLastModifiedDate)
                .max(Comparator.comparing(Date::getTime));
    }

    private List<String> getRelatedCaseIds(List<CrmTicket> value) {
        return value.stream()
                .map(ticket -> ticket.getOriginalCaseId() + "-" + ticket.getOriginalSystemName().name())
                .collect(Collectors.toList());
    }

    private List<Integer> getAffectedCustomers(List<CrmTicket> value) {
        return value.stream()
                .map(CrmTicket::getCustomerId)
                .collect(Collectors.toList());
    }

    private List<String> getAffectedProducts(List<CrmTicket> value) {
        return value.stream()
                .map(CrmTicket::getProductName)
                .collect(Collectors.toList());
    }

}