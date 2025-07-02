-- Fix database schema for Mascota and related tables
-- This script addresses the most common issues causing 500 errors

-- 1. Fix the dueno table structure
DROP TABLE IF EXISTS `dueno`;

CREATE TABLE `dueno` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `id_usuario` BIGINT NOT NULL,
    FOREIGN KEY (`id_usuario`) REFERENCES `usuario`(`id`)
);

-- 2. Fix the mascota table structure
DROP TABLE IF EXISTS `mascota`;

CREATE TABLE `mascota` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `nombre` VARCHAR(255) NOT NULL,
    `especie` VARCHAR(255),
    `raza` VARCHAR(255),
    `edad` INT,
    `id_dueno` BIGINT NOT NULL,
    `id_clinica` BIGINT NOT NULL,
    `id_usuario` BIGINT NOT NULL,
    FOREIGN KEY (`id_dueno`) REFERENCES `dueno`(`id`),
    FOREIGN KEY (`id_clinica`) REFERENCES `clinica_veterinaria`(`id`),
    FOREIGN KEY (`id_usuario`) REFERENCES `usuario`(`id`)
);

-- 3. Fix the detalle_cita table costo column
ALTER TABLE `detalle_cita` MODIFY COLUMN `costo` DECIMAL(10,2);

-- 4. Insert some test data if tables are empty
INSERT IGNORE INTO `usuario` (`id`, `nombres`, `apellidos`, `dni`, `celular`, `fecha_nacimiento`, `fecha_registro`) 
VALUES (1, 'Test', 'User', '12345678', '123456789', '1990-01-01', NOW());

INSERT IGNORE INTO `dueno` (`id`, `id_usuario`) 
VALUES (1, 1);

-- Note: You may need to adjust the clinica_veterinaria and veterinario data based on your existing data 