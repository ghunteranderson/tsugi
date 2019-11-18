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

# Hello World
```kotlin

func main(args:String[]){
    printLn("Hello World")
}
```

# Variables
```kotlin
// Tsugi is static typed but those types can be inferred
var i = 1
var d1 = 1.0, d2 = 2.0
var s = "Hello World"
var j = getValue();

// Types must be used if the variable is not assigned a value
var age : Int
var firstName: String, lastName: String

// Types may be included for any declaration you'd like
var height : Int = 67
var hobby:String="bicycle", food:String="tacos"
```

# Constants
```kotlin
// Constants typically will not need a type declaration
const PI=3.14159
// but you can include one if you'd like
const CACHE:LocalCache=createCacheInstance()
```

# For Loops
```java
for var i=0; i<10; i++ {
    printLn("Hello World")
}

for {
    printLn("Loop until break")
    break
}

for isRunning(){
    printLn("Run until condition is false")
}
```

# If/Else
``` java

if a < b {
    printLn("a<b")
} else if b < a {
    printLn("b<a")
} else {
    printLn("a==b")
}

// braces are optional if block is a single statement

if c || d
    printLn("c||d")
else
    printLn("!c&&!d")
```

# Functions
```kotlin
// Here, both parameters and function return type are declared
func getName(fName:String, lName:String) : String {
    return `${fName} ${lName}`
}

// Here we explicitly declare the return type void
func run() : void {
    // do something
}


// Return types of a function can be inferred
// Here the return type is inferred as Int
func guess(){
    return 42
}

// Tsugi compiler does not recognize checked exceptions.
// TODO: Review how JVM handles checked exceptions.
func crash(){
    throw new Exception()
}
```

# Class
```kotlin
class Human {
    var name:String
    var address:String
    var dob:Date
    val ID:Long

    init(id:Long){
        ID=id
    }

    toString(){
        return "Human[id: " + id + "]"
    }

    getId(): Long{
        return ID
    }

    setName(name:String): void{
        this.name = name
    }
}
```

# Interface
```kotlin
interface Car {
    update(milliseconds:int): void
    render(canvas: Canvas): void
    handle(event: Event): void
}
```

# Enum
```kotlin
enum Car {
    VALUE_1(1),
    VALUE_2(2),
    VALUE_3(3);
    var x: int

    init(x: int){
        this.x = x
    }
}
```

# Space
``` kotlin
space Math{
    func add(a: Int, b: Int){
        return a + b
    }

    func sub(a: Int, b: Int){
        return a - b
    }
}
// ...
Math.add(2, 4)
Math.sub(4, 6)
```
