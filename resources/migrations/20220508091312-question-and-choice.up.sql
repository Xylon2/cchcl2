create table question (
  id serial not null primary key,
  question_text varchar(100),
  pub_date timestamp default current_timestamp
);

--;;

create table choice (
  id serial not null primary key,
  question integer references question (id) on delete cascade,
  choice_text varchar(100),
  votes integer default 0 not null
);
