package com.example.springproject.controllers;

import com.example.springproject.domain.Ingredient;
import com.example.springproject.domain.Taco;
import com.example.springproject.repositories.IngredientRepository;
import com.example.springproject.repositories.TacoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/api/tacos")
public class TacoController {

    private final TacoRepository tacoRepository;
    private final IngredientRepository ingredientRepository;

    public TacoController(TacoRepository tacoRepository, IngredientRepository ingredientRepository) {
        this.tacoRepository = tacoRepository;
        this.ingredientRepository = ingredientRepository;
    }

    @GetMapping("/all")
    public Iterable<Taco> getAllTacos() {
        return tacoRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Taco> getTacoById(@PathVariable Long id) {
        return tacoRepository.findById(id);
    }

    @PostMapping
    public ResponseEntity saveTaco(@RequestBody @Valid Taco taco) {
        try {
            if (taco.getIngredients().stream().map(Ingredient::getId).allMatch(ingredientRepository::existsById)) {
                tacoRepository.save(taco);
                return new ResponseEntity(HttpStatus.CREATED);
            } else {
                return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception ex) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteTaco(@PathVariable Long id) {
        tacoRepository.deleteById(id);
    }

}
