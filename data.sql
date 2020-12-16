-- MySQL dump 10.13  Distrib 8.0.18, for Win64 (x86_64)
--
-- Host: localhost    Database: tzeentch-shop
-- ------------------------------------------------------
-- Server version	8.0.18

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Dumping data for table `author`
--

LOCK TABLES `author` WRITE;
/*!40000 ALTER TABLE `author` DISABLE KEYS */;
INSERT INTO `author` VALUES (1,'','Роулинг',1),(2,'descr 2','помошник роулинг',2),(4,'descr 4','вдохновитель роулинг',4),(6,'description','собака роулинг',1),(81,'very cool description\n                                    ','кошка Роулинг',1);
/*!40000 ALTER TABLE `author` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `book`
--

LOCK TABLES `book` WRITE;
/*!40000 ALTER TABLE `book` DISABLE KEYS */;
INSERT INTO `book` VALUES (1,1,'FINISHED','Отличная книга про приключения','Гарри Поттер Часть 1',1,20.00,'FINISHED',7,'2020-08-08 00:00:00.000000',4.8,'src/main/resources/books/the_little_prince.pdf','the_little_prince.pdf',1),(2,0,'CONTINUES','Отличная книга про какого-то мальчика','Гарри Поттер Часть 2',60,21.00,'REVIVE',8,'2020-09-09 00:00:00.000000',3,'src/main/resources/books/the_little_prince.pdf','the_little_prince.pdf',2),(3,1,'FINISHED','Отличная книга про ... ','Гарри Поттер Часть 3',0,20.00,'FINISHED',9,'2020-11-11 00:00:00.000000',2,'src/main/resources/books/the_little_prince.pdf','the_little_prince.pdf',3),(4,1,'CONTINUES','Зачем описывать такой шедевр','Гарри Поттер Часть 4',51,60.00,'CONTINUES',10,'2020-09-09 00:00:00.000000',1,'src/main/resources/books/the_little_prince.pdf','the_little_prince.pdf',4),(5,0,'ANNOUNCE','ну просто неть слов','Гарри Поттер Часть 5',20,100.00,'ANNOUNCE',11,'2020-10-10 00:00:00.000000',3.5,'src/main/resources/books/the_little_prince.pdf','the_little_prince.pdf',5);
/*!40000 ALTER TABLE `book` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `book_authors`
--

LOCK TABLES `book_authors` WRITE;
/*!40000 ALTER TABLE `book_authors` DISABLE KEYS */;
INSERT INTO `book_authors` VALUES (1,1),(2,2),(4,3),(6,4),(1,5);
/*!40000 ALTER TABLE `book_authors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `book_genre`
--

LOCK TABLES `book_genre` WRITE;
/*!40000 ALTER TABLE `book_genre` DISABLE KEYS */;
INSERT INTO `book_genre` VALUES (1,'FANTASY'),(2,'ADVENTURE'),(3,'ROMANCE'),(4,'CONTEMPORARY'),(5,'DYSTOPIAN'),(2,'DYSTOPIAN');
/*!40000 ALTER TABLE `book_genre` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `book_rewiew`
--

LOCK TABLES `book_rewiew` WRITE;
/*!40000 ALTER TABLE `book_rewiew` DISABLE KEYS */;
INSERT INTO `book_rewiew` VALUES (27,1),(29,1);
/*!40000 ALTER TABLE `book_rewiew` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `book_translater`
--

LOCK TABLES `book_translater` WRITE;
/*!40000 ALTER TABLE `book_translater` DISABLE KEYS */;
/*!40000 ALTER TABLE `book_translater` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `book_users`
--

LOCK TABLES `book_users` WRITE;
/*!40000 ALTER TABLE `book_users` DISABLE KEYS */;
INSERT INTO `book_users` VALUES (9,1),(9,2),(9,3),(9,4),(9,5);
/*!40000 ALTER TABLE `book_users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `chapter`
--

LOCK TABLES `chapter` WRITE;
/*!40000 ALTER TABLE `chapter` DISABLE KEYS */;
/*!40000 ALTER TABLE `chapter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `chapter_translater`
--

LOCK TABLES `chapter_translater` WRITE;
/*!40000 ALTER TABLE `chapter_translater` DISABLE KEYS */;
/*!40000 ALTER TABLE `chapter_translater` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (89),(89),(89),(89),(89),(89),(89),(89),(89),(89),(89),(89),(89),(89),(89),(89),(89),(89),(89),(89),(89),(89),(89),(89),(89),(89),(89),(89),(89),(89),(89),(89),(89),(89),(89),(89),(89),(89),(89),(89);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `message`
--

LOCK TABLES `message` WRITE;
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
INSERT INTO `message` VALUES (1,'2020-10-10 01:00:00.000000','Ух , а я всю ночь спал )','2','Egor',2,0,9),(2,'2020-10-10 00:00:00.000000','Я кодешник для математического моделирования, а еще два курсача писать. Одного в прошлом семаке явно было мало','1','Egor',1,0,9),(3,'2020-01-01 00:00:00.000000','Надеюсь, что твои старания не напрасны','1','Egor',1,0,9),(4,'2020-09-09 00:00:00.000000','я пол ночи сидела в телефоне, но на удивление выспалась','1','Egor',1,0,9),(5,'2020-09-09 00:00:00.000000','полет фантазии','1','Egor',1,0,9),(6,'2020-10-11 00:00:00.000000','Полностью с вами солидарен','2','Egor',2,0,9);
/*!40000 ALTER TABLE `message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `news`
--

LOCK TABLES `news` WRITE;
/*!40000 ALTER TABLE `news` DISABLE KEYS */;
INSERT INTO `news` VALUES (1,'some news','2020-09-09 00:00:00.000000','Подборка книг для любителей фентезт',9,1),(2,'new news','2020-10-10 00:00:00.000000','Новые поступления',9,2),(3,'news','2020-02-02 00:00:00.000000','Технические работы',9,3),(4,'cool things happend','2020-11-11 00:00:00.000000','Долгожданный анонс',9,4),(5,'nothing happend','2020-04-04 00:00:00.000000','Обсуждение анноса',9,1),(88,'enter message \n                        ','2020-12-10 16:05:48.076000','Важные новости',9,1);
/*!40000 ALTER TABLE `news` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `news_review`
--

LOCK TABLES `news_review` WRITE;
/*!40000 ALTER TABLE `news_review` DISABLE KEYS */;
INSERT INTO `news_review` VALUES (1,1),(2,1),(21,1),(24,1);
/*!40000 ALTER TABLE `news_review` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `picture`
--

LOCK TABLES `picture` WRITE;
/*!40000 ALTER TABLE `picture` DISABLE KEYS */;
INSERT INTO `picture` VALUES (0,'no_image','../images/no-image.png'),(1,'no-image','../images/no-image.png'),(2,'avatar 1','../images/avatar_2.jpg'),(3,'2','../images/avatar_2.jpg'),(4,'avatar 4','../images/avatar_4.jpg'),(5,'book','../images/book.jpg'),(6,'bookshelf','../images/bookshelf.jpg'),(7,'part1','../images/part1.jpg'),(8,'part2','../images/part2.jpg'),(9,'part3','../images/part3.jpg'),(10,'part4','../images/part4.jfif'),(11,'part5','../images/part5.jpg');
/*!40000 ALTER TABLE `picture` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `rating_param`
--

LOCK TABLES `rating_param` WRITE;
/*!40000 ALTER TABLE `rating_param` DISABLE KEYS */;
INSERT INTO `rating_param` VALUES (1,2,1,9),(2,3,2,9),(3,3,3,9),(4,4,4,9),(5,5,5,9);
/*!40000 ALTER TABLE `rating_param` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `rating_param_voted_user`
--

LOCK TABLES `rating_param_voted_user` WRITE;
/*!40000 ALTER TABLE `rating_param_voted_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `rating_param_voted_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `related_book`
--

LOCK TABLES `related_book` WRITE;
/*!40000 ALTER TABLE `related_book` DISABLE KEYS */;
INSERT INTO `related_book` VALUES (1,2),(2,3),(2,4);
/*!40000 ALTER TABLE `related_book` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `review`
--

LOCK TABLES `review` WRITE;
/*!40000 ALTER TABLE `review` DISABLE KEYS */;
INSERT INTO `review` VALUES (1,'comment','2020-09-09 00:00:00.000000',8,9),(2,'other comment','2020-10-10 00:00:00.000000',9,9),(3,'answer','2020-11-11 00:00:00.000000',3,9),(4,'must be not shown','2020-10-10 00:00:00.000000',10,9),(19,'add comment','2020-11-24 14:07:23.795000',0,9),(20,'add answer','2020-11-24 14:07:33.493183',NULL,9),(21,'add comment','2020-11-24 14:08:39.745000',0,9),(24,'add comment new','2020-11-24 14:39:59.058800',0,9),(26,'add comment new','2020-11-24 14:42:26.009319',NULL,9),(27,'cool book','1010-10-10 00:00:00.000000',2,9),(28,'yes it is','2020-10-10 00:00:00.000000',2,9),(29,'not like','2020-09-09 00:00:00.000000',2,9),(40,'cool','2020-12-03 16:00:24.243025',0,9),(57,'и че','2020-12-03 17:02:40.210972',NULL,9),(58,'о','2020-12-03 17:03:54.817392',0,9),(59,'и ниче','2020-12-03 17:04:11.494554',NULL,9),(70,'qwd','2020-12-03 17:49:47.269554',0,9),(71,'ad','2020-12-03 17:59:48.956072',0,9),(72,'v','2020-12-03 18:03:25.635269',0,9),(73,'add comment new','2020-12-03 18:07:10.823137',0,9),(74,'cool','2020-12-03 18:07:34.438881',NULL,9);
/*!40000 ALTER TABLE `review` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `review_answers`
--

LOCK TABLES `review_answers` WRITE;
/*!40000 ALTER TABLE `review_answers` DISABLE KEYS */;
INSERT INTO `review_answers` VALUES (2,3),(19,20),(24,26),(27,28),(29,57);
/*!40000 ALTER TABLE `review_answers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `room`
--

LOCK TABLES `room` WRITE;
/*!40000 ALTER TABLE `room` DISABLE KEYS */;
INSERT INTO `room` VALUES (1,'cool room. isn\'t it?','2020-11-26 00:00:00.000000','Правила и бла бла бла',9),(2,'Лучше бы еще раз перенеслт','2020-10-10 00:00:00.000000','релиз книги',9),(3,'Перенос','2020-11-11 00:00:00.000000','перенос',9),(4,'Долгожданный анонс','2020-10-11 00:00:00.000000','Долгожданный анонс',9),(5,'Рекомендации новичкам','2020-11-09 00:00:00.000000','Рекомендации новичкам',9),(87,'Обсуждение новинок','2020-12-10 15:04:18.596000','Обсуждение новинок',9);
/*!40000 ALTER TABLE `room` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `room_messages`
--

LOCK TABLES `room_messages` WRITE;
/*!40000 ALTER TABLE `room_messages` DISABLE KEYS */;
/*!40000 ALTER TABLE `room_messages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `spring_session`
--

LOCK TABLES `spring_session` WRITE;
/*!40000 ALTER TABLE `spring_session` DISABLE KEYS */;
INSERT INTO `spring_session` VALUES ('1de8cf82-68eb-469d-a5fc-b2cfd0fec68a','a295b8b5-0db5-4ac3-8554-625db2072c77',1608017036394,1608017433777,1800,1608019233777,NULL);
/*!40000 ALTER TABLE `spring_session` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `spring_session_attributes`
--

LOCK TABLES `spring_session_attributes` WRITE;
/*!40000 ALTER TABLE `spring_session_attributes` DISABLE KEYS */;
/*!40000 ALTER TABLE `spring_session_attributes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `translater`
--

LOCK TABLES `translater` WRITE;
/*!40000 ALTER TABLE `translater` DISABLE KEYS */;
/*!40000 ALTER TABLE `translater` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (9,'',_binary '\0','','107510623782968017062','2020-12-15 09:49:28.758833','ru','Ekrasouski Krasouski',1,'Egor',NULL,NULL,'','4716350378756874',100000000.00);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `user_credit_details`
--

LOCK TABLES `user_credit_details` WRITE;
/*!40000 ALTER TABLE `user_credit_details` DISABLE KEYS */;
INSERT INTO `user_credit_details` VALUES ('4716350378756874',99999960.00,9);
/*!40000 ALTER TABLE `user_credit_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` VALUES (9,'ADMIN'),(9,'USER'),(9,'UNKNOWN');
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-12-16 18:45:27
