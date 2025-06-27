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


INSERT INTO zona_envio (nombre, tarifa) VALUES ('Santiago Centro', 3500);
INSERT INTO zona_envio (nombre, tarifa) VALUES ('Providencia', 4000);
INSERT INTO zona_envio (nombre, tarifa) VALUES ('Las Condes', 5000);
INSERT INTO zona_envio (nombre, tarifa) VALUES ('Ñuñoa', 3800);
INSERT INTO zona_envio (nombre, tarifa) VALUES ('Maipú', 4200);


INSERT INTO transportista (nombre, rut, telefono) VALUES ('Juan Pérez', '12.345.678-9', '987654321');
INSERT INTO transportista (nombre, rut, telefono) VALUES ('María González', '21.987.654-3', '912345678');
INSERT INTO transportista (nombre, rut, telefono) VALUES ('Pedro Ramírez', '18.234.567-1', '923456789');