CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       full_name VARCHAR(255)
);

CREATE TABLE tasks (
                       id SERIAL PRIMARY KEY,
                       name VARCHAR(255) NOT NULL,
                       taskStatus VARCHAR(50) NOT NULL,
                       description TEXT,
                       deadline DATE,
                       priority VARCHAR(50),
                       user_id BIGINT,
                       FOREIGN KEY (user_id) REFERENCES users(id)
);
