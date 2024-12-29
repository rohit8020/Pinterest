CREATE TABLE pins (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255),
    description TEXT,
    image TEXT,
    created_by BIGINT,
    board_id BIGINT,
    link VARCHAR(255),
    tags VARCHAR(255),
    privacy BOOLEAN
);