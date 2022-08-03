//////////////////////////////////////////////////////////////////////////////

//script: String, encoding?, String, returnStatus?: boolean, returnStdout?: boolean
def exec(Map args = [:]) {

    println "exec called with $args"

    if (args.returnStdout == null) {
        args.returnStdout = false
    }

    if (args.returnStdout && !args.script.startsWith('@')) {
      args.script = '@' + args.script
    }

   return bat(script: args.script, returnStdout: args.returnStdout, label: args.label)
}

//////////////////////////////////////////////////////////////////////////////

def createCheckoutStep(platform) {
    return {
//         node(label: platform) {
//             stage("Checkout on $platform") {
//                 checkout scm
//             }
//         }
        node {
          checkout scm
        }
    }
}

//////////////////////////////////////////////////////////////////////////////

def createCleanupStep(platform) {
    return {
//         node(label: platform) {
//             deleteDir()
//         }
        node {
            deleteDir()
        }
    }
}

//////////////////////////////////////////////////////////////////////////////

def createCleanAndBuild(platform) {
    return {
        node {
          def dockerContainer = startPostgres()

          bat('gradlew clean build')

          stopPostgres(dockerContainer)
        }
    }
}

//////////////////////////////////////////////////////////////////////////////

def startPostgres() {
  def image = 'postgres:10.21'
  def args = '--name postgres_slsdev_v1 -e POSTGRES_PASSWORD=Password1 -p 5432:5432'

  return dockerStart(image, args)
}

//////////////////////////////////////////////////////////////////////////////

def stopPostgres(dockerContainer) {
  return dockerStop(dockerContainer)
}

//////////////////////////////////////////////////////////////////////////////

def dockerStart(image, args, command = '') {

    println "Starting docker image $image"

    def id = exec(label: "Start docker image $image", returnStdout: true, script: "docker run -d $args $image $command").trim()
    println 'Waiting 5 seconds for container to run'
    sleep 5
    println "create new docker container id: $id"

    return ['image': image,
            'args': args,
            'id': id]
}

//////////////////////////////////////////////////////////////////////////////

def dockerExec(instance, commandLine) {
    if (instance.id != null) {
        exec(label: "docker exec $instance.image $commandLine", script: "docker exec $instance.id $commandLine")
    }
}

//////////////////////////////////////////////////////////////////////////////

def dockerStop(dockerContainer) {
  try {
    def id = dockerContainer.id
    def image = dockerContainer.image
    def args = null
    def command = null
    println 'Waiting 5 seconds for container to stop'
    println "id = $id"
    println "image = $image"

    def script1 = "docker stop $args $image $command"
    println script1

    exec(label: "Stop docker container $id", returnStdout: true, script: "docker stop $args $image $command").trim()
    println 'Waiting 5 seconds for container to stop'
    sleep 5
    exec(label: "Remove docker container $id", returnStdout: true, script: "docker rm $args $image $command").trim()
  } catch (e) {
    println "Exception thrown stopping and removing $dockerContainer.image $e.message"
  }

  return id
}

//////////////////////////////////////////////////////////////////////////////

def createPublishStep(image) {
    return {
        node {
          bat('gradlew publish')
        }
    }
}

//////////////////////////////////////////////////////////////////////////////

def checkoutSteps
def cleanupSteps
def cleanAndBuildSteps
def publishSteps

stage('Initialize') {

	checkoutSteps = ['Checkout on Windows' : createCheckoutStep('Vision1')]

	cleanAndBuildSteps = ['Clean and build on Windows' : createCleanAndBuild('Vision1')]

	publishSteps = ['Publish' : createPublishStep('Vision1')]

	cleanupSteps = ['Clean up on Windows' : createCleanupStep('Vision1')]
}

try {
	stage('Checkout') {
		parallel checkoutSteps
	}

	stage('Clean and Build') {
	  parallel cleanAndBuildSteps
	}

// 	stage('Publish') {
// 	  parallel publishSteps
// 	}

} finally {
	stage('Clean up') {
		parallel cleanupSteps
	}
}