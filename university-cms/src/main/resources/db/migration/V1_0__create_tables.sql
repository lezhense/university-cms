DROP TABLE IF EXISTS cms.teachers_roles;
DROP TABLE IF EXISTS cms.students_roles;
DROP TABLE IF EXISTS cms.groups_lessons;
DROP TABLE IF EXISTS cms.groups_courses;
DROP TABLE IF EXISTS cms.lessons;
DROP TABLE IF EXISTS cms.students;
DROP TABLE IF EXISTS cms.courses;
DROP TABLE IF EXISTS cms.schedules_for_day;
DROP TABLE IF EXISTS cms.teachers;
DROP TABLE IF EXISTS cms.groups;
DROP TABLE IF EXISTS cms.roles;

CREATE SCHEMA IF NOT EXISTS cms;

CREATE TABLE IF NOT EXISTS cms.roles
(   
    id serial primary key,
    name varchar ( 255 ) unique NOT NULL 
);

CREATE TABLE IF NOT EXISTS cms.groups
(   
    id serial primary key,
    name varchar ( 255 ) unique NOT NULL,
    speciality varchar ( 255 ),
    year int
);

CREATE TABLE IF NOT EXISTS cms.teachers
(   
    id serial primary key,
    first_name varchar ( 255 ) NOT NULL,
    last_name varchar ( 255 ) NOT NULL,
    degree varchar ( 255 ),
    username varchar ( 255 ) UNIQUE NOT NULL, 
    password varchar ( 255 ) NOT NULL  
);

CREATE TABLE IF NOT EXISTS cms.schedules_for_day
(   
    id serial primary key,
    schedule_date date NOT NULL
);

CREATE TABLE IF NOT EXISTS cms.courses
(   id serial primary key, 
    teacher_id int,
    name varchar ( 255 ) unique NOT NULL,
    FOREIGN KEY (teacher_id)
    REFERENCES cms.teachers (id)
);

CREATE TABLE IF NOT EXISTS cms.students
(   
    id serial primary key,
    group_id int,
    first_name varchar ( 255 ) NOT NULL,
    last_name varchar ( 255 ) NOT NULL,
    username varchar ( 255 ) UNIQUE NOT NULL, 
    password varchar ( 255 ) NOT NULL,    
    FOREIGN KEY (group_id)
    REFERENCES cms.groups (id)
);

CREATE TABLE IF NOT EXISTS cms.lessons
(   
    id serial primary key,
    course_id int,
    teacher_id int,
    schedule_for_day_id int,
    number int,
    FOREIGN KEY (course_id)
    REFERENCES cms.courses (id),
    FOREIGN KEY (teacher_id)
    REFERENCES cms.teachers (id),
    FOREIGN KEY (schedule_For_Day_id)
    REFERENCES cms.schedules_for_day (id)
);

CREATE TABLE IF NOT EXISTS cms.groups_courses
(   
    group_id int NOT NULL,
    course_id int NOT NULL,
    constraint groups_courses_pk primary key (group_id, course_id),
    FOREIGN KEY (course_id)
    REFERENCES cms.courses (id) ON DELETE CASCADE,
    FOREIGN KEY (group_id)
    REFERENCES cms.groups (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS cms.groups_lessons
(   
    group_id int NOT NULL,
    lesson_id int NOT NULL,
    constraint groups_lessons_pk primary key (group_id, lesson_id),
    FOREIGN KEY (lesson_id)
    REFERENCES cms.lessons (id) ON DELETE CASCADE,
    FOREIGN KEY (group_id)
    REFERENCES cms.groups (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS cms.students_roles
(   
    student_id int NOT NULL,
    role_id int NOT NULL,
    constraint students_roles_pk primary key (student_id, role_id),
    FOREIGN KEY (role_id)
    REFERENCES cms.roles (id) ON DELETE CASCADE,
    FOREIGN KEY (student_id)
    REFERENCES cms.students (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS cms.teachers_roles
(   
    teacher_id int NOT NULL,
    role_id int NOT NULL,
    constraint teachers_roles_pk primary key (teacher_id, role_id),
    FOREIGN KEY (role_id)
    REFERENCES cms.roles (id) ON DELETE CASCADE,
    FOREIGN KEY (teacher_id)
    REFERENCES cms.teachers (id) ON DELETE CASCADE
);


