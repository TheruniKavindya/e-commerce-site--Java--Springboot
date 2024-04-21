package com.example.productservice;

import com.example.productservice.dto.ProductRequest;
import com.example.productservice.dto.ProductResponse;
import com.example.productservice.model.Product;
import com.example.productservice.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
//@Testcontainers
@TestPropertySource("classpath:application-test.properties")
@AutoConfigureMockMvc
@Slf4j
class ProductServiceApplicationTests {

//	@Container
//	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.2");
// docker image name (with version of mongo to use) should be given as argument

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper; 	// to convert pojo objects to json and vice versa

	@Autowired
	private ProductRepository productRepository;

	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry){
		dynamicPropertyRegistry.add("spring.data.mongodb.uri", () -> System.getProperty("spring.data.mongodb.uri"));
	}

//	@DynamicPropertySource
//	static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry){
//		dynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
//		//	dynamically set the MongoDB connection URI for a Spring Boot application using Testcontainers
//	}

	@Test
	void shouldCreateProduct() throws Exception { // this throws an exception because "writeValueAsString" and mockMvc.perform can throw an exception

		ProductRequest productRequest = getProductRequest();
		String productRequestString = objectMapper.writeValueAsString(productRequest);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
				.contentType(MediaType.APPLICATION_JSON)
				.content(productRequestString))
				.andExpect(status().isCreated());
		Assertions.assertEquals(1, productRepository.findAll().size()); // check whether there is only the test product in the database
	}

//	@Test
//	void shouldGetAllProducts() throws Exception {
//		mockMvc.perform(MockMvcRequestBuilders.get("/api/product")
//				.accept(MediaType.APPLICATION_JSON))
//				.andExpect(status().isOk());
//
//
//
//	}

	private ProductRequest getProductRequest() {
		return ProductRequest.builder()
				.name("iPhone 14")
				.description("iPhone 14")
				.price(BigDecimal.valueOf(160000))
				.build();
	}

}
