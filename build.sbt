name := "joy-system"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  "jp.t2v" %% "play2-auth"      % "0.11.0",
  "jp.t2v" %% "play2-auth-test" % "0.11.0" % "test",
  "com.typesafe.play" %% "play-slick" % "0.6.1",
  "mysql" % "mysql-connector-java" % "5.1.36"
)     

play.Project.playScalaSettings
