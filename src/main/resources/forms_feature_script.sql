CREATE TABLE hmanager.hm_form_field_type (
	"id" BIGSERIAL NOT NULL PRIMARY KEY,
	"type" TEXT NOT NULL
)

CREATE TABLE hmanager.hm_form (
	"id" BIGSERIAL NOT NULL PRIMARY KEY,
	"owner_id" BIGSERIAL REFERENCES hmanager.hm_user,
	"name" TEXT NOT NULL
)

CREATE TABLE hmanager.hm_form_field (
	"id" BIGSERIAL NOT NULL PRIMARY KEY,
	"form_id" BIGSERIAL REFERENCES hmanager.hm_form,
	"form_field_type_id" BIGSERIAL REFERENCES hmanager.hm_form_field_type,
	"name" TEXT NOT NULL,
	"is_required" BOOLEAN,
	"is_editable" BOOLEAN,
	"label" TEXT,
	"placeholder_text" TEXT NOT NULL,
	"contextual_help_text" TEXT NOT NULL,
	"warning_text" TEXT NOT NULL,
	"error_text" TEXT NOT NULL,
)

CREATE TABLE hmanager.hm_form_available_values (
	"id" BIGSERIAL NOT NULL PRIMARY KEY,
	"form_field_id" BIGSERIAL REFERENCES hmanager.hm_form_field,
	"value" TEXT,
	"name" TEXT
)

CREATE TABLE hmanager.hm_medical_checkups (
	"id" BIGSERIAL NOT NULL PRIMARY KEY,
	"form_id" BIGSERIAL REFERENCES hmanager.hm_form,
	"patient_id" BIGSERIAL REFERENCES hmanager.hm_patient,
	"created_by_id" BIGSERIAL REFERENCES hmanager.hm_user,
	"created_date" DATE,
	"last_modified_date" DATE
)

CREATE TABLE hmanager.hm_medical_checkups_values (
	"id" BIGSERIAL NOT NULL PRIMARY KEY,
	"medical_checkup_id" BIGSERIAL BIREFERENCES hmanager.hm_medical_checkups,
	"form_field_id" BIGSERIAL REFERENCES hmanager.hm_form_field,
	"value" TEXT
)

