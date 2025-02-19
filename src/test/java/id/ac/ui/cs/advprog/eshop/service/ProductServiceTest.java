package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @InjectMocks
    ProductServiceImpl productService;

    @Mock
    ProductRepository productRepository;

    private Product sampleProduct;

    @BeforeEach
    void setUp() {
        sampleProduct = new Product();
        sampleProduct.setProductId("1");
        sampleProduct.setProductName("Sample Product");
    }

    @Test
    void testCreate() {
        when(productRepository.create(sampleProduct)).thenReturn(sampleProduct);

        Product createdProduct = productService.create(sampleProduct);

        assertNotNull(createdProduct);
        assertEquals("1", createdProduct.getProductId());
        assertEquals("Sample Product", createdProduct.getProductName());
        verify(productRepository, times(1)).create(sampleProduct);
    }

    @Test
    void testFindAll() {
        List<Product> productList = new ArrayList<>();
        productList.add(sampleProduct);

        Iterator<Product> mockIterator = productList.iterator();
        when(productRepository.findAll()).thenReturn(mockIterator);

        List<Product> result = productService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("1", result.get(0).getProductId());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testDelete() {
        doNothing().when(productRepository).delete("1");

        productService.delete("1");

        verify(productRepository, times(1)).delete("1");
    }

    @Test
    void testFindById() {
        when(productRepository.findById("1")).thenReturn(sampleProduct);

        Product foundProduct = productService.findById("1");

        assertNotNull(foundProduct);
        assertEquals("1", foundProduct.getProductId());
        assertEquals("Sample Product", foundProduct.getProductName());
        verify(productRepository, times(1)).findById("1");
    }

    @Test
    void testUpdate() {
        when(productRepository.update(sampleProduct)).thenReturn(sampleProduct);

        Product updatedProduct = productService.update(sampleProduct);

        assertNotNull(updatedProduct);
        assertEquals("1", updatedProduct.getProductId());
        assertEquals("Sample Product", updatedProduct.getProductName());
        verify(productRepository, times(1)).update(sampleProduct);
    }
}