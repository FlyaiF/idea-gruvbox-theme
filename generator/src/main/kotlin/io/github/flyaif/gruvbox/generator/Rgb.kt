package io.github.flyaif.gruvbox.generator

import kotlin.math.roundToInt

@JvmInline
value class Rgb(private val packed: Int) {

    val hex: String get() = "#%06x".format(packed)
    val plainHex: String get() = "%06x".format(packed)

    /** This color composited over [base] with opacity [alpha] (0.0 = all base, 1.0 = all this). */
    fun blendOver(base: Rgb, alpha: Double): Rgb {
        fun channel(shift: Int): Int {
            val top = (packed shr shift) and 0xff
            val bottom = (base.packed shr shift) and 0xff
            return (top * alpha + bottom * (1 - alpha)).roundToInt()
        }
        return Rgb((channel(16) shl 16) or (channel(8) shl 8) or channel(0))
    }

    override fun toString(): String = hex

    companion object {
        fun parse(hex: String): Rgb {
            require(hex.length == 7 && hex.startsWith("#")) { "expected #rrggbb, got '$hex'" }
            val packed = hex.substring(1).toIntOrNull(16)
            require(packed != null) { "expected #rrggbb, got '$hex'" }
            return Rgb(packed)
        }
    }
}
