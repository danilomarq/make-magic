package br.com.hogwarts.repository;

import br.com.hogwarts.model.Character;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CharacterRepository extends CrudRepository<Character, Integer> {
    public List<Character> findByHouse(String houseKey);
}
