INSERT INTO worker (name, birthday, level, salary) VALUES 
    ('Ivanova Hanna', '1980-11-01', 'Middle', 7500),
    ('Grigorov Pavlo', '1988-04-23', 'Middle', 7800),
    ('Bojko Igor', '1999-05-07', 'Trainee', 990),
    ('Petrenko Olena', '1994-03-03', 'Junior', 3000),
    ('Shevchenko Ivan', '2001-10-15', 'Junior', 2700),
    ('Borysenko Maksim', '1975-09-30', 'Senior', 9500),
    ('Velichko Nadija', '1979-11-10', 'Senior', 9500),
    ('Fisher Matias', '1985-12-16', 'Middle', 7200),
    ('Berger Mariela', '1996-01-29', 'Middle', 7000),
    ('Miller Tomas', '2003-08-05', 'Trainee', 950);

INSERT INTO client (name) VALUES
    ('Globus'),
    ('Kosmos'),
    ('Sinus'),
    ('Magnat'),
    ('Trio');

INSERT INTO project (client_id, name, start_date, finish_date) VALUES
    (1, 'Project Globus 1', '2019-01-30', '2026-05-01'),
    (2, 'Project Kosmos 1', '2019-05-22', '2019-12-20'),
    (2, 'Project Kosmos 2', '2023-02-10', '2025-01-15'),
    (5, 'Project Trio 1', '2020-03-01', '2024-06-20'),
    (3, 'Project Sinus 1', '2021-12-20', '2023-05-20'),
    (5, 'Project Trio 2', '2022-09-01', '2025-04-25'),
    (4, 'Project Magnat 1', '2018-05-10', '2021-03-04'),
    (4, 'Project Magnat 2', '2025-08-22', '2025-12-10'),
    (1, 'Project Globus 2', '2025-09-04', '2027-07-01'),
    (2, 'Project Kosmos 1', '2025-09-20', '2026-03-10');

INSERT INTO project_worker (project_id, worker_id) VALUES
    (1, 2),
    (1, 6),
    (1, 9),
    (2, 1),
    (3, 2),
    (3, 4),
    (3, 7),
    (3, 9),
    (3, 10),
    (4, 1),
    (4, 10),
    (5, 3),
    (5, 5),
    (5, 8),
    (6, 8),
    (6, 9),
    (7, 5),
    (8, 3),
    (8, 6),
    (8, 9),
    (9, 2),
    (10, 5),
    (10, 4);

