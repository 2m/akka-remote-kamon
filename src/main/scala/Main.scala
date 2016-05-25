import akka.actor._
import com.typesafe.config.ConfigFactory
import scala.concurrent.duration._
import kamon._
import kamon.trace._

/**
 * Run the app with:
 *   sbt "aspectj-runner:run echo"
 *   sbt "aspectj-runner:run pinger"
 */
object Main extends App {

  Kamon.start()

  class EchoActor extends Actor with ActorLogging {
    def receive = {
      case msg => log.info("Received {}, ctx: {}", msg, Tracer.currentContext.token)
    }
  }

  class Pinger extends Actor {
    import context.dispatcher

    context.actorSelection("akka.tcp://remote-kamon@127.0.0.1:2553/user/echoActor").resolveOne(1.second).map { target =>
      context.system.scheduler.schedule(0.seconds, 1.second) {
        Tracer.withNewContext("sample-trace") {
          target ! math.random
        }
      }
    }

    def receive = {
      case msg =>
    }
  }

  val config = ConfigFactory.parseString("""
    akka {
      actor {
        provider = "akka.remote.RemoteActorRefProvider"
      }
      remote {
        enabled-transports = ["akka.remote.netty.tcp"]
        netty.tcp {
          hostname = "127.0.0.1"
        }
      }
    }

    kamon.metric.filters {
      akka-actor {
        includes = [ "remote-kamon/user/echoActor" ]
        excludes = [ "remote-kamon/system/**" ]
      }

      akka-dispatcher {
        includes = [ "remote-kamon/akka.actor.default-dispatcher" ]
      }
    }
  """)

  val (port: Int, start) = args.head.toLowerCase match {
    case "pinger" => (2552, () => sys.actorOf(Props(new Pinger)))
    case "echo" => (2553, () => sys.actorOf(Props(new EchoActor), "echoActor"))
  }

  val sys = ActorSystem("remote-kamon", ConfigFactory.parseString(s"akka.remote.netty.tcp.port = $port").withFallback(config))

  start()
}
