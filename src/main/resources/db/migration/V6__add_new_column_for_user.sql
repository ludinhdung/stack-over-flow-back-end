ALTER TABLE users
    ADD enable BIT(1) NULL;

ALTER TABLE users
    ADD password VARCHAR(255) NULL;

ALTER TABLE users
    ADD roles VARCHAR(255) NULL;

ALTER TABLE users
    MODIFY enable TINYINT NOT NULL;
