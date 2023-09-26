ALTER TABLE files
ADD auto_post_id int references auto_post(id);