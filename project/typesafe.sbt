// Update this when a new patch of Reactive Platform is available
val rpVersion = "16s01p07"

// Update this when a major version of Reactive Platform is available
val rpUrl = "https://repo.typesafe.com/typesafe/for-subscribers-only/7B885384C2F0904E32AA8CEBDB634710AF3DC819"

addSbtPlugin("com.typesafe.rp" % "sbt-typesafe-rp" % rpVersion)

// The resolver name must start with typesafe-rp
resolvers += "typesafe-rp-mvn" at rpUrl

// The resolver name must start with typesafe-rp
resolvers += Resolver.url("typesafe-rp-ivy", url(rpUrl))(Resolver.ivyStylePatterns)
