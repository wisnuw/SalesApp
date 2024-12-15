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
@Table(name = "products")
@EntityListeners(AuditingEntityListener.class)
public class Product {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "stock")
    private int stock;

    @Column(name = "price")
    private BigDecimal price;

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
    @Column(name = "deleted_at")
    private Date deletedAt;
    @Column(name = "deleted_by")
    private String deletedBy;
}
