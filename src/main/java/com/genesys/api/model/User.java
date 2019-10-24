package com.genesys.api.model;


import java.util.Date;

import javax.persistence.*;

/**
 * @author Bharanidharan C
 */

@Entity
@Table(name="USER")
public class User {


		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name="ID")
	    private int id;
	  	
	   
	    @Column(name="NAME")
	    private String name;
	    
	    
		@Column(name="EMAIL")
	    private String email;
	    
	    @Column(name="PASSWORD")
	    private String password;
	   
	    @Column(name = "LAST_LOGIN")
	    private Date lastLogin;

	    public User() {
	        super();
	    }
	   
	    
		public User(String name, String email, String password, Date lastLogin) {
			super();
			
			this.name = name;
			this.email = email;
			this.password = password;
			this.lastLogin = lastLogin;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public Date getLastLogin() {
			return lastLogin;
		}

		public void setLastLogin(Date lastLogin) {
			this.lastLogin = lastLogin;
		}
	 
}
