CREATE DATABASE productosdb;
USE productosdb;

CREATE TABLE producto (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    precio DECIMAL(10, 2) NOT NULL,
    stock INT NOT NULL
);

CREATE TABLE categoria (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL
);

CREATE TABLE producto_categoria (
    producto_id BIGINT,
    categoria_id INT,
    PRIMARY KEY (producto_id, categoria_id),
    FOREIGN KEY (producto_id) REFERENCES producto(id),
    FOREIGN KEY (categoria_id) REFERENCES categoria(id)
);

CREATE TABLE marca (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL
);

CREATE TABLE producto_marca (
    producto_id BIGINT,
    marca_id INT,
    PRIMARY KEY (producto_id, marca_id),
    FOREIGN KEY (producto_id) REFERENCES producto(id),
    FOREIGN KEY (marca_id) REFERENCES marca(id)
);

CREATE TABLE ingrediente (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL
);

CREATE TABLE producto_ingrediente (
    producto_id BIGINT,
    ingrediente_id INT,
    PRIMARY KEY (producto_id, ingrediente_id),
    FOREIGN KEY (producto_id) REFERENCES producto(id),
    FOREIGN KEY (ingrediente_id) REFERENCES ingrediente(id)
);
