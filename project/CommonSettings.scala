import play.sbt.PlayImport._
import sbt._
import Keys._

object CommonSettings {
  val commonLibDependencies = Seq(
    cache,
    ws,
    specs2 % Test,
    "mysql" % "mysql-connector-java" % "5.1.34",
    "com.typesafe.play" %% "play-slick" % "1.0.1",
    "com.typesafe.play" %% "play-slick-evolutions" % "1.0.1",
    "com.h2database" % "h2" % "1.4.189"
  )
}
