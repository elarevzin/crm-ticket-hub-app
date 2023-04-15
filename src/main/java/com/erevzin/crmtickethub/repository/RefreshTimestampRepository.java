package com.erevzin.crmtickethub.repository;

import com.erevzin.crmtickethub.datamodel.RefreshTimestamp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTimestampRepository extends JpaRepository<RefreshTimestamp, Long> {


}


