package br.com.hogwarts.repository;

import br.com.hogwarts.model.Character;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static br.com.hogwarts.TestUtils.createCharacter;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CharacterRepositoryTest {

    @Autowired
    TestEntityManager testEntityManager;
    @Autowired
    CharacterRepository characterRepository;

    @Test
    public void save_character(){
        Character character = new Character();
        character.setName("Test character");

        character = testEntityManager.persistAndFlush(character);

        Assert.assertEquals(character, characterRepository.findById(character.getId()).get());

    }

    @Test
    public void find_character_by_house(){
        Character character = new Character();
        character.setHouse("Test house");

        character = testEntityManager.persistAndFlush(character);

        Assert.assertEquals(character, characterRepository.findByHouse(character.getHouse()).get(0));

    }

}
