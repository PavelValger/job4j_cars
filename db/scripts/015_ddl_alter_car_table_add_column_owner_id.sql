ALTER TABLE car
ADD owner_id int unique references owners(id);