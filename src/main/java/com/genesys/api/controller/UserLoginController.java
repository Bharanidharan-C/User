package com.genesys.api.controller;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.genesys.api.dao.UserRepository;
import com.genesys.api.model.User;
import com.genesys.api.model.UserModel;
import com.genesys.api.model.UserNotFoundException;


/**
 * @author
 */
@RestController
@RequestMapping("/user")
public class UserLoginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserLoginController.class);

    private final UserRepository repository;

    public UserLoginController(UserRepository repository){
        super();
        this.repository = repository;
    }

    
    @GetMapping
    public List<User> getAllUsers(){
        return new ArrayList<>(this.repository.findAll());
    }

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody @Valid UserModel model){
        User User = this.repository.save(model.translateModelToUser());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(User.getId()).toUri();
        return ResponseEntity.created(location).body(User);
    }
    
    @PostMapping("/validate")
    public ResponseEntity<Boolean> validateUser(@RequestBody @Valid UserModel model){
    	  Optional<User> user = this.repository.findByEmail(model.getEmail());
	    	  if(user.isPresent() && user.get().getPassword().equals(model.getPassword())){
	    		 model.setLastLogin(new Date());
	    		  	updateUser(user.get().getId(), model);
	            	 return  ResponseEntity.ok(true);
	          } else {
	        	  	return ResponseEntity.ok(false);
	          }
    	  
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable int id){
        Optional<User> user = this.repository.findById(id);
        if(user.isPresent()){
            return user.get();
        }
        throw new UserNotFoundException("User not found with id: " + id);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable int id, @RequestBody UserModel model){
        Optional<User> existing = this.repository.findById(id);
        if(!existing.isPresent()){
            throw new UserNotFoundException("User not found with id: " + id);
        }
        User User = model.translateModelToUser();
        User.setId(id);
        return this.repository.save(User);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.RESET_CONTENT)
    public void deleteUser(@PathVariable int id){
        Optional<User> existing = this.repository.findById(id);
        if(!existing.isPresent()){
            throw new UserNotFoundException("User not found with id: " + id);
        }
        this.repository.deleteById(id);
    }
}
