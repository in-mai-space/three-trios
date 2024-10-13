## OOD Homework

To keep the code stable, the main branch is protected. This means:
- No direct commits are allowed to the main branch.
- All changes must go through a pull request (PR) and be reviewed before they can be merged into main.
- Pull requests must pass all required checks (e.g., tests, code formatting) before they can be merged.

[System Design Figjam
](https://www.figma.com/board/y7X5LMazWNhXcIhupD9NuT/OOD-System-Design?node-id=0-1&t=xOpvfAxoP355Bof8-1)

### 1. Fork the Repository

1. **Fork** the repository to your GitHub account by clicking the "Fork" button.
2. Clone your forked repository locally:
   ```bash
   git clone https://github.com/your-username/ood.git
   cd ood
   ```
3. Create a new branch for your changes:
   ```bash
   git checkout -b branch-name
   ```

### 2. Create a Pull Request (PR)

When your changes are ready:

1. Push your branch to your fork:
   ```bash
   git push origin my-contribution
   ```
2. Go to the original repository and open a **pull request** (PR) from your branch to the `main` branch:
   - Navigate to the **Pull Requests** tab.
   - Click **New Pull Request**.
   - Select your branch and submit the pull request.

### 3. Review Process

- All pull requests require a review before they can be merged.
- Make sure your pull request contains:
  - A clear description of your changes.
  - Links to related issues or discussions (if applicable).
  - Unit and integration tests, if necessary, to verify your changes.
- Once the review is complete and changes are approved, code from pull request will be merged.

### 4. Handling Issues or Conflicts

If you encounter any issues with your pull request, such as **Merge conflicts** with the `main` branch:

1. **Resolving Merge Conflicts**:
   - Pull the latest changes from the `main` branch:
     ```bash
     git fetch origin
     git checkout main
     git pull origin main
     git checkout branch-name
     git merge main
     ```
   - Resolve conflicts manually in your code editor.
   - Commit the changes and push them to your branch:
     ```bash
     git add .
     git commit -m "Resolved merge conflicts"
     git push origin branch-name
     ```

## Opening an Issue

If you have found a bug, open an issue by following these steps:

1. Go to the **Issues** tab.
2. Click **New Issue**.
3. Provide as much detail as possible:
   - A descriptive title.
   - Clear steps to reproduce the problem (if applicable).
   - The expected behavior and what actually happens.
   - Any related screenshots, logs, or error messages.

---

# Commit Message Convention

To ensure that all commit messages are consistent and easy to understand, please follow these conventions when writing commit messages:

#### 1. Commit Types

- **feat**: A new feature or functionality.
- **fix**: A bug fix or correction.
- **docs**: Documentation updates or changes.
- **style**: Code formatting (no functional changes, such as removing whitespace or semicolons).
- **refactor**: Code restructuring or refactoring without changing any functionality.
- **test**: Adding or modifying tests.

#### 2. Short Description
The description should be concise, ideally less than 72 characters, and describe **what** the commit does.

#### 3. Issue Reference (Optional)
If the commit is related to an issue or pull request, reference it at the end of the message:
```
fix: mutation of data bug

Fixes issue where data is mutated when it's not supposed to.
Closes #1.
```
### Additional Commit Guidelines
- **Small Commits**: Aim for small, focused commits. Each commit should represent a single change or fix.
- **Atomic Commits**: Avoid mixing unrelated changes in a single commit.

---
# Writing Tests
1. Test for edge cases and boundary conditions (e.g., null, empty inputs, max/min values).
2. Test all the exceptions of inputs (use IllegalArgumentException instead of NullPointerException)
3. Write unit tests first, then integration test
4. Write tests when a bug is found, test exhaustively near bugs
5. Do not delete the test if it is failing, just add a // FIXME next to it
6. If test is repetitive or difficult to understand, write helpers for test
7. Use descriptive test method names
8. Write test suites so we can run multiple test classes at a time
