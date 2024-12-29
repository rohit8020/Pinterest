CREATE TABLE follows (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    followee BIGINT,
    follower BIGINT,
    status VARCHAR(255),
    updated_at DATE
);