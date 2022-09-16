-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Sep 16, 2022 at 04:15 AM
-- Server version: 10.4.24-MariaDB
-- PHP Version: 8.1.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `bawang_berkah`
--

-- --------------------------------------------------------

--
-- Table structure for table `bawang`
--

CREATE TABLE `bawang` (
  `Nama_bawang` varchar(50) NOT NULL,
  `Stock` int(11) NOT NULL,
  `Price` int(11) NOT NULL,
  `grosir` int(11) NOT NULL,
  `partai` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `bawang`
--

INSERT INTO `bawang` (`Nama_bawang`, `Stock`, `Price`, `grosir`, `partai`) VALUES
('Bombay Royal 50/60', 250, 325000, 320000, 315000),
('Honam PB', 15, 14200, 14000, 13800),
('Kating', 20, 17000, 16800, 16600);

-- --------------------------------------------------------

--
-- Table structure for table `pembeli`
--

CREATE TABLE `pembeli` (
  `Nama_pembeli` varchar(255) NOT NULL,
  `No_telp` varchar(15) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `transaction`
--

CREATE TABLE `transaction` (
  `No_faktur` varchar(5) NOT NULL,
  `Nama_pembeli` varchar(255) NOT NULL,
  `total` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `transaction_detail`
--

CREATE TABLE `transaction_detail` (
  `No_faktur` varchar(5) NOT NULL,
  `Date_of_transaction` date NOT NULL DEFAULT current_timestamp(),
  `Nama_bawang` varchar(255) NOT NULL,
  `qty` int(11) NOT NULL,
  `weight` float NOT NULL,
  `subtotal` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `truck`
--

CREATE TABLE `truck` (
  `ID_truck` varchar(10) NOT NULL,
  `Date_of_arrival` date NOT NULL DEFAULT current_timestamp(),
  `Driver` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `truck`
--

INSERT INTO `truck` (`ID_truck`, `Date_of_arrival`, `Driver`) VALUES
('B008BBT009', '2022-08-11', 'Musa');

-- --------------------------------------------------------

--
-- Table structure for table `truck_detail`
--

CREATE TABLE `truck_detail` (
  `ID_Truck` varchar(55) NOT NULL,
  `id` varchar(4) NOT NULL,
  `Nama_bawang` varchar(50) NOT NULL,
  `qty` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `truck_detail`
--

INSERT INTO `truck_detail` (`ID_Truck`, `id`, `Nama_bawang`, `qty`) VALUES
('B008BBT009', '009A', 'Honam PB', 100),
('B008BBT009', '009B', 'Bombay Royal 50/60', 100);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `bawang`
--
ALTER TABLE `bawang`
  ADD PRIMARY KEY (`Nama_bawang`);

--
-- Indexes for table `pembeli`
--
ALTER TABLE `pembeli`
  ADD PRIMARY KEY (`Nama_pembeli`);

--
-- Indexes for table `transaction`
--
ALTER TABLE `transaction`
  ADD PRIMARY KEY (`No_faktur`),
  ADD KEY `transaction_ibfk_1` (`Nama_pembeli`);

--
-- Indexes for table `transaction_detail`
--
ALTER TABLE `transaction_detail`
  ADD PRIMARY KEY (`No_faktur`,`Nama_bawang`),
  ADD KEY `No_faktur` (`No_faktur`),
  ADD KEY `Nama_bawang` (`Nama_bawang`);

--
-- Indexes for table `truck`
--
ALTER TABLE `truck`
  ADD PRIMARY KEY (`ID_truck`);

--
-- Indexes for table `truck_detail`
--
ALTER TABLE `truck_detail`
  ADD PRIMARY KEY (`id`),
  ADD KEY `ID_Truck` (`ID_Truck`),
  ADD KEY `Nama_bawang` (`Nama_bawang`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `transaction`
--
ALTER TABLE `transaction`
  ADD CONSTRAINT `transaction_ibfk_1` FOREIGN KEY (`Nama_pembeli`) REFERENCES `pembeli` (`Nama_pembeli`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `transaction_detail`
--
ALTER TABLE `transaction_detail`
  ADD CONSTRAINT `transaction_detail_ibfk_1` FOREIGN KEY (`No_faktur`) REFERENCES `transaction` (`No_faktur`),
  ADD CONSTRAINT `transaction_detail_ibfk_2` FOREIGN KEY (`Nama_bawang`) REFERENCES `bawang` (`Nama_bawang`);

--
-- Constraints for table `truck_detail`
--
ALTER TABLE `truck_detail`
  ADD CONSTRAINT `truck_detail_ibfk_1` FOREIGN KEY (`ID_Truck`) REFERENCES `truck` (`ID_truck`),
  ADD CONSTRAINT `truck_detail_ibfk_2` FOREIGN KEY (`Nama_bawang`) REFERENCES `bawang` (`Nama_bawang`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
