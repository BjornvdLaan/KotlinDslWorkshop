package nl.bjornvanderlaan.example1.solution1

import java.time.LocalDate


@DslMarker
@Target(AnnotationTarget.CLASS, AnnotationTarget.TYPE)
annotation class CurriculumVitaeDsl

@CurriculumVitaeDsl
class CurriculumVitaeBuilder {
    private val sections = mutableListOf<CvSectionBuilder>()

    private var name = ""
    private var email = ""
    private var phone = ""

    fun name(cvName: String) {
        this.name = cvName
    }

    fun email(cvEmail: String) {
        this.email = cvEmail
    }

    fun phoneNumber(cvPhone: String) {
        this.phone = cvPhone
    }

    operator fun String.invoke(block: CvSectionBuilder.() -> Unit) {
        val sectionBuilder = CvSectionBuilder()
        sectionBuilder.header(this)
        sectionBuilder.block()
        sections += sectionBuilder
    }

    fun build() = CurriculumVitae(name, email, phone, sections.map { it.build() })
}

@CurriculumVitaeDsl
class CvSectionBuilder {
    private var header = ""
    private var roles = mutableListOf<CvRoleBuilder>()

    fun header(header: String) {
        this.header = header
    }

    operator fun String.invoke(block: CvRoleBuilder.() -> Unit) {
        val roleItem = CvRoleBuilder()
        roleItem.title(this)
        roleItem.block()
        roles += roleItem
    }

    fun build() = Section(header, roles.map { it.build() })
}

@CurriculumVitaeDsl
class CvRoleBuilder {
    private var title = ""
    private var company = ""
    private var startDate = LocalDate.parse("1970-01-01")
    private var endDate = LocalDate.now()
    private var descriptionBullets = mutableListOf<String>()

    val role = this

    infix fun title(roleItemTitle: String): CvRoleBuilder {
        this.title = roleItemTitle
        return this
    }

    infix fun at(roleItemCompany: String): CvRoleBuilder {
        company = roleItemCompany
        return this
    }

    infix fun from(roleItemStartDate: String): CvRoleBuilder {
        this.startDate = roleItemStartDate.toLocalDate()
        return this
    }

    infix fun to(roleItemEndDate: String): CvRoleBuilder {
        this.endDate = roleItemEndDate.toLocalDate()
        return this
    }

    operator fun String.unaryPlus() {
        descriptionBullets += this.trimIndent()
    }

    private fun String.toLocalDate(): LocalDate {
        return LocalDate.parse(this)
    }

    fun build() = Role(title, company, startDate, endDate, descriptionBullets.joinToString(" "))
}

fun curriculumVitae(apply: CurriculumVitaeBuilder.() -> Unit): CurriculumVitae {
    val curriculumVitae = CurriculumVitaeBuilder()
    curriculumVitae.apply()
    return curriculumVitae.build()
}