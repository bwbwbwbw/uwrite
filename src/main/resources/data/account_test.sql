SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

BEGIN;
INSERT INTO `account` VALUES ('1', 'default.png', 'test@example.com', 'testaccount', '$2a$10$6CGH7L3AvkX8imAn9bvDMu7/SxY8K8FVK0NWMXBGMoK6PFFm/w6qq', 'ROLE_ADMIN');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
