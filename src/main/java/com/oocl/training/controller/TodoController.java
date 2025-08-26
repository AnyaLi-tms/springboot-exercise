package com.oocl.training.controller;

import com.oocl.training.model.Todo;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// post https://localhost:8080/api/v1/todos
@RestController()
@RequestMapping("/api/v1")
public class TodoController {
    private final static Map<Integer, Todo> db = new HashMap<>();

    @PostMapping("/todos")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveTodo(@RequestBody Todo todo) {
        int id = todo.setId(db.size()+1);
        db.put(id, todo);
    }

    @GetMapping("/todos")
    public List<Todo> getTodos() {
        return new ArrayList<>(db.values());
    }
    @PutMapping("/todos")
    public void updateTodo(Todo todo) {

    }
}
