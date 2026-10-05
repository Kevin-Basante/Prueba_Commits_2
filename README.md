# Prosthetic Limb Design System

University project for **Object-Oriented Programming** demonstrating the
**Builder** and **Prototype** design patterns through a real-world,
uncommon case study: a configurator for custom biomedical prosthetic limbs.

## Case study

A biomedical engineering workflow needs to:

1. **Build** a complete prosthesis (socket, joint system, sensors, power
   source, cosmetic cover) step by step, with validation rules between
   components (e.g. a myoelectric sensor cannot be attached without a
   power source). → **Builder pattern**.
2. **Clone** previously validated clinical configurations ("templates")
   as the starting point for a new patient, instead of rebuilding
   everything from scratch, then only adjust the parts that differ
   (socket size, cosmetic color, etc.). → **Prototype pattern**.

Builder and Prototype meet explicitly in `TemplateInitializer`: the
`ClinicalConfigurationDirector` (Builder) constructs each master
configuration once, and those masters are registered into the
`ProsthesisTemplateRegistry` (Prototype) to be cloned afterward.

## Requirements

- JDK 17+
- Maven 3.8+
- Internet access on first build (Maven needs to download the JavaFX and
  JUnit dependencies declared in `pom.xml`)

## How to run

```bash
mvn clean javafx:run
```

## How to run the tests

```bash
mvn test
```

## Project structure

```
src/main/java/com/prosthetics/
├── Main.java                 Application entry point (JavaFX)
├── model/                    Domain classes (the "products"), each Cloneable
│   └── enums/                SocketType, Material, JointType, SensorType, PowerType
├── builder/                  Builder pattern: ProsthesisBuilder + Director
├── prototype/                Prototype pattern: template registry + initializer
├── service/                  Facade + validation used by the UI layer
└── ui/controller/            JavaFX controllers (one per tab)

src/main/resources/com/prosthetics/
├── fxml/                     Scene layouts (FXML)
└── css/                      Stylesheet

src/test/java/com/prosthetics/
├── builder/                  Unit tests for ProsthesisBuilder
└── prototype/                Unit tests for ProsthesisTemplateRegistry
```

## UI overview

- **Build New Prosthesis** — step-by-step form driving `ProsthesisBuilder`.
- **Template Gallery** — lists registered prototypes; "Clone & Customize"
  deep-clones one into the Builder tab for editing.
- **Comparison** — shows the original template next to the customized
  clone, side by side, to make the independence of the copy visible.

## Design notes

- Every mutable component (`Socket`, `JointSystem`, `Sensor`,
  `PowerSource`, `CosmeticCover`) implements `Cloneable` individually so
  `Prosthesis.clone()` can perform a true **deep copy**.
- `ProsthesisTemplateRegistry` stores its own defensive clone on
  registration and returns a fresh clone on every retrieval, so neither
  the caller nor the registry can corrupt each other's state.
- `ProsthesisBuilder` enforces domain validation rules in `build()`
  (mandatory components) and in `addSensor()` (power dependency),
  keeping invalid states impossible to construct.
