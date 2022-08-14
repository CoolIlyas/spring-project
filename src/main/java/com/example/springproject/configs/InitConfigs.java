package com.example.springproject.configs;

import com.example.springproject.domain.Ingredient;
import com.example.springproject.domain.Taco;
import com.example.springproject.domain.TacoOrder;
import com.example.springproject.repositories.IngredientRepository;
import com.example.springproject.repositories.OrderRepository;
import com.example.springproject.repositories.TacoRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Configuration
public class InitConfigs {

    @Bean
    @Profile("init")
    public ApplicationRunner initIngredients(IngredientRepository ingRepo,
                                             OrderRepository ordRepo,
                                             TacoRepository tacoRepo) {
        return args -> {
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
            ingRepo.saveAll(List.of(ing1,ing2,ing3,ing4,ing5,ing6,ing7,ing8,ing9,ing10));

            Taco taco1 = Taco.builder().id(1L).name("Taco 1").createdAt(LocalDate.now()).ingredients(Set.of(ing1,ing8,ing5)).build();
            Taco taco2 = Taco.builder().id(2L).name("Taco 2").createdAt(LocalDate.now().minusDays(5)).ingredients(Set.of(ing2,ing4,ing9)).build();
            tacoRepo.saveAll(List.of(taco1,taco2));


            TacoOrder order1 = TacoOrder.builder().id(1L).deliveryStreet("st1").deliveryName("delivery1").placedAt(LocalDate.now()).tacos(List.of(taco1)).build();
            TacoOrder order2 = TacoOrder.builder().id(2L).deliveryStreet("st2").deliveryName("delivery2").placedAt(LocalDate.now().minusDays(5)).tacos(List.of(taco2)).build();
            ordRepo.saveAll(List.of(order1,order2));
        };
    }
}
