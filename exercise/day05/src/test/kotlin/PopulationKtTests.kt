/*
I did not begin the actual challenge but here is my kotlin conversion of the test PopulationTests

1️⃣ type alias to add business meaning to collection (might be a first step to wrapped collection)
2️⃣ extract constant
3️⃣ replace stream() with kotlin std lib
4️⃣ use kotest assertion
5️⃣ extension function (on type alias) for test helper
6️⃣ use string template
7️⃣ replace negated isEmpty by isNotEmpty
8️⃣ extension function for test helper (might be a first step before moving it into Person class)
 */


// 1️⃣
typealias Population = List<Person>
// 2️⃣
val EOL = System.lineSeparator()!!

class PopulationKtTests : StringSpec({
    lateinit var population: Population
    beforeSpec {
        population = listOf(
                Person("Peter", "Griffin")
                        .addPet(PetType.CAT, "Tabby", 2),
// omitted for bievity
                Person("Glenn", "Quagmire")
        )
    }

    "Who owns the youngest pet" {
        // 3️⃣
        val filtered = population.minByOrNull(Person::youngestPetAge)
        // 4️⃣
        filtered!!.firstName shouldBe "Lois"
    }

    "People with their pets" {
        val response = population.format()

        response.toString() shouldBe
                "Peter Griffin who owns : Tabby " + EOL +
// omitted for brievity
                "Glenn Quagmire"
    }

})
// 5️⃣
fun Population.format(): StringBuilder {
    val response = StringBuilder()
    for (person in this) {
        // 6️⃣
        response.append("${person.firstName} ${person.lastName}")
        // 7️⃣
        if (person.pets.isNotEmpty()) {
            response.append(" who owns : ")
        }
        for (pet in person.pets) {
            response.append(pet.name).append(" ")
        }
        if (this.last() != person) {
            response.append(EOL)
        }
    }
    return response
}
// 8️⃣
fun Person.youngestPetAge(): Int {
    return this.pets.minOfOrNull(Pet::age) ?: Int.MAX_VALUE
}