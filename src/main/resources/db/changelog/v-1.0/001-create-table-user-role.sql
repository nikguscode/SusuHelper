--changeset nikguscode:recreating table of user roles
DROP TYPE role CASCADE;

CREATE TYPE role AS ENUM ('USER', 'MODERATOR1', 'MODERATOR2', 'ADMIN')