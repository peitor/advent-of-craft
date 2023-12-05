import people.Person;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.System.lineSeparator;

public class Population {
    private static List<Person> population;

    static String formatPopulation() {
        final var response = new StringBuilder();

        return population.stream()
                .map(person -> person.getPersonFormatted())
                .collect(Collectors.joining(lineSeparator())).toString();
    }

    public static List<Person> getPopulation() {
        return population;
    }

    public static void setPopulation(List<Person> population) {
        Population.population = population;
    }
}