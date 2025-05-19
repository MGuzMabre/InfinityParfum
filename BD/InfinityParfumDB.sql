CREATE DATABASE IF NOT EXISTS InfinityParfum;
USE InfinityParfum;
-- AREA USUARIOS
CREATE TABLE roles (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE permisos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    descripcion VARCHAR(255)
);

CREATE TABLE usuario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(100) NOT NULL UNIQUE,
    contraseña VARCHAR(255) NOT NULL,
    estado BOOLEAN DEFAULT TRUE,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE usuario_rol (
    usuario_id BIGINT,
    rol_id INT,
    PRIMARY KEY (usuario_id, rol_id),
    FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON DELETE CASCADE,
    FOREIGN KEY (rol_id) REFERENCES roles(id) ON DELETE CASCADE
);

CREATE TABLE rol_permiso (
    rol_id INT,
    permiso_id INT,
    PRIMARY KEY (rol_id, permiso_id),
    FOREIGN KEY (rol_id) REFERENCES roles(id) ON DELETE CASCADE,
    FOREIGN KEY (permiso_id) REFERENCES permisos(id) ON DELETE CASCADE
);

-- AREA PRODUCTOS

CREATE TABLE producto (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT NOT NULL,
    precio DECIMAL(10,2) NOT NULL CHECK (precio > 0),
    stock INT NOT NULL CHECK (stock >= 0)
);

-- AREA PEDIDOS

CREATE TABLE pedidos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cliente_id BIGINT NOT NULL,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total DECIMAL(10, 2) NOT NULL,
    estado VARCHAR(20) DEFAULT 'PENDIENTE',
    pago_id BIGINT,
    envio_id BIGINT,
    FOREIGN KEY (cliente_id) REFERENCES usuario(id)
);

CREATE TABLE item_pedido (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    pedido_id BIGINT NOT NULL,
    producto_id BIGINT NOT NULL,
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (pedido_id) REFERENCES pedidos(id) ON DELETE CASCADE,
    FOREIGN KEY (producto_id) REFERENCES producto(id)
);

-- AREA PAGOS

CREATE TABLE pagos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    pedido_id BIGINT NOT NULL,
    descripcion VARCHAR(255) NOT NULL,
    metodo VARCHAR(100) NOT NULL,
    monto DECIMAL(10,2) NOT NULL CHECK (monto > 0),
    FOREIGN KEY (pedido_id) REFERENCES pedidos(id)
);

-- AREA ENVIOS

CREATE TABLE envios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    pedido_id BIGINT NOT NULL,
    direccion VARCHAR(255) NOT NULL,
    estado VARCHAR(50) DEFAULT 'Pendiente',
    fecha_envio TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (pedido_id) REFERENCES pedidos(id)
);

-- INDICES 

CREATE INDEX idx_usuario_correo ON usuario(correo);
CREATE INDEX idx_producto_nombre ON producto(nombre);
CREATE INDEX idx_pedidos_cliente_id ON pedidos(cliente_id);
CREATE INDEX idx_item_producto_id ON item_pedido(producto_id);
CREATE INDEX idx_envio_pedido_id ON envios(pedido_id);
CREATE INDEX idx_pago_pedido_id ON pagos(pedido_id);

-- TEST

INSERT INTO roles(nombre) VALUES ('ADMIN'), ('CLIENTE'), ('GERENTE');

INSERT INTO permisos(nombre, descripcion) VALUES 
('GESTIONAR_USUARIOS', 'Puede crear, editar y eliminar usuarios'),
('VER_PRODUCTOS', 'Puede visualizar el catálogo'),
('REALIZAR_COMPRAS', 'Puede hacer pedidos');

INSERT INTO usuario(nombre, correo, contraseña) 
VALUES 
('Juan Pérez', 'juan@example.com', 'clave123'),
('Pepe Aguilar', 'pepe@gmail.com', 'clave456');

INSERT INTO usuario_rol(usuario_id, rol_id) VALUES (1, 1);

INSERT INTO rol_permiso(rol_id, permiso_id) VALUES 
(1, 1), (1, 2), (1, 3);