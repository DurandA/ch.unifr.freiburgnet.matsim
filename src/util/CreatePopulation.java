package util;

import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.network.Network;
import org.matsim.api.core.v01.population.Activity;
import org.matsim.api.core.v01.population.Person;
import org.matsim.api.core.v01.population.Plan;
import org.matsim.api.core.v01.population.Population;
import org.matsim.api.core.v01.population.PopulationFactory;
import org.matsim.core.api.internal.MatsimWriter;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.scenario.ScenarioUtils;
import org.matsim.core.utils.geometry.CoordinateTransformation;
import org.matsim.core.utils.geometry.transformations.TransformationFactory; 

public class CreatePopulation {

	public static void main(String[] args) {
		/*
		 * We enter coordinates in the WGS84 reference system, but we want them to appear in the population file
		 * projected to UTM33N, because we also generated the network that way.
		 */
		CoordinateTransformation ct =
				TransformationFactory.getCoordinateTransformation(TransformationFactory.WGS84, TransformationFactory.CH1903_LV03);
		/*
		 * First, create a new Config and a new Scenario.
		 */
		Config config = ConfigUtils.createConfig();
		Scenario sc = ScenarioUtils.createScenario(config);
		/*
		 * Pick the Network and the Population out of the Scenario for convenience.
		 */
		Network network = sc.getNetwork();
		Population population = sc.getPopulation();
		/*
		 * Pick the PopulationFactory out of the Population for convenience.
		 * It contains methods to create new Population items.
		 */
		PopulationFactory populationFactory = population.getFactory();
		/*
		 * Create a Person designated "1" and add it to the Population.
		 */
		for (int i=0; i<100; i++) {
			Person person = populationFactory.createPerson(sc.createId("pid"+i));
			population.addPerson(person);
			/*
			 * Create a Plan for the Person
			 */
			Plan plan = populationFactory.createPlan();
			/*
			 * Create a "home" Activity for the Person. In order to have the Person end its day at the same location,
			 * we keep the home coordinates for later use (see below).
			 * Note that we use the CoordinateTransformation created above.
			 */
			Coord homeCoordinates = sc.createCoord(7.157886,46.789348);
			Activity activity1 = populationFactory.createActivityFromCoord("home", ct.transform(homeCoordinates));
			activity1.setEndTime(21600); // leave at 6 o'clock
			plan.addActivity(activity1); // add the Activity to the Plan
			/*
			 * Create a Leg. A Leg initially hasn't got many attributes. It just says that a car will be used.
			 */
			plan.addLeg(populationFactory.createLeg("car"));
			/*
			 * Create a "work" Activity, at a different location.
			 */
			Activity activity2 = populationFactory.createActivityFromCoord("work", ct.transform(sc.createCoord(7.150989,46.803268)));
			activity2.setEndTime(57600); // leave at 4 p.m.
			plan.addActivity(activity2);
			/*
			 * Create another car Leg.
			 */
			plan.addLeg(populationFactory.createLeg("car"));
			/*
			 * End the day with another Activity at home. Note that it gets the same coordinates as the first activity.
			 */
			Activity activity3 = populationFactory.createActivityFromCoord("home", ct.transform(homeCoordinates));
			plan.addActivity(activity3);
			person.addPlan(plan);
		}
		/*
		 * Write the population (of 1 Person) to a file.
		 */
		MatsimWriter popWriter = new org.matsim.api.core.v01.population.PopulationWriter(population, network);
		popWriter.write("./input/freiburg-plans.xml");
	} 
}
