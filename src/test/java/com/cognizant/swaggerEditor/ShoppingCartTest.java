package com.cognizant.swaggerEditor;


import com.fasterxml.jackson.core.JsonProcessingException;
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

import javax.print.attribute.standard.Media;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc

public class ShoppingCartTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ShoppingCartRepository shoppingCartRepository;

    @Autowired
    LineItemRepository lineItemRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testCartById() throws Exception {
        //Setup
        ShoppingCart expectedShoppingCart = new ShoppingCart();
        expectedShoppingCart.setName("Shopping Cart");
        shoppingCartRepository.save(expectedShoppingCart);

        String path = "/api/cart/".concat(Integer.toString(expectedShoppingCart.getId()));
        MvcResult mockResult = mvc.perform(get(path))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();
        String mockResponse = mockResult.getResponse().getContentAsString();

        ShoppingCart actualShoppingCart = objectMapper.readValue(mockResponse,
                new TypeReference<ShoppingCart>(){});

        //Assert

        Assert.assertEquals("GET call to ".concat(path).concat(" should return the correct Shopping Cart."),
                expectedShoppingCart.getId(), actualShoppingCart.getId());

    }


    @Test
    public void testCreateCart() throws Exception {
        //Setup
        String expectedShoppingCartName = "Shopping Cart";
        ShoppingCart mockCart = new ShoppingCart(expectedShoppingCartName);
        String mockJSON = objectMapper.writeValueAsString(mockCart);

        //Exercise
        String mockResult = mvc.perform(post("/api/cart").contentType(MediaType.APPLICATION_JSON).content(mockJSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(expectedShoppingCartName))
                .andReturn()
                .getResponse()
                .getContentAsString();

        ShoppingCart actualShoppingCart = objectMapper.readValue(mockResult, new TypeReference<ShoppingCart>() {});
        String actualShoppingCartName = actualShoppingCart.getName();

        Assert.assertEquals("Newly created Shopping cart name should equal".concat(expectedShoppingCartName), expectedShoppingCartName, actualShoppingCartName);
    }

    @Test
    public void testGetLineItemsByCartId() throws Exception {
        // Setup
        ShoppingCart mockCart = new ShoppingCart("Gabe and Akash's Cart");
        shoppingCartRepository.save(mockCart);
        String mockJSON = objectMapper.writeValueAsString(shoppingCartRepository.findById(1));


        LineItem mockLineItem = new LineItem(mockCart.getId(), 1);
        lineItemRepository.save(mockLineItem);

        String mockLineItemJSON = objectMapper.writeValueAsString(mockLineItem);

        // Exercise
        String mockGetResult = mvc.perform(get("/api/cart/".concat(mockCart.getId().toString()).concat("/lineitem")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn()
                .getResponse()
                .getContentAsString();

        // Assert
        List<LineItem> mockLineItems = objectMapper.readValue(mockGetResult, new TypeReference<ArrayList<LineItem>>(){});

        assertEquals("Size of list of LineItems should equal 1.", 1, mockLineItems.size());
        assertEquals("Quantity of LineItem should equal 1.", (Integer)1, ((LineItem)mockLineItems.get(0)).getQuantity());
    }
}
