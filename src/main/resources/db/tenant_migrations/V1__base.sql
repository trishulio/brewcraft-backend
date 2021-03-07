CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE SUPPLIER_ADDRESS (
    ID INTEGER PRIMARY KEY,
    ADDRESS_LINE_1 VARCHAR(255) NOT NULL,
    ADDRESS_LINE_2 VARCHAR(255),
    COUNTRY VARCHAR(255) NOT NULL,
    PROVINCE VARCHAR(255) NOT NULL,
    CITY VARCHAR(255) NOT NULL,
    POSTAL_CODE VARCHAR(255) NOT NULL,
    CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    LAST_UPDATED TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);
CREATE SEQUENCE SUPPLIER_ADDRESS_SEQUENCE START 1;

CREATE TABLE SUPPLIER (
    ID INTEGER PRIMARY KEY,
    NAME VARCHAR(255) NOT NULL UNIQUE,
    ADDRESS_ID INTEGER REFERENCES SUPPLIER_ADDRESS (ID),
    CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    LAST_UPDATED TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    VERSION INTEGER
);
CREATE INDEX IDX_SUPPLIER_ADDRESS ON SUPPLIER(ADDRESS_ID);
--starting sequence at 2 since a default supplier is being added to supplier table
--in order for purchase / invoices to work
CREATE SEQUENCE SUPPLIER_SEQUENCE START 2;

CREATE TABLE SUPPLIER_CONTACT (
    ID INTEGER PRIMARY KEY,
    SUPPLIER_ID INTEGER REFERENCES SUPPLIER (ID),
    FIRST_NAME VARCHAR(255) NOT NULL,
    LAST_NAME VARCHAR(255),
    POSITION VARCHAR(255),
    EMAIL VARCHAR(255),
    PHONE_NUMBER VARCHAR(255),
    CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    LAST_UPDATED TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    VERSION INTEGER
);
CREATE INDEX IDX_SUPPLIER_CONTACT_SUPPLIER ON SUPPLIER_CONTACT(SUPPLIER_ID);
CREATE SEQUENCE SUPPLIER_CONTACT_SEQUENCE START 1;

CREATE TABLE CURRENCY (
    NUMERIC_CODE INTEGER PRIMARY KEY,
    CODE CHAR(3) NOT NULL
);

INSERT INTO CURRENCY VALUES (124, 'CAD');

CREATE TABLE MONEY (
    ID INTEGER PRIMARY KEY,
    CURRENCY_ID INTEGER REFERENCES CURRENCY(NUMERIC_CODE) NOT NULL,
    AMOUNT NUMERIC(20, 4) NOT NULL
);
CREATE INDEX IDX_MONEY_CURRENCY ON MONEY(CURRENCY_ID);
CREATE SEQUENCE MONEY_SEQUENCE START 1;

CREATE TABLE QTY_UNIT (
    SYMBOL VARCHAR(4) PRIMARY KEY,
    NAME VARCHAR(255) UNIQUE NOT NULL
);

--Predefined Quantity Units
INSERT INTO QTY_UNIT VALUES 
	('mg', 'Milligram'),
	('g', 'Gram'),
	('kg', 'Kilogram'),
	('ml', 'Millilitre'),
	('l', 'Litre'),
	('hl', 'Hectolitre'),
	('each', 'Each');

CREATE TABLE QTY (
    ID INTEGER PRIMARY KEY,
    UNIT_SYMBOL VARCHAR(4) REFERENCES QTY_UNIT(SYMBOL) NOT NULL,
    VALUE NUMERIC NOT NULL
);
CREATE INDEX IDX_QTY_UNIT_SYMBOL ON QTY(UNIT_SYMBOL);
CREATE SEQUENCE QTY_SEQUENCE START 1;

CREATE TABLE MATERIAL_CATEGORY (
	ID INTEGER PRIMARY KEY,
    NAME VARCHAR(255) UNIQUE NOT NULL,
    PARENT_CATEGORY_ID INTEGER REFERENCES MATERIAL_CATEGORY(ID),
    CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    LAST_UPDATED TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    VERSION INTEGER,
    UNIQUE (NAME, PARENT_CATEGORY_ID)
);
CREATE INDEX IDX_CATEGORY_PARENT ON MATERIAL_CATEGORY(PARENT_CATEGORY_ID);

-- Update sequence start value when adding more categories
INSERT INTO MATERIAL_CATEGORY VALUES
	/* Start of Material Top Level Categories */
    (1, 'Ingredient', null, current_timestamp, current_timestamp, 0),
    (2, 'Packaging', null, current_timestamp, current_timestamp, 0),
    /* Start of Ingredient Subcategories */
    (3, 'Barley', 1, current_timestamp, current_timestamp, 0),
    (4, 'Hop', 1, current_timestamp, current_timestamp, 0),
    (5, 'Malt', 1, current_timestamp, current_timestamp, 0),
    (6, 'Rice', 1, current_timestamp, current_timestamp, 0),
    (7, 'Rye', 1, current_timestamp, current_timestamp, 0),
    (8, 'Sugar', 1, current_timestamp, current_timestamp, 0),
    (9, 'Syrup', 1, current_timestamp, current_timestamp, 0),
    (10, 'Wheat', 1, current_timestamp, current_timestamp, 0),
    (11, 'Yeast', 1, current_timestamp, current_timestamp, 0),
    /* Start Of Packaging Subcategories */
    (12, 'Bottle', 2, current_timestamp, current_timestamp, 0),
    (13, 'Can', 2, current_timestamp, current_timestamp, 0),
    (14, 'Case', 2, current_timestamp, current_timestamp, 0),
    (15, 'Growler', 2, current_timestamp, current_timestamp, 0),
    (16, 'Keg', 2, current_timestamp, current_timestamp, 0),
    (17, 'Label', 2, current_timestamp, current_timestamp, 0),
    (18, 'Rings', 2, current_timestamp, current_timestamp, 0);
CREATE SEQUENCE MATERIAL_CATEGORY_SEQUENCE START 19;

CREATE TABLE MATERIAL (
	ID INTEGER PRIMARY KEY,
    NAME VARCHAR(255) UNIQUE NOT NULL,
    DESCRIPTION VARCHAR(255),
    MATERIAL_CATEGORY_ID INTEGER NOT NULL REFERENCES MATERIAL_CATEGORY(ID),
    UPC VARCHAR(12),
    UNIT_SYMBOL VARCHAR(4) NOT NULL REFERENCES QTY_UNIT(SYMBOL),
    CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    LAST_UPDATED TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    VERSION INTEGER
);
CREATE INDEX IDX_MATERIAL_CATEGORY ON MATERIAL(MATERIAL_CATEGORY_ID);
CREATE INDEX IDX_MATERIAL_UNIT_SYMBOL ON MATERIAL(UNIT_SYMBOL);
CREATE SEQUENCE MATERIAL_SEQUENCE START 1;

CREATE TABLE INVOICE_STATUS (
    ID INTEGER PRIMARY KEY,
    NAME VARCHAR(512) UNIQUE NOT NULL
);
CREATE SEQUENCE INVOICE_STATUS_SEQUENCE START 1;
INSERT INTO INVOICE_STATUS VALUES (1, 'PENDING');
INSERT INTO INVOICE_STATUS VALUES (2, 'FINAL');


CREATE TABLE PURCHASE_ORDER (
    ID INTEGER PRIMARY KEY,
    ORDER_NUMBER VARCHAR(256) UNIQUE NOT NULL,
    SUPPLIER_ID INTEGER REFERENCES SUPPLIER(ID)
);
CREATE SEQUENCE PURCHASE_ORDER_SEQUENCE START 1;
INSERT INTO SUPPLIER VALUES (1, 'RISHAB_SUPPLIER', NULL, '2021-01-01T12:00:00', '2021-01-01T12:00:00', 1);
INSERT INTO PURCHASE_ORDER VALUES (1, 'ABCDE-12345', 1);

CREATE TABLE TAX (
    ID INTEGER PRIMARY KEY,
    AMOUNT_ID INTEGER REFERENCES MONEY(ID)
);
CREATE SEQUENCE TAX_SEQUENCE START 1;

CREATE TABLE FREIGHT (
    ID INTEGER PRIMARY KEY,
    AMOUNT_ID INTEGER REFERENCES MONEY(ID)
);
CREATE SEQUENCE FREIGHT_SEQUENCE START 1;

CREATE TABLE INVOICE (
    ID INTEGER PRIMARY KEY,
    DESCRIPTION VARCHAR(2048),
    INVOICE_NUMBER VARCHAR(256) UNIQUE NOT NULL,
    PURCHASE_ORDER_ID INTEGER REFERENCES PURCHASE_ORDER(ID),
    GENERATED_ON TIMESTAMP,
    RECEIVED_ON TIMESTAMP,
    PAYMENT_DUE_DATE TIMESTAMP,
    FREIGHT_ID INTEGER REFERENCES FREIGHT(ID),
    LAST_UPDATED TIMESTAMP NOT NULL,
    CREATED_AT TIMESTAMP NOT NULL,
    INVOICE_STATUS_ID INTEGER REFERENCES INVOICE_STATUS(ID),
    VERSION INTEGER
);
CREATE INDEX IDX_INVOICE_PURCHASE_ORDER ON INVOICE(PURCHASE_ORDER_ID);
CREATE INDEX IDX_INVOICE_FREIGHT ON INVOICE(FREIGHT_ID);
CREATE INDEX IDX_INVOICE_INVOICE_STATUS ON INVOICE(INVOICE_STATUS_ID);
CREATE SEQUENCE INVOICE_SEQUENCE START 1;

CREATE TABLE INVOICE_ITEM (
    ID INTEGER PRIMARY KEY,
    DESCRIPTION VARCHAR(2048),
    INVOICE_ID INTEGER REFERENCES INVOICE(ID),
    QTY_ID INTEGER REFERENCES QTY(ID),
    PRICE_ID INTEGER REFERENCES MONEY(ID),
    TAX_ID INTEGER REFERENCES TAX(ID),
    MATERIAL_ID INTEGER REFERENCES MATERIAL(ID),
    VERSION INTEGER
);
CREATE INDEX IDX_INVOICE_ITEM_INVOICE ON INVOICE_ITEM(INVOICE_ID);
CREATE INDEX IDX_INVOICE_ITEM_QTY ON INVOICE_ITEM(QTY_ID);
CREATE INDEX IDX_INVOICE_ITEM_PRICE ON INVOICE_ITEM(PRICE_ID);
CREATE INDEX IDX_INVOICE_ITEM_MATERIAL ON INVOICE_ITEM(MATERIAL_ID);
CREATE SEQUENCE INVOICE_ITEM_SEQUENCE START 1;

CREATE TABLE FACILITY_ADDRESS (
    ID INTEGER PRIMARY KEY,
    ADDRESS_LINE_1 VARCHAR(255) NOT NULL,
    ADDRESS_LINE_2 VARCHAR(255),
    COUNTRY VARCHAR(255) NOT NULL,
    PROVINCE VARCHAR(255) NOT NULL,
    CITY VARCHAR(255) NOT NULL,
    POSTAL_CODE VARCHAR(255) NOT NULL,
    CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    LAST_UPDATED TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);
CREATE SEQUENCE FACILITY_ADDRESS_SEQUENCE START 1;

CREATE TABLE FACILITY (
    ID INTEGER PRIMARY KEY,
    NAME VARCHAR(255) NOT NULL UNIQUE,
    ADDRESS_ID INTEGER REFERENCES FACILITY_ADDRESS (ID),
    PHONE_NUMBER VARCHAR(255),
    FAX_NUMBER VARCHAR(255),
    CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    LAST_UPDATED TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    VERSION INTEGER
);
CREATE INDEX IDX_FACILITY_ADDRESS ON FACILITY(ADDRESS_ID);
CREATE SEQUENCE FACILITY_SEQUENCE START 1;

CREATE TABLE EQUIPMENT (
    ID INTEGER PRIMARY KEY,
    FACILITY_ID INTEGER REFERENCES FACILITY (ID),
    NAME VARCHAR(255) NOT NULL,
    TYPE VARCHAR(255) NOT NULL,
    STATUS VARCHAR(255),
    MAX_CAPACITY_VALUE NUMERIC NOT NULL,
    MAX_CAPACITY_UNIT VARCHAR(4) REFERENCES QTY_UNIT(SYMBOL),
    MAX_CAPACITY_DISPLAY_UNIT VARCHAR(4) REFERENCES QTY_UNIT(SYMBOL),
    CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    LAST_UPDATED TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    VERSION INTEGER,
    UNIQUE (FACILITY_ID, NAME)
);
CREATE INDEX IDX_EQUIPMENT_FACILITY ON EQUIPMENT(FACILITY_ID);
CREATE INDEX IDX_EQUIPMENT_UNIT ON EQUIPMENT(MAX_CAPACITY_UNIT);
CREATE INDEX IDX_EQUIPMENT_DISPLAY_UNIT ON EQUIPMENT(MAX_CAPACITY_DISPLAY_UNIT);
CREATE SEQUENCE EQUIPMENT_SEQUENCE START 1;

CREATE TABLE STORAGE (
    ID INTEGER PRIMARY KEY,
    FACILITY_ID INTEGER REFERENCES FACILITY (ID),
    NAME VARCHAR(255) NOT NULL,
    TYPE VARCHAR(255) NOT NULL,
    CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    LAST_UPDATED TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    VERSION INTEGER,
    UNIQUE (FACILITY_ID, NAME)
);
CREATE INDEX IDX_STORAGE_FACILITY ON STORAGE(FACILITY_ID);
CREATE SEQUENCE STORAGE_SEQUENCE START 1;

CREATE TABLE SHIPMENT (
    ID INTEGER PRIMARY KEY,
    SHIPMENT_NUMBER VARCHAR(256) NOT NULL UNIQUE,
    INVOICE_ID INTEGER REFERENCES INVOICE(ID)
);
CREATE SEQUENCE SHIPMENT_SEQUENCE START 1;

CREATE TABLE PRODUCT_CATEGORY (
	ID INTEGER PRIMARY KEY,
    NAME VARCHAR(255) UNIQUE NOT NULL,
    PARENT_CATEGORY_ID INTEGER REFERENCES PRODUCT_CATEGORY(ID),
    CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    LAST_UPDATED TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    VERSION INTEGER,
    UNIQUE (NAME, PARENT_CATEGORY_ID)
);
CREATE SEQUENCE PRODUCT_CATEGORY_SEQUENCE START 1;
INSERT INTO PRODUCT_CATEGORY VALUES 
	/* Start of Product Top Level Categories */
	(1, 'Beer', null, current_timestamp, current_timestamp, 0),
	/* Start of Beer Subcategories */
	(2, 'Lager', 1, current_timestamp, current_timestamp, 0),
	(3, 'Ale', 1, current_timestamp, current_timestamp, 0),
	(4, 'IPA', 1, current_timestamp, current_timestamp, 0),
	(5, 'Stout', 1, current_timestamp, current_timestamp, 0),
	(6, 'Porter', 1, current_timestamp, current_timestamp, 0);
	
CREATE TABLE PRODUCT_MEASURES (
    ID INTEGER PRIMARY KEY,
    ABV INTEGER,
    IBU INTEGER,
    PH INTEGER,
    MASH_TEMPERATURE INTEGER,
    GRAVITY INTEGER,
    YIELD INTEGER,
    BREWHOUSE_DURATION INTEGER,
    FERMENTATION_DAYS INTEGER,
    CONDITIONING_DAYS INTEGER
);
CREATE SEQUENCE PRODUCT_MEASURES_SEQUENCE START 1;

CREATE TABLE PRODUCT (
    ID INTEGER PRIMARY KEY,
    NAME VARCHAR(255) UNIQUE NOT NULL,
    DESCRIPTION VARCHAR(255),
    PRODUCT_CATEGORY_ID INTEGER REFERENCES PRODUCT_CATEGORY (ID),
    PRODUCT_MEASURES_ID INTEGER REFERENCES PRODUCT_MEASURES (ID),
    CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    LAST_UPDATED TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    VERSION INTEGER
);
CREATE INDEX IDX_PRODUCT_CATEGORY ON PRODUCT(PRODUCT_CATEGORY_ID);
CREATE INDEX IDX_PRODUCT_MEASURES ON PRODUCT(PRODUCT_MEASURES_ID);
CREATE SEQUENCE PRODUCT_SEQUENCE START 1;





