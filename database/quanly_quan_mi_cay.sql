-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 19, 2026 at 09:10 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.1.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `quanly_quan_mi_cay`
--

-- --------------------------------------------------------

--
-- Table structure for table `ban`
--

CREATE TABLE `ban` (
  `so_ban` int(11) NOT NULL,
  `trang_thai` varchar(50) DEFAULT 'Trống',
  `hoa_don_id` int(11) DEFAULT -1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `ban`
--

INSERT INTO `ban` (`so_ban`, `trang_thai`, `hoa_don_id`) VALUES
(1, 'Trống', -1),
(2, 'Trống', -1),
(3, 'Trống', -1),
(4, 'Trống', -1),
(5, 'Trống', -1),
(6, 'Trống', -1),
(7, 'Trống', -1),
(8, 'Trống', -1);

-- --------------------------------------------------------

--
-- Table structure for table `chi_tiet_hoa_don`
--

CREATE TABLE `chi_tiet_hoa_don` (
  `id` int(11) NOT NULL,
  `hoa_don_id` int(11) NOT NULL,
  `mon_id` int(11) NOT NULL,
  `ten_mon` varchar(100) NOT NULL,
  `so_luong` int(11) NOT NULL,
  `don_gia` decimal(10,0) NOT NULL,
  `thanh_tien` decimal(10,0) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `hoa_don`
--

CREATE TABLE `hoa_don` (
  `id` int(11) NOT NULL,
  `so_ban` int(11) NOT NULL,
  `nhan_vien_id` int(11) DEFAULT NULL,
  `ngay_tao` datetime NOT NULL,
  `tong_tien` decimal(10,0) DEFAULT 0,
  `giam_gia` decimal(10,0) DEFAULT 0,
  `thanh_tien` decimal(10,0) DEFAULT 0,
  `da_thanh_toan` tinyint(1) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `mon`
--

CREATE TABLE `mon` (
  `id` int(11) NOT NULL,
  `ten` varchar(100) NOT NULL,
  `loai` varchar(50) NOT NULL,
  `cap_do_cay` int(11) DEFAULT -1,
  `gia` decimal(10,0) NOT NULL,
  `con_ban` tinyint(1) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `mon`
--

INSERT INTO `mon` (`id`, `ten`, `loai`, `cap_do_cay`, `gia`, `con_ban`) VALUES
(1, 'Mì Cay', 'Mi Cay', 0, 35000, 1),
(2, 'Mì Cay', 'Mi Cay', 3, 45000, 1),
(3, 'Mì Cay', 'Mi Cay', 5, 55000, 1),
(4, 'Mì Cay', 'Mi Cay', 7, 65000, 1),
(5, 'Mì Cay TvT', 'Mi Cay', 10, 75000, 1),
(6, 'Trà Chanh', 'Nuoc Uong', -1, 15000, 1),
(7, 'Trà Sữa Matcha', 'Nuoc Uong', -1, 25000, 1),
(8, 'Phô Mai', 'Topping', -1, 10000, 1),
(9, 'Trứng', 'Topping', -1, 8000, 1);

-- --------------------------------------------------------

--
-- Table structure for table `nhan_vien`
--

CREATE TABLE `nhan_vien` (
  `id` int(11) NOT NULL,
  `ten` varchar(100) NOT NULL,
  `tai_khoan` varchar(50) NOT NULL,
  `mat_khau` varchar(255) NOT NULL,
  `vai_tro` enum('admin','nhan_vien') DEFAULT 'nhan_vien',
  `ngay_tao` datetime DEFAULT current_timestamp(),
  `trang_thai` tinyint(1) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `nhan_vien`
--

INSERT INTO `nhan_vien` (`id`, `ten`, `tai_khoan`, `mat_khau`, `vai_tro`, `ngay_tao`, `trang_thai`) VALUES
(1, 'Quản trị viên', 'admin', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'admin', '2026-05-19 09:41:29', 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `ban`
--
ALTER TABLE `ban`
  ADD PRIMARY KEY (`so_ban`);

--
-- Indexes for table `chi_tiet_hoa_don`
--
ALTER TABLE `chi_tiet_hoa_don`
  ADD PRIMARY KEY (`id`),
  ADD KEY `hoa_don_id` (`hoa_don_id`),
  ADD KEY `mon_id` (`mon_id`);

--
-- Indexes for table `hoa_don`
--
ALTER TABLE `hoa_don`
  ADD PRIMARY KEY (`id`),
  ADD KEY `so_ban` (`so_ban`),
  ADD KEY `nhan_vien_id` (`nhan_vien_id`);

--
-- Indexes for table `mon`
--
ALTER TABLE `mon`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `nhan_vien`
--
ALTER TABLE `nhan_vien`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `tai_khoan` (`tai_khoan`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `chi_tiet_hoa_don`
--
ALTER TABLE `chi_tiet_hoa_don`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `hoa_don`
--
ALTER TABLE `hoa_don`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `mon`
--
ALTER TABLE `mon`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `nhan_vien`
--
ALTER TABLE `nhan_vien`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `chi_tiet_hoa_don`
--
ALTER TABLE `chi_tiet_hoa_don`
  ADD CONSTRAINT `chi_tiet_hoa_don_ibfk_1` FOREIGN KEY (`hoa_don_id`) REFERENCES `hoa_don` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `chi_tiet_hoa_don_ibfk_2` FOREIGN KEY (`mon_id`) REFERENCES `mon` (`id`);

--
-- Constraints for table `hoa_don`
--
ALTER TABLE `hoa_don`
  ADD CONSTRAINT `hoa_don_ibfk_1` FOREIGN KEY (`so_ban`) REFERENCES `ban` (`so_ban`),
  ADD CONSTRAINT `hoa_don_ibfk_2` FOREIGN KEY (`nhan_vien_id`) REFERENCES `nhan_vien` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
