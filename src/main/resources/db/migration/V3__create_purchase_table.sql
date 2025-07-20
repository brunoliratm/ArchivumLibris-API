CREATE TABLE purchases (
    id BIGSERIAL PRIMARY KEY,
    purchase_date DATE NOT NULL,
    pay_method VARCHAR(20) NOT NULL CHECK (pay_method IN ('CREDIT_CARD', 'DEBIT_CARD', 'PIX', 'PAYPAL', 'BOLETO', 'OTHER')),
    price NUMERIC(19, 2) NOT NULL,
    book_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    FOREIGN KEY (book_id) REFERENCES books(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);