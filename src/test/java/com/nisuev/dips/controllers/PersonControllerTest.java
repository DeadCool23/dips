package com.nisuev.dips.controllers;

import com.nisuev.dips.services.PersonService;
import com.nisuev.dips.services.models.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class PersonControllerTest {

    @Mock
    private PersonService personService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new PersonController(personService)).build();
    }

    @Test
    void getPersonReturnsPersonResponse() throws Exception {
        when(personService.getPerson(7L))
                .thenReturn(Person.builder().id(7L).name("Иван").age(30).address("Москва").build());

        mockMvc.perform(get("/api/v1/persons/7"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(7))
                .andExpect(jsonPath("$.name").value("Иван"))
                .andExpect(jsonPath("$.age").value(30))
                .andExpect(jsonPath("$.address").value("Москва"));

        verify(personService).getPerson(7L);
    }

    @Test
    void createPersonReturnsCreatedLocation() throws Exception {
        when(personService.createPerson(any(Person.class))).thenReturn(11L);

        mockMvc.perform(post("/api/v1/persons")
                        .contentType("application/json")
                        .content("""
                                {"name":"Иван","age":25,"address":"Москва","work":"Завод"}
                                """))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/api/v1/persons/11"));

        verify(personService).createPerson(argThat(person ->
                person.getName().equals("Иван")
                        && person.getAge().equals(25)
                        && person.getAddress().equals("Москва")
                        && person.getWork().equals("Завод")));
    }
}
