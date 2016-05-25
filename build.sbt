// https://github.com/kamon-io/Kamon/issues/348
val kamonVersion = "0.6.2-b10b1cf2013460c791d5e3f7600fc654145f3d4e"

libraryDependencies ++= Seq(
  TypesafeLibrary.akkaRemote.value,
  "io.kamon" %% "kamon-core" % kamonVersion,
  "io.kamon" %% "kamon-scala" % kamonVersion,
  "io.kamon" %% "kamon-akka" % kamonVersion,
  "io.kamon" %% "kamon-akka-remote_akka-2.4" % kamonVersion,
  "io.kamon" %% "kamon-datadog" % kamonVersion,
  "io.kamon" %% "kamon-log-reporter" % kamonVersion,
  "io.kamon" %% "kamon-system-metrics" % kamonVersion,
  "org.aspectj" % "aspectjweaver" % "1.8.5"
)

resolvers += "Kamon Repository Snapshots" at "http://snapshots.kamon.io"
