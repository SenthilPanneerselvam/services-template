CREATE TABLE privilege (
	id BIGINT(20) NOT null AUTO_INCREMENT,
	code VARCHAR(50) NOT NULL,
	label VARCHAR(50) NOT NULL,
	active BIT(1) NOT NULL,
	PRIMARY KEY(id)
);

CREATE TABLE role (
	id BIGINT(20) NOT null AUTO_INCREMENT,
	code VARCHAR(50) NOT NULL,
	label VARCHAR(50) NOT NULL,
	active BIT(1) NOT NULL,
	PRIMARY KEY(id)
);

CREATE TABLE role_privilege (
	id BIGINT(20) NOT null AUTO_INCREMENT,
	role_id BIGINT(20) NOT NULL,
	privilege_id BIGINT(20) NOT NULL,
	active BIT(1) NOT NULL default 0,
	PRIMARY KEY(id),
	FOREIGN KEY (role_id) REFERENCES role(id),
	FOREIGN KEY (privilege_id) REFERENCES privilege(id)
);

CREATE TABLE user (
	id BIGINT(20) not null AUTO_INCREMENT,
    active BIT(1) NOT NULL,
    country_phone_code varchar(255) default null,
    email_id varchar(255) NOT NULL,
    federation_id varchar(255) default null,
    federation_type varchar(255) default null,
    mobile_number varchar(20),
    password varchar(255) not null,
    salt varchar(255) not null,
    user_name varchar(255) not null,
    role_id BIGINT(20) NOT NULL,
    PRIMARY KEY(id),
    UNIQUE(user_name),
    UNIQUE(email_id),
    FOREIGN KEY (role_id) REFERENCES role(id)
);

-- master data insertion --

INSERT INTO role(id, code, label, active)
VALUES (1,'SYSTEM_USER','System user',0);

INSERT INTO user(id, active, email_id, password, salt, user_name, role_id)
VALUES (1,1,'system@template.com','changethis','somesalt','System User',1);
 