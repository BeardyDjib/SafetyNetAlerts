package com.openclassrooms.SafetyNetAlerts.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.SafetyNetAlerts.model.SafetyNetData;
import com.openclassrooms.SafetyNetAlerts.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.ObjectCodec;


import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonService {

    private static final Logger logger = LoggerFactory.getLogger(PersonService.class);
    private final ObjectMapper objectMapper;
    private final String filePath = "src/main/resources/data.json";

    public PersonService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<Person> getPersonsByAddress(String address) {
        try {
            SafetyNetData data = objectMapper.readValue(new File(filePath), SafetyNetData.class);
            return data.getPersons().stream()
                    .filter(person -> person.getAddress().equalsIgnoreCase(address))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            logger.error("Error reading data from file", e);
            throw new RuntimeException("Failed to read data from file", e);
        }
    }
}
