DROP SEQUENCE DVD_SEQ;
DROP SEQUENCE CUST_SEQ;
DROP SEQUENCE RENT_SEQ;

DROP TABLE DVD;
DROP TABLE CUSTOMER;
DROP TABLE RENTAL;

CREATE SEQUENCE DVD_SEQ
  START WITH 1;
  
CREATE SEQUENCE CUST_SEQ
  START WITH 1;
  
CREATE SEQUENCE RENT_SEQ
  START WITH 1;

CREATE TABLE DVD (
    ID_DVD NUMBER(6)  CONSTRAINT DVD_PK PRIMARY KEY ,
    NAZWA VARCHAR2(32),
    CENA NUMBER(6,2),
    GATUNEK VARCHAR2(32),
    ROK_WYDANIA NUMBER(6,2),
    REZYSER VARCHAR2(32),
    ILOSC  NUMBER(6,2)
);

CREATE TABLE CUSTOMER (
    id_customer NUMBER(6)  CONSTRAINT CUSTOMER_PK PRIMARY KEY ,
    name VARCHAR2(32),
    surname VARCHAR2(32),
    village VARCHAR2(32),
    postcode VARCHAR2(32),
    apartment_number NUMBER(6,2),
    city VARCHAR2(32),
    email VARCHAR2(32),
    phone_number NUMBER(9,2)
    
);

CREATE TABLE RENTAL (
    id_rental NUMBER(6)  CONSTRAINT rental_PK PRIMARY KEY,
    id_klient NUMBER(6,2) REFERENCES CUSTOMER(id_customer),
    id_dvd NUMBER(6,2) REFERENCES DVD(ID_DVD)
);

CREATE TABLE USERR (
    login VARCHAR2(32) CONSTRAINT user_pk PRIMARY KEY,
    password VARCHAR2(32)
);