package com.github.mehmetakiftutuncu.errors

import com.github.mehmetakiftutuncu.errors.exceptions.{HasNoErrorsException, HasNoValueException}
import org.specs2.mutable.Specification

/**
  * @author Mehmet Akif Tütüncü
  */
class MaybeSpec extends Specification {
  "A Maybe" should {
    "be instantiated properly with an Errors" in {
      val errors: Errors       = Errors(CommonError.invalidRequest)
      val maybe: Maybe[String] = Maybe[String](errors)

      maybe.errors mustEqual errors
    }

    "be instantiated properly with a value" in {
      val value: String        = "foo"
      val maybe: Maybe[String] = Maybe[String](value)

      maybe.value mustEqual value
    }
  }

  br

  "Safely getting errors from a Maybe" should {
    "return Some(errors) when Maybe has errors in it" in {
      val errors: Errors       = Errors(CommonError.invalidRequest)
      val maybe: Maybe[String] = Maybe[String](errors)

      maybe.maybeErrors must beSome[Errors](errors)
    }

    "return None when Maybe has a value in it" in {
      val maybe: Maybe[String] = Maybe[String]("foo")

      maybe.maybeErrors must beNone
    }
  }

  br

  "Safely getting value from a Maybe" should {
    "return Some(value) when Maybe has a value in it" in {
      val value: String        = "foo"
      val maybe: Maybe[String] = Maybe[String](value)

      maybe.maybeValue must beSome[String](value)
    }

    "return None when Maybe has errors in it" in {
      val maybe: Maybe[String] = Maybe[String](Errors(CommonError.invalidRequest))

      maybe.maybeValue must beNone
    }
  }

  br

  "Getting errors from a Maybe" should {
    "return errors when Maybe has errors in it" in {
      val errors: Errors       = Errors(CommonError.invalidRequest)
      val maybe: Maybe[String] = Maybe[String](errors)

      maybe.errors mustEqual errors
    }

    "throw a HasNoErrorsException when Maybe has a value in it" in {
      val maybe: Maybe[String] = Maybe[String]("foo")

      maybe.errors must throwA[HasNoErrorsException[String]]
    }
  }

  br

  "Getting value from a Maybe" should {
    "return value when Maybe has a value in it" in {
      val value: String        = "foo"
      val maybe: Maybe[String] = Maybe[String](value)

      maybe.value mustEqual value
    }

    "throw a HasNoValueException when Maybe has errors in it" in {
      val maybe: Maybe[String] = Maybe[String](Errors(CommonError.invalidRequest))

      maybe.value must throwA[HasNoValueException[String]]
    }
  }

  br

  "Checking if Maybe has errors" should {
    "return true when Maybe has errors in it" in {
      val errors: Errors       = Errors(CommonError.invalidRequest)
      val maybe: Maybe[String] = Maybe[String](errors)

      maybe.hasErrors must beTrue
    }

    "return false when Maybe has a value in it" in {
      val maybe: Maybe[String] = Maybe[String]("foo")

      maybe.hasErrors must beFalse
    }
  }

  br

  "Checking if Maybe has a value" should {
    "return true when Maybe has a value in it" in {
      val value: String        = "foo"
      val maybe: Maybe[String] = Maybe[String](value)

      maybe.hasValue must beTrue
    }

    "return false when Maybe has errors in it" in {
      val maybe: Maybe[String] = Maybe[String](Errors(CommonError.invalidRequest))

      maybe.hasValue must beFalse
    }
  }
}
