package io.github.flyaif.gruvbox.generator

import java.io.File

fun main(args: Array<String>) {
    val outDir = File(requireNotNull(args.firstOrNull()) { "usage: generator <output-dir>" })
    outDir.mkdirs()
    for (variant in allVariants) {
        File(outDir, "${variant.fileStem}.xml").writeText(renderEditorScheme(editorSchemeFor(variant)))
        File(outDir, "${variant.fileStem}.theme.json").writeText(renderUiTheme(uiThemeFor(variant)))
    }
    println("Wrote ${allVariants.size * 2} theme files to $outDir")
}
