### README.md

# ExpressionParser

## Описание

`ExpressionParser` — это Java-класс, реализующий разбор и вычисление арифметических выражений, включающих числа, переменные и различные операции. Поддерживаются унарные и бинарные операции, работа с приоритетами операций и скобками.

---

## Основные функции

- **Парсинг строковых выражений**:
  - Числа: положительные и отрицательные.
  - Переменные: поддерживаются переменные `x`, `y`, `z`.
  - Скобки для управления порядком выполнения операций.
- **Унарные операции**:
  - Отрицание (`-`).
  - Побитовое дополнение (`~`).
- **Бинарные операции**:
  - Сложение (`+`), вычитание (`-`).
  - Умножение (`*`), деление (`/`).
  - Побитовые операции: `|` (или), `&` (и), `^` (исключающее или).

---

## Использование

### Пример

```java
import expression.parser.ExpressionParser;
import expression.MyExpression;

public class Main {
    public static void main(String[] args) {
        // Пример выражения
        String expression = "(17322361 | -1527258051) - (x ^ 572142912)";

        // Создание парсера
        ExpressionParser parser = new ExpressionParser();

        // Разбор выражения
        MyExpression parsedExpression = parser.parse(expression);

        // Вывод результата
        System.out.println(parsedExpression);
    }
}
```

---

## Архитектура

- **Класс `ExpressionParser`**:
  - Метод `parse(String exp)`: Основной метод парсинга, возвращает объект типа `MyExpression`.
  - Утилитарные методы: `withoutSpaces`, `amount`, `isOp`, `getPriority`, `makeExp`.

- **Интерфейс `MyExpression`**:
  - Базовый интерфейс для выражений.

- **Классы операций** (`Add`, `Subtract`, `Multiply`, `Divide`, `And`, `Or`, `Xor`, `Minus`, `Not`):
  - Реализуют конкретные арифметические и логические операции.
