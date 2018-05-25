import com.whisk.docker.{DockerContainer, DockerKit, DockerReadyChecker}

import scala.concurrent.duration._
import scala.language.postfixOps

trait DockerRootService extends DockerKit {

  val defaultPort = 8182

  val rootContainer = DockerContainer("docker-int")
    .withPorts(defaultPort -> Some(defaultPort))
    .withReadyChecker(
      DockerReadyChecker.HttpResponseCode(defaultPort, "/status")
        .within(100 millis)
        .looped(20, 1 second)
    )

  abstract override def dockerContainers = rootContainer :: super.dockerContainers
}
