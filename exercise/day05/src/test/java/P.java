import org.junit.jupiter.api.Test;
import people.Person;
import people.PetType;

import static org.assertj.core.api.Assertions.assertThat;

class PersonToText {

    @Test
    void shouldFormatPersonNicely() {
        Person person = new Person("Peter", "Griffin");

        String personFormatted = person.getPersonFormatted();

        assertThat(personFormatted)
                .isEqualTo("Peter Griffin");

    }

    @Test
    void shouldFormatPersonWith1Pet() {
        Person personWith1Pet = new Person("Peter", "Griffin")
                .addPet(PetType.CAT, "Tabby", 2);

        assertThat(personWith1Pet.getPersonFormatted())
                .isEqualTo("Peter Griffin who owns : Tabby ");

    }

    @Test
    void shouldFormatPersonWith2Pets() {
        Person personWith1Pet = new Person("Peter", "Griffin")
                .addPet(PetType.CAT, "Tabby", 2)
                .addPet(PetType.DOG, "Rolf", 4);

        assertThat(personWith1Pet.getPersonFormatted())
                .isEqualTo("Peter Griffin who owns : Tabby Rolf ");

    }
}
