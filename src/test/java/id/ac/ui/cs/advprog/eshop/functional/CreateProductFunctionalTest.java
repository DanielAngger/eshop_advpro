package id.ac.ui.cs.advprog.eshop.functional;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CreateProductFunctionalTest {

    @Autowired
    private ProductRepository productRepository;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setProductId("12345");
        product.setProductName("Laptop Gaming");
        product.setProductQuantity(10);
    }

    @Test
    void testCreateProduct() {
        productRepository.create(product);

        Iterator<Product> products = productRepository.findAll();
        boolean found = false;

        while (products.hasNext()) {
            Product p = products.next();
            if (p.getProductId().equals("12345") &&
                    p.getProductName().equals("Laptop Gaming") &&
                    p.getProductQuantity() == 10) {
                found = true;
                break;
            }
        }

        assertTrue(found, "Newly created product should be in the product list");
    }
}