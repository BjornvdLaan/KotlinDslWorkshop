package nl.bjornvanderlaan.example1.workshop1

fun main() {
    val myCV = curriculumVitae {
        name("Bjornvdlaan")

        role {
            title("Software Engineer")
            company("Interesting Corp ltd")
        }

        role {
            title("Solution Architect")
            company("United Bureaucratic Bank")
        }
    }

    println(myCV.toPrettyString())
}