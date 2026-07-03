package io.github.flyaif.gruvbox.generator

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class RgbTest {

    @Test
    fun `parses hex with leading hash`() {
        assertEquals("#1d2021", Rgb.parse("#1d2021").hex)
    }

    @Test
    fun `plainHex drops the hash`() {
        assertEquals("fb4934", Rgb.parse("#fb4934").plainHex)
    }

    @Test
    fun `rejects malformed hex`() {
        assertFailsWith<IllegalArgumentException> { Rgb.parse("fb4934") }
        assertFailsWith<IllegalArgumentException> { Rgb.parse("#fb49") }
        assertFailsWith<IllegalArgumentException> { Rgb.parse("#gggggg") }
    }

    @Test
    fun `blend at 0 returns the base, at 1 returns the color`() {
        val red = Rgb.parse("#fb4934")
        val bg = Rgb.parse("#282828")
        assertEquals(bg, red.blendOver(bg, 0.0))
        assertEquals(red, red.blendOver(bg, 1.0))
    }

    @Test
    fun `blend interpolates channels linearly`() {
        val white = Rgb.parse("#ffffff")
        val black = Rgb.parse("#000000")
        assertEquals("#808080", white.blendOver(black, 0.5).hex)
    }
}
