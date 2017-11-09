--
-- Database: ehospital
--
-- connect 'jdbc:derby:c:\db\hospital;';

-- --------------------------------------------------------


--
-- Table structure for table departments
--

CREATE TABLE departments (
  department_id int NOT NULL,
  name varchar(256) NOT NULL,
  description varchar(1024) NOT NULL,
  PRIMARY KEY (department_id)
);

-- --------------------------------------------------------

--
-- Table structure for table CMDREG
--

CREATE TABLE CMDREG (
  DEP_ID int NOT NULL,
  CLASS varchar(1024) NOT NULL,
  CONSTRAINT FK_CMDEP
  FOREIGN KEY (DEP_ID)
  REFERENCES departments (department_id)
);

-- --------------------------------------------------------



--
-- Table structure for table doctors
--
CREATE TABLE doctors (
  doctor_id bigint NOT NULL,
  fname varchar(64) NOT NULL,
  lname varchar(64) NOT NULL,
  mname varchar(64) NOT NULL,
  logonid varchar(64) NOT NULL,
  password varchar(64) NOT NULL,
  admintype_id int NOT NULL,
  department_id int NOT NULL,
  PRIMARY KEY (doctor_id),
  CONSTRAINT FK_DCDEP
  FOREIGN KEY (department_id)
  REFERENCES departments (department_id)
);

--
-- Table structure for table templates
--
CREATE TABLE templates (
  template_id bigint NOT NULL,
  name varchar(512) NOT NULL,
  department_id int NOT NULL,
  content varchar(1024) NOT NULL,
  author_id bigint NOT NULL,
  PRIMARY KEY (template_id),
  CONSTRAINT FK_TPDEP
  FOREIGN KEY (department_id)
  REFERENCES departments (department_id)
);
-- --------------------------------------------------------

--
-- Table structure for table patients
--

CREATE TABLE patients (
  patient_id bigint NOT NULL,
  fname varchar(128) NOT NULL,
  lname varchar(128) NOT NULL,
  mname varchar(128) DEFAULT NULL,
  description varchar(1024),
  dayofbirth date NOT NULL,
  gender int NOT NULL,
  PRIMARY KEY (patient_id)
);
-- --------------------------------------------------------

--
-- Table structure for table address
--

CREATE TABLE address (
  address_id bigint NOT NULL,
  region varchar(256) NOT NULL,
  district varchar(256) NOT NULL,
  zip varchar(9) NOT NULL,
  city varchar(256) NOT NULL,
  address1 varchar(1024) NOT NULL,
  phone varchar(32) NOT NULL,
  email varchar(256) NOT NULL,
  patient_id bigint NOT NULL,
  PRIMARY KEY (address_id),
  CONSTRAINT FK_ADPTS
  FOREIGN KEY (patient_id)
  REFERENCES patients (patient_id)
);

-- --------------------------------------------------------

--
-- Table structure for table epicrisis
--

CREATE TABLE epicrisis (
  epicrisis_id bigint NOT NULL,
  patient_id bigint NOT NULL,
  doctor_id bigint NOT NULL,
  department_id int NOT NULL,
  date date NOT NULL,
  name varchar(1024) NOT NULL,
  description varchar(1024) NOT NULL,
  CONSTRAINT FK_EPDEP
  FOREIGN KEY (department_id)
  REFERENCES departments (department_id),
  CONSTRAINT FK_EPPTS
  FOREIGN KEY (patient_id)
  REFERENCES patients (patient_id),
  CONSTRAINT FK_EPDCT
  FOREIGN KEY (doctor_id)
  REFERENCES doctors (doctor_id)  
);

-- --------------------------------------------------------

--
-- Table structure for table measurements
--

CREATE TABLE measurements (
  measurement_id bigint NOT NULL,
  department_id bigint NOT NULL,
  patient_id bigint NOT NULL,
  name varchar(32),
  doctor_id bigint NOT NULL,
  date date NOT NULL,
  type int NOT NULL,
  PRIMARY KEY (measurement_id),
  CONSTRAINT FK_PTS
  FOREIGN KEY (patient_id)
  REFERENCES patients (patient_id),
  CONSTRAINT FK_DCT
  FOREIGN KEY (doctor_id)
  REFERENCES doctors (doctor_id)
);

-- --------------------------------------------------------

--
-- Table structure for table measurevalues
--

CREATE TABLE measurevalues (
  measureval_id bigint NOT NULL,
  measurement_id bigint NOT NULL,
  intval int DEFAULT NULL,
  doubleval double DEFAULT NULL,
  stringval varchar(1024),
  type varchar(32) DEFAULT NULL,
  PRIMARY KEY (measureval_id),
  CONSTRAINT FK_MID
  FOREIGN KEY (measurement_id)
  REFERENCES measurements (measurement_id)
) ;
-- --------------------------------------------------------