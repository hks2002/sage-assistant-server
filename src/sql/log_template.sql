-- --------------------------------------------------------
-- 主机:                           192.168.0.246
-- 服务器版本:                        8.0.36 - Source distribution
-- 服务器操作系统:                      Linux
-- HeidiSQL 版本:                  12.7.0.6850
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

-- 导出  表 sageassistant.log_template 结构
CREATE TABLE IF NOT EXISTS `log_template` (
  `id` int NOT NULL AUTO_INCREMENT,
  `template_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `template_group` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `template_definition` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `template_definition_en` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `template_definition_zh` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `create_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `create_by` int DEFAULT NULL,
  `update_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_by` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 正在导出表  sageassistant.log_template 的数据：~8 rows (大约)
INSERT INTO `log_template` (`id`, `template_code`, `template_group`, `template_definition`, `template_definition_en`, `template_definition_zh`, `create_at`, `create_by`, `update_at`, `update_by`) VALUES
	(1, 'LOGIN_SUCCESS', 'SYSTEM', 'User {0} login success.', 'User {0} login success.', '用户{0}登录成功。', '2024-06-06 15:43:22', NULL, '2024-06-06 15:43:22', NULL),
	(2, 'LOGIN_FAILED', 'SYSTEM', 'User {0} login failed.', 'User {0} login failed.', '用户{0}登录失败。', '2024-06-24 18:07:33', NULL, '2024-06-24 18:07:33', NULL),
	(3, 'LOGOUT_SUCCESS', 'SYSTEM', 'User {0} logout success.', 'User {0} logout success.', '用户{0}注销成功。', '2024-06-25 12:15:11', NULL, '2024-06-25 12:15:11', NULL),
	(4, 'DOC_ACCESS_SUCCESS', 'WEBDAV', 'User {0} access {1} success.', 'User {0} access {1} success.', '用户{0}访问{1}成功。', '2024-06-24 18:03:18', NULL, '2024-06-24 18:03:18', NULL),
	(5, 'DOC_ACCESS_FAILED', 'WEBDAV', 'User {0} access {1} failed.', 'User {0} access {1} failed.', '用户{0}访问{1}失败。', '2024-06-24 18:05:30', NULL, '2024-06-24 18:05:30', NULL),
	(6, 'DOC_ACCESS_INIT_SUCCESS', 'WEBDAV', 'User {0} doc access initializing success.', 'User {0} doc access initializing success.', '用户{0}访问权限初始化成功。', '2024-06-24 18:10:24', NULL, '2024-06-24 18:10:24', NULL),
	(7, 'DOC_ACCESS_INIT_FAILED', 'WEBDAV', 'User {0} doc access initializing failed.', 'User {0} doc access initializing failed.', '用户{0}访问权限初始化失败。', '2024-06-24 18:10:45', NULL, '2024-06-24 18:10:45', NULL),
	(8, 'DOC_AUTO_DOWNLOAD', 'SYSTEM', 'Auto download {0} from Dms server to {1}.', 'Auto download {0} from Dms server to {1}.', '自动从服务器下载{0}到{1}。', '2024-07-03 02:12:22', NULL, '2024-07-03 02:12:22', NULL);

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
