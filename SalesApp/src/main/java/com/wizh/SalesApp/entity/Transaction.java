package com.wizh.SalesApp.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Builder
@Table(name = "transactions")
@EntityListeners(AuditingEntityListener.class)
public class Transaction {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "user_id")
    private UUID userId;

    @OneToMany(cascade = CascadeType.PERSIST)
    private List<TransactionDetail> items;

    @Column(name = "grand_total_price", precision = 10, scale = 2)
    private BigDecimal grandTotalPrice;

    @Column(nullable = false, updatable = false)
    @Setter(AccessLevel.PRIVATE)
    @CreatedDate
    private Instant createdAt;

    @Column(name = "created_by")
    private String createdBy;

    @Column(nullable = false)
    @Setter(AccessLevel.PRIVATE)
    @LastModifiedDate
    private Instant updatedAt;

    @Column(name = "updated_by")
    private String updatedBy;
}
