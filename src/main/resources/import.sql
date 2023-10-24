-- Inserción de marcas
INSERT INTO marcas(nombre, descripcion) VALUES ('BMW', 'El BMW Isetta es un mini vehículo construido en 1955');
INSERT INTO marcas(nombre, descripcion) VALUES ('Audi', 'Aerodinámicamente pensado al detalle, muestra su carácter único gracias a los exclusivos detalles RS. El Audi RS 3 Sportback en color rojo tango metalizado.');
INSERT INTO marcas(nombre, descripcion) VALUES ('Tesla', 'La berlina eléctrica de los ¡más de 1.000 CV! Ya se puede encargar el Model S más salvaje hasta la fecha, con unas prestaciones de infarto.');
INSERT INTO marcas(nombre, descripcion) VALUES ('Fiat', 'Versión Ejecutiva, cuenta con pisadera eléctrica, iluminación de interior led, butacas y además se puede instalar equipamiento adicionales como luces de cortesía en el piso interior.');
INSERT INTO marcas(nombre, descripcion) VALUES ('Foton', '"GRAN DISEÑO Y CAPACIDAD DE CARGA" Está diseñada para ser tu mejor aliado al transportar tus productos tanto en la ciudad o en zonas rurales.');
INSERT INTO marcas(nombre, descripcion) VALUES ('Ford', 'La nueva Maverick Híbrida, equipa un avanzado nivel de tecnología aplicada a la seguridad de los ocupantes y de terceros.');

-- Inserción de autos relacionados con las marcas
INSERT INTO autos (precio, nombre, marca_id) VALUES (150000, 'BMW Isetta', 1);
INSERT INTO autos (precio, nombre, marca_id) VALUES (80000, 'RS 3 Sportback', 2);
INSERT INTO autos (precio, nombre, marca_id) VALUES (60000, 'Tesla Model S Plaid', 3);
INSERT INTO autos (precio, nombre, marca_id) VALUES (35000, 'DUCATO PASAJEROS', 4);
INSERT INTO autos (precio, nombre, marca_id) VALUES (75000, 'Midi cargo refrigerado', 5);
INSERT INTO autos (precio, nombre, marca_id) VALUES (45000, 'Maverick Híbrida', 6);

