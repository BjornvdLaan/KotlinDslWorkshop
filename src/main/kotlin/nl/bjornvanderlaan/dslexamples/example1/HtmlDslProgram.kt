package nl.bjornvanderlaan.dslexamples.example1


fun printHtmlStep(step: Int, html: HTML) {
    println("Step $step")
    println(html.toString())
}

fun main(args: Array<String>) {
    var myHtml = HTML()

    //Version 0
    myHtml = HTML(
        Head("XML encoding in Kotlin"),
        Body(listOf(Header1("XML encoding in Kotlin"), Paragraph("This is some text")))
    )

    printHtmlStep(0, myHtml)

    //Version 1
    fun html1(init: () -> HTML): HTML {
        return init()
    }

    myHtml = html1({
        //Create new HTML object
        val html = HTML()

        //Apply changes to HTML object
        html.head = Head("XML encoding in Kotlin")
        val body = Body()
        body.elements = listOf(
            Header1("XML encoding in Kotlin"), Paragraph("This is some text")
        )
        html.body = body

        //Return HTML object
        html
    })

    printHtmlStep(1, myHtml)

    //Step 2: lambda can be put outside
    myHtml = html1 {
        //Create new HTML object
        val html = HTML()

        //Apply changes to HTML object
        html.head = Head("XML encoding in Kotlin")
        val body = Body()
        body.elements = listOf(
            Header1("XML encoding in Kotlin"), Paragraph("This is some text")
        )
        html.body = body

        //Return HTML object
        html
    }

    printHtmlStep(2, myHtml)

    //Step 3: getting rid of having to initialize new HTML object
    // Cool, but you still need a lambda parameter which looks kind of bad
    fun html3(init: (HTML) -> HTML): HTML {
        val html = HTML()
        val changedHtml = init(html)
        return changedHtml
    }

    myHtml = html3 { html ->
        //Apply changes to HTML object
        html.head = Head("XML encoding in Kotlin")
        val body = Body()
        body.elements = listOf(
            Header1("XML encoding in Kotlin"), Paragraph("This is some text")
        )
        html.body = body

        html
    }

    printHtmlStep(3, myHtml)

    // Step 4: function literal with receiver, so no lambda parameter anymore
    // Still awkward to return the object at the end of the builder..
    fun html4(init: HTML.() -> HTML): HTML {
        val html = HTML()
        val changedHtml = html.init()
        return changedHtml
    }

    myHtml = html4 {
        //Apply changes to HTML object
        head = Head("XML encoding in Kotlin")
        val myBody = Body()
        myBody.elements = listOf(
            Header1("XML encoding in Kotlin"), Paragraph("This is some text")
        )
        body = myBody

        this
    }

    printHtmlStep(4, myHtml)

    // Step 5: Change lambda return type
    fun html(init: HTML.() -> Unit): HTML {
        val html = HTML()
        html.init()
        return html
    }

    myHtml = html {
        //Apply changes to HTML object
        head = Head("XML encoding in Kotlin")
        val myBody = Body()
        myBody.elements = listOf(
            Header1("XML encoding in Kotlin"), Paragraph("This is some text")
        )
        body = myBody
    }

    printHtmlStep(5, myHtml)

    //Step 6: Re-applying the pattern
    fun HTML.head(init: Head.() -> Unit) {
        val myHead = Head()
        myHead.init()
        this.head = myHead
    }

    fun HTML.body(init: Body.() -> Unit) {
        val myBody = Body()
        myBody.init()
        this.body = myBody
    }

    myHtml = html {

        head {
            value = "XML encoding in Kotlin"
        }

        body {
            this.elements = listOf(Header1("XML encoding in Kotlin"), Paragraph("This is some text"))
        }

    }

    printHtmlStep(6, myHtml)

    //Step 7: going wild between the brackets
    myHtml = html {

        head {
            + "XML encoding in Kotlin"
        }

        body {
            a newElement Header1("XML encoding in Kotlin")
            a newElement Paragraph("This is some text")

            "My new section" {
                a newElement Paragraph("My new section is beautiful")
            }

            unorderedList("a")("b")("c")
        }

    }

    printHtmlStep(7, myHtml)
}
