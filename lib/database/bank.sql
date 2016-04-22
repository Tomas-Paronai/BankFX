CREATE DATABASE itbank14;
use itbank14;

CREATE TABLE Clients(
	ClientID int(11) auto_increment,
	FirstName varchar(20),
	LastName varchar(20),
	DOB date,
	primary key(ClientID)
);

CREATE TABLE Client_detail(
	ClientID int(11),
	Email varchar(30),
	Tel varchar(20),
	foreign key(ClientID) references Clients(ClientID) on update cascade on delete cascade
);

CREATE TABLE Accounts(
	AccountID int(11) auto_increment,
	ClientID int(11),
	Balance float(20),
	primary key(AccountID),
	foreign key(ClientID) references Clients(ClientID) on update cascade on delete cascade
);

CREATE TABLE Cards(
	CardID int(11) auto_increment,
	AccountID int(11),
	Active boolean,
	primary key(CardID),
	foreign key(AccountID) references Accounts(AccountID) on update cascade on delete cascade
);

CREATE TABLE Loans(
	LoanID int(11) auto_increment,
	ClientID int(11),
	Interest float(5),
	Amount float(20),
	Paid boolean,
	primary key(LoanID),
	foreign key(ClientID) references Clients(ClientID) on update cascade on delete restrict
);

CREATE TABLE Client_login(
	ClientID int(11),
	Username varchar(20) unique,
	Password varchar(20),
	Active boolean,
	foreign key(ClientID) references Clients(ClientID) on update cascade on delete cascade
);

CREATE TABLE Invalid_access(
	Username varchar(20),
	`Date` date,
	foreign key(Username) references Client_login(Username) on update cascade on delete cascade
);

CREATE TABLE Client_history_login(
	ClientID int(11),
	`Date` date,
	foreign key(ClientID) references Clients(ClientID) on update cascade on delete cascade
);

CREATE TABLE Positions(
	PositionID int(11) auto_increment,
	Name varchar(30),
	primary key(PositionID)
);

CREATE TABLE Employees(
	EmployeeID int(11) auto_increment,
	FirstName varchar(20),
	LastName varchar(20),
	PositionID int(11),
	primary key(EmployeeID),
	foreign key(PositionID) references Positions(PositionID) on update cascade
);

CREATE TABLE Employee_history_login(
	EmployeeID int(11),
	`Date` date,
	foreign key(EmployeeID) references Employees(EmployeeID) on update cascade on delete cascade
);

CREATE TABLE Employee_login(
	EmployeeID int(11),
	Username varchar(20) unique,
	Password varchar(20),
	foreign key(EmployeeID) references Employees(EmployeeID) on update cascade on delete cascade
);

CREATE TABLE Transaction_type(
	TypeID int(11) auto_increment,
	Name varchar(20) null,
	primary key(TypeID)
);

CREATE TABLE Transactions(
	TransactionID int(11) auto_increment,
	PersonID int(11) not null,
	AccountID int(11),
	`Type` int(11),
	`Value` float(20),
	`Date` date,
	primary key(TransactionID),
	foreign key(`Type`) references Transaction_type(TypeID) on update cascade,
	foreign key(AccountID) references Accounts(AccountID) on update cascade
);

CREATE TABLE Archived_clients(
	ClientID int(11),
	FirstName varchar(20),
	LastName varchar(30),
	Email varchar(50),
	Tel varchar(20),
	Username varchar(30),
	DOB date
);

INSERT INTO clients (firstname,lastname,dob) VALUES 
('Tyler','Murray','1968-05-07'), ('Bailey','Powell','1988-11-21'), ('Jay','Byrne','1991-01-22'),
('Layla','Marsh','1985-03-14'),('Victoria','Watts','1993-03-16'),('Francesca','Hurley','1975-07-27'),
('Kylie','Dickerson','1989-12-23'),('Tommy','Owen','1986-10-30'),('Rylee','Melton','1988-09-01'),
('Jameson','Lowe','1979-01-18'),('Evan','Shepherd','1983-11-25');


INSERT INTO client_detail(clientid,email,tel) VALUES
('1','tyler.murray@gmail.com','0902354721'),('2','bailer@centrum.org','0914789896'),
('3','jayjay@music-tv.com','0912636669'),('4','layla.marsh@gmail.com','0956554774'),
('5','victoriawatts@yahoo.com','0912235386'),('6','flower475@twitter.com','0923563632'),
('7','kyliebigdick@pornhub.com','0923536341'),('8','tommy.owen@tommy.blog.com','0922456663'),
('9','riko.melton@gmail.com','0912634412'),('10','jams.lowe@twitter.com','0921454221'),
('11','evan@yahoo.com','0987855214');

INSERT INTO client_login VALUES
('1','tyMurray','tm1968','1'),('2','baPowell','bp1988','1'),('3','jaByrne','jb1991','1'),
('4','laMarsh','lm1985','0'),('5','viWatts','vw1993','0'),('6','frHurley','fh1975','1'),
('7','kyDickerson','kd1989','1'),('8','toOwen','to1986','0'),('9','ryMelton','rm1988','1'),
('10','jaLowe','jl1979','1'),('11','evShepherd','es1983','1');

INSERT INTO positions (name) VALUES 
('manager'), ('employee'), ('student'), ('part_time');

INSERT INTO employees (firstname,lastname, positionid) VALUES 
('Donald','McRoy','1'), ('Walter','Adkins','2'),('Scott','Clay','2'),
('Marisa','Weber','2'),('Abbie','Lawrence','3'),('Clay','Woods','4');

INSERT INTO employee_login VALUES
('1','McDonald4','12345'),('1','Waltie','12345'),('2','blitz14','12345'),
('4','mariaweber','12345'),('5','abbiegale1993','12345'),('6','theClayman','12345');

INSERT INTO transaction_type (name) VALUES
('transfer'),('withdraw'),('deletion');

INSERT INTO accounts (clientid,balance) VALUES
('1','350'),('2','2210'),('2','15'),('2','123'),('3','550'),
('4','50'),('4','44'),('5','3252'),('6','456'),('7','64'),
('8','15'),('8','0'),('8','210'),('9','140'),('10','90'),
('10','0'),('11','275'),('11','75'),('4','1500');

INSERT INTO cards (accountid,active) VALUES
('1','1'),('5','1'),('6','1'),('8','0'),('9','1'),
('12','1'),('13','0'),('15','1'),('17','1'),('19','1');
