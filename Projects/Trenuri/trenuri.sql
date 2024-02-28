--
-- PostgreSQL database dump
--

-- Dumped from database version 16.1 (Ubuntu 16.1-1.pgdg22.04+1)
-- Dumped by pg_dump version 16.1 (Ubuntu 16.1-1.pgdg22.04+1)

-- Started on 2024-01-23 11:53:05 EET

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 215 (class 1259 OID 16833)
-- Name: cities; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.cities (
    cid character varying NOT NULL,
    name character varying NOT NULL
);


ALTER TABLE public.cities OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 16958)
-- Name: tickets; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tickets (
    trainid character varying NOT NULL,
    departurecityid character varying NOT NULL,
    date date NOT NULL
);


ALTER TABLE public.tickets OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 16841)
-- Name: train_stations; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.train_stations (
    tid integer NOT NULL,
    "trainId" character varying NOT NULL,
    "departureCityId" character varying NOT NULL,
    "destinationCityId" character varying NOT NULL
);


ALTER TABLE public.train_stations OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 16840)
-- Name: train_stations_tid_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.train_stations ALTER COLUMN tid ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.train_stations_tid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 218 (class 1259 OID 16941)
-- Name: trains; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.trains (
    trainid character varying NOT NULL
);


ALTER TABLE public.trains OWNER TO postgres;

--
-- TOC entry 3510 (class 0 OID 16833)
-- Dependencies: 215
-- Data for Name: cities; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.cities (cid, name) FROM stdin;
C1	Cluj-Napoca
C2	Apahida
C3	Campia Turzii
C4	Razboieni
C5	Gilau
\.


--
-- TOC entry 3514 (class 0 OID 16958)
-- Dependencies: 219
-- Data for Name: tickets; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tickets (trainid, departurecityid, date) FROM stdin;
T1	C1	2024-01-03
T1	C1	2024-01-20
T1	C1	2024-01-20
T5	C1	2024-01-20
T5	C1	2024-01-20
T1	C1	2024-01-03
T1	C1	2024-01-20
T1	C1	2024-01-03
T1	C1	2024-01-03
T1	C1	2024-01-20
T1	C1	2024-01-20
T1	C1	2024-01-20
T1	C1	2024-01-20
T1	C1	2024-01-23
T1	C1	2024-01-03
T1	C1	2024-01-03
T1	C1	2024-01-20
T1	C1	2024-01-20
T5	C1	2024-01-20
T5	C1	2024-01-20
T5	C1	2024-01-20
T5	C1	2024-01-20
T5	C1	2024-01-20
T5	C1	2024-01-20
T5	C1	2024-01-20
T5	C1	2024-01-20
T5	C1	2024-01-20
T1	C1	2024-01-20
T1	C1	2024-01-20
T1	C1	2024-01-20
T1	C1	2024-01-20
T1	C1	2024-01-02
T1	C1	2024-01-02
T1	C1	2024-01-02
T5	C1	2024-01-02
T4	C2	2024-01-10
T2	C2	2024-01-10
T2	C2	2024-01-10
T2	C2	2024-01-10
T1	C1	2024-01-20
T1	C1	2024-01-20
T1	C1	2024-01-20
T2	C2	2024-01-20
T2	C2	2024-01-20
T2	C2	2024-01-20
T2	C2	2024-01-20
T2	C2	2024-01-20
T2	C2	2024-01-27
T2	C2	2024-01-27
T2	C2	2024-01-27
T2	C2	2024-01-27
T2	C2	2024-01-27
T2	C2	2024-01-27
T2	C2	2024-01-27
T2	C2	2024-01-27
T2	C2	2024-01-27
T2	C2	2024-01-27
T2	C2	2024-01-27
T2	C2	2024-01-27
T2	C2	2024-01-27
T2	C2	2024-01-27
T2	C2	2024-01-27
T2	C2	2024-01-27
T2	C2	2024-01-27
\.


--
-- TOC entry 3512 (class 0 OID 16841)
-- Dependencies: 217
-- Data for Name: train_stations; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.train_stations (tid, "trainId", "departureCityId", "destinationCityId") FROM stdin;
1	T1	C1	C2
2	T1	C2	C3
3	T2	C2	C3
4	T2	C3	C4
5	T3	C3	C4
6	T4	C2	C4
7	T5	C1	C5
8	T5	C5	C3
\.


--
-- TOC entry 3513 (class 0 OID 16941)
-- Dependencies: 218
-- Data for Name: trains; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.trains (trainid) FROM stdin;
T1
T2
T3
T4
T5
\.


--
-- TOC entry 3520 (class 0 OID 0)
-- Dependencies: 216
-- Name: train_stations_tid_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.train_stations_tid_seq', 8, true);


--
-- TOC entry 3357 (class 2606 OID 16839)
-- Name: cities cities_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cities
    ADD CONSTRAINT cities_pkey PRIMARY KEY (cid);


--
-- TOC entry 3359 (class 2606 OID 16847)
-- Name: train_stations train_stations_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.train_stations
    ADD CONSTRAINT train_stations_pkey PRIMARY KEY (tid);


--
-- TOC entry 3361 (class 2606 OID 16947)
-- Name: trains trains_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.trains
    ADD CONSTRAINT trains_pk PRIMARY KEY (trainid);


--
-- TOC entry 3362 (class 2606 OID 16848)
-- Name: train_stations fk_DepartureCity; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.train_stations
    ADD CONSTRAINT "fk_DepartureCity" FOREIGN KEY ("departureCityId") REFERENCES public.cities(cid);


--
-- TOC entry 3363 (class 2606 OID 16853)
-- Name: train_stations fk_DestinationCity; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.train_stations
    ADD CONSTRAINT "fk_DestinationCity" FOREIGN KEY ("destinationCityId") REFERENCES public.cities(cid);


--
-- TOC entry 3365 (class 2606 OID 16968)
-- Name: tickets ticket_cities_cid_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tickets
    ADD CONSTRAINT ticket_cities_cid_fk FOREIGN KEY (departurecityid) REFERENCES public.cities(cid);


--
-- TOC entry 3366 (class 2606 OID 16963)
-- Name: tickets ticket_trains_trainid_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tickets
    ADD CONSTRAINT ticket_trains_trainid_fk FOREIGN KEY (trainid) REFERENCES public.trains(trainid);


--
-- TOC entry 3364 (class 2606 OID 16953)
-- Name: train_stations train_stations_trains_trainid_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.train_stations
    ADD CONSTRAINT train_stations_trains_trainid_fk FOREIGN KEY ("trainId") REFERENCES public.trains(trainid);


-- Completed on 2024-01-23 11:53:05 EET

--
-- PostgreSQL database dump complete
--

