package br.com.hogwarts.api;

import br.com.hogwarts.exception.InvalidHouseKeyException;
import br.com.hogwarts.model.Character;
import br.com.hogwarts.repository.CharacterRepository;
import br.com.hogwarts.service.CharacterService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static br.com.hogwarts.TestUtils.createCharacter;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@WebMvcTest(CharacterController.class)
public class CharacterControllerTest {


    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private CharacterService characterService;

    @MockBean
    private CharacterRepository characterRepository;

    @Before
    public void setUp(){
        mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void create_character_with_valid_request () throws Exception {
        when(characterService.saveCharacter(any(Character.class))).thenReturn(createCharacter());

        this.mockMvc.perform(post("/characters/saveCharacter")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{ \"name\": \"Harry Potter\", \"role\": \"student\", \"school\": \"Hogwarts School of Witchcraft and Wizardry\", \"house\": \"5a05e2b252f721a3cf2ea33f\", \"patronus\": \"stag\"}")
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.name").value("Harry Potter"))
        .andExpect(jsonPath("$.role").value("student"))
        .andExpect(jsonPath("$.house").value("5a05e2b252f721a3cf2ea33f"))
        .andExpect(jsonPath("$.school").value("Hogwarts School of Witchcraft and Wizardry"))
        .andExpect(jsonPath("$.patronus").value("stag"));

    }

    @Test
    public void create_character_with_invalid_house_key () throws Exception {
        when(characterService.saveCharacter(any(Character.class))).thenThrow(new InvalidHouseKeyException());

        this.mockMvc.perform(post("/characters/saveCharacter")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"name\": \"Harry Potter\", \"role\": \"student\", \"school\": \"Hogwarts School of Witchcraft and Wizardry\", \"house\": \"0\", \"patronus\": \"stag\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.mensagem").value("Invalid house key supplied"));

    }

    @Test
    public void list_all_characters() throws Exception {
        List<Character> characters = new ArrayList<>();
        characters.add(createCharacter());
        when(characterRepository.findAll()).thenReturn(characters);


        this.mockMvc.perform(get("/characters")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[{\"house\":\"5a05e2b252f721a3cf2ea33f\",\"name\":\"Harry Potter\",\"id\":1,\"role\":\"student\",\"school\":\"Hogwarts School of Witchcraft and Wizardry\",\"patronus\":\"stag\"}]"));

    }

    @Test
    public void list_characters_by_house() throws Exception {
        List<Character> characters = new ArrayList<>();
        characters.add(createCharacter());
        when(characterRepository.findByHouse(any(String.class))).thenReturn(characters);


        this.mockMvc.perform(get("/characters?house=5a05e2b252f721a3cf2ea33f")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[{\"house\":\"5a05e2b252f721a3cf2ea33f\",\"name\":\"Harry Potter\",\"id\":1,\"role\":\"student\",\"school\":\"Hogwarts School of Witchcraft and Wizardry\",\"patronus\":\"stag\"}]"));

    }

    @Test
    public void get_character_with_valid_request() throws Exception {
        when(characterRepository.findById(any(Integer.class))).thenReturn( Optional.of(createCharacter()));


        this.mockMvc.perform(get("/characters/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Harry Potter"))
                .andExpect(jsonPath("$.role").value("student"))
                .andExpect(jsonPath("$.house").value("5a05e2b252f721a3cf2ea33f"))
                .andExpect(jsonPath("$.school").value("Hogwarts School of Witchcraft and Wizardry"))
                .andExpect(jsonPath("$.patronus").value("stag"));
    }

    @Test
    public void get_character_by_id_return_404NotFound() throws Exception {
        when(characterRepository.findById(any(Integer.class))).thenReturn(Optional.empty());


        this.mockMvc.perform(get("/characters/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.mensagem").value("Requested character not found"));
    }


}
