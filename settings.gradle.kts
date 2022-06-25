rootProject.name = "multimodule"
val modulesRootFolder = "modules"

include("webservice")
include("database")
include("messaging")
include("cron-jobs")
include("api")
include("business")
include("third-party")

project(":webservice").projectDir = file("$modulesRootFolder/webservice")
project(":database").projectDir = file("$modulesRootFolder/database")
project(":messaging").projectDir = file("$modulesRootFolder/messaging")
project(":cron-jobs").projectDir = file("$modulesRootFolder/cron-jobs")
project(":api").projectDir = file("$modulesRootFolder/api")
project(":business").projectDir = file("$modulesRootFolder/business")
project(":third-party").projectDir = file("$modulesRootFolder/third-party")
