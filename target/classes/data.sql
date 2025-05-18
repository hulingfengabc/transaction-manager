-- 初始化交易数据
INSERT INTO transaction (type, amount, description, date) VALUES
('DEPOSIT', 1000.00, 'test1', '2025-01-01 09:00:00'),
('WITHDRAWAL', 200.50, 'test2', '2025-01-05 14:30:00'),
('DEPOSIT', 50000.00, 'test3', '2025-02-01 17:45:00'),
('WITHDRAWAL', 40.00, 'test4', '2025-02-15 08:15:00'),
('DEPOSIT', 0.01, 'test5', '2025-04-01 00:00:00'),
('WITHDRAWAL', 999999999.99, 'test6', '2025-12-31 23:59:59');

-- 添加分页测试数据
INSERT INTO transaction (type, amount, description, date) VALUES
('DEPOSIT', 1234.56, 'test7', '2025-05-10 11:22:33'),
('WITHDRAWAL', 789.01, 'test8', '2025-06-15 16:44:55'),
('DEPOSIT', 2345.67, 'test9', '2025-07-20 09:11:22'),
('WITHDRAWAL', 890.12, 'test10', '2025-08-25 13:33:44');