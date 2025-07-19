CREATE TABLE purchases (
    id BIGSERIAL PRIMARY KEY,
    purchase_date DATE,
    pay_method VARCHAR(255),
    price NUMERIC(19, 2),
    book_id BIGINT,
    user_id BIGINT,
    FOREIGN KEY (book_id) REFERENCES books(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);