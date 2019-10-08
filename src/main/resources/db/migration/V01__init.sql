create table product (
  id    integer      not null,
  name  varchar(100) not null,
  price float        not null,
  primary key (id)
);

create table review (
  id         integer not null,
  product_id integer not null,
  rating     float,
  text       varchar(500),
  primary key (id)

);

create table comment (
  id        integer not null,
  review_id integer not null,
  text      varchar(500),
  primary key (id)
);
