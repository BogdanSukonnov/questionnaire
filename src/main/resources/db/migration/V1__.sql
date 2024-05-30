CREATE SEQUENCE IF NOT EXISTS answer_seq START WITH 1 INCREMENT BY 50;

CREATE SEQUENCE IF NOT EXISTS question_seq START WITH 1 INCREMENT BY 50;

CREATE SEQUENCE IF NOT EXISTS questionnaire_seq START WITH 1 INCREMENT BY 50;

CREATE SEQUENCE IF NOT EXISTS submitted_questionnaire_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE answer
(
    id              BIGINT                      NOT NULL,
    version         INTEGER,
    user_id         BIGINT                      NOT NULL,
    question_id     BIGINT                      NOT NULL,
    text            VARCHAR(255),
    checked_answer  INTEGER,
    checked_answers JSONB,
    submitted_at    TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_answer PRIMARY KEY (id)
);

CREATE TABLE question
(
    id               BIGINT       NOT NULL,
    version          INTEGER,
    order_number     INTEGER,
    text             VARCHAR(255) NOT NULL,
    type             VARCHAR(255) NOT NULL,
    answers          JSONB,
    questionnaire_id BIGINT,
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

CREATE TABLE submitted_questionnaire
(
    id               BIGINT                      NOT NULL,
    version          INTEGER,
    user_id          BIGINT                      NOT NULL,
    questionnaire_id BIGINT,
    submitted_at     TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_submittedquestionnaire PRIMARY KEY (id)
);

ALTER TABLE answer
    ADD CONSTRAINT uc_answer_user_question UNIQUE (question_id);

ALTER TABLE question
    ADD CONSTRAINT uc_question_order_questionnaire UNIQUE (questionnaire_id);

ALTER TABLE submitted_questionnaire
    ADD CONSTRAINT uc_submitted_questionnaire_user_questionnaire UNIQUE (questionnaire_id);

ALTER TABLE answer
    ADD CONSTRAINT FK_ANSWER_ON_QUESTION FOREIGN KEY (question_id) REFERENCES question (id);

ALTER TABLE question
    ADD CONSTRAINT FK_QUESTION_ON_QUESTIONNAIRE FOREIGN KEY (questionnaire_id) REFERENCES questionnaire (id);

ALTER TABLE submitted_questionnaire
    ADD CONSTRAINT FK_SUBMITTEDQUESTIONNAIRE_ON_QUESTIONNAIRE FOREIGN KEY (questionnaire_id) REFERENCES questionnaire (id);