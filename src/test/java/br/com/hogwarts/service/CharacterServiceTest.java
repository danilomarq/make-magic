package br.com.hogwarts.service;


import br.com.hogwarts.TestUtils;
import br.com.hogwarts.exception.InvalidHouseKeyException;
import br.com.hogwarts.model.Character;
import br.com.hogwarts.repository.CharacterRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import static br.com.hogwarts.TestUtils.createCharacter;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CharacterServiceTest {

    @Mock
    private CharacterRepository characterRepository;

    @InjectMocks
    private CharacterService characterService;

    @Test
    public void when_save_character_should_return_character() throws Exception {
        Character character = createCharacter();
        character.setId(null);

        Character mockCharacter = createCharacter();

        when(characterRepository.save(any(Character.class))).thenReturn(mockCharacter);

        Character created = characterService.saveCharacter(character);

        Assert.assertEquals(mockCharacter.getId(), created.getId());
    }

    @Test(expected = InvalidHouseKeyException.class)
    public void when_save_with_invalid_house_key_should_return_InvalidHouseKeyException() throws Exception {
        Character character = createCharacter();
        character.setHouse("Invalid Key");

        characterService.saveCharacter(character);
    }

    @Test
    public void when_validate_valid_house_key_should_return_true() throws JsonProcessingException {
        Assert.assertTrue(characterService.validateHouseKey(TestUtils.VALID_HOUSE_KEY));
    }

    @Test
    public void when_validate_valid_house_key_should_return_false() throws JsonProcessingException {
        Assert.assertFalse(characterService.validateHouseKey("Invalid house key"));
    }



}
