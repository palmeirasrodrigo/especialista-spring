create table form_payment (
	id bigint not null auto_increment,
	description varchar(60) not null,
	primary key (id)
) engine=InnoDB default charset=utf8;

create table team (
	id bigint not null auto_increment,
	name varchar(60) not null,

	primary key (id)
) engine=InnoDB default charset=utf8;

create table team_permission (
	team_id bigint not null,
	permission_id bigint not null,

	primary key (team_id, permission_id)
) engine=InnoDB default charset=utf8;

create table permission (
	id bigint not null auto_increment,
	description varchar(60) not null,
	name varchar(100) not null,

	primary key (id)
) engine=InnoDB default charset=utf8;

create table product (
	id bigint not null auto_increment,
	restaurant_id bigint not null,
	name varchar(80) not null,
	description text not null,
	price decimal(10,2) not null,
	active tinyint(1) not null,

	primary key (id)
) engine=InnoDB default charset=utf8;

create table restaurant (
	id bigint not null auto_increment,
	kitchen_id bigint not null,
	name varchar(80) not null,
	shipping_fee decimal(10,2) not null,
	update_date datetime not null,
	create_at datetime not null,

	address_city_id bigint,
	address_cep varchar(9),
	address_public_place varchar(100),
	address_number varchar(20),
	address_complement varchar(60),
	address_district varchar(60),

	primary key (id)
) engine=InnoDB default charset=utf8;

create table restaurant_form_payment (
	restaurant_id bigint not null,
	form_payment_id bigint not null,

	primary key (restaurant_id, form_payment_id)
) engine=InnoDB default charset=utf8;

create table person (
	id bigint not null auto_increment,
	name varchar(80) not null,
	email varchar(255) not null,
	password varchar(255) not null,
	create_at datetime not null,

	primary key (id)
) engine=InnoDB default charset=utf8;

create table person_team (
	person_id bigint not null,
	team_id bigint not null,

	primary key (person_id, team_id)
) engine=InnoDB default charset=utf8;


alter table team_permission add constraint fk_team_permission_permission
foreign key (permission_id) references permission (id);

alter table team_permission add constraint fk_team_permission_team
foreign key (team_id) references team (id);

alter table product add constraint fk_product_restaurant
foreign key (restaurant_id) references restaurant (id);

alter table restaurant add constraint fk_restaurant_kitchen
foreign key (kitchen_id) references kitchen (id);

alter table restaurant add constraint fk_restaurant_city
foreign key (address_city_id) references city (id);

alter table restaurant_form_payment add constraint fk_rest_form_payment_form_payment
foreign key (form_payment_id) references form_payment (id);

alter table restaurant_form_payment add constraint fk_rest_form_payment_restaurant
foreign key (restaurant_id) references restaurant (id);

alter table person_team add constraint fk_person_team_team
foreign key (team_id) references team (id);

alter table person_team add constraint fk_person_team_person
foreign key (person_id) references person (id);