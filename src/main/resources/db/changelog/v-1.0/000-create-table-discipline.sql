--changeset nikguscode:create-table-discipline
CREATE TABLE discipline (
  id UUID PRIMARY KEY,
  discipline_name VARCHAR(120) NOT NULL,
  attendance_url TEXT NOT NULL,
  start_time TIME(0) NOT NULL,
  end_time TIME(0) NOT NULL,
  student_group_id UUID NOT NULL,
  discipline_code_id UUID NOT NULL
);