--
-- PostgreSQL database dump
--

-- Dumped from database version 9.3.6
-- Dumped by pg_dump version 9.3.6
-- Started on 2015-07-02 18:29:17

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- TOC entry 186 (class 3079 OID 11750)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2052 (class 0 OID 0)
-- Dependencies: 186
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

--
-- TOC entry 535 (class 1247 OID 26664)
-- Name: userstamptype; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE userstamptype AS (
	name_stamp character varying(255),
	user_name character varying(255),
	quantity integer,
	year integer
);


ALTER TYPE public.userstamptype OWNER TO postgres;

--
-- TOC entry 199 (class 1255 OID 26665)
-- Name: deletestamp(numeric, bigint, bigint); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION deletestamp(v_stamp_id numeric, v_image_id_small bigint, v_image_id_big bigint) RETURNS void
    LANGUAGE plpgsql
    AS $$
    BEGIN
	    RAISE NOTICE 'deleting stamp with id(%)', v_stamp_id;
	    DELETE FROM stamp_category WHERE stamp_id = v_stamp_id;	
	    DELETE FROM stamp WHERE stamp.id = v_stamp_id;
	    DELETE FROM image WHERE id = v_image_id_small or id = v_image_id_big;
    END;
    $$;


ALTER FUNCTION public.deletestamp(v_stamp_id numeric, v_image_id_small bigint, v_image_id_big bigint) OWNER TO postgres;

--
-- TOC entry 200 (class 1255 OID 26666)
-- Name: get_user_stamps(character varying, integer, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION get_user_stamps(v_user_name character varying, start_index integer, number_to_return integer) RETURNS SETOF userstamptype
    LANGUAGE plpgsql
    AS $$
    DECLARE
      r userStampType%rowtype;
    BEGIN
	FOR r IN SELECT sp.name, sp.year, sp.image_id, us.quantity FROM stamp sp LEFT JOIN user_stamp us 
		ON us.stamp_id = sp.id AND us.user_name = v_user_name offset start_index limit number_to_return  
	LOOP
		RETURN NEXT r;
	END LOOP;
	RETURN;
    END;
$$;


ALTER FUNCTION public.get_user_stamps(v_user_name character varying, start_index integer, number_to_return integer) OWNER TO postgres;

--
-- TOC entry 201 (class 1255 OID 26667)
-- Name: removedublicates(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION removedublicates() RETURNS void
    LANGUAGE plpgsql
    AS $$
    DECLARE
	uniqueStamp stamp%rowtype;
	dublicateStamp stamp%rowtype;
    BEGIN
	FOR uniqueStamp IN SELECT * FROM stamp
	LOOP
	    FOR dublicateStamp IN SELECT * FROM stamp WHERE stamp.id>uniqueStamp.id and stamp.name=uniqueStamp.name
	    LOOP
		EXECUTE deleteStamp(dublicateStamp.id, dublicateStamp.image_id, dublicateStamp.big_image_id);
	    END LOOP;
	END LOOP;
    END;
    $$;


ALTER FUNCTION public.removedublicates() OWNER TO postgres;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 171 (class 1259 OID 26668)
-- Name: category; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE category (
    id numeric(20,0) NOT NULL,
    name character varying(255) NOT NULL
);


ALTER TABLE public.category OWNER TO postgres;

--
-- TOC entry 172 (class 1259 OID 26671)
-- Name: country; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE country (
    id numeric(3,0) NOT NULL,
    name character varying(255) NOT NULL
);


ALTER TABLE public.country OWNER TO postgres;

--
-- TOC entry 173 (class 1259 OID 26674)
-- Name: event_logging; Type: TABLE; Schema: public; Owner: admin; Tablespace: 
--

CREATE TABLE event_logging (
    id numeric(30,0) NOT NULL,
    event_type_id integer NOT NULL,
    user_name character varying(20) NOT NULL,
    action_date date,
    quantity integer,
    stamp_id bigint
);


ALTER TABLE public.event_logging OWNER TO admin;

--
-- TOC entry 174 (class 1259 OID 26677)
-- Name: event_type; Type: TABLE; Schema: public; Owner: admin; Tablespace: 
--

CREATE TABLE event_type (
    id integer NOT NULL,
    name character varying(20) NOT NULL
);


ALTER TABLE public.event_type OWNER TO admin;

--
-- TOC entry 175 (class 1259 OID 26680)
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: admin
--

CREATE SEQUENCE hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 99999999999999
    CACHE 1
    CYCLE;


ALTER TABLE public.hibernate_sequence OWNER TO admin;

--
-- TOC entry 176 (class 1259 OID 26682)
-- Name: image; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE image (
    id numeric(20,0) NOT NULL,
    content bytea NOT NULL,
    alt character varying(255),
    height integer NOT NULL,
    width integer NOT NULL
);


ALTER TABLE public.image OWNER TO postgres;

--
-- TOC entry 185 (class 1259 OID 29375)
-- Name: postall_sequence; Type: SEQUENCE; Schema: public; Owner: admin
--

CREATE SEQUENCE postall_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 10000000
    CACHE 1;


ALTER TABLE public.postall_sequence OWNER TO admin;

--
-- TOC entry 177 (class 1259 OID 26688)
-- Name: stamp; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE stamp (
    id numeric(20,0) NOT NULL,
    block_image_id bigint,
    circulation text,
    design text,
    format text,
    perforation text,
    stamp_image_id bigint,
    denomination_stamp text,
    full_title text,
    number_stamp_in_piece_paper text,
    origin_publish text,
    publish_date timestamp without time zone,
    security text,
    short_title text,
    special_notes text,
    type_publish text,
    catalog_number text,
    bar_code text
);


ALTER TABLE public.stamp OWNER TO postgres;

--
-- TOC entry 178 (class 1259 OID 26694)
-- Name: stamp_category; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE stamp_category (
    stamp_id numeric(20,0) NOT NULL,
    category_id numeric(20,0) NOT NULL
);


ALTER TABLE public.stamp_category OWNER TO postgres;

--
-- TOC entry 179 (class 1259 OID 26697)
-- Name: stamp_condition; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE stamp_condition (
    id numeric(3,0) NOT NULL,
    name character varying(255) NOT NULL
);


ALTER TABLE public.stamp_condition OWNER TO postgres;

--
-- TOC entry 183 (class 1259 OID 26712)
-- Name: stamp_history; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE stamp_history (
    id numeric(30,0) NOT NULL,
    stamp_id numeric(20,0) NOT NULL,
    description character varying(255),
    name character varying(255),
    year integer,
    category_id integer,
    image_id bigint,
    keywords character varying(255),
    big_image_id bigint,
    number character varying(255)
);


ALTER TABLE public.stamp_history OWNER TO postgres;

--
-- TOC entry 180 (class 1259 OID 26700)
-- Name: stamp_type; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE stamp_type (
    id numeric(3,0) NOT NULL,
    name character varying(255) NOT NULL
);


ALTER TABLE public.stamp_type OWNER TO postgres;

--
-- TOC entry 181 (class 1259 OID 26703)
-- Name: user_roles; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE user_roles (
    user_role_id numeric(11,0) NOT NULL,
    role character varying(45) NOT NULL,
    user_name character varying(255)
);


ALTER TABLE public.user_roles OWNER TO postgres;

--
-- TOC entry 182 (class 1259 OID 26706)
-- Name: user_stamp; Type: TABLE; Schema: public; Owner: admin; Tablespace: 
--

CREATE TABLE user_stamp (
    stamp_id numeric(20,0) NOT NULL,
    quantity integer NOT NULL,
    user_name character varying(255) NOT NULL,
    stampid bigint NOT NULL,
    username character varying(255) NOT NULL
);


ALTER TABLE public.user_stamp OWNER TO admin;

--
-- TOC entry 184 (class 1259 OID 26722)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE users (
    user_name character varying(45) NOT NULL,
    password character varying(60) NOT NULL,
    enabled boolean DEFAULT true NOT NULL
);


ALTER TABLE public.users OWNER TO postgres;

--
-- TOC entry 2030 (class 0 OID 26668)
-- Dependencies: 171
-- Data for Name: category; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY category (id, name) FROM stdin;
\.


--
-- TOC entry 2031 (class 0 OID 26671)
-- Dependencies: 172
-- Data for Name: country; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY country (id, name) FROM stdin;
\.


--
-- TOC entry 2032 (class 0 OID 26674)
-- Dependencies: 173
-- Data for Name: event_logging; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY event_logging (id, event_type_id, user_name, action_date, quantity, stamp_id) FROM stdin;
\.


--
-- TOC entry 2033 (class 0 OID 26677)
-- Dependencies: 174
-- Data for Name: event_type; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY event_type (id, name) FROM stdin;
1	Add
2	Update
3	Delete
\.


--
-- TOC entry 2053 (class 0 OID 0)
-- Dependencies: 175
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; Owner: admin
--

SELECT pg_catalog.setval('hibernate_sequence', 18376, true);


--
-- TOC entry 2035 (class 0 OID 26682)
-- Dependencies: 176
-- Data for Name: image; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY image (id, content, alt, height, width) FROM stdin;
\.


--
-- TOC entry 2054 (class 0 OID 0)
-- Dependencies: 185
-- Name: postall_sequence; Type: SEQUENCE SET; Schema: public; Owner: admin
--

SELECT pg_catalog.setval('postall_sequence', 101, true);


--
-- TOC entry 2036 (class 0 OID 26688)
-- Dependencies: 177
-- Data for Name: stamp; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY stamp (id, block_image_id, circulation, design, format, perforation, stamp_image_id, denomination_stamp, full_title, number_stamp_in_piece_paper, origin_publish, publish_date, security, short_title, special_notes, type_publish, catalog_number, bar_code) FROM stdin;
\.


--
-- TOC entry 2037 (class 0 OID 26694)
-- Dependencies: 178
-- Data for Name: stamp_category; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY stamp_category (stamp_id, category_id) FROM stdin;
\.


--
-- TOC entry 2038 (class 0 OID 26697)
-- Dependencies: 179
-- Data for Name: stamp_condition; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY stamp_condition (id, name) FROM stdin;
\.


--
-- TOC entry 2042 (class 0 OID 26712)
-- Dependencies: 183
-- Data for Name: stamp_history; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY stamp_history (id, stamp_id, description, name, year, category_id, image_id, keywords, big_image_id, number) FROM stdin;
\.


--
-- TOC entry 2039 (class 0 OID 26700)
-- Dependencies: 180
-- Data for Name: stamp_type; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY stamp_type (id, name) FROM stdin;
\.


--
-- TOC entry 2040 (class 0 OID 26703)
-- Dependencies: 181
-- Data for Name: user_roles; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY user_roles (user_role_id, role, user_name) FROM stdin;
1	ROLE_USER	mkyong
2	ROLE_ADMIN	mkyong
3	ROLE_USER	alex
\.


--
-- TOC entry 2041 (class 0 OID 26706)
-- Dependencies: 182
-- Data for Name: user_stamp; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY user_stamp (stamp_id, quantity, user_name, stampid, username) FROM stdin;
\.


--
-- TOC entry 2043 (class 0 OID 26722)
-- Dependencies: 184
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY users (user_name, password, enabled) FROM stdin;
mkyong	$2a$10$04TVADrR6/SPLBjsK0N30.Jf5fNjBugSACeGv1S69dZALR7lSov0y	t
alex	$2a$10$04TVADrR6/SPLBjsK0N30.Jf5fNjBugSACeGv1S69dZALR7lSov0y	t
\.


--
-- TOC entry 1886 (class 2606 OID 29221)
-- Name: category_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY category
    ADD CONSTRAINT category_pkey PRIMARY KEY (id);


--
-- TOC entry 1888 (class 2606 OID 29223)
-- Name: country_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY country
    ADD CONSTRAINT country_pkey PRIMARY KEY (id);


--
-- TOC entry 1890 (class 2606 OID 29225)
-- Name: event_logging_pk; Type: CONSTRAINT; Schema: public; Owner: admin; Tablespace: 
--

ALTER TABLE ONLY event_logging
    ADD CONSTRAINT event_logging_pk PRIMARY KEY (id);


--
-- TOC entry 1892 (class 2606 OID 29227)
-- Name: event_type_pk; Type: CONSTRAINT; Schema: public; Owner: admin; Tablespace: 
--

ALTER TABLE ONLY event_type
    ADD CONSTRAINT event_type_pk PRIMARY KEY (id);


--
-- TOC entry 1894 (class 2606 OID 29229)
-- Name: image_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY image
    ADD CONSTRAINT image_pkey PRIMARY KEY (id);


--
-- TOC entry 1908 (class 2606 OID 29231)
-- Name: pk_user_stamp; Type: CONSTRAINT; Schema: public; Owner: admin; Tablespace: 
--

ALTER TABLE ONLY user_stamp
    ADD CONSTRAINT pk_user_stamp PRIMARY KEY (stamp_id, user_name);


--
-- TOC entry 1898 (class 2606 OID 29233)
-- Name: stamp_category_pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY stamp_category
    ADD CONSTRAINT stamp_category_pk PRIMARY KEY (stamp_id, category_id);


--
-- TOC entry 1900 (class 2606 OID 29235)
-- Name: stamp_condition_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY stamp_condition
    ADD CONSTRAINT stamp_condition_pkey PRIMARY KEY (id);


--
-- TOC entry 1910 (class 2606 OID 29237)
-- Name: stamp_history_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY stamp_history
    ADD CONSTRAINT stamp_history_pkey PRIMARY KEY (id);


--
-- TOC entry 1896 (class 2606 OID 29239)
-- Name: stamp_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY stamp
    ADD CONSTRAINT stamp_pkey PRIMARY KEY (id);


--
-- TOC entry 1902 (class 2606 OID 29241)
-- Name: stamp_type_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY stamp_type
    ADD CONSTRAINT stamp_type_pkey PRIMARY KEY (id);


--
-- TOC entry 1905 (class 2606 OID 29243)
-- Name: user_roles_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY user_roles
    ADD CONSTRAINT user_roles_pkey PRIMARY KEY (user_role_id);


--
-- TOC entry 1912 (class 2606 OID 29245)
-- Name: users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_pkey PRIMARY KEY (user_name);


--
-- TOC entry 1906 (class 1259 OID 29246)
-- Name: fki_stamp_user_user_name; Type: INDEX; Schema: public; Owner: admin; Tablespace: 
--

CREATE INDEX fki_stamp_user_user_name ON user_stamp USING btree (user_name);


--
-- TOC entry 1903 (class 1259 OID 29247)
-- Name: fki_user_roles_user_name; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX fki_user_roles_user_name ON user_roles USING btree (user_name);


--
-- TOC entry 1916 (class 2606 OID 29248)
-- Name: category_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY stamp_category
    ADD CONSTRAINT category_id_fk FOREIGN KEY (category_id) REFERENCES category(id);


--
-- TOC entry 1913 (class 2606 OID 29253)
-- Name: event_logging_fk_event_type; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY event_logging
    ADD CONSTRAINT event_logging_fk_event_type FOREIGN KEY (event_type_id) REFERENCES event_type(id) ON DELETE RESTRICT;


--
-- TOC entry 1914 (class 2606 OID 29263)
-- Name: event_logging_fk_user; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY event_logging
    ADD CONSTRAINT event_logging_fk_user FOREIGN KEY (user_name) REFERENCES users(user_name) ON DELETE RESTRICT;


--
-- TOC entry 1921 (class 2606 OID 29273)
-- Name: fk_category; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY stamp_history
    ADD CONSTRAINT fk_category FOREIGN KEY (category_id) REFERENCES category(id);


--
-- TOC entry 1915 (class 2606 OID 29370)
-- Name: fk_dmi6f8vxw7o5bgbijnqjeya7j; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY event_logging
    ADD CONSTRAINT fk_dmi6f8vxw7o5bgbijnqjeya7j FOREIGN KEY (stamp_id) REFERENCES stamp(id);


--
-- TOC entry 1922 (class 2606 OID 29278)
-- Name: fk_stamp_history; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY stamp_history
    ADD CONSTRAINT fk_stamp_history FOREIGN KEY (stamp_id) REFERENCES stamp(id);


--
-- TOC entry 1919 (class 2606 OID 29283)
-- Name: fk_stamp_user_user_name; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY user_stamp
    ADD CONSTRAINT fk_stamp_user_user_name FOREIGN KEY (user_name) REFERENCES users(user_name);


--
-- TOC entry 1918 (class 2606 OID 29288)
-- Name: fk_user_roles_user_name; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY user_roles
    ADD CONSTRAINT fk_user_roles_user_name FOREIGN KEY (user_name) REFERENCES users(user_name);


--
-- TOC entry 1917 (class 2606 OID 29293)
-- Name: stamp_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY stamp_category
    ADD CONSTRAINT stamp_id_fk FOREIGN KEY (stamp_id) REFERENCES stamp(id);


--
-- TOC entry 1920 (class 2606 OID 29298)
-- Name: stamp_user_fk_stamp; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY user_stamp
    ADD CONSTRAINT stamp_user_fk_stamp FOREIGN KEY (stamp_id) REFERENCES stamp(id);


--
-- TOC entry 2051 (class 0 OID 0)
-- Dependencies: 6
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2015-07-02 18:29:18

--
-- PostgreSQL database dump complete
--

