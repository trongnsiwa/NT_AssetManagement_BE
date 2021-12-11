-- public.asset_states definition

-- Drop table

-- DROP TABLE public.asset_states;

CREATE TABLE public.asset_states (
                                     id int8 NOT NULL,
                                     "name" varchar(255) NULL,
                                     CONSTRAINT asset_states_pkey PRIMARY KEY (id)
);


-- public.assignment_state definition

-- Drop table

-- DROP TABLE public.assignment_state;

CREATE TABLE public.assignment_state (
                                         id int8 NOT NULL,
                                         "name" varchar(255) NULL,
                                         CONSTRAINT assignment_state_pkey PRIMARY KEY (id)
);


-- public.categories definition

-- Drop table

-- DROP TABLE public.categories;

CREATE TABLE public.categories (
                                   id bigserial NOT NULL,
                                   "name" varchar(255) NOT NULL,
                                   prefix varchar(255) NOT NULL,
                                   CONSTRAINT categories_pkey PRIMARY KEY (id),
                                   CONSTRAINT uk_lob8kriveow2e9uyavt9vwa0d UNIQUE (prefix),
                                   CONSTRAINT uk_t8o6pivur7nn124jehx7cygw5 UNIQUE (name)
);
CREATE INDEX category_idx ON public.categories USING btree (id, name);


-- public.locations definition

-- Drop table

-- DROP TABLE public.locations;

CREATE TABLE public.locations (
                                  id int8 NOT NULL,
                                  "name" varchar(255) NULL,
                                  CONSTRAINT locations_pkey PRIMARY KEY (id)
);


-- public.roles definition

-- Drop table

-- DROP TABLE public.roles;

CREATE TABLE public.roles (
                              id int8 NOT NULL,
                              "name" varchar(50) NULL,
                              CONSTRAINT roles_pkey PRIMARY KEY (id)
);
CREATE INDEX type_idx ON public.roles USING btree (id, name);


-- public.staffcode_generator definition

-- DROP SEQUENCE public.staffcode_generator;

CREATE SEQUENCE public.staffcode_generator
    INCREMENT BY 50
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 100
    CACHE 1
    NO CYCLE;


-- public.users definition

-- Drop table

-- DROP TABLE public.users;

CREATE TABLE public.users (
                              code varchar(255) NOT NULL,
                              created_date timestamp NULL,
                              date_of_birth timestamp NULL,
                              first_name varchar(20) NULL,
                              gender bool NULL,
                              is_deleted bool NULL,
                              is_new bool NULL,
                              joined_date timestamp NULL,
                              last_name varchar(20) NULL,
                              "password" varchar(255) NULL,
                              updated_date timestamp NULL,
                              username varchar(255) NULL,
                              location_id int8 NULL,
                              role_id int8 NULL,
                              CONSTRAINT ukr43af9ap4edm43mmtq01oddj6 UNIQUE (username),
                              CONSTRAINT users_pkey PRIMARY KEY (code),
                              CONSTRAINT fkdk0xfnnthbj8afp1ira6sndte FOREIGN KEY (location_id) REFERENCES public.locations(id),
                              CONSTRAINT fkp56c1712k691lhsyewcssf40f FOREIGN KEY (role_id) REFERENCES public.roles(id)
);
CREATE INDEX idindex ON public.users USING btree (code, username);


-- public.assets definition

-- Drop table

-- DROP TABLE public.assets;

CREATE TABLE public.assets (
                               id int8 NOT NULL,
                               asset_code varchar(255) NULL,
                               created_date timestamp NULL,
                               installed_date timestamp NULL,
                               is_deleted bool NULL,
                               "name" varchar(255) NULL,
                               specification varchar(255) NULL,
                               updated_date timestamp NULL,
                               category_id int8 NULL,
                               location_id int8 NULL,
                               managed_by varchar(255) NULL,
                               state int8 NULL,
                               CONSTRAINT assets_pkey PRIMARY KEY (id),
                               CONSTRAINT ukh3rqbypxh7aycu4jdf3sisunv UNIQUE (asset_code),
                               CONSTRAINT fk1euaiqs9okaont1f67e2cg0be FOREIGN KEY (state) REFERENCES public.asset_states(id),
                               CONSTRAINT fkcvyf8pxl6m3wb2bjda2roip1f FOREIGN KEY (category_id) REFERENCES public.categories(id),
                               CONSTRAINT fkhuexoh5k5nfvvmw9ko38oi0bl FOREIGN KEY (location_id) REFERENCES public.locations(id),
                               CONSTRAINT fkkd5wpn4f5iymi7l89lmxi8bbk FOREIGN KEY (managed_by) REFERENCES public.users(code)
);


-- public.assignments definition

-- Drop table

-- DROP TABLE public.assignments;

CREATE TABLE public.assignments (
                                    id int8 NOT NULL,
                                    assigned_date timestamp NULL,
                                    is_deleted bool NULL,
                                    note varchar(255) NULL,
                                    updated_date timestamp NULL,
                                    asset_id int8 NULL,
                                    assigned_by varchar(255) NULL,
                                    assigned_to varchar(255) NULL,
                                    state int8 NULL,
                                    CONSTRAINT assignments_pkey PRIMARY KEY (id),
                                    CONSTRAINT fk3wm4vepyceb348quud9y1bx7g FOREIGN KEY (assigned_to) REFERENCES public.users(code),
                                    CONSTRAINT fkiep3tb021q3m4qhltfh2b6fg8 FOREIGN KEY (asset_id) REFERENCES public.assets(id),
                                    CONSTRAINT fkj0gs6a7smae26v1tpiry07hnv FOREIGN KEY (assigned_by) REFERENCES public.users(code),
                                    CONSTRAINT fkkh71dmfixdnnfp3l18am3eue3 FOREIGN KEY (state) REFERENCES public.assignment_state(id)
);


-- public.historical_assignments definition

-- Drop table

-- DROP TABLE public.historical_assignments;

CREATE TABLE public.historical_assignments (
                                               id int8 NOT NULL,
                                               assigned_date timestamp NULL,
                                               returned_date timestamp NULL,
                                               asset_id int8 NULL,
                                               assigned_by varchar(255) NULL,
                                               assigned_to varchar(255) NULL,
                                               CONSTRAINT historical_assignments_pkey PRIMARY KEY (id),
                                               CONSTRAINT fk4el01dvr8yf22ebomdovk48bn FOREIGN KEY (assigned_to) REFERENCES public.users(code),
                                               CONSTRAINT fkbiexjo7xybd5t7tsl376h5iis FOREIGN KEY (asset_id) REFERENCES public.assets(id),
                                               CONSTRAINT fkr0a4lnn5hv0uvkjp1fv15jkpy FOREIGN KEY (assigned_by) REFERENCES public.users(code)
);


-- public.return_request definition

-- Drop table

-- DROP TABLE public.return_request;

CREATE TABLE public.return_request (
                                       id int8 NOT NULL,
                                       assigned_date timestamp NULL,
                                       created_date timestamp NULL,
                                       is_deleted bool NULL,
                                       returned_date timestamp NULL,
                                       state bool NULL,
                                       updated_date timestamp NULL,
                                       accept_by varchar(255) NULL,
                                       asset_id int8 NULL,
                                       assignment_id int8 NULL,
                                       request_by varchar(255) NULL,
                                       CONSTRAINT return_request_pkey PRIMARY KEY (id),
                                       CONSTRAINT fk5ju24cykv1dy1qtpi04bhliiy FOREIGN KEY (accept_by) REFERENCES public.users(code),
                                       CONSTRAINT fk7n7l9kkg0652ct8cfwfq7mgl2 FOREIGN KEY (assignment_id) REFERENCES public.assignments(id),
                                       CONSTRAINT fkchhfnqc2bj5yqdtvyk365kcxk FOREIGN KEY (asset_id) REFERENCES public.assets(id),
                                       CONSTRAINT fkswnsccii9m3ejmvhb1jfnfn8a FOREIGN KEY (request_by) REFERENCES public.users(code)
);

-- public.hibernate_sequence definition

-- DROP SEQUENCE public.hibernate_sequence;

CREATE SEQUENCE public.hibernate_sequence
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 79
    CACHE 1
    NO CYCLE;


-- public.location_sequence definition

-- DROP SEQUENCE public.location_sequence;

CREATE SEQUENCE public.location_sequence
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1000
    CACHE 1
    NO CYCLE;


-- public.sq_gr_user_code definition

-- DROP SEQUENCE public.sq_gr_user_code;

CREATE SEQUENCE public.sq_gr_user_code
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 100
    CACHE 1
    NO CYCLE;