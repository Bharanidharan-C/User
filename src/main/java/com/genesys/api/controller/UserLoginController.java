package com.genesys.api.controller;



import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.genesys.api.model.ServiceException;
import com.genesys.api.model.User;
import com.genesys.api.model.UserModel;
import com.genesys.api.model.UserNotFoundException;
import com.genesys.api.service.UserService;


/**
 * @author
 */
@RestController
@RequestMapping("/user")
public class UserLoginController {

    private static Logger logger = LoggerFactory.getLogger(UserLoginController.class);

    @Autowired
    private  UserService service;


    @GetMapping
    public List<User> getAllUsers(){
        return service.getAllUsers();
    }

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody @Valid UserModel model){
        User user = service.addUser(model);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(location).body(user);
    }
    
    @PostMapping("/validate")
    public ResponseEntity<Boolean> validateUser(@RequestBody @Valid UserModel model){
    	boolean result = service.validateUser(model);
    	return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable int id){
      return service.getUser(id);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable int id, @RequestBody UserModel model){
        return service.updateUser(id, model);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.RESET_CONTENT)
    public void deleteUser(@PathVariable int id){
         service.deleteUser(id);
    }
    
    @ExceptionHandler(UserNotFoundException.class)
    public String handleUserNotFoundException(UserNotFoundException e) {
    	throw e;
    }
  
    
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleException(Exception e) {
    	logger.info(e.getMessage());
    	e.printStackTrace();
    	return "Service Error";
    }
    
  
    
}
