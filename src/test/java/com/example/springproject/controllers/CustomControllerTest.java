package com.example.springproject.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void test() throws Exception {
        //Посылаем все параметры
        MvcResult result1 = mockMvc.perform(post("/api/test=1")
                .param("var2", "2")
                .header("head", "4")
                .content("3"))
                .andExpect(status().isOk())
                .andReturn();
        assertEquals("path1 = 1, path2 = 2, body = 3, header = 4", result1.getResponse().getContentAsString());

        //Посылаем без необязательрого параметра
        MvcResult result2 = mockMvc.perform(post("/api/test=1")
                .header("head", "4")
                .content("3"))
                .andExpect(status().isOk())
                .andReturn();
        assertEquals("path1 = 1, path2 = null, body = 3, header = 4", result2.getResponse().getContentAsString());

        //Посылаем без обязательрого параметра
        mockMvc.perform(post("/api/test=1")
                .param("var2", "2")
                .content("3"))
                .andExpect(status().is4xxClientError());
    }
}
