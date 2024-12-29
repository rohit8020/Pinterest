CREATE TABLE boards (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255),
    summary TEXT,
    cover_image TEXT,
    created_by BIGINT
);