---
name: lld-report
description: Generates a full Low-Level Design analysis report for this Java parking lot project. Assesses SOLID principles, design pattern usage, class responsibilities, and suggests what to build next. Use when asked for a design review, LLD report, or architecture analysis.
---

# LLD Design Report Skill
# Usage: /lld-report

## Steps

1. Spawn `lld-analyzer` subagent to read all Java files across all layers
2. Produce the full report below using the analyzer's output

## Report Sections

### 1. Architecture — actual vs intended
Draw the actual dependency graph based on real import statements.
Flag any arrow that goes in the wrong direction vs the intended:
```
controller → service → repository → domain
              ↓
           adapter
```

### 2. SOLID Assessment
For each principle: ✅ Good / ⚠️ Partial / ❌ Violated + one real example from the code.

| Principle | Status | Example from this codebase |
|---|---|---|
| Single Responsibility | ? | ... |
| Open/Closed | ? | ... |
| Liskov Substitution | ? | ... |
| Interface Segregation | ? | ... |
| Dependency Inversion | ? | ... |

### 3. Design Pattern Audit
For each pattern in use (Strategy, Repository, Adapter):
- Is it implemented correctly?
- What would break if you added a new payment gateway / vehicle type / floor?

### 4. Critical Code Smells
Only things that affect correctness or maintainability:
- NPE time bombs
- Missing abstractions (repeated logic that should be a method or class)
- Primitive obsession (UUID/double used where a value object belongs)

### 5. What to build next (prioritized)
Top 3-5 improvements, each with: what to add, which layer, why it matters for the LLD.
