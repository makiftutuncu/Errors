package com.mehmetakiftutuncu.errors.representation

import com.mehmetakiftutuncu.errors.base.ErrorBase
import com.mehmetakiftutuncu.errors.common.CommonError
import org.specs2.mutable.Specification

/**
  * @author Mehmet Akif Tütüncü
  */
class JsonStringRepresenterSpec extends Specification {
  "Representing" >> {
    "CommonError" should {
      "represent properly with just name" in {
        val error    = CommonError(name = "foo")
        val expected = """{"name":"foo"}"""

        JsonStringRepresenter.represent(error) mustEqual expected
      }

      "represent properly with just name and reason" in {
        val error    = CommonError(name = "foo", reason = "bar")
        val expected = """{"name":"foo","reason":"bar"}"""

        JsonStringRepresenter.represent(error) mustEqual expected
      }

      "represent properly with just name and data" in {
        val error    = CommonError(name = "foo", data = "bar")
        val expected = """{"name":"foo","data":"bar"}"""

        JsonStringRepresenter.represent(error) mustEqual expected
      }

      "represent properly with all fields" in {
        val error    = CommonError(name = "foo", reason = "bar", data = "baz")
        val expected = """{"name":"foo","reason":"bar","data":"baz"}"""

        JsonStringRepresenter.represent(error) mustEqual expected
      }
    }

    br

    "an unknown error type" should {
      "throw a NotImplementedError" in {
        val error = new ErrorBase {}

        JsonStringRepresenter.represent(error) must throwA[NotImplementedError]
      }
    }

    br

    "a list of errors" should {
      "represent empty list of errors properly" in {
        JsonStringRepresenter.represent(List.empty[ErrorBase]) mustEqual "[]"
      }

      "represent properly with a list of CommonError properly" in {
        val error1    = CommonError(name = "foo")
        val error2    = CommonError(name = "foo", reason = "bar")
        val error3    = CommonError(name = "foo", data = "bar")
        val error4    = CommonError(name = "foo", reason = "bar", data = "baz")
        val expected1 = """{"name":"foo"}"""
        val expected2 = """{"name":"foo","reason":"bar"}"""
        val expected3 = """{"name":"foo","data":"bar"}"""
        val expected4 = """{"name":"foo","reason":"bar","data":"baz"}"""
        val expected  = s"[$expected1,$expected2,$expected3,$expected4]"

        JsonStringRepresenter.represent(List(error1, error2, error3, error4)) mustEqual expected
      }
    }
  }

  br

  "Getting representation as String" should {
    "just give representation itself" in {
      val error          = CommonError(name = "foo", reason = "bar", data = "baz")
      val representation = JsonStringRepresenter.represent(error)
      val expected       = """{"name":"foo","reason":"bar","data":"baz"}"""

      representation mustEqual expected
      JsonStringRepresenter.asString(representation) mustEqual expected
    }
  }
}
