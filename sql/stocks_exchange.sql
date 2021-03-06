-- phpMyAdmin SQL Dump
-- version 4.1.6
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Sep 25, 2014 at 04:50 AM
-- Server version: 5.6.16
-- PHP Version: 5.4.24

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+08:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `stocks_exchange`
--

-- --------------------------------------------------------

--
-- Table structure for table `asks`
--

DROP TABLE IF EXISTS `asks`;
DROP TABLE IF EXISTS `bids`;
DROP TABLE IF EXISTS `matched_transaction`;

CREATE TABLE IF NOT EXISTS `asks` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `stock` varchar(5) NOT NULL,
  `price` int(11) NOT NULL,
  `userid` varchar(255) NOT NULL,
  `date` timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
  `status` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `bids`
--

CREATE TABLE IF NOT EXISTS `bids` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `stock` varchar(5) NOT NULL,
  `price` int(11) NOT NULL,
  `userid` varchar(255) NOT NULL,
  `date` timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
  `status` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `bids`
--
-- --------------------------------------------------------

--
-- Table structure for table `matched_transaction`
--

CREATE TABLE IF NOT EXISTS `matched_transaction` (
  `bid_id` int(11) NOT NULL,
  `ask_id` int(11) NOT NULL,
  `date` TIMESTAMP NOT NULL,
  `price` int(11) NOT NULL,
  `stock` varchar(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
