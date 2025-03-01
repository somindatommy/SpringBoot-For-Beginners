CREATE TABLE payments
(
    payment_id     VARCHAR(255) PRIMARY KEY,
    initiator_id   VARCHAR(255)   NOT NULL,
    receiver_id    VARCHAR(255)   NOT NULL,
    amount         DECIMAL(10, 2) NOT NULL,
    initiated_time DATETIME       NOT NULL,
    status         VARCHAR(50)    NOT NULL
);

-- Inserting sample data
INSERT INTO payments (payment_id, initiator_id, receiver_id, amount, initiated_time, status)
VALUES ('PAY-20250227-204927-126871628939123-00001', 'initiator_1', 'receiver_1', 150.75, '2025-02-27 20:49:27',
        'SUCCESS'),
       ('PAY-20250227-204927-126871628939123-00002', 'initiator_2', 'receiver_2', 200.50, '2025-02-27 20:49:27',
        'PENDING');
