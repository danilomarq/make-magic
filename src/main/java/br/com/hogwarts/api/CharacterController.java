package br.com.hogwarts.api;

import br.com.hogwarts.exception.CharacterNotFoundException;
import br.com.hogwarts.model.Character;
import br.com.hogwarts.repository.CharacterRepository;
import br.com.hogwarts.service.CharacterService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;


@RestController
@RequestMapping(path = "/characters")
public class CharacterController {

    CharacterService characterService;
    CharacterRepository characterRepository;

    @Autowired
    public CharacterController(CharacterService characterService, CharacterRepository characterRepository){
        this.characterService = characterService;
        this.characterRepository = characterRepository;
    }

    @PostMapping(path = "/saveCharacter", consumes = "application/json", produces = "application/json")
    public Character saveCharacter(@RequestBody Character character,
                                   HttpServletResponse response,
                                   WebRequest request) throws Exception {
        character = characterService.saveCharacter(character);

        response.setStatus(HttpStatus.OK.value());
        response.setHeader("Location", String.format("%s/characters/%s", request.getContextPath(),character.getId()));

        return character;
    }

    @PostMapping(path = "/deleteCharacter")
    public void deleteCharacter(@RequestBody Character character){
        characterRepository.delete(character);
    }

    @GetMapping(produces = "application/json")
    public Iterable<Character> listCharacters(String house) throws JsonProcessingException {
        if(house != null &&!house.isEmpty()){
            return characterRepository.findByHouse(house);
        } else{
            return characterRepository.findAll();
        }
    }

    @GetMapping(path = "/{Id}", produces = "application/json")
    public Optional<Character> findCharacterById(@PathVariable("Id") Integer Id) throws CharacterNotFoundException {
        Optional<Character> character = characterRepository.findById(Id);

        if(!character.isPresent()){
            throw new CharacterNotFoundException();
        }

        return character;
    }
}
