import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.stream.ActorMaterializer

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.concurrent.duration._
import scala.language.postfixOps

object RootClient {

  implicit private val system: ActorSystem = ActorSystem()
  implicit private val materializer: ActorMaterializer = ActorMaterializer()
  private val http = Http()

  private def doRequest(request: HttpRequest): Future[HttpResponse] =
    http.singleRequest(
      request.copy(uri = s"http://${AppConfig.config.host}:${AppConfig.config.port}${request.uri.toString}")
    )

  def doRequest(uri: String): Future[String] = {
    doRequest(HttpRequest(uri = uri))
      .flatMap(_.entity.toStrict(10 seconds))
      .map(_.data.decodeString("UTF-8"))
  }
}
