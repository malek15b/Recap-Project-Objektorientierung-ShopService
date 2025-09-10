import java.util.List;

public class Main {
    public static void main(String[] args) {
        OrderRepo orderRepo = new OrderMapRepo();
        ProductRepo productRepo = new ProductRepo();
        ShopService shopService = new ShopService(productRepo, orderRepo);

        String productId1 = IdService.generateId();
        String productId2 = IdService.generateId();

        productRepo.addProduct(new Product(productId1, "TV"));
        productRepo.addProduct(new Product(productId2, "Phone"));

        Order order1 = shopService.addOrder(List.of(productId1));
        Order order2 = shopService.addOrder(List.of(productId2));
        Order order3 = shopService.addOrder(List.of(productId1, productId2));

        System.out.println(order1);
        System.out.println(order2);
        System.out.println(order3);
    }
}
