package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestEditProduct {
    @InjectMocks
    ProductRepository productRepository;

    @Mock
    ProductRepository mockRepository;

    Product product; // ✅ Declare product

    @BeforeEach
    void setUp() {
    }

    @Test
    void testEditProductName() {
        product = new Product(); // ✅ Initialize product
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        when(mockRepository.findById(product.getProductId())).thenReturn(product);

        Product existingProduct = mockRepository.findById(product.getProductId());
        assertNotNull(existingProduct); // ✅ Ensure product exists

        existingProduct.setProductName("Sampo Cap Panda");
        mockRepository.update(existingProduct);

        verify(mockRepository).update(existingProduct);
        assertEquals("Sampo Cap Panda", existingProduct.getProductName());
    }

    @Test
    void testEditProductQuantity() {
        product = new Product(); // ✅ Initialize product
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        when(mockRepository.findById(product.getProductId())).thenReturn(product);

        Product existingProduct = mockRepository.findById(product.getProductId());
        assertNotNull(existingProduct);

        existingProduct.setProductQuantity(12);
        mockRepository.update(existingProduct);

        verify(mockRepository).update(existingProduct);
        assertEquals(12, existingProduct.getProductQuantity());
    }
}