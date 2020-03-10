CREATE TABLE Temperature (
	dateTime TIMESTAMP NOT NULL,
	sensorID INT NOT NULL,
	value INT NOT NULL,
	PRIMARY KEY (dateTime),
	FOREIGN KEY (sensorID) REFERENCES Sensors(sensorID)
);
