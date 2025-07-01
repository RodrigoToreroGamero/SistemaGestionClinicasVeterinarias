-- Fix veterinario table structure
-- Add missing columns to match the JPA entity

-- First, check if the table exists and drop it if it has wrong structure
DROP TABLE IF EXISTS `veterinario`;

-- Create the veterinario table with proper structure
CREATE TABLE `veterinario` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `numero_colegio_medico` VARCHAR(255),
    `especialidad` VARCHAR(255),
    `horario_laboral` VARCHAR(255),
    `id_usuario` BIGINT NOT NULL,
    FOREIGN KEY (`id_usuario`) REFERENCES `usuario`(`id`)
);

-- If you need to preserve existing data, you can use this alternative approach:
-- ALTER TABLE `veterinario` ADD COLUMN `id` BIGINT AUTO_INCREMENT PRIMARY KEY FIRST;
-- ALTER TABLE `veterinario` ADD COLUMN `numero_colegio_medico` VARCHAR(255);
-- ALTER TABLE `veterinario` ADD COLUMN `especialidad` VARCHAR(255);
-- ALTER TABLE `veterinario` ADD COLUMN `horario_laboral` VARCHAR(255);
-- ALTER TABLE `veterinario` ADD COLUMN `id_usuario` BIGINT NOT NULL;
-- ALTER TABLE `veterinario` ADD CONSTRAINT `fk_veterinario_usuario` FOREIGN KEY (`id_usuario`) REFERENCES `usuario`(`id`); 