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

package io.ibntab.eventful.aggregate

import akka.actor.{Actor, ActorLogging}
import io.ibntab.eventful.aggregate.Aggregator.AggregateStrategy
import io.ibntab.eventful.{BaseEvent, EventType}

import scala.collection.mutable


// Created by ke.meng on 2014/11/17.

class Aggregator(group: String, strategy: AggregateStrategy) extends Actor with ActorLogging {

  val results = mutable.HashMap.empty[EventType, Any]

  override def receive: Receive = {
    case BaseEvent(d, g, t) =>
      require(g == group, s"OMG, the event hub send me a wrong event belongs to other group[$g]")
      results += (t -> d)
      strategy(results) match {
        case Some(data) =>
          // send to sb.
        case None =>
          log.info("A new event [group:{}, type:{}] was received, but it was not able to be" +
            " aggregated with other events", g, t)
      }
  }
}

object Aggregator {
  type AggregateStrategy[Data] = (mutable.Map[EventType, Any] => Option[Data])
}