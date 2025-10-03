CREATE TABLE IF NOT EXISTS worker (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(1000) 
        NOT NULL
        CHECK (LENGTH(name) BETWEEN 2 AND 1000),
    birthday DATE
        CHECK (YEAR(birthday) > 1900),
    level VARCHAR(20)
        NOT NULL
        CHECK (level IN ('Trainee', 'Junior', 'Middle', 'Senior')),
    salary INT
        CHECK (salary>=100 AND salary <=100000));

CREATE TABLE IF NOT EXISTS client (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(1000) 
        NOT NULL
        CHECK (LENGTH(name) BETWEEN 2 AND 1000));

CREATE TABLE IF NOT EXISTS project (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    client_id BIGINT,
    name VARCHAR(50), 
    start_date DATE,
    finish_date DATE 
        CHECK (finish_date > start_date),
    FOREIGN KEY (client_id) REFERENCES client(id));

CREATE TABLE IF NOT EXISTS project_worker (
    project_id BIGINT,
    worker_id BIGINT,
    PRIMARY KEY (project_id, worker_id),
    FOREIGN KEY (project_id)
         REFERENCES project(id),
    FOREIGN KEY (worker_id)
         REFERENCES worker(id));