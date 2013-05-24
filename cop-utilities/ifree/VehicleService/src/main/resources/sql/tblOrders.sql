create table orders (
  order_id bigint identity,
  customer_id bigint not null,
  vehicle_part_id bigint not null,
  create_time timestamp not null,
  update_time timestamp not null,
  status varchar(20) not null)