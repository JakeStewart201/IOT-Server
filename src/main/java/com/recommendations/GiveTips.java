import java.util.*;

public class GiveTips {

	static Boolean running = true;
	static LinkedList<Plant> plantsList = new LinkedList<Plant>();
	
	public static void main(String[] args) {
		
		CreatePlants();
		
		Scanner in = new Scanner (System.in);
		
		while (running) {                                                                                    
			
			System.out.println("Enter plant name to check:");
			String name = in.next();
			Plant plant = FindPlantInList(name);
			
			if (plant == null) {
				
				System.out.println("Plant name not in list");
				continue;
			}
			
			System.out.println("Enter measured temperature:");
			double mTemp = in.nextDouble();
			System.out.println("Enter measured humidity:");
			double mHum = in.nextDouble();
			System.out.println("Enter measured soil moisture:");
			double mSoil = in.nextDouble();
			System.out.println("Enter measured light level:");
			double mLight = in.nextDouble();
				
			CheckResult tempCheck = CheckTemperature(plant, mTemp);
			CheckResult humCheck = CheckHumidity(plant, mHum);
			CheckResult soilCheck = CheckSoilMoisture(plant, mSoil);
			CheckResult lightCheck = CheckLightLevel(plant, mLight);
			
			System.out.println(tempCheck.getMessage());
			System.out.println(humCheck.getMessage());
			System.out.println(soilCheck.getMessage());
			System.out.println(lightCheck.getMessage());
			
		}
	}
	
	static void CreatePlants() {

		plantsList = CreatePlants.CreatePlantsList();
	}
	
	static CheckResults DoChecks(Plant plant, double mTemp, double mHum, double mSoil, double mLight) {
		
		CheckResult tempCheck = CheckTemperature(plant, mTemp);
		CheckResult humCheck = CheckHumidity(plant, mHum);
		CheckResult soilCheck = CheckSoilMoisture(plant, mSoil);
		CheckResult lightCheck = CheckLightLevel(plant, mLight);
		
		return new CheckResults (tempCheck, humCheck, soilCheck, lightCheck);
	}
	
	static CheckResult CheckTemperature (Plant plant, double mTemp) {
		
		if (mTemp < plant.getMinTemperature()) {
			
			return new CheckResult (false, "Temperature is too low, plant needs heating");
		}
		
		if (mTemp > plant.getMaxTemperature()) {
			
			return new CheckResult (false, "Temperature is too high, plant needs cooling");
		}
		
		return new CheckResult (true, "Temperature is ideal");
	}
	
	static CheckResult CheckHumidity (Plant plant, double mHum) {
		
		if (mHum < plant.getMinHumidity()) {
			
			return new CheckResult (false, "Humidity is too low, increase humidty");
		}
		
		if (mHum > plant.getMaxHumidity()) {
			
			return new CheckResult (false, "Humidity is too high, decrease humidity");
		}
		
		return new CheckResult (true, "Humidity is ideal");
	}

	static CheckResult CheckSoilMoisture (Plant plant, double mSoil) {
		
		if (mSoil < plant.getMinSoilMoisture()) {
			
			return new CheckResult (false, "Soil moisture is too low, plant needs watering");
		}
		
		if (mSoil > plant.getMaxSoilMoisture()) {
			
			return new CheckResult (false, "Soil moisture is too high, do not water plant again yet");
		}
		
		return new CheckResult (true, "Soil moisture is ideal");
	}

	static CheckResult CheckLightLevel (Plant plant, double mLight) {
	
		if (mLight < plant.getMinLightLevel()) {
			
			return new CheckResult (false, "Light level is too low, plant needs more light");
		}
		
		if (mLight > plant.getMaxLightLevel()) {
			
			return new CheckResult (false, "Light level is too high, plants needs less light");
		}
		
		return new CheckResult (true, "Light level is ideal");
	}
	
	static Plant FindPlantInList(String name) {
		
		for (int i = 0; i < plantsList.size(); i++) {
			
			if (plantsList.get(i).getName().equals(name)) {
				
				return plantsList.get(i);
			}
		}
		
		return null;
	}
	

}
