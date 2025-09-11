import lombok.RequiredArgsConstructor;

import java.sql.SQLOutput;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@RequiredArgsConstructor
public class ShopService {
    private final ProductRepo productRepo;
    private final OrderRepo orderRepo;

    public ShopService() {
        productRepo = new ProductRepo();
        orderRepo = new OrderMapRepo();
    }

    public Order addOrder(List<String> productIds) {
        List<Product> products = new ArrayList<>();
        for (String productId : productIds) {
            Product productToOrder = productRepo.getProductById(productId)
                    .orElseThrow(NoSuchElementException::new);
            products.add(productToOrder);
        }

        Order newOrder = new Order(
                UUID.randomUUID().toString(),
                products,OrderStatus.PROCESSING,
                LocalDateTime.now()
        );

        return orderRepo.addOrder(newOrder);
    }

    public List<Order> getOrdersByStatus(OrderStatus status) {
        List<Order> orders = orderRepo.getOrders();
        return orders.stream().filter((order -> {
            return order.status().equals(status);
        })).toList();
    }

    public Order updateOrder(String orderId, OrderStatus status) {
        Order order = orderRepo.getOrderById(orderId);
        orderRepo.addOrder(order.withStatus(status));
        return order;
    }
}
