
CREATE TABLE users (
                       id BIGSERIAL PRIMARY KEY,
                       username VARCHAR(255) NOT NULL UNIQUE,
                       user_email VARCHAR(255) NOT NULL UNIQUE,
                       user_role VARCHAR(255) NOT NULL,
                       user_type VARCHAR(255) NOT NULL
);