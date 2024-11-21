package com.in28minutes.springboot.myfirstwebapp.todo;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;

@Controller
public class TodoControllerJpa {
	
	private TodoRepository todoRepository;
	
	public TodoControllerJpa(TodoRepository todoRepository) {
		super();
		this.todoRepository = todoRepository;
	}
	
	//go to http://localhost:8080/list-todos
	@RequestMapping("list-todos")
	public String listAllTodos(ModelMap model) {
		String username = getLoggedInUsername();
		List<Todo> todos = todoRepository.findByUsername(username);
		model.addAttribute("todos",todos);
		return "listTodos";
	}
	
	//Also called getMapping
	@RequestMapping(value="add-todo",method=RequestMethod.GET) //one-way binding of command bean
	public String showNewTodoPage(ModelMap model) {
		
		//**backing form object (command bean) is created...
		//giving value from bean (command bean) to form field (description) in add-todo through todo.jsp......
		Todo todo = new Todo(0, "", "", null, false); 
		model.put("todo", todo);
		return "todo";
	}
	
	
	///Also called postMapping
	@RequestMapping(value="add-todo",method=RequestMethod.POST) //two-way binding of command bean
	//BindingResult returns the result of validations used.....
	public String addNewTodo(ModelMap model,@Valid Todo todo,BindingResult result) { 
		
		if(result.hasErrors()) {
			return "todo";
		}
		
		//Needs to fetch the username again, as the user fills only description and targetDate on the add-todo page and that will only be stored in backing object......
		String username = getLoggedInUsername();
		todo.setUsername(username);
		todoRepository.save(todo);
		return "redirect:list-todos"; //url needs to be given in redirect:___
	}
	
	@RequestMapping("delete-todo")
	public String deleteTodos(@RequestParam int id) {
		todoRepository.deleteById(id);
		return "redirect:list-todos";
	}
	
	@RequestMapping(value="update-todo",method=RequestMethod.GET)
	public String showUpdateTodoPage(@RequestParam int id, ModelMap model) {
		Todo todo = todoRepository.findById(id).get();
		model.addAttribute("todo", todo);
		return "todo";
	}
	
	@RequestMapping(value="update-todo",method=RequestMethod.POST) //two-way binding of command bean
	public String updateTodo(@Valid Todo todo,BindingResult result) { 
		if(result.hasErrors()) {
			return "todo";
		}
		todo.setUsername(getLoggedInUsername());
		todoRepository.save(todo);
		return "redirect:list-todos";
	}
	
	private String getLoggedInUsername() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication.getName();
	}
}
