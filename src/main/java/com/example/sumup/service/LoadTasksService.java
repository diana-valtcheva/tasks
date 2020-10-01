package com.example.sumup.service;

import com.example.sumup.model.TasksSequence;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Reader;

@Service
public class LoadTasksService {

    @Autowired
    ObjectMapper mapper;

    public TasksSequence load(Reader reader) throws IOException {
        return mapper.readValue(reader, TasksSequence.class);
    }
}
