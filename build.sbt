ThisBuild / scalaVersion     := "3.0.0-RC3"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.rewe-digital"
ThisBuild / organizationName := "rewe-digital"

lazy val root = (project in file("."))
  .settings(
    name := "functional-data-design",
    libraryDependencies ++= Seq(
      "dev.zio" %% "zio" % "1.0.7",
      "dev.zio" %% "zio-test" % "1.0.7" % Test,
      "dev.zio" %% "zio-prelude" % "1.0.0-RC4",
      "io.arrow-kt" % "arrow-core" % "0.13.2",
      ),
    testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework"),
    kotlincOptions ++= Seq("-jvm-target", "1.8"),
    kotlinVersion  := "1.4.21",
  )
