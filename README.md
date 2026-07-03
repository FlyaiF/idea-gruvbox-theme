# Gruvbox Revival

Retro groove colors for IntelliJ-based IDEs, faithful to the original
[gruvbox](https://github.com/morhetz/gruvbox) Vim theme — built Java-first
and for the New UI.

Why another gruvbox plugin? The established ones lean Go-oriented and have
gone stale. This one is tuned for the code an IntelliJ IDEA user actually
looks at all day — Java, Spring config, Gradle scripts, SQL — and targets
the New UI exclusively (IDE 2024.2+).

## Variants

| Variant | Background |
| --- | --- |
| Gruvbox Dark Hard | `#1d2021` |
| Gruvbox Dark Medium | `#282828` |
| Gruvbox Dark Soft | `#32302f` |

The three Contrast Levels differ only in background shades; all foreground
and accent colors are shared, exactly as in the original Vim theme.

## Install

Until the first Marketplace release: download the zip from a CI build (or run
`./gradlew buildPlugin` and take `build/distributions/*.zip`), then
**Settings → Plugins → ⚙ → Install Plugin from Disk…** and pick the zip.
Choose the variant under **Settings → Appearance & Behavior → Appearance → Theme**.

## Development

The six theme files under `src/main/resources/themes/` are **generated —
do not edit them by hand.** The single source of truth is the palette and
mapping in `generator/src/main/kotlin/` (see
[docs/adr/0001](docs/adr/0001-generated-committed-theme-files.md)).

```sh
./gradlew :generator:test   # mapping + renderer tests
./gradlew generateThemes    # regenerate the committed theme files
./gradlew runIde            # try the theme in a sandbox IDE
./gradlew buildPlugin       # produce the installable zip
```

CI regenerates the themes and fails if the committed files have drifted from
the palette definition.

### Releasing

1. Set the new version in `gradle.properties` and describe the changes under
   `## [Unreleased]` in `CHANGELOG.md`; commit.
2. Tag and push: `git tag v0.2.0 && git push origin main v0.2.0`.
3. The Release workflow rebuilds, attaches the zip to a GitHub release, and —
   when a Marketplace `PUBLISH_TOKEN` repository secret is configured —
   publishes to the JetBrains Marketplace. (The very first Marketplace upload
   must be done manually through plugins.jetbrains.com.)
4. Afterwards run `./gradlew patchChangelog` to move the Unreleased notes
   under the released version heading, and commit.

### Mapping philosophy

Every color assignment gruvbox.vim makes is ported verbatim (keywords red,
strings green, types yellow, annotations blue, javadoc tags aqua…). IDEA
attributes that Vim has no concept of — fields, parameters, semantic
highlighting — are derived in the same spirit, with the reasoning recorded
as comments in `EditorScheme.kt`.

Java, XML, properties, YAML, JSON, and JavaScript have explicit
language-specific attributes. Gradle scripts, SQL, and everything else
inherit the gruvbox `DEFAULT_*` attributes (keywords, strings, numbers…),
with Darcula as the fallback for anything unmapped — dark and readable,
never broken.

## Credits

- [morhetz/gruvbox](https://github.com/morhetz/gruvbox) — the original palette and mappings (MIT/X11)

## License

[MIT](LICENSE)
