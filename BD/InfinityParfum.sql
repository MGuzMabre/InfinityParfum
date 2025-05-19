CREATE DATABASE IF NOT EXISTS InfinityParfum;
USE InfinityParfum;


-- Area Usuario 


-- Tabla de roles
CREATE TABLE roles (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE
);

-- Tabla de permisos
CREATE TABLE permisos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    descripcion VARCHAR(255)
);

-- Tabla de usuarios
CREATE TABLE usuario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(100) NOT NULL UNIQUE,
    contraseña VARCHAR(255) NOT NULL,
    estado BOOLEAN DEFAULT TRUE,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Relación muchos a muchos: usuario - rol
CREATE TABLE usuario_rol (
    usuario_id BIGINT,
    rol_id INT,
    PRIMARY KEY (usuario_id, rol_id),
    FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON DELETE CASCADE,
    FOREIGN KEY (rol_id) REFERENCES roles(id) ON DELETE CASCADE
);
SELECT * FROM usuario;
-- Relación muchos a muchos: rol - permiso
CREATE TABLE rol_permiso (
    rol_id INT,
    permiso_id INT,
    PRIMARY KEY (rol_id, permiso_id),
    FOREIGN KEY (rol_id) REFERENCES roles(id) ON DELETE CASCADE,
    FOREIGN KEY (permiso_id) REFERENCES permisos(id) ON DELETE CASCADE
);

-- Roles
INSERT INTO roles(nombre) VALUES ('ADMIN'), ('CLIENTE'), ('GERENTE');

-- Permisos
INSERT INTO permisos(nombre, descripcion) VALUES 
('GESTIONAR_USUARIOS', 'Puede crear, editar y eliminar usuarios'),
('VER_PRODUCTOS', 'Puede visualizar el catálogo'),
('REALIZAR_COMPRAS', 'Puede hacer pedidos');

-- Usuarios
INSERT INTO usuario(nombre, correo, contraseña) 
VALUES 
('Juan Pérez', 'juan@example.com', 'clave123'),
('Pepe Aguilar', 'pepe@gmail.com', 'clave456');

-- Asociar usuario 1 con rol ADMIN
INSERT INTO usuario_rol(usuario_id, rol_id) VALUES (1, 1);
INSERT INTO usuario_rol (usuario_id, rol_id) VALUES (1, 1);
-- Asociar rol ADMIN con todos los permisos
INSERT INTO rol_permiso(rol_id, permiso_id) VALUES 
(1, 1), (1, 2), (1, 3);
-- Ver roles y usuarios que ya tenemos
SELECT u.id AS usuario_id,
       u.nombre,
       u.correo,
       r.nombre AS rol
FROM usuario u
LEFT JOIN usuario_rol ur ON u.id = ur.usuario_id
LEFT JOIN roles r ON ur.rol_id = r.id
ORDER BY u.id;

-- Area pedidos

-- Tabla PEDIDOS
CREATE TABLE pedidos (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    cliente_id BIGINT NOT NULL,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total DECIMAL(10, 2) NOT NULL,
    estado VARCHAR(20) DEFAULT 'PENDIENTE',
    pago_id BIGINT,
    envio_id BIGINT
);

-- Tabla ITEM_PEDIDO
CREATE TABLE item_pedido (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    pedido_id BIGINT NOT NULL,
    producto_id BIGINT NOT NULL,
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (pedido_id) REFERENCES pedidos(id) ON DELETE CASCADE
);

-- Índices recomendados
CREATE INDEX idx_pedidos_cliente_id ON pedidos(cliente_id);
CREATE INDEX idx_item_pedido_producto_id ON item_pedido(producto_id);
