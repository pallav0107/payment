package com.ecom.ordermanagementservice.repository;



import com.ecom.ordermanagementservice.constant.OrderStatus;
import com.ecom.ordermanagementservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
  @Transactional
  @Modifying
  @Query("update Order o set o.orderStatus = ?1 where o.orderId = ?2")
  int updateOrderStatus(OrderStatus orderStatus, long orderId);

}
