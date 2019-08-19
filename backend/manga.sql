-- phpMyAdmin SQL Dump
-- version 4.8.2
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Aug 19, 2019 at 12:30 PM
-- Server version: 10.1.34-MariaDB
-- PHP Version: 7.2.8

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
  `title` varchar(30) DEFAULT NULL,
  `date_of_origin` date DEFAULT NULL,
  `chapter_path` varchar(30) DEFAULT NULL,
  `chapter_no` int(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `chapters`
--

INSERT INTO `chapters` (`chapter_id`, `manga_id`, `title`, `date_of_origin`, `chapter_path`, `chapter_no`) VALUES
(1, 1, 'Queen', '2019-08-15', '/chapters/1.pdf', 2),
(2, 1, 'King of Pirates', '2019-08-02', '/chapters/2.pdf', 3);

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
('pawankm4587@gmail.com', 2),
('pawankm4587@gmail.com', 4),
('pawankm4587@gmail.com', 1);

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
  `description` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `manga`
--

INSERT INTO `manga` (`manga_id`, `title`, `number_of_chapters`, `author`, `ratings`, `ratings_count`, `date_of_origin`, `last_release`, `cover_picture`, `description`) VALUES
(1, 'One Piece', '951', 'Echiro Oda', 5, 1000, '1998-04-10', '2019-08-03', '/covers/onepiece.jpeg', 'ffefsfsd ifdf dfsdf'),
(2, 'Hunter x hunter', '300', 'Togashi', 5, 500, '2019-08-04', '2019-08-07', '/covers/hxh.jpeg', 'rewrtghfgtgh  rer  resresr '),
(3, 'Attack on Titan', '120', 'Hajime Isayama', 4, 600, '2018-10-09', '2019-08-01', '/covers/aot.jpeg', 'Attack on Titan is a Japanese manga series both written and illustrated by Hajime Isayama. It is set in a fantasy world where humanity lives within territories surrounded by three enormous walls that '),
(4, 'The Promised Neverland', '80', 'Kaiu Shirai', 4, 400, '2018-02-07', '2019-08-28', '/covers/tpn.jpeg', 'Emma, Norman and Ray are the brightest kids at the Grace Field House orphanage. '),
(5, 'My Hero Academia', '330', 'Kohei Horikoshi', 5, 800, '2017-03-01', '2019-08-20', '/covers/mha.jpeg', 'My Hero Academia, abbreviated as HeroAca is a Japanese superhero manga series written and illustrated by K?hei Horikoshi.');

-- --------------------------------------------------------

--
-- Table structure for table `recent`
--

CREATE TABLE `recent` (
  `email_id` varchar(30) DEFAULT NULL,
  `manga_id` int(10) DEFAULT NULL,
  `at_time` datetime DEFAULT CURRENT_TIMESTAMP
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
('pawankm4587@gmail.com', 1, '2019-08-19 11:57:57');

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
(1, 'haha', '1', '/profile/pawankm4587@gmail.com.jpeg'),
(2, 'pawankm4587@gmail.com', '123', '/profile/pawankm4587@gmail.com.jpeg');

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
  MODIFY `chapter_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `manga`
--
ALTER TABLE `manga`
  MODIFY `manga_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `user_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

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
