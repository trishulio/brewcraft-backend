CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE SUPPLIER_ADDRESS (
    ID INTEGER PRIMARY KEY,
    ADDRESS_LINE_1 VARCHAR(255) NOT NULL,
    ADDRESS_LINE_2 VARCHAR(255),
    COUNTRY VARCHAR(255) NOT NULL,
    PROVINCE VARCHAR(255) NOT NULL,
    CITY VARCHAR(255) NOT NULL,
    POSTAL_CODE VARCHAR(255) NOT NULL,
    CREATED_AT TIMESTAMP NOT NULL,
    LAST_UPDATED TIMESTAMP NOT NULL
);
CREATE SEQUENCE SUPPLIER_ADDRESS_SEQUENCE START 1;

CREATE TABLE SUPPLIER (
    ID INTEGER PRIMARY KEY,
    NAME VARCHAR(255) NOT NULL UNIQUE,
    ADDRESS_ID INTEGER REFERENCES SUPPLIER_ADDRESS (ID),
    CREATED_AT TIMESTAMP NOT NULL,
    LAST_UPDATED TIMESTAMP NOT NULL,
    VERSION INTEGER
);
CREATE INDEX IDX_SUPPLIER_ADDRESS ON SUPPLIER(ADDRESS_ID);
CREATE SEQUENCE SUPPLIER_SEQUENCE START 1;

CREATE TABLE SUPPLIER_CONTACT (
    ID INTEGER PRIMARY KEY,
    SUPPLIER_ID INTEGER REFERENCES SUPPLIER (ID),
    FIRST_NAME VARCHAR(255) NOT NULL,
    LAST_NAME VARCHAR(255),
    POSITION VARCHAR(255),
    EMAIL VARCHAR(255),
    PHONE_NUMBER VARCHAR(255),
    CREATED_AT TIMESTAMP NOT NULL,
    LAST_UPDATED TIMESTAMP NOT NULL,
    VERSION INTEGER
);
CREATE INDEX IDX_SUPPLIER_CONTACT_SUPPLIER ON SUPPLIER_CONTACT(SUPPLIER_ID);
CREATE SEQUENCE SUPPLIER_CONTACT_SEQUENCE START 1;

CREATE TABLE CURRENCY (
    NUMERIC_CODE INTEGER PRIMARY KEY,
    CODE CHAR(3) NOT NULL
);

INSERT INTO CURRENCY VALUES (124, 'CAD');

CREATE TABLE QTY_UNIT (
    SYMBOL VARCHAR(4) PRIMARY KEY,
    NAME VARCHAR(255) UNIQUE NOT NULL,
    BASE_UNIT_SYMBOL VARCHAR(4) REFERENCES QTY_UNIT(SYMBOL)
);

--Predefined Quantity Units
INSERT INTO QTY_UNIT VALUES
    ('mg', 'Milligram', 'kg'),
    ('g', 'Gram', 'kg'),
    ('kg', 'Kilogram', 'kg'),
    ('ml', 'Millilitre', 'l'),
    ('l', 'Litre', 'l'),
    ('hl', 'Hectolitre', 'l'),
    ('each', 'Each', 'each');

CREATE TABLE MATERIAL_CATEGORY (
    ID INTEGER PRIMARY KEY,
    NAME VARCHAR(255) UNIQUE NOT NULL,
    PARENT_CATEGORY_ID INTEGER REFERENCES MATERIAL_CATEGORY(ID),
    CREATED_AT TIMESTAMP NOT NULL,
    LAST_UPDATED TIMESTAMP NOT NULL,
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
    IMAGE_SOURCE VARCHAR(255),
    CREATED_AT TIMESTAMP NOT NULL,
    LAST_UPDATED TIMESTAMP NOT NULL,
    VERSION INTEGER
);
CREATE INDEX IDX_MATERIAL_CATEGORY ON MATERIAL(MATERIAL_CATEGORY_ID);
CREATE INDEX IDX_MATERIAL_UNIT_SYMBOL ON MATERIAL(UNIT_SYMBOL);
CREATE SEQUENCE MATERIAL_SEQUENCE START 1;

CREATE TABLE INVOICE_STATUS (
    ID INTEGER PRIMARY KEY,
    NAME VARCHAR(512) UNIQUE NOT NULL,
    CREATED_AT TIMESTAMP NOT NULL,
    LAST_UPDATED TIMESTAMP NOT NULL,
    VERSION INTEGER NOT NULL
);
-- TODO: Put better values
INSERT INTO INVOICE_STATUS VALUES
(1, 'PENDING', '1999-01-01T12:00:00', '2000-01-01T12:00:00', 1),
(2, 'FINAL', '1999-01-01T12:00:00', '2000-01-01T12:00:00', 1),
(3, 'PAID', '1999-01-01T12:00:00', '2000-01-01T12:00:00', 1),
(4, 'REJECTED', '1999-01-01T12:00:00', '2000-01-01T12:00:00', 1);
CREATE SEQUENCE INVOICE_STATUS_SEQUENCE START 5;

CREATE TABLE PURCHASE_ORDER (
    ID INTEGER PRIMARY KEY,
    ORDER_NUMBER VARCHAR(255),
    SUPPLIER_ID INTEGER REFERENCES SUPPLIER(ID) NOT NULL,
    CREATED_AT TIMESTAMP NOT NULL,
    LAST_UPDATED TIMESTAMP NOT NULL,
    VERSION INTEGER NOT NULL
);
CREATE SEQUENCE PURCHASE_ORDER_SEQUENCE START 1;

CREATE TABLE INVOICE (
    ID INTEGER PRIMARY KEY,
    DESCRIPTION VARCHAR(2048),
    INVOICE_NUMBER VARCHAR(255),
    PURCHASE_ORDER_ID INTEGER REFERENCES PURCHASE_ORDER(ID),
    GENERATED_ON TIMESTAMP,
    RECEIVED_ON TIMESTAMP,
    PAYMENT_DUE_DATE TIMESTAMP,
    CURRENCY_CODE INTEGER REFERENCES CURRENCY(NUMERIC_CODE),
    AMOUNT NUMERIC,
    FREIGHT_CURRENCY_CODE INTEGER REFERENCES CURRENCY(NUMERIC_CODE),
    FREIGHT_AMOUNT NUMERIC,
    LAST_UPDATED TIMESTAMP NOT NULL,
    CREATED_AT TIMESTAMP NOT NULL,
    INVOICE_STATUS_ID INTEGER REFERENCES INVOICE_STATUS(ID),
    VERSION INTEGER NOT NULL
);
CREATE SEQUENCE INVOICE_SEQUENCE START 1;

-- CREATE INDEX IDX_INVOICE_PURCHASE_ORDER ON INVOICE(PURCHASE_ORDER_ID);
-- CREATE INDEX IDX_INVOICE_FREIGHT ON INVOICE(FREIGHT_ID);
-- CREATE INDEX IDX_INVOICE_INVOICE_STATUS ON INVOICE(INVOICE_STATUS_ID);

CREATE TABLE INVOICE_ITEM (
    ID INTEGER PRIMARY KEY,
    IDX INTEGER NOT NULL,
    DESCRIPTION VARCHAR(2048),
    INVOICE_ID INTEGER REFERENCES INVOICE(ID) ON DELETE CASCADE,
    DISPLAY_QTY_UNIT_SYMBOL VARCHAR(4) REFERENCES QTY_UNIT(SYMBOL),
    QTY_VALUE_IN_SYS_UNIT NUMERIC NOT NULL,
    PRICE_CURRENCY_CODE INTEGER REFERENCES CURRENCY(NUMERIC_CODE),
    PRICE_AMOUNT NUMERIC,
    TAX_CURRENCY_CODE INTEGER REFERENCES CURRENCY(NUMERIC_CODE),
    TAX_AMOUNT NUMERIC,
    MATERIAL_ID INTEGER REFERENCES MATERIAL(ID),
    CREATED_AT TIMESTAMP NOT NULL,
    LAST_UPDATED TIMESTAMP NOT NULL,
    VERSION INTEGER NOT NULL,
    CONSTRAINT UNIQUE_ITEM_IDX_PER_INVOICE UNIQUE(INVOICE_ID, IDX)
);
CREATE SEQUENCE INVOICE_ITEM_SEQUENCE START 1;

-- CREATE INDEX IDX_INVOICE_ITEM_INVOICE ON INVOICE_ITEM(INVOICE_ID);
-- CREATE INDEX IDX_INVOICE_ITEM_QTY ON INVOICE_ITEM(QTY_ID);
-- CREATE INDEX IDX_INVOICE_ITEM_PRICE ON INVOICE_ITEM(PRICE_ID);
-- CREATE INDEX IDX_INVOICE_ITEM_MATERIAL ON INVOICE_ITEM(MATERIAL_ID);

CREATE TABLE FACILITY_ADDRESS (
    ID INTEGER PRIMARY KEY,
    ADDRESS_LINE_1 VARCHAR(255) NOT NULL,
    ADDRESS_LINE_2 VARCHAR(255),
    COUNTRY VARCHAR(255) NOT NULL,
    PROVINCE VARCHAR(255) NOT NULL,
    CITY VARCHAR(255) NOT NULL,
    POSTAL_CODE VARCHAR(255) NOT NULL,
    CREATED_AT TIMESTAMP NOT NULL,
    LAST_UPDATED TIMESTAMP NOT NULL
);
CREATE SEQUENCE FACILITY_ADDRESS_SEQUENCE START 1;

CREATE TABLE FACILITY (
    ID INTEGER PRIMARY KEY,
    NAME VARCHAR(255) NOT NULL UNIQUE,
    ADDRESS_ID INTEGER REFERENCES FACILITY_ADDRESS (ID),
    PHONE_NUMBER VARCHAR(255),
    FAX_NUMBER VARCHAR(255),
    CREATED_AT TIMESTAMP NOT NULL,
    LAST_UPDATED TIMESTAMP NOT NULL,
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
    CREATED_AT TIMESTAMP NOT NULL,
    LAST_UPDATED TIMESTAMP NOT NULL,
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
    CREATED_AT TIMESTAMP NOT NULL,
    LAST_UPDATED TIMESTAMP NOT NULL,
    VERSION INTEGER,
    UNIQUE (FACILITY_ID, NAME)
);
CREATE INDEX IDX_STORAGE_FACILITY ON STORAGE(FACILITY_ID);
CREATE SEQUENCE STORAGE_SEQUENCE START 1;

CREATE TABLE SHIPMENT_STATUS (
    ID INTEGER PRIMARY KEY,
    NAME VARCHAR(255) NOT NULL UNIQUE,
    CREATED_AT TIMESTAMP NOT NULL,
    LAST_UPDATED TIMESTAMP NOT NULL,
    VERSION INTEGER NOT NULL
);
-- TODO: Put better values
INSERT INTO SHIPMENT_STATUS VALUES
(1, 'DELIVERED', '1999-01-01T12:00:00', '2000-01-01T12:00:00', 1),
(2, 'IN-TRANSIT', '1999-01-01T12:00:00', '2000-01-01T12:00:00', 1),
(3, 'PROCESSING', '1999-01-01T12:00:00', '2000-01-01T12:00:00', 1),
(4, 'PENDING', '1999-01-01T12:00:00', '2000-01-01T12:00:00', 1);
CREATE SEQUENCE SHIPMENT_STATUS_SEQUENCE START 5;

CREATE TABLE SHIPMENT (
    ID INTEGER PRIMARY KEY,
    SHIPMENT_NUMBER VARCHAR(255),
    DESCRIPTION VARCHAR (2048),
    SHIPMENT_STATUS_ID INTEGER REFERENCES SHIPMENT_STATUS(ID),
    DELIVERY_DUE_DATE TIMESTAMP,
    DELIVERED_DATE TIMESTAMP,
    CREATED_AT TIMESTAMP NOT NULL,
    LAST_UPDATED TIMESTAMP NOT NULL,
    VERSION INTEGER NOT NULL
);
CREATE SEQUENCE SHIPMENT_SEQUENCE START 1;

CREATE TABLE MATERIAL_LOT (
    ID INTEGER PRIMARY KEY,
    IDX INTEGER NOT NULL,
    LOT_NUMBER VARCHAR(255),
    DISPLAY_QTY_UNIT_SYMBOL VARCHAR(4) REFERENCES QTY_UNIT(SYMBOL),
    QTY_VALUE_IN_SYS_UNIT NUMERIC NOT NULL,
    SHIPMENT_ID INTEGER REFERENCES SHIPMENT(ID) ON DELETE CASCADE,
    INVOICE_ITEM_ID INTEGER REFERENCES INVOICE_ITEM(ID) ON DELETE SET NULL,
    STORAGE_ID INTEGER REFERENCES STORAGE(ID),
    CREATED_AT TIMESTAMP NOT NULL,
    LAST_UPDATED TIMESTAMP NOT NULL,
    VERSION INTEGER NOT NULL,
    CONSTRAINT UNIQUE_MATERIAL_LOT_IDX_PER_SHIPMENT UNIQUE(SHIPMENT_ID, IDX)
);
CREATE SEQUENCE MATERIAL_LOT_SEQUENCE START 1;

CREATE TABLE PRODUCT_CATEGORY (
    ID INTEGER PRIMARY KEY,
    NAME VARCHAR(255) UNIQUE NOT NULL,
    PARENT_CATEGORY_ID INTEGER REFERENCES PRODUCT_CATEGORY(ID),
    CREATED_AT TIMESTAMP NOT NULL,
    LAST_UPDATED TIMESTAMP NOT NULL,
    VERSION INTEGER,
    UNIQUE (NAME, PARENT_CATEGORY_ID)
);
INSERT INTO PRODUCT_CATEGORY VALUES
    /* Start of Product Top Level Categories */
    (1, 'Beer', null, current_timestamp, current_timestamp, 0),
    /* Start of Beer Subcategories */
    (2, 'Lager', 1, current_timestamp, current_timestamp, 0),
    (3, 'Ale', 1, current_timestamp, current_timestamp, 0),
    (4, 'IPA', 1, current_timestamp, current_timestamp, 0),
    (5, 'Stout', 1, current_timestamp, current_timestamp, 0),
    (6, 'Porter', 1, current_timestamp, current_timestamp, 0);
CREATE SEQUENCE PRODUCT_CATEGORY_SEQUENCE START 7;

CREATE TABLE MEASURE (
    ID INTEGER PRIMARY KEY,
    NAME VARCHAR(255) UNIQUE NOT NULL,
    CREATED_AT TIMESTAMP NOT NULL,
    LAST_UPDATED TIMESTAMP NOT NULL,
    VERSION INTEGER
);
INSERT INTO MEASURE VALUES
    (1, 'abv', current_timestamp, current_timestamp, 1),
    (2, 'ibu', current_timestamp, current_timestamp, 1),
    (3, 'ph', current_timestamp, current_timestamp, 1),
    (4, 'temperature', current_timestamp, current_timestamp, 1),
    (5, 'gravity', current_timestamp, current_timestamp, 1),
    (6, 'yield', current_timestamp, current_timestamp, 1),
    (7, 'brewhouseDuration', current_timestamp, current_timestamp, 1),
    (8, 'fermentationDays', current_timestamp, current_timestamp, 1),
    (9, 'conditioningDays', current_timestamp, current_timestamp, 1);
CREATE SEQUENCE MEASURE_SEQUENCE START 10;

CREATE TABLE PRODUCT (
    ID INTEGER PRIMARY KEY,
    NAME VARCHAR(255) UNIQUE NOT NULL,
    DESCRIPTION VARCHAR(255),
    PRODUCT_CATEGORY_ID INTEGER REFERENCES PRODUCT_CATEGORY (ID) NOT NULL,
    IMAGE_SOURCE VARCHAR(1024),
    CREATED_AT TIMESTAMP NOT NULL,
    LAST_UPDATED TIMESTAMP NOT NULL,
    DELETED_AT TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    VERSION INTEGER,
    UNIQUE (NAME, DELETED_AT)
);
CREATE INDEX IDX_PRODUCT_CATEGORY ON PRODUCT(PRODUCT_CATEGORY_ID);
CREATE SEQUENCE PRODUCT_SEQUENCE START 1;

CREATE TABLE PRODUCT_MEASURE_VALUE (
    ID INTEGER PRIMARY KEY,
    PRODUCT_ID INTEGER NOT NULL REFERENCES PRODUCT(ID),
    MEASURE_ID INTEGER NOT NULL REFERENCES MEASURE(ID),
    PRODUCT_MEASURE_VALUE NUMERIC NOT NULL,
    UNIQUE (PRODUCT_ID, MEASURE_ID)
);
CREATE SEQUENCE PRODUCT_MEASURE_VALUE_SEQUENCE START 1;

CREATE TABLE USER_STATUS (
    ID INTEGER PRIMARY KEY,
    NAME VARCHAR(255) UNIQUE NOT NULL,
    CREATED_AT TIMESTAMP NOT NULL,
    LAST_UPDATED TIMESTAMP  NOT NULL,
    VERSION INTEGER NOT NULL
);
INSERT INTO USER_STATUS VALUES (1, 'ENABLED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1);
INSERT INTO USER_STATUS VALUES (2, 'DISABLED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1);
CREATE SEQUENCE USER_STATUS_SEQUENCE START 3;

CREATE TABLE USER_SALUTATION (
    ID INTEGER PRIMARY KEY,
    NAME VARCHAR(255) UNIQUE NOT NULL,
    CREATED_AT TIMESTAMP NOT NULL,
    LAST_UPDATED TIMESTAMP NOT NULL,
    VERSION INTEGER NOT NULL
);
INSERT INTO USER_SALUTATION VALUES (1, 'MR', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1);
INSERT INTO USER_SALUTATION VALUES (2, 'MRS', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1);
INSERT INTO USER_SALUTATION VALUES (3, 'MISS', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1);
CREATE SEQUENCE USER_SALUTATION_SEQUENCE START 4;

CREATE TABLE _USER (
    ID INTEGER PRIMARY KEY,
    USER_NAME VARCHAR(255) UNIQUE NOT NULL,
    DISPLAY_NAME VARCHAR(255) NOT NULL,
    FIRST_NAME VARCHAR(255) NOT NULL,
    LAST_NAME VARCHAR(255) NOT NULL,
    EMAIL VARCHAR(255) UNIQUE NOT NULL,
    USER_SALUTATION_ID INTEGER REFERENCES USER_SALUTATION(ID),
    IMAGE_URL VARCHAR(255),
    PHONE_NUMBER VARCHAR(255),
    USER_STATUS_ID INTEGER REFERENCES USER_STATUS(ID),
    CREATED_AT TIMESTAMP NOT NULL,
    LAST_UPDATED TIMESTAMP  NOT NULL,
    VERSION INTEGER NOT NULL
);
CREATE INDEX IDX_USER_USER_STATUS ON _USER(USER_STATUS_ID);
CREATE INDEX IDX_USER_USER_SALUTATION ON _USER(USER_SALUTATION_ID);
CREATE SEQUENCE USER_SEQUENCE START 1;

CREATE TABLE USER_ROLE (
    ID INTEGER PRIMARY KEY,
    NAME VARCHAR(255) UNIQUE NOT NULL,
    CREATED_AT TIMESTAMP NOT NULL,
    LAST_UPDATED TIMESTAMP NOT NULL,
    VERSION INTEGER NOT NULL
);
INSERT INTO USER_ROLE VALUES (1, 'ADMIN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1);
CREATE SEQUENCE USER_ROLE_SEQUENCE START 2;

CREATE TABLE USER_ROLE_BINDING (
    ID INTEGER PRIMARY KEY,
    USER_ID INTEGER REFERENCES _USER(ID),
    USER_ROLE_ID INTEGER REFERENCES USER_ROLE(ID),
    CREATED_AT TIMESTAMP NOT NULL,
    LAST_UPDATED TIMESTAMP NOT NULL,
    VERSION INTEGER
);
CREATE INDEX IDX_USER_ROLE_USER ON USER_ROLE_BINDING(USER_ID);
CREATE INDEX IDX_USER_ROLE_USER_ROLE ON USER_ROLE_BINDING(USER_ROLE_ID);
CREATE SEQUENCE USER_ROLE_BINDING_SEQUENCE START 1;

CREATE TABLE BREW (
    ID INTEGER PRIMARY KEY,
    NAME VARCHAR(255),
    DESCRIPTION VARCHAR(255),
    BATCH_ID VARCHAR(255) NOT NULL,
    PRODUCT_ID INTEGER REFERENCES PRODUCT(ID),
    PARENT_BREW_ID INTEGER REFERENCES BREW(ID),
    STARTED_AT TIMESTAMP NOT NULL,
    ENDED_AT TIMESTAMP,
    CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    LAST_UPDATED TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    VERSION INTEGER
);
CREATE SEQUENCE BREW_SEQUENCE START 1;

CREATE TABLE BREW_TASK (
    ID INTEGER PRIMARY KEY,
    NAME VARCHAR(255) UNIQUE NOT NULL
);
INSERT INTO BREW_TASK VALUES (1, 'MASH');
INSERT INTO BREW_TASK VALUES (2, 'BOIL');
INSERT INTO BREW_TASK VALUES (3, 'WHIRLPOOL');
INSERT INTO BREW_TASK VALUES (4, 'DRY HOP');
INSERT INTO BREW_TASK VALUES (5, 'CONDITIONING');
INSERT INTO BREW_TASK VALUES (6, 'TRANSFER');
INSERT INTO BREW_TASK VALUES (7, 'FERMENT');
INSERT INTO BREW_TASK VALUES (8, 'STORAGE');
CREATE SEQUENCE BREW_TASK_SEQUENCE START 9;

CREATE TABLE BREW_STAGE_STATUS (
    ID INTEGER PRIMARY KEY,
    NAME VARCHAR(255) UNIQUE NOT NULL,
    CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    LAST_UPDATED TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    VERSION INTEGER
);
INSERT INTO BREW_STAGE_STATUS VALUES (1, 'IN-PROGRESS');
INSERT INTO BREW_STAGE_STATUS VALUES (2, 'COMPLETE');
INSERT INTO BREW_STAGE_STATUS VALUES (3, 'FAILED');
INSERT INTO BREW_STAGE_STATUS VALUES (4, 'INIT');
INSERT INTO BREW_STAGE_STATUS VALUES (5, 'STOP');
INSERT INTO BREW_STAGE_STATUS VALUES (6, 'SKIP');
CREATE SEQUENCE BREW_STAGE_STATUS_SEQUENCE START 7;

CREATE TABLE BREW_STAGE (
    ID INTEGER PRIMARY KEY,
    BREW_ID INTEGER NOT NULL,
    BREW_STAGE_STATUS_ID INTEGER REFERENCES BREW_STAGE_STATUS(ID),
    BREW_TASK_ID INTEGER NOT NULL REFERENCES BREW_TASK(ID),
    STARTED_AT TIMESTAMP,
    ENDED_AT TIMESTAMP,
    CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    LAST_UPDATED TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    VERSION INTEGER
);
CREATE SEQUENCE BREW_STAGE_SEQUENCE START 1;

CREATE TABLE MIXTURE (
    ID INTEGER PRIMARY KEY,
    DISPLAY_QTY_UNIT_SYMBOL VARCHAR(4) REFERENCES QTY_UNIT(SYMBOL),
    QTY_VALUE_IN_SYS_UNIT NUMERIC NOT NULL,
    EQUIPMENT_ID INTEGER REFERENCES EQUIPMENT(ID),
    PARENT_MIXTURE_ID INTEGER REFERENCES MIXTURE(ID),
    BREW_STAGE_ID INTEGER REFERENCES BREW_STAGE(ID),
    CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    LAST_UPDATED TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    VERSION INTEGER
);
CREATE SEQUENCE MIXTURE_SEQUENCE START 1;

CREATE TABLE MIXTURE_TO_PARENT_MIXTURE (
    MIXTURE_ID INTEGER REFERENCES MIXTURE(ID),
    PARENT_MIXTURE_ID INTEGER REFERENCES MIXTURE(ID),
    PRIMARY KEY (MIXTURE_ID, PARENT_MIXTURE_ID)
);

CREATE TABLE MIXTURE_RECORDING (
    ID INTEGER PRIMARY KEY,
    MIXTURE_ID INTEGER NOT NULL REFERENCES MIXTURE(ID),
    MEASURE_ID INTEGER NOT NULL REFERENCES MEASURE(ID),
    MEASURE_VALUE NUMERIC NOT NULL,
    RECORDED_AT TIMESTAMP,
    CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    LAST_UPDATED TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    VERSION INTEGER
);
CREATE SEQUENCE MIXTURE_RECORDING_SEQUENCE START 1;

CREATE TABLE MIXTURE_PORTION (
    ID INTEGER PRIMARY KEY,
    MIXTURE_ID INTEGER REFERENCES MIXTURE(ID),
    DISPLAY_QTY_UNIT_SYMBOL VARCHAR(4) REFERENCES QTY_UNIT(SYMBOL),
    QTY_VALUE_IN_SYS_UNIT NUMERIC NOT NULL,
    ADDED_AT TIMESTAMP,
    CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    LAST_UPDATED TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    VERSION INTEGER
);
CREATE SEQUENCE MIXTURE_PORTION_SEQUENCE START 1;

CREATE TABLE MATERIAL_PORTION (
    ID INTEGER PRIMARY KEY,
    MATERIAL_LOT_ID INTEGER REFERENCES MATERIAL_LOT(ID),
    QTY_VALUE_IN_SYS_UNIT NUMERIC NOT NULL NOT NULL,
    DISPLAY_QTY_UNIT_SYMBOL VARCHAR(4) REFERENCES QTY_UNIT(SYMBOL),
    ADDED_AT TIMESTAMP,
    CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    LAST_UPDATED TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    VERSION INTEGER
);
CREATE SEQUENCE MATERIAL_PORTION_SEQUENCE START 1;

CREATE TABLE MIXTURE_MATERIAL_PORTION (
    MATERIAL_PORTION_ID INTEGER REFERENCES MATERIAL_PORTION(ID),
    MIXTURE_ID INTEGER REFERENCES MIXTURE(ID),
    PRIMARY KEY (MATERIAL_PORTION_ID, MIXTURE_ID)
);

CREATE VIEW PROCUREMENT_LOT AS
    SELECT L.ID, L.LOT_NUMBER, U.BASE_UNIT_SYMBOL AS DISPLAY_QTY_UNIT_SYMBOL, COALESCE(L.QTY_VALUE_IN_SYS_UNIT, 0) AS QTY_VALUE_IN_SYS_UNIT, I.MATERIAL_ID, L.SHIPMENT_ID, L.INVOICE_ITEM_ID, L.STORAGE_ID, L.CREATED_AT, L.LAST_UPDATED, L.VERSION
    FROM MATERIAL_LOT AS L
        LEFT JOIN INVOICE_ITEM AS I
        ON L.INVOICE_ITEM_ID = I.ID
        LEFT JOIN QTY_UNIT AS U
        ON U.SYMBOL = L.DISPLAY_QTY_UNIT_SYMBOL
;

CREATE VIEW STOCK_LOT AS
    SELECT L.ID, L.LOT_NUMBER, L.DISPLAY_QTY_UNIT_SYMBOL, (L.QTY_VALUE_IN_SYS_UNIT - COALESCE(P.QTY_USED_IN_SYS_UNIT, 0)) as QTY_VALUE_IN_SYS_UNIT, L.MATERIAL_ID, L.SHIPMENT_ID, L.INVOICE_ITEM_ID, L.STORAGE_ID, L.CREATED_AT, L.LAST_UPDATED, L.VERSION
    FROM PROCUREMENT_LOT AS L
        LEFT JOIN (SELECT MATERIAL_LOT_ID, SUM(QTY_VALUE_IN_SYS_UNIT) AS QTY_USED_IN_SYS_UNIT FROM MATERIAL_PORTION GROUP BY MATERIAL_LOT_ID) AS P
        ON L.ID = P.MATERIAL_LOT_ID
;

CREATE TABLE SKU (
    ID INTEGER PRIMARY KEY,
    NAME VARCHAR(255) NOT NULL,
    DESCRIPTION VARCHAR(255),
    PRODUCT_ID INTEGER REFERENCES PRODUCT(ID),
    DISPLAY_QTY_UNIT_SYMBOL VARCHAR(4) REFERENCES QTY_UNIT(SYMBOL),
    QTY_VALUE_IN_SYS_UNIT NUMERIC NOT NULL,
    CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    LAST_UPDATED TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    VERSION INTEGER
);
CREATE SEQUENCE SKU_SEQUENCE START 1;

CREATE TABLE SKU_MATERIAL (
    ID INTEGER PRIMARY KEY,
    SKU_ID INTEGER NOT NULL REFERENCES SKU(ID),
    MATERIAL_ID INTEGER NOT NULL REFERENCES MATERIAL(ID),
    DISPLAY_QTY_UNIT_SYMBOL VARCHAR(4) REFERENCES QTY_UNIT(SYMBOL),
    QTY_VALUE_IN_SYS_UNIT NUMERIC NOT NULL,
    CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    LAST_UPDATED TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    VERSION INTEGER,
    CONSTRAINT sku_material_unique UNIQUE (SKU_ID, MATERIAL_ID) DEFERRABLE INITIALLY DEFERRED
);
CREATE SEQUENCE SKU_MATERIAL_SEQUENCE START 1;

CREATE TABLE FINISHED_GOOD_LOT (
    ID INTEGER PRIMARY KEY,
    SKU_ID INTEGER NOT NULL REFERENCES SKU(ID),
    PACKAGED_ON TIMESTAMP NOT NULL,
    DISPLAY_QTY_UNIT_SYMBOL VARCHAR(4) REFERENCES QTY_UNIT(SYMBOL),
    QTY_VALUE_IN_SYS_UNIT NUMERIC NOT NULL,
    CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    LAST_UPDATED TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    VERSION INTEGER NOT NULL
);
CREATE SEQUENCE FINISHED_GOOD_LOT_SEQUENCE START 1;

CREATE TABLE FINISHED_GOOD_LOT_PORTION (
    ID INTEGER PRIMARY KEY,
    FINISHED_GOOD_LOT_ID INTEGER NOT NULL REFERENCES FINISHED_GOOD_LOT(ID),
    DISPLAY_QTY_UNIT_SYMBOL VARCHAR(4) REFERENCES QTY_UNIT(SYMBOL),
    QTY_VALUE_IN_SYS_UNIT NUMERIC NOT NULL,
    CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    LAST_UPDATED TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    VERSION INTEGER
);
CREATE SEQUENCE FINISHED_GOOD_LOT_PORTION_SEQUENCE START 1;

CREATE TABLE FINISHED_GOOD_LOT_FINISHED_GOOD_LOT_PORTION (
    FINISHED_GOOD_LOT_ID INTEGER REFERENCES FINISHED_GOOD_LOT(ID) ON DELETE CASCADE,
    FINISHED_GOOD_LOT_PORTION_ID INTEGER REFERENCES FINISHED_GOOD_LOT_PORTION(ID),
    PRIMARY KEY (FINISHED_GOOD_LOT_ID, FINISHED_GOOD_LOT_PORTION_ID)
);

CREATE TABLE FINISHED_GOOD_LOT_MIXTURE_PORTION (
    MIXTURE_PORTION_ID INTEGER REFERENCES MIXTURE_PORTION(ID),
    FINISHED_GOOD_LOT_ID INTEGER REFERENCES FINISHED_GOOD_LOT(ID) ON DELETE CASCADE,
    PRIMARY KEY (MIXTURE_PORTION_ID, FINISHED_GOOD_LOT_ID)
);

CREATE TABLE FINISHED_GOOD_LOT_MATERIAL_PORTION (
    MATERIAL_PORTION_ID INTEGER REFERENCES MATERIAL_PORTION(ID),
    FINISHED_GOOD_LOT_ID INTEGER REFERENCES FINISHED_GOOD_LOT(ID) ON DELETE CASCADE,
    PRIMARY KEY (MATERIAL_PORTION_ID, FINISHED_GOOD_LOT_ID)
);

CREATE VIEW FINISHED_GOOD_INVENTORY AS
    SELECT FGL.ID, FGL.SKU_ID, (FGL.QTY_VALUE_IN_SYS_UNIT - COALESCE(FGLP.QTY_USED_IN_SYS_UNIT, 0)) as QTY_USED_IN_SYS_UNIT
    FROM FINISHED_GOOD_LOT AS FGL
        LEFT JOIN (SELECT FINISHED_GOOD_LOT_ID, SUM(QTY_VALUE_IN_SYS_UNIT) AS QTY_USED_IN_SYS_UNIT FROM FINISHED_GOOD_LOT_PORTION GROUP BY FINISHED_GOOD_LOT_ID) AS FGLP
        ON FGL.ID = FGLP.FINISHED_GOOD_LOT_ID
;

CREATE TABLE PROCUREMENT (
    SHIPMENT_ID INTEGER REFERENCES SHIPMENT(ID),
    INVOICE_ID INTEGER REFERENCES INVOICE(ID),
    PRIMARY KEY (SHIPMENT_ID, INVOICE_ID)
)