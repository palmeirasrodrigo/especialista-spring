create table demand (
  id bigint not null auto_increment,
  subtotal decimal(10,2) not null,
  shipping_fee decimal(10,2) not null,
  amount decimal(10,2) not null,

  restaurant_id bigint not null,
  person_client_id bigint not null,
  form_payment_id bigint not null,
  
  address_city_id bigint(20) not null,
  address_cep varchar(9) not null,
  address_public_place varchar(100) not null,
  address_number varchar(20) not null,
  address_complement varchar(60) not null,
  address_district varchar(60) not null,
  
  status varchar(10) not null,
  create_at datetime not null,
  confirmation_date datetime null,
  cancellation_date datetime null,
  delivery_date datetime null,

  primary key (id),

  constraint fk_demand_restaurant foreign key (restaurant_id) references restaurant (id),
  constraint fk_demand_person_client foreign key (person_client_id) references person (id),
  constraint fk_demand_form_payment foreign key (form_payment_id) references form_payment (id)
) engine=InnoDB default charset=utf8;

create table item_demand (
  id bigint not null auto_increment,
  quantity smallint(6) not null,
  unit_price decimal(10,2) not null,
  amount decimal(10,2) not null,
  note varchar(255) null,
  demand_id bigint not null,
  product_id bigint not null,
  
  primary key (id),
  unique key uk_item_demand_product (demand_id, product_id),

  constraint fk_item_demand_demand foreign key (demand_id) references demand (id),
  constraint fk_item_demand_product foreign key (product_id) references product (id)
) engine=InnoDB default charset=utf8;