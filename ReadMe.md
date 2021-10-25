# Kotlin Koans
Solution for Kotlin koans
### Last update: 25.10.2021

## Links

### Introduction
* [Simple Functions](#hello-world) 
* [Named arguments](#named-arguments) 
* [Default arguments](#default-arguments)  
* [Triple-quoted strings](#triple-quoted-strings)  
* [Strings](#string-templates)
* [Nullable types](#nullable-types)
* [Nullable types](#nothing-type)
* [Lambdas](#lambdas)

### Classes
* [Data classes](#data-classes) 
* [Smart casts](#smart-casts) 
* [Sealed classes](#sealed-classes) 
* [Rename on import](#rename-on-import) 
* [Extension functions](#extension-functions) 


## Introduction

### Hello, world

> Check out the function syntax and change the code to make the function start return the string "OK" 
> In the Kotlin Koans tasks, the function TODO() will throw an exception. To complete Kotlin Koans, you need to replace this function invocation with meaningful code according to the problem.

- #### Solution
```Kotlin
    fun start(): String = "OK"
```

### Named arguments

> Make the function joinOptions() return the list in a JSON format (for example, [a, b, c]) by specifying only two arguments. 
> Default and named arguments help to minimize the number of overloads and improve the readability of the function invocation. The library function joinToString is declared with default values for parameters:

```Kotlin
fun joinToString(
    separator: String = ", ",
    prefix: String = "",
    postfix: String = "",
    /* ... */
): String
```

> It can be called on a collection of Strings.

- #### Solution
```Kotlin
    fun joinOptions(options: Collection<String>) = options.joinToString(separator=", ", prefix="[", postfix="]")
```

### Default arguments

> Imagine you have several overloads of 'foo()' in Java: 

```Kotlin
public String foo(String name, int number, boolean toUpperCase) {
    return (toUpperCase ? name.toUpperCase() : name) + number;
}
public String foo(String name, int number) {
    return foo(name, number, false);
}
public String foo(String name, boolean toUpperCase) {
    return foo(name, 42, toUpperCase);
}
public String foo(String name) {
    return foo(name, 42);
}
```

> You can replace all these Java overloads with one function in Kotlin. Change the declaration of the foo function in a way that makes the code using foo compile. Use default and named arguments.

- #### Solution
```Kotlin
fun foo(name: String, number: Int = 42, toUpperCase: Boolean = false) =
        (if (toUpperCase) name.uppercase() else name) + number

fun useFoo() = listOf(
        foo("a"),
        foo("b", number = 1),
        foo("c", toUpperCase = true),
        foo(name = "d", number = 2, toUpperCase = true)
)
```

### Triple-quoted strings

> Learn about the different string literals and string templates in Kotlin.
You can use the handy library functions trimIndent and trimMargin to format multiline triple-quoted strings in accordance with the surrounding code.
Replace the trimIndent call with the trimMargin call taking # as the prefix value so that the resulting string doesn't contain the prefix character.

- #### Solution
```Kotlin
const val question = "life, the universe, and everything"
const val answer = 42

val tripleQuotedString = """
    #question = "$question"
    #answer = $answer""".trimMargin("#")

fun main() {
    println(tripleQuotedString)
}
```

### String templates

> Triple-quoted strings are not only useful for multiline strings but also for creating regex patterns as you don't need to escape a backslash with a backslash.
The following pattern matches a date in the format 13.06.1992 (two digits, a dot, two digits, a dot, four digits):

```Kotlin
fun getPattern() = """\d{2}\.\d{2}\.\d{4}"""
```

> Using the month variable, rewrite this pattern in such a way that it matches the date in the format 13 JUN 1992 (two digits, one whitespace, a month abbreviation, one whitespace, four digits).

- #### Solution
```Kotlin
val month = "(JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC)"

fun getPattern(): String = """\d{2} ${month} \d{4}"""
```

### Nullable types

> Learn about null safety and safe calls in Kotlin and rewrite the following Java code so that it only has one if expression:

```Kotlin
public void sendMessageToClient(
    @Nullable Client client,
    @Nullable String message,
    @NotNull Mailer mailer
) {
    if (client == null || message == null) return;

    PersonalInfo personalInfo = client.getPersonalInfo();
    if (personalInfo == null) return;

    String email = personalInfo.getEmail();
    if (email == null) return;

    mailer.sendMessage(email, message);
}
```

- #### Solution
```Kotlin
fun sendMessageToClient(
        client: Client?, message: String?, mailer: Mailer
) {
    if (client == null || message == null)
    	return
    val personalInfo = client.personalInfo
    val email = personalInfo?.email
    email?.let{it ->
        mailer.sendMessage(it, message)
    }
}

class Client(val personalInfo: PersonalInfo?)
class PersonalInfo(val email: String?)
interface Mailer {
    fun sendMessage(email: String, message: String)
}
```

### Nothing type

> Nothing type can be used as a return type for a function that always throws an exception. When you call such a function, the compiler uses the information that the execution doesn't continue beyond the function.
Specify Nothing return type for the failWithWrongAge function. Note that without the Nothing type, the checkAge function doesn't compile because the compiler assumes the age can be null.

- #### Solution
```Kotlin
import java.lang.IllegalArgumentException

fun failWithWrongAge(age: Int?): Nothing {
    throw IllegalArgumentException("Wrong age: $age")
}

fun checkAge(age: Int?) {
    if (age == null || age !in 0..150) failWithWrongAge(age)
    println("Congrats! Next year you'll be ${age + 1}.")
}

fun main() {
    checkAge(10)
}
```


### Lambdas
 
> Kotlin supports functional programming. Learn about lambdas in Kotlin.
Pass a lambda to the any function to check if the collection contains an even number. The any function gets a predicate as an argument and returns true if at least one element satisfies the predicate.

- #### Solution
```Kotlin
fun containsEven(collection: Collection<Int>): Boolean =
        collection.any { it -> it % 2 == 0 }
```

## Classes

### Data classes
 
> Learn about classes, properties and data classes and then rewrite the following Java code to Kotlin:

```Kotlin
public class Person {
    private final String name;
    private final int age;
​
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
​
    public String getName() {
        return name;
    }
​
    public int getAge() {
        return age;
    }
}
```

> Afterward, add the data modifier to the resulting class. The compiler will generate a few useful methods for this class: equals/hashCode, toString, and some others.

- #### Solution
```Kotlin
data class Person(
	val name: String, val age: Int
)

fun getPeople(): List<Person> {
    return listOf(Person("Alice", 29), Person("Bob", 31))
}

fun comparePeople(): Boolean {
    val p1 = Person("Alice", 29)
    val p2 = Person("Alice", 29)
    return p1 == p2  // should be true
}
```

### Smart casts
 
> Rewrite the following Java code using smart casts and the when expression:

```Kotlin
public int eval(Expr expr) {
    if (expr instanceof Num) {
        return ((Num) expr).getValue();
    }
    if (expr instanceof Sum) {
        Sum sum = (Sum) expr;
        return eval(sum.getLeft()) + eval(sum.getRight());
    }
    throw new IllegalArgumentException("Unknown expression");
}
```

- #### Solution
```Kotlin
fun eval(expr: Expr): Int =
        when (expr) {
            is Num -> expr.value
            is Sum -> eval(expr.left) + eval(expr.right)
            else -> throw IllegalArgumentException("Unknown expression")
        }

interface Expr
class Num(val value: Int) : Expr
class Sum(val left: Expr, val right: Expr) : Expr
```


### Sealed classes
 
> Reuse your solution from the previous task, but replace the interface with the sealed interface. Then you no longer need the else branch in when.

- #### Solution
```Kotlin
fun eval(expr: Expr): Int =
        when (expr) {
            is Num -> expr.value
            is Sum -> eval(expr.left) + eval(expr.right)
        }

sealed interface Expr
class Num(val value: Int) : Expr
class Sum(val left: Expr, val right: Expr) : Expr
```

### Rename on import
 
> When you import a class or a function, you can specify a different name for it by adding as NewName after the import directive. It can be useful if you want to use two classes or  functions with similar names from different libraries.

> Uncomment the code and make it compile. Rename Random from the kotlin package to KRandom, and Random from the java package to JRandom.

- #### Solution
```Kotlin
import kotlin.random.Random as KRandom
import java.util.Random as JRandom

fun useDifferentRandomClasses(): String {
    return "Kotlin random: " +
            KRandom.nextInt(2) +
            " Java random:" +
            JRandom().nextInt(2) +
            "."
}
```

### Extension functions
 
> Learn about extension functions. Then implement the extension functions Int.r() and Pair. () and make them convert Int and Pair to a RationalNumber. Pair is a class defined in the standard library:

```Kotlin
data class Pair<out A, out B>(
    val first: A,
    val second: B
)
```

- #### Solution
```Kotlin
fun Int.r(): RationalNumber = RationalNumber(this, 1)

fun Pair<Int, Int>.r(): RationalNumber = RationalNumber(this.component1(), this.component2())

data class RationalNumber(val numerator: Int, val denominator: Int)
```

