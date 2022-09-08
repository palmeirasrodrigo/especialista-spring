create table restaurant_person_responsible (
  restaurant_id bigint not null,
  person_id bigint not null,
  
  primary key (restaurant_id, person_id)
) engine=InnoDB default charset=utf8;

alter table restaurant_person_responsible add constraint fk_restaurant_person_restaurant
foreign key (restaurant_id) references restaurant (id);

alter table restaurant_person_responsible add constraint fk_restaurant_person_person
foreign key (person_id) references person (id);