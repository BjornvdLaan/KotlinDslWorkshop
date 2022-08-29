package nl.bjornvanderlaan.example1.extra

import nl.bjornvanderlaan.example1.solution1.CurriculumVitaeBuilder

/*
This extension function shows something that does not work.
 */

class CertificationBuilder {
    var name = ""
    var issuer = ""

    operator fun String.minus(other: String) {
        this@CertificationBuilder.name = this
        this@CertificationBuilder.issuer = other
    }

    fun build() = "Obtained certification $name, issued by $issuer"
}

val CurriculumVitaeBuilder.certifications: MutableList<CertificationBuilder>
    get() = mutableListOf<CertificationBuilder>()

fun CurriculumVitaeBuilder.certification(apply: CertificationBuilder.() -> Unit) {
    val certificationBuilder = CertificationBuilder()
    certificationBuilder.apply()
    this.certifications += certificationBuilder
}
