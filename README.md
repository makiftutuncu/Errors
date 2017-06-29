Errors [![Build Status](https://travis-ci.org/mehmetakiftutuncu/Errors.svg?branch=master)](https://travis-ci.org/mehmetakiftutuncu/Errors)
======

Errors is an easy-to-use library written in Scala for providing immutable, lightweight, extensible way to represent errors in your project.

How to Include in Your Project
--------------
Add following to your ```build.sbt``` if you are using **SBT**
```sbt
libraryDependencies += "com.github.mehmetakiftutuncu" %% "errors" % "1.2"
```

Or add following to your ```pom.xml``` if you are using **Maven** (use **errors_2.11** for **Scala 2.11**)
```xml
<dependency>
	<groupId>com.github.mehmetakiftutuncu</groupId>
	<artifactId>errors_2.12</artifactId>
	<version>1.2</version>
</dependency>
```

Or add following to your ```build.gradle``` if you are using **Gradle** (use **errors_2.11** for **Scala 2.11**)
```gradle
compile 'com.github.mehmetakiftutuncu:errors_2.12:1.2'
```

Examples
--------------
Since Errors is immutable, any kind of update operation (i.e. any method returning [```Errors```](src/main/scala/com/github/mehmetakiftutuncu/errors/Errors.scala) or any error extending [```ErrorBase```](src/main/scala/com/github/mehmetakiftutuncu/errors/base/ErrorBase.scala)) return a new copy with new data.

### Creating an Errors Instance
You can easily create both empty and non-empty [```Errors```](src/main/scala/com/github/mehmetakiftutuncu/errors/Errors.scala) instances. Just use ```empty``` or ```apply``` methods.

**Example**
```scala
// Empty errors
val errors1 = Errors.empty
val errors2 = Errors()

// Non-empty errors already containing given errors
val errors3 = Errors(CommonError.timeout, SimpleError.authorization)
```

****

### Creating Error Objects
You can create error objects to add to or remove from or even perform checks on an ```Errors``` instance. There are 2 types of errors already defined; [```CommonError```](src/main/scala/com/github/mehmetakiftutuncu/errors/CommonError.scala) and [```SimpleError```](src/main/scala/com/github/mehmetakiftutuncu/errors/SimpleError.scala). However, you can use any type of errors as long as they extend from [```ErrorBase```](src/main/scala/com/github/mehmetakiftutuncu/errors/base/ErrorBase.scala).

**Example**
```scala
// You can provide all info.
val error1 = CommonError(name = "invalidData", reason = "Value must be a positive integer.", data = "-5")

// You can provide some info and update reason and data of a CommonError later.
val error2 = CommonError("timeout").reason("Network might be down.").data("30 seconds")

// There are even some predefined helper methods to give you appropriate instances easily. 
val error3 = CommonError.authorization.reason("Username or password is invalid!")

// Literally "simple", just an error name
val error4 = SimpleError("database")
val error5 = SimpleError.timeout
```

****

### Adding Errors
You can add a single error, multiple errors or the errors in an existing ```Errors``` instance to an ```Errors``` instance.

**Example**
```scala
val errors1 = Errors.empty
val errors2 = Errors(SimpleError.authorization)

val errors3 = errors1 + CommonError.timeout

val errors4 = errors1.addAll(CommonError.timeout, SimpleError.authorization)

val errors5 = errors3 ++ Errors(SimpleError.authorization)
```

****

### Removing Errors
You can remove a single error, multiple errors or the errors in an existing ```Errors``` instance from an ```Errors``` instance.

**Example**
```scala
val errors1 = Errors(CommonError.timeout, SimpleError.authorization)

val errors2 = errors1 - CommonError.timeout

// Database error will be ignored here since it does not exist in errors1 anyway.
val errors3 = errors1.removeAll(SimpleError.authorization, CommonError.database)

val errors4 = errors1 -- Errors(SimpleError.authorization)
```

****

### Checking Errors Instance for Errors
You can perform several checks on an ```Errors``` instance.

**Example**
```scala
val errors1 = Errors(CommonError.timeout, SimpleError.authorization)
val errors2 = Errors.empty

errors1.isEmpty // false
errors2.isEmpty // true

errors1.nonEmpty // true
errors2.nonEmpty // false

errors1.hasErrors // true
errors2.hasErrors // false

errors1.size // 2
errors2.size // 0

errors1.numberOfErrors // 2
errors2.numberOfErrors // 0

errors1.contains(CommonError.timeout)  // true
errors1.contains(CommonError.database) // false
errors2.contains(CommonError.timeout)  // false

// true
errors1.exists {
  case CommonError(name, _, _) if name == "timeout" => true
  case _ => false
}
```

****

### Using Maybe
There is a type alias for ```Either[Errors, V]``` defined as [```Maybe```](src/main/scala/com/github/mehmetakiftutuncu/errors/package.scala). It can be useful for error handling while accessing a value.  

**Example**
```scala
// Method will either return some Errors or an Int
def divide(n1: Int, n2: Int): Maybe[Int] = {
  if (n2 == 0) {
    // Create maybe with Errors
    Maybe(Errors(CommonError.invalidData.reason("Cannot divide by 0!")))
  } else {
    // Create Maybe with a value
    Maybe(n1 / n2)
  }
}

val result1: Maybe[Int] = divide(3, 0)
val result2: Maybe[Int] = divide(4, 2)

result1.maybeErrors // Returns Some(Errors(CommonError.invalidData.reason("Cannot divide by 0!")))
result2.maybeErrors // Returns None

result1.maybeValue // Returns None
result2.maybeValue // Returns Some(2)

result1.hasErrors // Returns true
result2.hasErrors // Returns false

result1.hasValue // Returns false
result2.hasValue // Returns true

// Methods below throw exceptions unless their criteria are met. So, use them with caution!

result1.errors // Returns Errors(CommonError.invalidData.reason("Cannot divide by 0!"))
result2.errors // Throws HasNoErrorsException!

result1.value // Throws HasNoValueException!
result2.value // Returns 2
```

****

### Representing Errors
As default implementation, ```Errors``` will use [```JsonStringErrorRepresenter```](src/main/scala/com/github/mehmetakiftutuncu/errors/representation/JsonStringErrorRepresenter.scala) and give a Json formatted string representation of all errors as an array. You may also provide your own error representer extending [```ErrorRepresenter```](src/main/scala/com/github/mehmetakiftutuncu/errors/representation/ErrorRepresenter.scala) so you can represent your errors in any way you want.

**Example**
```scala
val error1 = CommonError(name = "foo")
val error2 = CommonError(name = "foo", reason = "bar")
val error3 = CommonError(name = "foo", data = "bar")
val error4 = CommonError(name = "foo", reason = "bar", data = "baz")
val error5 = SimpleError(name = "goo")
val errors = Errors(error1, error2, error3, error4, error5)

// Output will be
// [{"name":"foo"},{"name":"foo","reason":"bar"},{"name":"foo","data":"bar"},{"name":"foo","reason":"bar","data":"baz"},{"name":"goo"}]
errors.represent(includeWhen = false)
errors.toString

// Output will be
// [{"name":"foo","when":1454271634493},{"name":"foo","reason":"bar","when":1454271634557},{"name":"foo","data":"bar","when":1454271634621},{"name":"foo","reason":"bar","data":"baz","when":1454271634680},{"name":"goo","when":1454271634737}]
errors.represent(includeWhen = true)

// Custom error representer giving true unless Errors is empty, for demonstration purposes
val customBooleanRepresenter = new ErrorRepresenter[Boolean] {
  override def represent(error: ErrorBase, includeWhen: Boolean): Boolean = true
  override def represent(errors: List[ErrorBase], includeWhen: Boolean): Boolean = if (errors.isEmpty) false else true
  override def asString(representation: Boolean): String = representation.toString
}

Errors.empty.represent(customBooleanRepresenter, includeWhen = false) // Returns false
errors.represent(customBooleanRepresenter, includeWhen = true)        // Returns true
```

You can see a real error representer example in [```JsonErrorRepresenter```](samples/PlayFrameworkExample/app/controllers/JsonErrorRepresenter.scala).

Contributing
--------------
I'd appreciate if you comment, file an issue, send pull requests. Please feel free to and do contribute.

### Contributors

* [**@forthy**](https://github.com/forthy) - Cross compilation for Scala 2.11 and 2.12

License
--------------
The MIT License (MIT), Copyright (c) 2016 Mehmet Akif Tütüncü

See [**LICENSE.md**](LICENSE.md) for details.
