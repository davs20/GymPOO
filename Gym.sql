-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost
-- Tiempo de generación: 05-12-2017 a las 17:30:17
-- Versión del servidor: 10.1.28-MariaDB
-- Versión de PHP: 7.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `Gym`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Asistencia`
--

CREATE TABLE `Asistencia` (
  `fecha` date NOT NULL,
  `Cliente_idCliente` varchar(13) NOT NULL,
  `diasRestantes` varchar(45) DEFAULT NULL,
  `hora` varchar(45) DEFAULT NULL,
  `idAsistencia` varchar(45) DEFAULT NULL,
  `Usuarios_idUsuarios` varchar(13) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Disparadores `Asistencia`
--
DELIMITER $$
CREATE TRIGGER `Asistencia_BEFORE_INSERT` BEFORE INSERT ON `Asistencia` FOR EACH ROW BEGIN

END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Cliente`
--

CREATE TABLE `Cliente` (
  `idCliente` varchar(13) NOT NULL,
  `fechaNacimiento` date DEFAULT NULL,
  `nombre` varchar(45) DEFAULT NULL,
  `apellido` varchar(45) DEFAULT NULL,
  `telefono` varchar(45) DEFAULT NULL,
  `Membresia_idMembresia` int(11) NOT NULL,
  `Clientecol` varchar(45) DEFAULT NULL,
  `edad` int(11) DEFAULT NULL,
  `Enfermedades_idEnfermedades` int(11) DEFAULT NULL,
  `peso` float DEFAULT NULL,
  `fuerza` float DEFAULT NULL,
  `masaMuscular` varchar(45) DEFAULT NULL,
  `correo` varchar(60) DEFAULT NULL,
  `direccion` text,
  `altura` float DEFAULT NULL,
  `foto` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `Cliente`
--

INSERT INTO `Cliente` (`idCliente`, `fechaNacimiento`, `nombre`, `apellido`, `telefono`, `Membresia_idMembresia`, `Clientecol`, `edad`, `Enfermedades_idEnfermedades`, `peso`, `fuerza`, `masaMuscular`, `correo`, `direccion`, `altura`, `foto`) VALUES
('0101199701719', '2017-12-14', 'franco', 'escamilla', '99913215', 1, NULL, 33, 1, 123, 34324, '123', 'francoelfathr@infiel.com', NULL, 22, NULL),
('0104199201816', '2017-12-20', 'dsd', 'sdasd', 'sdasd', 1, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
('0104199601519', '2017-11-23', 'yarince', 'Padillá', '312312312', 1, 'dasda', 22, 1, 245.56, 23.45, 'dasdas', NULL, NULL, NULL, NULL),
('0105199403415', '2017-11-04', 'Tulio', 'Garcia', '4444', 1, '43', 88, 1, 444, 333, '434', 'hola.com', 'fadf', 23.45, 'hello-world.png'),
('0201199503412', '2017-11-10', 'holis', 'holis', NULL, 1, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Computada`
--

CREATE TABLE `Computada` (
  `computadoraId` int(11) NOT NULL,
  `nombre` varchar(45) DEFAULT NULL,
  `marca` varchar(45) DEFAULT NULL,
  `modelo` varchar(45) DEFAULT NULL,
  `PuntoVenta_CAI` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Enfermedades`
--

CREATE TABLE `Enfermedades` (
  `idEnfermedades` int(11) NOT NULL,
  `nombre` varchar(45) DEFAULT NULL,
  `impedimiento` varchar(45) DEFAULT NULL,
  `cuidado` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `Enfermedades`
--

INSERT INTO `Enfermedades` (`idEnfermedades`, `nombre`, `impedimiento`, `cuidado`) VALUES
(1, 'ella', 'intranquilidad', 'no ver sus mensajes durante el ejercicio');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Factura`
--

CREATE TABLE `Factura` (
  `idVenta` int(11) NOT NULL,
  `fecha` date DEFAULT NULL,
  `Sucursal_idSucursal` int(11) NOT NULL,
  `PuntoVenta_idPuntoVenta` int(11) NOT NULL,
  `subtotal` double DEFAULT NULL,
  `cambio` double DEFAULT NULL,
  `efectivo` double DEFAULT NULL,
  `Cliente_idCliente` varchar(13) NOT NULL,
  `CAEE` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `privilegio`
--

CREATE TABLE `privilegio` (
  `idPrivilegio` int(11) NOT NULL,
  `privilegiocol` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `privilegio`
--

INSERT INTO `privilegio` (`idPrivilegio`, `privilegiocol`) VALUES
(1, 'Gerente'),
(2, 'Cajero');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `PuntoEmision`
--

CREATE TABLE `PuntoEmision` (
  `CAI` int(11) NOT NULL,
  `fechaLimite` date DEFAULT NULL,
  `fechaEmision` date DEFAULT NULL,
  `Sucursal_idSucursal` varchar(45) DEFAULT NULL,
  `Sucursal_direccion` varchar(45) DEFAULT NULL,
  `PuntoEmisioncol` varchar(45) DEFAULT NULL,
  `Usuarios_idUsuarios` varchar(13) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Seguimiento`
--

CREATE TABLE `Seguimiento` (
  `Entrenador_idEntrenador` varchar(13) NOT NULL,
  `Cliente_idCliente` varchar(13) NOT NULL,
  `peso` double DEFAULT NULL,
  `porcentajeGrasa` decimal(3,0) DEFAULT NULL,
  `altura` double DEFAULT NULL,
  `Usuarios_idUsuarios` varchar(13) NOT NULL,
  `fecha` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Servicio`
--

CREATE TABLE `Servicio` (
  `idMembresia` int(11) NOT NULL,
  `nombre` varchar(45) DEFAULT NULL,
  `descripcion` varchar(45) DEFAULT NULL,
  `duracion` double DEFAULT NULL,
  `precio` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `Servicio`
--

INSERT INTO `Servicio` (`idMembresia`, `nombre`, `descripcion`, `duracion`, `precio`) VALUES
(1, 'Basico', 'dasdasd', 12.43, 500.3);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Usuarios`
--

CREATE TABLE `Usuarios` (
  `idUsuarios` varchar(13) NOT NULL,
  `contrasena` varchar(45) DEFAULT NULL,
  `intentos` int(11) DEFAULT NULL,
  `nombres` varchar(45) DEFAULT NULL,
  `apellidos` varchar(45) DEFAULT NULL,
  `token` varchar(45) DEFAULT NULL,
  `correo` varchar(45) DEFAULT NULL,
  `telefono` varchar(45) DEFAULT NULL,
  `privilegio_idPrivilegio` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `Usuarios`
--

INSERT INTO `Usuarios` (`idUsuarios`, `contrasena`, `intentos`, `nombres`, `apellidos`, `token`, `correo`, `telefono`, `privilegio_idPrivilegio`) VALUES
('0101199702515', '09dejuliod', 0, 'David Fernando', 'Delcid Rivera', 'dasdasd', 'franando14@gmail.com', '993413121', 1),
('0104199402413', '09dejuliod', 0, 'Fernando Alonzo', 'Zapata Martinez', 'dasdasd', 'franando14@gmail.com', '312312312', 2);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `Asistencia`
--
ALTER TABLE `Asistencia`
  ADD PRIMARY KEY (`Cliente_idCliente`),
  ADD KEY `fk_Asistencia_Cliente1_idx` (`Cliente_idCliente`),
  ADD KEY `fk_Asistencia_Usuarios1_idx` (`Usuarios_idUsuarios`);

--
-- Indices de la tabla `Cliente`
--
ALTER TABLE `Cliente`
  ADD PRIMARY KEY (`idCliente`),
  ADD KEY `fk_Cliente_Membresia1_idx` (`Membresia_idMembresia`),
  ADD KEY `fk_Cliente_Enfermedades1_idx` (`Enfermedades_idEnfermedades`);

--
-- Indices de la tabla `Computada`
--
ALTER TABLE `Computada`
  ADD PRIMARY KEY (`computadoraId`),
  ADD KEY `fk_table2_PuntoVenta1_idx` (`PuntoVenta_CAI`);

--
-- Indices de la tabla `Enfermedades`
--
ALTER TABLE `Enfermedades`
  ADD PRIMARY KEY (`idEnfermedades`);

--
-- Indices de la tabla `Factura`
--
ALTER TABLE `Factura`
  ADD PRIMARY KEY (`idVenta`,`Sucursal_idSucursal`,`PuntoVenta_idPuntoVenta`,`CAEE`),
  ADD KEY `fk_Venta_PuntoVenta1_idx` (`PuntoVenta_idPuntoVenta`),
  ADD KEY `fk_Venta_Cliente1_idx` (`Cliente_idCliente`);

--
-- Indices de la tabla `privilegio`
--
ALTER TABLE `privilegio`
  ADD PRIMARY KEY (`idPrivilegio`);

--
-- Indices de la tabla `PuntoEmision`
--
ALTER TABLE `PuntoEmision`
  ADD PRIMARY KEY (`CAI`),
  ADD KEY `fk_PuntoEmision_Usuarios1_idx` (`Usuarios_idUsuarios`);

--
-- Indices de la tabla `Seguimiento`
--
ALTER TABLE `Seguimiento`
  ADD PRIMARY KEY (`Entrenador_idEntrenador`,`Cliente_idCliente`),
  ADD KEY `fk_Entrenador_has_Cliente_Cliente1_idx` (`Cliente_idCliente`),
  ADD KEY `fk_Seguimiento_Usuarios1_idx` (`Usuarios_idUsuarios`);

--
-- Indices de la tabla `Servicio`
--
ALTER TABLE `Servicio`
  ADD PRIMARY KEY (`idMembresia`);

--
-- Indices de la tabla `Usuarios`
--
ALTER TABLE `Usuarios`
  ADD PRIMARY KEY (`idUsuarios`),
  ADD KEY `fk_Usuarios_privilegio1_idx` (`privilegio_idPrivilegio`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `privilegio`
--
ALTER TABLE `privilegio`
  MODIFY `idPrivilegio` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `Asistencia`
--
ALTER TABLE `Asistencia`
  ADD CONSTRAINT `fk_Asistencia_Cliente1` FOREIGN KEY (`Cliente_idCliente`) REFERENCES `Cliente` (`idCliente`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Asistencia_Usuarios1` FOREIGN KEY (`Usuarios_idUsuarios`) REFERENCES `Usuarios` (`idUsuarios`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `Cliente`
--
ALTER TABLE `Cliente`
  ADD CONSTRAINT `fk_Cliente_Enfermedades1` FOREIGN KEY (`Enfermedades_idEnfermedades`) REFERENCES `Enfermedades` (`idEnfermedades`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Cliente_Membresia1` FOREIGN KEY (`Membresia_idMembresia`) REFERENCES `Servicio` (`idMembresia`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `Computada`
--
ALTER TABLE `Computada`
  ADD CONSTRAINT `fk_table2_PuntoVenta1` FOREIGN KEY (`PuntoVenta_CAI`) REFERENCES `PuntoEmision` (`CAI`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `Factura`
--
ALTER TABLE `Factura`
  ADD CONSTRAINT `fk_Venta_Cliente1` FOREIGN KEY (`Cliente_idCliente`) REFERENCES `Cliente` (`idCliente`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Venta_PuntoVenta1` FOREIGN KEY (`PuntoVenta_idPuntoVenta`) REFERENCES `PuntoEmision` (`CAI`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `PuntoEmision`
--
ALTER TABLE `PuntoEmision`
  ADD CONSTRAINT `fk_PuntoEmision_Usuarios1` FOREIGN KEY (`Usuarios_idUsuarios`) REFERENCES `Usuarios` (`idUsuarios`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `Seguimiento`
--
ALTER TABLE `Seguimiento`
  ADD CONSTRAINT `fk_Entrenador_has_Cliente_Cliente1` FOREIGN KEY (`Cliente_idCliente`) REFERENCES `Cliente` (`idCliente`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Seguimiento_Usuarios1` FOREIGN KEY (`Usuarios_idUsuarios`) REFERENCES `Usuarios` (`idUsuarios`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `Usuarios`
--
ALTER TABLE `Usuarios`
  ADD CONSTRAINT `fk_Usuarios_privilegio1` FOREIGN KEY (`privilegio_idPrivilegio`) REFERENCES `privilegio` (`idPrivilegio`) ON DELETE NO ACTION ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
