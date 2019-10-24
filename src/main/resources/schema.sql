DROP TABLE IF EXISTS USER;
  
CREATE TABLE USER (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  NAME VARCHAR(250) NOT NULL,
  EMAIL VARCHAR(250) NOT NULL UNIQUE,
  PASSWORD VARCHAR(250) DEFAULT NULL,
  LAST_LOGIN DATE
);


