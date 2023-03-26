package com.ngu.pizzamania.Controller;

import com.ngu.pizzamania.Model.Category;
import com.ngu.pizzamania.Service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@AllArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;


    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Category createCategory(@RequestBody Category category) {
        return categoryService.createCategory(category);
    }

    @GetMapping(value = "/categories")
    public List<Category> getallCategories() {
        return categoryService.FindAllCategories();
    }

    @GetMapping(value = "/{id}")
    public Category findCategoryById(@PathVariable int id) {
        return categoryService.findById(id);
    }

    @DeleteMapping(value = "/delete/{id}")
    public String deleteCategoryById(@PathVariable int id) {
        categoryService.deleteCategoryById(id);
        return "Category Deleted with id " + id;
    }

}
