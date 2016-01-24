package com.mehmetakiftutuncu.errors.representation

import com.mehmetakiftutuncu.errors.base.ErrorBase
import com.mehmetakiftutuncu.errors.{CommonError, SimpleError}
import org.specs2.mutable.Specification

/**
  * @author Mehmet Akif Tütüncü
  */
class JsonStringErrorRepresenterSpec extends Specification {
  "Representing" >> {
    "CommonError" should {
      "represent properly with just name" in {
        val error    = CommonError(name = "foo")
        val expected = s"""{"name":"foo","when":${error.when}}"""

        JsonStringErrorRepresenter.represent(error) mustEqual expected
      }

      "represent properly with just name and reason" in {
        val error    = CommonError(name = "foo", reason = "bar")
        val expected = s"""{"name":"foo","reason":"bar","when":${error.when}}"""

        JsonStringErrorRepresenter.represent(error) mustEqual expected
      }

      "represent properly with just name and data" in {
        val error    = CommonError(name = "foo", data = "bar")
        val expected = s"""{"name":"foo","data":"bar","when":${error.when}}"""

        JsonStringErrorRepresenter.represent(error) mustEqual expected
      }

      "represent properly with all fields" in {
        val error    = CommonError(name = "foo", reason = "bar", data = "baz")
        val expected = s"""{"name":"foo","reason":"bar","data":"baz","when":${error.when}}"""

        JsonStringErrorRepresenter.represent(error) mustEqual expected
      }
    }

    br

    "SimpleError" should {
      "represent properly" in {
        val error    = SimpleError(name = "foo")
        val expected = s"""{"name":"foo","when":${error.when}}"""

        JsonStringErrorRepresenter.represent(error) mustEqual expected
      }
    }

    br

    "a list of errors" should {
      "represent empty list of errors properly" in {
        JsonStringErrorRepresenter.represent(List.empty[ErrorBase]) mustEqual "[]"
      }

      "represent properly with a list of errors properly" in {
        val error1    = CommonError(name = "foo")
        val error2    = CommonError(name = "foo", reason = "bar")
        val error3    = CommonError(name = "foo", data = "bar")
        val error4    = CommonError(name = "foo", reason = "bar", data = "baz")
        val error5    = SimpleError(name = "goo")
        val expected1 = s"""{"name":"foo","when":${error1.when}}"""
        val expected2 = s"""{"name":"foo","reason":"bar","when":${error2.when}}"""
        val expected3 = s"""{"name":"foo","data":"bar","when":${error3.when}}"""
        val expected4 = s"""{"name":"foo","reason":"bar","data":"baz","when":${error4.when}}"""
        val expected5 = s"""{"name":"goo","when":${error5.when}}"""
        val expected  = s"[$expected1,$expected2,$expected3,$expected4,$expected5]"

        JsonStringErrorRepresenter.represent(List(error1, error2, error3, error4, error5)) mustEqual expected
      }
    }
  }

  br

  "Getting representation as String" should {
    "just give representation itself" in {
      val error          = CommonError(name = "foo", reason = "bar", data = "baz")
      val representation = JsonStringErrorRepresenter.represent(error)
      val expected       = s"""{"name":"foo","reason":"bar","data":"baz","when":${error.when}}"""

      representation mustEqual expected
      JsonStringErrorRepresenter.asString(representation) mustEqual expected
    }
  }
}
