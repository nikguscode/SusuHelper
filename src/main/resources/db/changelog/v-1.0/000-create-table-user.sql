--changeset nikguscode:create-table-user
CREATE TABLE "user" (
  id UUID PRIMARY KEY,
  susu_login VARCHAR(60) NOT NULL,
  susu_password TEXT NOT NULL,
  susu_group_id UUID,
  tg_tag TEXT,
  tg_id TEXT,
  is_blocked BOOLEAN NOT NULL,
  api_role role NOT NULL
);