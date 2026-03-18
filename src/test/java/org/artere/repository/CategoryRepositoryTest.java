package org.artere.repository;

import org.artere.TestcontainersConfiguration;
import org.artere.model.Category;
import org.artere.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(TestcontainersConfiguration.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Test
    void shouldSaveAndFindRootCategories() {
        Category root = new Category("Electronics", "Electronic devices");
        categoryRepository.save(root);

        List<Category> roots = categoryRepository.findByParentIsNull();
        assertThat(roots).hasSize(1);
        assertThat(roots.get(0).getName()).isEqualTo("Electronics");
    }

    @Test
    void shouldSaveSubCategories() {
        Category parent = new Category("Electronics", "Electronic devices");
        categoryRepository.save(parent);

        Category child = new Category("Phones", "Mobile phones");
        parent.addSubCategory(child);
        categoryRepository.save(parent);

        List<Category> subCategories = categoryRepository.findByParentId(parent.getId());
        assertThat(subCategories).hasSize(1);
        assertThat(subCategories.get(0).getName()).isEqualTo("Phones");
    }

    @Test
    void shouldSaveCategoryWithProducts() {
        Category category = new Category("Electronics", "Electronic devices");
        Product product = new Product("Laptop", 999.99, 10);
        category.addProduct(product);
        categoryRepository.save(category);

        List<Product> products = productRepository.findByCategoryId(category.getId());
        assertThat(products).hasSize(1);
        assertThat(products.get(0).getName()).isEqualTo("Laptop");
        assertThat(products.get(0).getPrice()).isEqualTo(999.99);
    }

    @Test
    void shouldSaveCategoryWithBothSubCategoriesAndProducts() {
        Category parent = new Category("Electronics", "Electronic devices");

        Category subCategory = new Category("Phones", "Mobile phones");
        parent.addSubCategory(subCategory);

        Product product = new Product("Universal Charger", 29.99, 100);
        parent.addProduct(product);

        categoryRepository.save(parent);

        Category found = categoryRepository.findById(parent.getId()).orElseThrow();
        assertThat(found.getSubCategories()).hasSize(1);
        assertThat(found.getProducts()).hasSize(1);
    }
}

