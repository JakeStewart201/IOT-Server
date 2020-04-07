CREATE TABLE SoilMoisture (
	dateTime TIMESTAMP NOT NULL,
	sensorID INT NOT NULL,
	value INT NOT NULL,
	PRIMARY KEY (dateTime, sensorID),
	FOREIGN KEY (sensorID) REFERENCES Sensors(sensorID)
);
