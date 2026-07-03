package io.github.flyaif.gruvbox.generator

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Vim-faithfulness tests: every assertion here mirrors a mapping that
 * gruvbox.vim defines explicitly (see the Mapping entry in CONTEXT.md).
 */
class EditorSchemeSpecTest {

    private val scheme = editorSchemeFor(Variant(Contrast.MEDIUM))

    private fun attr(key: String): Attr =
        scheme.attributes[key] ?: error("scheme does not define $key")

    @Test
    fun `scheme names variant and inherits Darcula`() {
        assertEquals("Gruvbox Dark Medium", scheme.name)
        assertEquals("Darcula", scheme.parentScheme)
    }

    @Test
    fun `default text is fg1 on the variant bg0`() {
        val text = attr("TEXT")
        assertEquals(Palette.fg1, text.foreground)
        assertEquals(Palette.bg0, text.background)
    }

    @Test
    fun `keywords are bright red`() { // Keyword -> GruvboxRed
        assertEquals(Palette.brightRed, attr("DEFAULT_KEYWORD").foreground)
    }

    @Test
    fun `strings are bright green`() { // String -> green
        assertEquals(Palette.brightGreen, attr("DEFAULT_STRING").foreground)
    }

    @Test
    fun `numbers and constants are bright purple`() { // Number/Constant -> GruvboxPurple
        assertEquals(Palette.brightPurple, attr("DEFAULT_NUMBER").foreground)
        assertEquals(Palette.brightPurple, attr("DEFAULT_CONSTANT").foreground)
    }

    @Test
    fun `class names are bright yellow`() { // Type -> GruvboxYellow
        assertEquals(Palette.brightYellow, attr("DEFAULT_CLASS_NAME").foreground)
    }

    @Test
    fun `function declarations are bold bright green`() { // Function -> GruvboxGreenBold
        val decl = attr("DEFAULT_FUNCTION_DECLARATION")
        assertEquals(Palette.brightGreen, decl.foreground)
        assertTrue(decl.bold)
    }

    @Test
    fun `comments are italic gray`() { // Comment -> gray + italicize_comments
        val comment = attr("DEFAULT_LINE_COMMENT")
        assertEquals(Palette.gray, comment.foreground)
        assertTrue(comment.italic)
    }

    @Test
    fun `string escapes are bright orange`() { // Special -> GruvboxOrange
        assertEquals(Palette.brightOrange, attr("DEFAULT_VALID_STRING_ESCAPE").foreground)
    }

    @Test
    fun `java annotations are bright blue`() { // javaAnnotation -> GruvboxBlue
        assertEquals(Palette.brightBlue, attr("ANNOTATION_NAME_ATTRIBUTES").foreground)
    }

    @Test
    fun `javadoc tags are bright aqua`() { // javaDocTags -> GruvboxAqua
        assertEquals(Palette.brightAqua, attr("DEFAULT_DOC_COMMENT_TAG").foreground)
    }

    @Test
    fun `static final fields read as constants`() { // Constant -> GruvboxPurple
        assertEquals(Palette.brightPurple, attr("STATIC_FINAL_FIELD_ATTRIBUTES").foreground)
    }

    @Test
    fun `instance fields are bright blue`() { // Identifier -> GruvboxBlue
        assertEquals(Palette.brightBlue, attr("INSTANCE_FIELD_ATTRIBUTES").foreground)
    }

    @Test
    fun `xml tags are blue but html tag names are bold aqua`() { // xmlTagName vs htmlTagName
        assertEquals(Palette.brightBlue, attr("XML_TAG_NAME").foreground)
        val html = attr("HTML_TAG_NAME")
        assertEquals(Palette.brightAqua, html.foreground)
        assertTrue(html.bold)
    }

    @Test
    fun `xml attributes are aqua and entities orange`() { // xmlAttrib, xmlEntity
        assertEquals(Palette.brightAqua, attr("XML_ATTRIBUTE_NAME").foreground)
        assertEquals(Palette.brightOrange, attr("XML_ENTITY_REFERENCE").foreground)
    }

    @Test
    fun `typos get a blue wave like SpellBad`() { // SpellBad -> undercurl blue
        val typo = attr("TYPO")
        assertEquals(Palette.brightBlue, typo.effectColor)
        assertEquals(EffectType.WAVE, typo.effectType)
    }

    @Test
    fun `errors wave in bright red`() {
        val err = attr("ERRORS_ATTRIBUTES")
        assertEquals(Palette.brightRed, err.effectColor)
        assertEquals(EffectType.WAVE, err.effectType)
    }

    @Test
    fun `caret row is bg1 and selection bg2`() { // CursorLine -> bg1
        assertEquals(Palette.bg1, scheme.colors["CARET_ROW_COLOR"])
        assertEquals(Palette.bg2, scheme.colors["SELECTION_BACKGROUND"])
    }

    @Test
    fun `line numbers are bg4`() { // LineNr -> bg4
        assertEquals(Palette.bg4, scheme.colors["LINE_NUMBERS_COLOR"])
    }

    @Test
    fun `matched braces highlight on bg3 like MatchParen`() {
        val brace = attr("MATCHED_BRACE_ATTRIBUTES")
        assertEquals(Palette.bg3, brace.background)
        assertTrue(brace.bold)
    }

    @Test
    fun `diff backgrounds are dim blends of the vim diff hues`() {
        // DiffAdd green / DiffDelete red / DiffChange aqua / DiffText yellow,
        // adapted from vim's inverse-video to subtle backgrounds.
        assertEquals(Palette.neutralGreen.blendOver(Palette.bg0, 0.25), attr("DIFF_INSERTED").background)
        assertEquals(Palette.neutralRed.blendOver(Palette.bg0, 0.25), attr("DIFF_DELETED").background)
        assertEquals(Palette.neutralAqua.blendOver(Palette.bg0, 0.25), attr("DIFF_MODIFIED").background)
    }

    @Test
    fun `console ansi colors use neutral and bright palette rows`() {
        assertEquals(Palette.neutralRed, attr("CONSOLE_RED_OUTPUT").foreground)
        assertEquals(Palette.brightRed, attr("CONSOLE_RED_BRIGHT_OUTPUT").foreground)
        assertEquals(Palette.fg1, attr("CONSOLE_WHITE_OUTPUT").foreground)
        assertEquals(Palette.gray, attr("CONSOLE_DARKGRAY_OUTPUT").foreground)
    }
}
