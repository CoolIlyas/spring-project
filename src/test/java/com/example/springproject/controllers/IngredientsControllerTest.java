package com.example.springproject.controllers;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.springproject.domain.Ingredient;
import com.example.springproject.repositories.IngredientRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class IngredientsControllerTest {

    @Autowired
    private IngredientRepository repository;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void init() {
        repository.save(new Ingredient("test1", "test1", Ingredient.Type.CHEESE));
        repository.save(new Ingredient("test2", "test2", Ingredient.Type.SAUCE));
    }

    @AfterEach
    void clear() {
        repository.deleteAll();
    }


    @Test
    void getAllIngredientsTest() throws Exception {
        mockMvc.perform(get("/api/ingredients/all"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$", Matchers.hasSize(2)));
    }

    @Test
    void getIngredientByIdTest() throws Exception {
        mockMvc.perform(get("/api/ingredients/test1"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id", containsString("test1")));
    }

    @Test
    void saveIngredientTest() throws Exception {
        mockMvc.perform(post("/api/ingredients")
                .content("{\"id\":\"test3\",\"name\":\"test3\",\"type\":\"VEGGIES\"}")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isCreated());

        assertNotNull(repository.findById("test3").orElse(null));

        //посылаем навалидные ингридиенты
        mockMvc.perform(post("/api/ingredients")
                .content("{\"id\":\"test3\",\"name\":\"te\",\"type\":\"VEGGIES\"}")
                .contentType(APPLICATION_JSON))
                .andExpect(status().is4xxClientError());

        mockMvc.perform(post("/api/ingredients")
                .content("{\"id\":\"test3\",\"type\":\"VEGGIES\"}")
                .contentType(APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void deleteIngredientTest() throws Exception {
        assertEquals(2, repository.count());
        mockMvc.perform(delete("/api/ingredients/test1"))
                .andExpect(status().is2xxSuccessful());
        assertEquals(1, repository.count());
    }

}
