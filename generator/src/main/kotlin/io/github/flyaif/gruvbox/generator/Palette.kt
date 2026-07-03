package io.github.flyaif.gruvbox.generator

/**
 * The canonical gruvbox dark palette, verbatim from morhetz/gruvbox
 * (colors/gruvbox.vim). The single source of truth for every color the
 * plugin ships.
 */
object Palette {
    val bg0Hard = Rgb.parse("#1d2021")
    val bg0 = Rgb.parse("#282828")
    val bg0Soft = Rgb.parse("#32302f")
    val bg1 = Rgb.parse("#3c3836")
    val bg2 = Rgb.parse("#504945")
    val bg3 = Rgb.parse("#665c54")
    val bg4 = Rgb.parse("#7c6f64")

    val gray = Rgb.parse("#928374")

    val fg0 = Rgb.parse("#fbf1c7")
    val fg1 = Rgb.parse("#ebdbb2")
    val fg2 = Rgb.parse("#d5c4a1")
    val fg3 = Rgb.parse("#bdae93")
    val fg4 = Rgb.parse("#a89984")

    val brightRed = Rgb.parse("#fb4934")
    val brightGreen = Rgb.parse("#b8bb26")
    val brightYellow = Rgb.parse("#fabd2f")
    val brightBlue = Rgb.parse("#83a598")
    val brightPurple = Rgb.parse("#d3869b")
    val brightAqua = Rgb.parse("#8ec07c")
    val brightOrange = Rgb.parse("#fe8019")

    val neutralRed = Rgb.parse("#cc241d")
    val neutralGreen = Rgb.parse("#98971a")
    val neutralYellow = Rgb.parse("#d79921")
    val neutralBlue = Rgb.parse("#458588")
    val neutralPurple = Rgb.parse("#b16286")
    val neutralAqua = Rgb.parse("#689d6a")
    val neutralOrange = Rgb.parse("#d65d0e")

    val fadedRed = Rgb.parse("#9d0006")
    val fadedGreen = Rgb.parse("#79740e")
    val fadedYellow = Rgb.parse("#b57614")
    val fadedBlue = Rgb.parse("#076678")
    val fadedPurple = Rgb.parse("#8f3f71")
    val fadedAqua = Rgb.parse("#427b58")
    val fadedOrange = Rgb.parse("#af3a03")
}

enum class Contrast(val id: String, val label: String, val bg0: Rgb) {
    HARD("hard", "Hard", Palette.bg0Hard),
    MEDIUM("medium", "Medium", Palette.bg0),
    SOFT("soft", "Soft", Palette.bg0Soft),
}

data class Variant(val contrast: Contrast) {
    val displayName: String = "Gruvbox Dark ${contrast.label}"
    val fileStem: String = "gruvbox-dark-${contrast.id}"
    val bg0: Rgb = contrast.bg0
}

val allVariants: List<Variant> = Contrast.entries.map(::Variant)
