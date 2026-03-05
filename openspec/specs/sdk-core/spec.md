# sdk-core Specification

## Purpose

Provides the foundational interfaces and base classes that all generated JUDO SDK code depends on: entity identity (`Identifiable`), map-based payload conversion (`MapHolder`, `SdkUtil`), enumeration contracts (`Enumeration`), named element support (`NamedElement`), and the abstract DAO base class (`AbstractSdkDao`).

## Architecture

The core layer lives in `hu.blackbelt.judo.sdk` and defines:

- **`Identifiable`** — identity interface with identifier, entity type, version, `adaptTo()`, and a static `equals()` that compares all three identity fields
- **`MapHolder`** — conversion interface to `Map<String, Object>` payloads and `adaptTo()` for type adaptation
- **`SdkUtil`** — static utility class bridging typed SDK objects and the DAO's map-based payload format, plus EMF reference resolution
- **`Enumeration`** — interface for generated enums with `getName()`, `getOrdinal()`, `getFqName()`
- **`NamedElement`** — simple named element interface with `getName()`
- **`AbstractSdkDao`** — abstract base class holding `DAO`, `AsmModel`/`AsmUtils`, and `PayloadValidator` references; provides `getEClass(fqName)` for resolving EMF classes

## Requirements

### Requirement: Entity identity contract

`Identifiable` SHALL provide access to an entity's identifier, entity type, and version, and SHALL support type adaptation via `adaptTo()`.

#### Scenario: Two identifiables with same identity are equal
- **GIVEN** two `Identifiable` instances with the same `getIdentifier()`, `getVersion()`, and `getEntityType()` values
- **WHEN** `Identifiable.equals(a, b)` is called
- **THEN** the result SHALL be `true`

#### Scenario: Null comparison returns false
- **GIVEN** an `Identifiable` instance
- **WHEN** `Identifiable.equals(identifiable, null)` is called
- **THEN** the result SHALL be `false`

#### Scenario: Different version means not equal
- **GIVEN** two `Identifiable` instances with the same identifier and entity type but different versions
- **WHEN** `Identifiable.equals(a, b)` is called
- **THEN** the result SHALL be `false`

### Requirement: Map-based payload conversion

`MapHolder` SHALL provide a `toMap()` method returning a `Map<String, Object>` representation of the entity.

#### Scenario: Single entity conversion
- **GIVEN** an object implementing `MapHolder`
- **WHEN** `SdkUtil.asMap(object)` is called
- **THEN** it SHALL delegate to `MapHolder.toMap()` and return the resulting map

#### Scenario: Collection conversion
- **GIVEN** a collection of `MapHolder` instances
- **WHEN** `SdkUtil.asMap(collection)` is called
- **THEN** it SHALL return a collection of maps, each produced by calling `toMap()` on the corresponding element

### Requirement: Convenience map builders

`SdkUtil` SHALL provide overloaded `asMap()` methods for constructing `Map<String, Object>` from up to 4 key-value pairs.

#### Scenario: Two key-value pair map
- **WHEN** `SdkUtil.asMap("k1", v1, "k2", v2)` is called
- **THEN** the result SHALL be a `HashMap` with exactly those two entries

### Requirement: EMF reference resolution

`SdkUtil.getReference()` SHALL resolve an `EReference` by name from a given `EClass`.

#### Scenario: Resolve existing reference
- **GIVEN** an `EClass` with a reference named `"items"`
- **WHEN** `SdkUtil.getReference(eClass, "items")` is called
- **THEN** the corresponding `EReference` SHALL be returned

### Requirement: Abstract DAO wiring

`AbstractSdkDao` SHALL accept `DAO`, `AsmModel`, and `PayloadValidator` via setter injection and SHALL construct `AsmUtils` from the model's resource set.

#### Scenario: Setting the ASM model initializes AsmUtils
- **GIVEN** an `AbstractSdkDao` subclass
- **WHEN** `setAsmModel(asmModel)` is called
- **THEN** `asmUtils` SHALL be initialized with `new AsmUtils(asmModel.getResourceSet())`

#### Scenario: Resolving an EClass by FQN
- **GIVEN** an `AbstractSdkDao` with an initialized `asmUtils`
- **WHEN** `getEClass("com.example.MyEntity")` is called
- **THEN** it SHALL delegate to `asmUtils.getClassByFQName()` and return the resolved `EClass`

### Requirement: Enumeration contract

`Enumeration` SHALL provide name, ordinal, and fully qualified name for generated enum constants.

#### Scenario: Enum constant identity
- **GIVEN** a generated enum implementing `Enumeration`
- **WHEN** `getName()`, `getOrdinal()`, and `getFqName()` are called
- **THEN** they SHALL return the constant's name, ordinal position, and fully qualified name respectively
