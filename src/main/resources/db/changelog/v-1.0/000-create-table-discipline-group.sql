--changeset nikguscode:create-table-discipline-group
CREATE TABLE user_group (
  id UUID PRIMARY KEY,
  discipline_id UUID NOT NULL,
  group_code TEXT NOT NULL
);