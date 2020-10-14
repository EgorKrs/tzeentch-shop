

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (6),(6),(6),(6),(6),(6),(6),(6),(6),(6),(6),
(6),(6),(6),(6),(6),(6),(6),(6),(6),(6),(6),(6),(6),(6),(6),(6),
(6),(6),(6),(6),(6),(6),(6),(6),(6),(6),(6),(6),(6),(6),(6),(6),
(6),(6),(6),(6),(6),(6),(6),(6),(6),(6),(6),(6),(6),(6),(6),(6),
(6),(6),(6),(6),(6),(6),(6),(6),(6),(6),(6),(6),(6),(6),(6),(6),
(6),(6),(6),(6),(6),(6),(6),(6);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

LOCK TABLES `picture` WRITE;
/*!40000 ALTER TABLE `picture` DISABLE KEYS */;
INSERT INTO `picture` VALUES
(1,'avatar 1','author_files/avatar_1.jpg'),
(2,'avatar 2','author_files/avatar_2.jpg'),
(3,'avatar 3','author_files/avatar_3.jpg'),
(4,'avatar 4','author_files/avatar_4.jpg'),
(5,'avatar 5','author_files/avatar_5.jpg');
/*!40000 ALTER TABLE `picture` ENABLE KEYS */;
UNLOCK TABLES;

LOCK TABLES `author` WRITE;
/*!40000 ALTER TABLE `author` DISABLE KEYS */;
INSERT INTO `author` VALUES
(1,'descr 1','fio 1',1),
(2,'descr 2','fio 2',2),
(3,'descr 3','fio 3',3),
(4,'descr 4','fio 4',4),
(5,'descr 5','fio 5',5);
/*!40000 ALTER TABLE `author` ENABLE KEYS */;
UNLOCK TABLES;
