package com.erevzin.crmtickethub.datamodel;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "REFRESH_TIMESTAMP")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class RefreshTimestamp {

    @Column
    @Id
    private Long id = 1L;

    @Column
    private Date lastRefreshTimestamp;

}
