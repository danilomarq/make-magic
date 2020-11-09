package br.com.hogwarts.service;

import br.com.hogwarts.exception.InvalidHouseKeyException;
import br.com.hogwarts.model.Character;
import br.com.hogwarts.repository.CharacterRepository;
import br.com.hogwarts.utils.PotterApiRoutes;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Service
public class CharacterService {

    private static final String POTTER_API_KEY= "$2a$10$ysdflW6CO054Zcl0VFhmXeHb/yXorWWm4NblCev.sU8NS8nEdaXz.";
    private static final String HOUSE_ROUTE= "https://www.potterapi.com/v1/houses";

    private final CharacterRepository characterRepository;

    @Autowired
    public CharacterService(CharacterRepository characterRepository){
        this.characterRepository = characterRepository;
    }

    public Character saveCharacter(Character character) throws InvalidHouseKeyException, JsonProcessingException {
        if(validateHouseKey(character.getHouse())){
          character = characterRepository.save(character);
        } else {
            throw new InvalidHouseKeyException();
        }
        return character;
    }

    public boolean validateHouseKey(String houseKey) throws JsonProcessingException {
        if(houseKey != null && !houseKey.isBlank()){
            RestTemplate restTemplate = new RestTemplateBuilder().build();
            String URL = HOUSE_ROUTE + "?key="+POTTER_API_KEY;
            String validKeys = restTemplate.getForObject(URL,String.class);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(validKeys);
            for(JsonNode validHouseKey : node.findValues("_id")){
                if(houseKey.equals(validHouseKey.toString().replace("\"", ""))){
                    return true;
                }
            }
        }

        return false;
    }
}
