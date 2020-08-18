package com.labforward.api.hello;

import com.labforward.api.common.MVCIntegrationTest;
import com.labforward.api.hello.controller.HelloController;
import com.labforward.api.hello.domain.Greeting;
import com.labforward.api.hello.domain.GreetingDTO;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MvcResult;

import static com.labforward.api.hello.ApplicationConstants.*;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest
public class HelloControllerTest extends MVCIntegrationTest {

	private static final String HELLO_LUKE = "Hello Luke";

	@Test
	public void getDefaultGreetingIsOKAndReturnsValidJSON() throws Exception {
		mockMvc.perform(get("/hello"))
		       .andExpect(status().isOk())
		       .andExpect(jsonPath("$.id", is(ApplicationConstants.DEFAULT_ID)))
		       .andExpect(jsonPath("$.message", is(ApplicationConstants.DEFAULT_MESSAGE)));
	}

	@Test
	public void getGreetingIsOKAndReturnsValidJSON() throws Exception {
		Greeting hello = new Greeting("Id1",HELLO_LUKE);

		mockMvc.perform(post("/hello/create").contentType(MediaType.APPLICATION_JSON)
				.content(getGreetingBody(hello)))
				.andExpect(status().isCreated());

		mockMvc.perform(get("/hello/Id1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is("Id1")))
				.andExpect(jsonPath("$.message", is(HELLO_LUKE)));
	}

	@Test
	public void returnsNotFoundWhenGetGreetingWithUnknownId() throws Exception {
		mockMvc.perform(get("/hello/id"))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message", containsString(GREETING_NOT_FOUND+" id")));
	}

	@Test
	public void returnsBadRequestWhenMessageMissing() throws Exception {
		mockMvc.perform(post("/hello/create").content("{}")
		                              .contentType(MediaType.APPLICATION_JSON))
		       .andExpect(status().isUnprocessableEntity())
		       .andExpect(jsonPath("$.validationErrors", hasSize(1)))
		       .andExpect(jsonPath("$.validationErrors[*].field", contains("message")));
	}

	@Test
	public void returnsBadRequestWhenUnexpectedAttributeProvided() throws Exception {
		String body = "{ \"tacos\":\"value\" }}";
		mockMvc.perform(post("/hello/create").content(body).contentType(MediaType.APPLICATION_JSON))
		       .andExpect(status().isUnprocessableEntity())
				.andExpect(jsonPath("$.validationErrors", hasSize(1)))
				.andExpect(jsonPath("$.validationErrors[*].field", contains("message")));
	}

	@Test
	public void returnsBadRequestWhenMessageEmptyString() throws Exception {
		Greeting emptyMessage = new Greeting("");

		mockMvc.perform(post("/hello/create").content(getGreetingBody(emptyMessage))
		                              .contentType(MediaType.APPLICATION_JSON))
		       .andExpect(status().isUnprocessableEntity())
		       .andExpect(jsonPath("$.validationErrors", hasSize(1)))
		       .andExpect(jsonPath("$.validationErrors[*].field", contains("message")));
	}

	@Test
	public void createOKWhenRequiredGreetingProvided() throws Exception {
		Greeting hello = new Greeting(HELLO_LUKE);

		mockMvc.perform(post("/hello/create").contentType(MediaType.APPLICATION_JSON)
		                              .content(getGreetingBody(hello)))
		       .andExpect(status().isCreated())
		       .andExpect(jsonPath("$.message", is(hello.getMessage())));
	}

	@Test
	public void returnsBadRequestWhenIdAlreadyExist() throws Exception {
		Greeting hello = new Greeting("Id",HELLO_LUKE);

		mockMvc.perform(post("/hello/create").contentType(MediaType.APPLICATION_JSON)
				.content(getGreetingBody(hello)))
				.andExpect(status().isCreated());

		Greeting message = new Greeting("Id","New Data");

		mockMvc.perform(post("/hello/create").content(getGreetingBody(message))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message", containsString(ID_ALREADY_EXIST)));
	}

	@Test
	public void returnsValidationFailedWhenUpdateRequestIdMisMatches() throws Exception {
		Greeting hello = new Greeting("10",HELLO_LUKE);

		mockMvc.perform(put("/hello/update/2").contentType(MediaType.APPLICATION_JSON)
				.content(getGreetingBody(hello)))
				.andExpect(status().isUnprocessableEntity())
				.andExpect(jsonPath("$.validationErrors", hasSize(1)))
				.andExpect(jsonPath("$.validationErrors[*].field", contains("id")));
	}

	@Test
	public void updateGreetingIsOkWhenValidRequest() throws Exception {
		Greeting hello = new Greeting("validId",HELLO_LUKE);

		mockMvc.perform(post("/hello/create").contentType(MediaType.APPLICATION_JSON)
				.content(getGreetingBody(hello)))
				.andExpect(status().isCreated());

		hello = new Greeting("validId","New Message");

		mockMvc.perform(put("/hello/update/validId").contentType(MediaType.APPLICATION_JSON)
				.content(getGreetingBody(hello)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is("validId")))
				.andExpect(jsonPath("$.message", is("New Message")));

	}

	@Test
	public void returnsNotFoundWhenUpdateRequestIdNotExist() throws Exception {
		Greeting hello = new Greeting("2",HELLO_LUKE);

		mockMvc.perform(put("/hello/update/2").contentType(MediaType.APPLICATION_JSON)
				.content( getGreetingBody(hello)))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message", containsString(GREETING_NOT_FOUND+" 2")));
	}

	private String getGreetingBody(Greeting greeting) throws JSONException {
		JSONObject json = new JSONObject().put("message", greeting.getMessage());

		if (greeting.getId() != null) {
			json.put("id", greeting.getId());
		}

		return json.toString();
	}

}
