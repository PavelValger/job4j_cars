ALTER TABLE price_history
ADD auto_post_id int references auto_post(id);