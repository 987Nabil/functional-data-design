ThisBuild / scalaVersion     := "3.0.0"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.rewe-digital"
ThisBuild / organizationName := "rewe-digital"

lazy val root = (project in file("."))
  .settings(
    name := "functional-data-design",
    libraryDependencies ++= Seq(
      ("dev.zio" %% "zio" % "1.0.7").cross(CrossVersion.for3Use2_13),
      ("dev.zio" %% "zio-test" % "1.0.7" % Test).cross(CrossVersion.for3Use2_13),
      ("dev.zio" %% "zio-prelude" % "1.0.0-RC4").cross(CrossVersion.for3Use2_13),
      "io.arrow-kt" % "arrow-core" % "0.13.2",
      ),
    testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework"),
    kotlincOptions ++= Seq("-jvm-target", "11"),
    kotlinVersion  := "1.5.0",
    kotlinLib("stdlib"),
  )
