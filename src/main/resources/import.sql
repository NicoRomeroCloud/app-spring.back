-- Inserción de marcas
INSERT INTO marcas(nombre, descripcion) VALUES ('BMW', 'BMW (Bayerische Motoren Werke AG) es una empresa alemana de automóviles y motocicletas que se ha convertido en una de las marcas de automóviles de lujo más conocidas y respetadas en el mundo.');
INSERT INTO marcas(nombre, descripcion) VALUES ('Audi', 'Audi es conocida por fabricar automóviles de alta calidad que combinan un diseño elegante, tecnología avanzada y un rendimiento excepcional.');
INSERT INTO marcas(nombre, descripcion) VALUES ('Tesla', 'Tesla es una marca de automóviles eléctricos y tecnológicamente avanzados. Se destaca por su enfoque en la innovación y la sostenibilidad, siendo pionera en la adopción de tecnología de propulsión eléctrica en la industria automotriz.');

-- Inserción de autos relacionados con las marcas
INSERT INTO autos (precio, nombre, marca_id) VALUES (150000, 'BMW Isetta', 1);
INSERT INTO autos (precio, nombre, marca_id) VALUES (80000, 'RS 3 Sportback', 2);
INSERT INTO autos (precio, nombre, marca_id) VALUES (60000, 'Tesla Model S Plaid', 3);

