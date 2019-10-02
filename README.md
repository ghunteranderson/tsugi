# Tsugi
Just another programming language project. Below, are some samples and proposed
syntax from which, one day, a spec may be written.

# Concepts
modules - similar to a namespace, a module contains types and functions. However can also
contain global constants.   
type - a data type either custom or built in. This could be simple and internally represented
as a primative data type or complex.

# Comments
``` java
// Single line comments can be written like this

/* While multi line comments
can be written like this */
```

# Importing modules
```
// Entire modules can be imported like this
import module Math

/* Sometimes, we may want to alias the module name. This can be especially
 * useful if the module has a long fully qualified name
 */
import module QualityGraphs as qg

/* Only specific types from a module can be imported if desired.
 * These types can now be directly referenced in the namespace without
 * having to specify which module they come from. However, types cannot
 * be aliased
 */
import type QualityGraphs.QGWindow

/* Just like types, functions and variables can be directly imported as well.
 * Function assertTrue and constant PI can be referenced without specifing the
 * module.
 */
import function Assertions.assertTrue // Import function from namespace
import constant Math.PI
```

# Native Data Types
Some data types get special treatment by the compiler. They are the data types
natively supported by the language evenn without the standard library.
|  type  | description |
+--------+-------------+
| Long | An 8 byte signed whole number |
| Integer | A 4 byte signed whole number |
| Short | A 2 byte signed whole number |
| Byte | A 1 byte signed whole number |
| Char | A single character from the UTF-8 charset. It may take up to 4 bytes to store |
| Double | A double precision floating point number |
| Float | A single percision floating point number |
| Object | The parent type from which all other types extend |

Additionally, arrays of any single type are supported as well. Arrays are fixed-length
and can only store types assignable to the declared type of the array.

# Literals
TODO

# Variables and Constants
```
// Declare a new variables
Integer number
Double[] decimalArray
String[] foods
CustomType object

// Set the value of the existing variable
number = 34
decimalArray = new Double[20]
foods = ["Peanuts", "Noodles", "Chicken"]
object = new CustomType()

// Implicit static typing
// This is only allowed when the variable is immediately assigned a value
var graph = new BarGraph()
var age = 3

// Since constants must be instantiated immediately, no type is required
const PI = 3.14159
```

# Conditionals
```
// Standard if/else block
if 30 in range:
    write("30 is in the range")
else if 20 in range:
    write("20 is in the range")
else
    write("This is the else block")

// Single line if
if 20 < 30 then write("Always")
else if 20<Math.random() then write("maybe")
else then write("else block")

// Mixed approach
if arg >= 0 then return arg
else:
    arg = arg * -1
    return arg

// If Expression
// Only available when an expression is expected, not a statement
return if arg >= 0 then arg else arg*-1
```

# Ranges
```
var range = [1...3]         // Iterable<Integer>
```

# Iteration
```
// For var in iterable
for x in [1...40]:
    var b = x * Math.random()
    graph.put(x, b)


// Standard while loop
var stop = False
while !stop:
    print("Never gonna give you up")
    stop = Math.random() < 0.1
```

# Auto-closing resources
```
// Auto closing resource block
open a = gq.closable1(), b=g1.closable2(), c=gq.closable3():
    a.apply(b).apply(c)
```

# Functions

## Standard Declaration
Functions can be declared as reusable code
```
// Multi line function
// type is Function<Double, Double, Double>
Double random(Double min, Double max):
    var range = max  - min
    var random = Math.random() * range
    random = random + min
    return random

// Single line functions
void sayHello() -> write("Hello") // Type is Function<Void>

void printLine(String value) -> write(value + "\n") // Type is Function<Void,String>
```
## As Expressions (Lambdas)
Functions can be declared as objects and stored in variables
```
// Single line function (as an expression)
Function<Integer, Integer> abs = a -> Math.abs(a)
```

## Composition
Functions can be passed into another functions
```
void forEach(Object[] list, Function<Void, Object, Integer> handler):
    var i=0
    while i<list.length:
        handler(list[i], i)
        i++

// Passing a new lamda function
var foods = ["apple", "banana", "carrot"]
forEach(foods, (obj, index) -> write(index + ". " + obj))

// Passing an existing function
void print(Object obj, Integer index):
    write(index + ". " + obj)
forEach(foods, print)
```

# Types

```
public type Object:
    public String toString() -> "Object"
    public Boolean equals(Object obj) -> obj.toString().equals(self.toString())
```
```
private type Human: // Implicitly extends Object

    // Private attributes
    private Boolean alive
    private Long age
    private String name

    // Constructor
    public init(String name):
        self.name = name
        self.age = 0
        self.alive = true

    // Public behavior
    public void update(Long ms):
        if alive:
            age += ms
            updateAlive(ms)

    // Private behavior
    private Boolean updateAlive(Long delta):
        for i=0, i<delta, i++:
            var random = Math.random()
            if random<=0.01:
                alive=False
                break
```
