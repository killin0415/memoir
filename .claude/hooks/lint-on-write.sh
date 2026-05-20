#!/usr/bin/env bash
# PostToolUse hook for Edit/Write/MultiEdit.
#
# 1. Lints the modified file with the right tool for its extension.
#    Linter map:
#      .kt  / .kts          -> detekt        (brew install detekt)
#      .py                   -> ruff          (brew install ruff   or   uv add --dev ruff)
#      .ts / .tsx / .js …    -> nearest node_modules/.bin/eslint
#    Missing tool -> skip silently (don't nag).
#
# 2. Records the path in .claude/.state/modified-<session_id>.txt so the
#    Stop hook (review-on-stop.sh) can ask Claude to spawn a reviewer agent.
#
# Exit codes:
#   0 — clean (or linter not installed)
#   2 — lint found issues; stderr is fed back to Claude

set -uo pipefail

input="$(cat)"

file_path="$(printf '%s' "$input" | python3 -c \
  "import json,sys; d=json.load(sys.stdin); print(d.get('tool_input',{}).get('file_path',''))")"
session_id="$(printf '%s' "$input" | python3 -c \
  "import json,sys; print(json.load(sys.stdin).get('session_id','unknown'))")"

[[ -z "$file_path" || ! -f "$file_path" ]] && exit 0

repo_root="${CLAUDE_PROJECT_DIR:-$(cd "$(dirname "$0")/../.." && pwd)}"
rel_path="${file_path#$repo_root/}"

# --- 1. Skip non-code / generated paths (no track, no lint) ---
case "$rel_path" in
  .claude/*|*/build/*|*/.gradle/*|*/node_modules/*|*/.venv/*|*/dist/*|*/out/*)
    exit 0 ;;
esac

# --- 2. Track for Stop hook ---
state_dir="$repo_root/.claude/.state"
mkdir -p "$state_dir"
printf '%s\n' "$rel_path" >> "$state_dir/modified-$session_id.txt"

# --- 3. Lint ---
lint_out=""
lint_rc=0

case "$file_path" in
  *.kt|*.kts)
    if command -v detekt &>/dev/null; then
      lint_out="$(detekt --input "$file_path" 2>&1)" || lint_rc=$?
    fi
    ;;
  *.py)
    if command -v ruff &>/dev/null; then
      lint_out="$(ruff check "$file_path" 2>&1)" || lint_rc=$?
    fi
    ;;
  *.ts|*.tsx|*.js|*.jsx|*.mjs|*.cjs)
    dir="$(dirname "$file_path")"
    while [[ "$dir" != "/" && "$dir" != "$repo_root" ]]; do
      if [[ -x "$dir/node_modules/.bin/eslint" ]]; then
        lint_out="$(cd "$dir" && ./node_modules/.bin/eslint "$file_path" 2>&1)" || lint_rc=$?
        break
      fi
      dir="$(dirname "$dir")"
    done
    ;;
esac

if [[ $lint_rc -ne 0 ]]; then
  {
    printf 'Lint failed for %s:\n' "$rel_path"
    printf '%s\n' "$lint_out"
  } >&2
  exit 2
fi

exit 0
