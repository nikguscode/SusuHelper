--changeset nikguscode:create-table-user-role
CREATE TYPE role AS ENUM ('USER', 'MODERATOR-1', 'MODERATOR-2', 'ADMIN');