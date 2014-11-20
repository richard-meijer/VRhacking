package sentry.core

import akka.actor.Actor
import spray.routing._
import spray.http._
import MediaTypes._

class RoutesActor extends Actor with Routes {

  def actorRefFactory = context
  def receive = runRoute(myRoutes)
}

trait Routes extends HttpService
  with DefaultRoutes  
  with PingRoutes
  with PictureRoutes {

  val myRoutes = defaultRoutes ~ pingRoutes ~ pictureRoutes
}

trait DefaultRoutes extends HttpService {
  def defaultRoutes = {
    path("") {
    get {
      respondWithMediaType(`text/html`) {
        complete {
          <html>
            <body>
              <h1>Say hello to <i>spray-routing</i> on <i>spray-can</i>!</h1>
            </body>
          </html>
        }
      }
    }}
  }
}

trait PingRoutes extends HttpService {
  def pingRoutes = {
    path("ping") {
      get {
        complete("pong")
      }
    }
  }
}

trait PictureRoutes extends HttpService {

  def pictureRoutes = {
    path("picture" / """\d+""".r) { number =>
      pathEnd {
        get {
          complete("gogo picture time " + number)
        }
      }
    }
  }
}