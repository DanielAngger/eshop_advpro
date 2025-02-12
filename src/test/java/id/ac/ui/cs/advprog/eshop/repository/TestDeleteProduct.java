package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith (MockitoExtension.class)
public class TestDeleteProduct {
    @InjectMocks
    ProductRepository productRepository;

    Product product;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testDeleteProduct() {
        product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName ("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.delete(product.getProductId());
        assertFalse(productRepository.findAll().hasNext());
    }

    @Test
    void testDeletedProductNotFound() {
        product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        productRepository.delete(product.getProductId());

        boolean found = false;
        Iterator<Product> products = productRepository.findAll();

        while (products.hasNext()) {
            Product p = products.next();
            if (p.getProductId().equals("eb558e9f-1c39-460e-8860-71af6af63bd6")) {
                found = true;
                break;
            }
        }

        assertFalse(found, "Deleted product should not be found in the repository");
    }
}