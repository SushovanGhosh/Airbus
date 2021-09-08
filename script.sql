create database airbus_challenge;
use airbus_challenge;

DROP TABLE IF EXISTS `product_details`;

CREATE TABLE `product_details` (
  `product_id` varchar(255) NOT NULL,
  `product_category` varchar(255) DEFAULT NULL,
  `product_name` varchar(255) DEFAULT NULL,
  `product_description` varchar(255) DEFAULT NULL,
  `units
  ` int DEFAULT 0,
   PRIMARY KEY (`product_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;