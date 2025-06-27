CREATE DATABASE usuariosdb;
USE usuariosdb;

CREATE TABLE usuario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    correo VARCHAR(100) NOT NULL UNIQUE,
    contraseña VARCHAR(20) NOT NULL,
    estado BOOLEAN DEFAULT TRUE,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE roles (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(10) NOT NULL UNIQUE
);

CREATE TABLE permisos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(30) NOT NULL UNIQUE,
    descripcion VARCHAR(100)
);

CREATE TABLE usuario_rol (
    usuario_id BIGINT,
    rol_id INT,
    PRIMARY KEY (usuario_id, rol_id),
    FOREIGN KEY (usuario_id) REFERENCES usuario(id),
    FOREIGN KEY (rol_id) REFERENCES roles(id)
);

CREATE TABLE rol_permiso (
    rol_id INT,
    permiso_id INT,
    PRIMARY KEY (rol_id, permiso_id),
    FOREIGN KEY (rol_id) REFERENCES roles(id),
    FOREIGN KEY (permiso_id) REFERENCES permisos(id)
);
SELECT * FROM usuario;

-- =========================
-- Insertar roles necesarios
-- =========================
INSERT IGNORE INTO roles (nombre) VALUES ('CLIENTE');
INSERT IGNORE INTO roles (nombre) VALUES ('ADMIN');

-- =========================
-- Ejemplo: insertar permisos básicos (opcional)
-- =========================
INSERT IGNORE INTO permisos (nombre, descripcion) VALUES ('CREAR_USUARIO', 'Permite crear usuarios');
INSERT IGNORE INTO permisos (nombre, descripcion) VALUES ('VER_USUARIOS', 'Permite ver usuarios');
INSERT IGNORE INTO permisos (nombre, descripcion) VALUES ('ASIGNAR_ROL', 'Permite asignar roles a usuarios');

-- =========================
-- Ejemplo: asignar permisos a roles (opcional)
-- =========================
-- Asignar todos los permisos a ADMIN
INSERT IGNORE INTO rol_permiso (rol_id, permiso_id)
SELECT r.id, p.id FROM roles r, permisos p WHERE r.nombre = 'ADMIN';

-- Asignar permiso CREAR_USUARIO a CLIENTE (opcional)
INSERT IGNORE INTO rol_permiso (rol_id, permiso_id)
SELECT r.id, p.id FROM roles r, permisos p WHERE r.nombre = 'CLIENTE' AND p.nombre = 'CREAR_USUARIO';

-- =========================
-- Consulta rápida para verificar roles y permisos
-- =========================
SELECT * FROM roles;
SELECT * FROM permisos;
SELECT * FROM rol_permiso;