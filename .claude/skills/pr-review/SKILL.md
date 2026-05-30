---
name: pr-review
description: Reviews a GitHub pull request for a Java LLD project. Spawns pr-explorer and lld-analyzer agents in parallel, then produces a simple report for both the PR reader and the PR author. Use when asked to review a PR, check a pull request, or analyze code changes.
argument-hint: OWNER/REPO#PR_NUMBER
---

# PR Review Skill — Java LLD
# Usage: /pr-review OWNER/REPO#PR_NUMBER

## Steps

1. Parse argument → extract owner, repo, PR number
2. **Spawn two agents IN PARALLEL** (do not wait for one before starting the other):
   - `pr-explorer` → fetches PR metadata, all diffs, existing comments
   - `lld-analyzer` → reads all Java files in the repo, maps responsibilities and imports
3. Wait for both agents to complete, then combine their output
4. Run the Java LLD checklist below against the combined data
5. Produce the report in the format below
6. Ask for approval before posting anything to GitHub

## Java LLD Checklist (run against the diffs)

**Critical — flag these as blockers:**
- `Optional.orElse(null).method()` → NPE crash when slot/entity not found
- Controller directly calling a repository (skips service layer)
- Domain class containing business logic (calculation, state machine)
- `instanceof` check used instead of polymorphism in a Strategy pattern

**Should fix — flag but not blockers:**
- Missing null/empty check at controller entry points
- `System.out.println` in service or controller (use a logger)
- Empty catch block or swallowed exception
- `ConcurrentHashMap` mutated outside `computeIfAbsent` / `putIfAbsent`

**Learning note — explain but don't block:**
- Primitive obsession (using raw `double` for money instead of a value object)
- Single Responsibility violation (one class doing two unrelated things)
- Missing interface where one is needed for testability

---

## Report Format

Write the report in plain English. No jargon. Two sections — one for the reader, one for the author.

```
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
PR #[number]: [title]
Verdict: ✅ SAFE TO MERGE  /  ❌ NEEDS FIXES FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

## FOR THE REVIEWER (what changed and is it safe?)
[2-3 plain sentences: what this PR does, which layer it touches, whether the design is sound]

### What was changed
- [file]: [one line — what was added or fixed]

### Red flags (things that could break the system)
[If none: "None found — code looks safe to merge"]
[If any: bullet per issue, plain English, why it matters]

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

## FOR THE AUTHOR (what you need to fix or learn)
### Must fix (blocking)
[numbered list — each item: what to change + corrected Java code snippet]

### Good to improve (not blocking)
[bullet list — learning notes about LLD design]

### What you did well
[1-2 things done correctly — always include this]
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
```
