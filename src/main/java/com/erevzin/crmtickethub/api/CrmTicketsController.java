package com.erevzin.crmtickethub.api;

import com.erevzin.crmtickethub.datamodel.CrmTicketAggregate;
import com.erevzin.crmtickethub.datamodel.CrmTicketStatus;
import com.erevzin.crmtickethub.logic.CrmTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/crm-tickets")
public class CrmTicketsController {

    private final CrmTicketService crmTicketService;

    @Autowired
    public CrmTicketsController(CrmTicketService crmTicketService) {
        this.crmTicketService = crmTicketService;
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PaginatedResponse<CrmTicketAggregate>> findAll(@RequestParam(defaultValue = "0") Integer page,
                                                            @RequestParam(defaultValue = "10") Integer size) {
        Pageable paging = PageRequest.of(page, size);
        PaginatedResponse<CrmTicketAggregate> crmTickets = crmTicketService.findAll(paging);
        return ResponseEntity.ok(crmTickets);
    }

    @GetMapping(value = "/refresh", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PaginatedResponse<CrmTicketAggregate>> refreshAndFindAll(@RequestParam(defaultValue = "0") Integer page,
                                                                      @RequestParam(defaultValue = "10") Integer size) {
        Pageable paging = PageRequest.of(page, size);
        PaginatedResponse<CrmTicketAggregate> crmTickets = crmTicketService.refreshAndFindAll(paging);
        return ResponseEntity.ok(crmTickets);
    }

    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PaginatedResponse<CrmTicketAggregate>> search(@RequestParam(required = false) Integer errorCode,
                                                           @RequestParam(required = false) String providerName,
                                                           @RequestParam(required = false) CrmTicketStatus status,
                                                           @RequestParam(defaultValue = "0") Integer page,
                                                           @RequestParam(defaultValue = "10") Integer size) {
        Pageable paging = PageRequest.of(page, size);
        PaginatedResponse<CrmTicketAggregate> crmTickets = crmTicketService.searchByParameters(errorCode, providerName, status, paging);
        return ResponseEntity.ok(crmTickets);
    }


}
