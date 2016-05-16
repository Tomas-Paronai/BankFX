CREATE DATABASE itbank14;
use itbank14;

CREATE TABLE Clients(
	ClientID int(11) auto_increment,
	FirstName varchar(20),
	LastName varchar(20),
	DOB date,
	primary key(ClientID)
);
ALTER TABLE Clients AUTO_INCREMENT=1001;

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
ALTER TABLE Accounts AUTO_INCREMENT=1001;

CREATE TABLE Cards(
	CardID int(11) auto_increment,
	AccountID int(11),
	Active boolean,
	primary key(CardID),
	foreign key(AccountID) references Accounts(AccountID) on update cascade on delete cascade
);
ALTER TABLE Cards AUTO_INCREMENT=1001;

CREATE TABLE Loans(
	LoanID int(11) auto_increment,
	ClientID int(11),
	Interest float(5) default 0,
	Amount float(20) not null,
	Paid int(11) default 0,
	primary key(LoanID),
	foreign key(ClientID) references Clients(ClientID) on update cascade on delete restrict
);
ALTER TABLE Loans AUTO_INCREMENT=1001;

CREATE TABLE Client_login(
	ClientID int(11),
	Username varchar(20) unique,
	Password varchar(20),
	Active boolean,
	foreign key(ClientID) references Clients(ClientID) on update cascade on delete cascade
);

CREATE TABLE Invalid_access(
	ClientID int(11),
	`Date` timestamp,
	foreign key(ClientID) references Clients(ClientID) on update cascade on delete cascade
);

CREATE TABLE Client_history_login(
	ClientID int(11),
	`Date` timestamp,
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
ALTER TABLE Employees AUTO_INCREMENT=1001;

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
	`Date` timestamp,
	primary key(TransactionID),
	foreign key(`Type`) references Transaction_type(TypeID) on update cascade,
	foreign key(AccountID) references Accounts(AccountID) on update cascade
);
ALTER TABLE Transactions AUTO_INCREMENT=1001;

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
('1001','tyler.murray@gmail.com','0902354721'),('1002','bailer@centrum.org','0914789896'),
('1003','jayjay@music-tv.com','0912636669'),('1004','layla.marsh@gmail.com','0956554774'),
('1005','victoriawatts@yahoo.com','0912235386'),('1006','flower475@twitter.com','0923563632'),
('1007','kyliebigdick@pornhub.com','0923536341'),('1008','tommy.owen@tommy.blog.com','0922456663'),
('1009','riko.melton@gmail.com','0912634412'),('1010','jams.lowe@twitter.com','0921454221'),
('1011','evan@yahoo.com','0987855214');

INSERT INTO client_login VALUES
('1001','tyMurray','tm1968','1'),('1002','baPowell','bp1988','1'),('1003','jaByrne','jb1991','1'),
('1004','laMarsh','lm1985','0'),('1005','viWatts','vw1993','0'),('1006','frHurley','fh1975','1'),
('1007','kyDickerson','kd1989','1'),('1008','toOwen','to1986','0'),('1009','ryMelton','rm1988','1'),
('1010','jaLowe','jl1979','1'),('1011','evShepherd','es1983','1');

INSERT INTO positions (name) VALUES 
('manager'), ('employee'), ('student'), ('part_time');

INSERT INTO employees (firstname,lastname, positionid) VALUES 
('Donald','McRoy','1'), ('Walter','Adkins','2'),('Scott','Clay','2'),
('Marisa','Weber','2'),('Abbie','Lawrence','3'),('Clay','Woods','4');

INSERT INTO employee_login VALUES
('1001','McDonald4','12345'),('1001','Waltie','12345'),('1002','blitz14','12345'),
('1004','mariaweber','12345'),('1005','abbiegale1993','12345'),('1006','theClayman','12345');

INSERT INTO transaction_type (name) VALUES
('transfer'),('withdraw'),('deletion');

INSERT INTO accounts (clientid,balance) VALUES
('1001','350'),('1002','2210'),('1002','15'),('1002','123'),('1003','550'),
('1004','50'),('1004','44'),('1005','3252'),('1006','456'),('1007','64'),
('1008','15'),('1008','0'),('1008','210'),('1009','140'),('1010','90'),
('1010','0'),('1011','275'),('1011','75'),('1004','1500');

INSERT INTO cards (accountid,active) VALUES
('1001','1'),('1005','1'),('1006','1'),('1008','0'),('1009','1'),
('1012','1'),('1013','0'),('1015','1'),('1017','1'),('1019','1');

INSERT INTO loans (clientid,interest,amount,paid) VALUES
(1002,0.2,3500,250), (1003,0.15,500,0), (1004,0.3,5000,1400),
(1002,0.1,400,0), (1007,0.15,4550,1871), (1008,0.3,5000,0),
(1005,0.3,1200,0), (1009,0.3,4550,1871), (1010,0.5,5000,4400);