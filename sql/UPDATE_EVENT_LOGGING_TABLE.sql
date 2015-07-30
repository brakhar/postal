INSERT INTO event_logging(id, event_type_id, user_name, action_date, quantity, stamp_id)

SELECT nextval('postall_sequence'), 1, 'mkyong', '2015-06-19', quantity, stamp_id FROM user_stamp
