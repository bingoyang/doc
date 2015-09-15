name := "joy-system"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  "jp.t2v" %% "play2-auth"      % "0.11.0",
  "jp.t2v" %% "play2-auth-test" % "0.11.0" % "test",
  "se.radley" %% "play-plugins-salat" % "1.5.0",
  "com.typesafe.play" %% "play-slick" % "1.0.1",
  "com.typesafe.play" %% "play-slick-evolutions" % "1.0.1",
  "mysql" % "mysql-connector-java" % "5.1.36"
)     

play.Project.playScalaSettings
