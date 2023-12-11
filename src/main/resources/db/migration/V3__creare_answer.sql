CREATE TABLE answers
(
    id                 BIGINT AUTO_INCREMENT NOT NULL,
    body               LONGTEXT NULL,
    create_date        date             NOT NULL,
    last_modified_date date NULL,
    user_id            BIGINT           NOT NULL,
    question_id        BIGINT           NOT NULL,
    approve           BIT(1) DEFAULT 0 NOT NULL,
    CONSTRAINT pk_answers PRIMARY KEY (id)
);

ALTER TABLE answers
    ADD CONSTRAINT FK_ANSWERS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE;

ALTER TABLE answers
    ADD CONSTRAINT FK_ANSWERS_ON_USER3JjJ40 FOREIGN KEY (user_id) REFERENCES questions (id) ON DELETE CASCADE;