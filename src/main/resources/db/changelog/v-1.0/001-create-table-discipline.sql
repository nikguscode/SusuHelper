--changeset nikguscode:recreating table discipline
DROP TABLE discipline;

CREATE TABLE discipline (
  id UUID PRIMARY KEY,
  discipline_name VARCHAR(120) NOT NULL,
  attendance_url TEXT NOT NULL,
  start_time TIME NOT NULL,
  end_time TIME NOT NULL,
  scheduled_days TEXT[] NOT NULL,
  student_group_id UUID NOT NULL,
  discipline_verification_code_id UUID NOT NULL
);