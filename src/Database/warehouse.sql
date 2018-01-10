-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 10, 2018 at 06:35 PM
-- Server version: 10.1.29-MariaDB
-- PHP Version: 7.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `warehouse`
--

-- --------------------------------------------------------

--
-- Table structure for table `akcesoria`
--

CREATE TABLE `akcesoria` (
  `id` int(11) NOT NULL,
  `name` text COLLATE utf8_bin NOT NULL,
  `amount` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `akcesoria`
--

INSERT INTO `akcesoria` (`id`, `name`, `amount`) VALUES
(1, 'Akcesrioa4', 7),
(2, 'Akcesoria7', 2),
(3, 'Akcesrioa150', 3),
(4, 'Akcesoria80', 9);

-- --------------------------------------------------------

--
-- Table structure for table `instrumenty_dete`
--

CREATE TABLE `instrumenty_dete` (
  `id` int(11) NOT NULL,
  `name` text COLLATE utf8_bin NOT NULL,
  `amount` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `instrumenty_dete`
--

INSERT INTO `instrumenty_dete` (`id`, `name`, `amount`) VALUES
(1, 'InstrumentDety1', 3),
(2, 'InstrumentDety2', 5),
(3, 'InstrumentDety3', 4),
(4, 'InstrumentDety4', 7);

-- --------------------------------------------------------

--
-- Table structure for table `instrumenty_smyczkowe`
--

CREATE TABLE `instrumenty_smyczkowe` (
  `id` int(11) NOT NULL,
  `name` text COLLATE utf8_bin NOT NULL,
  `amount` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `instrumenty_smyczkowe`
--

INSERT INTO `instrumenty_smyczkowe` (`id`, `name`, `amount`) VALUES
(1, 'Instrument', 5),
(2, 'Instrument4', 1),
(3, 'Instrument4', 8),
(4, 'Instrument3', 7),
(5, 'skrzypce', 5);

-- --------------------------------------------------------

--
-- Table structure for table `sprzet_naglosnieniowy`
--

CREATE TABLE `sprzet_naglosnieniowy` (
  `id` int(11) NOT NULL,
  `name` text COLLATE utf8_bin NOT NULL,
  `amount` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `sprzet_naglosnieniowy`
--

INSERT INTO `sprzet_naglosnieniowy` (`id`, `name`, `amount`) VALUES
(1, 'Sprzet2', 7),
(2, 'Sprzet1', 4),
(3, 'Sprzet33', 9),
(4, 'Sprzet7', 30);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `akcesoria`
--
ALTER TABLE `akcesoria`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `instrumenty_dete`
--
ALTER TABLE `instrumenty_dete`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `instrumenty_smyczkowe`
--
ALTER TABLE `instrumenty_smyczkowe`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `sprzet_naglosnieniowy`
--
ALTER TABLE `sprzet_naglosnieniowy`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `akcesoria`
--
ALTER TABLE `akcesoria`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `instrumenty_dete`
--
ALTER TABLE `instrumenty_dete`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `instrumenty_smyczkowe`
--
ALTER TABLE `instrumenty_smyczkowe`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `sprzet_naglosnieniowy`
--
ALTER TABLE `sprzet_naglosnieniowy`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
