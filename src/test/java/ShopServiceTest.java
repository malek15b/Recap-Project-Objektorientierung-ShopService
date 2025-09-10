import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class ShopServiceTest {

    @Test
    void addOrderTest() {
        //GIVEN
        ShopService shopService = new ShopService();
        List<String> productsIds = List.of("1");

        //WHEN
        Order actual = shopService.addOrder(productsIds);

        //THEN
        Order expected = new Order("-1", List.of(new Product("1", "Apfel")), OrderStatus.PROCESSING, LocalDateTime.now());
        assertEquals(expected.products(), actual.products());
        assertNotNull(expected.id());
    }

    @Test
    void addOrderTestWhenInvalidProductIdExpectExcpetion() {
        //GIVEN
        ShopService shopService = new ShopService();
        List<String> productsIds = List.of("1", "2");

        //THEN
        assertThrows(NoSuchElementException.class, () -> shopService.addOrder(productsIds));
    }

    @Test
    void getOrdersByStatus_PROCESSING() {
        //GIVEN
        ShopService shopService = new ShopService();
        Product product = new Product("1", "Apfel");
        Order order1 = shopService.addOrder(List.of(product.id()));

        Product product2 = new Product("5", "Banane");
        Order order2 = shopService.addOrder(List.of(product.id(), product2.id()));

        Order order3 = shopService.addOrder(List.of(product2.id()));
        shopService.updateOrder(order3.id(), OrderStatus.COMPLETED);

        int expected = 2;

        //WHEN
        List<Order> orders = shopService.getOrdersByStatus(OrderStatus.PROCESSING);

        //THEN
        assertEquals(expected, orders.size());
    }
}
