# judo-sdk-common — module agent doctrine

## Module purpose

`judo-sdk-common` is the hand-written compile target that JUDO's *generated* SDK
code is written against: the generators (`judo-tatami-base/judo-tatami-asm2sdk`,
the `judo-psm-generator-sdk-core` template set) emit Java that `extends`
`AbstractSdkDao`, `implements` `Identifiable` / `MapHolder` / `Enumeration` /
`NamedElement`, and builds JQL predicate strings out of the `…sdk.query` filter
enums. What is actually "common" here is therefore not utilities — it is the
**frozen API contract between code generators and the runtime**: the entity
identity model, the typed-object ⇄ `Map<String, Object>` payload bridge into
`judo-dao-api`, the ASM-metamodel lookup path (`AsmModel`/`AsmUtils` →
`getEClass(fqName)`), and the type-safe filter DSL. Nothing in this module
generates or transforms anything; it only defines what generated code may call.

Consumers pin it as `hu.blackbelt.judo:judo-sdk-common` — `judo-tatami-base`,
`judo-tatami`, `judo-runtime-core`, `tools/judo-spring-maven-plugin`, the
`judo-psm-generator-sdk-*` templates, and `judo-jsl-springboot-starter`.
Because the whole package tree ships as an OSGi export
(`hu.blackbelt.judo.sdk*`), **any signature change here breaks every generated
SDK project downstream** — treat public API edits as an ecosystem-wide release
action, not a local edit.

Artifact: `hu.blackbelt.judo:judo-sdk-common`, packaging `bundle` (Felix
`maven-bundle-plugin`), version `${revision}` (current `1.0.4-SNAPSHOT`),
Java 21, licensed EPL-2.0. Repository: `BlackBeltTechnology/judo-sdk-common`.

## Reactor map

`pom.xml` declares **no `<modules>`** — `judo-sdk-common` is a single-module
(non-aggregator) reactor. Nothing to descend into.

<modules>
  (none — single-module OSGi bundle reactor)
</modules>

What the POM contributes instead of submodules:

| POM contribution | Effect |
|---|---|
| `maven-bundle-plugin` (`extensions=true`) | Packages as OSGi bundle. `Export-Package: hu.blackbelt.judo.sdk*;version=${project.version}`; `Import-Package` prepends `${osgi-default-import}` (OSGi framework `[1.8,2.0)`, `javax.annotation` `[1.0,2)`, Gson `[2.1,3)`, SLF4J `[1.6,3)`, `!lombok`) then `*`. |
| `flatten-maven-plugin` 1.3.0 | `resolveCiFriendliesOnly` at `process-resources`, so `${revision}` resolves into the deployed POM; cleaned at `clean`. |
| `lombok-maven-plugin` | `delombok` at `generate-sources` into `target/delombok` (`addOutputDirectory=false` — feeds JavaDoc, not compilation). |
| `jacoco-maven-plugin` 0.8.12 | `prepare-agent` into `${jacoco.agent}`, consumed by the Surefire `argLine`; `report` bound by default. |
| `maven-javadoc-plugin` 3.4.1 | Attaches `javadoc` jar; registers EMF custom tags `@model`, `@generated`, `@ordered`, `@param`. |
| `maven-surefire-plugin` 3.5.1 | `trimStackTrace=false`; points `logback.configurationFile` at `${logback-test-config}`; supplies the `--add-opens` JVM args below. |
| `dependencyManagement` import | `fr.jmini.ecentral:eclipse-platform-dependencies:4.18` (pom import) governs the EMF/Eclipse versions. |

## Layout

```
judo-sdk-common/
├── src/main/java/hu/blackbelt/judo/sdk/   # Production source code
│   ├── query/                              # Type-safe query filter framework
│   └── *.java                              # Core interfaces and base classes
├── .github/workflows/                      # GitHub Actions CI/CD pipelines
├── openspec/                               # OpenSpec configuration
├── pom.xml                                 # Maven build configuration (${revision})
└── logback-test.xml                        # Test logging configuration
```

Per-file detail lives in the directory `AGENTS.md` under
`src/main/java/hu/blackbelt/judo/sdk/`. The two logical layers are the **core
layer** (`hu.blackbelt.judo.sdk` — identity, payload bridge, DAO superclass,
enumeration/named-element contracts) and the **query layer**
(`hu.blackbelt.judo.sdk.query` — `Filter`/`Operation` plus String, Number,
Boolean, Date, Time, Timestamp and Enumeration filters).

## What this module provides

1. Base classes and interfaces that generated JUDO SDK source code depends on at runtime
2. The entity identity model (`Identifiable`), map-based payload conversion (`MapHolder`, `SdkUtil`), and the enumeration contract (`Enumeration`)
3. `AbstractSdkDao` — the abstract superclass for all generated SDK DAOs, wiring the DAO layer, ASM metamodel, and payload validation
4. A type-safe query filter framework (`query` package) for constructing JQL-like predicates across String, Number, Boolean, Date, Time, Timestamp, and Enumeration types
5. OSGi bundle packaging exporting `hu.blackbelt.judo.sdk*`

## Code Instructions

1. First think through the problem, read the codebase for relevant files.
2. Before you make any major changes, check in with me and I will verify the plan.
3. Please every step of the way just give me a high level explanation of what changes you made.
4. Make every task and code change you do as simple as possible. We want to avoid making any massive or complex changes. Every change should impact as little code as possible. Everything is about simplicity.
5. Maintain a documentation file that describes how the architecture of the app works inside and out.
6. Never speculate about code you have not opened. If the user references a specific file, you MUST read the file before answering. Make sure to investigate and read relevant files BEFORE answering questions about the codebase. Never make any claims about code before investigating unless you are certain of the correct answer - give grounded and hallucination-free answers.
7. For implementation use TDD (Test-Driven Development): write or update tests first to define the expected behaviour, verify they fail, then write the minimal implementation to make them pass.
8. Use DRY (Don't Repeat Yourself): extract reusable logic into separate classes, utilities, or components. If the same pattern appears in multiple places, refactor it into a shared helper.

## Build commands

Maven Wrapper is available (`./mvnw`). Never run a repo-root `mvn` across the
monorepo — build this module through its own wrapper or via `./project.py`.

```bash
# Run tests
mvn clean test

# Full build and install to local repository
mvn clean install

# Build with a specific version (CI usage)
./mvnw -B -Drevision=<version> clean install

# Generate code coverage report
mvn clean test jacoco:report

# Run SonarQube analysis
./mvnw sonar:sonar -Dsonar.projectKey=judo-sdk-common -Dsonar.host.url=https://sonar.judo.technology

# Generate JavaDoc
mvn javadoc:jar

# Update EPL-2.0 license headers on source files
mvn license:update-file-header -Pupdate-source-code-license

# Deploy to internal Nexus
./mvnw -B -Drevision=<version> -Psign-artifacts -Prelease-judong deploy

# Deploy to Maven Central
./mvnw -B -Drevision=<version> -P"release-central,sign-artifacts" deploy
```

### Maven Profiles

| Profile | Purpose |
|---------|---------|
| `sign-artifacts` | Sign artifacts with GPG for release |
| `release-dummy` | Deploy to local filesystem (`/tmp/`) for testing |
| `release-judong` | Deploy to internal Nexus (`nexus.judo.technology`) |
| `release-central` | Deploy to Maven Central via OSSRH |
| `generate-github-asciidoc-diagrams` | Generate PlantUML diagrams from AsciiDoc |
| `update-source-code-license` | Update EPL-2.0 license headers in source files |

## Technology stack

### Core Technologies
- **Java 21** — language level for source and target
- **Eclipse EMF 2.21.0** — `EClass`, `EReference` for metamodel representation
- **OSGi 6.0.0** — bundle packaging, declarative services
- **Lombok 1.18.34** — boilerplate reduction (provided scope)
- **Gson 2.9.1** — JSON processing (provided scope, exported via OSGi)
- **Google Guava 30.0-jre** — collection utilities

### JUDO Ecosystem Dependencies
- **judo-dao-api** — `DAO` and `PayloadValidator` interfaces
- **judo-meta-asm** — `AsmModel` and `AsmUtils` for ASM metamodel access

### Build & Quality
- **Maven 3.9.4** with Maven Wrapper (`mvnw`)
- **JUnit 5 Jupiter 5.9.1** — unit testing
- **Mockito 4.8.0** — mocking framework
- **Hamcrest 2.2** — assertion matchers
- **JaCoCo 0.8.12** — code coverage
- **SonarQube** — static analysis via `sonar-maven-plugin 3.9.1.2184`
- **Felix maven-bundle-plugin 5.1.8** — OSGi bundle packaging
- **flatten-maven-plugin 1.3.0** — CI-friendly version resolution

## Development environment

**Required:**
- Java 21 JDK
- Maven 3.9.4+

**JVM arguments for tests** (configured automatically via Surefire):
```
--add-opens java.base/java.lang=ALL-UNNAMED
--add-opens java.base/java.util=ALL-UNNAMED
--add-opens java.base/java.time=ALL-UNNAMED
--add-opens java.base/java.net=ALL-UNNAMED
```

Build/test configuration lives in `pom.xml` (version driven by the `${revision}`
property) and `logback-test.xml` (Logback config for test execution). CI is
defined under `.github/workflows/` — `build.yml` (main build/test/deploy/tag),
`release.yml` (manual release; opens PRs for `master` and `develop`), and
`merge-pr-tagged.yml` (auto-merge triggered by `merge-pr/*` tags).

## Git workflow

- **Main Branch:** `develop`
- **Release Branch:** `master`
- **Versioning:** CI-friendly via `${revision}` property (current: `1.0.4-SNAPSHOT`), resolved by `flatten-maven-plugin`
- **Branch naming:** `feature/JNG-xxx_description`, `bugfix/JNG-xxx_description`, `release/X.Y.Z`, `hotfix/JNG-xxx_description`
- **All commits must include a JIRA ticket number** in `JNG-xxx` format

## Important notes

1. This is a **library consumed by generated code** — changes to public APIs affect all generated SDK projects downstream
2. The `MapHolder.toMap()` / `SdkUtil.asMap()` pattern is the bridge between typed SDK objects and the `Map<String, Object>` payload format used by `judo-dao-api`
3. `AbstractSdkDao.getEClass()` calls `Optional.get()` directly — it assumes the FQN always resolves (by design, since it's called from generated code with known-valid names)
4. Query filters produce JQL-like expression strings via `MessageFormat.format()`. `StringOperation` distinguishes between infix operators (e.g., `==`) and function-style operators (e.g., `like`, `ilike`, `matches`)
5. `TimestampFilter` normalizes `OffsetDateTime` inputs to UTC before formatting
6. OSGi exports: `hu.blackbelt.judo.sdk*` with version tracking. Imports include Gson, SLF4J, and OSGi framework packages with version ranges

## Related documentation

- [README](README.md) — Project introduction and architecture overview
- [Contributing Guide](CONTRIBUTING.md) — Development setup and PR guidelines
- [CI/CD Flow](.github/CIFLOW.md) — Branching strategy and GitHub Actions pipeline
- [judo-community](https://github.com/BlackBeltTechnology/judo-community) — Parent ecosystem documentation

<!-- dox-doctrine -->
## Documentation Update Protocol (WRITE discipline)

Per-directory `AGENTS.md` files form a tree. Each directory `AGENTS.md` is the
per-file record for the files in that directory. This module-root `AGENTS.md`
holds doctrine + architecture pointers only — never a per-file index.

**Keep the root lean.** This file loads into every agent turn — every byte costs
tokens on every turn. A verbose root file buries the rules the model must follow
(signal dilution) and measurably degrades adherence; a lean file keeps doctrine
salient. Default assumption: your update does NOT belong in the root — route it
by the table below.

**Route every doc update by kind:**

| Kind of update | Goes in |
|---|---|
| New file in a directory, or its per-file detail / change history | Nearest directory `AGENTS.md`. Add a `` | `<basename>` | <purpose> | `` row, path-alphabetical. |
| Data flow, protocol, architecture rationale | `docs/architecture.md` or a `docs/<topic>.md` |
| End-user / developer setup | `README.md` |
| Cross-cutting rule every agent needs every turn (rare) | this module-root `AGENTS.md` |

**Read before editing (chain walk).** Before editing a file, read the nearest
`AGENTS.md` chain root→leaf so you know the file's recorded purpose, contracts,
and change history. Do not edit blind.

**Update after editing (closeout pass).** After changing a file, update its row
in the nearest directory `AGENTS.md`: find the file's row, update its purpose in
place; if absent, add it in path-alphabetical order. New directory → scaffold
its `AGENTS.md`. One row per file. The purpose carries a one-line summary, key
exported symbols, contracts/invariants, and `See change: <id>` history.

**Row style (caveman).** Short declarative fragments. Drop articles. Subject →
verb → object, present tense. One fact per row. Prefer concrete tokens (paths,
symbols, env vars) over prose. Keep identifiers verbatim.

**Size rule — split an over-large directory `AGENTS.md` file-based.** pi
auto-injects a directory `AGENTS.md` on every turn when cwd sits at/below it, so
an over-large directory `AGENTS.md` is not supported. Split it file-based: a row
exceeding the length threshold promotes to a per-file `<File>.AGENTS.md`
sidecar carrying that file's full detail (including every `See change:`). The
sidecar is pull-only — its name is not `AGENTS.md`, so pi never auto-injects it
— yet it stays search-indexed (`agents` doc_type). The directory `AGENTS.md`
keeps a one-line summary plus a `→ see `<File>.AGENTS.md`` pointer. Rows within
the threshold stay verbatim (lossless).

## Finding docs (READ discipline)

`kb_*` tools are faster and cheaper than raw search — they return a one-line
purpose + key exports per file, not raw bytes. **This fires on the ACTION, not
the intent** — before you `grep`/`rg` for a symbol, `cat`/read a file to learn
what it does, or chase an import, the kb call goes first. It fires **even
mid-task when you already know the file**; knowing the file does not exempt you.
When your reflex is the left column, run the right column instead:

| You're about to… | Do this FIRST instead |
|---|---|
| `grep -rn "SymbolName" src/` — find where a fn / type / const lives | `kb_search --doc-type agents "SymbolName"` — tree indexes key exports per file |
| `grep -rn "feature\|topic" src/` — how does X work / where's X handled | `kb_search "feature topic"` |
| `cat` / read a file just to learn its purpose before editing | `kb agents <path>` — one-line purpose + exports + change history |
| chase imports / callers across files | `kb_neighbors <path\|heading>` |
| read one doc section in full | `kb_get <path> <section>` |

**Fall-through (explicit):** if the kb call returns nothing relevant, `rg` /
source read is allowed — then add the missing directory `AGENTS.md` row per the
WRITE discipline. kb does NOT replace grep; it goes first.
