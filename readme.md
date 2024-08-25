
# Expense Management System

This project is an Expense Management System that follows the Command-Query-Responsibility-Segregation (CQRS) pattern using Spring Boot. The system manages the lifecycle of expenses including creation, updating, deletion, and querying, with events triggered for each operation.

## Event-Driven Architecture

This system leverages an event-driven architecture where commands initiate changes, and corresponding events are triggered to reflect those changes across the system. This approach decouples the components, making the system more scalable, responsive, and easy to maintain.

### Process Flow Diagram

```mermaid
graph TD
    subgraph Command Flow
        ECC[Expense Command Controller]
        ECC -->|Create| CEC[Create Expense Command]
        ECC -->|Update| UEC[Update Expense Command]
        ECC -->|Delete| DEC[Delete Expense Command]

        CEC --> ES1[Expense Service]
        UEC --> ES1
        DEC --> ES1

        ES1 --> CEEX[Create Expense Executor]
        ES1 --> UEEX[Update Expense Executor]
        ES1 --> DEEX[Delete Expense Executor]

        CEEX --> ER1[Expense Repository - Command]
        UEEX --> ER1
        DEEX --> ER1

        ER1 --> EDB1[(Expense Database - Command)]
    end

    subgraph Event Flow
        CEEX -->|Trigger| CEE[Create Expense Event]
        UEEX -->|Trigger| UEE[Update Expense Event]
        DEEX -->|Trigger| DEE[Delete Expense Event]

        CEE -->|Publish| KTK[Kafka Topic - budget]
        UEE -->|Publish| KTK
        DEE -->|Publish| KTK
    end

    subgraph Query Flow
        EQC[Expense Query Controller]
        EQC -->|Get by User ID| GEBUI[Get Expenses By User ID Query]
        EQC -->|Get by Type and User ID| GETUI[Get Expenses By Type and User ID Query]

        GEBUI --> ES2[Expense Service]
        GETUI --> ES2

        ES2 --> GEBUIE[Get Expenses By User ID Executor]
        ES2 --> GETUIE[Get Expenses By Type Executor]

        GEBUIE --> ER2[Expense Repository - Query]
        GETUIE --> ER2

        ER2 --> EDB2[(Expense Database - Query)]
    end
```

### Class Diagrams with Descriptions

#### Command Classes
Commands represent actions to be performed, such as creating, updating, or deleting an expense. They are used to request changes in the system.

```mermaid
classDiagram
    class CreateExpenseCommand {
        Long userId
        String expenseName
        String expenseDescription
        Category expenseType
        Double amount
    }

    class UpdateExpenseCommand {
        Long expenseId
        Long userId
        String expenseName
        String expenseDescription
        Category expenseType
        Double amount
    }

    class DeleteExpenseCommand {
        Long expenseId
    }
```

#### Event Classes
Events are triggered after commands are executed, capturing the results of those actions. They notify the system that a significant change has occurred, which can be used to update other components.

```mermaid
classDiagram
    class CreateExpenseEvent {
        Long expenseId
        Long userId
        String expenseName
        String expenseDescription
        Category expenseType
        Double amount
    }

    class UpdateExpenseEvent {
        Long expenseId
        Long userId
        String expenseName
        String expenseDescription
        Category expenseType
        Double newAmount
        Double diffAmount
    }

    class DeleteExpenseEvent {
        Long expenseId
        Long userId
        String expenseName
        String expenseDescription
        Category expenseType
        Double amount
    }
```

#### Entity Class
Entities represent the data model of the system, such as an `Expense`. They define the structure and attributes of the data that is stored and managed within the system.

```mermaid
classDiagram
    class Expense {
        Long expenseId
        Long userId
        String expenseName
        String expenseDescription
        Category expenseType
        Double amount
    }

    class Category {
        <<enumeration>>
        FOOD
        TRANSPORTATION
        ENTERTAINMENT
        OTHER
    }
```

#### Query Classes
Queries are used to retrieve information from the system, such as fetching expenses based on certain criteria. They do not change the state of the system but are essential for providing data to users and other components.

```mermaid
classDiagram
    class GetExpensesByUserIdQuery {
        Long userId
    }

    class GetExpensesByTypeAndUserIdQuery {
        Long userId
        Category expenseType
    }
```

### Controllers
Controllers handle incoming HTTP requests and map them to the appropriate commands, queries, or events. The `ExpenseCommandController` manages commands related to expenses, while the `ExpenseQueryController` manages queries. These controllers serve as the entry points to the system's functionality, exposing the API to the outside world.

## Getting Started

To run the service locally:

1. **Clone the repository**:
   ```bash
   git clone https://github.com/viswarajramji/expense.git
   cd expense
   ```

2. **Build the application**:
   ```bash
   ./mvnw clean install
   ```

3. **Start the application**:
   ```bash
   ./mvnw spring-boot:run
   ```

4. **Access the application** at `http://localhost:8084`.

5. **Access the database** at `http://localhost:8084/h2-console`.

**Note**: Ensure Kafka is running and the topic `expenseservice` is created.

## Swagger Endpoint

Access the Swagger UI to interact with the API:

- **URL**: `http://localhost:8084/swagger-ui.html`
