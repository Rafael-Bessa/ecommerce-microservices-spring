package bessa.morangon.rafael.order.service.repository;

import bessa.morangon.rafael.order.service.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
