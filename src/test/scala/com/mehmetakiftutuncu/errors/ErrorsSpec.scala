package com.mehmetakiftutuncu.errors

import com.mehmetakiftutuncu.errors.base.ErrorBase
import com.mehmetakiftutuncu.errors.representation.{ErrorRepresenter, JsonStringErrorRepresenter}
import org.specs2.mutable.Specification

/**
  * @author Mehmet Akif Tütüncü
  */
class ErrorsSpec extends Specification {
  "Adding an error" should {
    "add a CommonError to empty Errors properly" in {
      val error    = CommonError("foo")
      val errors   = Errors.empty
      val expected = Errors(error)

      (errors + error) mustEqual expected
    }

    "add a SimpleError to empty Errors properly" in {
      val error    = SimpleError("foo")
      val errors   = Errors.empty
      val expected = Errors(error)

      (errors + error) mustEqual expected
    }

    "add a CommonError to non-empty Errors properly" in {
      val error1   = CommonError("foo")
      val error2   = CommonError("bar")
      val errors   = Errors(error1)
      val expected = Errors(error1, error2)

      (errors + error2) mustEqual expected
    }

    "add a SimpleError to non-empty Errors properly" in {
      val error1   = CommonError("foo")
      val error2   = SimpleError("bar")
      val errors   = Errors(error1)
      val expected = Errors(error1, error2)

      (errors + error2) mustEqual expected
    }
  }

  br

  "Adding multiple errors" should {
    "add multiple CommonErrors to empty Errors properly" in {
      val error1   = CommonError("foo")
      val error2   = CommonError("bar")
      val errors   = Errors.empty
      val expected = Errors(error1, error2)

      errors.addAll(error1, error2) mustEqual expected
    }

    "add multiple SimpleErrors to empty Errors properly" in {
      val error1   = SimpleError("foo")
      val error2   = SimpleError("bar")
      val errors   = Errors.empty
      val expected = Errors(error1, error2)

      errors.addAll(error1, error2) mustEqual expected
    }

    "add multiple CommonErrors to non-empty Errors properly" in {
      val error1   = CommonError("foo")
      val error2   = CommonError("bar")
      val error3   = CommonError("baz")
      val errors   = Errors(error1)
      val expected = Errors(error1, error2, error3)

      errors.addAll(error2, error3) mustEqual expected
    }

    "add multiple SimpleErrors to non-empty Errors properly" in {
      val error1   = SimpleError("foo")
      val error2   = SimpleError("bar")
      val error3   = SimpleError("baz")
      val errors   = Errors(error1)
      val expected = Errors(error1, error2, error3)

      errors.addAll(error2, error3) mustEqual expected
    }

    "add multiple types of errors to non-empty Errors properly" in {
      val error1   = SimpleError("foo")
      val error2   = CommonError("bar")
      val error3   = SimpleError("baz")
      val errors   = Errors(error1)
      val expected = Errors(error1, error2, error3)

      errors.addAll(error2, error3) mustEqual expected
    }
  }

  br

  "Adding errors in another Errors" should {
    "add errors in another Errors to empty Errors properly" in {
      val error1    = CommonError("foo")
      val error2    = SimpleError("bar")
      val errors1   = Errors.empty
      val errors2   = Errors(error1, error2)
      val expected  = Errors(error1, error2)

      (errors1 ++ errors2) mustEqual expected
    }

    "add errors in another Errors to non-empty Errors properly" in {
      val error1    = SimpleError("foo")
      val error2    = CommonError("bar")
      val error3    = SimpleError("baz")
      val errors1   = Errors(error1)
      val errors2   = Errors(error2, error3)
      val expected  = Errors(error1, error2, error3)

      (errors1 ++ errors2) mustEqual expected
    }
  }

  br

  "Removing an error" should {
    "remove a CommonError from empty Errors properly" in {
      val error    = CommonError("foo")
      val errors   = Errors.empty
      val expected = Errors.empty

      (errors - error) mustEqual expected
    }

    "remove a SimpleError from empty Errors properly" in {
      val error    = SimpleError("foo")
      val errors   = Errors.empty
      val expected = Errors.empty

      (errors - error) mustEqual expected
    }

    "remove a CommonError from non-empty Errors containing the CommonError properly" in {
      val error1   = CommonError("foo")
      val error2   = CommonError("bar")
      val errors   = Errors(error1, error2)
      val expected = Errors(error1)

      (errors - error2) mustEqual expected
    }

    "remove a CommonError from non-empty Errors not containing the CommonError properly" in {
      val error1   = CommonError("foo")
      val error2   = SimpleError("bar")
      val errors   = Errors(error1, error2)
      val expected = Errors(error1, error2)

      (errors - CommonError("baz")) mustEqual expected
    }

    "remove a SimpleError from non-empty Errors containing the SimpleError properly" in {
      val error1   = SimpleError("foo")
      val error2   = SimpleError("bar")
      val errors   = Errors(error1, error2)
      val expected = Errors(error1)

      (errors - error2) mustEqual expected
    }

    "remove a SimpleError from non-empty Errors not containing the SimpleError properly" in {
      val error1   = CommonError("foo")
      val error2   = SimpleError("bar")
      val errors   = Errors(error1, error2)
      val expected = Errors(error1, error2)

      (errors - SimpleError("baz")) mustEqual expected
    }
  }

  br

  "Removing multiple errors" should {
    "remove multiple CommonErrors from empty Errors properly" in {
      val error1   = CommonError("foo")
      val error2   = CommonError("bar")
      val errors   = Errors.empty
      val expected = Errors.empty

      errors.removeAll(error1, error2) mustEqual expected
    }

    "remove multiple SimpleErrors from empty Errors properly" in {
      val error1   = SimpleError("foo")
      val error2   = SimpleError("bar")
      val errors   = Errors.empty
      val expected = Errors.empty

      errors.removeAll(error1, error2) mustEqual expected
    }

    "remove multiple CommonErrors from non-empty Errors containing all of given CommonErrors properly" in {
      val error1   = CommonError("foo")
      val error2   = CommonError("bar")
      val error3   = CommonError("baz")
      val errors   = Errors(error1, error2, error3)
      val expected = Errors(error1)

      errors.removeAll(error2, error3) mustEqual expected
    }

    "remove multiple SimpleErrors from non-empty Errors containing all of given SimpleErrors properly" in {
      val error1   = SimpleError("foo")
      val error2   = SimpleError("bar")
      val error3   = SimpleError("baz")
      val errors   = Errors(error1, error2, error3)
      val expected = Errors(error1)

      errors.removeAll(error2, error3) mustEqual expected
    }

    "remove multiple CommonErrors from non-empty Errors containing some of given CommonErrors properly" in {
      val error1   = CommonError("foo")
      val error2   = CommonError("bar")
      val error3   = CommonError("baz")
      val errors   = Errors(error1, error2, error3)
      val expected = Errors(error1, error3)

      errors.removeAll(error2, CommonError("goo")) mustEqual expected
    }

    "remove multiple SimpleErrors from non-empty Errors containing some of given SimpleErrors properly" in {
      val error1   = SimpleError("foo")
      val error2   = SimpleError("bar")
      val error3   = SimpleError("baz")
      val errors   = Errors(error1, error2, error3)
      val expected = Errors(error1, error3)

      errors.removeAll(error2, SimpleError("goo")) mustEqual expected
    }
  }

  br

  "Removing errors in another Errors" should {
    "remove errors in another Errors from empty Errors properly" in {
      val error1   = CommonError("foo")
      val error2   = SimpleError("bar")
      val errors1  = Errors.empty
      val errors2  = Errors(error1, error2)
      val expected = Errors.empty

      (errors1 -- errors2) mustEqual expected
    }

    "remove errors in another Errors from non-empty Errors properly" in {
      val error1    = CommonError("foo")
      val error2    = SimpleError("bar")
      val error3    = CommonError("baz")
      val errors1   = Errors(error1, error2, error3)
      val errors2   = Errors(error2, error3)
      val expected  = Errors(error1)

      (errors1 -- errors2) mustEqual expected
    }

    "remove empty Errors from non-empty Errors properly" in {
      val error1    = SimpleError("foo")
      val error2    = CommonError("bar")
      val error3    = SimpleError("baz")
      val errors1   = Errors(error1, error2, error3)
      val errors2   = Errors.empty
      val expected  = Errors(error1, error2, error3)

      (errors1 -- errors2) mustEqual expected
    }
  }

  br

  "Checking if Errors is empty" should {
    "return true for empty Errors" in {
      Errors.empty.isEmpty must beTrue
    }

    "return false for non-empty Errors" in {
      Errors(CommonError("foo")).isEmpty must beFalse
    }
  }

  br

  "Checking if Errors is non-empty" should {
    "return false for empty Errors" in {
      Errors.empty.nonEmpty must beFalse
    }

    "return true for non-empty Errors" in {
      Errors(CommonError("foo")).nonEmpty must beTrue
    }
  }

  br

  "Checking if Errors has errors" should {
    "return false for empty Errors" in {
      Errors.empty.hasErrors must beFalse
    }

    "return true for non-empty Errors" in {
      Errors(CommonError("foo")).hasErrors must beTrue
    }
  }

  br

  "Getting Errors' size" should {
    "return 0 for empty Errors" in {
      Errors.empty.size mustEqual 0
    }

    "return number of errors for non-empty Errors properly" in {
      Errors(CommonError("foo"), SimpleError("bar")).size mustEqual 2
    }
  }

  br

  "Getting number of errors in Errors" should {
    "return 0 for empty Errors" in {
      Errors.empty.size mustEqual 0
    }

    "return number of errors for non-empty Errors properly" in {
      Errors(CommonError("foo"), SimpleError("bar")).size mustEqual 2
    }
  }

  br

  "Checking if Errors contains an error" should {
    "return false for empty Errors" in {
      Errors.empty.contains(CommonError("foo")) must beFalse
    }

    "return false when non-empty Errors does not contain given error" in {
      Errors(CommonError("foo"), SimpleError("bar")).contains(CommonError("baz")) must beFalse
    }

    "return true when non-empty Errors contains given error" in {
      Errors(CommonError("foo"), SimpleError("bar")).contains(SimpleError("bar")) must beTrue
    }
  }

  br

  "Checking if an error exists in Errors" should {
    "return false for empty Errors" in {
      Errors.empty.exists(e => e == CommonError("foo")) must beFalse
    }

    "return false when a non-empty Errors does not contain such an error" in {
      Errors(CommonError("foo"), SimpleError("bar")).exists(e => e == CommonError("baz")) must beFalse
    }

    "return true when a non-empty Errors contains such an error" in {
      Errors(CommonError("foo"), SimpleError("bar")).exists(e => e == SimpleError("bar")) must beTrue
    }
  }

  br

  "Representing" should {
    "represent empty Errors properly with given representer" in {
      Errors.empty(JsonStringErrorRepresenter).represent mustEqual "[]"
    }

    "represent non-empty Errors properly with given representer" in {
      val error1   = CommonError("foo")
      val error2   = CommonError("foo", "bar")
      val error3   = CommonError("foo", "bar", "baz")
      val error4   = SimpleError("boo")
      val errors   = Errors(JsonStringErrorRepresenter, error1, error2, error3, error4)
      val expected = s"""[{"name":"foo","when":${error1.when}},{"name":"foo","reason":"bar","when":${error2.when}},{"name":"foo","reason":"bar","data":"baz","when":${error3.when}},{"name":"boo","when":${error4.when}}]"""

      errors.represent mustEqual expected
    }
  }

  br

  "Creating new Errors" should {
    val representer = new ErrorRepresenter[String] {
      override def represent(error: ErrorBase): String = "foo"
      override def represent(errors: List[ErrorBase]): String = "bar"
      override def asString(representation: String): String = "baz"
    }

    "create empty Errors with given representer properly" in {
      val errors1 = Errors(representer)
      errors1.isEmpty must beTrue
      errors1.representer mustEqual representer
      errors1.represent mustEqual "bar"
      errors1.toString  mustEqual "baz"

      val errors2 = Errors.empty(representer)
      errors2.isEmpty must beTrue
      errors2.representer mustEqual representer
      errors2.represent mustEqual "bar"
      errors2.toString  mustEqual "baz"
    }

    "create empty Errors with JsonStringErrorRepresenter properly when representer is not given" in {
      val errors1 = Errors()
      errors1.isEmpty must beTrue
      errors1.representer mustEqual JsonStringErrorRepresenter
      errors1.represent   mustEqual "[]"
      errors1.toString    mustEqual "[]"

      val errors2 = Errors.empty
      errors2.isEmpty must beTrue
      errors2.representer mustEqual JsonStringErrorRepresenter
      errors2.represent   mustEqual "[]"
      errors2.toString    mustEqual "[]"
    }

    "create non-empty Errors with given representer properly" in {
      val errors = Errors(representer, SimpleError("foo"), CommonError("bar", "baz"))

      errors.nonEmpty must beTrue
      errors.representer mustEqual representer
      errors.represent   mustEqual "bar"
      errors.toString    mustEqual "baz"
    }

    "create non-empty Errors with JsonStringErrorRepresenter properly when representer is not given" in {
      val error1 = SimpleError("foo")
      val error2 = CommonError("bar", "baz")
      val errors   = Errors(error1, error2)
      val expected = s"""[{"name":"foo","when":${error1.when}},{"name":"bar","reason":"baz","when":${error2.when}}]"""

      errors.nonEmpty must beTrue
      errors.representer mustEqual JsonStringErrorRepresenter
      errors.represent   mustEqual expected
      errors.toString    mustEqual expected
    }
  }
}
