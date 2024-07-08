CREATE TABLE sessions (
                          id UUID PRIMARY KEY,
                          user_id UUID NOT NULL,
                          token TEXT NOT NULL,
                          expiration_time TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                          FOREIGN KEY (user_id) REFERENCES users(id)
);