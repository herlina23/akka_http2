name := "akka_http2"

version := "0.1"

scalaVersion := "2.12.9"


libraryDependencies += "com.typesafe.play" %% "play-json" % "2.6.7"

libraryDependencies += "joda-time" % "joda-time" % "2.10.3"

libraryDependencies += "joda-time" % "joda-time" % "2.10.3"

libraryDependencies += "com.typesafe.play" %% "play-json" % "2.6.7"

lazy val akkaVersion = "2.5.25"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  "com.typesafe.akka" %% "akka-testkit" % akkaVersion,
  "org.scalatest" %% "scalatest" % "3.0.5" % "test"
)

//libraryDependencies += "com.lightbend.akka" %% "akka-stream-alpakka-amqp" % "0.1"

libraryDependencies += "com.lightbend.akka" %% "akka-stream-alpakka-file" % "1.1.1"
libraryDependencies += "com.lightbend.akka" %% "akka-stream-alpakka-csv" % "1.1.1"


//libraryDependencies += "com.typesafe.akka" %% "akka-http"   % "10.1.3"
libraryDependencies += "com.typesafe.akka" %% "akka-http"   % "10.1.10"
libraryDependencies +="com.typesafe.akka" %% "akka-stream-kafka" % "0.21.1"
libraryDependencies += "com.typesafe.akka" %% "akka-http-spray-json" % "10.1.10"

libraryDependencies += "de.heikoseeberger" %% "akka-http-play-json" % "1.21.0"