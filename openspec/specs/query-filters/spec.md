# query-filters Specification

## Purpose

Provides a type-safe filter builder framework for constructing JQL-like query predicates. Each supported data type has a dedicated `Filter` implementation with static factory methods and a corresponding `Operation` enum that defines the operator patterns.

## Architecture

The query framework lives in `hu.blackbelt.judo.sdk.query` and consists of:

- **`Filter`** (interface) — defines `getOperation()`, `getValueAsString()`, and a default `toString(expression)` that formats the predicate via `MessageFormat.format(operation.getPattern(), expression, value)`
- **`Operation`** (interface) — defines `getPattern()` returning a `MessageFormat`-compatible pattern string
- **Type-specific filters** — `StringFilter`, `NumberFilter`, `BooleanFilter`, `DateFilter`, `TimeFilter`, `TimestampFilter`, `EnumerationFilter`
- **Type-specific operations** — `StringOperation`, `NumericOperation`, `BooleanOperation`, `EnumerationOperation` (enums implementing `Operation`)

Filters use private constructors and expose static factory methods (e.g., `StringFilter.equalTo("abc")`). Operation enums define the JQL operator patterns.

### Supported operations by type

| Filter Type | Operations |
|-------------|-----------|
| `StringFilter` | `<`, `<=`, `>`, `>=`, `==`, `!=`, `matches`, `like`, `ilike` |
| `NumberFilter` | `<`, `<=`, `>`, `>=`, `==`, `!=` |
| `BooleanFilter` | `isTrue`, `isFalse` |
| `DateFilter` | `<`, `<=`, `>`, `>=`, `==`, `!=` |
| `TimeFilter` | `<`, `<=`, `>`, `>=`, `==`, `!=` |
| `TimestampFilter` | `<`, `<=`, `>`, `>=`, `==`, `!=` |
| `EnumerationFilter` | `==`, `!=` |

## Requirements

### Requirement: Filter expression formatting

`Filter.toString(expression)` SHALL produce a formatted predicate string by applying `MessageFormat.format(operation.getPattern(), expression, valueAsString)`.

#### Scenario: Infix operator formatting
- **GIVEN** a `NumberFilter.greaterThan(42)`
- **WHEN** `filter.toString("self.age")` is called
- **THEN** the result SHALL be `"(self.age > 42)"`

#### Scenario: Function-style operator formatting
- **GIVEN** a `StringFilter.like("%test%")`
- **WHEN** `filter.toString("self.name")` is called
- **THEN** the result SHALL be `"(self.name)!like(\"%test%\")"`

### Requirement: String filter operations

`StringFilter` SHALL support comparison operators (`<`, `<=`, `>`, `>=`, `==`, `!=`) and pattern-matching operators (`matches`, `like`, `ilike`).

#### Scenario: String equality
- **WHEN** `StringFilter.equalTo("hello")` is created
- **THEN** `getOperation()` SHALL return `StringOperation.EQUAL_TO`
- **AND** `getValueAsString()` SHALL return `"\"hello\""` (double-quote wrapped)

#### Scenario: String value escaping
- **GIVEN** a string value containing double quotes
- **WHEN** `StringFilter.equalTo("say \"hi\"")` is created
- **THEN** `getValueAsString()` SHALL return the value with escaped inner quotes

#### Scenario: Case-insensitive like
- **WHEN** `StringFilter.ilike("%pattern%")` is created
- **THEN** `getOperation()` SHALL return `StringOperation.ILIKE` with a function-style pattern

### Requirement: Numeric filter operations

`NumberFilter` SHALL support the six standard comparison operators for any `Number` value.

#### Scenario: Numeric less-than
- **WHEN** `NumberFilter.lessThan(100)` is created
- **THEN** `getOperation()` SHALL return `NumericOperation.LESS_THAN`
- **AND** `getValueAsString()` SHALL return `"100"`

### Requirement: Boolean filter operations

`BooleanFilter` SHALL support `isTrue()` and `isFalse()` predicates.

#### Scenario: Boolean true filter
- **WHEN** `BooleanFilter.isTrue()` is created
- **THEN** `getOperation()` SHALL return `BooleanOperation.TRUE`

#### Scenario: Boolean false filter
- **WHEN** `BooleanFilter.isFalse()` is created
- **THEN** `getOperation()` SHALL return `BooleanOperation.FALSE`

### Requirement: Date filter operations

`DateFilter` SHALL support the six standard comparison operators using `LocalDate` values formatted as backtick-wrapped ISO date strings.

#### Scenario: Date comparison value format
- **GIVEN** a `DateFilter.equalTo(LocalDate.of(2024, 1, 15))`
- **WHEN** `getValueAsString()` is called
- **THEN** the result SHALL be `` `2024-01-15` ``

### Requirement: Time filter operations

`TimeFilter` SHALL support the six standard comparison operators using `LocalTime` values formatted as backtick-wrapped ISO time strings.

#### Scenario: Time comparison value format
- **GIVEN** a `TimeFilter.greaterThan(LocalTime.of(14, 30, 0))`
- **WHEN** `getValueAsString()` is called
- **THEN** the result SHALL be `` `14:30:00` ``

### Requirement: Timestamp filter operations

`TimestampFilter` SHALL support the six standard comparison operators using `LocalDateTime` or `OffsetDateTime` values. `OffsetDateTime` inputs SHALL be normalized to UTC before formatting.

#### Scenario: OffsetDateTime UTC normalization
- **GIVEN** an `OffsetDateTime` at `2024-01-15T10:00:00+02:00`
- **WHEN** `TimestampFilter.equalTo(offsetDateTime)` is created
- **THEN** the stored value SHALL be `2024-01-15T08:00:00` (converted to UTC)

#### Scenario: Timestamp value format
- **GIVEN** a `TimestampFilter` with value `2024-01-15T08:00:00`
- **WHEN** `getValueAsString()` is called
- **THEN** the result SHALL be `` `2024-01-15T08:00:00` ``

### Requirement: Enumeration filter operations

`EnumerationFilter` SHALL support equality and inequality comparisons using the enumeration's fully qualified name.

#### Scenario: Enum equality
- **GIVEN** an `Enumeration` instance with `getFqName()` returning `"com.example.Status#ACTIVE"`
- **WHEN** `EnumerationFilter.equalTo(enumValue)` is created
- **THEN** `getValueAsString()` SHALL return `"com.example.Status#ACTIVE"`

#### Scenario: Enum inequality
- **WHEN** `EnumerationFilter.notEqualTo(enumValue)` is created
- **THEN** `getOperation()` SHALL return `EnumerationOperation.NOT_EQUAL_TO`
