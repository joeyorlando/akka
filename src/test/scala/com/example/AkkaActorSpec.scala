package com.example

import org.scalatest.BeforeAndAfterEach
import org.scalatest.funspec.AnyFunSpecLike
import org.scalatest.matchers.should.Matchers
import akka.actor.ActorSystem
import com.example.messages.SetRequest
import akka.testkit.TestActorRef


class AkkaActorSpec extends AnyFunSpecLike with Matchers with BeforeAndAfterEach {
  implicit val system = ActorSystem()
  describe("akkaActor") {
    describe("given SetRequest") {
      it("should place key/value into map") {
        val actorRef = TestActorRef(new AkkaActor)
        actorRef ! SetRequest("key", "value")
        val akkaActor = actorRef.underlyingActor
        akkaActor.map.get("key") should equal(Some("value"))
      }
    }
  }
}