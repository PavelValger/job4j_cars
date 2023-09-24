ALTER TABLE owners
ADD history_id int not null unique references history(id);