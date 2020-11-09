package br.com.hogwarts;

import br.com.hogwarts.model.Character;

public class TestUtils {

    public static final String VALID_HOUSE_KEY = "5a05e2b252f721a3cf2ea33f";

    public static  Character createCharacter() {
        Character mockCharacter = new Character();
        mockCharacter.setId(1);
        mockCharacter.setName("Harry Potter");
        mockCharacter.setRole("student");
        mockCharacter.setHouse(VALID_HOUSE_KEY);
        mockCharacter.setSchool("Hogwarts School of Witchcraft and Wizardry");
        mockCharacter.setPatronus("stag");
        return mockCharacter;
    }
}
