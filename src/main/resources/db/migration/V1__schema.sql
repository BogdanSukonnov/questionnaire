CREATE SEQUENCE IF NOT EXISTS question_seq START WITH 1 INCREMENT BY 50;

CREATE SEQUENCE IF NOT EXISTS questionnaire_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE question
(
    id             BIGINT       NOT NULL,
    version        INTEGER,
    text           VARCHAR(255) NOT NULL,
    type           VARCHAR(255) NOT NULL,
    answers        JSONB,
    questionary_id BIGINT,
    CONSTRAINT pk_question PRIMARY KEY (id)
);

CREATE TABLE questionnaire
(
    id          BIGINT                      NOT NULL,
    version     INTEGER,
    title       VARCHAR(255)                NOT NULL,
    start_date  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    end_date    TIMESTAMP WITHOUT TIME ZONE,
    description VARCHAR(255),
    CONSTRAINT pk_questionnaire PRIMARY KEY (id)
);

ALTER TABLE question
    ADD CONSTRAINT FK_QUESTION_ON_QUESTIONARY FOREIGN KEY (questionary_id) REFERENCES questionnaire (id);