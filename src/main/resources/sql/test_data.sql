--changeset JSA:100.0 contextFilter:test
DELETE FROM student_course WHERE (1=1);
DELETE FROM student WHERE (1=1);
DELETE FROM course WHERE (1=1);

INSERT INTO student (id, first_name, last_name, email) VALUES (1000, 'first', 'last', 'firstlast0@mail.com');
INSERT INTO student (id, first_name, last_name, email) VALUES (1001, 'first', 'last', 'firstlast1@mail.com');
INSERT INTO student (id, first_name, last_name, email) VALUES (1002, 'first', 'last', 'firstlast2@mail.com');
INSERT INTO student (id, first_name, last_name, email) VALUES (1003, 'first', 'last', 'firstlast3@mail.com');
INSERT INTO student (id, first_name, last_name, email) VALUES (1004, 'first', 'last', 'firstlast4@mail.com');

INSERT INTO course (id, course_name, course_description) VALUES (1000, 'course1000', 'desc1000');
INSERT INTO course (id, course_name, course_description) VALUES (1001, 'course1001', 'desc1001');

INSERT INTO student_course (id, fk_student_id, fk_course_id) VALUES (1000, 1000, 1000);
INSERT INTO student_course (id, fk_student_id, fk_course_id) VALUES (1001, 1000, 1001);
INSERT INTO student_course (id, fk_student_id, fk_course_id) VALUES (1002, 1001, 1000);
INSERT INTO student_course (id, fk_student_id, fk_course_id) VALUES (1003, 1001, 1001);
INSERT INTO student_course (id, fk_student_id, fk_course_id) VALUES (1004, 1002, 1000);
INSERT INTO student_course (id, fk_student_id, fk_course_id) VALUES (1005, 1003, 1000);
INSERT INTO student_course (id, fk_student_id, fk_course_id) VALUES (1006, 1004, 1001);
