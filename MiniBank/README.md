# MiniBank — Java Banking Application

## Overview
MiniBank is a console-based banking application developed in Java. It demonstrates object-oriented programming, exception handling, multithreading, file persistence, generics, annotations, and layered architecture.

## Features
- Open savings, current, and fixed-deposit accounts
- Deposit, withdraw, and transfer money
- Account validation and custom exceptions
- Abstract classes, inheritance, and polymorphism
- Interfaces and functional interfaces
- Custom annotations and reflection-based validation
- Thread-safe account operations and concurrent transaction processing
- Producer-consumer pattern and deadlock prevention demonstrations
- Account persistence using Java serialization
- Transaction logging and report generation
- Generic repository with account lookup, listing, deletion, and average calculation
- Service layer for banking operations
- JUnit tests for repository operations

## Technologies
- Java
- Java Collections Framework
- Java Concurrency API
- Java Serialization
- Reflection and custom annotations
- JUnit 5

## Project Structure
- `MiniBank.java` — Main application and menu
- `src/model/` — Account classes and models
- `src/service/` — Banking services and concurrency demonstrations
- `src/util/` — Validation, persistence, repository, logging, and reporting utilities
- `src/exception/` — Custom exceptions
- `src/model/annotation/` — Custom annotations
- `tests/` — JUnit tests
- `data/` — Saved accounts and generated reports
- `logs/` — Transaction logs
- `out/` — Compiled classes
- `MiniBank.jar` — Runnable application

## Requirements
Install a compatible JDK and ensure `java`, `javac`, and `jar` are available in the terminal.

## Compile
Run from the `OOPs-sem3` parent directory:

```powershell
javac -d MiniBank\out MiniBank\MiniBank.java MiniBank\src\model\*.java MiniBank\src\model\annotation\*.java MiniBank\src\exception\*.java MiniBank\src\util\*.java MiniBank\src\service\*.java
```

## Run the Application
From the `OOPs-sem3` parent directory:

```powershell
java -jar MiniBank\MiniBank.jar
```

## Run JUnit Tests
From the `OOPs-sem3` parent directory:

```powershell
java -jar junit-platform-console-standalone-1.13.1.jar execute --class-path MiniBank\out --select-class MiniBank.tests.RepositoryJUnitTest
```

## Data Files
Saved accounts are stored in `data/accounts.dat`, transaction logs in `logs/transactions.log`, and generated reports in `data/transaction-report.txt`, relative to the expected working directory.

## Author
Student project — OOP Semester 3