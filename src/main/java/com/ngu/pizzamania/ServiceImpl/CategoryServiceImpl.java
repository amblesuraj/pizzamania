package com.ngu.pizzamania.ServiceImpl;

import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import com.ngu.pizzamania.Model.Category;
import com.ngu.pizzamania.Repository.CategoryRepository;
import com.ngu.pizzamania.Service.CategoryService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> FindAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findById(int id) {
        return categoryRepository.getReferenceById(id);
    }

    @Override
    public void deleteCategoryById(int id) {
        categoryRepository.deleteById(id);
    }
}
