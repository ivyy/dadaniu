import play.sbt.PlayImport._
import sbt._
import Keys._

object CommonSettings {
  val commonLibDependencies = Seq(
    cache,
    ws,
    "mysql" % "mysql-connector-java" % "5.1.34",
    "com.typesafe.play" %% "play-slick" % "1.0.1",
    "com.typesafe.play" %% "play-slick-evolutions" % "1.0.1",
    "com.h2database" % "h2" % "1.4.189",
    "io.strongtyped" % "active-slick_2.11" % "0.3.3",
    "org.scalatestplus" %% "play" % "1.4.0" % "test",
    "org.scalatest" %% "scalatest" % "2.2.6" % "test"
  )
}
