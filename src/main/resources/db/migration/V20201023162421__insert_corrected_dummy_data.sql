INSERT INTO COMPANY (COMPANY_NAME)
VALUES ('OOCL');

INSERT INTO EMPLOYEE (
       AGE
     , COMPANY_ID
     , GENDER
     , NAME
     , SALARY
)
VALUES (
         25
       , SELECT COMPANY_ID
           FROM COMPANY
          WHERE COMPANY_NAME = 'OOCL'
       , 'Male'
       , 'Bryan'
       , 60000
);
INSERT INTO EMPLOYEE (
       AGE
     , COMPANY_ID
     , GENDER
     , NAME
     , SALARY
)
VALUES (
         23
       , SELECT COMPANY_ID
           FROM COMPANY
          WHERE COMPANY_NAME = 'OOCL'
       , 'Male'
       , 'John'
       , 10000
);
INSERT INTO EMPLOYEE (
       AGE
     , COMPANY_ID
     , GENDER
     , NAME
     , SALARY
)
VALUES (
         23
       , SELECT COMPANY_ID
           FROM COMPANY
          WHERE COMPANY_NAME = 'OOCL'
       , 'Male'
       , 'Justine'
       , 60000
);
INSERT INTO EMPLOYEE (
       AGE
     , COMPANY_ID
     , GENDER
     , NAME
     , SALARY
)
VALUES (
         25
       , SELECT COMPANY_ID
           FROM COMPANY
          WHERE COMPANY_NAME = 'OOCL'
       , 'Male'
       , 'Vance'
       , 60000
);