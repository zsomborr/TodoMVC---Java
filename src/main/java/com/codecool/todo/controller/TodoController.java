package com.codecool.todo.controller;

import com.codecool.todo.entity.Todo;
import com.codecool.todo.model.TodoDto;
import com.codecool.todo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TodoController {

    @Autowired
    private TodoService todoService;

    private static final String SUCCESS = "{\"success\":true}";

    @PostMapping("/addTodo")
    public String addNewTodo(@RequestParam("todo-title") String todoTitle){
        try {
            todoService.addNewTodo(todoTitle);
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @PostMapping("/list")
    public List<TodoDto> listByStatus(@RequestParam String status){
        return todoService.getAllByStatus(status);
    }

    @DeleteMapping("/todos/completed")
    public String deleteAllCompleted(){
        try {
            todoService.removeAllCompleted();
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @PutMapping("/todos/toggle_all")
    public String toggleAllStatus(@RequestParam("toggle-all") boolean complete) {
        try {
            todoService.toggleAllStatus(complete);
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @DeleteMapping("/todos/{id}")
    public String deleteTodo(@PathVariable Long id){
        try {
            todoService.removeById(id);
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @PutMapping("/todos/{id}")
    public String updateTodo(@RequestParam("todo-title") String todoTitle, @PathVariable Long id) {
        try {
            todoService.updateById(todoTitle, id);
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @GetMapping("/todos/{id}")
    public Todo getTodo(@PathVariable Long id){
        try {
            return todoService.findById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PutMapping("/todos/{id}/toggle_status")
    public String toggleStatus(@RequestParam boolean status, @PathVariable Long id) {
        try {
            todoService.toggleStatus(status, id);
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

//        // Toggle status by id
//        put("/todos/:id/toggle_status", (req, res) -> {
//            boolean completed = req.queryParams("status").equals("true");
//            TodoDao.toggleStatus(req.params("id"), completed);
//            return SUCCESS;
//        });


}