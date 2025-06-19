CREATE DATABASE enviosdb;
USE enviosdb;

CREATE TABLE transportista (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100),
    rut VARCHAR(20),
    telefono VARCHAR(20)
);

CREATE TABLE zona_envio (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100),
    tarifa DECIMAL(10, 2)
);

CREATE TABLE envio (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    pedido_id BIGINT NOT NULL, -- se comunica con pedidos_db
    direccion TEXT NOT NULL,
    estado VARCHAR(50) DEFAULT 'Pendiente',
    fecha_envio TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE envio_transportista (
    envio_id BIGINT,
    transportista_id BIGINT,
    PRIMARY KEY (envio_id, transportista_id),
    FOREIGN KEY (envio_id) REFERENCES envio(id),
    FOREIGN KEY (transportista_id) REFERENCES transportista(id)
);
