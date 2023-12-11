
INSERT INTO users (id, username) VALUES (1, 'John');

INSERT INTO questions (id, title, body, create_date, last_modified_date, user_id, vote_count)
VALUES (1, 'Sample Question', 'This is the body of the question.', NOW(), NULL, 1, 0);

INSERT INTO answers (id, body, create_date, last_modified_date, user_id, question_id, approve)
VALUES
    (1, 'Good job man', NOW(), NULL, 1, 1, 0),
    (2, 'Thats great', NOW(), NULL, 1, 1, 1);
