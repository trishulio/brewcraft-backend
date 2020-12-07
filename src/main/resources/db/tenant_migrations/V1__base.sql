CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE SUPPLIER_ADDRESS (
    ID INTEGER PRIMARY KEY,
    ADDRESS_LINE_1 VARCHAR(255) NOT NULL,
    ADDRESS_LINE_2 VARCHAR(255),
    COUNTRY VARCHAR(255) NOT NULL,
    PROVINCE VARCHAR(255) NOT NULL,
    CITY VARCHAR(255) NOT NULL,
    POSTAL_CODE VARCHAR(255) NOT NULL,
    CREATED TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    LAST_UPDATED TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);
CREATE SEQUENCE SUPPLIER_ADDRESS_SEQUENCE START 1;

CREATE TABLE SUPPLIER (
    ID INTEGER PRIMARY KEY,
    NAME VARCHAR(255) NOT NULL UNIQUE,
    ADDRESS_ID INTEGER REFERENCES SUPPLIER_ADDRESS (ID),
    CREATED TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    LAST_UPDATED TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    VERSION INTEGER
);
CREATE SEQUENCE SUPPLIER_SEQUENCE START 1;

CREATE TABLE SUPPLIER_CONTACT (
    ID INTEGER PRIMARY KEY,
    SUPPLIER_ID INTEGER REFERENCES SUPPLIER (ID),
    FIRST_NAME VARCHAR(255) NOT NULL,
    LAST_NAME VARCHAR(255),
    POSITION VARCHAR(255),
    EMAIL VARCHAR(255),
    PHONE_NUMBER VARCHAR(255),
    CREATED TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    LAST_UPDATED TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    VERSION INTEGER
);
CREATE SEQUENCE SUPPLIER_CONTACT_SEQUENCE START 1;

CREATE TABLE INVOICE (
    ID INTEGER PRIMARY KEY,
    SUPPLIER_ID INTEGER REFERENCES SUPPLIER(ID),
    DATE TIMESTAMP NOT NULL,
    STATUS VARCHAR(64) NOT NULL,
    VERSION INTEGER
);
CREATE SEQUENCE INVOICE_SEQUENCE START 1;

CREATE TABLE INVOICE_ITEM (
    ID INTEGER PRIMARY KEY,
    INVOICE_ID INTEGER REFERENCES INVOICE(ID),
    QTY_VALUE INTEGER NOT NULL,
    UNIT VARCHAR(64) NOT NULL,
    DATE TIMESTAMP NOT NULL,
    VERSION INTEGER
);
CREATE SEQUENCE INVOICE_ITEM_SEQUENCE START 1;

CREATE TABLE MATERIAL (
	ID INTEGER PRIMARY KEY
);
CREATE SEQUENCE MATERIAL_SEQUENCE START 1;