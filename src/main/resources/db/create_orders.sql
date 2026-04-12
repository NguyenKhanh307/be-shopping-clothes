-- MySQL structure based on Dump20260411 

-- Chọn database trước khi chạy (Nếu database của bạn tên khác, hãy đổi lại)
USE defaultdb;

-- 1. Table structure for table `orders`
CREATE TABLE IF NOT EXISTS `orders` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `customer_id` int unsigned NOT NULL,
  `order_number` varchar(50) NOT NULL COMMENT 'Mã đơn hàng hiển thị, vd: ORD-20250424-001',
  `total_amount` decimal(12,2) NOT NULL,
  `status` enum('pending','processing','shipped','delivered','cancelled') NOT NULL DEFAULT 'pending',
  `shipping_address` text NOT NULL,
  `note` text,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `order_number` (`order_number`),
  KEY `idx_orders_customer` (`customer_id`),
  KEY `idx_orders_status` (`status`),
  CONSTRAINT `orders_user_fk` FOREIGN KEY (`customer_id`) REFERENCES `users` (`id`) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Đơn hàng';

-- 2. Table structure for table `order_items`
CREATE TABLE IF NOT EXISTS `order_items` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `order_id` int unsigned NOT NULL,
  `product_id` int unsigned NOT NULL,
  `quantity` int unsigned NOT NULL DEFAULT '1',
  `unit_price` decimal(12,2) NOT NULL COMMENT 'Giá tại thời điểm đặt hàng',
  `color` varchar(10) DEFAULT NULL COMMENT 'Màu đã chọn (hex)',
  PRIMARY KEY (`id`),
  KEY `idx_items_order` (`order_id`),
  KEY `idx_items_product` (`product_id`),
  CONSTRAINT `order_items_fk_order` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`) ON DELETE CASCADE,
  CONSTRAINT `order_items_fk_product` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Chi tiết đơn hàng';

-- 3. Sample data for user ID 7
INSERT INTO `orders` (`id`, `customer_id`, `order_number`, `total_amount`, `status`, `shipping_address`, `note`, `created_at`) VALUES 
(101, 7, 'ORD-20260412-001', 160.00, 'delivered', '180 Cao Lỗ, Phường 4, Quận 8, TP. HCM', 'Giao tối sau 18h', '2026-04-12 10:00:00'),
(102, 7, 'ORD-20260412-002', 89.00, 'shipped', '180 Cao Lỗ, Phường 4, Quận 8, TP. HCM', NULL, '2026-04-12 11:30:00'),
(103, 7, 'ORD-20260412-003', 40.00, 'pending', '180 Cao Lỗ, Phường 4, Quận 8, TP. HCM', 'Đóng gói quà tặng', '2026-04-12 15:45:00');

-- 4. Sample items for user ID 7 orders
INSERT INTO `order_items` (`order_id`, `product_id`, `quantity`, `unit_price`, `color`) VALUES 
(101, 1, 1, 40.00, '#1C58F2'),
(101, 7, 1, 120.00, NULL),
(102, 9, 1, 89.00, NULL),
(103, 4, 1, 40.00, '#000000');

