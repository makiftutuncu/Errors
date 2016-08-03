package com.github.mehmetakiftutuncu.errors

import org.specs2.mutable.Specification

/**
  * @author Mehmet Akif Tütüncü
  */
class CommonErrorSpec extends Specification {
  "A CommonError" should {
    "be instantiated properly with just name" in {
      val error: CommonError = CommonError(name = "foo")

      error.name   mustEqual "foo"
      error.reason mustEqual ""
      error.data   mustEqual ""
    }

    "be instantiated properly with just name and reason" in {
      val error: CommonError = CommonError(name = "foo", reason = "bar")

      error.name   mustEqual "foo"
      error.reason mustEqual "bar"
      error.data   mustEqual ""
    }

    "be instantiated properly with just name and data" in {
      val error: CommonError = CommonError(name = "foo", data = "bar")

      error.name   mustEqual "foo"
      error.reason mustEqual ""
      error.data   mustEqual "bar"
    }

    "be instantiated properly with all fields" in {
      val error: CommonError = CommonError(name = "foo", reason = "bar", data = "baz")

      error.name   mustEqual "foo"
      error.reason mustEqual "bar"
      error.data   mustEqual "baz"
    }
  }

  br

  "Changing reason of CommonError" should {
    "change reason properly" in {
      val before: CommonError = CommonError(name = "foo", reason = "bar")

      val after: CommonError = before.reason("baz")

      before must not equalTo after
      after.reason mustEqual "baz"
    }
  }

  br

  "Changing data of CommonError" should {
    "change data properly" in {
      val before: CommonError = CommonError(name = "foo", data = "bar")

      val after: CommonError = before.data("baz")

      before must not equalTo after
      after.data mustEqual "baz"
    }
  }

  br

  "Representing a CommonError" should {
    "represent properly with just name" in {
      val error: CommonError       = CommonError(name = "foo")
      val expected: String         = s"""{"name":"foo"}"""
      val expectedWithWhen: String = s"""{"name":"foo","when":${error.when}}"""

      error.represent(includeWhen = false) mustEqual expected
      error.represent(includeWhen = true)  mustEqual expectedWithWhen
    }

    "represent properly with just name and reason" in {
      val error: CommonError       = CommonError(name = "foo", reason = "\"bar\"")
      val expected: String         = s"""{"name":"foo","reason":"\\"bar\\""}"""
      val expectedWithWhen: String = s"""{"name":"foo","reason":"\\"bar\\"","when":${error.when}}"""

      error.represent(includeWhen = false) mustEqual expected
      error.represent(includeWhen = true)  mustEqual expectedWithWhen
    }

    "represent properly with just name and data" in {
      val error: CommonError       = CommonError(name = "foo", data = "bar")
      val expected: String         = s"""{"name":"foo","data":"bar"}"""
      val expectedWithWhen: String = s"""{"name":"foo","data":"bar","when":${error.when}}"""

      error.represent(includeWhen = false) mustEqual expected
      error.represent(includeWhen = true)  mustEqual expectedWithWhen
    }

    "represent properly with all fields" in {
      val error: CommonError       = CommonError(name = "foo", reason = "bar", data = "baz")
      val expected: String         = s"""{"name":"foo","reason":"bar","data":"baz"}"""
      val expectedWithWhen: String = s"""{"name":"foo","reason":"bar","data":"baz","when":${error.when}}"""

      error.represent(includeWhen = false) mustEqual expected
      error.represent(includeWhen = true)  mustEqual expectedWithWhen
    }
  }
}
