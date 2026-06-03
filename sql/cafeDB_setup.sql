-- ============================================================
--  Java & Bean Café — Database Setup Script
--  Run: mysql -u root -p < cafeDB_setup.sql
-- ============================================================

CREATE DATABASE IF NOT EXISTS cafeDB
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE cafeDB;

-- ── Users ────────────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS users (
  id         INT          NOT NULL AUTO_INCREMENT,
  username   VARCHAR(50)  NOT NULL,
  email      VARCHAR(120) NOT NULL,
  password   VARCHAR(255) NOT NULL,   -- stores salt$hash (see PasswordUtil.java)
  created_at DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  UNIQUE KEY uq_username (username),
  UNIQUE KEY uq_email    (email)
) ENGINE=InnoDB;

-- ── Menu ─────────────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS menu (
  id          INT           NOT NULL AUTO_INCREMENT,
  item_name   VARCHAR(100)  NOT NULL,
  description VARCHAR(255),
  price       DECIMAL(8,2)  NOT NULL,
  emoji       VARCHAR(10)   DEFAULT '☕',
  PRIMARY KEY (id)
) ENGINE=InnoDB;

-- ── Orders ───────────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS orders (
  id         INT           NOT NULL AUTO_INCREMENT,
  user_id    INT           NOT NULL,
  total      DECIMAL(10,2) NOT NULL,
  created_at DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- ── Order Items ──────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS order_items (
  id        INT NOT NULL AUTO_INCREMENT,
  order_id  INT NOT NULL,
  menu_id   INT NOT NULL,
  quantity  INT NOT NULL DEFAULT 1,
  PRIMARY KEY (id),
  FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
  FOREIGN KEY (menu_id)  REFERENCES menu(id)
) ENGINE=InnoDB;

-- ── Sample Menu Data ─────────────────────────────────────────
INSERT INTO menu (item_name, description, price, emoji) VALUES
  ('Espresso',         'Bold, concentrated single shot of pure coffee',         65.00,  '☕'),
  ('Americano',        'Espresso diluted with hot water, clean and strong',      75.00,  '☕'),
  ('Cappuccino',       'Equal parts espresso, steamed milk, and foam',           95.00,  '☕'),
  ('Café Latte',       'Smooth espresso with lots of creamy steamed milk',       105.00, '🥛'),
  ('Caramel Macchiato','Vanilla-flavored latte with caramel drizzle',            115.00, '🍮'),
  ('Mocha',            'Espresso, chocolate syrup, and steamed milk',            110.00, '🍫'),
  ('Matcha Latte',     'Ceremonial-grade matcha with oat or whole milk',         120.00, '🍵'),
  ('Iced Coffee',      'Cold-brewed coffee over ice with your choice of milk',   85.00,  '🧊'),
  ('Strawberry Smoothie','Fresh strawberries blended with yogurt and honey',     110.00, '🍓'),
  ('Classic Milk Tea', 'Black tea with milk, brown sugar, and tapioca pearls',   95.00,  '🧋'),
  ('Croissant',        'Buttery, flaky all-butter French croissant',             75.00,  '🥐'),
  ('Chocolate Donut',  'Fluffy donut glazed with rich dark chocolate',           55.00,  '🍩');

-- ── Verify ───────────────────────────────────────────────────
SELECT CONCAT('✅ ', COUNT(*), ' menu items loaded') AS status FROM menu;
