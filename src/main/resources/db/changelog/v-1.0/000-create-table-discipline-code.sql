--changeset nikguscode:create-table-discipline-code
CREATE TABLE discipline_code (
  id UUID PRIMARY KEY,
  discipline_id UUID NOT NULL,
  code TEXT NOT NULL,
  expiration_date DATE NOT NULL,
  id_user_who_added_code UUID
);