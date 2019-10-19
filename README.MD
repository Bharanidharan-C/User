Title: UserAPI
Description: 
It contains rest endpoint for Create, Update, Delete, List web service endpoints for a User object
User Object Contains:
name, email address, password and the date of their last login
Language and Framework used:
Java, SpringBoot

DB:
H2 in memory DB is used.

Server Port:
8090

Assumption:
-Email is unique for each user
-password is stored in encrypted manner using protegrity services, so that plain password texxt is not stored in DB
Future Enhancements:
Password can be encrypted and stored through code usring bcrypt algorithm

Sample Request Response:
1. Test Get All Users
Operation: 
Get
Request:
	http://localhost:8090/user
Response:
	[
	{
	id: 1,
	name: "AAA",
	email: "a@a.com",
	password: "Rfvb@098",
	lastLogin: "2015-12-15T18:30:00.000+0000"
	}
	]

2. Get Spcific User Details
Operation:
Post
Request:
http://localhost:8090/user/1
Response:
{
id: 1,
name: "AAA",
email: "a@a.com",
password: "Rfvb@098",
lastLogin: "2015-12-15T18:30:00.000+0000"
}

3. Add User
Operation:
Post
Request:
http://localhost:8090/user
Body:
	
	{
	
	"name": "C",
	"email": "a@c.com",
	"password": "Rfvb@098",
	"lastLogin": "2015-12-15T18:30:00.000+0000"
	}
	
Response:
	{
	    "id": 4,
	    "name": "C",
	    "email": "a@c.com",
	    "password": "Rfvb@098",
	    "lastLogin": "2015-12-15T18:30:00.000+0000"
	}
	

3. Update User
Operation:
PUT
Request:
http://localhost:8090/user
Body:
	
	{
	
	"name": "CC",
	"email": "a@c.com",
	"password": "Rfvb@098",
	"lastLogin": "2015-12-15T18:30:00.000+0000"
	}
	
Response:
	{
	    "id": 4,
	    "name": "CC",
	    "email": "a@c.com",
	    "password": "Rfvb@098",
	    "lastLogin": "2015-12-15T18:30:00.000+0000"
	}


3. Validate email and password
Operation:
POST
Request:
http://localhost:8090/user/validate
Body:
	
	
{


"email": "a@c.com",
"password": "Rfvb@098"

}

	
Response:
true


3. Update User
Operation:
DELETE
Request:
http://localhost:8090/user/1
Response:None.


