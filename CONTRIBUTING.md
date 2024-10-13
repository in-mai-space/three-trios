## OOD Homework

To keep the code stable, the `main` branch is protected. This means:
- No direct commits are allowed to `main`.
- All changes must go through a pull request (PR) and be reviewed before merging.
- PRs must pass all required checks (tests, code formatting) before merging.

[System Design Figjam](https://www.figma.com/board/y7X5LMazWNhXcIhupD9NuT/OOD-System-Design?node-id=0-1&t=xOpvfAxoP355Bof8-1)

### 1. Fork the Repository

1. Fork the repository on GitHub.
2. Clone your fork locally:
   ```bash
   git clone https://github.com/your-username/ood.git
   cd ood
   ```
3. Create a new branch:
   ```bash
   git checkout -b branch-name
   ```

### 2. Create a Pull Request (PR)

1. Push your branch:
   ```bash
   git push origin branch-name
   ```
2. Open a PR from your branch to `main` in the original repo:
   - Go to the **Pull Requests** tab.
   - Click **New Pull Request** and submit.

### 3. Review Process

- PRs must be reviewed before merging.
- Include a clear description of changes and any related issues.
- Add tests for your changes if necessary.
- Once approved, the PR will be merged.

### 4. Resolving Merge Conflicts

If you encounter merge conflicts with `main`:
1. Pull the latest changes from `main`:
   ```bash
   git checkout main
   git pull origin main
   git checkout branch-name
   git merge main
   ```
2. Resolve conflicts, then push your changes:
   ```bash
   git add .
   git commit -m "Resolved merge conflicts"
   git push origin branch-name
   ```

## Opening an Issue

To report a bug:
1. Go to the **Issues** tab.
2. Click **New Issue** and provide:
   - A descriptive title.
   - Steps to reproduce (if applicable).
   - Expected vs actual behavior.
   - Any relevant screenshots, logs, or errors.

---

# Commit Message Guidelines

### 1. Commit Types

- **feat**: New feature.
- **fix**: Bug fix.
- **docs**: Documentation changes.
- **style**: Code formatting (no functional changes).
- **refactor**: Code restructuring without changing functionality.
- **test**: Adding or modifying tests.

### 2. Short Description
Keep descriptions under 72 characters, explaining **what** the commit does.

### 3. Issue Reference
If the commit relates to an issue:
```
fix: resolve data mutation bug

Fixes issue where data was mutated unintentionally. Closes #1.
```

### Additional Guidelines
- **Small Commits**: Focus on one change per commit.
- **Atomic Commits**: Avoid mixing unrelated changes in a single commit.

---

# Writing Tests

1. Test edge cases (e.g., null, empty, max/min values).
2. Test exceptions (use `IllegalArgumentException` over `NullPointerException`).
3. Write unit tests first, then integration tests.
4. Write tests for found bugs and test exhaustively around them.
5. Do not delete failing tests; add `// FIXME` next to them.
6. Write test helpers for repetitive code.
7. Use descriptive test method names.
8. Use test suites to run multiple test classes.

---

# Writing Code

- **Keep methods/classes small**: Follow the Single Responsibility Principle.
- **Self-explanatory code**: Use meaningful names for variables, methods, and classes.
- **Consistent naming**: Stick to a unified naming convention.
- **Refactor regularly**: Eliminate redundancy and improve readability after writing code and tests.
- **Write tests as you code**: Ensure your code is testable and well-covered.
- **Handle exceptions properly**: Gracefully handle errors and edge cases.
- **Avoid redundancy**: Abstract common functionality to avoid repeating code.
- **Use design patterns**: Apply patterns like Factory, Builder, etc.
- **Document as needed**: Clarify complex logic with concise comments or documentation.
- **Avoid deep nesting**: Refactor nested loops or conditionals for clarity.
