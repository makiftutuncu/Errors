package com.mehmetakiftutuncu.errors

import org.specs2.mutable.Specification

/**
  * @author Mehmet Akif Tütüncü
  */
class SimpleErrorSpec extends Specification {
  "A SimpleError" should {
    "be instantiated properly" in {
      val error = SimpleError(name = "foo")

      error.name mustEqual "foo"
    }
  }

  br

  "Representing a SimpleError" should {
    "represent properly" in {
      val error    = SimpleError(name = "foo")
      val expected = s"""{"name":"foo","when":${error.when}}"""

      error.represent() mustEqual expected
    }
  }
}
