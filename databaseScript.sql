
-- schema
CREATE SCHEMA hmanager;
COMMENT ON SCHEMA hmanager IS 'Namespace for healthmanager database.';

-- create types
CREATE TYPE ROLE AS ENUM ('DOCTOR', 'PATIENT', 'ADMINISTRATION');


-- create tables
CREATE TABLE hmanager.hm_personal_detail (
    "id" BIGSERIAL NOT NULL PRIMARY KEY,
	"first_name" VARCHAR(50) NOT NULL,
	"last_name" VARCHAR(50) NOT NULL,
	"pesel" VARCHAR(11) UNIQUE NOT NULL,
	"birth_date" DATE NOT NULL,
	"gender" VARCHAR(20) NOT NULL,
	"phone_number" VARCHAR(50) NOT NULL,
	"country" VARCHAR(50) NOT NULL,
	"street" VARCHAR(50) NOT NULL,
	"city" VARCHAR(50) NOT NULL,
	"building_number" INTEGER NOT NULL,
	"flat_number" INTEGER
);

CREATE TABLE hmanager.hm_user (
	"id" BIGSERIAL NOT NULL PRIMARY KEY,
	"email" VARCHAR(255) UNIQUE NOT NULL,
	"password" TEXT NOT NULL,
	"role" ROLE NOT NULL,
	"url_image" TEXT,
	"personal_details_id" BIGSERIAL NOT NULL REFERENCES hmanager.hm_personal_detail,
	"created_date" TIMESTAMP NOT NULL DEFAULT now()
);




CREATE TABLE hmanager.hm_patient (
	"id" BIGSERIAL NOT NULL PRIMARY KEY,
	"user_id" BIGSERIAL NOT NULL REFERENCES hmanager.hm_user,
	"personal_details_id" BIGSERIAL NOT NULL REFERENCES hmanager.hm_personal_detail,
	"emergency_contact_id" BIGSERIAL NOT NULL REFERENCES hmanager.hm_personal_detail,
	"insurance_number" VARCHAR(50)
);

CREATE TABLE hmanager.hm_patient_medical_information (
	"id" BIGSERIAL NOT NULL PRIMARY KEY,
	"patient_id" BIGSERIAL NOT NULL REFERENCES hmanager.hm_patient,
	"allergies" TEXT,
	"weight" INTEGER,
	"height" INTEGER,
	"other_notes" TEXT
);

CREATE TABLE hmanager.hm_specialization (
	"id" BIGSERIAL NOT NULL PRIMARY KEY,
	"description" TEXT
);

CREATE TABLE hmanager.hm_doctor (
	"id" BIGSERIAL NOT NULL PRIMARY KEY,
	"user_id" BIGSERIAL NOT NULL REFERENCES hmanager.hm_user
);

CREATE TABLE hmanager.hm_doctor_specialization (
	"id" BIGSERIAL NOT NULL PRIMARY KEY,
	"doctor_id" BIGSERIAL NOT NULL REFERENCES hmanager.hm_doctor,
	"specialization_id" BIGSERIAL NOT NULL REFERENCES hmanager.hm_specialization
);

CREATE TABLE hmanager.hm_template (
	"id" BIGSERIAL NOT NULL PRIMARY KEY,
	"doctor_id" BIGSERIAL NOT NULL REFERENCES hmanager.hm_doctor,
	"template" json
);

CREATE TABLE hmanager.hm_time_slot_type (
	"id" BIGSERIAL NOT NULL PRIMARY KEY,
	"name" VARCHAR(30)
);


CREATE TABLE hmanager.hm_time_slot (
	"id" BIGSERIAL NOT NULL PRIMARY KEY,
	"doctor_id" BIGSERIAL NOT NULL REFERENCES hmanager.hm_doctor,
	"start_date_time" TIMESTAMP,
	"end_date_time" TIMESTAMP,
	"time_slot_type_id" BIGSERIAL REFERENCES hmanager.hm_time_slot_type
);

CREATE TABLE hmanager.hm_appointment (
	"id" BIGSERIAL NOT NULL PRIMARY KEY,
	"time_slot_id" BIGSERIAL NOT NULL REFERENCES hmanager.hm_time_slot,
	"took_place" BOOLEAN,
	"office_number" INTEGER,
	"data" json
);

CREATE TABLE hmanager.hm_appointment_data (
	"id" BIGSERIAL NOT NULL PRIMARY KEY,
	"appointment_id" BIGSERIAL
);

CREATE TABLE hmanager.hm_drug(
  id BIGSERIAL,
  name TEXT NOT NULL,
  refund_rate SMALLINT CONSTRAINT hm_drugs_proper_refund CHECK (refund_rate >= 0 and refund_rate <= 101),
  PRIMARY KEY (id)
);

CREATE TABLE hmanager.hm_disease(
  id BIGSERIAL,
  name TEXT NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE hmanager.hm_medical_history(
  id BIGSERIAL,
  name TEXT,
  symptoms TEXT,
  patient_id BIGINT REFERENCES hmanager.hm_patient,
  disease_id BIGINT REFERENCES hmanager.hm_disease,
  detection_date DATE,
  cure_date DATE,
  CONSTRAINT hm_medical_history_date_order CHECK (detection_date <= cure_date),
  PRIMARY KEY (id)
);


CREATE TABLE hmanager.hm_current_condition(
  id BIGSERIAL,
  condition_name TEXT NOT NULL,
  symptoms TEXT,
  patient_id BIGINT REFERENCES hmanager.hm_patient,
  PRIMARY KEY (id)
);


CREATE TABLE hmanager.hm_current_drug(
  condition_id BIGINT,
  drug_id BIGINT,
  PRIMARY KEY (condition_id, drug_id)
);




CREATE TABLE hmanager.hm_medical_checkup(
  id BIGINT,
  doctor_id BIGINT REFERENCES hmanager.hm_doctor NOT NULL,
  creation_date TIMESTAMP DEFAULT CURRENT_DATE,
  time_slot_type BIGINT REFERENCES hmanager.hm_time_slot_type,
  PRIMARY KEY (id)
);
