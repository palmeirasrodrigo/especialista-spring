alter table form_payment add update_date datetime null;
update form_payment set update_date = utc_timestamp;
alter table form_payment modify update_date datetime not null;