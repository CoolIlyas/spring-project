package com.example.springproject.controllers;

import com.example.springproject.domain.Ingredient;
import com.example.springproject.domain.Taco;
import com.example.springproject.repositories.IngredientRepository;
import com.example.springproject.repositories.TacoRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TacosControllerTest {

    @Autowired
    private TacoRepository tacoRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void init() {
        Ingredient ing1 = new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP);
        Ingredient ing2 = new Ingredient("COTO", "Corn Tortilla", Ingredient.Type.WRAP);
        Ingredient ing3 = new Ingredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN);
        Ingredient ing4 = new Ingredient("CARN", "Carnitas", Ingredient.Type.PROTEIN);
        Ingredient ing5 = new Ingredient("LETC", "Lettuce", Ingredient.Type.VEGGIES);
        Ingredient ing6 = new Ingredient("CHED", "Cheddar", Ingredient.Type.CHEESE);
        Ingredient ing7 = new Ingredient("JACK", "Monterrey Jack", Ingredient.Type.CHEESE);
        Ingredient ing8 = new Ingredient("SLSA", "Salsa", Ingredient.Type.SAUCE);
        Ingredient ing9 = new Ingredient("SRCR", "Sour Cream", Ingredient.Type.SAUCE);
        Ingredient ing10 = new Ingredient("TMTO", "Diced Tomatoes", Ingredient.Type.VEGGIES);
        ingredientRepository.saveAll(List.of(ing1,ing2,ing3,ing4,ing5,ing6,ing7,ing8,ing9,ing10));

        Taco taco1 = Taco.builder().name("Taco 1").createdAt(LocalDate.now()).ingredients(Set.of(ing1,ing8,ing5)).build();
        Taco taco2 = Taco.builder().name("Taco 2").createdAt(LocalDate.now().minusDays(5)).ingredients(Set.of(ing2,ing4,ing9)).build();
        tacoRepository.saveAll(List.of(taco1,taco2));
    }

    @AfterEach
    void clear() {
        tacoRepository.deleteAll();
        ingredientRepository.deleteAll();
    }


    @Test
    void getAllTacosTest() throws Exception {
        mockMvc.perform(get("/api/tacos/all"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$", Matchers.hasSize(2)));
    }

    @Test
    void getTacoByIdTest() throws Exception {
        mockMvc.perform(get("/api/tacos/1"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.name", containsString("Taco 1")));
    }

    @Test
    @Transactional
    void saveTacoTest() throws Exception {
        mockMvc.perform(post("/api/tacos")
                .content("{\"name\":\"Taco 3\",\"createdAt\":\"2022-08-12\",\"ingredients\":[{\"id\":\"FLTO\"},{\"id\":\"COTO\"}]}")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isCreated());

        Taco createdTaco = tacoRepository.findById(3L).orElse(null);
        assertNotNull(createdTaco);
        assertEquals("Taco 3", createdTaco.getName());

        //Пытаемся сохранить с несуществующими ингридиентами
        mockMvc.perform(post("/api/tacos")
                .content("{\"name\":\"Taco 4\",\"createdAt\":\"2022-08-12\",\"ingredients\":[{\"id\":\"FLTO1\"},{\"id\":\"COTO\"}]}")
                .contentType(APPLICATION_JSON))
                .andExpect(status().is5xxServerError());


        //посылаем навалидный тако
        mockMvc.perform(post("/api/tacos")
                .content("{\"name\":\"Taco 4\",\"createdAt\":\"2022-08-12\"}")
                .contentType(APPLICATION_JSON))
                .andExpect(status().is4xxClientError());


    }

    @Test
    void deleteTacoTest() throws Exception {
        assertEquals(2, tacoRepository.count());
        mockMvc.perform(delete("/api/tacos/1"))
                .andExpect(status().is2xxSuccessful());
        assertEquals(1, tacoRepository.count());
    }

}
