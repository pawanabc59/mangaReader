-- phpMyAdmin SQL Dump
-- version 4.9.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Sep 27, 2019 at 07:13 AM
-- Server version: 10.4.6-MariaDB
-- PHP Version: 7.3.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `manga`
--

-- --------------------------------------------------------

--
-- Table structure for table `chapters`
--

CREATE TABLE `chapters` (
  `chapter_id` int(10) NOT NULL,
  `manga_id` int(10) DEFAULT NULL,
  `title` varchar(100) DEFAULT NULL,
  `date_of_origin` date DEFAULT NULL,
  `chapter_path` varchar(100) DEFAULT NULL,
  `chapter_no` int(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `chapters`
--

INSERT INTO `chapters` (`chapter_id`, `manga_id`, `title`, `date_of_origin`, `chapter_path`, `chapter_no`) VALUES
(1, 1, 'Romance Dawn', '2019-08-15', '/chapters/one_piece/one_piece_1.pdf', 1),
(2, 1, 'That boy \r\nThe straw hat wearing Luffy', '2019-08-02', '/chapters/one_piece/one_piece_2.pdf', 2),
(3, 1, 'Pirate hunter zoro enters', '2019-08-02', '/chapters/one_piece/one_piece_3.pdf', 3),
(4, 1, 'Marine captain \'Axe arm Morgan\'', '2019-08-02', '/chapters/one_piece/one_piece_4.pdf', 4),
(5, 1, 'The pirate king and the great sowrdsman', '2019-08-02', '/chapters/one_piece/one_piece_5.pdf', 5),
(6, 1, 'The first crew member', '2019-08-02', '/chapters/one_piece/one_piece_6.pdf', 6),
(7, 1, 'Friends', '2019-08-02', '/chapters/one_piece/one_piece_7.pdf', 7),
(8, 1, 'Nami enters', '2019-08-02', '/chapters/one_piece/one_piece_8.pdf', 8),
(9, 1, 'Evil woman', '2019-08-02', '/chapters/one_piece/one_piece_9.pdf', 9),
(10, 1, 'What happened at the bar', '2019-08-02', '/chapters/one_piece/one_piece_10.pdf', 10),
(11, 1, 'Flee', '2019-08-02', '/chapters/one_piece/one_piece_11.pdf', 11),
(12, 1, 'The Dog', '2019-08-02', '/chapters/one_piece/one_piece_12.pdf', 12),
(13, 1, 'Treasure', '2019-08-02', '/chapters/one_piece/one_piece_13.pdf', 13),
(14, 1, 'Reckless!!', '2019-08-02', '/chapters/one_piece/one_piece_14.pdf', 14),
(15, 1, 'Gong', '2019-08-02', '/chapters/one_piece/one_piece_15.pdf', 15),
(16, 3, '2000 Years ago', '2019-09-27', '/chapters/aot/aot1.pdf', 1),
(17, 3, 'On that day', '2019-09-27', '/chapters/aot/aot2.pdf', 2),
(18, 3, 'The Night of disbanding', '2019-09-27', '/chapters/aot/aot3.pdf', 3),
(19, 3, 'Their first battle', '2019-09-27', '/chapters/aot/aot4.pdf', 4),
(20, 3, 'The world of titans', '2019-09-27', '/chapters/aot/aot5.pdf', 5);

-- --------------------------------------------------------

--
-- Table structure for table `favourites`
--

CREATE TABLE `favourites` (
  `email_id` varchar(30) DEFAULT NULL,
  `manga_id` int(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `favourites`
--

INSERT INTO `favourites` (`email_id`, `manga_id`) VALUES
('pawankm4587@gmail.com', 1),
('deepak@gmail.com', 1),
('deepak@gmail.com', 2),
('pawankm4587@gmail.com', 4),
('pawankm4587@gmail.com', 3);

-- --------------------------------------------------------

--
-- Table structure for table `manga`
--

CREATE TABLE `manga` (
  `manga_id` int(10) NOT NULL,
  `title` varchar(30) DEFAULT NULL,
  `number_of_chapters` varchar(30) DEFAULT NULL,
  `author` varchar(30) DEFAULT NULL,
  `ratings` int(11) DEFAULT NULL,
  `ratings_count` int(11) DEFAULT NULL,
  `date_of_origin` date DEFAULT NULL,
  `last_release` date DEFAULT NULL,
  `cover_picture` varchar(100) DEFAULT NULL,
  `description` varchar(1100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `manga`
--

INSERT INTO `manga` (`manga_id`, `title`, `number_of_chapters`, `author`, `ratings`, `ratings_count`, `date_of_origin`, `last_release`, `cover_picture`, `description`) VALUES
(1, 'One Piece', '951', 'Echiro Oda', 5, 1000, '1998-04-10', '2019-08-03', '/covers/onepiece.jpeg', 'There once lived a pirate named Gol D. Roger. He obtained wealth, fame, and power to earn the title of Pirate King. When he was captured and about to be executed, he revealed that his treasure called One Piece was hidden somewhere at the Grand Line. This made all people set out to search and uncover the One Piece treasure, but no one ever found the location of Gol D. Roger\'s treasure, and the Grand Line was too dangerous a place to overcome. Twenty-two years after Gol D. Roger\'s death, a boy named Monkey D. Luffy decided to become a pirate and search for Gol D. Roger\'s treasure to become the next Pirate King.'),
(2, 'Hunter x hunter', '300', 'Togashi', 5, 500, '2019-08-04', '2019-08-07', '/covers/hxh.jpeg', 'Gon Freecss is a young boy living on Whale Island. He learns from \"Hunter\" Kite, that his father, who he was told was dead, is still alive somewhere as a top \"Hunter,\" risking his life to seek unknown items, such as hidden treasures, curiosa, exotic living creatures, etc. Gon decides to become a professional Hunter and leaves the island. To become a Hunter, he must pass the Hunter Examination, where he meets and befriends three other applicants: Kurapika, Leorio and Killua. Can Gon pass this formidable hurdle, the Hunter Examination, to become \"the Best Hunter in the World\" and eventually meet his father?'),
(3, 'Attack on Titan', '120', 'Hajime Isayama', 4, 600, '2018-10-09', '2019-08-01', '/covers/aot.jpeg', 'Humans are nearly exterminated by giant creatures called Titans. Titans are typically several stories tall, seem to have no intelligence, devour human beings and, worst of all, seem to do it for the pleasure rather than as a food source. A small percentage of humanity survived by walling themselves in a city protected by extremely high walls, even taller than the biggest of titans. Flash forward to the present and the city has not seen a titan in over 100 years. Teenage boy Eren and his foster sister Mikasa witness something horrific as the city walls are destroyed by a colossal titan that appears out of thin air. As the smaller titans flood the city, the two kids watch in horror as their mother is eaten alive. Eren vows that he will murder every single titan and take revenge for all of mankind.'),
(4, 'The Promised Neverland', '80', 'Kaiu Shirai', 4, 400, '2018-02-07', '2019-08-28', '/covers/tpn.jpeg', 'A group of the smartest kids at a seemingly perfect orphanage uncover its dark truth when they a break a rule to never leave the orphanage grounds. Once the truth is discovered, they begin to plan an escape to save all of the children.'),
(5, 'My Hero Academia', '330', 'Kohei Horikoshi', 5, 800, '2017-03-01', '2019-08-20', '/covers/mha.jpeg', 'In a world populated with superhumans, the superhero-loving Izuku Midoriya is without power. However, after the Quirkless dreamer Izuku inherits the powers of the world\'s best superhero, All Might, his hopes of becoming the top hero are now possible. Once enrolled in the high school for heroes, U.A., Izuku soon discovers being a hero is much more complicated than it appears');

-- --------------------------------------------------------

--
-- Table structure for table `recent`
--

CREATE TABLE `recent` (
  `email_id` varchar(30) DEFAULT NULL,
  `manga_id` int(10) DEFAULT NULL,
  `at_time` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `recent`
--

INSERT INTO `recent` (`email_id`, `manga_id`, `at_time`) VALUES
(NULL, 3, '2019-08-17 18:58:47'),
(NULL, 5, '2019-08-17 18:58:50'),
(NULL, 3, '2019-08-17 18:59:34'),
(NULL, 1, '2019-08-17 18:59:44'),
(NULL, 1, '2019-08-17 19:00:08'),
(NULL, 1, '2019-08-17 19:01:14'),
(NULL, 2, '2019-08-17 19:01:19'),
(NULL, 2, '2019-08-17 19:01:31'),
(NULL, 5, '2019-08-17 19:01:36'),
('pawankm4587@gmail.com', 1, '2019-08-17 19:04:09'),
('pawankm4587@gmail.com', 2, '2019-08-17 19:04:11'),
('pawankm4587@gmail.com', 3, '2019-08-17 19:04:33'),
('pawankm4587@gmail.com', 5, '2019-08-17 19:05:03'),
('pawankm4587@gmail.com', 1, '2019-08-17 19:07:02'),
('pawankm4587@gmail.com', 2, '2019-08-17 19:07:05'),
('pawankm4587@gmail.com', 1, '2019-08-17 19:08:51'),
('pawankm4587@gmail.com', 3, '2019-08-17 19:08:54'),
('pawankm4587@gmail.com', 1, '2019-08-17 19:10:01'),
('pawankm4587@gmail.com', 2, '2019-08-17 19:10:03'),
('pawankm4587@gmail.com', 1, '2019-08-19 11:39:27'),
('pawankm4587@gmail.com', 2, '2019-08-19 11:43:26'),
('pawankm4587@gmail.com', 1, '2019-08-19 11:50:35'),
('pawankm4587@gmail.com', 2, '2019-08-19 11:50:51'),
('pawankm4587@gmail.com', 5, '2019-08-19 11:51:02'),
('pawankm4587@gmail.com', 5, '2019-08-19 11:51:08'),
('pawankm4587@gmail.com', 1, '2019-08-19 11:51:25'),
('pawankm4587@gmail.com', 3, '2019-08-19 11:51:57'),
('pawankm4587@gmail.com', 1, '2019-08-19 11:52:07'),
('pawankm4587@gmail.com', 1, '2019-08-19 11:52:37'),
('pawankm4587@gmail.com', 1, '2019-08-19 11:57:57'),
('pawankm4587@gmail.com', 5, '2019-09-03 00:07:17'),
('pawankm4587@gmail.com', 1, '2019-09-03 00:07:19'),
('pawankm4587@gmail.com', 3, '2019-09-03 00:07:29'),
('pawankm4587@gmail.com', 3, '2019-09-03 00:07:34'),
('pawankm4587@gmail.com', 1, '2019-09-10 09:27:37'),
('pawankm4587@gmail.com', 1, '2019-09-10 09:29:54'),
('pawankm4587@gmail.com', 1, '2019-09-10 09:29:56'),
('pawankm4587@gmail.com', 1, '2019-09-10 09:30:19'),
('pawankm4587@gmail.com', 1, '2019-09-10 09:30:21'),
('pawankm4587@gmail.com', 1, '2019-09-10 09:30:25'),
('pawankm4587@gmail.com', 1, '2019-09-10 09:30:28'),
('pawankm4587@gmail.com', 1, '2019-09-10 09:38:03'),
('pawankm4587@gmail.com', 1, '2019-09-10 09:39:48'),
('pawankm4587@gmail.com', 1, '2019-09-10 09:43:57'),
('pawankm4587@gmail.com', 1, '2019-09-10 10:23:51'),
('pawankm4587@gmail.com', 1, '2019-09-10 10:25:48'),
('pawankm4587@gmail.com', 1, '2019-09-10 10:27:20'),
('pawankm4587@gmail.com', 1, '2019-09-10 10:27:26'),
('pawankm4587@gmail.com', 1, '2019-09-10 10:27:34'),
('pawankm4587@gmail.com', 4, '2019-09-10 10:27:46'),
('pawankm4587@gmail.com', 4, '2019-09-10 10:27:53'),
('pawankm4587@gmail.com', 1, '2019-09-10 10:35:31'),
('pawankm4587@gmail.com', 1, '2019-09-10 10:38:47'),
('pawankm4587@gmail.com', 3, '2019-09-10 10:40:44'),
('pawankm4587@gmail.com', 3, '2019-09-10 10:41:11'),
('pawankm4587@gmail.com', 3, '2019-09-10 10:44:21'),
('pawankm4587@gmail.com', 3, '2019-09-10 10:44:29'),
('pawankm4587@gmail.com', 2, '2019-09-17 10:19:27'),
('pawankm4587@gmail.com', 2, '2019-09-17 10:19:33'),
('pawankm4587@gmail.com', 2, '2019-09-17 10:19:39'),
('pawankm4587@gmail.com', 1, '2019-09-17 10:19:41'),
('pawankm4587@gmail.com', 5, '2019-09-17 10:19:43'),
('pawankm4587@gmail.com', 5, '2019-09-17 10:19:44'),
('pawankm4587@gmail.com', 2, '2019-09-17 10:19:45'),
('pawankm4587@gmail.com', 5, '2019-09-17 10:19:46'),
('pawankm4587@gmail.com', 2, '2019-09-17 10:21:00'),
('pawankm4587@gmail.com', 1, '2019-09-17 10:21:16'),
('pawankm4587@gmail.com', 1, '2019-09-21 23:00:54'),
('pawankm4587@gmail.com', 1, '2019-09-22 14:20:06'),
('pawankm4587@gmail.com', 1, '2019-09-22 16:01:26'),
('pawankm4587@gmail.com', 1, '2019-09-22 16:03:25'),
('pawankm4587@gmail.com', 1, '2019-09-22 16:03:35'),
('pawankm4587@gmail.com', 1, '2019-09-22 16:06:44'),
('pawankm4587@gmail.com', 1, '2019-09-22 16:07:08'),
('pawankm4587@gmail.com', 1, '2019-09-22 16:07:57'),
('pawankm4587@gmail.com', 1, '2019-09-22 16:08:30'),
('pawankm4587@gmail.com', 1, '2019-09-22 16:10:25'),
('pawankm4587@gmail.com', 3, '2019-09-22 16:10:42'),
('pawankm4587@gmail.com', 1, '2019-09-22 16:12:15'),
('pawankm4587@gmail.com', 1, '2019-09-22 16:13:50'),
('pawankm4587@gmail.com', 1, '2019-09-22 16:17:13'),
('pawankm4587@gmail.com', 1, '2019-09-22 16:18:16'),
('pawankm4587@gmail.com', 1, '2019-09-22 16:18:40'),
('pawankm4587@gmail.com', 1, '2019-09-22 20:43:49'),
('pawankm4587@gmail.com', 1, '2019-09-22 20:43:55'),
('pawankm4587@gmail.com', 1, '2019-09-22 20:46:25'),
('pawankm4587@gmail.com', 1, '2019-09-22 20:51:35'),
('pawankm4587@gmail.com', 1, '2019-09-22 20:53:46'),
('pawankm4587@gmail.com', 1, '2019-09-22 20:54:54'),
('pawankm4587@gmail.com', 1, '2019-09-22 21:02:38'),
('pawankm4587@gmail.com', 1, '2019-09-22 21:37:50'),
('pawankm4587@gmail.com', 1, '2019-09-22 21:44:13'),
('pawankm4587@gmail.com', 1, '2019-09-22 22:15:48'),
('pawankm4587@gmail.com', 1, '2019-09-22 22:27:50'),
('pawankm4587@gmail.com', 3, '2019-09-22 22:28:02'),
('pawankm4587@gmail.com', 1, '2019-09-22 22:28:05'),
('pawankm4587@gmail.com', 1, '2019-09-22 22:28:19'),
('pawankm4587@gmail.com', 1, '2019-09-22 22:28:37'),
('pawankm4587@gmail.com', 4, '2019-09-22 22:28:42'),
('pawankm4587@gmail.com', 1, '2019-09-22 22:31:40'),
('pawankm4587@gmail.com', 1, '2019-09-22 22:50:19'),
('pawankm4587@gmail.com', 1, '2019-09-22 22:50:25'),
('pawankm4587@gmail.com', 1, '2019-09-23 00:13:53'),
('pawankm4587@gmail.com', 1, '2019-09-23 00:13:57'),
('pawankm4587@gmail.com', 2, '2019-09-23 00:14:09'),
('pawankm4587@gmail.com', 1, '2019-09-23 00:14:56'),
('pawankm4587@gmail.com', 1, '2019-09-23 00:22:54'),
('pawankm4587@gmail.com', 2, '2019-09-23 00:22:57'),
('pawankm4587@gmail.com', 1, '2019-09-23 00:28:20'),
('pawankm4587@gmail.com', 1, '2019-09-23 00:28:27'),
('pawankm4587@gmail.com', 1, '2019-09-23 00:37:38'),
('pawankm4587@gmail.com', 1, '2019-09-23 00:38:51'),
('pawankm4587@gmail.com', 1, '2019-09-23 00:39:06'),
('pawankm4587@gmail.com', 1, '2019-09-23 00:42:00'),
('pawankm4587@gmail.com', 1, '2019-09-23 00:42:05'),
('pawankm4587@gmail.com', 5, '2019-09-23 00:44:22'),
('pawankm4587@gmail.com', 1, '2019-09-23 11:27:14'),
('pawankm4587@gmail.com', 1, '2019-09-23 11:27:29'),
('pawankm4587@gmail.com', 1, '2019-09-23 11:29:01'),
('pawankm4587@gmail.com', 1, '2019-09-23 11:29:46'),
('pawankm4587@gmail.com', 1, '2019-09-23 11:31:24'),
('pawankm4587@gmail.com', 2, '2019-09-23 11:31:36'),
('pawankm4587@gmail.com', 1, '2019-09-23 11:31:39'),
('pawankm4587@gmail.com', 1, '2019-09-23 11:32:11'),
('pawankm4587@gmail.com', 1, '2019-09-23 11:33:45'),
('pawankm4587@gmail.com', 1, '2019-09-23 11:34:22'),
('pawankm4587@gmail.com', 1, '2019-09-23 11:35:05'),
('pawankm4587@gmail.com', 1, '2019-09-23 11:50:27'),
('pawankm4587@gmail.com', 1, '2019-09-23 14:20:25'),
('pawankm4587@gmail.com', 5, '2019-09-23 14:20:36'),
('pawankm4587@gmail.com', 1, '2019-09-23 14:33:54'),
('pawankm4587@gmail.com', 1, '2019-09-23 14:34:00'),
('pawankm4587@gmail.com', 1, '2019-09-23 14:46:45'),
('pawankm4587@gmail.com', 1, '2019-09-25 15:14:49'),
('pawankm4587@gmail.com', 1, '2019-09-25 15:14:54'),
('pawankm4587@gmail.com', 1, '2019-09-25 15:27:41'),
('pawankm4587@gmail.com', 5, '2019-09-25 21:24:38'),
('pawankm4587@gmail.com', 1, '2019-09-25 22:17:47'),
('pawankm4587@gmail.com', 1, '2019-09-25 22:18:12'),
('pawankm4587@gmail.com', 1, '2019-09-25 22:18:23'),
('pawankm4587@gmail.com', 1, '2019-09-25 22:19:43'),
('pawankm4587@gmail.com', 1, '2019-09-25 22:19:47'),
('pawankm4587@gmail.com', 2, '2019-09-25 22:20:01'),
('pawankm4587@gmail.com', 2, '2019-09-25 22:20:07'),
('pawankm4587@gmail.com', 5, '2019-09-26 09:45:23'),
('pawankm4587@gmail.com', 1, '2019-09-26 09:45:27'),
('pawankm4587@gmail.com', 5, '2019-09-26 09:45:57'),
('pawankm4587@gmail.com', 5, '2019-09-26 09:46:36'),
('pawankm4587@gmail.com', 5, '2019-09-26 09:46:41'),
('pawankm4587@gmail.com', 5, '2019-09-26 09:47:05'),
('pawankm4587@gmail.com', 3, '2019-09-26 09:47:09'),
('pawankm4587@gmail.com', 3, '2019-09-26 09:47:16'),
('pawankm4587@gmail.com', 2, '2019-09-26 11:21:38'),
(NULL, 1, '2019-09-26 11:52:15'),
(NULL, 1, '2019-09-26 11:52:50'),
(NULL, 1, '2019-09-26 11:53:02'),
(NULL, 1, '2019-09-26 11:53:08'),
(NULL, 4, '2019-09-26 11:54:38'),
(NULL, 5, '2019-09-26 11:54:42'),
(NULL, 2, '2019-09-26 11:55:02'),
('deepak@gmail.com', 1, '2019-09-26 11:56:17'),
('deepak@gmail.com', 2, '2019-09-26 11:57:09'),
('pawankm4587@gmail.com', 2, '2019-09-26 20:51:52'),
('pawankm4587@gmail.com', 2, '2019-09-26 20:52:02'),
('pawankm4587@gmail.com', 2, '2019-09-26 20:52:08'),
('pawankm4587@gmail.com', 2, '2019-09-26 20:52:11'),
('pawankm4587@gmail.com', 2, '2019-09-26 20:52:22'),
('pawankm4587@gmail.com', 2, '2019-09-26 20:53:06'),
('pawankm4587@gmail.com', 3, '2019-09-26 20:55:31'),
('pawankm4587@gmail.com', 3, '2019-09-26 20:55:35'),
('pawankm4587@gmail.com', 5, '2019-09-26 20:55:39'),
('pawankm4587@gmail.com', 3, '2019-09-26 20:55:54'),
('pawankm4587@gmail.com', 3, '2019-09-26 20:55:57'),
('pawankm4587@gmail.com', 3, '2019-09-26 20:56:01'),
('pawankm4587@gmail.com', 3, '2019-09-26 20:56:09'),
('pawankm4587@gmail.com', 3, '2019-09-26 20:56:12'),
('pawankm4587@gmail.com', 4, '2019-09-26 20:57:15'),
('pawankm4587@gmail.com', 4, '2019-09-26 20:57:26'),
('pawankm4587@gmail.com', 4, '2019-09-26 20:57:32'),
('pawankm4587@gmail.com', 4, '2019-09-26 20:57:42'),
('pawankm4587@gmail.com', 5, '2019-09-26 20:57:51'),
('pawankm4587@gmail.com', 5, '2019-09-26 20:58:02'),
('pawankm4587@gmail.com', 4, '2019-09-26 20:58:10'),
('pawankm4587@gmail.com', 4, '2019-09-26 20:58:18'),
('pawankm4587@gmail.com', 4, '2019-09-26 20:58:20'),
('pawankm4587@gmail.com', 2, '2019-09-26 20:58:55'),
('pawankm4587@gmail.com', 2, '2019-09-26 21:00:42'),
('pawankm4587@gmail.com', 2, '2019-09-26 21:00:46'),
('pawankm4587@gmail.com', 1, '2019-09-27 10:08:14'),
('pawankm4587@gmail.com', 3, '2019-09-27 10:08:28'),
('pawankm4587@gmail.com', 2, '2019-09-27 10:08:42'),
('pawankm4587@gmail.com', 1, '2019-09-27 10:11:41'),
('pawankm4587@gmail.com', 1, '2019-09-27 10:13:43');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `user_id` int(10) NOT NULL,
  `email_id` varchar(30) DEFAULT NULL,
  `password` varchar(30) DEFAULT NULL,
  `profile_picture` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_id`, `email_id`, `password`, `profile_picture`) VALUES
(2, 'pawankm4587@gmail.com', '123', '/profile/pawankm4587@gmail.com.jpeg'),
(3, 'deepak@gmail.com', '123', NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `chapters`
--
ALTER TABLE `chapters`
  ADD PRIMARY KEY (`chapter_id`),
  ADD KEY `manga_id` (`manga_id`);

--
-- Indexes for table `favourites`
--
ALTER TABLE `favourites`
  ADD KEY `email_id` (`email_id`),
  ADD KEY `manga_id` (`manga_id`);

--
-- Indexes for table `manga`
--
ALTER TABLE `manga`
  ADD PRIMARY KEY (`manga_id`);

--
-- Indexes for table `recent`
--
ALTER TABLE `recent`
  ADD KEY `email_id` (`email_id`),
  ADD KEY `manga_id` (`manga_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `email_id` (`email_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `chapters`
--
ALTER TABLE `chapters`
  MODIFY `chapter_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT for table `manga`
--
ALTER TABLE `manga`
  MODIFY `manga_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `user_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `chapters`
--
ALTER TABLE `chapters`
  ADD CONSTRAINT `chapters_ibfk_1` FOREIGN KEY (`manga_id`) REFERENCES `manga` (`manga_id`);

--
-- Constraints for table `favourites`
--
ALTER TABLE `favourites`
  ADD CONSTRAINT `favourites_ibfk_1` FOREIGN KEY (`email_id`) REFERENCES `users` (`email_id`),
  ADD CONSTRAINT `favourites_ibfk_2` FOREIGN KEY (`manga_id`) REFERENCES `manga` (`manga_id`);

--
-- Constraints for table `recent`
--
ALTER TABLE `recent`
  ADD CONSTRAINT `recent_ibfk_1` FOREIGN KEY (`email_id`) REFERENCES `users` (`email_id`),
  ADD CONSTRAINT `recent_ibfk_2` FOREIGN KEY (`manga_id`) REFERENCES `manga` (`manga_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;