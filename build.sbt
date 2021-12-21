name:="scala_meta_live_coding"
version:="1.0.0"
scalaVersion:="2.12.8"
description:="This project is scala meta live coding by pathikrit bowmick"
scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature")
libraryDependencies ++= Seq(
	"org.scalameta"%%"scalameta"%"1.8.0"%Provided,
	"org.scalatest"%%"scalatest"%"3.2.10"%Test)

addCompilerPlugin("org.scalameta" %% "paradise" % "3.0.0-M11" cross CrossVersion.full)
				  //"org.scalameta" %% "paradise" % "3.0.0-M11"

