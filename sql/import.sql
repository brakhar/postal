--
-- PostgreSQL database dump
--

-- Dumped from database version 9.3.6
-- Dumped by pg_dump version 9.3.6
-- Started on 2015-03-29 00:15:54 EET

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 177 (class 1259 OID 32859)
-- Name: stamp_type; Type: TABLE; Schema: public; Owner: admin; Tablespace: 
--

CREATE TABLE stamp_type (
    id integer NOT NULL,
    name character varying(255)
);


ALTER TABLE public.stamp_type OWNER TO admin;

--
-- TOC entry 1989 (class 0 OID 32859)
-- Dependencies: 177
-- Data for Name: stamp_type; Type: TABLE DATA; Schema: public; Owner: admin
--



--
-- TOC entry 1881 (class 2606 OID 32863)
-- Name: stamp_type_pkey; Type: CONSTRAINT; Schema: public; Owner: admin; Tablespace: 
--

ALTER TABLE ONLY stamp_type
    ADD CONSTRAINT stamp_type_pkey PRIMARY KEY (id);


-- Completed on 2015-03-29 00:15:54 EET

--
-- PostgreSQL database dump complete
--

