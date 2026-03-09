# Judo SDK Common - Project Documentation

## Project Overview


**Repository:** BlackBeltTechnology/judo-sdk-common
**License:** Eclipse Public License 2.0 (EPL-2.0)
**Java Version:** 21
**Build System:** Maven 3.9.4 with OSGi bundle packaging (Felix maven-bundle-plugin)

1. Provides base classes and interfaces that generated JUDO SDK source code depends on at runtime
2. Defines the entity identity model (`Identifiable`), map-based payload conversion (`MapHolder`, `SdkUtil`), and enumeration contract (`Enumeration`)
3. Contains `AbstractSdkDao` — the abstract superclass for all generated SDK DAOs, wiring the DAO layer, ASM metamodel, and payload validation
4. Implements a type-safe query filter framework (`query` package) for constructing JQL-like predicates across String, Number, Boolean, Date, Time, Timestamp, and Enumeration types
5. Packaged as an OSGi bundle exporting `hu.blackbelt.judo.sdk*`

## Code Instructions

1. First think through the problem, read the codebase for relevant files.
2. Before you make any major changes, check in with me and I will verify the plan.
3. Please every step of the way just give me a high level explanation of what changes you made.
4. Make every task and code change you do as simple as possible. We want to avoid making any massive or complex changes. Every change should impact as little code as possible. Everything is about simplicity.
5. Maintain a documentation file that describes how the architecture of the app works inside and out.
6. Never speculate about code you have not opened. If the user references a specific file, you MUST read the file before answering. Make sure to investigate and read relevant files BEFORE answering questions about the codebase. Never make any claims about code before investigating unless you are certain of the correct answer - give grounded and hallucination-free answers.
7. For implementation use TDD (Test-Driven Development): write or update tests first to define the expected behaviour, verify they fail, then write the minimal implementation to make them pass.
8. Use DRY (Don't Repeat Yourself): extract reusable logic into separate classes, utilities, or components. If the same pattern appears in multiple places, refactor it into a shared helper.

## Directory Structure

```
judo-sdk-common/
├── src/main/java/hu/blackbelt/judo/sdk/   # Production source code
│   ├── query/                              # Type-safe query filter framework
│   └── *.java                              # Core interfaces and base classes
├── src/test/java/                          # Unit tests
├── .github/workflows/                      # GitHub Actions CI/CD pipelines
├── openspec/                               # OpenSpec configuration
├── pom.xml                                 # Maven build configuration
└── logback-test.xml                        # Test logging configuration
```

## Core Modules

This is a single-module Maven project. The source code is organized into two logical layers:

### Core Layer (`hu.blackbelt.judo.sdk`)

| Class/Interface | Type | Purpose |
|----------------|------|---------|
| `AbstractSdkDao` | Abstract class | Base class for generated DAOs; holds `DAO`, `AsmModel`, `AsmUtils`, `PayloadValidator` references |
| `Identifiable` | Interface | Entity identity contract: identifier, entity type, version, and `adaptTo()` for type conversion |
| `MapHolder` | Interface | Contract for objects convertible to `Map<String, Object>` (the DAO payload format) |
| `SdkUtil` | Utility class | Static helpers for entity-to-map conversion and EMF `EReference` resolution |
| `Enumeration` | Interface | Contract for generated enum types: `getName()`, `getOrdinal()`, `getFqName()` |
| `NamedElement` | Interface | Simple contract for elements with a display name |

### Query Layer (`hu.blackbelt.judo.sdk.query`)

| Class | Type | Purpose |
|-------|------|---------|
| `Filter` | Interface | Base contract — produces a string expression via `Operation.getPattern()` |
| `Operation` | Interface | Defines `getPattern()` returning a `MessageFormat` pattern string |
| `StringFilter` / `StringOperation` | Filter + Enum | String comparisons (`==`, `!=`, `<`, `>`, `<=`, `>=`) plus `matches`, `like`, `ilike` |
| `NumberFilter` / `NumericOperation` | Filter + Enum | Numeric comparisons (`==`, `!=`, `<`, `>`, `<=`, `>=`) |
| `BooleanFilter` / `BooleanOperation` | Filter + Enum | Boolean predicates (`isTrue`, `isFalse`) |
| `DateFilter` | Filter | Date comparisons using `LocalDate`, formatted as backtick-wrapped ISO strings |
| `TimeFilter` | Filter | Time comparisons using `LocalTime`, formatted as backtick-wrapped ISO strings |
| `TimestampFilter` | Filter | Timestamp comparisons using `LocalDateTime`/`OffsetDateTime` (converted to UTC) |
| `EnumerationFilter` / `EnumerationOperation` | Filter + Enum | Enum equality/inequality using `Enumeration.getFqName()` |

## Technology Stack

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

## Build Commands

Maven Wrapper is available (`./mvnw`).

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

## Key Configuration Files

| File | Purpose |
|------|---------|
| `pom.xml` | Maven build configuration; version controlled via `${revision}` property |
| `logback-test.xml` | Logback configuration for test execution |
| `.github/workflows/build.yml` | Main CI workflow — build, test, deploy, tag |
| `.github/workflows/release.yml` | Manual release workflow — creates PRs for master and develop |
| `.github/workflows/merge-pr-tagged.yml` | Auto-merge workflow triggered by `merge-pr/*` tags |

## Development Environment

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

## Git Workflow

- **Main Branch:** `develop`
- **Release Branch:** `master`
- **Versioning:** CI-friendly via `${revision}` property (current: `1.0.4-SNAPSHOT`), resolved by `flatten-maven-plugin`
- **Branch naming:** `feature/JNG-xxx_description`, `bugfix/JNG-xxx_description`, `release/X.Y.Z`, `hotfix/JNG-xxx_description`
- **All commits must include a JIRA ticket number** in `JNG-xxx` format

## Important Notes

1. This is a **library consumed by generated code** — changes to public APIs affect all generated SDK projects downstream
2. The `MapHolder.toMap()` / `SdkUtil.asMap()` pattern is the bridge between typed SDK objects and the `Map<String, Object>` payload format used by `judo-dao-api`
3. `AbstractSdkDao.getEClass()` calls `Optional.get()` directly — it assumes the FQN always resolves (by design, since it's called from generated code with known-valid names)
4. Query filters produce JQL-like expression strings via `MessageFormat.format()`. `StringOperation` distinguishes between infix operators (e.g., `==`) and function-style operators (e.g., `like`, `ilike`, `matches`)
5. `TimestampFilter` normalizes `OffsetDateTime` inputs to UTC before formatting
6. OSGi exports: `hu.blackbelt.judo.sdk*` with version tracking. Imports include Gson, SLF4J, and OSGi framework packages with version ranges

## Related Documentation

- [README](README.md) — Project introduction and architecture overview
- [Contributing Guide](CONTRIBUTING.md) — Development setup and PR guidelines
- [CI/CD Flow](.github/CIFLOW.md) — Branching strategy and GitHub Actions pipeline
- [judo-community](https://github.com/BlackBeltTechnology/judo-community) — Parent ecosystem documentation
