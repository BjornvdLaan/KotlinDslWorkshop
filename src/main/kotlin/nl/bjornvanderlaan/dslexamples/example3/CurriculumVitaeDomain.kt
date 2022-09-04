package nl.bjornvanderlaan.dslexamples.example3

import java.time.LocalDate


data class Role(
    val title: String,
    val company: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val description: String
)

data class Section(val name: String, val entries: List<Role>)

data class CurriculumVitae(val name: String, val email: String, val phone: String, val roles: List<Section>)

fun CurriculumVitae.toPrettyString(): String {
    val sb = StringBuilder()
    sb.append("Name: $name\n")
    sb.append("Email: $email\n")
    sb.append("Phone: $phone\n")
    sb.append("\n")
    roles.forEach { section ->
        sb.append("Section: ${section.name}\n")
        section.entries.forEach { role ->
            sb.append("\tRole: ${role.title}\n")
            sb.append("\tCompany: ${role.company}\n")
            sb.append("\tStart Date: ${role.startDate}\n")
            if (role.endDate.isAfter(LocalDate.now())) {
                sb.append("\tEnd Date: ${role.endDate}\n")
            } else {
                sb.append("\tEnd Date: ${role.endDate} (current)\n")
            }
            if (role.description.isNotBlank()) {
                sb.append("\tDescription: ${role.description}\n")
            }
            sb.append("\n")
        }
        sb.append("\n")
    }
    return sb.toString()
}