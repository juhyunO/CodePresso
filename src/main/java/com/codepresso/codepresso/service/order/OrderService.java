package com.codepresso.codepresso.service.order;

import com.codepresso.codepresso.dto.auth.order.OrderDetailResponse;
import com.codepresso.codepresso.dto.auth.order.OrderListResponse;
import com.codepresso.codepresso.entity.member.Member;
import com.codepresso.codepresso.entity.order.Orders;
import com.codepresso.codepresso.repository.member.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final MemberRepository memberRepository;
//    private final OrdersRepository ordersRepository;
//    private final BranchRepository branchRepository;

    /**
     * 주문 목록 조회
     * */
    public OrderListResponse getOrderList(Long memberId, String period){
        // 존재하는 회원인지 확인 (테스트용으로 회원 검증 임시 비활성화)
        // Member member = memberRepository.findById(memberId)
        //         .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 회원입니다."));

        // 기간 계산
        LocalDateTime startDate = calculateStartDate(period);

        // 실제 구현 ( 나중에 주석 해제 )
//        List<Orders> orders = ordersRepository.findByMemberIdOrderByOrderDateDesc(memberId);
//        List<OrderDetailResponse.OrderSummary> orderSummaries = new ArrayList<>();
//        for (Orders order : orders) {
//            OrderListResponse.OrderSummary orderSummary = convertToOrderSummary(order);
//            orderSummaries.add(orderSummary);
//        }

        // 하드코딩 주문 목록 생성 (나중에 삭제)
        List<OrderListResponse.OrderSummary> orderSummaries = createDummyOrderListByPeriod(period);

        return OrderListResponse.builder()
                .orders(orderSummaries)
                .build();
    }

    /**
     * 주문 상세 조회
     * */
    public OrderDetailResponse getOrderDetail(Long orderId){
        //실제구현 나중에 주석 해제
        // Orders orders = ordersRepository.findById(orderId)
        //         .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 주문입니다."));
        // return convertToOrderDetail(orders);

        // 하드코딩 주문 상세 (나중에 삭제 )
        return createDummyOrderDetail(orderId);
    }

    // ========== 실제 구현 메서드들 (나중에 주석 해제) ==========

    // private OrderListResponse.OrderSummary convertToOrderSummary(Orders orders) {
    //     // 대표 상품명 계산
    //     String representativeItem = calculateRepresentativeItem(orders.getOrdersDetails());
    //
    //     // 총 금액 계산
    //      int totalAmount = 0;
    //      for (OrdersDetail detail : orders.getOrdersDetails()) {
    //      totalAmount += detail.getPrice();
    //}
    //
    //     return OrderListResponse.OrderSummary.builder()
    //             .orderId(orders.getId())
    //             .orderNumber(generateOrderNumber(orders.getId(), orders.getOrderDate()))
    //             .orderDate(orders.getOrderDate())
    //             .productionStatus(orders.getProductionStatus())
    //             .branchName(orders.getBranch().getBranchName())
    //             .isTakeout(orders.getIsTakeout())
    //             .pickupTime(orders.getPickupTime())
    //             .totalAmount(totalAmount)
    //             .representativeItem(representativeItem)
    //             .build();
    // }

    // private OrderDetailResponse convertToOrderDetail(Orders orders) {
    //     // 주문 상품 목록 변환
    //      List<OrderDetailResponse.OrderItem> orderItems = new ArrayList<>();
    //      for (OrdersDetail detail : orders.getOrdersDetails()) {
    //      OrderDetailResponse.OrderItem orderItem = convertToOrderItem(detail);
    //      orderItems.add(orderItem);
    //      }
    //
    //     // 지점 정보
    //     OrderDetailResponse.BranchInfo branch = OrderDetailResponse.BranchInfo.builder()
    //             .branchId(orders.getBranch().getId())
    //             .branchName(orders.getBranch().getBranchName())
    //             .address(orders.getBranch().getAddress())
    //             .branchNumber(orders.getBranch().getBranchNumber())
    //             .build();
    //
    //     // 결제 정보 (Payment 엔티티가 있다면)
    //     OrderDetailResponse.PaymentInfo payment = convertToPaymentInfo(orders);
    //
    //     return OrderDetailResponse.builder()
    //             .orderId(orders.getId())
    //             .orderNumber(generateOrderNumber(orders.getId(), orders.getOrderDate()))
    //             .orderDate(orders.getOrderDate())
    //             .productionStatus(orders.getProductionStatus())
    //             .pickupTime(orders.getPickupTime())
    //             .isTakeout(orders.getIsTakeout())
    //             .requestNote(orders.getRequestNote())
    //             .branch(branch)
    //             .orderItems(orderItems)
    //             .payment(payment)
    //             .build();
    // }

    // private OrderDetailResponse.OrderItem convertToOrderItem(OrdersDetail detail) {
    //     // 옵션 이름들 수집
    //      List<String> optionNames = new ArrayList<>();
    //      if (detail.getOptions() != null) {
    //      for (OrdersItemOptions option : detail.getOptions()) {
    //          optionNames.add(option.getOption().getOptionName());
    //          }
    //      }
    //
    //     return OrderDetailResponse.OrderItem.builder()
    //             .orderDetailId(detail.getId())
    //             .productName(detail.getProduct().getProductName())
    //             .quantity(1) // OrdersDetail에 quantity 필드 추가 시 수정
    //             .unitPrice(detail.getProduct().getPrice())
    //             .totalPrice(detail.getPrice())
    //             .optionNames(optionNames)
    //             .build();
    // }

    // private OrderDetailResponse.PaymentInfo convertToPaymentInfo(Orders orders) {
    //     // Payment 엔티티가 있는 경우
    //     if (orders.getPayment() != null && !orders.getPayment().getPaymentDetail().isEmpty()) {
    //         String paymentMethod = orders.getPayment().getPaymentDetail().get(0)
    //                 .getPaymentType().getPaymentTypeName();
    //         int totalAmount = orders.getPayment().getPaymentDetail().stream()
    //                 .mapToInt(PaymentDetail::getPaymentAmt)
    //                 .sum();
    //         LocalDateTime paymentDate = orders.getPayment().getPaymentDetail().get(0)
    //                 .getPaymentDate();
    //
    //         return OrderDetailResponse.PaymentInfo.builder()
    //                 .paymentMethod(paymentMethod)
    //                 .totalAmount(totalAmount)
    //                 .paymentDate(paymentDate)
    //                 .build();
    //     }
    //
    //     // Payment 정보가 없는 경우 기본값
    //     return OrderDetailResponse.PaymentInfo.builder()
    //             .paymentMethod("결제 정보 없음")
    //             .totalAmount(0)
    //             .paymentDate(orders.getOrderDate())
    //             .build();
    // }

    // private String calculateRepresentativeItem(List<OrdersDetail> orderDetails) {
    //     if (orderDetails.isEmpty()) {
    //         return "주문 상품 없음";
    //     }
    //
    //     String firstProductName = orderDetails.get(0).getProduct().getProductName();
    //     int totalItems = orderDetails.size();
    //
    //     if (totalItems == 1) {
    //         return firstProductName;
    //     } else {
    //         return firstProductName + " 외 " + (totalItems - 1) + "개";
    //     }
    // }

    // private String generateOrderNumber(Long orderId, LocalDateTime orderDate) {
//            String dateStr = orderDate.format(DateTimeFormatter.ofPattern("MMdd"));
    //     // 주문번호 생성 로직 (예: 날짜 기반 + 순번)
    //     // 실제로는 더 복잡한 로직이 필요할 수 있음
    //     int dayOfYear = orderDate.getDayOfYear();
    //     int orderSequence = orderId.intValue() % 100;
    //     return String.format("%04d-%d", dayOfYear, orderSequence);
    // }

    /**
     * 기간별 시작일 계산
     */
    private LocalDateTime calculateStartDate(String period) {
        LocalDateTime now = LocalDateTime.now();

        switch (period) {
            case "1개월":
                return now.minusMonths(1);
            case "3개월":
                return now.minusMonths(3);
            case "전체":
                return LocalDateTime.of(2020, 1, 1, 0, 0); // 충분히 과거 날짜
            default:
                return now.minusMonths(1); // 기본값: 1개월
        }
    }

    /**
     * 주문번호 생성 (하드코딩 버전)
     */
    private String generateDummyOrderNumber(LocalDateTime orderDate, int sequence) {
        // 날짜 형식: MMdd (예: 0906)
        String dateStr = String.format("%02d%02d",
                orderDate.getMonthValue(),
                orderDate.getDayOfMonth());

        return String.format("%s-%d", dateStr, sequence);
    }

    // ========== 하드코딩 더미 데이터 생성 메서드들 (나중에 삭제) ==========

    private List<OrderListResponse.OrderSummary> createDummyOrderListByPeriod(String period) {
        List<OrderListResponse.OrderSummary> orders = new ArrayList<>();

        LocalDateTime now = LocalDateTime.now();

        if ("1개월".equals(period) || "3개월".equals(period) || "전체".equals(period)) {
            // 최근 주문 (오늘)
            orders.add(OrderListResponse.OrderSummary.builder()
                    .orderId(1001L)
                    .orderNumber(generateDummyOrderNumber(now, 3))
                    .orderDate(now.minusHours(2))
                    .productionStatus("제조완료")
                    .branchName("강남대로점")
                    .isTakeout(true)
                    .pickupTime(now.minusHours(1))
                    .totalAmount(2900)
                    .representativeName("시그니처아메리카노")
                    .productImage("/images/signature-americano.jpg")
                    .canReview(true)
                    .build());

            // 어제 주문
            LocalDateTime yesterday = now.minusDays(1);
            orders.add(OrderListResponse.OrderSummary.builder()
                    .orderId(1002L)
                    .orderNumber(generateDummyOrderNumber(yesterday, 2))
                    .orderDate(yesterday.minusHours(3))
                    .productionStatus("픽업완료")
                    .branchName("강남점")
                    .isTakeout(false)
                    .pickupTime(yesterday.minusHours(2))
                    .totalAmount(2800)
                    .representativeName("페퍼민트")
                    .productImage("/images/peppermint.jpg")
                    .canReview(true)
                    .build());
        }

        if ("3개월".equals(period) || "전체".equals(period)) {
            // 2개월 전 주문
            LocalDateTime twoMonthsAgo = now.minusMonths(2);
            orders.add(OrderListResponse.OrderSummary.builder()
                    .orderId(1003L)
                    .orderNumber(generateDummyOrderNumber(twoMonthsAgo, 1))
                    .orderDate(twoMonthsAgo)
                    .productionStatus("픽업완료")
                    .branchName("강남대로점")
                    .isTakeout(true)
                    .pickupTime(twoMonthsAgo.plusMinutes(30))
                    .totalAmount(4300)
                    .representativeName("복숭아에이드 외 1개")
                    .productImage("/images/peach-ade.jpg")
                    .canReview(true)
                    .build());
        }

        if ("전체".equals(period)) {
            // 6개월 전 주문
            LocalDateTime sixMonthsAgo = now.minusMonths(6);
            orders.add(OrderListResponse.OrderSummary.builder()
                    .orderId(1004L)
                    .orderNumber(generateDummyOrderNumber(sixMonthsAgo, 5))
                    .orderDate(sixMonthsAgo)
                    .productionStatus("픽업완료")
                    .branchName("서초점")
                    .isTakeout(false)
                    .pickupTime(sixMonthsAgo.plusMinutes(45))
                    .totalAmount(3500)
                    .representativeName("카페라떼")
                    .productImage("/images/cafe-latte.jpg")
                    .canReview(true)
                    .build());
        }

        return orders;
    }

    private OrderDetailResponse createDummyOrderDetail(Long orderId) {
        // 주문 ID에 따라 다른 상세 정보 반환
        if (orderId.equals(1001L)) {
            return createOrderDetail1001();
        } else if (orderId.equals(1002L)) {
            return createOrderDetail1002();
        } else {
            return createDefaultOrderDetail(orderId);
        }
    }

    private OrderDetailResponse createOrderDetail1001() {
        // 주문 상품 목록
        List<OrderDetailResponse.OrderItem> orderItems = new ArrayList<>();
        orderItems.add(OrderDetailResponse.OrderItem.builder()
                .orderDetailId(1L)
                .productName("시그니처아메리카노")
                .quantity(1)
                .price(2900)
                .totalPrice(2900)
                .optionsName(List.of())
                .build());

        // 지점 정보
        OrderDetailResponse.BranchInfo branch = OrderDetailResponse.BranchInfo.builder()
                .branchId(1L)
                .branchName("강남대로점")
                .address("서울시 강남구")
                .branchNumber("070-4512-0297")
                .build();

        // 결제 정보
        OrderDetailResponse.PaymentInfo payment = OrderDetailResponse.PaymentInfo.builder()
                .paymentMethod("신용/체크카드")
                .totalAmount(2900)
                .paymentDate(LocalDateTime.now().minusHours(2))
                .build();

        return OrderDetailResponse.builder()
                .orderId(1001L)
                .orderNumber(generateDummyOrderNumber(LocalDateTime.now(), 3))
                .orderDate(LocalDateTime.now().minusHours(2))
                .productionStatus("제조완료")
                .pickupTime(LocalDateTime.now().minusHours(1))
                .isTakeout(true)
                .requestNote("")
                .branch(branch)
                .orderItems(orderItems)
                .payment(payment)
                .build();
    }

    private OrderDetailResponse createOrderDetail1002() {
        // 주문 상품 목록
        List<OrderDetailResponse.OrderItem> orderItems = new ArrayList<>();
        orderItems.add(OrderDetailResponse.OrderItem.builder()
                .orderDetailId(2L)
                .productName("페퍼민트")
                .quantity(1)
                .price(2800)
                .totalPrice(2800)
                .optionsName(List.of())
                .build());

        // 지점 정보
        OrderDetailResponse.BranchInfo branch = OrderDetailResponse.BranchInfo.builder()
                .branchId(1L)
                .branchName("강남점")
                .address("서울시 강남구")
                .branchNumber("070-4512-0297")
                .build();

        // 결제 정보
        OrderDetailResponse.PaymentInfo payment = OrderDetailResponse.PaymentInfo.builder()
                .paymentMethod("신용/체크카드")
                .totalAmount(2800)
                .paymentDate(LocalDateTime.now().minusDays(1).minusHours(3))
                .build();

        LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
        return OrderDetailResponse.builder()
                .orderId(1002L)
                .orderNumber(generateDummyOrderNumber(yesterday, 2))
                .orderDate(yesterday.minusHours(3))
                .productionStatus("픽업완료")
                .pickupTime(yesterday.minusHours(2))
                .isTakeout(false)
                .requestNote("")
                .branch(branch)
                .orderItems(orderItems)
                .payment(payment)
                .build();
    }

    private OrderDetailResponse createDefaultOrderDetail(Long orderId) {
        // 기본 주문 상세 (존재하지 않는 주문 ID인 경우)
        List<OrderDetailResponse.OrderItem> orderItems = new ArrayList<>();
        orderItems.add(OrderDetailResponse.OrderItem.builder()
                .orderDetailId(orderId)
                .productName("테스트 상품")
                .quantity(1)
                .price(3000)
                .totalPrice(3000)
                .optionsName(List.of())
                .build());

        OrderDetailResponse.BranchInfo branch = OrderDetailResponse.BranchInfo.builder()
                .branchId(1L)
                .branchName("테스트 지점")
                .address("서울시 테스트구")
                .branchNumber("070-0000-0000")
                .build();

        OrderDetailResponse.PaymentInfo payment = OrderDetailResponse.PaymentInfo.builder()
                .paymentMethod("신용/체크카드")
                .totalAmount(3000)
                .paymentDate(LocalDateTime.now())
                .build();

        return OrderDetailResponse.builder()
                .orderId(orderId)
                .orderNumber("TEST-" + orderId)
                .orderDate(LocalDateTime.now())
                .productionStatus("주문접수")
                .pickupTime(LocalDateTime.now().plusMinutes(30))
                .isTakeout(true)
                .requestNote("")
                .branch(branch)
                .orderItems(orderItems)
                .payment(payment)
                .build();
    }

}
