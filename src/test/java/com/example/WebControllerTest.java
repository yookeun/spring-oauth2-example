package com.example;


import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest
public class WebControllerTest {
	
	@Autowired
	protected WebApplicationContext context;
	
	private MockMvc mockMvc;
	
	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders
									.webAppContextSetup(context)
									.apply(springSecurity()).build();
	}
	
	
	@Test
	public void 엑세스토큰_받기() throws Exception {
		mockMvc.perform(post("/oauth/token")
								.with(user("bar").password("foo"))
								.param("grant_type", "password")
								.param("username", "user")
								.param("password", "test"))
		.andDo(print()).andExpect(status().isOk());
		//curl -u bar:foo http://localhost:8080/oauth/token -d  "grant_type=password&username=user&password=test"
	}
	
	
	@Test
	public void 엑세스토큰_접근() throws Exception {
		String accessToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsic3ByaW5nLWJvb3QtYXBwbGljYXRpb24iXSwidXNlcl9uYW1lIjoidXNlciIsInNjb3BlIjpbInJlYWQiLCJ3cml0ZSJdLCJleHAiOjE0ODkwMjQ0MjMsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJqdGkiOiIzYjBmNTk2ZC1kMTI1LTQ1MTItYTlkNy00ZjY5OWI5NzMwMzAiLCJjbGllbnRfaWQiOiJiYXIifQ.svzhiuACjxXggJOqfackvjXjjQklSlqnFqZdpF6OAQY";
		
		mockMvc.perform(get("/simple")
								.header("authorization", "bearer "+accessToken))
		.andDo(print()).andExpect(status().isOk());
		//curl -H "authorization: bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsic3ByaW5nLWJvb3QtYXBwbGljYXRpb24iXSwidXNlcl9uYW1lIjoidXNlciIsInNjb3BlIjpbInJlYWQiLCJ3cml0ZSJdLCJleHAiOjE0ODkwMzY0OTMsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJqdGkiOiI2NGI0NjcyOC1lMDQ1LTQzMmUtYWU0OS1hN2Q0MzEzNDViMmUiLCJjbGllbnRfaWQiOiJiYXIifQ.RUDgAPta1NyFXNpVBDHsCeqh-xtHoFozlzBAbrv6TVE" http://localhost:8080/simple
	}
}
