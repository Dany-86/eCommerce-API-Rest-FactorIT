-- +--------------------------------------------------------------------------+
-- |                Base de datos ecommercedb API REST - Factor IT            |
-- +--------------------------------------------------------------------------+
--
-- Ultima actualizacion: 13-DIC-2022
--
-- Modelo Relacional de la base de datos ecommercedb:
--   - products     (*id, name, price)
--   - clients      (*dni, name, vip)
--   - carts	  (*id, is_special, client_dni) 
--   - items	  (*id, quantity, product_id, cart_id)
--   - purchases    (*id, date, total, client_dni)
--
-- Instrucciones:
--   -> Mediante un cliente de BD ejecutar: 
--
--  Drop database If Exists `ecommercedb`;
--  Create database `ecommercedb`; 
--
--   -> Conectarse mediante la app para generar las tablas y relaciones
--   -> Conectarse Mediante un cliente de BD a la base ecommercedb
--
--  USE `ecommercedb`;
--
--   -> Ejecutar el siguiente Scrip para poblar las tablas clients y products
--
--
-- --------------------------- Inicio script -----------------------------------
--
--
-- -----------------------------------------------------------------------------
--
-- ----------               POBLAR TABLA PURCHASES                 ------------
--
INSERT INTO purchases (id, date, total, client_dni) VALUES 
( 1 , '2022-9-11', 1200.00, 21647987),
( 2 , '2022-9-11', 2200.00, 21647987),
( 3 , '2022-9-13', 3100.00, 38255416),
( 4 , '2022-9-16', 1000.00, 21647987),
( 5 , '2022-9-23', 2500.00, 38255416),
( 6 , '2022-9-15', 100.00, 18589685),
( 7 , '2022-9-3', 3300.00, 38255416),
( 8 , '2022-10-19', 500.00, 38255416),
( 9 , '2022-10-20', 2000.00, 38255416),
( 10 , '2022-10-18', 7000.00, 21647987),
( 11 , '2022-10-11', 3200.00, 21647987),
( 12 , '2022-10-13', 1100.00, 38255416),
( 14 , '2022-10-16', 2000.00, 21647987),
( 15 , '2022-11-23', 3500.00, 38255416),
( 16 , '2022-11-15', 100.00, 38255416),
( 17 , '2022-11-3', 2300.00, 38255416),
( 18 , '2022-11-18', 500.00, 18589685),
( 19 , '2022-11-20', 2000.00, 38255416),
( 20 , '2022-11-13', 5100.00, 21647987),
( 21 , '2022-11-11', 200.00, 21647987),
( 31 , '2022-12-13', 1100.00, 18589685),
( 22 , '2022-12-1', 2000.00, 21647987),
( 25 , '2022-12-2', 3500.00, 38255416),
( 26 , '2022-12-13', 100.00, 38255416),
( 27 , '2022-12-3', 4300.00, 38255416),
( 28 , '2022-11-18', 500.00, 18589685),
( 29 , '2022-8-20', 2000.00, 38255416),
( 30 , '2022-12-13', 7000.00, 21647987);
--
--

--
-- -------------------------------------------------------------------------
--
-- ----------               POBLAR TABLA CLIENTS                 ------------
--
INSERT INTO clients (dni, name, vip) VALUES 
('21647987', 'Carlos Martinez', false),
('34953641', 'Alexis Pierro', false),
('17589665', 'Susana Ramirez', false),
('22654558', 'Jose Alvarez', false),
('23548469', 'Juan Fernandez', false),
('42596565', 'Melany Silva', false),
('38255416', 'Sabrina Fuentes', false),
('32546688', 'Andres Nieto', false),
('32511224', 'Sebastian Zabala', false),
('22647987', 'Carmen Pierro', false),
('32953641', 'Alejandro Perez', false),
('18589685', 'Ximena Garcia', false),
('27854598', 'Manuel Quintana', false),
('23798469', 'Jorge Colombres', false),
('40596565', 'Melisa Alvarez', false),
('30255416', 'Romina Pereira', false),
('32779811', 'Andrea Perez', false),
('32456224', 'Diego Torres', false),
('42669654', 'Mirta Romero', false);     
--   
--
-- --------------------------------------------------------------------------
--
-- ----------               POBLAR TABLA PRODUCTS                 ----------
--
INSERT INTO products (id, name, price) VALUES 
( 1, 'Impresora', 700),
( 2, 'TV Smart', 3200),
( 3, 'Ventilador', 400),
( 4, 'Celular', 2000),
( 5, 'Laptop', 4500),
( 6, 'Mouse', 100),
( 7, 'Heladera', 4300),
( 8, 'Licuadora', 500),
( 9, 'Tablet', 2000),
( 10, 'Aire Acondicioando', 4200),
( 11, 'Monitor', 1500),
( 12 , 'Estabilizador', 400),
( 13 , 'Cafetera', 1000),
( 14 , 'Minipimer', 450),
( 15 , 'Tostadora', 500),
( 18 , 'Lavarropas', 2500),
( 21 , 'Calefactor', 400),
( 50 , 'Cocina', 3000),
( 67 , 'Auriculares', 150),
( 73 , 'Parlante Portatil', 900);
--
--
--
--
--
-- --------------------------  Fin script -----------------------------------