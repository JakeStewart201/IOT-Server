package com.recommendations;

import java.util.*;

public class CreatePlants {

	public CreatePlants() {
		
	}
	
	public static LinkedList<Plant> CreatePlantsList() {
		
		LinkedList<Plant> plantsList = new LinkedList<Plant>();
		
		plantsList.add(new Plant("Tomato", 16, 30, 80, 90, 70, 80, 700, 800));
		plantsList.add(new Plant("Cactus", 18, 30, 15, 25, 35, 50, 600, 900));
		plantsList.add(new Plant("Carrot", 0, 10, 90, 100, 65, 75, 700, 800));
		plantsList.add(new Plant("Marrigold", 10, 25, 80, 100, 60, 80, 600, 900));
		plantsList.add(new Plant("Venus Fly Trap", 15, 35, 50, 100, 60, 100, 600, 900));
		
		
		return plantsList;
	}
}
