CREATE TABLE `birddata` (
  `birdDataId` VARCHAR(35) NOT NULL,
  `birdTypeId` VARCHAR(35) NOT NULL,
  `RINGNO` VARCHAR(50) NOT NULL,
  `RINGTYPE` VARCHAR(7) DEFAULT NULL,
  `Breedstart` NUMBER(15) DEFAULT NULL,
  `RINGAT` NUMBER(15) DEFAULT NULL,
  `BUYAT` NUMBER(15) DEFAULT NULL,
  `BUYADRESSE` VARCHAR(256) DEFAULT NULL,
  `SELLAT` NUMBER(15) DEFAULT NULL,
  `SELLADRESSE` VARCHAR(256) DEFAULT NULL,
  `DEATHAT` NUMBER(15) DEFAULT NULL,
  `DEATHNOTE` VARCHAR(256) DEFAULT NULL,
  `MEDICSTART` NUMBER(15) DEFAULT NULL,
  `MEDICNOTE` VARCHAR(256) DEFAULT NULL,
  `MEDICEND` NUMBER(15) DEFAULT NULL,
  `MEDICCONTROL` VARCHAR(256) DEFAULT NULL,
  `DOC` VARCHAR(256) DEFAULT NULL,
  `ombudsman` VARCHAR(256) DEFAULT NULL,
  `OFFICE` VARCHAR(256) DEFAULT NULL,
  `GENDER` NUMBER(2,1) NOT NULL,
  `COLOR` VARCHAR(60) DEFAULT NULL,
  `birdFATHER` VARCHAR(35) DEFAULT NULL,
  `birdMOTHER` VARCHAR(35) DEFAULT NULL,
  `MODFLAG` NUMBER(15) DEFAULT 0,
  PRIMARY KEY (`birdDataId`)
);

create table `birdspecies` (
	`birdTypeId` VARCHAR(35) NOT NULL,
	`birdspeciesname` VARCHAR(50) NOT NULL,
	PRIMARY KEY (`birdTypeId`)
	);