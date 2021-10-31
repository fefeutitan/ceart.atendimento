package br.com.ivia.ceart.atendimento;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.google.common.collect.Lists;

import br.com.ivia.ceart.atendimento.model.TipoAtendimento;
import br.com.ivia.ceart.atendimento.restcontroller.TipoAtendimentoRestController;
import br.com.ivia.ceart.atendimento.service.TipoAtendimentoService;

public class TipoAtendimentoRestControllerTests extends ApplicationTests {

	private MockMvc mockMvc;
	
	@Autowired
	private TipoAtendimentoRestController tipoAtendimentoRestController;
	
    @Autowired
    private TipoAtendimentoService service;
    
	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(tipoAtendimentoRestController).build();
	}
	
//	@Test
//	public void index() throws Exception {
//		this.mockMvc.perform(MockMvcRequestBuilders.get("/tipoatendimento"))
//			.andExpect(MockMvcResultMatchers.status().isOk());
//	}
//	
//	@Test
//	public void findAll() throws Exception {
//		this.mockMvc.perform(MockMvcRequestBuilders.get("/tipoatendimento/findAll"))
//			.andExpect(MockMvcResultMatchers.status().isOk());
//	}
//	
//	@Test
//	public void testFindAll() throws Exception {
//		List<TipoAtendimento> tipos = Lists.newArrayList(service.findAll());
//		assertEquals(tipos.size(), 6);
//	}
}
