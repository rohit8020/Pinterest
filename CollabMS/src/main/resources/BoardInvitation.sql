CREATE TABLE board_invitations (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    invitee BIGINT,
    inviter BIGINT,
    board_id BIGINT,
    description TEXT,
    status VARCHAR(255),
    updated_at DATE
);