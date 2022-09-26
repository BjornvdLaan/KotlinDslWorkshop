package nl.bjornvanderlaan.dslexamples.example1_1

interface Element
interface BodyElement

@DslMarker
@Target(AnnotationTarget.CLASS, AnnotationTarget.TYPE)
annotation class HtmlDsl

@HtmlDsl
class HTML(private var head: Head = Head(), private var body: Body = Body()): Element {
    override fun toString(): String {
        return "<html>$head$body</html>"
    }

    fun head(init: Head.() -> Unit) {
        val myHead = Head()
        myHead.init()
        this.head = myHead
    }

    fun body(init: Body.() -> Unit) {
        val myBody = Body()
        myBody.init()
        this.body = myBody
    }
}

@HtmlDsl
class Head(private var value: String = ""): Element {
    override fun toString(): String {
        return "<head>$value</head>"
    }

    operator fun String.unaryPlus() {
        value = this
    }

    operator fun String.unaryMinus() {
        value = this
    }

    operator fun String.not() {
        value = this
    }
}

fun html(init: HTML.() -> Unit): HTML {
    val html = HTML()
    html.init()
    return html
}

@HtmlDsl
class Div(var elements: MutableList<BodyElement> = mutableListOf()): BodyElement {
    override fun toString(): String {
        return "<div>${elements.joinToString("")}</div>"
    }

    val a = this

    infix fun newElement(element: BodyElement) {
        elements.add(element)
    }
}

@HtmlDsl
class Body(private var elements: List<BodyElement> = listOf()): Element {
    override fun toString(): String {
        return "<body>${elements.joinToString("")}</body>"
    }

    // 1. Create an alias for this
    val a = this

    // 2. Add infix function to use behind alias
    infix fun newElement(element: BodyElement): Body {
        this.elements += element
        return this
    }

    operator fun String.invoke(init: Div.() -> Unit) {
        val newDiv = Div()
        newDiv.elements += Header1(this)
        newDiv.init()
        this@Body.elements += newDiv
    }
}

@HtmlDsl
class Header1(private var value: String = ""): BodyElement {
    override fun toString(): String {
        return "<h1>$value</h1>"
    }
}

@HtmlDsl
class Paragraph(private var value: String = ""): BodyElement {
    override fun toString(): String {
        return "<p>$value</p>"
    }
}

@HtmlDsl
class Img(
    private var src: String = "",
    private var alt: String = "",
    private var width: Int = 100,
    private var height: Int = 100
): BodyElement {
    @Override
    override fun toString(): String {
        return "<img src=\"$src\" alt=\"$alt\" width=\"$width\" height=\"$height\" />"
    }

    infix fun src(value: String): Img {
        this.src = value
        return this
    }

    infix fun alt(value: String): Img {
        this.alt = value
        return this
    }

    infix fun width(value: Int): Img {
        this.width = value
        return this
    }

    infix fun height(value: Int): Img {
        this.height = value
        return this
    }
}

val Body.img: Img
    get() = Img().also {
        this@img.newElement(it)
    }

@HtmlDsl
class UnorderedList(private var elements: MutableList<String> = mutableListOf()): BodyElement {
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
