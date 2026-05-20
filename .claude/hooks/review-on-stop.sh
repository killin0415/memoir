#!/usr/bin/env bash
# Stop hook: if Claude modified files this turn, block stop once and ask Claude
# to spawn a code-reviewer agent on those files. Anti-loops via `stop_hook_active`.
#
# State source: .claude/.state/modified-<session_id>.txt (written by lint-on-write.sh)
#
# Output contract: a JSON object on stdout
#   {"decision":"block","reason":"..."}  -> Claude continues with `reason` as guidance
#   (no output, exit 0)                   -> Claude stops normally

set -uo pipefail

input="$(cat)"
session_id="$(printf '%s' "$input" | python3 -c \
  "import json,sys; print(json.load(sys.stdin).get('session_id','unknown'))")"
stop_hook_active="$(printf '%s' "$input" | python3 -c \
  "import json,sys; print(json.load(sys.stdin).get('stop_hook_active',False))")"

# Anti-loop: if we're already inside a Stop chain (Claude is responding to our
# own previous block), allow stop to proceed.
if [[ "$stop_hook_active" == "True" ]]; then
  exit 0
fi

repo_root="${CLAUDE_PROJECT_DIR:-$(cd "$(dirname "$0")/../.." && pwd)}"
modified_file="$repo_root/.claude/.state/modified-$session_id.txt"

# Nothing modified this turn -> nothing to review
[[ ! -s "$modified_file" ]] && exit 0

files="$(sort -u "$modified_file" | head -30)"
rm -f "$modified_file"

FILES="$files" python3 <<'PY'
import json, os
files = os.environ.get('FILES', '').strip()
reason = (
    "本 turn 修改了下列檔案,在 stop 前 spawn 一個 Agent (subagent_type='general-purpose') "
    "做 code review。\n"
    "Review 重點:bug、安全性問題、code smell、與 CLAUDE.md 規範的偏離、無謂的抽象 / 過度設計。\n"
    "回應限 300 字以內,findings 用 bullet,引用 `file:line` 格式。\n"
    "Review 完把重點總結給 user 再 stop(不用重貼整段 agent 輸出)。\n\n"
    "Files:\n" + files
)
print(json.dumps({"decision": "block", "reason": reason}))
PY
