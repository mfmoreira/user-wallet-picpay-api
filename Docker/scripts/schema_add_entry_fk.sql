USE MY_DB;

ALTER TABLE ENTRY 
ADD FOREIGN KEY (ACCOUNT_ID) REFERENCES ACCOUNT(ACCOUNT_ID);


