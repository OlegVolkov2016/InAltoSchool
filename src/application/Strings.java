package application;

public enum Strings {
	
	// Strings
	MainTitle("Ласкаво просимо в школу InAlto","Welcome to InAlto School"),
	
	// Resources
	MainApplicationCSS("/application/inaltoschool.css"),
//	MainIcon_id("/application/InAltoSchool.png"),
	MainIcon_id("file:resources/InAltoSchool.png"),
	MainRootLayout("/application/view/RootLayout.fxml"),
	MainSplitPane("/application/view/MainSplitPane.fxml"),
	TextAreaDialog("/application/view/TextAreaDialog.fxml"),
	WebViewDialog("/application/view/WebViewDialog.fxml"),
	ContactsDialog("/application/view/ContactsDialog.fxml"),
	CoursesSplitPane("/application/view/CoursesSplitPane.fxml"),
	CourseDialog("/application/view/CourseDialog.fxml"),
	StudentsCourseDialog("/application/view/StudentsCourseDialog.fxml"),
	LessonsSplitPane("/application/view/LessonsSplitPane.fxml"),
	ProjectsSplitPane("/application/view/ProjectsSplitPane.fxml"),
	ProjectDialog("/application/view/ProjectDialog.fxml"),
	StudentsProjectDialog("/application/view/StudentsProjectDialog.fxml"),
	StatusDialog("/application/view/StatusDialog.fxml"),
	ProjectsTasksSplitPane("/application/view/ProjectsTasksSplitPane.fxml"),
	TaskDialog("/application/view/TaskDialog.fxml"),
	TeachersSplitPane("/application/view/TeachersSplitPane.fxml"),
	StudentsSplitPane("/application/view/StudentsSplitPane.fxml"),
	UserDialog("/application/view/UserDialog.fxml"),
	UserPaymentDialog("/application/view/UserPaymentDialog.fxml"),
	AuthorizationDialog("/application/view/AuthorizationDialog.fxml"),
	ForgotDialog("/application/view/ForgotDialog.fxml"),
	RegistrationDialog("/application/view/RegistrationDialog.fxml"),
	UsersTableSplitPane("/application/view/UsersTableSplitPane.fxml"),

	// Gmail
	GmailUsername("novasoftprint@gmail.com"),
	GmailPassword("20grafitec12"),
	
	// Menu
	AboutMenu("Про нас","About us"),
	WhoAreWeMenu("Хто ми?","Who are we?"),
	AdvantagesMenu("Переваги співпраці з нами","Advantages of cooperation with us"),
	ContactsMenu("Контакти","Contacts"),
	ExitMenu("Вихід","Exit"),
	EducationMenu("Навчання","Education"),
	CoursesMenu("Курси","Courses"),
	ProjectsMenu("Проекти","Projects"),
	UsersMenu("Учасники","Users"),
	TeachersMenu("Викладачі","Teachers"),
	StudentsMenu("Студенти","Students"),
	LoginMenu("Логін","Login"),
	AuthorizationMenu("Авторизація","Authorization"),
	RegistrationMenu("Реєстрація","Registration"),
	EditProfileMenu("Редагувати профіль","Edit profile"),
	LanguageMenu("Мова(Language)","Language(Мова)"),
	LanguageUKMenu("Українська","Українська"),
	LanguageENMenu("English","English"),
	AdministartionMenu("Адміністрування","Administration"),
	TablesMenu("Бази даних","Tables"),
	RolesTableMenu("Ролі","Roles"),
	LoginsTableMenu("Логіни","Logins"),
	UsersTableMenu("Користувачі","Users"),
	ProjectsTableMenu("Проекти","Projects"),
	ProjectsExecutionsTableMenu("Участь в проектах","Projects participation"),
	CoursesTableMenu("Курси","Courses"),
	CoursesExecutionsTableMenu("Проходження курсів","Courses training"),
	LessonsTableMenu("Уроки","Lessons"),
	LessonsExecutionsTableMenu("Вивчення уроків","Lessons learning"),
	TasksTableMenu("Завдання","Tasks"),	
	TasksExecutionsTableMenu("Виконання завданнь","Tasks execution"),	
	UserPaymentsTableMenu("Оплати студентів","Students payments"),
	
	LeftStatus("Авторизуйтесь або зареєструйтесь в системі","Authorize or register in system"),
	RightStatus("© In Alto Study, 2016","© In Alto Study, 2016"),
	
	// Root layout
	OKButton("OK","OK"),
	CancelButton("Відмінити","Cancel"),
	SendButton("Відправити","Send"),
	NewGroupButton("Створити групу","New group"),
	AddGroupButton("Додати групу","Add group"),
	EditGroupButton("Редагувати групу","Edit group"),
	AddButton("Додати","Add"),
	AddStudentButton("Додати студента","Add student"),
	RemoveButton("Видалити","Remove"),
	StatusButton("Статус","Status"),
	NewButton("Новий...","New..."),
	EditButton("Змінити...","Edit..."),
	DeleteButton("Видалити","Delete"),
	TasksButton("Завдання >","Tasks >"),
	ProjectsButton("< До проектів","< To projects"),
	LessonsButton("Уроки >","Lessons >"),
	LessonsBackButton("< До уроків","< To lessons"),
	CoursesButton("< До курсів","< To courses"),
	DetailsButton("Додатково >","Details >"),
	CoursesToButton("До курсу >","To course >"),
	ProjectsToButton("До проекту >","To project >"),
	LoginButton("Авторизація >","Autorization >"),
	
	ErrorTitle("Помилка","Error"),
	InputError("Помилка вводу!","Input error!"),
	InitError("Помилка ініціалізації!","Initializing error!"),
	
	EmptyError("Заповніть це поле!","Fill this field!"),
	EmailError("Невірна адреса email!","Wrong email address!"),
	LoginError("Невірне ім'я користувача або пароль!","Wrong username or password!"),
	QuestionError("Контрольне питання не задане!","Security question is not set!"),
	AnswerError("Невірна контрольна відповідь!","Wrong security answer!"),
	PasswordError("Паролі не співпадають!","Passwords do not match!"),
	FormatError("Невірний формат!","Wrong format!"),
	SelectError("Помилка запросу до бази!","Select from database error!"),
	InsertError("Помилка додавання в базу!","Insert database error!"),
	UpdateError("Помилка оновлення бази!","Update database error!"),
	DeleteErrorHeader("Пoмилка видалення","Delete error"),
	DeleteErrorContent("Помилка видалення з бази!","Delete database error!"),
	
	InformationTitle("Інформація","Information"),
	MailHeader("Відправка email повідомлення","Sending email message"),
	MailContent("Повідомлення було відправлено успішно.","Message was sent successfully."),
	AuthorizationHeader("Авторизація користувача","User authorization"),
	AuthorizationContent("Авторизація пройшла успішно.","Authorization is successfull."),
	RegistrationHeader("Реєстрація користувача","User registration"),
	RegistrationContent("Реєстрація пройшла успішно.","Registration is successfull."),
	EditProfileHeader("Редагування профілю","Edit profile"),
	EditProfileContent("Інформація оновлена успішно.","Information was updated successfully."),
	AccessFailHeader("Відсутність дозволу","Permission is absent"),
	AccessFailContent("Ви не маєте доступу до інформації цього об'єкту. \nАвторизуйтесь та зверніться до автору.",
		"You have not permission to this object information. \nPlease authorize and contact the author."),
	
	ConfirmTitle("Підтвердження","Confirmation"),
	ConfirmDeleteHeader("Підтвердження видалення","Delete confirmation"),
	ConfirmDeleteContent("Ви впевнено бажаєте видалити ","Are you sure to delete "),
	
	// Main SplitPane
	MainSplitPaneTitle("ОНЛАЙН IT АКАДЕМІЯ","ONLINE IT ACADEMY"),
	AllItems("Усі","All"),
	
	// Courses SplitPane
	LabelCourseTitle("Курси школи:","School courses:"),
	LabelCourseDetails("Опис курсу:","Course information:"),
	LabelCourseStudents("Групи і студенти:","Groups and students:"),
	ColumnCourseName("Назва курсу","Course name"),
	ColumnCourseAuthor("Автор курсу","Course author"),
	ColumnCourseStudentName("Група або студент","Group or student"),
	ColumnCourseStudentDate("Дата початку","Start date"),
	LabelCourseRoot("Індивідуальне навчання","Individual studying"),
	
	// Course Dialog
	CourseDialogTitle("Курс школи","School course"),
	LabelCourseAuthor("Автор курсу:","Course author:"),
	LabelCourseName("Назва курсу:","Course name:"),
	LabelCourseGroupPrice("Вартість групового навчання:","Group studying price:"),
	LabelCourseIndividualPrice("Вартість індивідуального навчання:","Individual studying price:"),
	TextCourseName("Введіть назву курсу","Input course name"),
	TextCourseGroupPrice("Введіть вартість","Input price"),
	TextCourseIndividualPrice("Введіть вартість","Input price"),
	TextCourseDetails("Введіть опис курсу","Input course information"),
	
	// Group Dialog
	GroupDialogTitle("Група курсу","Course group"),
	LabelGroupCourse("Курс школи:","School course:"),
	LabelGroupName("Назва групи:","Group name:"),
	LabelGroupDetails("Опис групи:","Group details:"),
	TextGroupName("Введіть назву групи","Input group name"),
	TextGroupDetails("Введіть опис групи","Input group details"),
	
	// Students Course Dialog
	StudentsCourseDialogTitle("Додати студента","Add student"),
	LabelCoursesStudents("Студенти:","Students:"),
	LabelCoursesGroups("Додати в групу:","Add to group:"),
	
	// Lessons SplitPane
	LabelLessonTitle("Уроки курсу:","Course lessons:"),
	LabelLessonDetails("Зміст уроку:","Lesson content:"),
	ColumnLessonName("Назва уроку","Lesson name"),
	
	// Lesson Dialog
	LessonDialogTitle("Урок курсу","Course lesson"),
	LabelLessonName("Назва уроку:","Lesson name:"),
	TextLessonName("Введіть назву уроку","Input lesson name"),
	TextLessonDetails("Введіть текст уроку","Input lesson content"),
	
	// Students Lesson Dialog
	GroupsLessonDialogTitle("Додати групу","Add group"),
	StudentsLessonDialogTitle("Додати студента","Add student"),
	StudentsLessonTaskDialogTitle("Додати виконавця","Add executor"),
	LabelLessonsGroups("Групи:","Gropus:"),
		
	// Projects SplitPane
	LabelProjectTitle("Проекти школи:","School projects:"),
	LabelAuthors("Автори:","Authors:"),
	LabelInformation("Інформація:","Information:"),
	LabelProjectDetails("Опис проекту:","Project information:"),
	LabelProjectStudents("Учасники:","Students:"),
	ColumnProjectName("Назва проекту","Project name"),
	ColumnProjectAuthor("Автор проекту","Project author"),
	ColumnProjectStudentName("Учасник","Student"),
	ColumnProjectStudentDate("Дата початку","Start date"),
	
	// Project Dialog
	ProjectDialogTitle("Проект школи","School project"),
	LabelProjectAuthor("Автор проекту:","Project author:"),
	LabelProjectName("Назва проекту:","Project name:"),
	TextProjectName("Введіть назву проекту","Input project name"),
	TextProjectDetails("Введіть опис проекту","Input project information"),
	
	// Students Project Dialog
	StudentsProjectDialogTitle("Додати учасника","Add student"),
	StudentsProjectTaskDialogTitle("Додати виконавця","Add executor"),
	
	// Status Dialog
	StatusDialogTitle("Перегляд статусу","Status view"),
	LabelProjectStudent("Учасник:","Student:"),
	LabelCourseStudent("Студент:","Student:"),
	LabelStartDate("Дата початку роботи:","Date of start working:"),
	LabelEndDate("Дата закінчення роботи:","Date of end working:"),
	LabelResult("Результат (оцінка) автора:","Result (mark) of author:"),
	LabelStatus("Статус:","Status:"),
	TextStartDate("Введіть дату початку","Input start date"),
	TextEndDate("Введіть дату закінчення","Input end date"),
	TextResult("Введіть результат (оцінку)","Input result (mark)"),
	RadioIssued("Надано","Issued"),
	RadioPerformed("Прийнято","Performed"),
	RadioChecked("Виконано","Checked"),
	RadioFinished("Закінчено","Finished"),
	
	// Project Tasks SplitPane
	LabelTaskTitle("Список завдань:","Tasks list:"),
	LabelAuthor("Автор:","Author:"),
	LabelTaskDetails("Текст завдання:","Task conditions:"),
	LabelExecutors("Виконавці:","Executors:"),
	ColumnTaskName("Назва завдання","Task name"),
	ColumnExecutorName("Виконавець","Executor"),
	ColumnExecutorDate("Дата початку","Start date"),
	
	// Project Task Dialog
	TaskDialogTitle("Завдання уроку чи проекту","Project or lesson task"),
	LabelTaskName("Назва завдання:","Task name:"),
	TextTaskName("Введіть назву завдання","Input task name"),
	TextTaskDetails("Введіть текст завдання","Input task conditions"),
	
	// Teachers SplitPane
	LabelTeacherTitle("Викладачі школи:","School teachers:"),
	ColumnFirstName("Ім'я","First name"),
	ColumnLastName("Прізвище","Second name"),
	
	// Teachers SplitPane
	LabelStudentTitle("Студенти і учасники школи:","School students and executors:"),
	ColumnBirthday("Дата народження","Birthday date"),
	LabelCoursePayments("Оплати:","Payments:"),
	
	// User dialog
	TeacherDialogTitle("Викладач школи","School teacher"),
	StudentDialogTitle("Студент школи","School student"),
	UserDialogTitle("Користувач школи","School user"),
	TeacherLabelFirstName("Ім'я викладача:","Teacher first name:"),
	TeacherLabelLastName("Прізвище викладача:","Teacher last name:"),
	StudentLabelFirstName("Ім'я студента/учасника:","Student/executor first name:"),
	StudentLabelLastName("Прізвище студента/учасника:","Student/executor last name:"),
	ColumnCourseGroup("Група","Group"),
	ColumnCourseLessons("Уроки","Lessons"),
	
	// UserPayment Dialog
	UserPaymentDialogTitle("Оплата","Payment"),
	LabelPaymentDate("Дата оплати","Date of payment"),
	LabelPaymentSum("Сума оплати","Sum of payment"),
	TextPaymentDate("Введіть дату оплати","Input date of payment"),
	TextPaymentSum("Введіть суму оплати","Input sum of payment"),
	
	// WhoAreWe Dialog
	WhoAreWeDialogTitle("Хто ми?","Who are we?"),
	WhoAreWeDialogTextArea("<html dir=\"ltr\"><head></head><body contenteditable=\"true\"><p style=\"box-sizing: border-box; margin: 0px 0px 10px; color: rgb(162, 162, 162); font-family: 'Noto Serif', serif; font-size: 16px; font-style: italic; line-height: 25.6px; widows: 1; background-color: rgb(255, 255, 255);\"><strong class=\"cl123\" style=\"box-sizing: border-box; color: rgb(104, 164, 196);\">Компанія \"In Alto Online It Academy\"</strong>&nbsp;пропонує навчитися створювати програми, не виходячи з дому, у приємній для Вас атмосфері, за допомогою різних мов програмування.</p><p style=\"box-sizing: border-box; margin: 0px 0px 10px; color: rgb(162, 162, 162); font-family: 'Noto Serif', serif; font-size: 16px; font-style: italic; line-height: 25.6px; widows: 1; background-color: rgb(255, 255, 255);\"><br style=\"box-sizing: border-box;\"><em style=\"box-sizing: border-box;\">Уроки розраховані на&nbsp;<strong class=\"cl123\" style=\"box-sizing: border-box; color: rgb(104, 164, 196);\">онлайн (віддалене) навчання&nbsp;</strong>, що дасть змогу більше часу приділити вивченню програми курсу. Ви навчитеся програмувати, використовуючи такі добре відомі мови, як: Java, С#, php, мова-розмітка html, JavaScript і т.д. Наші викладачі мають великий досвід програмування як в українських, так і закордонних компаніях.</em></p><p style=\"box-sizing: border-box; margin: 0px 0px 10px; color: rgb(162, 162, 162); font-family: 'Noto Serif', serif; font-size: 16px; font-style: italic; line-height: 25.6px; widows: 1; background-color: rgb(255, 255, 255);\"><em style=\"box-sizing: border-box;\">Компанія \"In Alto\" постала у відповідь на постійне зростання потреби у програмістах високого рівня з боку ІТ індустрії. Багаторічний досвід викладання дисциплін в галузі інформатики та постійна співпраця з бізнесом дали змогу випрацювати оптимальну програму курсів підготовки ІТ фахівців.</em></p><p style=\"box-sizing: border-box; margin: 0px 0px 10px; color: rgb(162, 162, 162); font-family: 'Noto Serif', serif; font-size: 16px; font-style: italic; line-height: 25.6px; widows: 1; background-color: rgb(255, 255, 255);\"><em style=\"box-sizing: border-box;\">Нашою метою є підготовка спеціалістів в галузі програмування, які після завершення навчання могли б одразу стати до роботи в ІТ компаніях. Наші курси орієнтовані як на людей, що мають базові знання в програмуванні, так і на новачків. Зміст та форма подачі матеріалу може адаптуватись відповідно до середнього рівня студента, що дає змогу найкращим чином донести матеріал до слухачів.</em></p><p style=\"box-sizing: border-box; margin: 0px 0px 10px; color: rgb(162, 162, 162); font-family: 'Noto Serif', serif; font-size: 16px; font-style: italic; line-height: 25.6px; widows: 1; background-color: rgb(255, 255, 255);\"><em style=\"box-sizing: border-box;\">Викладачами є програмісти - педагоги відомих ІТ компаній, показником успішності роботи яких є значний відсоток працевлаштованих за фахом студентів. Ми також запрошуємо до викладацької діяльності спеціалістів, які працюють в індустрії ІТ, що дає змогу налагоджувати обмін досвідом та дотримуватись актуальності навчальних програм.</em></p><p style=\"box-sizing: border-box; margin: 0px 0px 10px; color: rgb(162, 162, 162); font-family: 'Noto Serif', serif; font-size: 16px; font-style: italic; line-height: 25.6px; widows: 1; background-color: rgb(255, 255, 255);\"><em style=\"box-sizing: border-box;\">Слухачі отримають змогу прослухати курси апробовані впродовж кількох років викладання та складені відповідного до вимог бізнесу, починаючи з задач актуальних на сучасному етапі розвитку ІТ технологій до типових завдань на співбесідах при прийомі на роботу.</em></p><p style=\"box-sizing: border-box; margin: 0px 0px 10px; color: rgb(162, 162, 162); font-family: 'Noto Serif', serif; font-size: 16px; font-style: italic; line-height: 25.6px; widows: 1; background-color: rgb(255, 255, 255);\"><em style=\"box-sizing: border-box;\">Індустрія інформаційних технологій постійно перебуває в динамічному розвитку. Підготовка фахівців в цій галузі мусить також відповідати вимогам часу</em></p><p><br style=\"box-sizing: border-box; color: rgb(162, 162, 162); font-family: 'Noto Serif', serif; font-size: 16px; font-style: italic; line-height: 25.6px; text-align: justify; widows: 1; background-color: rgb(255, 255, 255);\"></p><p class=\"lead\" style=\"box-sizing: border-box; margin: 0px 0px 20px; font-size: 21px; line-height: 1.4; color: rgb(162, 162, 162); font-family: 'Noto Serif', serif; font-style: italic; widows: 1; background-color: rgb(255, 255, 255);\"><span class=\"pullquote-left\" style=\"box-sizing: border-box; display: block; color: rgb(25, 17, 86); font-size: 14px; line-height: 1.6em; padding-left: 20px;\"></span></p><blockquote style=\"box-sizing: border-box; padding: 10px 20px; margin: 0px 0px 20px; font-size: 16px; border-left-width: 5px; border-style: none none none solid; border-left-color: rgb(238, 238, 238); font-family: 'Noto Serif', serif; font-style: italic; color: rgb(162, 162, 162); line-height: 25.6px; text-align: justify; widows: 1; background-color: rgb(255, 255, 255);\">&nbsp;А, і ще,&nbsp;<strong class=\"cl123\" style=\"box-sizing: border-box; color: rgb(104, 164, 196);\">ПРОБНЕ ЗАНЯТТЯ Є БЕЗКОШТОВНИМ</strong>, де ви зможете ознайомитися з викладачем та програмою і тільки після того оформляти договір.</blockquote><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p></body></html>",
		"<html dir=\"ltr\"><head></head><body contenteditable=\"true\"><p style=\"box-sizing: border-box; margin: 0px 0px 10px; color: rgb(162, 162, 162); font-family: 'Noto Serif', serif; font-size: 16px; font-style: italic; line-height: 25.6px; widows: 1; background-color: rgb(255, 255, 255);\"><strong class=\"cl123\" style=\"box-sizing: border-box; color: rgb(104, 164, 196);\">Our company \"In Alto Online IT Academy\"</strong>&nbsp;offers to study a creation of programmes at home, in a pleasant atmosphare for You with the aid of different languages of programming.</p><p style=\"box-sizing: border-box; margin: 0px 0px 10px; color: rgb(162, 162, 162); font-family: 'Noto Serif', serif; font-size: 16px; font-style: italic; line-height: 25.6px; widows: 1; background-color: rgb(255, 255, 255);\"><br style=\"box-sizing: border-box;\"><em style=\"box-sizing: border-box;\">The classes are expected on the&nbsp;<strong class=\"cl123\" style=\"box-sizing: border-box; color: rgb(104, 164, 196);\">online (remote) studying.&nbsp;</strong>It will enable to give much time for studying of cource`s programme. You will study to program using such the well known languages as: Java, С#, php, markup language html, JavaScript and so on. Our teachers have got a bag experience of programming both in the Ukrainian and foreign companies.</em></p><p style=\"box-sizing: border-box; margin: 0px 0px 10px; color: rgb(162, 162, 162); font-family: 'Noto Serif', serif; font-size: 16px; font-style: italic; line-height: 25.6px; widows: 1; background-color: rgb(255, 255, 255);\"><em style=\"box-sizing: border-box;\">The company \"In Alto\" arose due to a permanent growing of necessity in programmers of a high quality in the IT industry. A perennial experience of teaching the disciplines in informatics and a permanent cooperation with business have given the possibility to work out an optimal programme of cources in the area of IT-experts` training.</em></p><p style=\"box-sizing: border-box; margin: 0px 0px 10px; color: rgb(162, 162, 162); font-family: 'Noto Serif', serif; font-size: 16px; font-style: italic; line-height: 25.6px; widows: 1; background-color: rgb(255, 255, 255);\"><em style=\"box-sizing: border-box;\">Our purpose is the training of IT-experts, which could work at the IT-companies after their studying at once. Our cources are oriented both on the persons, which have got the base knowledges in programming and on the newcomers. A content and form of material`s presentation can adapt to the middle level of student`s knowledge. It enables to deliver he material to the listeners by the best way.</em></p><p style=\"box-sizing: border-box; margin: 0px 0px 10px; color: rgb(162, 162, 162); font-family: 'Noto Serif', serif; font-size: 16px; font-style: italic; line-height: 25.6px; widows: 1; background-color: rgb(255, 255, 255);\"><em style=\"box-sizing: border-box;\">Our teachers are programmers - educators from the well known IT-companies, which have a high success rate, that means a big the percentage of employed students by their speciality. We also invite the specialists for teaching activities, which are working in the IT-industry. It enables to exchange the experience and keep the topicality of studying programmes.</em></p><p style=\"box-sizing: border-box; margin: 0px 0px 10px; color: rgb(162, 162, 162); font-family: 'Noto Serif', serif; font-size: 16px; font-style: italic; line-height: 25.6px; widows: 1; background-color: rgb(255, 255, 255);\"><em style=\"box-sizing: border-box;\">The listeners will be able to listen the cources, that are tested during a few years of teaching and composed in accordance with the requirements of business.</em></p><p style=\"box-sizing: border-box; margin: 0px 0px 10px; color: rgb(162, 162, 162); font-family: 'Noto Serif', serif; font-size: 16px; font-style: italic; line-height: 25.6px; widows: 1; background-color: rgb(255, 255, 255);\"><em style=\"box-sizing: border-box;\">The IT-industry are always in a dynamic development. The training of this industry`s specialists has also to correspond to the requirements of time.</em></p><p style=\"box-sizing: border-box; margin: 0px 0px 10px; color: rgb(162, 162, 162); font-family: 'Noto Serif', serif; font-size: 16px; font-style: italic; line-height: 25.6px; widows: 1; background-color: rgb(255, 255, 255);\"><br style=\"box-sizing: border-box; line-height: 25.6px; text-align: justify;\"></p><p class=\"lead\" style=\"box-sizing: border-box; margin: 0px 0px 20px; font-size: 21px; line-height: 1.4; color: rgb(162, 162, 162); font-family: 'Noto Serif', serif; font-style: italic; widows: 1; background-color: rgb(255, 255, 255);\"><span class=\"pullquote-left\" style=\"box-sizing: border-box; display: block; color: rgb(25, 17, 86); font-size: 14px; line-height: 1.6em; padding-left: 20px;\"></span></p><blockquote style=\"box-sizing: border-box; padding: 10px 20px; margin: 0px 0px 20px; font-size: 16px; border-left-width: 5px; border-style: none none none solid; border-left-color: rgb(238, 238, 238); font-family: 'Noto Serif', serif; font-style: italic; color: rgb(162, 162, 162); line-height: 25.6px; text-align: justify; widows: 1; background-color: rgb(255, 255, 255);\">&nbsp;<strong class=\"cl123\" style=\"box-sizing: border-box; color: rgb(104, 164, 196);\">A TEST CLASS IS FREE</strong>, where You will meet with Your teacher and the programme. And only after that You will confirm the agreement.</blockquote><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p></body></html>"),
//	WhoAreWeDialogTextArea("Компанія \"In Alto Online It Academy\" пропонує навчитися створювати програми, не виходячи з дому, у приємній для Вас атмосфері, за допомогою різних мов програмування.\n\n"+
//		"Уроки розраховані на онлайн (віддалене) навчання , що дасть змогу більше часу приділити вивченню програми курсу. Ви навчитеся програмувати, використовуючи такі добре відомі мови, як: Java, С#, php, мова-розмітка html, JavaScript і т.д. Наші викладачі мають великий досвід програмування як в українських, так і закордонних компаніях.\n"+
//		"Компанія \"In Alto\" постала у відповідь на постійне зростання потреби у програмістах високого рівня з боку ІТ індустрії. Багаторічний досвід викладання дисциплін в галузі інформатики та постійна співпраця з бізнесом дали змогу випрацювати оптимальну програму курсів підготовки ІТ фахівців.\n"+
//		"Нашою метою є підготовка спеціалістів в галузі програмування, які після завершення навчання могли б одразу стати до роботи в ІТ компаніях. Наші курси орієнтовані як на людей, що мають базові знання в програмуванні, так і на новачків. Зміст та форма подачі матеріалу може адаптуватись відповідно до середнього рівня студента, що дає змогу найкращим чином донести матеріал до слухачів.\n"+
//		"Викладачами є програмісти - педагоги відомих ІТ компаній, показником успішності роботи яких є значний відсоток працевлаштованих за фахом студентів. Ми також запрошуємо до викладацької діяльності спеціалістів, які працюють в індустрії ІТ, що дає змогу налагоджувати обмін досвідом та дотримуватись актуальності навчальних програм.\n"+
//		"Слухачі отримають змогу прослухати курси апробовані впродовж кількох років викладання та складені відповідного до вимог бізнесу, починаючи з задач актуальних на сучасному етапі розвитку ІТ технологій до типових завдань на співбесідах при прийомі на роботу.\n"+
//		"Індустрія інформаційних технологій постійно перебуває в динамічному розвитку. Підготовка фахівців в цій галузі мусить також відповідати вимогам часу.\n\n"+
//		"А, і ще, ПРОБНЕ ЗАНЯТТЯ Є БЕЗКОШТОВНИМ, де ви зможете ознайомитися з викладачем та програмою і тільки після того оформляти договір.",
//		"Our company \"In Alto Online IT Academy\" offers to study a creation of programmes at home, in a pleasant atmosphare for You with the aid of different languages of programming.\n\n"+
//		"The classes are expected on the online (remote) studying. It will enable to give much time for studying of cource`s programme. You will study to program using such the well known languages as: Java, C#, php, markup language html, JavaScript and so on. Our teachers have got a bag experience of programming both in the Ukrainian and foreign companies.\n"+
//		"The company \"In Alto\" arose due to a permanent growing of necessity in programmers of a high quality in the IT industry. A perennial experience of teaching the disciplines in informatics and a permanent cooperation with business have given the possibility to work out an optimal programme of cources in the area of IT-experts` training.\n"+
//		"Our purpose is the training of IT-experts, which could work at the IT-companies after their studying at once. Our cources are oriented both on the persons, which have got the base knowledges in programming and on the newcomers. A content and form of material`s presentation can adapt to the middle level of student`s knowledge. It enables to deliver he material to the listeners by the best way.\n"+
//		"Our teachers are programmers - educators from the well known IT-companies, which have a high success rate, that means a big the percentage of employed students by their speciality. We also invite the specialists for teaching activities, which are working in the IT-industry. It enables to exchange the experience and keep the topicality of studying programmes.\n"+
//		"The listeners will be able to listen the cources, that are tested during a few years of teaching and composed in accordance with the requirements of business.\n"+
//		"The IT-industry are always in a dynamic development. The training of this industry`s specialists has also to correspond to the requirements of time.\n\n"+
//		"A TEST CLASS IS FREE, where You will meet with Your teacher and the programme. And only after that You will confirm the agreement."),
	
	// Advantages Dialog
	AdvantagesDialogTitle("Переваги співпраці з нами","Advantages of cooperation with us"),
	AdvantagesDialogTextArea("<html dir=\"ltr\"><head></head><body contenteditable=\"true\"><p style=\"box-sizing: border-box; margin: 0px 0px 10px; color: rgb(162, 162, 162); font-family: 'Noto Serif', serif; font-size: 16px; font-style: italic; line-height: 25.6px; widows: 1; background-color: rgb(255, 255, 255);\"><strong style=\"box-sizing: border-box;\">Штат експертів.&nbsp;</strong>Наші працівники – це команда професіоналів, які мають великий досвід роботи в консалтинговому бізнесі.</p><p style=\"box-sizing: border-box; margin: 0px 0px 10px; color: rgb(162, 162, 162); font-family: 'Noto Serif', serif; font-size: 16px; font-style: italic; line-height: 25.6px; widows: 1; background-color: rgb(255, 255, 255);\"><br style=\"box-sizing: border-box;\"><em style=\"box-sizing: border-box;\"><strong style=\"box-sizing: border-box;\">Швидке реагування на будь-які питання.&nbsp;</strong>До нас легко додзвонитися і з нами легко спілкуватися. Ваші побажання швидко обробляються, що дає Вам можливість повністю сконцентруватися на побажаннях і потребах Клієнта.</em></p><p style=\"box-sizing: border-box; margin: 0px 0px 10px; color: rgb(162, 162, 162); font-family: 'Noto Serif', serif; font-size: 16px; font-style: italic; line-height: 25.6px; widows: 1; background-color: rgb(255, 255, 255);\"><br style=\"box-sizing: border-box;\"><em style=\"box-sizing: border-box;\"><strong style=\"box-sizing: border-box;\">Комфорт у роботі.&nbsp;</strong>Якщо Ви не можете приїхати до нас в офіс, то ми виїжджаємо до Вас. Для зручності надаємо різні види консультацій: усну, письмову, по Скайпу, по телефону.</em></p><p style=\"box-sizing: border-box; margin: 0px 0px 10px; color: rgb(162, 162, 162); font-family: 'Noto Serif', serif; font-size: 16px; font-style: italic; line-height: 25.6px; widows: 1; background-color: rgb(255, 255, 255);\"><br style=\"box-sizing: border-box;\"><em style=\"box-sizing: border-box;\"><strong style=\"box-sizing: border-box;\">Орієнтація на довготривале партнерство.&nbsp;</strong>Ми будуємо довгострокові бізнес-відносини, орієнтовані на взаємовигідне співробітництво та досягнення спільної мети. Відтак, ми – компанія, яка поважає думку Клієнта.</em></p><p style=\"box-sizing: border-box; margin: 0px 0px 10px; color: rgb(162, 162, 162); font-family: 'Noto Serif', serif; font-size: 16px; font-style: italic; line-height: 25.6px; widows: 1; background-color: rgb(255, 255, 255);\"><br style=\"box-sizing: border-box;\"><em style=\"box-sizing: border-box;\"><strong style=\"box-sizing: border-box;\">Відданість справі.&nbsp;</strong>Кожен з нас відданий своїй справі. Ми йдемо на роботу з посмішкою і працюємо із задоволенням.</em></p><p style=\"box-sizing: border-box; margin: 0px 0px 10px; color: rgb(162, 162, 162); font-family: 'Noto Serif', serif; font-size: 16px; font-style: italic; line-height: 25.6px; widows: 1; background-color: rgb(255, 255, 255);\"><br style=\"box-sizing: border-box;\"><em style=\"box-sizing: border-box;\"><strong style=\"box-sizing: border-box;\">Система знижок для клієнтів.&nbsp;</strong>Ми пропонуємо нашим Клієнтам бонуси за залучення інших компаній до співпраці з нами, а також знижки для постійних Клієнтів. Детально про умови системи такого співробітництва Вам можуть розповісти наші менеджери по роботі з Клієнтами.</em></p><p style=\"box-sizing: border-box; margin: 0px 0px 10px; color: rgb(162, 162, 162); font-family: 'Noto Serif', serif; font-size: 16px; font-style: italic; line-height: 25.6px; widows: 1; background-color: rgb(255, 255, 255);\"><br style=\"box-sizing: border-box;\"><em style=\"box-sizing: border-box;\"><strong style=\"box-sizing: border-box;\">Доступні ціни.&nbsp;</strong>Ми вираховуємо вартість послуги, виходячи зі справжніх потреб витрачених з нашого боку ресурсів, а не з оцінки Клієнта та його матеріальних можливостей. Наші ціни відповідають дійсності.</em></p><p style=\"box-sizing: border-box; margin: 0px 0px 10px; color: rgb(162, 162, 162); font-family: 'Noto Serif', serif; font-size: 16px; font-style: italic; line-height: 25.6px; widows: 1; background-color: rgb(255, 255, 255);\"><br style=\"box-sizing: border-box;\"><em style=\"box-sizing: border-box;\"><strong style=\"box-sizing: border-box;\">Участь у реальних проектах та можливість працевлаштування.&nbsp;</strong>Кожен студент нашого навчального центру має можливість брати участь у внутрішніх, а також виконувати власні проекти. Крім того, ми готуємо своїх студентів для проходження співбесід в ІТ компаніях.</em></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p></body></html>",
		"<html dir=\"ltr\"><head></head><body contenteditable=\"true\"><p style=\"box-sizing: border-box; margin: 0px 0px 10px; color: rgb(162, 162, 162); font-family: 'Noto Serif', serif; font-size: 16px; font-style: italic; line-height: 25.6px; widows: 1; background-color: rgb(255, 255, 255);\"><strong style=\"box-sizing: border-box;\">Group of experts.&nbsp;</strong>Our workers are a team of professionals, which has got a great experience of work in consulting business.</p><p style=\"box-sizing: border-box; margin: 0px 0px 10px; color: rgb(162, 162, 162); font-family: 'Noto Serif', serif; font-size: 16px; font-style: italic; line-height: 25.6px; widows: 1; background-color: rgb(255, 255, 255);\"><br style=\"box-sizing: border-box;\"><em style=\"box-sizing: border-box;\"><strong style=\"box-sizing: border-box;\">A quick reaction on any questions.&nbsp;</strong>You can call us and communicate with us easy. We work out Your wishes quickly, that enables to concentrate at wishes and demands of our customer.</em></p><p style=\"box-sizing: border-box; margin: 0px 0px 10px; color: rgb(162, 162, 162); font-family: 'Noto Serif', serif; font-size: 16px; font-style: italic; line-height: 25.6px; widows: 1; background-color: rgb(255, 255, 255);\"><br style=\"box-sizing: border-box;\"><em style=\"box-sizing: border-box;\"><strong style=\"box-sizing: border-box;\">Comfort in work.&nbsp;</strong>If You cannot come to our office, we will come to You. We give different types of consultation for Your convenience: oral, written, by Skype, by telephone.</em></p><p style=\"box-sizing: border-box; margin: 0px 0px 10px; color: rgb(162, 162, 162); font-family: 'Noto Serif', serif; font-size: 16px; font-style: italic; line-height: 25.6px; widows: 1; background-color: rgb(255, 255, 255);\"><br style=\"box-sizing: border-box;\"><em style=\"box-sizing: border-box;\"><strong style=\"box-sizing: border-box;\">Orientation for long-term partnership.&nbsp;</strong>We build long-term business relations, that are oriented for mutually beneficial cooperation and reaching of a corporate purpose. That`s why we are a company, that respects a client`s opinion.</em></p><p style=\"box-sizing: border-box; margin: 0px 0px 10px; color: rgb(162, 162, 162); font-family: 'Noto Serif', serif; font-size: 16px; font-style: italic; line-height: 25.6px; widows: 1; background-color: rgb(255, 255, 255);\"><br style=\"box-sizing: border-box;\"><em style=\"box-sizing: border-box;\"><strong style=\"box-sizing: border-box;\">Devotion to our business.&nbsp;</strong>Everyone of us is devoted to our business. We go to work with a smile and we work with pleasure.</em></p><p style=\"box-sizing: border-box; margin: 0px 0px 10px; color: rgb(162, 162, 162); font-family: 'Noto Serif', serif; font-size: 16px; font-style: italic; line-height: 25.6px; widows: 1; background-color: rgb(255, 255, 255);\"><br style=\"box-sizing: border-box;\"><em style=\"box-sizing: border-box;\"><strong style=\"box-sizing: border-box;\">System of discounts for customers.&nbsp;</strong>We offer the bonuses for attraction of another companies to cooperation with us and the discounts for the permanent customers. You can get more information about such a system of cooperation calling our managers.</em></p><p style=\"box-sizing: border-box; margin: 0px 0px 10px; color: rgb(162, 162, 162); font-family: 'Noto Serif', serif; font-size: 16px; font-style: italic; line-height: 25.6px; widows: 1; background-color: rgb(255, 255, 255);\"><br style=\"box-sizing: border-box;\"><em style=\"box-sizing: border-box;\"><strong style=\"box-sizing: border-box;\">Available prices.&nbsp;</strong>We calculate the price of service, getting out from real necessities, our spent resources, but not from the assessment of our client and his material possibilities. Our prices correspond to the reality.</em></p><p style=\"box-sizing: border-box; margin: 0px 0px 10px; color: rgb(162, 162, 162); font-family: 'Noto Serif', serif; font-size: 16px; font-style: italic; line-height: 25.6px; widows: 1; background-color: rgb(255, 255, 255);\"><br style=\"box-sizing: border-box;\"><em style=\"box-sizing: border-box;\"><strong style=\"box-sizing: border-box;\">Participation in real projects and possibility of employment.&nbsp;</strong>Every student of our study center can participate in the inner projects and work out his own ones. In addition we train our students for passing the interviews in the IT-companies.</em></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p><p></p></body></html>"),
//	AdvantagesDialogTextArea("Штат експертів. Наші працівники – це команда професіоналів, які мають великий досвід роботи в консалтинговому бізнесі.\n\n"+
//		"Швидке реагування на будь-які питання. До нас легко додзвонитися і з нами легко спілкуватися. Ваші побажання швидко обробляються, що дає Вам можливість повністю сконцентруватися на побажаннях і потребах Клієнта.\n\n"+
//		"Комфорт у роботі. Якщо Ви не можете приїхати до нас в офіс, то ми виїжджаємо до Вас. Для зручності надаємо різні види консультацій: усну, письмову, по Скайпу, по телефону.\n\n"+
//		"Орієнтація на довготривале партнерство. Ми будуємо довгострокові бізнес-відносини, орієнтовані на взаємовигідне співробітництво та досягнення спільної мети. Відтак, ми – компанія, яка поважає думку Клієнта.\n\n"+
//		"Відданість справі. Кожен з нас відданий своїй справі. Ми йдемо на роботу з посмішкою і працюємо із задоволенням.\n\n"+
//		"Система знижок для клієнтів. Ми пропонуємо нашим Клієнтам бонуси за залучення інших компаній до співпраці з нами, а також знижки для постійних Клієнтів. Детально про умови системи такого співробітництва Вам можуть розповісти наші менеджери по роботі з Клієнтами.\n\n"+
//		"Доступні ціни. Ми вираховуємо вартість послуги, виходячи зі справжніх потреб витрачених з нашого боку ресурсів, а не з оцінки Клієнта та його матеріальних можливостей. Наші ціни відповідають дійсності.\n\n"+
//		"Участь у реальних проектах та можливість працевлаштування. Кожен студент нашого навчального центру має можливість брати участь у внутрішніх, а також виконувати власні проекти. Крім того, ми готуємо своїх студентів для проходження співбесід в ІТ компаніях.",
//		"Group of experts. Our workers are a team of professionals, which has got a great experience of work in consulting business.\n\n"+
//		"A quick reaction on any questions. You can call us and communicate with us easy. We work out Your wishes quickly, that enables to concentrate at wishes and demands of our customer.\n\n"+
//		"Comfort in work. If You cannot come to our office, we will come to You. We give different types of consultation for Your convenience: oral, written, by Skype, by telephone.\n\n"+
//		"Orientation for long-term partnership. We build long-term business relations, that are oriented for mutually beneficial cooperation and reaching of a corporate purpose. That`s why we are a company, that respects a client`s opinion.\n\n"+
//		"Devotion to our business. Everyone of us is devoted to our business. We go to work with a smile and we work with pleasure.\n\n"+
//		"System of discounts for customers. We offer the bonuses for attraction of another companies to cooperation with us and the discounts for the permanent customers. You can get more information about such a system of cooperation calling our managers.\n\n"+
//		"Available prices. We calculate the price of service, getting out from real necessities, our spent resources, but not from the assessment of our client and his material possibilities. Our prices correspond to the reality.\n\n"+
//		"Participation in real projects and possibility of employment. Every student of our study center can participate in the inner projects and work out his own ones. In addition we train our students for passing the interviews in the IT-companies."),	
	
	// Contacts Dialog
	ContactsDialogTitle("Вихід","Contacts"),
	ContactsLabelTitle("Наші дані: In Alto. Online IT academy.\n"+
		"По всій території України і не тільки, тел. +380 (97) 468-07-41,\n"+
		"E-mail: mail@inaltostudy.info, mykolakotsyuba@gmail.com\n"+
		"www.inaltostudy.info",
		"Our contacts: In Alto. Online IT academy.\n"+
		"Throughout Ukraine and more, tel. +380 (97) 468-07-41,\n"+
		"E-mail: mail@inaltostudy.info, mykolakotsyuba@gmail.com\n"+
		"www.inaltostudy.info"),
	ContactsLabelQuestion("Є запитання? Хочете записатися на безкоштовний пробний урок?\n"+
		"Тоді заповніть форму і ми Вам перетелефонуємо або дзвоніть самостійно +380 (97) 468- 07- 41.",
		"Any questions? Wishes? If you want to sign up for a free trial lesson?\n"+
		"Then either fill the form below and we call you or call yourself +380 (97) 468- 07- 41."),
	ContactsLabelName("Ім'я, прізвище:","First name, last name:"),
	ContactsLabelEmail("E-mail:","E-mail:"),
	ContactsLabelCourseName("Назва курсу:","Course name:"),
	ContactsLabelMessage("Текст повідомлення:","Message text:"),
	ContactsTextName("Введіть повне ім'я та прізвище","Input full first name and last name:"),
	ContactsTextEmail("Введіть свій e-mail","Input your e-mail:"),
	ContactsTextCourseName("Введіть назву обраного курсу","Input choosen course name"),
	ContactsTextMessage("Введіть текст повідомлення","Input message text"),
	
	// Exit Dialog
	ExitDialogTitle("Вихід","Exit"),
	ExitDialogText("Дійсно бажаєте вийти з програми?","Are you sure to exit?"),
	
	// Authorization Dialog
	AuthorizationDialogTitle("Вихід","Authorization"),
	AuthorizationLabelLogin("Логін користувача:","User login:"),
	AuthorizationLabelPassword("Пароль користувача:","User password:"),
	AuthorizationTextLogin("Введіть свій логін","Input your login"),
	AuthorizationTextPassword("Введіть свій пароль","Input your password"),
	AuthorizationLinkRegistration("Реєстрація...","Registration..."),
	AuthorizationLinkForgot("Забули пароль...","Forgot password..."),
	
	// Forgot Dialog
	ForgotDialogTitle("Забули пароль","Forgot password"),
	ForgotLabelQuestion("Контрольне питання:","Security question:"),
	ForgotLabelAnswer("Контрольна відповідь:","Security answer:"),
	ForgotTextAnswer("Введіть відповідь","Input answer"),
	ForgotLinkRegistration("Реєстрація...","Registration..."),
	
	// Registration Dialog
	RegistrationDialogTitle("Реєстрація","Registration"),
	RegistrationLabelRoleName("Роль користувача:","User role:"),
	RegistrationLabelLogin("Логін користувача:","User login:"),
	RegistrationLabelPassword("Пароль користувача:","User password:"),
	RegistrationLabelPasswordRepeat("Повторіть пароль:","Repeat password:"),
	RegistrationLabelQuestion("Контрольне питання:","Security question:"),
	RegistrationLabelAnswer("Контрольна відповідь:","Security answer:"),
	RegistrationLabelFirstName("Ім'я користувача:","User first name:"),
	RegistrationLabelLastName("Прізвище користувача:","User last name:"),
	RegistrationLabelBirthday("Дата народження:","Birthday:"),
	RegistrationLabelAddress("Адреса:","Address:"),
	RegistrationLabelPhone("Телефон:","Phone:"),
	RegistrationLabelEmail("Email:","Email:"),
	RegistrationLabelSkype("Skype:","Skype:"),
	RegistrationLabelDetails("Додаткова інформація:","Additional information:"),
	RegistrationTextLogin("Введіть свій логін","Input your login"),
	RegistrationTextPassword("Введіть свій пароль","Input your password"),
	RegistrationTextPasswordRepeat("Повторіть свій пароль","Repeat your password"),
	RegistrationTextQuestion("Введіть контрольне питання","Input security question"),
	RegistrationTextAnswer("Введіть контрольну відповідь","Input security answer"),
	RegistrationTextFirstName("Введіть своє ім'я","Input your first name"),
	RegistrationTextLastName("Введіть своє прізвище","Input your last name"),
	RegistrationTextBirthday("Введіть свою дату народження дд.мм.гггг","Input your birthday dd.mm.yyyy"),
	RegistrationTextAddress("Введіть свою адресу","Input your address"),
	RegistrationTextPhone("Введіть свій телефон","Input your phone"),
	RegistrationTextEmail("Введіть свою адресу email","Input your email address"),
	RegistrationTextSkype("Введіть свій логін Skype","Input your Skype login"),
	RegistrationTextDetails("Введіть додаткову інформацію","Input additional information"),
	
	// Users Table SplitPane
	LabelUserTitle("Користувачі школи:","School users:"),
	
//	// Tables
//	TableList("Довідник ","Table "),
//	
//	// RolesSplitPane
//	TableRoleName("Роль користувача","User's role"),
//	TableRoleDetails("Інформація","Details"),
//	
//	// LoginsSplitPane
//	TableLoginName("Логін користувача","User's login"),
//	TableLoginPassword("Пароль користувача","User's password"),
//	TableLoginPasswordRepeat("Повторіть пароль","Repeat password"),
//	TableLoginQuestion("Контрольне питання","Security question"),
//	TableLoginAnswer("Контрольна відповідь","Security answer"),	
//	TablePasswordError("Паролі не співпадають!","Passwords mismatch!"),
//
//	// UsersSplitPane
//	TableUserName("Дані користувача","User's data"),
//	TableUserFirstName("Ім'я користувача","User's first name"),
//	TableUserLastName("Прізвище користувача","User's last name"),
//	TableUserAddress("Адреса користувача","User's address"),
//	TableUserPhone("Телефон користувача","User's phone"),
//	TableUserEmail("E-mail користувача","User's email"),
//	TableUserSkype("Skype користувача","User's skype"),
//	TableUserDetails("Інша інформація","Other details"),
//	
//	// ProjectSplitPane
//	TableProjectName("Проект школи","School project"),
//	TableProjectDetails("Опис проекту:","Project description:"),
//	
//	// CoursesSplitPane
//	TableCourseName("Курс школи","School course"),
//	TableCourseDetails("Опис курсу:","Course description:"),
//	TableCoursePrice("Вартість курсу, грн.","Course price (UAH)"),
//	TableDoubleError("Недопустиме значення поля!","Iinvalid field value!"),
//	
//	// LessonsSplitPane
//	TableLessonName("Урок курсу","Course lesson"),
//	TableLessonAuthor("Автор курсу","Course's author"),
//	TableLessonDetails("Зміст уроку:","Lesson content:"),
//	
//	// TasksSplitPane
//	TableTaskName("Завдання уроку чи проекту","Lesson or project task"),
//	TableTaskDetails("Умови завдання:","Task conditions:"),
	
	;
	
	private String[] value;
	
	private Strings(String...value) {
		this.value = new String[value.length];
		for (int i = 0; i < value.length; i++) {
			this.value[i] = value[i];
		}
	}
	
	public String getValue(int index) {
		if ((index >= 0) && (index < value.length))
			return value[index];
		else
			return "";
	}
	
	public void setValue(String...value) {
		this.value = value;
	}


}