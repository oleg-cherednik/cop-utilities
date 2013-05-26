create table orders (
  history_id bigint identity,
  order_id bigint not null,
  user_id bigint not null,
  update_time timestamp not null,
  status varchar(20) not null)