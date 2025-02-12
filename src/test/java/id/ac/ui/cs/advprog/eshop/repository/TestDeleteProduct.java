package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

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
}