package pl.krakow.zio.http4s

import cats.data.Kleisli
import org.http4s._
import org.http4s.dsl.Http4sDsl
import org.http4s.implicits._
import org.http4s.server.blaze._
import zio._
import zio.interop.catz._
import zio.interop.catz.implicits._

object Main extends scala.App {
  val server: ZIO[zio.ZEnv, Throwable, Unit] =
    ZIO.runtime[ZEnv]
      .flatMap {
        implicit rts =>
          BlazeServerBuilder[Task]
            .bindHttp(8080, "localhost")
            .withHttpApp(Hello1Service.service)
            .serve
            .compile
            .drain
      }

  def run(args: List[String]): URIO[zio.ZEnv, ExitCode] =
    server.fold(_ => ExitCode.success, _ => ExitCode.failure)
}

object Hello1Service {
  private val dsl = Http4sDsl[Task]
  import dsl._

  val service: Kleisli[Task, Request[Task], Response[Task]] =
    HttpRoutes.of[Task] {
      case GET -> Root => Ok("hello!")
    }.orNotFound
}
