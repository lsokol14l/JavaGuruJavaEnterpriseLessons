--
-- PostgreSQL database dump
--

\restrict Nught2M4Pwu9tmUffY9Ta55thE22m1Mzd70fWmYnOV5rVtZFOpykBD11u3bp0FX

-- Dumped from database version 18.1
-- Dumped by pg_dump version 18.1

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

DROP DATABASE IF EXISTS flight_repo_test;
--
-- Name: flight_repo; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE flight_repo_test WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Russian_Russia.1251';


ALTER DATABASE flight_repo_test OWNER TO postgres;

\unrestrict Nught2M4Pwu9tmUffY9Ta55thE22m1Mzd70fWmYnOV5rVtZFOpykBD11u3bp0FX
\connect flight_repo_test
\restrict Nught2M4Pwu9tmUffY9Ta55thE22m1Mzd70fWmYnOV5rVtZFOpykBD11u3bp0FX

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: public; Type: SCHEMA; Schema: -; Owner: postgres
--

-- *not* creating schema, since initdb creates it


ALTER SCHEMA public OWNER TO postgres;

--
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON SCHEMA public IS '';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: aircraft; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.aircraft
(
    id    integer                NOT NULL,
    model character varying(128) NOT NULL
);


ALTER TABLE public.aircraft
    OWNER TO postgres;

--
-- Name: aircraft_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.aircraft_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.aircraft_id_seq OWNER TO postgres;

--
-- Name: aircraft_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.aircraft_id_seq OWNED BY public.aircraft.id;


--
-- Name: airport; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.airport
(
    code    character(3)           NOT NULL,
    country character varying(128) NOT NULL,
    city    character varying(128) NOT NULL
);


ALTER TABLE public.airport
    OWNER TO postgres;

--
-- Name: flight; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.flight
(
    id                     bigint                      NOT NULL,
    flight_no              character varying(16)       NOT NULL,
    departure_date         timestamp without time zone NOT NULL,
    departure_airport_code character(3)                NOT NULL,
    arrival_date           timestamp without time zone NOT NULL,
    arrival_airport_code   character(3)                NOT NULL,
    aircraft_id            integer                     NOT NULL,
    status                 character varying(32)       NOT NULL
);


ALTER TABLE public.flight
    OWNER TO postgres;

--
-- Name: flight_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.flight_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.flight_id_seq OWNER TO postgres;

--
-- Name: flight_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.flight_id_seq OWNED BY public.flight.id;


--
-- Name: seat; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.seat
(
    aircraft_id integer      NOT NULL,
    seat_no     character(4) NOT NULL
);


ALTER TABLE public.seat
    OWNER TO postgres;

--
-- Name: ticket; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ticket
(
    id             bigint                 NOT NULL,
    passport_no    character varying(64)  NOT NULL,
    passenger_name character varying(256) NOT NULL,
    flight_id      integer                NOT NULL,
    seat_no        character(4)           NOT NULL,
    cost           numeric                NOT NULL
);


ALTER TABLE public.ticket
    OWNER TO postgres;

--
-- Name: ticket_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.ticket_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.ticket_id_seq OWNER TO postgres;

--
-- Name: ticket_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.ticket_id_seq OWNED BY public.ticket.id;


--
-- Name: aircraft id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.aircraft
    ALTER COLUMN id SET DEFAULT nextval('public.aircraft_id_seq'::regclass);


--
-- Name: flight id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.flight
    ALTER COLUMN id SET DEFAULT nextval('public.flight_id_seq'::regclass);


--
-- Name: ticket id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ticket
    ALTER COLUMN id SET DEFAULT nextval('public.ticket_id_seq'::regclass);


--
-- Data for Name: aircraft; Type: TABLE DATA; Schema: public; Owner: postgres
--
INSERT INTO public.aircraft (id, model)
VALUES (1, 'Боинг 777-300');
INSERT INTO public.aircraft (id, model)
VALUES (2, 'Боинг 737-300');
INSERT INTO public.aircraft (id, model)
VALUES (3, 'Аэробус A320-200');
INSERT INTO public.aircraft (id, model)
VALUES (4, 'Суперджет-100');


--
-- Data for Name: airport; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.airport (code, country, city)
VALUES ('MNK', 'Беларусь', 'Минск');
INSERT INTO public.airport (code, country, city)
VALUES ('LDN', 'Англия', 'Лондон');
INSERT INTO public.airport (code, country, city)
VALUES ('MSK', 'Россия', 'Москва');
INSERT INTO public.airport (code, country, city)
VALUES ('BSL', 'Испания', 'Барселона');


--
-- Data for Name: flight; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.flight (id, flight_no, departure_date, departure_airport_code, arrival_date, arrival_airport_code,
                           aircraft_id, status)
VALUES (1, 'MN3002', '2020-06-14 14:30:00', 'MNK', '2020-06-14 18:07:00', 'LDN', 1, 'ARRIVED');
INSERT INTO public.flight (id, flight_no, departure_date, departure_airport_code, arrival_date, arrival_airport_code,
                           aircraft_id, status)
VALUES (2, 'MN3002', '2020-06-16 09:15:00', 'LDN', '2020-06-16 13:00:00', 'MNK', 1, 'ARRIVED');
INSERT INTO public.flight (id, flight_no, departure_date, departure_airport_code, arrival_date, arrival_airport_code,
                           aircraft_id, status)
VALUES (3, 'BC2801', '2020-07-28 23:25:00', 'MNK', '2020-07-29 02:43:00', 'LDN', 2, 'ARRIVED');
INSERT INTO public.flight (id, flight_no, departure_date, departure_airport_code, arrival_date, arrival_airport_code,
                           aircraft_id, status)
VALUES (4, 'BC2801', '2020-08-01 11:00:00', 'LDN', '2020-08-01 14:15:00', 'MNK', 2, 'DEPARTED');
INSERT INTO public.flight (id, flight_no, departure_date, departure_airport_code, arrival_date, arrival_airport_code,
                           aircraft_id, status)
VALUES (5, 'TR3103', '2020-05-03 13:10:00', 'MSK', '2020-05-03 18:38:00', 'BSL', 3, 'ARRIVED');
INSERT INTO public.flight (id, flight_no, departure_date, departure_airport_code, arrival_date, arrival_airport_code,
                           aircraft_id, status)
VALUES (6, 'TR3103', '2020-05-10 07:15:00', 'BSL', '2020-05-10 12:44:00', 'MSK', 3, 'CANCELLED');
INSERT INTO public.flight (id, flight_no, departure_date, departure_airport_code, arrival_date, arrival_airport_code,
                           aircraft_id, status)
VALUES (7, 'CV9827', '2020-09-09 18:00:00', 'MNK', '2020-09-09 19:15:00', 'MSK', 4, 'SCHEDULED');
INSERT INTO public.flight (id, flight_no, departure_date, departure_airport_code, arrival_date, arrival_airport_code,
                           aircraft_id, status)
VALUES (8, 'CV9827', '2020-09-19 08:55:00', 'MSK', '2020-09-19 10:05:00', 'MNK', 4, 'SCHEDULED');
INSERT INTO public.flight (id, flight_no, departure_date, departure_airport_code, arrival_date, arrival_airport_code,
                           aircraft_id, status)
VALUES (9, 'QS8712', '2020-12-18 03:35:00', 'MNK', '2020-12-18 06:46:00', 'LDN', 2, 'ARRIVED');
INSERT INTO public.flight (id, flight_no, departure_date, departure_airport_code, arrival_date, arrival_airport_code,
                           aircraft_id, status)
VALUES (10, 'QS8712', '2020-12-18 03:35:00', 'MNK', '2020-12-18 06:46:00', 'LDN', 2, 'ARRIVED');
INSERT INTO public.flight (id, flight_no, departure_date, departure_airport_code, arrival_date, arrival_airport_code,
                           aircraft_id, status)
VALUES (11, 'QS8712', '2020-12-18 03:35:00', 'MNK', '2020-12-18 06:46:00', 'LDN', 2, 'ARRIVED');


--
-- Data for Name: seat; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.seat (aircraft_id, seat_no)
VALUES (1, 'A1  ');
INSERT INTO public.seat (aircraft_id, seat_no)
VALUES (1, 'A2  ');
INSERT INTO public.seat (aircraft_id, seat_no)
VALUES (1, 'B1  ');
INSERT INTO public.seat (aircraft_id, seat_no)
VALUES (1, 'B2  ');
INSERT INTO public.seat (aircraft_id, seat_no)
VALUES (1, 'C1  ');
INSERT INTO public.seat (aircraft_id, seat_no)
VALUES (1, 'C2  ');
INSERT INTO public.seat (aircraft_id, seat_no)
VALUES (1, 'D1  ');
INSERT INTO public.seat (aircraft_id, seat_no)
VALUES (1, 'D2  ');
INSERT INTO public.seat (aircraft_id, seat_no)
VALUES (2, 'A1  ');
INSERT INTO public.seat (aircraft_id, seat_no)
VALUES (2, 'A2  ');
INSERT INTO public.seat (aircraft_id, seat_no)
VALUES (2, 'B1  ');
INSERT INTO public.seat (aircraft_id, seat_no)
VALUES (2, 'B2  ');
INSERT INTO public.seat (aircraft_id, seat_no)
VALUES (2, 'C1  ');
INSERT INTO public.seat (aircraft_id, seat_no)
VALUES (2, 'C2  ');
INSERT INTO public.seat (aircraft_id, seat_no)
VALUES (2, 'D1  ');
INSERT INTO public.seat (aircraft_id, seat_no)
VALUES (2, 'D2  ');
INSERT INTO public.seat (aircraft_id, seat_no)
VALUES (3, 'A1  ');
INSERT INTO public.seat (aircraft_id, seat_no)
VALUES (3, 'A2  ');
INSERT INTO public.seat (aircraft_id, seat_no)
VALUES (3, 'B1  ');
INSERT INTO public.seat (aircraft_id, seat_no)
VALUES (3, 'B2  ');
INSERT INTO public.seat (aircraft_id, seat_no)
VALUES (3, 'C1  ');
INSERT INTO public.seat (aircraft_id, seat_no)
VALUES (3, 'C2  ');
INSERT INTO public.seat (aircraft_id, seat_no)
VALUES (3, 'D1  ');
INSERT INTO public.seat (aircraft_id, seat_no)
VALUES (3, 'D2  ');
INSERT INTO public.seat (aircraft_id, seat_no)
VALUES (4, 'A1  ');
INSERT INTO public.seat (aircraft_id, seat_no)
VALUES (4, 'A2  ');
INSERT INTO public.seat (aircraft_id, seat_no)
VALUES (4, 'B1  ');
INSERT INTO public.seat (aircraft_id, seat_no)
VALUES (4, 'B2  ');
INSERT INTO public.seat (aircraft_id, seat_no)
VALUES (4, 'C1  ');
INSERT INTO public.seat (aircraft_id, seat_no)
VALUES (4, 'C2  ');
INSERT INTO public.seat (aircraft_id, seat_no)
VALUES (4, 'D1  ');
INSERT INTO public.seat (aircraft_id, seat_no)
VALUES (4, 'D2  ');


--
-- Data for Name: ticket; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.ticket (id, passport_no, passenger_name, flight_id, seat_no, cost)
VALUES (1, '112233', 'Иван Иванов', 1, 'A1  ', 200);
INSERT INTO public.ticket (id, passport_no, passenger_name, flight_id, seat_no, cost)
VALUES (2, '23234A', 'Петр Петров', 1, 'B1  ', 180);
INSERT INTO public.ticket (id, passport_no, passenger_name, flight_id, seat_no, cost)
VALUES (3, 'SS988D', 'Светлана Светикова', 1, 'B2  ', 175);
INSERT INTO public.ticket (id, passport_no, passenger_name, flight_id, seat_no, cost)
VALUES (4, 'QYASDE', 'Андрей Андреев', 1, 'C2  ', 175);
INSERT INTO public.ticket (id, passport_no, passenger_name, flight_id, seat_no, cost)
VALUES (5, 'POQ234', 'Иван Кожемякин', 1, 'D1  ', 160);
INSERT INTO public.ticket (id, passport_no, passenger_name, flight_id, seat_no, cost)
VALUES (6, '898123', 'Олег Рубцов', 1, 'A2  ', 198);
INSERT INTO public.ticket (id, passport_no, passenger_name, flight_id, seat_no, cost)
VALUES (7, '555321', 'Екатерина Петренко', 2, 'A1  ', 250);
INSERT INTO public.ticket (id, passport_no, passenger_name, flight_id, seat_no, cost)
VALUES (8, 'QO23OO', 'Иван Розмаринов', 2, 'B2  ', 225);
INSERT INTO public.ticket (id, passport_no, passenger_name, flight_id, seat_no, cost)
VALUES (9, '123UI2', 'Андрей Буйнов', 2, 'C2  ', 227);
INSERT INTO public.ticket (id, passport_no, passenger_name, flight_id, seat_no, cost)
VALUES (10, 'SS988D', 'Светлана Светикова', 2, 'D2  ', 277);
INSERT INTO public.ticket (id, passport_no, passenger_name, flight_id, seat_no, cost)
VALUES (11, 'EE2344', 'Дмитрий Трусцов', 3, 'А1  ', 300);
INSERT INTO public.ticket (id, passport_no, passenger_name, flight_id, seat_no, cost)
VALUES (12, 'AS23PP', 'Максим Комсомольцев', 3, 'А2  ', 285);
INSERT INTO public.ticket (id, passport_no, passenger_name, flight_id, seat_no, cost)
VALUES (13, '322349', 'Эдуард Щеглов', 3, 'B1  ', 99);
INSERT INTO public.ticket (id, passport_no, passenger_name, flight_id, seat_no, cost)
VALUES (14, 'DL123S', 'Игорь Беркутов', 3, 'B2  ', 199);
INSERT INTO public.ticket (id, passport_no, passenger_name, flight_id, seat_no, cost)
VALUES (15, 'MVM111', 'Алексей Щербин', 3, 'C1  ', 299);
INSERT INTO public.ticket (id, passport_no, passenger_name, flight_id, seat_no, cost)
VALUES (16, 'ZZZ111', 'Денис Колобков', 3, 'C2  ', 230);
INSERT INTO public.ticket (id, passport_no, passenger_name, flight_id, seat_no, cost)
VALUES (17, '234444', 'Иван Старовойтов', 3, 'D1  ', 180);
INSERT INTO public.ticket (id, passport_no, passenger_name, flight_id, seat_no, cost)
VALUES (18, 'LLLL12', 'Людмила Старовойтова', 3, 'D2  ', 224);
INSERT INTO public.ticket (id, passport_no, passenger_name, flight_id, seat_no, cost)
VALUES (19, 'RT34TR', 'Степан Дор', 4, 'A1  ', 129);
INSERT INTO public.ticket (id, passport_no, passenger_name, flight_id, seat_no, cost)
VALUES (20, '999666', 'Анастасия Шепелева', 4, 'A2  ', 152);
INSERT INTO public.ticket (id, passport_no, passenger_name, flight_id, seat_no, cost)
VALUES (21, '234444', 'Иван Старовойтов', 4, 'B1  ', 140);
INSERT INTO public.ticket (id, passport_no, passenger_name, flight_id, seat_no, cost)
VALUES (22, 'LLLL12', 'Людмила Старовойтова', 4, 'B2  ', 140);
INSERT INTO public.ticket (id, passport_no, passenger_name, flight_id, seat_no, cost)
VALUES (23, 'LLLL12', 'Роман Дронов', 4, 'D2  ', 109);
INSERT INTO public.ticket (id, passport_no, passenger_name, flight_id, seat_no, cost)
VALUES (24, '112233', 'Иван Иванов', 5, 'С2  ', 170);
INSERT INTO public.ticket (id, passport_no, passenger_name, flight_id, seat_no, cost)
VALUES (25, 'NMNBV2', 'Лариса Тельникова', 5, 'С1  ', 185);
INSERT INTO public.ticket (id, passport_no, passenger_name, flight_id, seat_no, cost)
VALUES (26, 'DSA586', 'Лариса Привольная', 5, 'A1  ', 204);
INSERT INTO public.ticket (id, passport_no, passenger_name, flight_id, seat_no, cost)
VALUES (27, 'DSA583', 'Артур Мирный', 5, 'B1  ', 189);
INSERT INTO public.ticket (id, passport_no, passenger_name, flight_id, seat_no, cost)
VALUES (28, 'DSA581', 'Евгений Кудрявцев', 6, 'A1  ', 204);
INSERT INTO public.ticket (id, passport_no, passenger_name, flight_id, seat_no, cost)
VALUES (29, 'EE2344', 'Дмитрий Трусцов', 6, 'A2  ', 214);
INSERT INTO public.ticket (id, passport_no, passenger_name, flight_id, seat_no, cost)
VALUES (30, 'AS23PP', 'Максим Комсомольцев', 6, 'B2  ', 176);
INSERT INTO public.ticket (id, passport_no, passenger_name, flight_id, seat_no, cost)
VALUES (31, '112233', 'Иван Иванов', 6, 'B1  ', 135);
INSERT INTO public.ticket (id, passport_no, passenger_name, flight_id, seat_no, cost)
VALUES (32, '309623', 'Татьяна Крот', 6, 'С1  ', 155);
INSERT INTO public.ticket (id, passport_no, passenger_name, flight_id, seat_no, cost)
VALUES (33, '319623', 'Юрий Дувинков', 6, 'D1  ', 125);
INSERT INTO public.ticket (id, passport_no, passenger_name, flight_id, seat_no, cost)
VALUES (34, '322349', 'Эдуард Щеглов', 7, 'A1  ', 69);
INSERT INTO public.ticket (id, passport_no, passenger_name, flight_id, seat_no, cost)
VALUES (35, 'DIOPSL', 'Евгений Безфамильная', 7, 'A2  ', 58);
INSERT INTO public.ticket (id, passport_no, passenger_name, flight_id, seat_no, cost)
VALUES (36, 'DIOPS1', 'Константин Швец', 7, 'D1  ', 65);
INSERT INTO public.ticket (id, passport_no, passenger_name, flight_id, seat_no, cost)
VALUES (37, 'DIOPS2', 'Юлия Швец', 7, 'D2  ', 65);
INSERT INTO public.ticket (id, passport_no, passenger_name, flight_id, seat_no, cost)
VALUES (38, '1IOPS2', 'Ник Говриленко', 7, 'C2  ', 73);
INSERT INTO public.ticket (id, passport_no, passenger_name, flight_id, seat_no, cost)
VALUES (39, '999666', 'Анастасия Шепелева', 7, 'B1  ', 66);
INSERT INTO public.ticket (id, passport_no, passenger_name, flight_id, seat_no, cost)
VALUES (40, '23234A', 'Петр Петров', 7, 'C1  ', 80);
INSERT INTO public.ticket (id, passport_no, passenger_name, flight_id, seat_no, cost)
VALUES (41, 'QYASDE', 'Андрей Андреев', 8, 'A1  ', 100);
INSERT INTO public.ticket (id, passport_no, passenger_name, flight_id, seat_no, cost)
VALUES (42, '1QAZD2', 'Лариса Потемнкина', 8, 'A2  ', 89);
INSERT INTO public.ticket (id, passport_no, passenger_name, flight_id, seat_no, cost)
VALUES (43, '5QAZD2', 'Карл Хмелев', 8, 'B2  ', 79);
INSERT INTO public.ticket (id, passport_no, passenger_name, flight_id, seat_no, cost)
VALUES (44, '2QAZD2', 'Жанна Хмелева', 8, 'С2  ', 77);
INSERT INTO public.ticket (id, passport_no, passenger_name, flight_id, seat_no, cost)
VALUES (45, 'BMXND1', 'Светлана Хмурая', 8, 'В2  ', 94);
INSERT INTO public.ticket (id, passport_no, passenger_name, flight_id, seat_no, cost)
VALUES (46, 'BMXND2', 'Кирилл Сарычев', 8, 'D1  ', 81);
INSERT INTO public.ticket (id, passport_no, passenger_name, flight_id, seat_no, cost)
VALUES (47, 'SS988D', 'Светлана Светикова', 9, 'A2  ', 222);
INSERT INTO public.ticket (id, passport_no, passenger_name, flight_id, seat_no, cost)
VALUES (48, 'SS978D', 'Андрей Желудь', 9, 'A1  ', 198);
INSERT INTO public.ticket (id, passport_no, passenger_name, flight_id, seat_no, cost)
VALUES (49, 'SS968D', 'Дмитрий Воснецов', 9, 'B1  ', 243);
INSERT INTO public.ticket (id, passport_no, passenger_name, flight_id, seat_no, cost)
VALUES (50, 'SS958D', 'Максим Гребцов', 9, 'С1  ', 251);
INSERT INTO public.ticket (id, passport_no, passenger_name, flight_id, seat_no, cost)
VALUES (51, '112233', 'Иван Иванов', 9, 'С2  ', 135);
INSERT INTO public.ticket (id, passport_no, passenger_name, flight_id, seat_no, cost)
VALUES (52, 'NMNBV2', 'Лариса Тельникова', 9, 'B2  ', 217);
INSERT INTO public.ticket (id, passport_no, passenger_name, flight_id, seat_no, cost)
VALUES (53, '23234A', 'Петр Петров', 9, 'D1  ', 189);
INSERT INTO public.ticket (id, passport_no, passenger_name, flight_id, seat_no, cost)
VALUES (54, '123951', 'Полина Зверева', 9, 'D2  ', 234);
INSERT INTO public.ticket (id, passport_no, passenger_name, flight_id, seat_no, cost)
VALUES (55, '9883IO', 'Иван Кожемякин', 2, 'C1  ', 217);


--
-- Name: aircraft_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.aircraft_id_seq', 4, true);


--
-- Name: flight_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.flight_id_seq', 11, true);


--
-- Name: ticket_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.ticket_id_seq', 110, true);


--
-- Name: aircraft aircraft_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.aircraft
    ADD CONSTRAINT aircraft_pkey PRIMARY KEY (id);


--
-- Name: airport airport_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.airport
    ADD CONSTRAINT airport_pkey PRIMARY KEY (code);


--
-- Name: flight flight_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.flight
    ADD CONSTRAINT flight_pkey PRIMARY KEY (id);


--
-- Name: seat seat_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.seat
    ADD CONSTRAINT seat_pkey PRIMARY KEY (aircraft_id, seat_no);


--
-- Name: ticket ticket_flight_id_seat_no_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ticket
    ADD CONSTRAINT ticket_flight_id_seat_no_key UNIQUE (flight_id, seat_no);


--
-- Name: ticket ticket_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ticket
    ADD CONSTRAINT ticket_pkey PRIMARY KEY (id);


--
-- Name: flight flight_aircraft_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.flight
    ADD CONSTRAINT flight_aircraft_id_fkey FOREIGN KEY (aircraft_id) REFERENCES public.aircraft (id);


--
-- Name: flight flight_arrival_airport_code_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.flight
    ADD CONSTRAINT flight_arrival_airport_code_fkey FOREIGN KEY (arrival_airport_code) REFERENCES public.airport (code);


--
-- Name: flight flight_departure_airport_code_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.flight
    ADD CONSTRAINT flight_departure_airport_code_fkey FOREIGN KEY (departure_airport_code) REFERENCES public.airport (code);


--
-- Name: seat seat_aircraft_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.seat
    ADD CONSTRAINT seat_aircraft_id_fkey FOREIGN KEY (aircraft_id) REFERENCES public.aircraft (id);


--
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE USAGE ON SCHEMA public FROM PUBLIC;


--
-- PostgreSQL database dump complete
--

\unrestrict Nught2M4Pwu9tmUffY9Ta55thE22m1Mzd70fWmYnOV5rVtZFOpykBD11u3bp0FX