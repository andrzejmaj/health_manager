CREATE TABLE hmanager.hm_form_field_type (
	"id" BIGSERIAL NOT NULL PRIMARY KEY,
	"name" VARCHAR(50) NOT NULL
)

CREATE TABLE hmanager.hm_form (
	"id" BIGSERIAL NOT NULL PRIMARY KEY,
	"owner_id" BIGSERIAL REFERENCES hmanager.hm_user,
	"name" VARCHAR(50) NOT NULL
)

CREATE TABLE hmanager.hm_form_field (
	"id" BIGSERIAL NOT NULL PRIMARY KEY,
	"form_id" BIGSERIAL REFERENCES hmanager.hm_form,
	"form_field_type_id" BIGSERIAL REFERENCES hmanager.hm_form_field_type,
	"name" VARCHAR(50) NOT NULL,
	"is_required" BOOLEAN,
	"is_editable" BOOLEAN,
	"label" VARCHAR(50),
	"placeholder_text" VARCHAR(50) NOT NULL,
	"contextual_help_text" VARCHAR(100) NOT NULL,
	"warning_text" VARCHAR(50) NOT NULL,
	"error_text" VARCHAR(50) NOT NULL,
)

CREATE TABLE hmanager.hm_medical_checkups (
	"id" BIGSERIAL NOT NULL PRIMARY KEY,
	"form_id" BIGSERIAL REFERENCES hmanager.hm_form,
	"patient_id" BIGSERIAL REFERENCES hmanager.hm_patient,
	"doctor_id" BIGSERIAL REFERENCES hmanager.hm_doctor,
	"created_by_id" BIGSERIAL REFERENCES hmanager.hm_user
)

CREATE TABLE hmanager.hm_form_values (
	"id" BIGSERIAL NOT NULL PRIMARY KEY,
	"medical_checkup_id" BIGSERIAL BIREFERENCES hmanager.hm_medical_checkups,
	"form_field_id" BIGSERIAL REFERENCES hmanager.hm_form_field,
	"value" TEXT
)

