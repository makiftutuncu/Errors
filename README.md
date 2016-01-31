Errors
=================================

Errors is an easy-to-use library written in Scala for providing immutable, lightweight, extensible way to represent errors in your project.

To-Do
--------------
* Add this project to a Maven repository so it can easily be integrated into other projects as a library.

Examples
--------------
Since Errors is immutable, any kind of update operation (i.e. any method returning [```Errors```](src/main/scala/com/mehmetakiftutuncu/errors/Errors.scala) or any error extending [```ErrorBase```](src/main/scala/com/mehmetakiftutuncu/errors/base/ErrorBase.scala)) return a new copy with new data.

###Creating an Errors Instance
You can easily create both empty and non-empty [```Errors```](src/main/scala/com/mehmetakiftutuncu/errors/Errors.scala) instances. Just use ```empty``` or ```apply``` methods.

**Example**
```scala
// Empty errors
val errors1 = Errors.empty
val errors2 = Errors()

// Non-empty errors already containing given errors
val errors3 = Errors(CommonError.timeout, SimpleError.authorization)
```

****

###Creating Error Objects
You can create error objects to add to or remove from or even perform checks on an ```Errors``` instance. There are 2 types of errors already defined; [```CommonError```](src/main/scala/com/mehmetakiftutuncu/errors/CommonError.scala) and [```SimpleError```](src/main/scala/com/mehmetakiftutuncu/errors/SimpleError.scala). However, you can use any type of errors as long as they extend from [```ErrorBase```](src/main/scala/com/mehmetakiftutuncu/errors/base/ErrorBase.scala).

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

###Adding Errors
You can add a single error, multiple errors or the errors in an existing Errors instance to an Errors instance.

**Example**
```scala
val errors1 = Errors.empty
val errors2 = Errors(SimpleError.authorization)

val errors3 = errors1 + CommonError.timeout

val errors4 = errors1.addAll(CommonError.timeout, SimpleError.authorization)

val errors5 = errors3 ++ Errors(SimpleError.authorization)
```

****

###Removing Errors
You can remove a single error, multiple errors or the errors in an existing Errors instance from an Errors instance.

**Example**
```scala
val errors1 = Errors(CommonError.timeout, SimpleError.authorization)

val errors2 = errors1 - CommonError.timeout

// Database error will be ignored here since it does not exist in errors1 anyway.
val errors3 = errors1.removeAll(SimpleError.authorization, CommonError.database)

val errors4 = errors1 -- Errors(SimpleError.authorization)
```

****

###Checking Errors Instance for Errors
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

###Representing Errors
As default implementation, ```Errors``` will use [```JsonStringErrorRepresenter```](src/main/scala/com/mehmetakiftutuncu/errors/representation/JsonStringErrorRepresenter.scala) and give a Json formatted string representation of all errors as an array. You may also provide your own error representer extending [```ErrorRepresenter```](src/main/scala/com/mehmetakiftutuncu/errors/representation/ErrorRepresenter.scala) so you can represent your errors in any way you want.

**Example**
```scala
val error1 = CommonError(name = "foo")
val error2 = CommonError(name = "foo", reason = "bar")
val error3 = CommonError(name = "foo", data = "bar")
val error4 = CommonError(name = "foo", reason = "bar", data = "baz")
val error5 = SimpleError(name = "goo")
val errors = Errors(error1, error2, error3, error4, error5)

// Output will be
// [{"name":"foo","when":1454271634493},{"name":"foo","reason":"bar","when":1454271634557},{"name":"foo","data":"bar","when":1454271634621},{"name":"foo","reason":"bar","data":"baz","when":1454271634680},{"name":"goo","when":1454271634737}]
errors.represent()
errors.toString

// Custom error representer giving true unless Errors is empty, for demonstration purposes
val customBooleanRepresenter = new ErrorRepresenter[Boolean] {
  override def represent(error: ErrorBase): Boolean = true
  override def represent(errors: List[ErrorBase]): Boolean = if (errors.isEmpty) false else true
  override def asString(representation: Boolean): String = representation.toString
}

Errors.empty.represent(customBooleanRepresenter) // false
errors.represent(customBooleanRepresenter)       // true
```

****

To see ```Errors``` in action, you can check [```Sample```](sample/src/main/scala/Sample.scala) out in sample module.

License
--------------
The MIT License (MIT), Copyright (c) 2016 Mehmet Akif Tütüncü

See [**LICENSE.md**](LICENSE.md) for details.
