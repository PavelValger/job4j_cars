ALTER TABLE car
ADD owner_id int not null unique references owners(id);