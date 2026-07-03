package io.github.flyaif.gruvbox.generator

/** Renders an [EditorScheme] in the .icls XML format IntelliJ reads. */
fun renderEditorScheme(scheme: EditorScheme): String = buildString {
    appendLine("""<scheme name="${xmlEscape(scheme.name)}" version="142" parent_scheme="${xmlEscape(scheme.parentScheme)}">""")
    appendLine("""  <metaInfo>""")
    appendLine("""    <property name="ide">idea</property>""")
    appendLine("""  </metaInfo>""")

    appendLine("""  <colors>""")
    for ((key, value) in scheme.colors) {
        appendLine("""    <option name="$key" value="${value.plainHex}" />""")
    }
    appendLine("""  </colors>""")

    appendLine("""  <attributes>""")
    for ((key, attr) in scheme.attributes) {
        appendLine("""    <option name="$key">""")
        appendLine("""      <value>""")
        attr.foreground?.let { appendLine("""        <option name="FOREGROUND" value="${it.plainHex}" />""") }
        attr.background?.let { appendLine("""        <option name="BACKGROUND" value="${it.plainHex}" />""") }
        fontType(attr).takeIf { it != 0 }?.let { appendLine("""        <option name="FONT_TYPE" value="$it" />""") }
        attr.effectColor?.let { appendLine("""        <option name="EFFECT_COLOR" value="${it.plainHex}" />""") }
        attr.effectType?.let { appendLine("""        <option name="EFFECT_TYPE" value="${it.xmlValue}" />""") }
        attr.errorStripe?.let { appendLine("""        <option name="ERROR_STRIPE_COLOR" value="${it.plainHex}" />""") }
        appendLine("""      </value>""")
        appendLine("""    </option>""")
    }
    appendLine("""  </attributes>""")
    append("""</scheme>""")
    appendLine()
}

private fun fontType(attr: Attr): Int = (if (attr.bold) 1 else 0) + (if (attr.italic) 2 else 0)

private fun xmlEscape(s: String): String =
    s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;")

/** Renders a [UiTheme] in the .theme.json format IntelliJ reads. */
fun renderUiTheme(theme: UiTheme): String {
    val root = linkedMapOf<String, Any>(
        "name" to theme.name,
        "dark" to theme.dark,
        "author" to theme.author,
        "editorScheme" to theme.editorSchemePath,
        "colors" to theme.colors,
        "ui" to theme.ui,
        "icons" to theme.icons,
    )
    return buildString {
        appendJson(root, 0)
        appendLine()
    }
}

private fun StringBuilder.appendJson(value: Any?, indent: Int) {
    when (value) {
        null -> append("null")
        is String -> append('"').append(jsonEscape(value)).append('"')
        is Boolean, is Int, is Long, is Double -> append(value.toString())
        is Map<*, *> -> {
            if (value.isEmpty()) {
                append("{}")
                return
            }
            appendLine("{")
            val pad = "  ".repeat(indent + 1)
            val entries = value.entries.toList()
            entries.forEachIndexed { i, (k, v) ->
                append(pad).append('"').append(jsonEscape(k.toString())).append("\": ")
                appendJson(v, indent + 1)
                if (i < entries.lastIndex) append(',')
                appendLine()
            }
            append("  ".repeat(indent)).append('}')
        }
        else -> error("cannot render ${value::class} as JSON")
    }
}

private fun jsonEscape(s: String): String = buildString {
    for (c in s) {
        when (c) {
            '"' -> append("\\\"")
            '\\' -> append("\\\\")
            '\n' -> append("\\n")
            else -> if (c < ' ') append("\\u%04x".format(c.code)) else append(c)
        }
    }
}
