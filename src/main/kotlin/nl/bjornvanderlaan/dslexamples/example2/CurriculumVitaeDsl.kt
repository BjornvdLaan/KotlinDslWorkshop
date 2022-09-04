package nl.bjornvanderlaan.dslexamples.example2

// 1. Parent Type-Safe Builder
class CvBuilder {
    private var name = ""
    private val roles = mutableListOf<CvRoleBuilder>()

    fun name(cvName: String) {
        this.name = cvName
    }

    fun build() = CurriculumVitae(name, roles.map { it.build() })

    // Function to create a new role builder
    fun role(apply: CvRoleBuilder.() -> Unit) {
        val role = CvRoleBuilder()
        role.apply()
        roles.add(role)
    }
}

// 2. Child Type-Safe Builder
class CvRoleBuilder {
    private var title = ""
    private var company = ""

    fun title(roleItemTitle: String) {
        this.title = roleItemTitle
    }

    fun company(roleItemCompany: String) {
        this.company = roleItemCompany
    }

    fun build() = Role(title, company)
}

// 3. Function to create a new CvBuilder
fun curriculumVitae(apply: CvBuilder.() -> Unit): CurriculumVitae {
    val curriculumVitae = CvBuilder()
    curriculumVitae.apply()
    return curriculumVitae.build()
}