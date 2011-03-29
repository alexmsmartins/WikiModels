CONNECT 'jdbc:derby://localhost:1527/userauth;user=APP;password=APP';

DROP TABLE "APP"."USERINFOTABLE";
DROP TABLE "APP"."GROUPTABLE";
DROP TABLE "APP"."USERTABLE";

CREATE TABLE "APP"."USERINFOTABLE"
(
   USERID varchar(32) NOT NULL,
   FIRSTNAME varchar(32),
   LASTNAME varchar(32),
   EMAIL varchar(100)
)
;


CREATE TABLE "APP"."GROUPTABLE"
(
   USERID varchar(32) NOT NULL,
   GROUPID varchar(32) NOT NULL,
   CONSTRAINT GROUP_FK PRIMARY KEY (USERID,GROUPID)
)
;

CREATE TABLE "APP"."USERTABLE"
(
   USERID varchar(32) PRIMARY KEY NOT NULL,
   PASSWORD varchar(32) NOT NULL,
   PASSWDDIGEST varchar(32) NOT NULL
)
;

ALTER TABLE USERINFOTABLE
ADD CONSTRAINT USERINFO_FK
FOREIGN KEY (USERID)
REFERENCES USERTABLE(USERID) ON DELETE CASCADE
;
CREATE INDEX SQL090616190958780 ON USERINFOTABLE(USERID)
;

ALTER TABLE GROUPTABLE
ADD CONSTRAINT USER_FK
FOREIGN KEY (USERID)
REFERENCES USERTABLE(USERID) ON DELETE CASCADE

;
CREATE INDEX SQL090616190958681 ON GROUPTABLE(USERID)
;
CREATE UNIQUE INDEX SQL090616190958680 ON GROUPTABLE
(
  USERID,
  GROUPID
)
;


CREATE UNIQUE INDEX SQL090616190958500 ON USERTABLE(USERID)
;

INSERT INTO "APP"."USERTABLE" (USERID,PASSWORD,PASSWDDIGEST) VALUES ('Alexandre','06a05b13819f4afad991cc2143732b66','not used yet');
INSERT INTO "APP"."USERTABLE" (USERID,PASSWORD,PASSWDDIGEST) VALUES ('Armindo','4e0acaf7f52013e81f4a4a4f091134ff','');
INSERT INTO "APP"."USERTABLE" (USERID,PASSWORD,PASSWDDIGEST) VALUES ('Gonçalo','64a620a89229018e90da6bd8bac52708','');
INSERT INTO "APP"."USERTABLE" (USERID,PASSWORD,PASSWDDIGEST) VALUES ('admin','21232f297a57a5a743894a0e4a801fc3','not used yet');
INSERT INTO "APP"."USERTABLE" (USERID,PASSWORD,PASSWDDIGEST) VALUES ('alex','534b44a19bf18d20b71ecc4eb77c572f','');


INSERT INTO "APP"."GROUPTABLE" (USERID,GROUPID) VALUES ('Alexandre','ADMINISTRATORS');
INSERT INTO "APP"."GROUPTABLE" (USERID,GROUPID) VALUES ('Alexandre','USERS');
INSERT INTO "APP"."GROUPTABLE" (USERID,GROUPID) VALUES ('Armindo','ADMINISTRATORS');
INSERT INTO "APP"."GROUPTABLE" (USERID,GROUPID) VALUES ('Armindo','USERS');
INSERT INTO "APP"."GROUPTABLE" (USERID,GROUPID) VALUES ('Gonçalo','ADMINISTRATORS');
INSERT INTO "APP"."GROUPTABLE" (USERID,GROUPID) VALUES ('Gonçalo','USERS');
INSERT INTO "APP"."GROUPTABLE" (USERID,GROUPID) VALUES ('admin','ADMINISTRATORS');
INSERT INTO "APP"."GROUPTABLE" (USERID,GROUPID) VALUES ('admin','USERS');
INSERT INTO "APP"."GROUPTABLE" (USERID,GROUPID) VALUES ('alex','ADMINISTRATORS');
INSERT INTO "APP"."GROUPTABLE" (USERID,GROUPID) VALUES ('alex','USERS');

INSERT INTO "APP"."USERINFOTABLE" (USERID,FIRSTNAME,LASTNAME,EMAIL) VALUES ('admin','delete or edit me','password is admin','');


