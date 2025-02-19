package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductServiceImpl productService;

    @Mock
    private Model model;

    private Product sampleProduct;

    @BeforeEach
    void setUp() {
        sampleProduct = new Product();
        sampleProduct.setProductId("1");
        sampleProduct.setProductName("Sample Product");
    }

    @Test
    void testCreateProductPage() {
        String viewName = productController.createProductPage(model);

        verify(model, times(1)).addAttribute(eq("product"), any(Product.class));
        assertEquals("CreateProduct", viewName);
    }

    @Test
    void testCreateProductPost() {
        String viewName = productController.createProductPost(sampleProduct, model);

        verify(productService, times(1)).create(sampleProduct);
        assertEquals("redirect:list", viewName);
    }

    @Test
    void testProductListPage() {
        List<Product> products = new ArrayList<>();
        products.add(sampleProduct);
        when(productService.findAll()).thenReturn(products);

        String viewName = productController.productListPage(model);

        verify(model, times(1)).addAttribute("products", products);
        assertEquals("ProductList", viewName);
    }

    @Test
    void testDeleteProduct() {
        String viewName = productController.deleteProduct("1");

        verify(productService, times(1)).delete("1");
        assertEquals("redirect:/product/list", viewName);
    }

    @Test
    void testEditProductPage_ProductExists() {
        when(productService.findById("1")).thenReturn(sampleProduct);

        String viewName = productController.editProductPage("1", model);

        verify(model, times(1)).addAttribute("product", sampleProduct);
        assertEquals("EditProduct", viewName);
    }

    @Test
    void testEditProductPage_ProductNotFound() {
        when(productService.findById("1")).thenReturn(null);

        String viewName = productController.editProductPage("1", model);

        assertEquals("redirect:/product/list", viewName);
    }

    @Test
    void testEditProductPost() {
        String viewName = productController.editProductPost(sampleProduct);

        verify(productService, times(1)).update(sampleProduct);
        assertEquals("redirect:/product/list", viewName);
    }

    @Test
    void testHomePage() {
        HomeController controller = new HomeController();
        Model model = mock(Model.class); // Mock Model object

        String viewName = controller.homePage(model);

        assertEquals("home", viewName); // Pastikan return valuenya benar
    }
}
