ALTER TABLE auto_post
ADD file_id int unique references files(id);