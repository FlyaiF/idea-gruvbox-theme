package io.github.flyaif.gruvbox.generator

import com.google.gson.JsonParser
import java.io.ByteArrayInputStream
import javax.xml.parsers.DocumentBuilderFactory
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class RendererTest {

    private val variant = Variant(Contrast.MEDIUM)

    @Test
    fun `editor scheme renders as well-formed xml with darcula parent`() {
        val xml = renderEditorScheme(editorSchemeFor(variant))
        val doc = DocumentBuilderFactory.newInstance()
            .newDocumentBuilder()
            .parse(ByteArrayInputStream(xml.toByteArray()))

        assertEquals("scheme", doc.documentElement.tagName)
        assertEquals("Gruvbox Dark Medium", doc.documentElement.getAttribute("name"))
        assertEquals("Darcula", doc.documentElement.getAttribute("parent_scheme"))
    }

    @Test
    fun `scheme xml writes colors without hash prefix`() {
        val xml = renderEditorScheme(editorSchemeFor(variant))
        assertTrue("""value="fb4934"""" in xml, "expected bare hex values in scheme xml")
        assertTrue("#fb4934" !in xml, "scheme xml must not contain #-prefixed colors")
    }

    @Test
    fun `ui theme renders as valid json with expected top-level shape`() {
        val json = JsonParser.parseString(renderUiTheme(uiThemeFor(variant))).asJsonObject

        assertEquals("Gruvbox Dark Medium", json["name"].asString)
        assertTrue(json["dark"].asBoolean)
        assertEquals("/themes/gruvbox-dark-medium.xml", json["editorScheme"].asString)
        assertEquals("#282828", json["colors"].asJsonObject["bg0"].asString)
        assertTrue(json["ui"].asJsonObject.has("*"))
    }

    @Test
    fun `font styles render as icls font type integers`() {
        val scheme = editorSchemeFor(variant)
        val xml = renderEditorScheme(scheme)
        // DEFAULT_FUNCTION_DECLARATION is bold green: fontType 1
        assertTrue(
            Regex(
                """DEFAULT_FUNCTION_DECLARATION">\s*<value>\s*<option name="FOREGROUND" value="b8bb26"\s*/>\s*<option name="FONT_TYPE" value="1"""",
            ).containsMatchIn(xml),
            "expected bold font type on function declarations",
        )
    }
}
