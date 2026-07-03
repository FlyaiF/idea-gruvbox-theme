package io.github.flyaif.gruvbox.generator

enum class EffectType(val xmlValue: Int) {
    BOXED(0),
    UNDERLINE(1),
    WAVE(2),
    STRIKETHROUGH(3),
}

data class Attr(
    val foreground: Rgb? = null,
    val background: Rgb? = null,
    val bold: Boolean = false,
    val italic: Boolean = false,
    val effectColor: Rgb? = null,
    val effectType: EffectType? = null,
    val errorStripe: Rgb? = null,
)

data class EditorScheme(
    val name: String,
    val parentScheme: String,
    val colors: Map<String, Rgb>,
    val attributes: Map<String, Attr>,
)

/**
 * The Mapping (see CONTEXT.md): palette slots assigned to IDE attribute keys.
 * Comments cite the gruvbox.vim highlight group each choice follows; entries
 * marked "spirit" are IDEA-only concepts derived in the same style.
 */
fun editorSchemeFor(variant: Variant): EditorScheme = with(Palette) {
    val bg0 = variant.bg0
    val colors = linkedMapOf<String, Rgb>()
    val attrs = linkedMapOf<String, Attr>()

    fun color(key: String, value: Rgb) {
        colors[key] = value
    }

    fun attr(
        key: String,
        fg: Rgb? = null,
        bg: Rgb? = null,
        bold: Boolean = false,
        italic: Boolean = false,
        effect: Rgb? = null,
        effectType: EffectType? = null,
        stripe: Rgb? = null,
    ) {
        attrs[key] = Attr(fg, bg, bold, italic, effect, effectType, stripe)
    }

    // --- Editor chrome colors ------------------------------------------------
    color("CARET_COLOR", fg1)
    color("CARET_ROW_COLOR", bg1) // CursorLine -> bg1
    color("SELECTION_BACKGROUND", bg2) // Visual (vim inverts video; bg2 is the readable adaptation)
    color("LINE_NUMBERS_COLOR", bg4) // LineNr -> bg4
    color("LINE_NUMBER_ON_CARET_ROW_COLOR", fg3)
    color("GUTTER_BACKGROUND", bg0)
    color("CONSOLE_BACKGROUND_KEY", bg0)
    color("INDENT_GUIDE", bg1)
    color("SELECTED_INDENT_GUIDE", bg2)
    color("RIGHT_MARGIN_COLOR", bg1) // ColorColumn spirit
    color("TEARLINE_COLOR", bg1)
    color("METHOD_SEPARATORS_COLOR", bg2)
    color("SOFT_WRAP_SIGN_COLOR", gray)
    color("WHITESPACES", bg2) // NonText/SpecialKey -> bg2
    color("FOLDED_TEXT_BORDER_COLOR", bg2)
    color("DOCUMENTATION_COLOR", bg1)
    color("ANNOTATIONS_COLOR", fg4)
    color("ADDED_LINES_COLOR", neutralGreen) // GruvboxGreenSign
    color("MODIFIED_LINES_COLOR", neutralBlue) // GruvboxBlueSign? no: GruvboxAquaSign; blue reads clearer in gutter (spirit)
    color("DELETED_LINES_COLOR", neutralRed) // GruvboxRedSign
    color("WHITESPACES_MODIFIED_LINES_COLOR", fadedYellow)

    // --- Base text and syntax (vim base groups) ------------------------------
    attr("TEXT", fg = fg1, bg = bg0) // Normal -> fg1 on bg0
    attr("DEFAULT_KEYWORD", fg = brightRed) // Keyword/Statement -> GruvboxRed
    attr("DEFAULT_STRING", fg = brightGreen) // String -> green
    attr("DEFAULT_VALID_STRING_ESCAPE", fg = brightOrange) // Special -> GruvboxOrange
    attr("DEFAULT_INVALID_STRING_ESCAPE", fg = brightRed, effect = brightRed, effectType = EffectType.WAVE)
    attr("DEFAULT_NUMBER", fg = brightPurple) // Number -> GruvboxPurple
    attr("DEFAULT_CONSTANT", fg = brightPurple) // Constant -> GruvboxPurple
    attr("DEFAULT_PREDEFINED_SYMBOL", fg = brightAqua) // PreProc spirit
    attr("DEFAULT_LABEL", fg = brightRed) // Label -> GruvboxRed
    attr("DEFAULT_IDENTIFIER", fg = fg1)
    attr("DEFAULT_LOCAL_VARIABLE", fg = fg1) // vim leaves locals unhighlighted
    attr("DEFAULT_GLOBAL_VARIABLE", fg = brightBlue, italic = true) // Identifier spirit
    attr("DEFAULT_PARAMETER", fg = fg1)
    attr("DEFAULT_INSTANCE_FIELD", fg = brightBlue) // Identifier -> GruvboxBlue
    attr("DEFAULT_STATIC_FIELD", fg = brightBlue, italic = true)
    attr("DEFAULT_FUNCTION_DECLARATION", fg = brightGreen, bold = true) // Function -> GruvboxGreenBold
    attr("DEFAULT_FUNCTION_CALL", fg = brightGreen)
    attr("DEFAULT_INSTANCE_METHOD", fg = brightGreen, bold = true)
    attr("DEFAULT_STATIC_METHOD", fg = brightGreen, italic = true)
    attr("DEFAULT_CLASS_NAME", fg = brightYellow) // Type -> GruvboxYellow
    attr("DEFAULT_CLASS_REFERENCE", fg = brightYellow)
    attr("DEFAULT_INTERFACE_NAME", fg = brightYellow, italic = true)
    attr("DEFAULT_METADATA", fg = brightBlue) // javaAnnotation -> GruvboxBlue
    attr("DEFAULT_TAG", fg = brightBlue) // xmlTag -> GruvboxBlue
    attr("DEFAULT_ATTRIBUTE", fg = brightAqua) // xmlAttrib -> GruvboxAqua
    attr("DEFAULT_ENTITY", fg = brightOrange) // xmlEntity -> GruvboxOrange

    // Punctuation: javaParen -> GruvboxFg3, javaScriptBraces -> GruvboxFg1
    attr("DEFAULT_BRACES", fg = fg1)
    attr("DEFAULT_PARENTHS", fg = fg3)
    attr("DEFAULT_BRACKETS", fg = fg3)
    attr("DEFAULT_DOT", fg = fg1)
    attr("DEFAULT_COMMA", fg = fg1)
    attr("DEFAULT_SEMICOLON", fg = fg1)
    attr("DEFAULT_OPERATION_SIGN", fg = brightOrange) // javaOperator -> GruvboxOrange

    // Comments: Comment -> gray + italicize_comments (gruvbox default: on)
    attr("DEFAULT_LINE_COMMENT", fg = gray, italic = true)
    attr("DEFAULT_BLOCK_COMMENT", fg = gray, italic = true)
    attr("DEFAULT_DOC_COMMENT", fg = gray, italic = true)
    attr("DEFAULT_DOC_MARKUP", fg = fg2)
    attr("DEFAULT_DOC_COMMENT_TAG", fg = brightAqua) // javaDocTags -> GruvboxAqua
    attr("DEFAULT_DOC_COMMENT_TAG_VALUE", fg = fg3)

    // --- Inspections and effects ----------------------------------------------
    attr("ERRORS_ATTRIBUTES", effect = brightRed, effectType = EffectType.WAVE, stripe = brightRed)
    attr("WARNING_ATTRIBUTES", effect = brightYellow, effectType = EffectType.WAVE, stripe = neutralYellow)
    attr("INFO_ATTRIBUTES", effect = fg4, effectType = EffectType.WAVE, stripe = fg4)
    attr("DEPRECATED_ATTRIBUTES", effect = fg1, effectType = EffectType.STRIKETHROUGH)
    attr("MARKED_FOR_REMOVAL_ATTRIBUTES", effect = brightRed, effectType = EffectType.STRIKETHROUGH)
    attr("NOT_USED_ELEMENT_ATTRIBUTES", fg = bg4)
    attr("WRONG_REFERENCES_ATTRIBUTES", fg = brightRed)
    attr("TYPO", effect = brightBlue, effectType = EffectType.WAVE) // SpellBad -> undercurl blue
    attr("TODO_DEFAULT_ATTRIBUTES", fg = fg0, bold = true, italic = true, stripe = brightYellow) // Todo
    attr("HYPERLINK_ATTRIBUTES", fg = brightBlue, effect = brightBlue, effectType = EffectType.UNDERLINE) // Underlined
    attr("FOLLOWED_HYPERLINK_ATTRIBUTES", fg = brightPurple, effect = brightPurple, effectType = EffectType.UNDERLINE)
    attr("CTRL_CLICKABLE", fg = brightBlue, effect = brightBlue, effectType = EffectType.UNDERLINE)

    attr("MATCHED_BRACE_ATTRIBUTES", bg = bg3, bold = true) // MatchParen -> bg3 bold
    attr("UNMATCHED_BRACE_ATTRIBUTES", bg = neutralRed.blendOver(bg0, 0.4))
    attr("FOLDED_TEXT_ATTRIBUTES", fg = gray, bg = bg1, italic = true) // Folded

    // Search: yellow inverse; current match orange inverse (IncSearch)
    attr("SEARCH_RESULT_ATTRIBUTES", fg = fg0, bg = fadedYellow, stripe = neutralYellow)
    attr("WRITE_SEARCH_RESULT_ATTRIBUTES", fg = fg0, bg = fadedOrange, stripe = neutralOrange)
    attr("TEXT_SEARCH_RESULT_ATTRIBUTES", fg = bg0, bg = neutralYellow, stripe = neutralYellow)
    attr("IDENTIFIER_UNDER_CARET_ATTRIBUTES", bg = bg2, stripe = neutralAqua)
    attr("WRITE_IDENTIFIER_UNDER_CARET_ATTRIBUTES", bg = bg2, stripe = neutralOrange)

    // Debugger
    attr("BREAKPOINT_ATTRIBUTES", bg = neutralRed.blendOver(bg0, 0.3))
    attr("EXECUTIONPOINT_ATTRIBUTES", fg = fg0, bg = fadedBlue)
    attr("DEBUGGER_INLINED_VALUES", fg = fg4, italic = true)

    // Diff: DiffAdd/Delete/Change/Text hues, adapted from inverse video to dim backgrounds
    attr("DIFF_INSERTED", bg = neutralGreen.blendOver(bg0, 0.25), stripe = neutralGreen)
    attr("DIFF_DELETED", bg = neutralRed.blendOver(bg0, 0.25), stripe = neutralRed)
    attr("DIFF_MODIFIED", bg = neutralAqua.blendOver(bg0, 0.25), stripe = neutralAqua)
    attr("DIFF_CONFLICT", bg = neutralOrange.blendOver(bg0, 0.3), stripe = neutralOrange)

    // --- Console --------------------------------------------------------------
    attr("CONSOLE_NORMAL_OUTPUT", fg = fg1)
    attr("CONSOLE_SYSTEM_OUTPUT", fg = fg1)
    attr("CONSOLE_ERROR_OUTPUT", fg = brightRed)
    attr("CONSOLE_USER_INPUT", fg = brightGreen, italic = true)
    // ANSI 0-7: gruvbox terminal row (neutral); 8-15: bright row
    attr("CONSOLE_BLACK_OUTPUT", fg = bg0)
    attr("CONSOLE_RED_OUTPUT", fg = neutralRed)
    attr("CONSOLE_GREEN_OUTPUT", fg = neutralGreen)
    attr("CONSOLE_YELLOW_OUTPUT", fg = neutralYellow)
    attr("CONSOLE_BLUE_OUTPUT", fg = neutralBlue)
    attr("CONSOLE_MAGENTA_OUTPUT", fg = neutralPurple)
    attr("CONSOLE_CYAN_OUTPUT", fg = neutralAqua)
    attr("CONSOLE_GRAY_OUTPUT", fg = fg4)
    attr("CONSOLE_DARKGRAY_OUTPUT", fg = gray)
    attr("CONSOLE_RED_BRIGHT_OUTPUT", fg = brightRed)
    attr("CONSOLE_GREEN_BRIGHT_OUTPUT", fg = brightGreen)
    attr("CONSOLE_YELLOW_BRIGHT_OUTPUT", fg = brightYellow)
    attr("CONSOLE_BLUE_BRIGHT_OUTPUT", fg = brightBlue)
    attr("CONSOLE_MAGENTA_BRIGHT_OUTPUT", fg = brightPurple)
    attr("CONSOLE_CYAN_BRIGHT_OUTPUT", fg = brightAqua)
    attr("CONSOLE_WHITE_OUTPUT", fg = fg1)
    attr("LOG_ERROR_OUTPUT", fg = brightRed)
    attr("LOG_WARNING_OUTPUT", fg = brightYellow)

    // --- Java -------------------------------------------------------------
    attr("ANNOTATION_NAME_ATTRIBUTES", fg = brightBlue) // javaAnnotation -> GruvboxBlue
    attr("ANNOTATION_ATTRIBUTE_NAME_ATTRIBUTES", fg = fg2)
    attr("CLASS_NAME_ATTRIBUTES", fg = brightYellow) // Type -> GruvboxYellow
    attr("ANONYMOUS_CLASS_NAME_ATTRIBUTES", fg = brightYellow)
    attr("ABSTRACT_CLASS_NAME_ATTRIBUTES", fg = brightYellow)
    attr("INTERFACE_NAME_ATTRIBUTES", fg = brightYellow, italic = true)
    attr("ENUM_NAME_ATTRIBUTES", fg = brightYellow)
    attr("TYPE_PARAMETER_NAME_ATTRIBUTES", fg = brightAqua) // Structure -> GruvboxAqua
    attr("INSTANCE_FIELD_ATTRIBUTES", fg = brightBlue) // Identifier -> GruvboxBlue
    attr("STATIC_FIELD_ATTRIBUTES", fg = brightBlue, italic = true)
    attr("STATIC_FINAL_FIELD_ATTRIBUTES", fg = brightPurple, italic = true) // Constant -> GruvboxPurple
    attr("METHOD_DECLARATION_ATTRIBUTES", fg = brightGreen, bold = true) // Function -> GruvboxGreenBold
    attr("CONSTRUCTOR_DECLARATION_ATTRIBUTES", fg = brightGreen, bold = true)
    attr("METHOD_CALL_ATTRIBUTES", fg = brightGreen)
    attr("CONSTRUCTOR_CALL_ATTRIBUTES", fg = brightGreen)
    attr("STATIC_METHOD_ATTRIBUTES", fg = brightGreen, italic = true)

    // --- XML / HTML ---------------------------------------------------------
    attr("XML_TAG", fg = brightBlue) // xmlTag -> GruvboxBlue
    attr("XML_TAG_NAME", fg = brightBlue) // xmlTagName -> GruvboxBlue
    attr("XML_ATTRIBUTE_NAME", fg = brightAqua) // xmlAttrib -> GruvboxAqua
    attr("XML_ATTRIBUTE_VALUE", fg = brightGreen)
    attr("XML_TAG_DATA", fg = fg1)
    attr("XML_ENTITY_REFERENCE", fg = brightOrange) // xmlEntity -> GruvboxOrange
    attr("HTML_TAG", fg = brightBlue) // htmlTag -> GruvboxBlue
    attr("HTML_TAG_NAME", fg = brightAqua, bold = true) // htmlTagName -> GruvboxAquaBold
    attr("HTML_ATTRIBUTE_NAME", fg = brightAqua) // htmlArg -> GruvboxAqua
    attr("HTML_ATTRIBUTE_VALUE", fg = brightGreen)
    attr("HTML_ENTITY_REFERENCE", fg = brightOrange) // htmlSpecialChar -> GruvboxOrange

    // --- Data formats (keys aqua like xmlAttrib, since IDE strings stay green) ---
    attr("JSON.PROPERTY_KEY", fg = brightAqua)
    attr("JSON.KEYWORD", fg = brightPurple) // Boolean -> GruvboxPurple
    attr("YAML_SCALAR_KEY", fg = brightAqua)
    attr("YAML_SCALAR_STRING", fg = brightGreen)
    attr("YAML_SCALAR_DSTRING", fg = brightGreen)
    attr("YAML_ANCHOR", fg = brightPurple)
    attr("YAML_SIGN", fg = fg3)
    attr("PROPERTIES.KEY", fg = brightAqua)
    attr("PROPERTIES.VALUE", fg = fg1)
    attr("PROPERTIES.KEY_VALUE_SEPARATOR", fg = fg3)
    attr("PROPERTIES.VALID_STRING_ESCAPE", fg = brightOrange)
    attr("PROPERTIES.INVALID_STRING_ESCAPE", fg = brightRed)

    // --- JavaScript ---------------------------------------------------------
    attr("JS.REGEXP", fg = brightOrange) // Special -> GruvboxOrange
    attr("JS.GLOBAL_VARIABLE", fg = brightBlue) // javaScriptMember spirit

    EditorScheme(
        name = variant.displayName,
        parentScheme = "Darcula",
        colors = colors,
        attributes = attrs,
    )
}
