package com.codepresso.codepresso.entity.product;

import com.codepresso.codepresso.entity.member.Member;
import com.codepresso.codepresso.entity.order.OrdersDetail;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "review")
@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    private BigDecimal rating;

    @Column(name = "content", nullable = false, length = 500)
    private String content;

    @Column(name = "photo_url", nullable = false, length = 500)
    private String photoUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_detail_id", nullable = false)
    private OrdersDetail ordersDetail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

}
