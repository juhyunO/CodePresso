package com.codepresso.codepresso.service.payment;

import com.codepresso.codepresso.dto.auth.payment.CheckoutRequest;
import com.codepresso.codepresso.dto.auth.payment.CheckoutResponse;
import com.codepresso.codepresso.entity.branch.Branch;
import com.codepresso.codepresso.entity.member.Member;
import com.codepresso.codepresso.entity.order.Orders;
import com.codepresso.codepresso.entity.order.OrdersDetail;
import com.codepresso.codepresso.entity.order.OrdersItemOptions;
import com.codepresso.codepresso.entity.product.Product;
import com.codepresso.codepresso.entity.product.ProductOption;
import com.codepresso.codepresso.repository.member.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 결제 서비스
 */

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final MemberRepository memberRepository;
    //private final OrderRepository orderRepository;
    //private final BranchRepository branchRepository;
    //private final ProductRepository productRepository;
    //private final ProductOptionRepository productOptionRepository;

    /**
     * 주문 및 결제 처리 ( 결제없이 주문만 생성 )
     * */
    @Transactional
    public CheckoutResponse processCheckout(CheckoutRequest request) {
        // 1. 회원 및 지점 정보 조회 (테스트용으로 회원 검증 임시 비활성화)
        // Member member = memberRepository.findById(request.getMemberId())
        //         .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다.") );
        Member member = createDummyMember(request.getMemberId());

        // 지점정보조회 ( 일단 하드코딩으로 구현해 놓음 - 연결할 때 주석 해제해서 사용 )
        Branch branch = createDummyBranch(request.getBranchId());
        //        Branch branch = branchRepository.findById(request.getBranchId())
        //                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 지점입니다."));

        // 2. 주문생성
        Orders orders = createOrder(request, member, branch);

        // 3. 주문 상세 생성
        List<OrdersDetail> ordersDetails = createOrderDetails(request.getOrderItems(), orders);
        orders.setOrdersDetails(ordersDetails);

        // 4. 주문 저장 - orderRepository 만든 후 주석 해제
        // Orders savedOrder = ordersRepository.save(orders);
        orders.setId(System.currentTimeMillis());
        Orders savedOrder = orders;

        // 5. 응답 데이터 생성
        return buildCheckoutResponse(savedOrder);
    }

    private Orders createOrder(CheckoutRequest request, Member member, Branch branch) {
        return Orders.builder()
                .member(member)
                .branch(branch)
                .productionStatus("주문접수")
                .isTakeout(request.getIsTakeout())
                .pickupTime(request.getPickupTime())
                .orderDate(LocalDateTime.now())
                .requestNote(request.getRequestNote())
                .isPickup(false) // 초기값: 픽업 전
                .build();
    }

    private List<OrdersDetail> createOrderDetails(List<CheckoutRequest.OrderItem> orderItems, Orders orders) {
        List<OrdersDetail> orderDetails = new ArrayList<>();

        for (CheckoutRequest.OrderItem item : orderItems) {

            // 상품정보 조회 ( 하드코딩 - 나중에 주석 해제 )
            Product product = createDummyProduct(item.getProductId());
            //                Product product = productRepository.findById(item.getProductId())
            //                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다: " + item.getProductId()));


            OrdersDetail orderDetail = OrdersDetail.builder()
                    .orders(orders)
                    .product(product)
                    .price(item.getPrice() * item.getQuantity())
                    .build();

            // 옵션 추가
            if (item.getOptionIds() != null && !item.getOptionIds().isEmpty()) {
                List<OrdersItemOptions> options = createOrderItemOptions(item.getOptionIds(), orderDetail);
                orderDetail.setOptions(options);
            }

            orderDetails.add(orderDetail);
        }

        return orderDetails;
    }

    private List<OrdersItemOptions> createOrderItemOptions(List<Long> optionIds, OrdersDetail orderDetail) {
        List<OrdersItemOptions> orderItemOptions = new ArrayList<>();

        for (Long optionId : optionIds) {
            // 하드코딩 옵션 생성 - 나중에 주석 해제
            ProductOption productOption = createDummyProductOption(optionId);
            //            ProductOption productOption = productOptionRepository.findById(optionId)
            //                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 옵션입니다: " + optionId));

            OrdersItemOptions orderItemOption = OrdersItemOptions.builder()
                    .option(productOption)
                    .ordersDetail(orderDetail)
                    .build();

            orderItemOptions.add(orderItemOption);
        }

        return orderItemOptions;
    }

    private CheckoutResponse buildCheckoutResponse(Orders orders) {
        // 1. 주문 상세 정보 리스트 생성
        List<CheckoutResponse.OrderItem> orderItems = new ArrayList<>();

        // 2. 각 주문 상세를 OrderItem으로 변환
        for (OrdersDetail detail : orders.getOrdersDetails()) {
            // 옵션 이름들 수집
            List<String> optionNames = new ArrayList<>();
            if (detail.getOptions() != null) {
                for (OrdersItemOptions option : detail.getOptions()) {
                    optionNames.add(option.getOption().getOptionName());
                }
            }

            // OrderItem 생성
            CheckoutResponse.OrderItem orderItem = CheckoutResponse.OrderItem.builder()
                    .orderDetailId(detail.getId())
                    .productName(detail.getProduct().getProductName())
                    .quantity(1) // OrdersDetail에 quantity 필드가 없어서 일단 1로 설정
                    .price(detail.getPrice())
                    .optionNames(optionNames)
                    .build();

            orderItems.add(orderItem);
        }

        // 3. 총 주문 금액 계산
        int totalAmount = 0;
        for (OrdersDetail detail : orders.getOrdersDetails()) {
            totalAmount += detail.getPrice();
        }

        // 4. 최종 응답 객체 생성
        return CheckoutResponse.builder()
                .orderId(orders.getId())
                .productionStatus(orders.getProductionStatus())
                .orderDate(orders.getOrderDate())
                .pickupTime(orders.getPickupTime())
                .isTakeout(orders.getIsTakeout())
                .requestNote(orders.getRequestNote())
                .totalAmount(totalAmount)
                .orderItems(orderItems)
                .build();
    }





    // ========== 하드코딩 더미 객체 생성 메서드들 (나중에 삭제) ==========

    private Member createDummyMember(Long memberId) {
        Member member = new Member();
        member.setId(memberId);
        member.setEmail("test@test.com");
        member.setPassword("test123");
        member.setNickname("테스트유저");
        //member.setPhoneNumber("010-1234-5678");
        return member;
    }

    private Branch createDummyBranch(Long branchId) {
        Branch branch = new Branch();
        branch.setId(branchId);
        branch.setBranchName("테스트 지점");
        branch.setAddress("서울시 강남구");
        branch.setIsOpen(true);
        return branch;
    }

    private Product createDummyProduct(Long productId) {
        Product product = new Product();
        product.setId(productId);

        // 간단한 하드코딩 상품명 매핑
        switch (productId.intValue()) {
            case 1:
                product.setProductName("아메리카노");
                product.setPrice(4000);
                break;
            case 2:
                product.setProductName("카페라떼");
                product.setPrice(4500);
                break;
            case 3:
                product.setProductName("망고쥬스");
                product.setPrice(4000);
                break;
            default:
                product.setProductName("테스트 상품_" + productId);
                product.setPrice(3000);
                break;
        }

        return product;
    }

    private ProductOption createDummyProductOption(Long optionId) {
        ProductOption option = new ProductOption();
        option.setId(optionId);

        // 간단한 하드코딩 옵션 매핑
        switch (optionId.intValue()) {
            case 1:
                option.setOptionName("샷 추가");
                option.setExtraPrice(500);
                break;
            case 2:
                option.setOptionName("시럽 추가");
                option.setExtraPrice(500);
                break;
            case 3:
                option.setOptionName("휘핑 추가");
                option.setExtraPrice(700);
                break;
            default:
                option.setOptionName("테스트 옵션_" + optionId);
                option.setExtraPrice(0);
                break;
        }

        return option;
    }

}
