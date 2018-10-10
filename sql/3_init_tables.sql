-- -------------------------------------
-- Inserting clients in table 'user'
-- -------------------------------------
INSERT INTO `online-pharmacy`.user (user.name, user.lastname, user.email, user.role, user.password, user.cash)
VALUES ('Nikita', 'Ustyushenko', 'nikita09061999@mail.ru', 'client', sha2('46754579', 256), '350'),
('Egor', 'Karduban', 'egorkarduban@gmail.com', 'client', sha2('7653546', 256), '500'),
('Vadim', 'Falometov', 'vadimfalometov@mail.ru', 'client', sha2('65347322', 256), '400'),
('Ivan', 'Ivanov', 'ivanivanov@mail.ru', 'client', sha2('12345678', 256), '250'),
('Rita', 'Korsak', 'ritakorsak@mail.ru', 'client', sha2('1234567', 256), '600'),
('Ilya', 'Mandrik', 'ilyamandrik@mail.ru', 'client', sha2('5543653', 256), '75'),
('Masha', 'Ivanova', 'mashaivanova@gmail.com', 'client', sha2('8767986', 256), '100');

-- -------------------------------------
-- Inserting admins in table 'user' 
-- -------------------------------------
INSERT INTO `online-pharmacy`.user (user.name, user.lastname, user.email, user.role, user.password)
VALUES ('Nikita', 'Ustyushenko', 'nikita1999760@gmail.com', 'admin', sha2('34576544', 256)),
('Egor', 'Karduban', 'egorkarduban@mail.ru', 'admin', sha2('65465433', 256));

-- -------------------------------------
-- Inserting doctor in table 'user'
-- -------------------------------------
INSERT INTO `online-pharmacy`.user (user.name, user.lastname, user.email, user.role, user.password, user.speciality)
VALUES('Gleb', 'Pavlovich', 'gleb06052000@mail.ru', 'doctor', sha2('65436421', 256), 'surgeon');

-- -------------------------------------
-- Inserting pharmacist in table 'user'
-- -------------------------------------
INSERT INTO `online-pharmacy`.user (user.name, user.lastname, user.email, user.role, user.password)
VALUES('Kseniay', 'Baikova', 'kseniyabaikova@mail.ru', 'pharm', sha2('63453235', 256));

-- ------------------------------------------------------
-- Inserting substances in table 'substance'
-- ------------------------------------------------------
INSERT INTO `online-pharmacy`.substance (substance.name) 
VALUES('Bisoprolol'), ('Paracetamol'), ('Fenibut'), ('Acyclovir'), ('Nifuroxazide'), ('Rivaroxaban');

-- ------------------------------------------
-- Inserting drugs in table 'drug' 
-- ------------------------------------------
INSERT INTO `online-pharmacy`.drug (drug.substance_id, drug.name, drug.company, drug.country, drug.price, 
drug.count, drug.dosage, drug.type, drug.is_required_recipe) 
VALUES ('3', 'Fenibut', 'Belmeddrugs', 'Belarus', '13', '3075', '250', 'pill', '0'), 
('4', 'Acyclovir-VISHFA', 'Zhytomyr FF', 'Ukraine', '3.40', '1', '10000', 'ointment', '0'),
('3', 'Aminobut', 'BorisovZMP', 'Belarus', '12', '528', '100', 'pill', '0'), 
('1', 'Bicarde-LF', 'Leffarm', 'Belarus', '6', '2742', '5', 'pill', '1'), 
('1', 'Bicarde-LF', 'Leffarm', 'Belarus', '4.20', '2465', '10', 'pill', '1'), 
('4', 'Acyclovir', 'Belmeddrugs', 'Belarus', '4.60', '7', '250', 'pill', '0'), 
('4', 'Acyclovir', 'Belmeddrugs', 'Belarus', '8.90', '12', '500', 'pill', '0'), 
('4', 'Acyclovir-ACOS', 'Farm-Center', 'Russia', '2.50', '22', '200', 'pill', '0'), 
('6', 'Ksarelto', 'Bayer Pharma', 'Germany', '62.80', '30', '10', 'pill', '1'), 
('6', 'Ksarelto', 'Bayer Pharma', 'Germany', '170.92', '764', '15', 'pill', '1');

-- ------------------------------------
-- Inserting recipes in table 'recipe'
-- ------------------------------------
INSERT INTO `online-pharmacy`.recipe (recipe.drug_id, recipe.doctor_id, recipe.client_id, recipe.count) 
VALUES ('4', '8', '5', '2'), 
('4', '8', '1', '2'), 
('5', '8', '3', '1'), 
('4', '8', '4', '2'),
('4', '8', '2', '1'), 
('5', '8', '5', '2'), 
('5', '8', '5', '3');

-- ----------------------------------
-- Inserting orders in table 'order'
-- ----------------------------------
INSERT INTO `online-pharmacy`.`order` (`order`.client_id, `order`.price, `order`.date, `order`.is_paid) VALUES 
('4', '16.40', '2017-08-11 22:54:41', '1'),
('5', '11.00', '2017-09-09 21:43:42', '1'),
('1', '11.00', '2017-09-11 18:10:23', '0'), 
('1', '8.90', '2017-09-21 19:11:21', '1'), 
('3', '4.20', '2017-10-01 17:11:34', '0'),
('4', '45.60', '2017-10-10 18:01:56', '1'),
('2', '12.80', '2017-10-11 16:17:43', '1'),
('2', '15.00', '2017-10-11 18:15:51', '1'),
('3', '23.00', '2017-11-05 11:22:46', '1'),
('5', '14.60', '2017-11-06 20:08:22', '1'),
('5', '32.60', '2017-12-25 21:18:12', '0'),
('4', '12.00', '2017-12-25 22:54:41', '1'),
('1', '17.80', '2018-04-21 18:10:23', '1'), 
('1', '24.80', '2018-04-25 12:11:11', '1'), 
('5', '36.60', '2018-04-25 20:08:22', '0'),
('4', '11.40', '2018-08-11 22:54:41', '1');

-- --------------------------------------------
-- Inserting order items in table 'order_item'
-- --------------------------------------------
INSERT INTO `online-pharmacy`.order_item (order_item.order_id, order_item.drug_id, order_item.recipe_id, order_item.count) 
VALUES ('1', '2', NULL, '1'), 
('1', '1', NULL, '1'), 
('2', '4', '1', '2'), 
('3', '4', '2', '2'), 
('4', '7', NULL, '1'), 
('5', '5', '3', '1'), 
('6', '4', '4', '2'), 
('6', '3', NULL, '1'), 
('6', '1', NULL, '2'), 
('7', '1', NULL, '1'), 
('8', '4', '5', '1'), 
('8', '7', NULL, '1'), 
('9', '3', NULL, '2'), 
('10', '6', NULL, '1'), 
('10', '3', NULL, '1'), 
('11', '1', NULL, '2'), 
('11', '5', '6', '2'), 
('12', '3', NULL, '1'), 
('13', '1', NULL, '1'), 
('13', '6', NULL, '1'), 
('14', '3', NULL, '2'), 
('15', '3', NULL, '2'), 
('15', '5', '7', '3'), 
('16', '7', NULL, '1'), 
('16', '8', NULL, '1');
