CREATE USER IF NOT EXISTS jwduser IDENTIFIED BY 'pass';

DROP DATABASE IF EXISTS AirportSecurityJPAReact;
CREATE DATABASE AirportSecurityJPAReact DEFAULT CHARACTER SET utf8;

USE AirportSecurityJPAReact;

GRANT ALL ON AirportSecurityJPAReact.* TO 'jwduser'@'%';

FLUSH PRIVILEGES;

