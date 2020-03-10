CREATE TABLE Sensors (
	sensorID INT NOT NULL,
	deviceID INT NOT NULL,
	PRIMARY KEY (sensorID),
	FOREIGN KEY (deviceID) REFERENCES Device(deviceID)
);
