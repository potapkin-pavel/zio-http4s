val Http4sVersion  = "0.21.3"

lazy val root = (project in file("."))
  .settings(
    name := "zio-http4s",
    version := "0.0.1-SNAPSHOT",
    scalaVersion := "2.13.6",
    libraryDependencies ++= Seq(
      "org.http4s" %% "http4s-blaze-client" % Http4sVersion,
      "org.http4s" %% "http4s-blaze-server" % Http4sVersion,
      "org.http4s" %% "http4s-dsl"          % Http4sVersion,
      "dev.zio"    %% "zio"                 % "1.0.1",
      "dev.zio"    %% "zio-interop-cats"    % "2.5.1.0"
    )
  )

scalacOptions ++= Seq(
  "-deprecation",
  "-encoding",
  "UTF-8",
  "-language:higherKinds",
  "-language:postfixOps",
  "-feature",
  "-Xfatal-warnings"
)