-- Fix database schema for clinica_veterinaria table
-- Add missing id_veterinario column

ALTER TABLE `clinica_veterinaria` 
ADD COLUMN `id_veterinario` BIGINT NOT NULL;

-- Add foreign key constraint
ALTER TABLE `clinica_veterinaria` 
ADD CONSTRAINT `fk_clinica_veterinario` 
FOREIGN KEY (`id_veterinario`) REFERENCES `veterinario`(`id`);

-- If you need to update existing records, you can set a default value
-- UPDATE `clinica_veterinaria` SET `id_veterinario` = 1 WHERE `id_veterinario` IS NULL; 