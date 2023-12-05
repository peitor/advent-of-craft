package people;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

public record Person(String firstName, String lastName, List<Pet> pets) {
    public Person(String firstName, String lastName) {
        this(firstName, lastName, new ArrayList<>());
    }

    public String getPersonFormatted() {
        var personStringBuilder = new StringBuilder();
        personStringBuilder.append(format("%s %s", firstName(), lastName()));

        if (!pets().isEmpty()) {
            personStringBuilder.append(" who owns : ");
        }

        for (var pet : pets()) {
            personStringBuilder.append(pet.name()).append(" ");
        }

        return personStringBuilder.toString();
    }

    public Person addPet(PetType petType, String name, int age) {
        pets.add(new Pet(petType, name, age));
        return this;
    }
}
