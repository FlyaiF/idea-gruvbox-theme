# 0001. Theme files are generated, and the output is committed

Date: 2026-07-03

## Status

Accepted

## Context

The plugin ships three Variants, each a UI Theme (`.theme.json`) plus an
Editor Color Scheme (`.icls` XML) — six files that differ only in a handful
of background values. Hand-maintaining six files invites drift between
variants (the classic bug in multi-variant theme repos). The alternatives:

1. Hand-write all six files.
2. Generate them at build time (files never in git).
3. Generate them with a manually-run task and commit the output.

## Decision

Option 3. A Kotlin generator (`generator/`) holds the palette and the
palette→attribute mapping as compiler-checked code; `./gradlew generateThemes`
writes the six files into `src/main/resources/themes/`, and those files are
committed. CI regenerates and fails on any diff, so the committed output
cannot drift from the definition.

## Consequences

- One color tweak lands in all three variants automatically; variant drift
  is structurally impossible.
- Generated output stays diffable in review, and the plugin build remains
  plain resource packaging with no generator dependency — theme live-reload
  during development works on real files.
- Anyone editing `src/main/resources/themes/` by hand will have their change
  reverted by the next generation run; the README warns about this, CI
  enforces it.
