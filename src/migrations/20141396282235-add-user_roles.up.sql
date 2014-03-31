CREATE table user_roles (id int not null primary key auto_increment,
                         user_id int not null,
		         role_id int not null,
			 INDEX u_id (user_id),
                         INDEX r_id (role_id),
                         FOREIGN KEY (user_id) REFERENCES user(id),
			 FOREIGN KEY (role_id) REFERENCES role(id)) ENGINE=InnoDB;

