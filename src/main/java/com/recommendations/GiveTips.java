package com.recommendations;

import java.util.*;

public class GiveTips {
	static LinkedList<Plant> plantsList = CreatePlants.CreatePlantsList();

	public static List<String> GET_TIPS(String name, double mTemp, double mHum, double mSoil, double mLight) {
		List<String> tips = new LinkedList<>();

		Plant plant = FindPlantInList(name);

		if (plant == null) {

			System.err.println("Plant name not in list");
			return tips;
		}

		if (mTemp != -1) {
			CheckResult tempCheck = CheckTemperature(plant, mTemp);
			addCheckResult(tempCheck, tips);
		}
		if (mHum != -1) {
			CheckResult humCheck = CheckHumidity(plant, mHum);
			addCheckResult(humCheck, tips);
		}
		if (mSoil != -1) {
			CheckResult soilCheck = CheckSoilMoisture(plant, mSoil);
			addCheckResult(soilCheck, tips);
		}
		if (mLight != -1) {
			CheckResult lightCheck = CheckLightLevel(plant, mLight);
			addCheckResult(lightCheck, tips);
		}

		return tips;
	}

	private static void addCheckResult(CheckResult cr, List<String> s) {
		s.add(cr.getMessage());
	}

	static CheckResults DoChecks(Plant plant, double mTemp, double mHum, double mSoil, double mLight) {

		CheckResult tempCheck = CheckTemperature(plant, mTemp);
		CheckResult humCheck = CheckHumidity(plant, mHum);
		CheckResult soilCheck = CheckSoilMoisture(plant, mSoil);
		CheckResult lightCheck = CheckLightLevel(plant, mLight);

		return new CheckResults(tempCheck, humCheck, soilCheck, lightCheck);
	}

	static CheckResult CheckTemperature(Plant plant, double mTemp) {

		if (mTemp < plant.getMinTemperature()) {

			return new CheckResult("bad", "Temperature is too low, plant needs heating", "temp");
		}

		if (mTemp > plant.getMaxTemperature()) {

			return new CheckResult("bad", "Temperature is too high, plant needs cooling", "temp");
		}

		return new CheckResult("good", "Temperature is ideal", "temp");
	}

	static CheckResult CheckHumidity(Plant plant, double mHum) {

		if (mHum < plant.getMinHumidity()) {

			return new CheckResult("bad", "Humidity is too low, increase humidty", "hum");
		}

		if (mHum > plant.getMaxHumidity()) {

			return new CheckResult("bad", "Humidity is too high, decrease humidity", "hum");
		}

		return new CheckResult("good", "Humidity is ideal", "hum");
	}

	static CheckResult CheckSoilMoisture(Plant plant, double mSoil) {

		if (mSoil < plant.getMinSoilMoisture()) {

			return new CheckResult("bad", "Soil moisture is too low, plant needs watering", "water");
		}

		if (mSoil > plant.getMaxSoilMoisture()) {

			return new CheckResult("bad", "Soil moisture is too high, do not water plant again yet", "water");
		}

		return new CheckResult("good", "Soil moisture is ideal", "water");
	}

	static CheckResult CheckLightLevel(Plant plant, double mLight) {

		if (mLight < plant.getMinLightLevel()) {

			return new CheckResult("bad", "Light level is too low, plant needs more light", "light");
		}

		if (mLight > plant.getMaxLightLevel()) {

			return new CheckResult("bad", "Light level is too high, plants needs less light", "light");
		}

		return new CheckResult("good", "Light level is ideal", "light");
	}

	static Plant FindPlantInList(String name) {

		for (int i = 0; i < plantsList.size(); i++) {

			if (plantsList.get(i).getName().equalsIgnoreCase(name)) {

				return plantsList.get(i);
			}
		}

		return null;
	}

}
