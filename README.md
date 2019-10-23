# Tsugi
Just another programming language project. Below, are some samples and proposed
syntax from which, one day, a spec may be written.

# Concepts
* **module** - a collection of classes, interfaces, lists, spaces, and scripts.
A module should be a coherent group of components serving a similar purpose. All
files of the same module are stored in a single folder. The modules name is
derived from the folders name.
* **class** - a template describing the attributes and behaviors of a particular
type of object. Multiple objects created from the same class will have the same
API available.
* **interface** - an API lacking the implementation. If a class can implements
the methods of an interface, then it will be accepted as a sub-type of the
interface.
* **enum** - A collection of constants of the same type. Those constants can have
other data tied to them.
* **space** - A collection of functions. The space can be imported and methods
referenced using the space's name as a prefix

# Comments
``` java
// Single line comments can be written like this

/* While multi line comments
can be written like this */
```

# Importing Types, Functions, and Variables
Types have a fully qualified name that consist of the <module>.<type>. That means
referencing another type might look like this.
```
var obj = new tsugi.package.CustomType(arg1, arg2, arg3)
```
But we like easy to read code and this is not that. We can use the `import`
keyword to specify any imports or aliasing. In Tsugi, with statements should be
listed at the beginning of the file directly below the module declaration
```
// Types can be imported allowing them to be referenced without the module prefix
// This works for classes, interfaces, and enums
import tsugi.util.StringBuilder
var bob = new StringBuilder()

// Spaces can be imported to access functions and constants without the module prefix
import tsugi.util.Math
var pipi = Math.pow(Math.PI, 2)

// If we want to keep a module prefix but make it shorter, we can alias the module name
import tsugi.long.stocking as pls
// Using a type from this module might now look like this
pls.Type t = new pls.Type(arg1, arg2)
// Note that only modules can be aliased. Types and methods cannot be aliased
```

# Native Data Types
TODO

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
// This is allowed when the variable is immediately assigned a value
var graph = new BarGraph()
var age = 3 // Integer type

// Since constants must be instantiated immediately, no type is required
const PI = 3.14159
```

# Conditionals
```
// Standard if/else block
if 30 in myList:
    write("30 is in the list")
else if 20 in mySet:
    write("20 is in the set")
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
let myRance = range(1, 3)         // Iterable<Integer>
```

# Iteration
```
// For var in iterable
for x in range(1, 40):
    let b = x * Math.random()
    graph.put(x, b)

// Traditional for loop
for x=0, x<12, x++:
    write(Math.square(x))

// Standard while loop
let stop = False
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
// Single line function (as an expression) can be passed into another function
collection.forEach(item -> item.setDate(now))
// They can even be stored as objects
Function<Integer, Integer> abs = a -> Math.abs(a)
```
