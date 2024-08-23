package ru.spring.rest.spring.communication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.spring.rest.spring.model.Person;

import java.util.List;

@Component
public class Communication {
    private final String URL = "http://localhost:8080/persons";

    @Autowired
    private RestTemplate restTemplate;

    public List<Person> getAllPersons() {
        ResponseEntity<List<Person>> responseEntity = restTemplate.exchange(
                URL, HttpMethod.GET,null, new ParameterizedTypeReference<List<Person>>() {}
        );
        return responseEntity.getBody();
    }

    public Person getPersonById(int id) {
        return restTemplate.getForObject(URL + "/" + id, Person.class);
    }

    public void savePerson(Person person) {
        int id = person.getId();
        if (id == 0 ){
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(URL,person,String.class);
            System.out.println("Было совершено добавление нового сотрудника");
            System.out.println("Добавлен: " + responseEntity.getBody());;
        }else {
            restTemplate.put(URL,person);
            System.out.println("По данному ID: " + id + ", был обновлен сотрудник");
        }

    }
    public void deletePersonByID(int id) {
        restTemplate.delete(URL + "/" + id);
        System.out.println("Сотрудник с ID: "+id+" был удален из базы!");

    }

}