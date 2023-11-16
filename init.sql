CREATE SEQUENCE IF NOT EXISTS DATE_MATRIX_DATE_ID_SEQ START WITH 1000 INCREMENT BY 1;

/*==============================================================*/
/* Creating the date_matrix_date table                         */
/*==============================================================*/
CREATE TABLE date_matrix_date
(
    id   BIGINT      NOT NULL default nextval('DATE_MATRIX_DATE_ID_SEQ'),
    name VARCHAR(32) NOT NULL,
    CONSTRAINT date_matrix_date_pk PRIMARY KEY (id)
);

/*================================================================*/
/* Adding constraints for date_matrix_date table                  */
/*================================================================*/
ALTER TABLE date_matrix_date
    ADD CONSTRAINT date_matrix_date_unique_name UNIQUE (name);
