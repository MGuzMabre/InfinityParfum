CREATE DATABASE IF NOT EXISTS usuariodb;
USE usuariodb;

CREATE TABLE roles (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL 
);

CREATE TABLE permisos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(255)
);

CREATE TABLE usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(100) NOT NULL UNIQUE,
    contraseña VARCHAR(255) NOT NULL,
    estado BOOLEAN DEFAULT TRUE,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE usuario_rol (
    usuario_id BIGINT,
    rol_id BIGINT,
    PRIMARY KEY (usuario_id, rol_id),
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
    FOREIGN KEY (rol_id) REFERENCES roles(id)
);

CREATE TABLE rol_permiso (
    rol_id INT,
    permiso_id INT,
    PRIMARY KEY (rol_id, permiso_id),
    FOREIGN KEY (rol_id) REFERENCES roles(id),
    FOREIGN KEY (permiso_id) REFERENCES permisos(id)
);

INSERT INTO roles(nombre) VALUES ('ADMIN'), ('CLIENTE'), ('GERENTE');

INSERT INTO permisos(nombre, descripcion) VALUES 
('GESTIONAR_USUARIOS', 'Puede crear/editar/borrar usuarios'),
('VER_PRODUCTOS', 'Puede visualizar el catálogo'),
('REALIZAR_COMPRAS', 'Puede hacer pedidos');

INSERT INTO usuarios(nombre, correo, contraseña) 
VALUES ('Juan Pérez', 'juan@example.com', 'claveEncriptada123');

INSERT INTO usuario_rol(usuario_id, rol_id) VALUES (1, 1); -- Juan es ADMIN

INSERT INTO rol_permiso(rol_id, permiso_id) VALUES 
(1, 1), (1, 2), (1, 3); -- ADMIN tiene todos los permisos

