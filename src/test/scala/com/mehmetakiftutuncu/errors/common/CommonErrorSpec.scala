package com.mehmetakiftutuncu.errors.common

import org.specs2.mutable.Specification

/**
  * @author Mehmet Akif Tütüncü
  */
class CommonErrorSpec extends Specification {
  "A CommonError" should {
    "be instantiated properly with just name" in {
      val error = CommonError(name = "foo")

      error.name   mustEqual "foo"
      error.reason mustEqual ""
      error.data   mustEqual ""
    }

    "be instantiated properly with just name and reason" in {
      val error = CommonError(name = "foo", reason = "bar")

      error.name   mustEqual "foo"
      error.reason mustEqual "bar"
      error.data   mustEqual ""
    }

    "be instantiated properly with just name and data" in {
      val error = CommonError(name = "foo", data = "bar")

      error.name   mustEqual "foo"
      error.reason mustEqual ""
      error.data   mustEqual "bar"
    }

    "be instantiated properly with all fields" in {
      val error = CommonError(name = "foo", reason = "bar", data = "baz")

      error.name   mustEqual "foo"
      error.reason mustEqual "bar"
      error.data   mustEqual "baz"
    }
  }

  br

  "Changing reason of CommonError" should {
    "change reason properly" in {
      val before = CommonError(name = "foo", reason = "bar")

      val after = before.reason("baz")

      before must not equalTo after
      after.reason mustEqual "baz"
    }
  }

  br

  "Changing data of CommonError" should {
    "change data properly" in {
      val before = CommonError(name = "foo", data = "bar")

      val after = before.data("baz")

      before must not equalTo after
      after.data mustEqual "baz"
    }
  }
}
