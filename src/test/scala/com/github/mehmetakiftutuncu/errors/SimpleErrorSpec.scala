package com.github.mehmetakiftutuncu.errors

import org.specs2.mutable.Specification

/**
  * @author Mehmet Akif Tütüncü
  */
class SimpleErrorSpec extends Specification {
  "A SimpleError" should {
    "be instantiated properly" in {
      val error: SimpleError = SimpleError(name = "foo")

      error.name mustEqual "foo"
    }
  }

  br

  "Representing a SimpleError" should {
    "represent properly" in {
      val error: SimpleError       = SimpleError(name = "\"foo\"")
      val expected: String         = s"""{"name":"\\"foo\\""}"""
      val expectedWithWhen: String = s"""{"name":"\\"foo\\"","when":${error.when}}"""

      error.represent(includeWhen = false) mustEqual expected
      error.represent(includeWhen = true)  mustEqual expectedWithWhen
    }
  }
}
