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

object Dependencies {

  object Versions {
    val scalaVersion = sys.props.get("eventful.scalaVersion").getOrElse("2.11.4")
    val akkaVersion = sys.props.get("eventful.akkaVersion").getOrElse("2.3.6")
    val scalaTestVersion = sys.props.get("eventful.scalaTestVersion").getOrElse("2.2.2")
    val scalaCheckVersion = sys.props.get("eventful.scalaCheckVersion").getOrElse("1.11.6")
  }

  val resolutionRepos = Seq(
    "Typesafe Releases" at "http://repo.typesafe.com/typesafe/releases/"
  )

  def compile   (deps: ModuleID*): Seq[ModuleID] = deps map (_ % "compile")
  def provided  (deps: ModuleID*): Seq[ModuleID] = deps map (_ % "provided")
  def test      (deps: ModuleID*): Seq[ModuleID] = deps map (_ % "test")
  def runtime   (deps: ModuleID*): Seq[ModuleID] = deps map (_ % "runtime")
  def container (deps: ModuleID*): Seq[ModuleID] = deps map (_ % "container")

  import Versions._

  val akkaActor     = "com.typesafe.akka"                       %%  "akka-actor"                  % akkaVersion
  val akkaSlf4j     = "com.typesafe.akka"                       %%  "akka-slf4j"                  % akkaVersion
  val akkaTestKit   = "com.typesafe.akka"                       %%  "akka-testkit"                % akkaVersion
  val scalatest     = "org.scalatest"                           %%  "scalatest"                   % scalaTestVersion  // ApacheV2
  val scalacheck    = "org.scalacheck"                          %%  "scalacheck"                  % scalaCheckVersion // New BSD
  val logback       = "ch.qos.logback"                          %   "logback-classic"             % "1.1.2" excludeAll ExclusionRule(organization = "org.slf4j")
}