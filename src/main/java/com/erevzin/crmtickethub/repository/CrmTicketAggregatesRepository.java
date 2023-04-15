package com.erevzin.crmtickethub.repository;

import com.erevzin.crmtickethub.datamodel.CrmTicketAggregate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CrmTicketAggregatesRepository extends CrudRepository<CrmTicketAggregate, Long>, PagingAndSortingRepository<CrmTicketAggregate, Long>, JpaSpecificationExecutor<CrmTicketAggregate> {

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    Page<CrmTicketAggregate> findAll(Specification<CrmTicketAggregate> spec, Pageable pageable);
}


