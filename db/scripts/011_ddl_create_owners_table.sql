create table owners(
    id serial primary key,
    name text not null,
    user_id int references auto_user(id)
);