import com.whisk.docker.{DockerContainer, DockerKit, DockerReadyChecker}

trait DockerRootService extends DockerKit {

  val defaultPort = 8182

  val rootContainer = DockerContainer("docker-int")
    .withPorts(defaultPort -> Some(defaultPort))
    .withReadyChecker(DockerReadyChecker.LogLineContains("Server online at"))

  abstract override def dockerContainers = rootContainer :: super.dockerContainers
}
