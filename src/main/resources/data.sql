-- Пароли: admin = admin123, storekeeper = store123, receptionist = recept123
INSERT INTO users (login, password_hash, full_name, role, active, created_at) VALUES
('ADMIN', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'Администратор Системы', 'ADMIN', true, NOW()),
('SKLAD', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'Петров П.П.', 'STOREKEEPER', true, NOW()),
('PRIEM', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'Сидорова С.С.', 'RECEPTIONIST', true, NOW());

-- Тестовые пациенты
INSERT INTO patients (medical_card_number, full_name, birth_date, created_at) VALUES
('ИБ-10001', 'Иванов Иван Иванович', '1980-03-20', NOW()),
('ИБ-10002', 'Петров Петр Петрович', '1975-07-15', NOW()),
('ИБ-10003', 'Сидорова Анна Сергеевна', '1990-12-05', NOW());

INSERT INTO storages (name, storage_type) VALUES
('Склад приёмного отделения', 'RECEPTION'),
('Склад долговременного хранения №1', 'LONG_TERM');

INSERT INTO storage_cells (name, storage_id, is_occupied) VALUES
('Ячейка А-01', 2, false),
('Ячейка А-02', 2, false),
('Ячейка А-03', 2, false),
('Ячейка Б-01', 2, false),
('Ячейка Б-02', 2, false);
