import java.util.*;


public class RecommendPlant {

	static Boolean running = true;
	static LinkedList<Plant> plantsList = new LinkedList<Plant>();
	
	public static void main(String[] args) {
		
		CreatePlants();
		
		Scanner in = new Scanner (System.in);
		
		while (running) {
			
			System.out.println("Enter area conditions");
			
			System.out.println("Enter temperature");
			double temperature = in.nextDouble();
			
			System.out.println("Enter humidity");
			double humidity = in.nextDouble();
			
			System.out.println("Enter soil moisture");
			double soilMoisture = in.nextDouble();
			
			System.out.println("Enter light level");
			double lightLevel = in.nextDouble();
			
			BestFitResult bestFit = SearchForBestPlant(plantsList, temperature, humidity, soilMoisture, lightLevel);
			
			if (bestFit.getFound()) {
				
				Plant bestPlant = bestFit.getResultsList().getFirst();
				//System.out.println("Best fit is " + bestPlant.getName() + ", whose min temperature = " + bestPlant.getMinTemperature() + ", min humidity = " + bestPlant.getMinHumidity() + ", min soil moisture = " + bestPlant.getMinSoilMoisture() + ", and min light level = " + bestPlant.getMinLightLevel() + "\n");
				System.out.println("Best fit is " + bestPlant.getName());
			}
			else {
				
				System.out.println(bestFit.getMessage());
			}
			
		}
	}

	static void CreatePlants() {
		
		plantsList = CreatePlants.CreatePlantsList();
	}
	
	static BestFitResult SearchForBestPlant(LinkedList<Plant> plantsList, double temperature, double humidity, double soilMoisture, double lightLevel) {
		
		LinkedList<SearchResult> resultsList = new LinkedList<SearchResult>();
		final double tempRange = 50;
		final double humRange = 100;
		final double soilRange = 100;
		final double lightRange = 300;
		
		Boolean foundTemp = false;
		Boolean foundHum = false;
		Boolean foundSoil = false;
		Boolean foundLight = false;
		
		for (int i = 0; i < plantsList.size(); i++) {
			
			Plant plant = plantsList.get(i);
			
			double tempDif;
			double humDif;
			double soilDif;
			double lightDif;
			
			if (temperature >= plant.getMinTemperature() && temperature <= plant.getMaxTemperature()) {
				
				tempDif = (Math.abs(temperature - (plant.getMinTemperature() + plant.getMaxTemperature())/2))/tempRange;
				foundTemp = true;
			}
			else {
				
				continue;
			}

			if (humidity >= plant.getMinHumidity() && humidity <= plant.getMaxHumidity()) {
				
				humDif = (Math.abs(humidity - (plant.getMinHumidity() + plant.getMaxHumidity())/2))/humRange;
				foundHum = true;
			}
			else {
				
				continue;
			}
			
			if (soilMoisture >= plant.getMinSoilMoisture() && soilMoisture <= plant.getMaxSoilMoisture()) {
				
				soilDif = (Math.abs(soilMoisture - (plant.getMinSoilMoisture() + plant.getMaxSoilMoisture())/2))/soilRange;
				foundSoil = true;
			}
			else {
				
				continue;
			}
			
			if (lightLevel >= plant.getMinLightLevel() && lightLevel <= plant.getMaxLightLevel()) {
				
				lightDif = (Math.abs(lightLevel - (plant.getMinLightLevel() + plant.getMaxLightLevel())/2))/lightRange;
				foundLight = true;
			}
			else {
				
				continue;
			}
			
			resultsList.add(new SearchResult (plant, tempDif, humDif, soilDif, lightDif));
		}
		
		
		if (!foundTemp) {
			
			return new BestFitResult (false, "No suitable plant found for this temperature");
		}
		if (!foundHum) {
			
			return new BestFitResult (false, "No suitable plant found for this humidity");
		}
		if (!foundSoil) {
	
			return new BestFitResult (false, "No suitable plant found for this soil moisture");
		}
		if (!foundLight) {
	
			return new BestFitResult (false, "No suitable plant found for this light level");
		}
		
		double lowestTotalDifference = 1000000;
		
		BestFitResult bestFit = new BestFitResult(false, "not found");
		
		for (int i = 0; i < resultsList.size(); i++) {
			
			double currentTotalDifference = resultsList.get(i).getTotalDifference();
			
			if (currentTotalDifference < lowestTotalDifference) {
				
				lowestTotalDifference = currentTotalDifference;
				bestFit = new BestFitResult (true, resultsList.get(i).plant);
			}
		}
		
		return bestFit;
		
	}
}


 
