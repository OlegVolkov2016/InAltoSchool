-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Хост: 127.0.0.1
-- Час створення: Січ 06 2016 р., 02:00
-- Версія сервера: 10.1.8-MariaDB
-- Версія PHP: 5.6.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- База даних: `inaltoschool`
--
CREATE DATABASE IF NOT EXISTS `inaltoschool` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin;
USE `inaltoschool`;

-- --------------------------------------------------------

--
-- Структура таблиці `courses`
--

CREATE TABLE IF NOT EXISTS `courses` (
  `course_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Course''s ID',
  `author_id` int(11) NOT NULL COMMENT 'Appropriate author''s ID',
  `course_name` varchar(50) COLLATE utf8_bin NOT NULL COMMENT 'Course''s name',
  `course_details` text COLLATE utf8_bin COMMENT 'Course''s information (program)',
  `course_group_price` decimal(7,2) NOT NULL COMMENT 'Course''s group price',
  `course_individual_price` decimal(7,2) NOT NULL COMMENT 'Course''s individual price',
  PRIMARY KEY (`course_id`),
  UNIQUE KEY `author_id` (`author_id`,`course_name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='School courses table';

--
-- Дамп даних таблиці `courses`
--

INSERT INTO `courses` (`course_id`, `author_id`, `course_name`, `course_details`, `course_group_price`, `course_individual_price`) VALUES
(1, 1, 'Java Core', 'Програма курсу "Java - Core" призначена для тих, хто хоче в короткі терміни освоїти Java і мати стабільний і міцний фундамент знань для подальшого розвитку. На цьому курсі розбираються принципи об’єктно орієнтованого програмування на Java, цікаві алгоритми і структури даних, архітектурні підходи, що дасть достатній фундамент для подальшого розвитку А головне: всі бажаючі пройти курс Java не повинні мати уявлення про структурне програмування чи знати синтаксис і вміти працювати з основними конструкціями Java (типи даних, умови, цикли, масиви). Всі ці знання Ви отримаєте разом із нами, не виходячи з дому.\n\nПісля закінчення курсу Ви навчитеся програмувати в об’єктно орієнтованому стилі, налагоджувати програми, проектувати архітектуру програми, познайомитеся з бібліотеками Java SE, освоїте ефективну методологію вивчення програмування, яка буде служити для подальшого розвитку в ІТ сфері. Пройшовши курси java, Ви отримаєте досвід роботи над реальними проектами для подальшого працевлаштування. Дані курси програмування Java призначені для того, щоб познайомити слухача з базовими концепціями та технологіями програмування на мові Java, що дозволить освоїти конкретну бібліотеку, або платформу програмування на Java.\nНеобхідна підготовка\n\nБажана англійська мова на рівні читання / письма.\nВолодіння основами роботи на ПК на рівні «досвідчений користувач».\nРекомендуємо також ознайомитися з книгами "Thinking in Java" автора Bruce Eckel або "Бібліотека професіонала" авторів Хорстманн К., Корнелл Г.', '3599.99', '3999.99'),
(2, 2, 'Java Advance', '<html dir="ltr"><head></head><body contenteditable="true">Програма професійного курсу <b>"Java - Advance"</b> представляє логічно розроблені теми, які відмінно допомагають отримати дані знання в доступній формі. Тому, для прослуховування уроків професійного курсу "Java - Advance" бажано мати відповідну підготовку або прослухати курс "Java - Core (Базовий)". Наша компанія "In Alto" надає можливість навіть вдома отримати такі знання. В курсі роз`яснюються передові концепції програмування за допомогою Java , а також використання спеціальних Java-бібліотек. Курс охоплює передову об`єктно орієнтовану концепцію, через універсальні типи і рефлексію, просунуту обробку потоків, послідовні команди і графіку. Викладацький склад має великий досвід роботи із людьми різної вікової категорії та допоможе Вам підготуватися до практичного використання Java для реалізації конкретних рішень. Незалежно, чи вам 10 чи 100, - залишайте заявку на наш курс та переконайтеся про вищесказане на безкоштовному пробному уроці. А далі вирішуйте самі.\n\nНеобхідна підготовка\n\nБажана англійська мова на рівні читання / письма.\nВолодіння основами роботи на ПК на рівні «досвідчений користувач».\nПрослуханий курс "Java - Core"\nОзнайомлення з книгами "Thinking in Java" автора Bruce Eckel або "Бібліотека професіонала" авторів Хорстманн К., Корнелл Г.\nЗнання основних принципів ООП</body></html>', '3499.99', '3999.99'),
(4, 1, 'Course', '<html dir="ltr"><head></head><body contenteditable="true"></body></html>', '0.00', '0.00'),
(5, 1, 'Course 2', '<html dir="ltr"><head></head><body contenteditable="true"></body></html>', '0.00', '0.00');

-- --------------------------------------------------------

--
-- Структура таблиці `courses_executions`
--

CREATE TABLE IF NOT EXISTS `courses_executions` (
  `courses_execution_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Course execution''s ID',
  `course_id` int(11) NOT NULL COMMENT 'Appropriate course''s ID',
  `executor_id` int(11) DEFAULT NULL COMMENT 'Appropriate student''s ID',
  `group_id` int(11) DEFAULT NULL COMMENT 'Appropriate group''s ID',
  `courses_execution_start_date` date DEFAULT NULL COMMENT 'Course training start date',
  `courses_execution_end_date` date DEFAULT NULL COMMENT 'Course training end date',
  `courses_execution_status` int(1) NOT NULL DEFAULT '0' COMMENT 'Course training status (0 - issued, 1 - performed, 2 - checked, 3 - finished)',
  `courses_execution_result` int(3) NOT NULL DEFAULT '0' COMMENT 'Course training result (rating, mark)',
  PRIMARY KEY (`courses_execution_id`),
  UNIQUE KEY `course_id` (`course_id`,`executor_id`,`group_id`,`courses_execution_start_date`),
  KEY `executor_id` (`executor_id`),
  KEY `group_id` (`group_id`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Course executions (training) table';

--
-- Дамп даних таблиці `courses_executions`
--

INSERT INTO `courses_executions` (`courses_execution_id`, `course_id`, `executor_id`, `group_id`, `courses_execution_start_date`, `courses_execution_end_date`, `courses_execution_status`, `courses_execution_result`) VALUES
(8, 2, NULL, 3, '2016-01-03', NULL, 0, 0),
(9, 2, NULL, 4, '2016-01-03', NULL, 0, 0),
(10, 2, 1, 3, '2016-01-03', NULL, 0, 0),
(37, 4, NULL, 10, '2016-01-03', NULL, 0, 0),
(38, 4, 2, 10, '2016-01-03', NULL, 0, 0),
(39, 4, 3, NULL, '2016-01-03', NULL, 0, 0),
(40, 5, NULL, 11, '2016-01-03', NULL, 0, 0),
(41, 5, 2, 11, '2016-01-03', NULL, 0, 0),
(43, 2, 3, 4, '2016-01-03', NULL, 0, 0),
(46, 5, 3, 11, '2016-01-03', NULL, 0, 0);

-- --------------------------------------------------------

--
-- Структура таблиці `groups`
--

CREATE TABLE IF NOT EXISTS `groups` (
  `group_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Group''s ID',
  `course_id` int(11) NOT NULL COMMENT 'Appropriate course''s ID',
  `group_name` varchar(50) COLLATE utf8_bin NOT NULL COMMENT 'Group''s name',
  `group_details` text COLLATE utf8_bin COMMENT 'Group''s information',
  PRIMARY KEY (`group_id`),
  UNIQUE KEY `course_id` (`course_id`,`group_name`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Course groups table';

--
-- Дамп даних таблиці `groups`
--

INSERT INTO `groups` (`group_id`, `course_id`, `group_name`, `group_details`) VALUES
(3, 2, 'Group 11', '<html dir="ltr"><head></head><body contenteditable="true"><p><font face="Segoe UI">Group 11</font></p></body></html>'),
(4, 2, 'Group 1', '<html dir="ltr"><head></head><body contenteditable="true"><p><font face="Segoe UI">Group 1</font></p></body></html>'),
(10, 4, 'Group 1', '<html dir="ltr"><head></head><body contenteditable="true"></body></html>'),
(11, 5, 'Group 2', '<html dir="ltr"><head></head><body contenteditable="true"></body></html>');

-- --------------------------------------------------------

--
-- Структура таблиці `lessons`
--

CREATE TABLE IF NOT EXISTS `lessons` (
  `lesson_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Lesson''s ID',
  `course_id` int(11) NOT NULL COMMENT 'Appropriate course''s ID',
  `lesson_name` varchar(50) COLLATE utf8_bin NOT NULL COMMENT 'Lesson''s name',
  `lesson_details` text COLLATE utf8_bin COMMENT 'Lesson''s information (content)',
  PRIMARY KEY (`lesson_id`),
  UNIQUE KEY `course_id` (`course_id`,`lesson_name`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Course lessons table';

--
-- Дамп даних таблиці `lessons`
--

INSERT INTO `lessons` (`lesson_id`, `course_id`, `lesson_name`, `lesson_details`) VALUES
(6, 4, 'Lesson 2', '<html dir="ltr"><head></head><body contenteditable="true"></body></html>'),
(8, 4, 'Lesson 1', '<html dir="ltr"><head></head><body contenteditable="true"></body></html>'),
(9, 5, 'Lesson 1', '<html dir="ltr"><head></head><body contenteditable="true"></body></html>'),
(10, 5, 'Lesson 2', '<html dir="ltr"><head></head><body contenteditable="true"></body></html>'),
(11, 2, 'Lesson 1', '<html dir="ltr"><head></head><body contenteditable="true"></body></html>'),
(12, 2, 'Lesson 2', '<html dir="ltr"><head></head><body contenteditable="true"></body></html>');

-- --------------------------------------------------------

--
-- Структура таблиці `lessons_executions`
--

CREATE TABLE IF NOT EXISTS `lessons_executions` (
  `lessons_execution_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Lesson execution''s ID',
  `lesson_id` int(11) NOT NULL COMMENT 'Appropriate lesson''s ID',
  `executor_id` int(11) DEFAULT NULL COMMENT 'Appropriate student''s ID',
  `group_id` int(11) DEFAULT NULL COMMENT 'Appropriate group''s ID',
  `lessons_execution_start_date` date DEFAULT NULL COMMENT 'Lesson learning start date',
  `lessons_execution_end_date` date DEFAULT NULL COMMENT 'Lesson learning end date',
  `lessons_execution_status` int(1) NOT NULL DEFAULT '0' COMMENT 'Lesson learning status (0 - issued, 1 - performed, 2 - checked, 3 - finished)',
  `lessons_execution_result` int(3) NOT NULL DEFAULT '0' COMMENT 'Lesson learning result (rating, mark)',
  PRIMARY KEY (`lessons_execution_id`),
  UNIQUE KEY `lesson_id` (`lesson_id`,`executor_id`,`group_id`,`lessons_execution_start_date`),
  KEY `executor_id` (`executor_id`),
  KEY `group_id` (`group_id`)
) ENGINE=InnoDB AUTO_INCREMENT=88 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Lesson executions (learning) table';

--
-- Дамп даних таблиці `lessons_executions`
--

INSERT INTO `lessons_executions` (`lessons_execution_id`, `lesson_id`, `executor_id`, `group_id`, `lessons_execution_start_date`, `lessons_execution_end_date`, `lessons_execution_status`, `lessons_execution_result`) VALUES
(52, 9, NULL, 11, '2016-01-01', NULL, 0, 0),
(53, 9, 2, 11, '2016-01-01', NULL, 0, 0),
(55, 9, NULL, 11, '2016-01-03', NULL, 0, 0),
(56, 9, 2, 11, '2016-01-03', NULL, 0, 0),
(58, 10, NULL, 11, '2016-01-03', NULL, 0, 0),
(59, 10, 2, 11, '2016-01-03', NULL, 0, 0),
(61, 8, NULL, 10, '2016-01-01', NULL, 0, 0),
(62, 8, 2, 10, '2016-01-01', NULL, 0, 0),
(63, 8, 3, NULL, '2016-01-01', NULL, 0, 0),
(64, 8, NULL, 10, '2016-01-03', NULL, 0, 0),
(65, 8, 2, 10, '2016-01-03', NULL, 0, 0),
(66, 8, 3, NULL, '2016-01-03', NULL, 0, 0),
(67, 11, NULL, 4, '2016-01-04', NULL, 0, 0),
(68, 11, 3, 4, '2016-01-04', NULL, 0, 0),
(69, 12, NULL, 3, '2016-01-04', NULL, 0, 0),
(70, 12, 1, 3, '2016-01-04', NULL, 0, 0),
(71, 6, 3, NULL, '2016-01-04', NULL, 0, 0),
(84, 9, 3, 11, '2016-01-01', NULL, 0, 0),
(85, 9, 3, 11, '2016-01-03', NULL, 0, 0),
(86, 10, 3, 11, '2016-01-03', NULL, 0, 0);

-- --------------------------------------------------------

--
-- Структура таблиці `logins`
--

CREATE TABLE IF NOT EXISTS `logins` (
  `login_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Login''s ID',
  `role_id` int(11) NOT NULL COMMENT 'Appropriate role''s ID',
  `login_name` varchar(50) COLLATE utf8_bin NOT NULL COMMENT 'Login''s name',
  `login_password` int(11) NOT NULL COMMENT 'Login''s password hashcode',
  `login_question` varchar(50) COLLATE utf8_bin NOT NULL COMMENT 'Login''s security question',
  `login_answer` int(11) NOT NULL COMMENT 'Login''s security answer hashcode',
  PRIMARY KEY (`login_id`),
  UNIQUE KEY `login_name` (`login_name`),
  KEY `role_id` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Users logins table';

--
-- Дамп даних таблиці `logins`
--

INSERT INTO `logins` (`login_id`, `role_id`, `login_name`, `login_password`, `login_question`, `login_answer`) VALUES
(1, 1, 'Admin', 92668751, 'Color?', 112785),
(2, 2, 'Teacher', -1439577118, 'Color?', 3027034),
(3, 3, 'Student', -1879145925, 'Color?', 113101865);

-- --------------------------------------------------------

--
-- Структура таблиці `projects`
--

CREATE TABLE IF NOT EXISTS `projects` (
  `project_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Project''s ID',
  `author_id` int(11) NOT NULL COMMENT 'Appropriate author''s ID',
  `project_name` varchar(50) COLLATE utf8_bin NOT NULL COMMENT 'Project''s name',
  `project_details` text COLLATE utf8_bin COMMENT 'Project''s information',
  PRIMARY KEY (`project_id`),
  UNIQUE KEY `author_id` (`author_id`,`project_name`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='School projects table';

--
-- Дамп даних таблиці `projects`
--

INSERT INTO `projects` (`project_id`, `author_id`, `project_name`, `project_details`) VALUES
(2, 1, 'Java-реалізація школи InAlto', '<html dir="ltr"><head></head><body contenteditable="true">Створення прикладної програми на Java, що реалізує можливість реєстрації викладачів, студентів, замовлення та здійснення онлайн-навчання в онлайн-академії InAlto.&nbsp;</body></html>'),
(6, 1, 'Багатомовний сайт InAlto', '<html dir="ltr"><head></head><body contenteditable="true"><p><span style="font-family: Helvetica, Arial, sans-serif; font-size: 12px; line-height: 12px; text-align: justify; widows: 1; background-color: rgb(238, 238, 238);">Створення багатомовного сайту онлайн-академії&nbsp;</span><span style="margin: 0px; padding: 0px; border: 0px; font-weight: 700; font-stretch: inherit; font-size: 12px; line-height: 12px; font-family: Helvetica, Arial, sans-serif; vertical-align: baseline; text-align: justify; widows: 1; background-color: rgb(238, 238, 238);">InAlto</span><span style="font-family: Helvetica, Arial, sans-serif; font-size: 12px; line-height: 12px; text-align: justify; widows: 1; background-color: rgb(238, 238, 238);">.</span></p></body></html>'),
(8, 1, 'Project', '<html dir="ltr"><head></head><body contenteditable="true"></body></html>');

-- --------------------------------------------------------

--
-- Структура таблиці `projects_executions`
--

CREATE TABLE IF NOT EXISTS `projects_executions` (
  `projects_execution_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Project execution''s ID',
  `project_id` int(11) NOT NULL COMMENT 'Appropriate project''s ID',
  `executor_id` int(11) NOT NULL COMMENT 'Appropriate student''s ID',
  `projects_execution_start_date` date DEFAULT NULL COMMENT 'Project participation''s start date',
  `projects_execution_end_date` date DEFAULT NULL COMMENT 'Project participation''s end date',
  `projects_execution_status` int(1) NOT NULL DEFAULT '0' COMMENT 'Project participation''s status (0 - invited, 1 - performed, 2 - checked, 3 - finished)',
  `projects_execution_result` int(3) NOT NULL DEFAULT '0' COMMENT 'Project participation''s result (rating, mark)',
  PRIMARY KEY (`projects_execution_id`),
  UNIQUE KEY `project_id` (`project_id`,`executor_id`),
  KEY `executor_id` (`executor_id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Project executions (participation) table';

--
-- Дамп даних таблиці `projects_executions`
--

INSERT INTO `projects_executions` (`projects_execution_id`, `project_id`, `executor_id`, `projects_execution_start_date`, `projects_execution_end_date`, `projects_execution_status`, `projects_execution_result`) VALUES
(15, 2, 3, '2015-12-27', NULL, 1, 0),
(17, 2, 2, '2015-12-27', NULL, 1, 0),
(27, 6, 2, '2016-01-03', NULL, 0, 0),
(28, 6, 3, '2016-01-03', NULL, 0, 0),
(36, 8, 2, '2016-01-03', NULL, 0, 0);

-- --------------------------------------------------------

--
-- Структура таблиці `roles`
--

CREATE TABLE IF NOT EXISTS `roles` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Role''s ID',
  `role_name` varchar(50) COLLATE utf8_bin NOT NULL COMMENT 'Role''s name',
  `role_details` text COLLATE utf8_bin COMMENT 'Role''s Information',
  PRIMARY KEY (`role_id`),
  UNIQUE KEY `role_name` (`role_name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Users roles (rights) table';

--
-- Дамп даних таблиці `roles`
--

INSERT INTO `roles` (`role_id`, `role_name`, `role_details`) VALUES
(1, 'Administrator', 'InAlto School Administrator'),
(2, 'Teacher', 'InAlto School Teacher'),
(3, 'Student', 'InAlto School Student');

-- --------------------------------------------------------

--
-- Структура таблиці `students`
--

CREATE TABLE IF NOT EXISTS `students` (
  `student_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Student''s ID',
  `group_id` int(11) DEFAULT NULL COMMENT 'Appropriate group''s ID',
  `user_id` int(11) DEFAULT NULL COMMENT 'Appropriate user''s ID',
  PRIMARY KEY (`student_id`),
  UNIQUE KEY `group_id` (`group_id`,`user_id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Groups students table';

--
-- Дамп даних таблиці `students`
--

INSERT INTO `students` (`student_id`, `group_id`, `user_id`) VALUES
(3, 3, 1),
(21, 4, 3),
(18, 10, 2),
(19, 11, 2),
(26, 11, 3);

-- --------------------------------------------------------

--
-- Структура таблиці `tasks`
--

CREATE TABLE IF NOT EXISTS `tasks` (
  `task_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Task''s ID',
  `lesson_id` int(11) DEFAULT NULL COMMENT 'Appropriate lesson''s ID',
  `project_id` int(11) DEFAULT NULL COMMENT 'Appropriate project''s ID',
  `task_name` varchar(50) COLLATE utf8_bin NOT NULL COMMENT 'Task''s name',
  `task_details` text COLLATE utf8_bin COMMENT 'Task''s information (statement)',
  PRIMARY KEY (`task_id`),
  UNIQUE KEY `lesson_id` (`lesson_id`,`project_id`,`task_name`),
  KEY `project_id` (`project_id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Lesson or project tasks table';

--
-- Дамп даних таблиці `tasks`
--

INSERT INTO `tasks` (`task_id`, `lesson_id`, `project_id`, `task_name`, `task_details`) VALUES
(5, NULL, 2, 'Завдання 2', 'Завдання 2 - Текст'),
(9, NULL, 2, 'Завдання 1', '<html dir="ltr"><head></head><body contenteditable="true"></body></html>'),
(10, NULL, 6, 'Завдання 1', '<html dir="ltr"><head></head><body contenteditable="true"><p><font face="Segoe UI">Завдання 1</font></p></body></html>'),
(11, NULL, 6, 'Завдання 2', '<html dir="ltr"><head></head><body contenteditable="true"><p><font face="Segoe UI">Завдання 2</font></p></body></html>'),
(14, NULL, 8, 'Task 1', '<html dir="ltr"><head></head><body contenteditable="true"></body></html>'),
(15, NULL, 8, 'Task 2', '<html dir="ltr"><head></head><body contenteditable="true"></body></html>'),
(16, 12, NULL, 'Task 1', '<html dir="ltr"><head></head><body contenteditable="true"><p><font face="Segoe UI">Task 1</font></p></body></html>'),
(17, 12, NULL, 'Task 2', '<html dir="ltr"><head></head><body contenteditable="true"><p><font face="Segoe UI">Task 2</font></p></body></html>'),
(19, 8, NULL, 'Task 1', '<html dir="ltr"><head></head><body contenteditable="true"></body></html>'),
(20, 8, NULL, 'Task 2', '<html dir="ltr"><head></head><body contenteditable="true"></body></html>'),
(21, 9, NULL, 'Task 1', '<html dir="ltr"><head></head><body contenteditable="true"></body></html>'),
(22, 10, NULL, 'Task 2', '<html dir="ltr"><head></head><body contenteditable="true"></body></html>'),
(23, 6, NULL, 'Task 11', '<html dir="ltr"><head></head><body contenteditable="true"></body></html>'),
(24, 9, NULL, 'Task 2', '<html dir="ltr"><head></head><body contenteditable="true"></body></html>'),
(25, 11, NULL, 'Task 1', '<html dir="ltr"><head></head><body contenteditable="true"></body></html>');

-- --------------------------------------------------------

--
-- Структура таблиці `tasks_executions`
--

CREATE TABLE IF NOT EXISTS `tasks_executions` (
  `tasks_execution_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Task execution''s ID',
  `task_id` int(11) NOT NULL COMMENT 'Appropriate task''s ID',
  `executor_id` int(11) NOT NULL COMMENT 'Appropriate executor''s ID',
  `tasks_execution_start_date` date DEFAULT NULL COMMENT 'Task execution''s start date',
  `tasks_execution_end_date` date DEFAULT NULL COMMENT 'Task execution''s end date',
  `tasks_execution_status` int(1) NOT NULL DEFAULT '0' COMMENT 'Task execution''s status (0 - issued, 1 - performed, 2 - checked, 3 - finished)',
  `tasks_execution_result` int(3) NOT NULL DEFAULT '0' COMMENT 'Task execution''s result (rating, mark)',
  PRIMARY KEY (`tasks_execution_id`),
  UNIQUE KEY `task_id` (`task_id`,`executor_id`),
  KEY `executor_id` (`executor_id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Task executions table';

--
-- Дамп даних таблиці `tasks_executions`
--

INSERT INTO `tasks_executions` (`tasks_execution_id`, `task_id`, `executor_id`, `tasks_execution_start_date`, `tasks_execution_end_date`, `tasks_execution_status`, `tasks_execution_result`) VALUES
(2, 5, 3, '2015-12-29', NULL, 0, 0),
(3, 5, 2, '2015-12-29', NULL, 0, 0),
(20, 14, 2, '2016-01-03', NULL, 0, 0),
(22, 15, 2, '2016-01-03', NULL, 0, 0),
(26, 19, 3, '2016-01-04', NULL, 1, 0),
(28, 19, 2, '2016-01-04', NULL, 0, 0),
(29, 21, 2, '2016-01-04', NULL, 0, 0),
(31, 22, 2, '2016-01-04', NULL, 0, 0),
(33, 23, 3, '2016-01-04', NULL, 0, 0),
(36, 22, 3, '2016-01-04', NULL, 0, 0),
(37, 24, 3, '2016-01-04', NULL, 0, 0),
(38, 25, 3, '2016-01-04', NULL, 0, 0);

-- --------------------------------------------------------

--
-- Структура таблиці `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'User''s ID',
  `login_id` int(11) NOT NULL COMMENT 'Appropriate login''s ID',
  `user_firstname` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT 'User''s first name',
  `user_lastname` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT 'User ''s last name',
  `user_birthday` date DEFAULT NULL COMMENT 'User''s birthday',
  `user_address` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT 'User''s address',
  `user_phone` varchar(13) COLLATE utf8_bin DEFAULT NULL COMMENT 'User''s phone',
  `user_email` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT 'User''s email',
  `user_skype` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT 'User''s skype login',
  `user_details` text COLLATE utf8_bin COMMENT 'User''s information',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `login_id` (`login_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Users data table';

--
-- Дамп даних таблиці `users`
--

INSERT INTO `users` (`user_id`, `login_id`, `user_firstname`, `user_lastname`, `user_birthday`, `user_address`, `user_phone`, `user_email`, `user_skype`, `user_details`) VALUES
(1, 1, 'Oleg', 'Volkov', '1974-04-25', 'Kharkiv', '+380504007482', 'novasoft.print@gmail.com', 'novasoft.print', 'www.oleg-volkov.bz.ua'),
(2, 2, 'Mykola', 'Kotsuba', NULL, 'Ukraine', '+380632080781', 'mykolakotsyuba@gmail.com', 'kotsubam', 'www.inaltostudy.info'),
(3, 3, 'Alex', 'Colt', '1979-03-25', 'Kharkiv', '+380634424940', 'rsoftdesign@ukr.net', 'rsoftdesign', 'Java Basic');

-- --------------------------------------------------------

--
-- Структура таблиці `users_payments`
--

CREATE TABLE IF NOT EXISTS `users_payments` (
  `users_payment_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'User''s payment ID',
  `user_id` int(11) NOT NULL COMMENT 'Appropriate user ID',
  `course_id` int(11) NOT NULL COMMENT 'Appropriate course ID',
  `users_payment_date` date NOT NULL COMMENT 'Appropriate date of payment',
  `users_payment_sum` decimal(7,2) NOT NULL COMMENT 'Sum of payment',
  PRIMARY KEY (`users_payment_id`),
  UNIQUE KEY `user_id` (`user_id`,`course_id`,`users_payment_date`),
  KEY `course_id` (`course_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Users payments table';

--
-- Дамп даних таблиці `users_payments`
--

INSERT INTO `users_payments` (`users_payment_id`, `user_id`, `course_id`, `users_payment_date`, `users_payment_sum`) VALUES
(1, 3, 2, '2016-01-06', '200.00'),
(4, 3, 4, '2016-01-06', '200.00');

--
-- Обмеження зовнішнього ключа збережених таблиць
--

--
-- Обмеження зовнішнього ключа таблиці `courses`
--
ALTER TABLE `courses`
  ADD CONSTRAINT `courses_ibfk_1` FOREIGN KEY (`author_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Обмеження зовнішнього ключа таблиці `courses_executions`
--
ALTER TABLE `courses_executions`
  ADD CONSTRAINT `courses_executions_ibfk_1` FOREIGN KEY (`course_id`) REFERENCES `courses` (`course_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `courses_executions_ibfk_2` FOREIGN KEY (`executor_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `courses_executions_ibfk_3` FOREIGN KEY (`group_id`) REFERENCES `groups` (`group_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Обмеження зовнішнього ключа таблиці `groups`
--
ALTER TABLE `groups`
  ADD CONSTRAINT `groups_ibfk_1` FOREIGN KEY (`course_id`) REFERENCES `courses` (`course_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Обмеження зовнішнього ключа таблиці `lessons`
--
ALTER TABLE `lessons`
  ADD CONSTRAINT `lessons_ibfk_1` FOREIGN KEY (`course_id`) REFERENCES `courses` (`course_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Обмеження зовнішнього ключа таблиці `lessons_executions`
--
ALTER TABLE `lessons_executions`
  ADD CONSTRAINT `lessons_executions_ibfk_1` FOREIGN KEY (`lesson_id`) REFERENCES `lessons` (`lesson_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `lessons_executions_ibfk_2` FOREIGN KEY (`executor_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `lessons_executions_ibfk_3` FOREIGN KEY (`group_id`) REFERENCES `groups` (`group_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Обмеження зовнішнього ключа таблиці `logins`
--
ALTER TABLE `logins`
  ADD CONSTRAINT `logins_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `roles` (`role_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Обмеження зовнішнього ключа таблиці `projects`
--
ALTER TABLE `projects`
  ADD CONSTRAINT `projects_ibfk_1` FOREIGN KEY (`author_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Обмеження зовнішнього ключа таблиці `projects_executions`
--
ALTER TABLE `projects_executions`
  ADD CONSTRAINT `projects_executions_ibfk_1` FOREIGN KEY (`project_id`) REFERENCES `projects` (`project_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `projects_executions_ibfk_2` FOREIGN KEY (`executor_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Обмеження зовнішнього ключа таблиці `students`
--
ALTER TABLE `students`
  ADD CONSTRAINT `students_ibfk_1` FOREIGN KEY (`group_id`) REFERENCES `groups` (`group_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `students_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Обмеження зовнішнього ключа таблиці `tasks`
--
ALTER TABLE `tasks`
  ADD CONSTRAINT `tasks_ibfk_1` FOREIGN KEY (`lesson_id`) REFERENCES `lessons` (`lesson_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `tasks_ibfk_2` FOREIGN KEY (`project_id`) REFERENCES `projects` (`project_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Обмеження зовнішнього ключа таблиці `tasks_executions`
--
ALTER TABLE `tasks_executions`
  ADD CONSTRAINT `tasks_executions_ibfk_1` FOREIGN KEY (`task_id`) REFERENCES `tasks` (`task_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `tasks_executions_ibfk_2` FOREIGN KEY (`executor_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Обмеження зовнішнього ключа таблиці `users`
--
ALTER TABLE `users`
  ADD CONSTRAINT `users_ibfk_1` FOREIGN KEY (`login_id`) REFERENCES `logins` (`login_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Обмеження зовнішнього ключа таблиці `users_payments`
--
ALTER TABLE `users_payments`
  ADD CONSTRAINT `users_payments_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `users_payments_ibfk_2` FOREIGN KEY (`course_id`) REFERENCES `courses` (`course_id`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
