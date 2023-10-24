-- Inserción de marcas
INSERT INTO marcas(nombre, descripcion) VALUES ('BMW', 'Deportivo');
INSERT INTO marcas(nombre, descripcion) VALUES ('Audi', 'Sedán de lujo');
INSERT INTO marcas(nombre, descripcion) VALUES ('Tesla', 'Vehículo eléctrico');

-- Inserción de autos relacionados con las marcas
INSERT INTO autos (precio, nombre, marca_id) VALUES (150000, 'BMW X5', 3);
INSERT INTO autos (precio, nombre, marca_id) VALUES (80000, 'Audi A6', 1);
INSERT INTO autos (precio, nombre, marca_id) VALUES (60000, 'Tesla Model 3', 2);
INSERT INTO autos (precio, nombre, marca_id) VALUES (35000, 'BMW 3 Series', 3);
INSERT INTO autos (precio, nombre, marca_id) VALUES (75000, 'Audi Q5',1);
INSERT INTO autos (precio, nombre, marca_id) VALUES (45000, 'Tesla Model Y', 2);

