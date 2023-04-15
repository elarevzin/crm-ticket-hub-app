package com.erevzin.crmtickethub.repository;

import com.erevzin.crmtickethub.datamodel.CrmTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrmTicketRepository extends JpaRepository<CrmTicket, Long> {


}


