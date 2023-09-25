ALTER TABLE owners
ADD history_id int unique references history(id);