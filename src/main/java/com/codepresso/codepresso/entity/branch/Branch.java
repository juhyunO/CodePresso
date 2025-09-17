package com.codepresso.codepresso.entity.branch;

import com.codepresso.codepresso.entity.order.Orders;
import jakarta.persistence.*;
import jakarta.persistence.criteria.Order;
import lombok.*;
import java.time.LocalTime;
import java.util.List;

@NoArgsConstructor @AllArgsConstructor @Builder
@Getter @Setter
@Table(name = "branch")
@Entity
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "branch_id")
    private Long id;

    @Column(name = "branch_name", length = 100)
    private String branchName;

    @Column(name = "address", length = 255)
    private String address;

    @Column(name = "opening_time")
    private LocalTime openingTime;

    @Column(name = "closing_time")
    private LocalTime closingTime;

    @Column(name = "open")
    private Boolean isOpen;   // 규칙에 맞게 Boolean → is 접두사

    @Column(name = "branch_number", length = 20)
    private String branchNumber;

    // 연관관계: Branch ↔ OrderMaster (1:N)
    @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Orders> orders = new java.util.ArrayList<>();
}

