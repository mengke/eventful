/*
 * Copyright 2014 mengke@me.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import sbt._
import Keys._

object Build extends Build {
  import Dependencies._


  // -------------------------------------------------------------------------------------------------------------------
  // Root Project
  // -------------------------------------------------------------------------------------------------------------------

  lazy val root = Project("Eventful",file("."))
    .aggregate(eventfulCore, eventfulCoreTests)
    .settings(basicSettings: _*)
    .settings(noPublishing: _*)

  // -------------------------------------------------------------------------------------------------------------------
  // Modules
  // -------------------------------------------------------------------------------------------------------------------

  lazy val eventfulCore = Project("eventful-core",file("eventful-core"))
    .settings(basicSettings: _*)
    .settings(libraryDependencies ++=
      compile(akkaActor) ++
      compile(akkaSlf4j) ++
      runtime(logback)
    )

  lazy val eventfulCoreTests = Project("eventful-core-tests",file("eventful-core-tests"))
    .dependsOn(eventfulCore)
    .settings(basicSettings: _*)
    .settings(libraryDependencies ++=
      test(akkaTestKit) ++
      test(scalatest) ++
      test(scalacheck) ++
      test(logback)
    )

  lazy val basicSettings = Seq(
    version               := "0.1.0",
    organization          := "io.ibntab",
    organizationHomepage  := Some(new URL("http://mengke.github.io/")),
    description           := "A suite of lightweight Scala libraries for aggregating, routing and grouping events " +
                             "as well as generating new events based on incoming events.",
    startYear             := Some(2014),
    scalaVersion          := Dependencies.Versions.scalaVersion,
    resolvers             ++= Dependencies.resolutionRepos,
    licenses              := Seq("Apache 2" -> new URL("http://www.apache.org/licenses/LICENSE-2.0.txt")),
    scalacOptions         := Seq(
      "-encoding", "utf8",
      "-feature",
      "-unchecked",
      "-deprecation",
      "-language:_",
      "-Xlog-reflective-calls"
    )
  )

  lazy val noPublishing = Seq(
    publish := (),
    publishLocal := ()
  )

}