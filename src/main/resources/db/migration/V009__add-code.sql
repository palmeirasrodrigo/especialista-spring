alter table demand add code varchar(36) not null after id;
update demand set code = uuid();
alter table demand add constraint uk_demand_code unique (code);