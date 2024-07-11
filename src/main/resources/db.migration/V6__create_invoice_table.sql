CREATE TABLE invoices
(
    id          UUID PRIMARY KEY,
    amount      BIGINT       NOT NULL,
    comment     TEXT,
    issue_date  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    type        VARCHAR(255) NOT NULL,
    sender_id   UUID,
    receiver_id UUID,
    FOREIGN KEY (sender_id) REFERENCES users (id),
    FOREIGN KEY (receiver_id) REFERENCES users (id)
);