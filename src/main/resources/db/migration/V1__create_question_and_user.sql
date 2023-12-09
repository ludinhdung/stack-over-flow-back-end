CREATE TABLE questions
(
    id                 BIGINT AUTO_INCREMENT NOT NULL,
    title              VARCHAR(255)          NULL,
    body               LONGTEXT              NULL,
    create_date        DATETIME                  NOT NULL,
    last_modified_date DATETIME                  NULL,
    user_id            BIGINT                NOT NULL,
    CONSTRAINT pk_questions PRIMARY KEY (id)
);

CREATE TABLE users
(
    id BIGINT AUTO_INCREMENT NOT NULL,
    username VARCHAR(255) NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE questions
    ADD CONSTRAINT FK_QUESTIONS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE;