package com.github.mehmetakiftutuncu.errors.representation

import com.github.mehmetakiftutuncu.errors.{SimpleError, CommonError}
import com.github.mehmetakiftutuncu.errors.base.ErrorBase
import org.specs2.mutable.Specification

/**
  * @author Mehmet Akif Tütüncü
  */
class JsonStringErrorRepresenterSpec extends Specification {
  "Representing" >> {
    "CommonError" should {
      "represent properly with just name" in {
        val error: CommonError       = CommonError(name = "foo")
        val expected: String         = s"""{"name":"foo"}"""
        val expectedWithWhen: String = s"""{"name":"foo","when":${error.when}}"""

        JsonStringErrorRepresenter.represent(error, includeWhen = false) mustEqual expected
        JsonStringErrorRepresenter.represent(error, includeWhen = true)  mustEqual expectedWithWhen
      }

      "represent properly with just name and reason" in {
        val error: CommonError       = CommonError(name = "foo", reason = "bar")
        val expected: String         = s"""{"name":"foo","reason":"bar"}"""
        val expectedWithWhen: String = s"""{"name":"foo","reason":"bar","when":${error.when}}"""

        JsonStringErrorRepresenter.represent(error, includeWhen = false) mustEqual expected
        JsonStringErrorRepresenter.represent(error, includeWhen = true)  mustEqual expectedWithWhen
      }

      "represent properly with just name and data" in {
        val error: CommonError       = CommonError(name = "foo", data = "\"bar\"")
        val expected: String         = s"""{"name":"foo","data":"\\"bar\\""}"""
        val expectedWithWhen: String = s"""{"name":"foo","data":"\\"bar\\"","when":${error.when}}"""

        JsonStringErrorRepresenter.represent(error, includeWhen = false) mustEqual expected
        JsonStringErrorRepresenter.represent(error, includeWhen = true)  mustEqual expectedWithWhen
      }

      "represent properly with all fields" in {
        val error: CommonError       = CommonError(name = "foo", reason = "bar", data = "baz")
        val expected: String         = s"""{"name":"foo","reason":"bar","data":"baz"}"""
        val expectedWithWhen: String = s"""{"name":"foo","reason":"bar","data":"baz","when":${error.when}}"""

        JsonStringErrorRepresenter.represent(error, includeWhen = false) mustEqual expected
        JsonStringErrorRepresenter.represent(error, includeWhen = true)  mustEqual expectedWithWhen
      }
    }

    br

    "SimpleError" should {
      "represent properly" in {
        val error: SimpleError       = SimpleError(name = "foo")
        val expected: String         = s"""{"name":"foo"}"""
        val expectedWithWhen: String = s"""{"name":"foo","when":${error.when}}"""

        JsonStringErrorRepresenter.represent(error, includeWhen = false) mustEqual expected
        JsonStringErrorRepresenter.represent(error, includeWhen = true)  mustEqual expectedWithWhen
      }
    }

    br

    "a list of errors" should {
      "represent empty list of errors properly" in {
        JsonStringErrorRepresenter.represent(List.empty[ErrorBase], includeWhen = false) mustEqual "[]"
        JsonStringErrorRepresenter.represent(List.empty[ErrorBase], includeWhen = true)  mustEqual "[]"
      }

      "represent properly with a list of errors properly" in {
        val error1: CommonError = CommonError(name = "foo")
        val error2: CommonError = CommonError(name = "foo", reason = "\"bar\"")
        val error3: CommonError = CommonError(name = "foo", data = "bar")
        val error4: CommonError = CommonError(name = "foo", reason = "bar", data = "baz")
        val error5: SimpleError = SimpleError(name = "goo")
        val expected1: String   = s"""{"name":"foo"}"""
        val expected2: String   = s"""{"name":"foo","reason":"\\"bar\\""}"""
        val expected3: String   = s"""{"name":"foo","data":"bar"}"""
        val expected4: String   = s"""{"name":"foo","reason":"bar","data":"baz"}"""
        val expected5: String   = s"""{"name":"goo"}"""
        val expected: String    = s"[$expected1,$expected2,$expected3,$expected4,$expected5]"

        JsonStringErrorRepresenter.represent(List(error1, error2, error3, error4, error5), includeWhen = false) mustEqual expected
      }

      "represent properly with a list of errors properly with when" in {
        val error1: CommonError = CommonError(name = "foo")
        val error2: CommonError = CommonError(name = "foo", reason = "bar")
        val error3: CommonError = CommonError(name = "foo", data = "bar")
        val error4: CommonError = CommonError(name = "foo", reason = "bar", data = "\"baz\"")
        val error5: SimpleError = SimpleError(name = "goo")
        val expected1: String   = s"""{"name":"foo","when":${error1.when}}"""
        val expected2: String   = s"""{"name":"foo","reason":"bar","when":${error2.when}}"""
        val expected3: String   = s"""{"name":"foo","data":"bar","when":${error3.when}}"""
        val expected4: String   = s"""{"name":"foo","reason":"bar","data":"\\"baz\\"","when":${error4.when}}"""
        val expected5: String   = s"""{"name":"goo","when":${error5.when}}"""
        val expected: String    = s"[$expected1,$expected2,$expected3,$expected4,$expected5]"

        JsonStringErrorRepresenter.represent(List(error1, error2, error3, error4, error5), includeWhen = true) mustEqual expected
      }
    }
  }

  br

  "Getting representation as String" should {
    "just give representation itself" in {
      val error: CommonError             = CommonError(name = "foo", reason = "bar", data = "baz")
      val representation: String         = JsonStringErrorRepresenter.represent(error, includeWhen = false)
      val representationWithWhen: String = JsonStringErrorRepresenter.represent(error, includeWhen = true)
      val expected: String               = s"""{"name":"foo","reason":"bar","data":"baz"}"""
      val expectedWithWhen: String       = s"""{"name":"foo","reason":"bar","data":"baz","when":${error.when}}"""

      representation         mustEqual expected
      representationWithWhen mustEqual expectedWithWhen

      JsonStringErrorRepresenter.asString(representation)         mustEqual expected
      JsonStringErrorRepresenter.asString(representationWithWhen) mustEqual expectedWithWhen
    }
  }
}
