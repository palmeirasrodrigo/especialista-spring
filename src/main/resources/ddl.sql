create table city (id bigint not null auto_increment, name varchar(255) not null, state_id bigint not null, primary key (id)) engine=InnoDB;
create table form_payment (id bigint not null auto_increment, description varchar(255) not null, primary key (id)) engine=InnoDB;
create table group (id bigint not null auto_increment, name varchar(255) not null, primary key (id)) engine=InnoDB;
create table group_permission (group_id bigint not null, permission_id bigint not null) engine=InnoDB;
create table kitchen (id bigint not null auto_increment, name varchar(255) not null, primary key (id)) engine=InnoDB;
create table permission (id bigint not null auto_increment, description varchar(255) not null, name varchar(255) not null, primary key (id)) engine=InnoDB;
create table product (id bigint not null auto_increment, active bit not null, description varchar(255) not null, name varchar(255) not null, price decimal(19,2) not null, restaurant_id bigint not null, primary key (id)) engine=InnoDB;
create table restaurant (id bigint not null auto_increment, address_cep varchar(255), address_complement varchar(255), address_district varchar(255), address_number varchar(255), address_public_place varchar(255), create_at datetime not null, name varchar(255) not null, shipping_fee decimal(19,2) not null, update_date datetime not null, address_city_id bigint, kitchen_id bigint not null, primary key (id)) engine=InnoDB;
create table restaurant_form_payment (restaurant_id bigint not null, form_payment_id bigint not null) engine=InnoDB;
create table state (id bigint not null auto_increment, name varchar(255) not null, primary key (id)) engine=InnoDB;
create table user (id bigint not null auto_increment, create_at datetime not null, email varchar(255) not null, name varchar(255) not null, password varchar(255) not null, primary key (id)) engine=InnoDB;
create table user_group (user_id bigint not null, group_id bigint not null) engine=InnoDB;
alter table city add constraint FK6p2u50v8fg2y0js6djc6xanit foreign key (state_id) references state (id);
alter table group_permission add constraint FKss14p30qbokhpkpdov4ha3wro foreign key (permission_id) references permission (id);
alter table group_permission add constraint FK4twq9e99a6jwgl699bqlu5tsc foreign key (group_id) references group (id);
alter table product add constraint FKp4b7e36gck7975p51raud8juk foreign key (restaurant_id) references restaurant (id);
alter table restaurant add constraint FK8pcwgn41lfg43d8u82ewn3cn foreign key (address_city_id) references city (id);
alter table restaurant add constraint FKrur1dqx79jim8s8dvdp16qc3d foreign key (kitchen_id) references kitchen (id);
alter table restaurant_form_payment add constraint FK80rp4krwb9tjg25d9a57kfvxo foreign key (form_payment_id) references form_payment (id);
alter table restaurant_form_payment add constraint FK7b8eis4ak2l7j8ra4yt1qef3o foreign key (restaurant_id) references restaurant (id);
alter table user_group add constraint FKjonf4pqux3h1e687sd18dhcnj foreign key (group_id) references group (id);
alter table user_group add constraint FK1c1dsw3q36679vaiqwvtv36a6 foreign key (user_id) references user (id);