package nl.bjornvanderlaan.example1.workshop1

data class Role(val title: String, val company: String)

data class CurriculumVitae(val name: String, val roles: List<Role>)

fun CurriculumVitae.toPrettyString(): String {
    val sb = StringBuilder()
    sb.append(
        """
  #####                                                              #     #                       
 #     # #    # #####  #####  #  ####  #    # #      #    # #    #   #     # # #####   ##   ###### 
 #       #    # #    # #    # # #    # #    # #      #    # ##  ##   #     # #   #    #  #  #      
 #       #    # #    # #    # # #      #    # #      #    # # ## #   #     # #   #   #    # #####  
 #       #    # #####  #####  # #      #    # #      #    # #    #    #   #  #   #   ###### #      
 #     # #    # #   #  #   #  # #    # #    # #      #    # #    #     # #   #   #   #    # #      
  #####   ####  #    # #    # #  ####   ####  ######  ####  #    #      #    #   #   #    # ###### 
                                                                                                   
    """.trimIndent()
    )
    sb.append("\n")
    sb.append("Curriculum Vitae of $name\n")
    sb.append("\n=^..^=   =^..^=   =^..^=    =^..^=    =^..^=    =^..^=    =^..^=\n\n")
    roles.forEach { role ->
        sb.append("\tRole: ${role.title}\n")
        sb.append("\tCompany: ${role.company}\n")
        sb.append("\n=^..^=   =^..^=   =^..^=    =^..^=    =^..^=    =^..^=    =^..^=\n\n")
    }
    return sb.toString()
}