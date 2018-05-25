import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.stream.ActorMaterializer

import scala.concurrent.Future

object RootClient {

  implicit private val system       = ActorSystem()
  implicit private val materializer = ActorMaterializer()
  private val http = Http()

  def doRequest(request: HttpRequest): Future[HttpResponse] = {
    http.singleRequest(request)
  }
}
