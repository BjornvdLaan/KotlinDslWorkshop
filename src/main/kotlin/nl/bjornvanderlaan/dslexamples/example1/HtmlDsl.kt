package nl.bjornvanderlaan.dslexamples.example1

interface Element
interface BodyElement

class HTML(var head: Head = Head(), var body: Body = Body()): Element {
    override fun toString(): String {
        return "<html>$head$body</html>"
    }
}

class Head(var value: String = ""): Element {
    override fun toString(): String {
        return "<head>$value</head>"
    }

    operator fun String.unaryPlus() {
        value = this
    }
}

class Div(var elements: List<BodyElement> = listOf()): BodyElement {
    override fun toString(): String {
        return "<div>${elements.joinToString("")}</div>"
    }
}

class Body(var elements: List<BodyElement> = listOf()): Element {
    override fun toString(): String {
        return "<body>${elements.joinToString("")}</body>"
    }

    val a = this

    infix fun newElement(element: BodyElement) {
        this.elements += element
    }

    operator fun String.invoke(init: Div.() -> Unit) {
        val newDiv = Div()
        newDiv.elements += Header1(this)
        newDiv.init()
        this@Body.elements += newDiv
    }

    val unorderedList: (String) -> (String) -> (String) -> Unit = UnorderedList().run {
       this@Body.newElement(this)
       this.unorderedList
    }
}

class Header1(value: String = ""): BodyElement {
    override fun toString(): String {
        return "<h1>value</h1>"
    }
}

class Paragraph(value: String = ""): BodyElement {
    override fun toString(): String {
        return "<p>value</p>"
    }
}

class UnorderedList(var items: MutableList<String> = mutableListOf()): BodyElement {
    override fun toString(): String {
        return "<ul>${items.joinToString("") { "<li>$it</li>" }}</ul>"
    }

    fun interface TriStringConsumer {
        fun accept(a: String, b: String, c: String)

    }

    private fun TriStringConsumer.curried() =
        { p1: String -> { p2: String -> { p3: String -> this.accept(p1, p2, p3) } } }

    private fun addItems(vararg newItems: String) {
        items.addAll(newItems)
    }

    val unorderedList = TriStringConsumer { a, b, c -> addItems(a, b, c) }.curried()
}
