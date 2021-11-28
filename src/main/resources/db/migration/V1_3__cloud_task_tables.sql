CREATE TABLE TASK.EXECUTION  (
                                 TASK_EXECUTION_ID BIGINT  NOT NULL PRIMARY KEY ,
                                 START_TIME DATETIME DEFAULT NULL ,
                                 END_TIME DATETIME DEFAULT NULL ,
                                 TASK_NAME  VARCHAR(100) ,
                                 EXIT_CODE INTEGER ,
                                 EXIT_MESSAGE VARCHAR(2500) ,
                                 ERROR_MESSAGE VARCHAR(2500) ,
                                 LAST_UPDATED DATETIME ,
                                 EXTERNAL_EXECUTION_ID VARCHAR(255),
                                 PARENT_EXECUTION_ID BIGINT
);

CREATE TABLE TASK.EXECUTION_PARAMS  (
                                        TASK_EXECUTION_ID BIGINT NOT NULL ,
                                        TASK_PARAM VARCHAR(2500) ,
                                        constraint TASK_EXEC_PARAMS_FK foreign key (TASK_EXECUTION_ID)
                                            references TASK.EXECUTION(TASK_EXECUTION_ID)
) ;

CREATE TABLE TASK.TASK_BATCH (
                                 TASK_EXECUTION_ID BIGINT NOT NULL ,
                                 JOB_EXECUTION_ID BIGINT NOT NULL ,
                                 constraint TASK_EXEC_BATCH_FK foreign key (TASK_EXECUTION_ID)
                                     references TASK.EXECUTION(TASK_EXECUTION_ID)
) ;

CREATE SEQUENCE TASK.SEQ START WITH 0 MINVALUE 0 MAXVALUE 9223372036854775807 NO CACHE NO CYCLE;

CREATE TABLE TASK.LOCK  (
                            LOCK_KEY CHAR(36) NOT NULL,
                            REGION VARCHAR(100) NOT NULL,
                            CLIENT_ID CHAR(36),
                            CREATED_DATE DATETIME NOT NULL,
                            constraint LOCK_PK primary key (LOCK_KEY, REGION)
);