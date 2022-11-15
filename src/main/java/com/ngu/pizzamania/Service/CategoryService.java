package com.ngu.pizzamania.Service;

import com.ngu.pizzamania.Model.Category;

import java.util.List;

public interface CategoryService {

    Category createCategory(Category category);

    Category updateCategory(Category category);

    List<Category> FindAllCategories();

    Category findById(int id);

    void deleteCategoryById(int id);

}
