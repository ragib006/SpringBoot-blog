package com.brandyodhiambo.ThoughtHub.controller;

import com.brandyodhiambo.ThoughtHub.model.Todo;
import com.brandyodhiambo.ThoughtHub.payload.response.ApiResponse;
import com.brandyodhiambo.ThoughtHub.payload.response.PagedResponse;
import com.brandyodhiambo.ThoughtHub.security.CurrentUser;
import com.brandyodhiambo.ThoughtHub.service.impl.UserPrincipal;
import com.brandyodhiambo.ThoughtHub.service.TodoService;
import com.brandyodhiambo.ThoughtHub.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

	@Autowired
	private TodoService todoService;

	@GetMapping
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<PagedResponse<Todo>> getAllTodos(
			@CurrentUser UserPrincipal currentUser,
			@RequestParam(value = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
			@RequestParam(name = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size) {

		PagedResponse<Todo> response = todoService.getAllTodos(currentUser, page, size);

		return new ResponseEntity< >(response, HttpStatus.OK);
	}

	@PostMapping
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<Todo> addTodo(@Valid @RequestBody Todo todo, @CurrentUser UserPrincipal currentUser) {
		Todo newTodo = todoService.addTodo(todo, currentUser);

		return new ResponseEntity< >(newTodo, HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<Todo> getTodo(@PathVariable(value = "id") Long id, @CurrentUser UserPrincipal currentUser) {
		Todo todo = todoService.getTodo(id, currentUser);

		return new ResponseEntity< >(todo, HttpStatus.OK);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<Todo> updateTodo(@PathVariable(value = "id") Long id, @Valid @RequestBody Todo newTodo,
			@CurrentUser UserPrincipal currentUser) {
		Todo updatedTodo = todoService.updateTodo(id, newTodo, currentUser);

		return new ResponseEntity< >(updatedTodo, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<ApiResponse> deleteTodo(@PathVariable(value = "id") Long id, @CurrentUser UserPrincipal currentUser) {
		ApiResponse apiResponse = todoService.deleteTodo(id, currentUser);

		return new ResponseEntity<>(apiResponse, HttpStatus.OK);
	}

	@PutMapping("/{id}/complete")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<Todo> completeTodo(@PathVariable(value = "id") Long id, @CurrentUser UserPrincipal currentUser) {

		Todo todo = todoService.completeTodo(id, currentUser);

		return new ResponseEntity< >(todo, HttpStatus.OK);
	}

	@PutMapping("/{id}/unComplete")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<Todo> unCompleteTodo(@PathVariable(value = "id") Long id, @CurrentUser UserPrincipal currentUser) {

		Todo todo = todoService.unCompleteTodo(id, currentUser);

		return new ResponseEntity< >(todo, HttpStatus.OK);
	}
}
