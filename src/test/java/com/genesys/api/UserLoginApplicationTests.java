package com.genesys.api;



import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.FixMethodOrder;

import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.genesys.api.controller.UserLoginController;
import com.genesys.api.model.User;
import com.genesys.api.model.UserModel;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserLoginApplicationTests {

	 
	@Autowired
	WebApplicationContext context;
	private MockMvc mockMVC;
	 
	@Test
	public void contextLoads() {
	}
	
	@Before
	public void setUp() {
		mockMVC = MockMvcBuilders.webAppContextSetup(context).build();
	}
	
	@Test
	public void test_2_RetrieveAllUser() throws Exception {
		mockMVC.perform(get("/user")).andDo(print())
		.andExpect(status().isOk());
	
	
	}
	
	@Test
	public void test_1_GetUser() throws Exception{
		
		mockMVC.perform(get("/user/1")).andDo(print())
		.andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
		
		
		
	}
	
	@Test
	public void test_3_addUser() throws Exception{
		
		UserModel user = new UserModel();
		user.setEmail("b@b.com");
		user.setName("B");
		user.setPassword("QWSx@098");
		
		String requestJSON = toJson(user);
		MvcResult result = mockMVC.perform(post("/user").content(requestJSON).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isCreated()).andReturn();
		User addedUser = toUser(result.getResponse().getContentAsString());		
		mockMVC.perform(get("/user/"+addedUser.getId())).andExpect(status().isOk());
				
	
	}
	
	@Test
	public void test_4_UpdateUser() throws Exception{
		UserModel user = new UserModel();
		user.setEmail("c@c.com");
		user.setName("C");
		user.setPassword("QWSx@098");
		
		String requestJSON = toJson(user);
		MvcResult result = mockMVC.perform(post("/user").content(requestJSON).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isCreated()).andReturn();
		
		User addedUser = toUser(result.getResponse().getContentAsString());	
		
		user.setName("CC");
		
		String updatedRequestJSON = toJson(user);
		
		mockMVC.perform(put("/user/"+addedUser.getId()).content(updatedRequestJSON).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andDo(print())
			.andExpect(status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("CC"));;
	}
	
	@Test
	public void testz_5_DeleteCust() throws Exception{
		
		

		UserModel user = new UserModel();
		user.setEmail("d@d.com");
		user.setName("DD");
		user.setPassword("QWSx@098");
		
		String requestJSON = toJson(user);
		MvcResult result = mockMVC.perform(post("/user").content(requestJSON).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isCreated()).andReturn();
		
		User addedUser = toUser(result.getResponse().getContentAsString());	
		
		mockMVC.perform(delete("/user/"+addedUser.getId())).andExpect(status().isResetContent());
				
	}
	

	 private String toJson(Object r) throws Exception {
	        ObjectMapper map = new ObjectMapper();
	        return map.writeValueAsString(r);
	    }
	 
	 private User toUser(String response) throws Exception {
	        ObjectMapper map = new ObjectMapper();
	        return map.readValue(response, User.class);
	    }
}
