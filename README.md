# InfinityParfum

InfinityParfum es una aplicación basada en microservicios diseñada para gestionar una tienda de perfumes. La arquitectura está compuesta por 5 microservicios que trabajan de manera independiente pero colaborativa para ofrecer funcionalidades como gestión de usuarios, roles, pedidos, pagos y más.

## Tabla de Contenidos
- [Descripción General](#descripción-general)
- [Arquitectura de Microservicios](#arquitectura-de-microservicios)
- [Requisitos Previos](#requisitos-previos)
- [Instalación](#instalación)
- [Uso](#uso)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [Contribuir](#contribuir)
- [Licencia](#licencia)

---

## Descripción General

InfinityParfum utiliza una arquitectura de microservicios para dividir las responsabilidades en módulos independientes. Cada microservicio tiene su propia base de datos y lógica de negocio, lo que permite una mayor escalabilidad y mantenimiento.

---

## Arquitectura de Microservicios

La aplicación está compuesta por los siguientes microservicios:

1. **Usuario**:
   - Gestiona los usuarios y sus roles.
   - Proporciona autenticación y autorización.
   - Endpoints principales:
     - `GET /usuarios`
     - `POST /usuarios`
     - `PUT /usuarios/{id}/desactivar`

2. **Roles**:
   - Gestiona los roles que pueden asignarse a los usuarios.
   - Relación directa con el microservicio de Usuario.
   - Endpoints principales:
     - `GET /roles`
     - `POST /roles`

3. **Pedidos**:
   - Gestiona los pedidos realizados por los usuarios.
   - Permite crear, actualizar y consultar pedidos.
   - Endpoints principales:
     - `GET /pedidos`
     - `POST /pedidos`

4. **Pagos**:
   - Gestiona los pagos asociados a los pedidos.
   - Proporciona integración con métodos de pago.
   - Endpoints principales:
     - `GET /pagos`
     - `POST /pagos`

5. **Productos**:
   - Gestiona el catálogo de productos disponibles en la tienda.
   - Permite consultar y actualizar información de productos.
   - Endpoints principales:
     - `GET /productos`
     - `POST /productos`

Cada microservicio tiene su propia base de datos y se comunica con los demás a través de API RESTful.

---

## Requisitos Previos

Antes de comenzar, asegúrate de tener instalado lo siguiente:

- **Java 17** o superior.
- **Maven** para gestionar dependencias.
- **MySQL** para las bases de datos de los microservicios.
- Un IDE como **IntelliJ IDEA** o **VS Code**.
- **Postman** o **cURL** para probar los endpoints.

---

## Instalación

Sigue estos pasos para configurar los microservicios en tu máquina local:

1. Clona el repositorio:
   ```bash
   git clone https://github.com/MGuzMabre/InfinityParfum.git
   cd InfinityParfum
