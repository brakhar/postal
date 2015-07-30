select catalog_number, block_number, block from stamp where block=true;



--update stamp SET catalog_number=regexp_replace(catalog_number, '(\(b[0-9]+)\)', '') WHERE block=true;


--update stamp SET block_number=substring(catalog_number from '(b[0-9]+)') WHERE block=true;

--select publish_date, catalog_number, title, small_paper, block from stamp where stamp_image_id is null order by publish_date