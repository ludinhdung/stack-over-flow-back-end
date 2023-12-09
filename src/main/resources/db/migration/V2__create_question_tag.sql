CREATE TABLE question_tags
(
    question_id BIGINT NOT NULL,
    tags        VARCHAR(255) NULL
);

ALTER TABLE question_tags
    ADD CONSTRAINT fk_question_tags_on_question FOREIGN KEY (question_id) REFERENCES questions (id);