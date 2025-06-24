CREATE DATABASE pedidosdb;
USE pedidosdb;

CREATE TABLE pedidos (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    cliente_id BIGINT NOT NULL, -- se comunica con usuarios_db
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total DECIMAL(10, 2) NOT NULL,
    estado VARCHAR(20) DEFAULT 'PENDIENTE',
    pago_id BIGINT,
    envio_id BIGINT
);

CREATE TABLE item_pedido (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    pedido_id BIGINT NOT NULL,
    producto_id BIGINT NOT NULL, -- se comunica con productos_db
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (pedido_id) REFERENCES pedidos(id) ON DELETE CASCADE
);

