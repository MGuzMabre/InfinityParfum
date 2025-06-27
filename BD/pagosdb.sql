CREATE DATABASE pagosdb;
USE pagosdb;

CREATE TABLE metodo_pago (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    detalles TEXT
);

CREATE TABLE pago (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    pedido_id BIGINT NOT NULL, -- se comunica con pedidos_db
    descripcion VARCHAR(255),
    metodo_id INT,
    monto DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (metodo_id) REFERENCES metodo_pago(id)
);
-- Insertar métodos de pago básicos
INSERT INTO metodo_pago (nombre, detalles) VALUES ('Tarjeta', 'Visa, MasterCard, etc.');
INSERT INTO metodo_pago (nombre, detalles) VALUES ('Transferencia', 'Transferencia bancaria');
INSERT INTO metodo_pago (nombre, detalles) VALUES ('Efectivo', 'Pago en efectivo');