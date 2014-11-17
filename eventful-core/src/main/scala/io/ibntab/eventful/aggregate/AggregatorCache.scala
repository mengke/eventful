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

import akka.actor.{Actor, ActorLogging, Props}
import io.ibntab.eventful.BaseEvent

// Created by ke.meng on 2014/11/17.

class AggregatorCache extends Actor with ActorLogging {

  override def receive: Receive = {
    case e: BaseEvent =>
      val aggregator = context.child("aggregator-" + e.group) getOrElse {
        context.actorOf(
          props = Props(classOf[Aggregator], e.group),
          name = "aggregator-" + e.group)
      }
      aggregator forward e
    case msg =>
      log.warning("An event which I can't understand is sent to me[{}]", msg)
  }
}
