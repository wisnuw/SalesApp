package com.wizh.SalesApp.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "transaction_detail")
@EntityListeners(AuditingEntityListener.class)
public class TransactionDetail {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "product_id")
    private UUID productId;

    @Column(name = "amount")
    private int amount;

    @Column(name = "total_price", precision = 10, scale = 2)
    private BigDecimal totalPrice;

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
