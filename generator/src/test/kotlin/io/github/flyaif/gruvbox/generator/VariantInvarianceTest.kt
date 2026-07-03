package io.github.flyaif.gruvbox.generator

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * The Contrast Level axis must only move background shades (CONTEXT.md:
 * "Only background shades differ between levels; foreground and accent
 * colors are shared.").
 */
class VariantInvarianceTest {

    @Test
    fun `there are exactly three dark variants`() {
        assertEquals(
            listOf("gruvbox-dark-hard", "gruvbox-dark-medium", "gruvbox-dark-soft"),
            allVariants.map { it.fileStem },
        )
    }

    @Test
    fun `variant bg0 follows the gruvbox contrast backgrounds`() {
        assertEquals(Palette.bg0Hard, Variant(Contrast.HARD).bg0)
        assertEquals(Palette.bg0, Variant(Contrast.MEDIUM).bg0)
        assertEquals(Palette.bg0Soft, Variant(Contrast.SOFT).bg0)
    }

    @Test
    fun `editor schemes differ across contrasts only where bg0 is involved`() {
        val hard = editorSchemeFor(Variant(Contrast.HARD))
        val medium = editorSchemeFor(Variant(Contrast.MEDIUM))

        assertEquals(medium.attributes.keys, hard.attributes.keys)
        assertEquals(medium.colors.keys, hard.colors.keys)

        val differingAttrs = medium.attributes.keys.filter { medium.attributes[it] != hard.attributes[it] }
        val differingColors = medium.colors.keys.filter { medium.colors[it] != hard.colors[it] }

        (differingAttrs + differingColors).forEach { key ->
            val m = medium.attributes[key]
            val h = hard.attributes[key]
            val usesBg0 = if (m != null && h != null) {
                // every channel that differs must be the one carrying bg0 or a blend over it
                listOf(m.foreground to h.foreground, m.background to h.background, m.effectColor to h.effectColor)
                    .filter { (a, b) -> a != b }
                    .all { (a, _) -> a != null }
            } else {
                medium.colors[key] != hard.colors[key]
            }
            assertTrue(usesBg0, "attribute $key differs across contrasts in a non-bg0 channel")
        }

        // and the differences must exist at all: TEXT background carries bg0
        assertEquals(Palette.bg0Hard, hard.attributes["TEXT"]!!.background)
        assertEquals(Palette.bg0, medium.attributes["TEXT"]!!.background)
    }

    @Test
    fun `ui themes differ across contrasts only in bg0-derived named colors`() {
        val hard = uiThemeFor(Variant(Contrast.HARD))
        val medium = uiThemeFor(Variant(Contrast.MEDIUM))

        assertEquals(medium.colors.keys, hard.colors.keys)
        val differing = medium.colors.keys.filter { medium.colors[it] != hard.colors[it] }
        assertTrue(differing.isNotEmpty(), "expected at least the bg0 named color to differ")
        differing.forEach { name ->
            assertTrue(name.startsWith("bg0"), "named color $name differs across contrasts but is not bg0-derived")
        }

        // the ui/icons sections reference colors by name, so they must be identical
        assertEquals(medium.ui, hard.ui)
        assertEquals(medium.icons, hard.icons)
    }
}
