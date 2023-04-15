DROP TABLE IF EXISTS CRM_TICKET;
CREATE TABLE CRM_TICKET (
                            ID                 INTEGER NOT NULL AUTO_INCREMENT,
                            CASE_ID            INTEGER NOT NULL,
                            CUSTOMER_ID        INTEGER ,
                            PROVIDER_ID        INTEGER,
                            ERROR_CODE         INTEGER,
                            CREATION_DATE      DATE,
                            LAST_MODIFIED_DATE DATE,
                            PRODUCT_NAME       VARCHAR(128),
                            PRIMARY KEY        (CASE_ID)
);