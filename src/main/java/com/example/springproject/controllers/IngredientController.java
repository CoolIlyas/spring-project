package com.example.springproject.controllers;

import com.example.springproject.domain.Ingredient;
import com.example.springproject.repositories.IngredientRepository;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/ingredients")
public class IngredientController {

    public IngredientRepository repository;

    public IngredientController(IngredientRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/all")
    public Iterable<Ingredient> getAllIngredients() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Ingredient> getIngredientById(@PathVariable String id) {
        return repository.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void saveIngredient(@RequestBody @Valid Ingredient ingredient) {
        repository.save(ingredient);
    }

    @DeleteMapping("/{id}")
    public void deleteIngredient(@PathVariable String id) {
        repository.deleteById(id);
    }
}
