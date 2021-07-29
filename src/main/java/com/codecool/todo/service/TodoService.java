package com.codecool.todo.service;

import com.codecool.todo.entity.Todo;
import com.codecool.todo.model.Status;
import com.codecool.todo.model.TodoDto;
import com.codecool.todo.repository.TodoRepository;
import com.codecool.todo.util.StringToEnumConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private StringToEnumConverter stringToEnumConverter;

    public void addNewTodo(String todoTitle) {
        Todo todo = Todo.builder().title(todoTitle).status(Status.ACTIVE).build();
        todoRepository.save(todo);
    }

    public List<TodoDto> getAllByStatus(String status) {
        List<Todo> todos = new ArrayList<>();
        try {
            Status statusEnum = stringToEnumConverter.convert(status);
            Optional<List<Todo>> allByStatus = todoRepository.findAllByStatus(statusEnum);
            if (allByStatus.isPresent()){
                todos = allByStatus.get();
            }
            return convertEntityToDto(todos);
        } catch (Exception e){
            return convertEntityToDto(todoRepository.findAll());
        }
    }

    private List<TodoDto> convertEntityToDto(List<Todo> todos) {
        List<TodoDto> todoDtoList = new ArrayList<>();
        for (Todo todo : todos) {
            todoDtoList.add(TodoDto.builder()
                    .id(todo.getId())
                    .completed(todo.isCompleted())
                    .title(todo.getTitle())
                    .build());
        }
        return todoDtoList;
    }

    public void removeAllCompleted() {
        Optional<List<Todo>> allByStatus = todoRepository.findAllByStatus(Status.COMPLETE);
        if (allByStatus.isPresent()){
            List<Todo> todos = allByStatus.get();
            for (Todo todo : todos) {
                todoRepository.deleteById(todo.getId());
            }
        }
    }

    public void toggleAllStatus(boolean complete) {
        List<Todo> all = todoRepository.findAll();
        all.forEach(t -> t.setStatus(complete ? Status.COMPLETE : Status.ACTIVE));
        todoRepository.saveAll(all);
    }

    public void removeById(Long id) {
        todoRepository.deleteById(id);
    }

    public void updateById(String todoTitle, Long id) {
        Optional<Todo> todoOptional = todoRepository.findById(id);
        if (todoOptional.isPresent()){
            Todo todo = todoOptional.get();
            todo.setTitle(todoTitle);
            todoRepository.save(todo);
        }
    }

    public Todo findById(Long id) {
        Optional<Todo> todoOptional = todoRepository.findById(id);
        if (todoOptional.isPresent()){
            return todoOptional.get();
        } else throw new NoSuchElementException();
    }

    public void toggleStatus(boolean status, Long id) {
        Optional<Todo> todoOptional = todoRepository.findById(id);
        if (todoOptional.isPresent()) {
            Todo todo = todoOptional.get();
            todo.setStatus(status ? Status.COMPLETE : Status.ACTIVE);
            todoRepository.save(todo);
        }

    }
}
