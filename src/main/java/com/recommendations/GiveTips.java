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

		CheckResult tempCheck = CheckTemperature(plant, mTemp);
		CheckResult humCheck = CheckHumidity(plant, mHum);
		CheckResult soilCheck = CheckSoilMoisture(plant, mSoil);
		CheckResult lightCheck = CheckLightLevel(plant, mLight);

		addCheckResult(tempCheck, tips);
		addCheckResult(humCheck, tips);
		addCheckResult(soilCheck, tips);
		addCheckResult(lightCheck, tips);
		return tips;
	}

	private static void addCheckResult(CheckResult cr, List<String> s) {
		if (!cr.getOk()) {
			s.add(cr.getMessage());
		}
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

			return new CheckResult(false, "Temperature is too low, plant needs heating");
		}

		if (mTemp > plant.getMaxTemperature()) {

			return new CheckResult(false, "Temperature is too high, plant needs cooling");
		}

		return new CheckResult(true, "Temperature is ideal");
	}

	static CheckResult CheckHumidity(Plant plant, double mHum) {

		if (mHum < plant.getMinHumidity()) {

			return new CheckResult(false, "Humidity is too low, increase humidty");
		}

		if (mHum > plant.getMaxHumidity()) {

			return new CheckResult(false, "Humidity is too high, decrease humidity");
		}

		return new CheckResult(true, "Humidity is ideal");
	}

	static CheckResult CheckSoilMoisture(Plant plant, double mSoil) {

		if (mSoil < plant.getMinSoilMoisture()) {

			return new CheckResult(false, "Soil moisture is too low, plant needs watering");
		}

		if (mSoil > plant.getMaxSoilMoisture()) {

			return new CheckResult(false, "Soil moisture is too high, do not water plant again yet");
		}

		return new CheckResult(true, "Soil moisture is ideal");
	}

	static CheckResult CheckLightLevel(Plant plant, double mLight) {

		if (mLight < plant.getMinLightLevel()) {

			return new CheckResult(false, "Light level is too low, plant needs more light");
		}

		if (mLight > plant.getMaxLightLevel()) {

			return new CheckResult(false, "Light level is too high, plants needs less light");
		}

		return new CheckResult(true, "Light level is ideal");
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
