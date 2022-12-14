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
}

class Header1(var value: String = ""): BodyElement {
    override fun toString(): String {
        return "<h1>$value</h1>"
    }
}

class Paragraph(var value: String = ""): BodyElement {
    override fun toString(): String {
        return "<p>$value</p>"
    }
}

class UnorderedList(var elements: MutableList<String> = mutableListOf()): BodyElement {
    override fun toString(): String {
        return "<ul>${elements.joinToString("") { "<li>$it</li>" }}</ul>"
    }

    private fun addElements(vararg newItems: String) {
        elements.addAll(newItems)
    }

    // 1. Add TriConsumer interface
    private fun interface TriConsumer<T> {
        fun accept(a: T, b: T, c: T)
    }

    // 2. Extension function on TriConsumer for curry-ing
    //  (a, b, c) -> x becomes (a)(b)(c) -> x
    private fun TriConsumer<String>.curried() =
        { p1: String -> { p2: String -> { p3: String -> this.accept(p1, p2, p3) } } }

    // 3. Alias for curried TriConsumer that adds elements to list
    val threeElementsList = TriConsumer<String> { a, b, c -> addElements(a, b, c) }.curried()
}

// 4. Extension property on Body to add the unordered list
val Body.unorderedList: (String) -> (String) -> (String) -> Unit
    get() = UnorderedList().run {
        this@unorderedList.newElement(this)
        this.threeElementsList
    }
