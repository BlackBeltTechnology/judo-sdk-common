# Contributing to JUDO

## Development Environment Requirements

Your development environment must comply with the requirements in the parent project's [CONTRIBUTING guide](https://github.com/BlackBeltTechnology/judo-community/blob/develop/CONTRIBUTING.adoc). Key requirements:

- **Java 21** JDK
- **Maven 3.9.4+**

## Code Structure

This is a standard single-module Maven project:

| Directory | Contents |
|-----------|----------|
| `src/main/java` | Production code — base classes and interfaces for generated SDK code |
| `src/test/java` | Unit tests (JUnit 5 + Mockito + Hamcrest) |

## Submitting an Issue

Before submitting, search the [issue tracker](https://github.com/BlackBeltTechnology/judo-sdk-common/issues) — your problem may already be resolved.

To help us reproduce and fix bugs quickly, please include:

- Output of `java -version` and `mvn -version`
- Your `pom.xml` or `.flattened-pom.xml` (when applicable)
- A minimal reproduction case that demonstrates the failure

File new issues using the [issue form](https://github.com/BlackBeltTechnology/judo-sdk-common/issues/new/choose).

## Submitting a Pull Request

This project follows [GitHub's standard forking model](https://guides.github.com/activities/forking/). Fork the project to submit pull requests.

> **Important:** All commits must include a JIRA ticket number in `JNG-xxx` format.

## Build Commands

```bash
# Run tests
mvn clean test

# Full build and install to local repository
mvn clean install
```
