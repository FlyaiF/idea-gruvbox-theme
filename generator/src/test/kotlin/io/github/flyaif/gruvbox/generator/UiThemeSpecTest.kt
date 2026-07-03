package io.github.flyaif.gruvbox.generator

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class UiThemeSpecTest {

    private val theme = uiThemeFor(Variant(Contrast.SOFT))

    @Test
    fun `theme is dark, named for the variant, and points at its scheme`() {
        assertEquals("Gruvbox Dark Soft", theme.name)
        assertTrue(theme.dark)
        assertEquals("/themes/gruvbox-dark-soft.xml", theme.editorSchemePath)
    }

    @Test
    fun `named colors carry the palette and the variant bg0`() {
        assertEquals(Palette.bg0Soft.hex, theme.colors["bg0"])
        assertEquals(Palette.fg1.hex, theme.colors["fg1"])
        assertEquals(Palette.brightOrange.hex, theme.colors["orange"])
    }

    @Test
    fun `every ui value that looks like a color reference resolves to a named color`() {
        val names = theme.colors.keys

        fun check(node: Any?, path: String) {
            when (node) {
                is Map<*, *> -> node.forEach { (k, v) -> check(v, "$path.$k") }
                is String ->
                    if (!node.startsWith("#")) {
                        assertTrue(node in names, "$path references unknown color name '$node'")
                    }
                else -> {} // numbers, booleans: fine
            }
        }
        check(theme.ui, "ui")
    }

    @Test
    fun `wildcard defaults put fg1 on bg0`() {
        @Suppress("UNCHECKED_CAST")
        val star = theme.ui["*"] as Map<String, Any>
        assertEquals("bg0", star["background"])
        assertEquals("fg1", star["foreground"])
    }

    @Test
    fun `the accent underline on editor tabs is gruvbox orange`() {
        @Suppress("UNCHECKED_CAST")
        val tabs = theme.ui["EditorTabs"] as Map<String, Any>
        assertEquals("orange", tabs["underlineColor"])
    }
}
