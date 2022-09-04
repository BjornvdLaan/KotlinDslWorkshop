package nl.bjornvanderlaan.dslexamples.example2

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