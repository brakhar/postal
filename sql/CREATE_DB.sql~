CREATE TABLE stamp_condition
(
  id numeric(2,0) NOT NULL,
  name character varying(255),
  CONSTRAINT stamp_condition_pkey PRIMARY KEY (id)
);

CREATE TABLE stamp_type
(
  id numeric(2,0) NOT NULL,
  name character varying(255),
  CONSTRAINT stamp_type_pkey PRIMARY KEY (id)
);


CREATE TABLE category
(
  id numeric(3,0) NOT NULL,
  name character varying(255),
  CONSTRAINT category_pkey PRIMARY KEY (id)
);

CREATE TABLE country
(
  id numeric(3,0) NOT NULL,
  name character varying(255),
  CONSTRAINT country_pkey PRIMARY KEY (id)
);


CREATE TABLE image
(
  id numeric(20,0) NOT NULL,
  content bytea,
  CONSTRAINT image_pkey PRIMARY KEY (id)
);

CREATE TABLE stamp
(
  id numeric(20,0) NOT NULL,
  description character varying(255),
  name character varying(255),
  year integer,
  category_id integer,
  country_id integer,
  image bytea,
  image_id bigint,
  keywords character varying(255),
  price numeric(19,2),
  remarks character varying(255),
  stamp_number character varying(255),
  face_value character varying(255),
  hinged boolean,
  stamp_type_id integer,
  stamp_condition_id integer,
  CONSTRAINT stamp_pkey PRIMARY KEY (id),
  CONSTRAINT fk_category FOREIGN KEY (category_id)
      REFERENCES category (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_hchaq33adr88adreatqa5bir FOREIGN KEY (stamp_type_id)
      REFERENCES stamp_type (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_p60b1pd8ncv0p2qwry1t1trfv FOREIGN KEY (stamp_condition_id)
      REFERENCES stamp_condition (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_s3tb7r9fycmockkduestc8n8o FOREIGN KEY (country_id)
      REFERENCES country (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE stamp_category
(
  stamp_id numeric(3,0) NOT NULL,
  category_id numeric(3,0) NOT NULL,
  CONSTRAINT stamp_category_pk PRIMARY KEY (stamp_id, category_id),
  CONSTRAINT category_id_fk FOREIGN KEY (category_id)
      REFERENCES category (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT stamp_id_fk FOREIGN KEY (stamp_id)
      REFERENCES stamp (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);


