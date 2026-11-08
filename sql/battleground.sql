-- l2paulera: tabelas do motor de battlegrounds
-- Pontos de rank por personagem e por tipo de evento. O rank geral e a soma dos modos.
-- O que se gasta na loja sai da carteira (bg_wallet); o rank nunca diminui.

CREATE TABLE IF NOT EXISTS `bg_rank` (
  `char_id` INT UNSIGNED NOT NULL,
  `mode` VARCHAR(16) NOT NULL,
  `points` INT NOT NULL DEFAULT 0 COMMENT 'pontos de rank ganhos neste modo',
  `matches` INT NOT NULL DEFAULT 0,
  `wins` INT NOT NULL DEFAULT 0,
  `losses` INT NOT NULL DEFAULT 0,
  `kills` INT NOT NULL DEFAULT 0,
  `deaths` INT NOT NULL DEFAULT 0,
  PRIMARY KEY (`char_id`, `mode`),
  KEY `idx_mode_points` (`mode`, `points`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `bg_match` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `mode` VARCHAR(16) NOT NULL,
  `ended_at` DATETIME NOT NULL,
  `players` INT NOT NULL,
  `result` VARCHAR(64) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_mode_time` (`mode`, `ended_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `bg_wallet` (
  `char_id` INT UNSIGNED NOT NULL,
  `balance` INT NOT NULL DEFAULT 0 COMMENT 'pontos disponiveis para a loja',
  PRIMARY KEY (`char_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `bg_shop_log` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `char_id` INT UNSIGNED NOT NULL,
  `item_id` INT NOT NULL,
  `count` BIGINT NOT NULL,
  `price` INT NOT NULL,
  `bought_at` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_char` (`char_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
