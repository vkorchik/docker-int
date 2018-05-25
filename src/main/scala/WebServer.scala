import akka.http.scaladsl.server.{HttpApp, Route}

object WebServer extends HttpApp {
  override protected def routes: Route =
    get {
      path("status") {
        complete("ok")
      } ~ {
        path("id") {
          complete(System.currentTimeMillis().toString.takeRight(3).mkString)
        }
      }
    }
}
