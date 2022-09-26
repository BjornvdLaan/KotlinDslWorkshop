package nl.bjornvanderlaan.dslexamples.example1_1

fun main() {


    val myHtml = html {

        head {
            + "My Kotlin Website"
            - "My Kotlin Website"
            ! "My Kotlin Website"
        }

        body {
            a newElement Header1("XML encoding in Kotlin")
            a newElement Paragraph("This is some text")

            "My new section" {
                a newElement Paragraph("My new section is beautiful")
            }

            a newElement (img src "https://kotlin.nl/logo.png" alt "Kotlin logo" width 200 height 200)

            unorderedList ("a")("b")("c")
        }
    }

    print(myHtml)
}
