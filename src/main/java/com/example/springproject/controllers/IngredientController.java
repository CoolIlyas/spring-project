package com.example.springproject.controllers;

import com.example.springproject.domain.Ingredient;
import com.example.springproject.repositories.IngredientRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/ingredients")
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
}
