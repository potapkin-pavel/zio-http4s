package pl.krakow.zio.http4s.test

import org.http4s._
import org.http4s.implicits._
import zio._
import zio.interop.catz._
import zio.test._
import zio.test.Assertion._
import TestAspect._
import pl.krakow.zio.http4s.Hello1Service

object TestHello1Service extends DefaultRunnableSpec {
  override def spec: ZSpec[Any, Throwable] = suite("routes suite")(
    testM("root request returns Ok") {
      for {
        response <- Hello1Service.service.run(Request[Task](Method.GET, uri"/"))
      } yield assert(response.status)(equalTo(Status.Ok))
    },
    testM("root request returns Ok, using assertM") {
      assertM(Hello1Service.service.run(Request[Task](Method.GET, uri"/")).map(_.status))(
        equalTo(Status.Ok))
    },
    testM("no root request returns NotFound, using assertM") {
      assertM(Hello1Service.service.run(Request[Task](Method.GET, uri"/a")).map(_.status))(
        equalTo(Status.NotFound))
    },
    testM("root request body returns hello!") {
      val io = for {
        response <- Hello1Service.service.run(Request[Task](Method.GET, uri"/"))
        body <- response.body.compile.toVector.map(x => x.map(_.toChar).mkString(""))
      } yield body
      assertM(io)(equalTo("hello!"))
    }
  ) @@ sequential
}
