package com.cognizant.swaggerEditor;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc

public class ItemTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    ItemRepository itemRepository;

    private ObjectMapper objectMapper = new ObjectMapper();


    @Test
    public void testGetAllItems () throws Exception {
        //Setup
        Item expectedItem = new Item();
        expectedItem.setName("Handbag");
        expectedItem.setPrice((long) 1200);
        expectedItem.setOnSale(false);

        itemRepository.save(expectedItem);

        String response = mvc.perform(MockMvcRequestBuilders.get("/api/item"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<Item> actual = objectMapper.readValue(response, new TypeReference<List<Item>>() {});


        Assert.assertEquals("GET call to /item should return one item.",1, actual.size());

        Item actualItem = actual.get(0);
        Assert.assertEquals("GET response should match the record in the database.", expectedItem, actualItem);
    }

     @Test
     public void testCreateNewItem () throws Exception {
         //Setup
         String expectedName = "Chocolate";
         String expectedContentType = "application/json;charset=UTF-8";
         String mockJSON = "{\"name\": \"".concat(expectedName).concat("\"}");

         //Exercise
         MvcResult mockResult = mvc.perform(post("/api/item").contentType(MediaType.APPLICATION_JSON).content(mockJSON))
                 .andExpect(status().isOk())
                 .andExpect(content().contentType(expectedContentType))
                 .andExpect(jsonPath("$.name").value(expectedName))
                 .andReturn();

         String mockResultContent = mockResult.getResponse().getContentAsString();
         Item actualitem = objectMapper.readValue(mockResultContent, new TypeReference<Item>() {
         });
         String actualName = actualitem.getName();

         //Assert
         Assert.assertEquals("Content type of POST response should equal \"application/json;charset=UTF-8\"",
                 expectedContentType, mockResult.getResponse().getContentType());

         Assert.assertEquals("Newly created Item name should equal".concat(expectedName), expectedName, actualName);

     }

     @Test
    public void testGetItemById() throws Exception {
        //Setup
         Item expectedItem = new Item();
         expectedItem.setName("Handbag");
         expectedItem.setPrice((long) 1200);
         expectedItem.setOnSale(false);

         String expectedContentType = "application/json;charset=UTF-8";

         //Exercise
         itemRepository.save(expectedItem);

         String path = "/api/item/".concat(Integer.toString(expectedItem.getId()));
         MvcResult mockResult = mvc.perform(get(path))
                 .andExpect(status().isOk())
                 .andReturn();

         String mockResponse = mockResult.getResponse().getContentAsString();

         Item actualItem = objectMapper.readValue(mockResponse,
                 new TypeReference<Item>(){});

         // Assert
        Assert. assertEquals("Content type of GET response should equal \"application/json;charset=UTF-8\"",
                 expectedContentType, mockResult.getResponse().getContentType());

        Assert.assertEquals("GET call to ".concat(path).concat(" should return the correct Item."),
                 expectedItem, actualItem);

     }

     @Test
    public void testUpdateItem() throws Exception {
        //Setup
         Item newItem = new Item("Apple");
         itemRepository.save(newItem);
         Integer newItemId = newItem.getId();

         String expectedName = "App";
         String expetedContentType = "application/json;charset=UTF-8";
         String expectedPath = "/api/item/".concat(newItemId.toString());
         String mockJSON = "{ \"name\": \"".concat(expectedName).concat("\"}");

         //Exercice
         MvcResult mockResult = mvc.perform(put(expectedPath)
                 .contentType(MediaType.APPLICATION_JSON).content(mockJSON))
                 .andExpect(status().isOk())
                 .andExpect(content().contentType(expetedContentType))
                 .andExpect(jsonPath("$.name").value(expectedName))
                 .andReturn();

         String mockResultContent = mockResult.getResponse().getContentAsString();
         Item actualItem = objectMapper.readValue(mockResultContent, new TypeReference<Item>(){});
         String actualName = actualItem.getName();

         //Assert
         Assert.assertEquals("Contect type of put should equal to application/json;charset=UTF-8", expetedContentType,mockResult.getResponse().getContentType());

         Assert.assertEquals("Updated user name should equal ".concat(expectedName),expectedName,actualName);

     }



     @Test
    public void testDeleteItem() throws Exception {
        //Setup
         Item newItem = new Item("Banana");
         itemRepository.save(newItem);

         Integer newItemId = newItem.getId();

         String expectedPAth = "/api/item/".concat(newItemId.toString());

         //Exercise

         mvc.perform(delete(expectedPAth))
                 .andExpect(status().isOk())
                 .andReturn();

        //Assert
         assertNull("Object should not be found.", itemRepository.findById(newItemId).orElse(null));
         assertEquals("Object should not be found.", itemRepository.findById(newItemId), Optional.empty());
     }
}

