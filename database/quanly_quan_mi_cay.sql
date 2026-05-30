-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 30, 2026 at 12:21 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

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
(8, 'Trống', -1),
(9, 'Trống', -1),
(10, 'Trống', -1),
(11, 'Trống', -1),
(12, 'Trống', -1),
(13, 'Trống', -1),
(14, 'Trống', -1),
(15, 'Trống', -1),
(16, 'Trống', -1),
(17, 'Trống', -1),
(18, 'Trống', -1),
(19, 'Trống', -1),
(20, 'Trống', -1),
(21, 'Trống', -1),
(22, 'Trống', -1),
(23, 'Trống', -1),
(24, 'Trống', -1),
(25, 'Trống', -1),
(26, 'Trống', -1),
(27, 'Trống', -1),
(28, 'Trống', -1),
(29, 'Trống', -1),
(30, 'Trống', -1),
(31, 'Trống', -1),
(32, 'Trống', -1),
(33, 'Trống', -1),
(34, 'Trống', -1),
(35, 'Trống', -1),
(36, 'Trống', -1),
(37, 'Trống', -1),
(38, 'Trống', -1),
(39, 'Trống', -1),
(40, 'Trống', -1),
(41, 'Trống', -1),
(42, 'Trống', -1),
(43, 'Trống', -1),
(44, 'Trống', -1),
(45, 'Trống', -1),
(46, 'Trống', -1),
(47, 'Trống', -1),
(48, 'Trống', -1),
(49, 'Trống', -1),
(50, 'Trống', -1),
(51, 'Trống', -1),
(52, 'Trống', -1),
(53, 'Trống', -1),
(54, 'Trống', -1),
(55, 'Trống', -1),
(56, 'Trống', -1),
(57, 'Trống', -1),
(58, 'Trống', -1),
(59, 'Trống', -1),
(60, 'Trống', -1);

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

--
-- Dumping data for table `chi_tiet_hoa_don`
--

INSERT INTO `chi_tiet_hoa_don` (`id`, `hoa_don_id`, `mon_id`, `ten_mon`, `so_luong`, `don_gia`, `thanh_tien`) VALUES
(1, 1, 1, 'Mi Cay Cấp 0 (Không cay)', 1, 35000, 35000),
(2, 1, 2, 'Mi Cay Cấp 3', 1, 45000, 45000),
(3, 2, 1, 'Mi Cay Cấp 0 (Không cay)', 1, 35000, 35000),
(4, 2, 5, 'Mi Cay TvT (Siêu cay)', 1, 75000, 75000),
(5, 2, 6, 'Trà Chanh', 1, 15000, 15000),
(6, 5, 5, 'Mi Cay TvT (Siêu cay)', 1, 75000, 75000),
(7, 5, 6, 'Trà Chanh', 1, 15000, 15000),
(8, 3, 5, 'Mi Cay TvT (Siêu cay)', 1, 75000, 75000),
(9, 3, 5, 'Mi Cay TvT (Siêu cay)', 1, 75000, 75000),
(10, 3, 5, 'Mi Cay TvT (Siêu cay)', 1, 75000, 75000),
(11, 3, 5, 'Mi Cay TvT (Siêu cay)', 1, 75000, 75000),
(12, 3, 5, 'Mi Cay TvT (Siêu cay)', 1, 75000, 75000),
(13, 3, 5, 'Mi Cay TvT (Siêu cay)', 1, 75000, 75000),
(14, 3, 5, 'Mi Cay TvT (Siêu cay)', 1, 75000, 75000),
(15, 3, 5, 'Mi Cay TvT (Siêu cay)', 1, 75000, 75000),
(16, 3, 5, 'Mi Cay TvT (Siêu cay)', 1, 75000, 75000),
(17, 3, 5, 'Mi Cay TvT (Siêu cay)', 1, 75000, 75000),
(18, 3, 5, 'Mi Cay TvT (Siêu cay)', 1, 75000, 75000),
(19, 3, 5, 'Mi Cay TvT (Siêu cay)', 1, 75000, 75000),
(20, 3, 5, 'Mi Cay TvT (Siêu cay)', 1, 75000, 75000),
(21, 3, 5, 'Mi Cay TvT (Siêu cay)', 1, 75000, 75000),
(22, 3, 5, 'Mi Cay TvT (Siêu cay)', 1, 75000, 75000),
(23, 3, 5, 'Mi Cay TvT (Siêu cay)', 1, 75000, 75000),
(24, 3, 5, 'Mi Cay TvT (Siêu cay)', 1, 75000, 75000),
(25, 3, 5, 'Mi Cay TvT (Siêu cay)', 1, 75000, 75000),
(26, 3, 5, 'Mi Cay TvT (Siêu cay)', 1, 75000, 75000),
(27, 3, 5, 'Mi Cay TvT (Siêu cay)', 1, 75000, 75000),
(28, 3, 5, 'Mi Cay TvT (Siêu cay)', 1, 75000, 75000),
(29, 3, 5, 'Mi Cay TvT (Siêu cay)', 1, 75000, 75000),
(30, 9, 2, 'Mi Cay Cấp 3', 1, 45000, 45000),
(31, 10, 1, 'Mi Cay Cấp 0 (Không cay)', 1, 35000, 35000),
(32, 9, 4, 'Mi Cay Cấp 7 (ĐẶC BIỆT)', 1, 65000, 65000),
(33, 11, 1, 'Mi Cay Cấp 0 (Không cay)', 1, 35000, 35000),
(34, 11, 5, 'Mi Cay TvT (Siêu cay)', 1, 75000, 75000),
(35, 11, 4, 'Mi Cay Cấp 7 (ĐẶC BIỆT)', 1, 65000, 65000),
(36, 10, 1, 'Mi Cay Cấp 0 (Không cay)', 1, 35000, 35000),
(37, 10, 1, 'Mi Cay Cấp 0 (Không cay)', 1, 35000, 35000),
(38, 10, 4, 'Mi Cay Cấp 7 (ĐẶC BIỆT)', 1, 65000, 65000),
(39, 10, 2, 'Mi Cay Cấp 3', 1, 45000, 45000),
(40, 10, 3, 'Mi Cay Cấp 5', 1, 55000, 55000),
(41, 10, 2, 'Mi Cay Cấp 3', 1, 45000, 45000),
(42, 10, 2, 'Mi Cay Cấp 3', 1, 45000, 45000),
(43, 12, 2, 'Mi Cay Cấp 3', 1, 45000, 45000),
(44, 12, 1, 'Mi Cay Cấp 0 (Không cay)', 1, 35000, 35000),
(45, 12, 1, 'Mi Cay Cấp 0 (Không cay)', 5, 35000, 175000),
(46, 14, 1, 'Mi Cay Cấp 0 (Không cay)', 1, 35000, 35000),
(47, 13, 2, 'Mi Cay Cấp 3', 3, 45000, 135000),
(48, 13, 2, 'Mi Cay Cấp 3', 3, 45000, 135000),
(49, 13, 4, 'Mi Cay Cấp 7 (ĐẶC BIỆT)', 7, 65000, 455000),
(50, 13, 2, 'Mi Cay Cấp 3', 10, 45000, 450000),
(51, 16, 1, 'Mi Cay Cấp 0 (Không cay)', 10, 35000, 350000),
(58, 17, 1, 'Mi Cay Cấp 0 (Không cay)', 12, 35000, 420000),
(62, 19, 1, 'Mi Cay Cấp 0 (Không cay)', 1, 35000, 35000),
(66, 22, 5, 'Mì Cay TvT (Siêu cay)', 1, 75000, 75000),
(67, 23, 2, 'Mì Cay Cấp 3', 1000, 45000, 45000000),
(69, 26, 1, 'Mì Cay Cấp 0 (Không cay)', 100, 35000, 3500000),
(70, 27, 1, 'Mì Cay Cấp 0 (Không cay)', 100, 35000, 3500000),
(71, 28, 1, 'Mì Cay Cấp 0 (Không cay)', 100, 35000, 3500000),
(72, 29, 1, 'Mì Cay Cấp 0 (Không cay)', 1000, 35000, 35000000),
(73, 30, 1, 'Mì Cay Cấp 0 (Không cay)', 100, 35000, 3500000),
(74, 31, 1, 'Mì Cay Cấp 0 (Không cay)', 10, 35000, 350000),
(75, 32, 1, 'Mì Cay Cấp 0 (Không cay)', 100, 35000, 3500000),
(76, 33, 1, 'Mì Cay Cấp 0 (Không cay)', 1, 35000, 35000),
(77, 34, 1, 'Mì Cay Cấp 0 (Không cay)', 100, 35000, 3500000),
(78, 35, 3, 'Mì Cay Cấp 5', 100, 55000, 5500000);

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

--
-- Dumping data for table `hoa_don`
--

INSERT INTO `hoa_don` (`id`, `so_ban`, `nhan_vien_id`, `ngay_tao`, `tong_tien`, `giam_gia`, `thanh_tien`, `da_thanh_toan`) VALUES
(1, 1, 1, '2026-05-19 14:13:02', 80000, 0, 80000, 1),
(2, 1, 1, '2026-05-19 14:19:54', 125000, 100000000, -99875000, 1),
(3, 2, 1, '2026-05-19 14:22:31', 1650000, 0, 1650000, 1),
(4, 3, 1, '2026-05-19 14:22:34', 0, 0, 0, 1),
(5, 1, 1, '2026-05-19 14:55:22', 90000, 0, 90000, 1),
(6, 1, 1, '2026-05-24 10:37:02', 0, 0, 0, 1),
(7, 1, 1, '2026-05-24 11:26:24', 0, 0, 0, 1),
(8, 1, 1, '2026-05-24 12:12:41', 0, 0, 0, 1),
(9, 1, 1, '2026-05-24 12:13:15', 110000, 0, 110000, 1),
(10, 2, 1, '2026-05-24 12:13:23', 360000, 0, 360000, 1),
(11, 7, 1, '2026-05-24 12:13:37', 175000, 0, 175000, 1),
(12, 1, 1, '2026-05-26 16:51:56', 255000, 0, 255000, 1),
(13, 6, 1, '2026-05-26 17:03:21', 1175000, 90000000, -88825000, 1),
(14, 1, 1, '2026-05-26 17:45:00', 35000, 0, 35000, 1),
(15, 1, 1, '2026-05-29 00:59:41', 12, 0, 12, 1),
(16, 41, 1, '2026-05-29 01:00:23', 350004, 0, 350004, 1),
(17, 1, 1, '2026-05-29 02:07:27', 420003, 0, 420003, 1),
(18, 1, 1, '2026-05-29 02:42:39', 11, 0, 11, 1),
(19, 1, 1, '2026-05-29 02:43:05', 35000, 0, 35000, 1),
(20, 1, 1, '2026-05-29 02:44:06', 1, 0, 1, 1),
(21, 1, 1, '2026-05-29 03:07:09', 11, 0, 11, 1),
(22, 1, 1, '2026-05-29 03:18:45', 75000, 0, 75000, 1),
(23, 1, 1, '2026-05-29 04:10:31', 45000000, 0, 45000000, 1),
(26, 1, 1, '2026-05-30 15:01:42', 3500000, 0, 3500000, 1),
(27, 2, 1, '2026-05-30 15:08:45', 3500000, 0, 3500000, 1),
(28, 1, 1, '2026-05-30 15:27:02', 3500000, 0, 3500000, 1),
(29, 1, 1, '2026-05-30 15:35:01', 35000000, 0, 35000000, 1),
(30, 1, 1, '2026-05-30 15:36:36', 3500000, 0, 3500000, 1),
(31, 1, 1, '2026-05-30 15:37:15', 350000, 35000, 350000, 1),
(32, 1, 1, '2026-05-30 15:40:41', 3500000, 0, 3500000, 1),
(33, 1, 1, '2026-05-30 15:44:19', 35000, 3500, 31500, 1),
(34, 1, 1, '2026-05-30 15:47:11', 3500000, 0, 3500000, 1),
(35, 1, 1, '2026-05-30 16:16:05', 5500000, 0, 5500000, 1);

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
(9, 'Trứng', 'Topping', -1, 5000, 1),
(11, 'Sữa Bò', 'Nuoc Uong', -1, 100000, 1);

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
(1, 'Quản trị viên', 'admin', '96cae35ce8a9b0244178bf28e4966c2ce1b8385723a96a6b838858cdd6ca0a1e', 'admin', '2026-05-19 09:41:29', 1),
(2, 'Lê Quang Tiến', 'quangtien', '96cae35ce8a9b0244178bf28e4966c2ce1b8385723a96a6b838858cdd6ca0a1e', 'nhan_vien', '2026-05-30 15:58:46', 1),
(3, 'Võ Ngọc Vỹ', 'ngocvy', '96cae35ce8a9b0244178bf28e4966c2ce1b8385723a96a6b838858cdd6ca0a1e', 'nhan_vien', '2026-05-30 15:59:06', 1),
(4, 'Nguyễn Quốc Thắng', 'quocthang', '96cae35ce8a9b0244178bf28e4966c2ce1b8385723a96a6b838858cdd6ca0a1e', 'nhan_vien', '2026-05-30 15:59:28', 1);

-- --------------------------------------------------------

--
-- Table structure for table `voucher`
--

CREATE TABLE `voucher` (
  `id` int(11) NOT NULL,
  `ma_voucher` varchar(20) NOT NULL,
  `muc_giam` int(11) NOT NULL DEFAULT 10,
  `trang_thai` varchar(20) DEFAULT 'Chưa sử dụng',
  `ngay_tao` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `voucher`
--

INSERT INTO `voucher` (`id`, `ma_voucher`, `muc_giam`, `trang_thai`, `ngay_tao`) VALUES
(1, 'TVT-F9EY4K', 10, 'Đã sử dụng', '2026-05-30 15:36:42'),
(2, 'TVT-SSGN3U', 10, 'Đã sử dụng', '2026-05-30 15:44:00'),
(3, 'TVT-0QVCF6', 10, 'Chưa sử dụng', '2026-05-30 15:47:23'),
(4, 'TVT-PEMOS6', 10, 'Chưa sử dụng', '2026-05-30 16:16:13');

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
-- Indexes for table `voucher`
--
ALTER TABLE `voucher`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `ma_voucher` (`ma_voucher`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `chi_tiet_hoa_don`
--
ALTER TABLE `chi_tiet_hoa_don`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=79;

--
-- AUTO_INCREMENT for table `hoa_don`
--
ALTER TABLE `hoa_don`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=36;

--
-- AUTO_INCREMENT for table `mon`
--
ALTER TABLE `mon`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

--
-- AUTO_INCREMENT for table `nhan_vien`
--
ALTER TABLE `nhan_vien`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `voucher`
--
ALTER TABLE `voucher`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

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
