package com.genesys.api.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.genesys.api.dao.UserRepository;
import com.genesys.api.model.User;
import com.genesys.api.model.UserModel;
import com.genesys.api.model.UserNotFoundException;

@Service
public class UserService {
	 private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

	 @Autowired   
	 private  UserRepository repository;

	    
	    
	  
	    public List<User> getAllUsers(){
	        return new ArrayList<>(this.repository.findAll());
	    }

	
	    public User addUser(UserModel model){
	        User user = this.repository.save(model.translateModelToUser());	        
	        return user;
	    }
	    
	   
	    public boolean validateUser(UserModel model){
	    	  Optional<User> user = this.repository.findByEmail(model.getEmail());
		    	  if(user.isPresent() && user.get().getPassword().equals(model.getPassword())){
		    		 model.setLastLogin(new Date());
		    		  	updateUser(user.get().getId(), model);
		            	 return  true;
		          } else {
		        	  	return false;
		          }
	    	  
	    }


	    public User getUser(int id){
	        Optional<User> user = this.repository.findById(id);
	        if(user.isPresent()){
	            return user.get();
	        }
	        throw new UserNotFoundException("User not found with id: " + id);
	    }

	    
	    public User updateUser( int id,  UserModel model){
	        Optional<User> existingUser = this.repository.findById(id);
	        User user = model.translateModelToUser();
	        if(existingUser.isPresent()){
	        	user.setId(existingUser.get().getId());
	 	        return this.repository.save(user);
	        }else {
	        	return addUser(model);
	        }
	       
	    }

	    
	    public void deleteUser(int id){
	        Optional<User> existing = this.repository.findById(id);
	        if(!existing.isPresent()){
	            throw new UserNotFoundException("User not found with id: " + id);
	        }
	        this.repository.deleteById(id);
	    }
}
