import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        Order expected = new Order("-1", List.of(new Product("1", "Apfel")), OrderStatus.PROCESSING);
        assertEquals(expected.products(), actual.products());
        assertNotNull(expected.id());
    }

    @Test
    void addOrderTest_whenInvalidProductId_expectNull() {
        //GIVEN
        ShopService shopService = new ShopService();
        List<String> productsIds = List.of("1", "2");

        //WHEN
        Order actual = shopService.addOrder(productsIds);

        //THEN
        assertNull(actual);
    }

    @Test
    void getOrdersByStatus_PROCESSING() {
        //GIVEN
        ShopService shopService = new ShopService();
        Product product = new Product("1", "Apfel");
        Order order1 = shopService.addOrder(List.of(product.id()));

        Product product2 = new Product("5", "Banane");
        Order order2 = shopService.addOrder(List.of(product.id(), product2.id()));

        List<Order> expected = List.of(order1,  order2);

        //WHEN
        List<Order> orders = shopService.getOrdersByStatus(OrderStatus.PROCESSING);

        //THEN
        assertEquals(expected, orders);
    }
}
