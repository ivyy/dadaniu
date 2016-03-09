name := """dadaniu"""

lazy val commonSettings: Seq[Setting[_]] = Seq(
  organization := "com.dadaniu",
  version := "0.1",
  scalaVersion := "2.11.7",
  routesGenerator := InjectedRoutesGenerator,
  resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"
)

lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .settings(commonSettings: _*)
  .dependsOn(security, common, stock, wechat)
  .aggregate(security, common, stock, wechat)

lazy val wechat = (project in file("modules") / "wechat")
  .enablePlugins(PlayScala)
  .settings(commonSettings: _*)
  .dependsOn(common)

//lazy val admin = (project in file("modules") / "admin").enablePlugins(PlayScala)

lazy val common = (project in file("modules") / "common")
  .enablePlugins(PlayScala)
  .settings(commonSettings: _*)

lazy val security = (project in file("modules") / "security")
  .enablePlugins(PlayScala)
  .settings(commonSettings: _*)
  .dependsOn(common)

lazy val stock = (project in file("modules") / "stock")
  .enablePlugins(PlayScala)
  .settings(commonSettings: _*)
  .dependsOn(common)

libraryDependencies ++= Seq(
  cache,
  ws,
  "mysql" % "mysql-connector-java" % "5.1.34",
  "com.typesafe.play" %% "play-slick" % "1.0.1",
  "com.typesafe.play" %% "play-slick-evolutions" % "1.0.1",
  "com.h2database" % "h2" % "1.4.189"
)
