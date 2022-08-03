//////////////////////////////////////////////////////////////////////////////

//script: String, encoding?, String, returnStatus?: boolean, returnStdout?: boolean
def exec(Map args = [:]) {

    println "exec called with $args"

    if (args.returnStdout == null) {
        args.returnStdout = false
    }

    if (isUnix()) {
        return sh(script: args.script, returnStdout: args.returnStdout, label: args.label)

    } else {
        if (args.returnStdout && !args.script.startsWith('@')) {
            args.script = '@' + args.script
        }

        return bat(script: args.script, returnStdout: args.returnStdout, label: args.label)
    }
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
          bat('gradlew clean build')
        }
    }
}

//////////////////////////////////////////////////////////////////////////////

def createStartPostgres(platform) {
    return {
        node {
          def image = '7cab63bfd74a'
          return dockerStart(image)
        }
    }
}

//////////////////////////////////////////////////////////////////////////////

def createStopPostgres(platform) {
    return {
        node {
          def image = '7cab63bfd74a'
          return dockerStop(image)
        }
    }
}

//////////////////////////////////////////////////////////////////////////////

def dockerStart(image, args = '', command = '') {

    println "Starting docker image $image"

    def id = exec(label: "Start docker image $image", returnStdout: true, script: "docker start $image").trim()
    println "id: $id"

}

//////////////////////////////////////////////////////////////////////////////

def dockerStop(image) {
  println "Stopping $image"
  try {
    exec(label: "Stop docker image $image", script: "docker stop $image")
  } catch (e) {
    println "Exception thrown stopping docker instance $image $e.message"
  }
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
def startPostgresSteps
def stopPostgresSteps
def publishSteps

stage('Initialize') {

	checkoutSteps = ['Checkout on Windows' : createCheckoutStep('Vision1')]

	startPostgresSteps = ['Start postgres' : createStartPostgres('Vision1')]

	cleanAndBuildSteps = ['Clean and build on Windows' : createCleanAndBuild('Vision1')]

	stopPostgresSteps = ['Stop postgres' : createStopPostgres('Vision1')]

	publishSteps = ['Publish' : createPublishStep('Vision1')]

	cleanupSteps = ['Clean up on Windows' : createCleanupStep('Vision1')]

}

try {
	stage('Checkout') {
		parallel checkoutSteps
	}

	stage('Start Postgres') {
	  parallel startPostgresSteps
	}

	stage('Clean and Build') {
	  parallel cleanAndBuildSteps
	}

	stage('Stop Postgres') {
	  parallel stopPostgresSteps
	}

	stage('Publish') {
	  parallel publishSteps
	}

} finally {
	stage('Clean up') {
		parallel cleanupSteps
	}
}