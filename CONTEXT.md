# Gruvbox Theme for JetBrains IDEs

A JetBrains Marketplace plugin delivering the Gruvbox color palette as both IDE chrome styling and editor syntax colors. Exists because the established Gruvbox plugins are Go-oriented and stale; this one is Java-first (IntelliJ IDEA) and actively maintained.

## Language

**Plugin**:
The installable Marketplace artifact. Bundles one or more UI Themes and their Editor Color Schemes.

**UI Theme**:
A `.theme.json` file that styles the IDE chrome — menus, toolbars, tool windows, panels.
_Avoid_: "theme" on its own (ambiguous with Editor Color Scheme)

**Editor Color Scheme**:
An XML (`.icls`-format) file that styles editor text — syntax highlighting, diff colors, console colors. Referenced by a UI Theme.
_Avoid_: color theme, syntax theme

**Variant**:
One shippable UI Theme + Editor Color Scheme pair, identified by a Contrast Level. Shipped variants: Dark Hard, Dark Medium, Dark Soft. (Light mode is out of scope for now.)
_Avoid_: flavor, edition

**Contrast Level**:
Hard, Medium, or Soft — Gruvbox's background-darkness axis. Only background shades differ between levels; foreground and accent colors are shared.
_Avoid_: intensity, brightness

**Palette**:
The canonical set of named Gruvbox colors (e.g. `bg0_hard`, `fg1`, `bright_orange`) from which every Variant's files are derived. The single source of truth for color values.
_Avoid_: color list, swatches

**Mapping**:
The assignment of Palette colors to IDE attribute keys. Vim-faithful: follows gruvbox.vim wherever it defines a color; IDEA-specific attributes are derived in its spirit.
_Avoid_: color scheme (ambiguous), styling rules
