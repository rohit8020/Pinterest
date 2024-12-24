CREATE TABLE boards (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255),
    summary TEXT,
    coverImage TEXT,
    createdBy BIGINT
);