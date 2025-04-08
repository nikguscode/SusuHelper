--changeset nikguscode:recreating table of users
DROP TABLE "user";

CREATE TABLE "user" (
  id UUID PRIMARY KEY,
  susu_login VARCHAR(60) NOT NULL,
  susu_password TEXT NOT NULL,
  susu_group TEXT NOT NULL,
  tg_tag TEXT,
  tg_id BIGINT,
  is_blocked BOOLEAN NOT NULL,
  api_role role NOT NULL
);