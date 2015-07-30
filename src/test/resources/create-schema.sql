CREATE TABLE STAMP_CONDITION
(ID INTEGER NOT NULL,
  NAME VARCHAR(255),
  PRIMARY KEY (ID)
);

CREATE TABLE STAMP_TYPE
( ID INTEGER NOT NULL,
  NAME VARCHAR(255),
  PRIMARY KEY (ID)
);

CREATE TABLE CATEGORY
(ID INTEGER NOT NULL,
  NAME VARCHAR(255),
  PRIMARY KEY (ID)
);

CREATE TABLE COUNTRY
(ID INTEGER NOT NULL,
  NAME VARCHAR(255),
  PRIMARY KEY (ID)
);

CREATE TABLE IMAGE
(ID BIGINT NOT NULL,
  CONTENT BLOB,
  PRIMARY KEY (ID)
);

CREATE TABLE STAMP
( ID INTEGER NOT NULL,
  DESCRIPTION VARCHAR(255),
  NAME VARCHAR(255),
  PUBLISH_YEAR INTEGER,
  CATEGORY_ID INTEGER,
  COUNTRY_ID INTEGER,
  IMAGE_ID BIGINT,
  KEYWORDS VARCHAR(255),
  PRICE BIGINT,
  REMARKS VARCHAR(255),
  STAMP_NUMBER VARCHAR(255),
  FACE_VALUE VARCHAR(255),
  HINGED INTEGER,
  STAMP_TYPE_ID INTEGER,
  STAMP_CONDITION_ID INTEGER,
  PRIMARY KEY (ID),
  CONSTRAINT FK_CATEGORY FOREIGN KEY (CATEGORY_ID)
      REFERENCES CATEGORY (ID)
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT FK_STAMP_TYPE_ID FOREIGN KEY (STAMP_TYPE_ID)
      REFERENCES STAMP_TYPE (ID)
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT FK_STAMP_CONDITION_ID FOREIGN KEY (STAMP_CONDITION_ID)
      REFERENCES STAMP_CONDITION (ID)
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT FK_COUNTRY_ID FOREIGN KEY (COUNTRY_ID)
      REFERENCES COUNTRY (ID)
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE STAMP_CATEGORY
( STAMP_ID INTEGER NOT NULL,
  CATEGORY_ID INTEGER NOT NULL,
  PRIMARY KEY (STAMP_ID, CATEGORY_ID),
  CONSTRAINT CATEGORY_ID_FK FOREIGN KEY (CATEGORY_ID)
      REFERENCES CATEGORY (ID)
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT STAMP_ID_FK FOREIGN KEY (STAMP_ID)
      REFERENCES STAMP (ID)
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

