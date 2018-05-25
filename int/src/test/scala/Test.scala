import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.whisk.docker.impl.spotify.DockerKitSpotify
import com.whisk.docker.scalatest.DockerTestKit
import org.scalatest.{Matchers, WordSpecLike}

import scala.concurrent.duration._
import scala.language.postfixOps

class Test extends Matchers
  with WordSpecLike
  with ScalatestRouteTest
  with DockerTestKit
  with DockerKitSpotify
  with DockerRootService {

  override implicit def patienceConfig: PatienceConfig = PatienceConfig(20 seconds, 100 millis)

  "Root container" should {
    "be online" in {
      isContainerReady(rootContainer).futureValue shouldBe true
      rootContainer.getPorts().futureValue.get(8182) should not be empty
      rootContainer.getIpAddresses().futureValue should not be Seq.empty

      RootClient.doRequest("/status").futureValue shouldBe "ok"
    }

    "response with random id" in {
      RootClient.doRequest("/id").futureValue.toLong should (be > 0L and be < 1000L)
    }
  }

}
