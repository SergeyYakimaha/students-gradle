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
          def image = 'postgres:10.21'
          def args =  '--name postgres_slsdev_v1 -e POSTGRES_PASSWORD=Password1 -p 5432:5432'
          return dockerStart(image, args)
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

def dockerStart(image, args, command = '') {

    println "Starting docker image $image"

    def id = exec(label: "Start docker image $image", returnStdout: true, script: "docker run -d $args $image $command").trim()
    println "id: $id"

    def ipAddress = '{{.NetworkSettings.Networks.nat.IPAddress}}'
    def hostname = exec(returnStdout: true, script: "docker inspect -f $ipAddress $id").trim()
    println "hostname: $hostname"

    if (hostname == '' || hostname == null) {
        exec(script: "docker inspect $id")
    }

    return ['image': image,
            'args': args,
            'id': id,
            'hostname': hostname]
}

//////////////////////////////////////////////////////////////////////////////

def dockerExec(instance, commandLine) {
    if (instance.id != null) {
        exec(label: "docker exec $instance.image $commandLine", script: "docker exec $instance.id $commandLine")
    }
}

//////////////////////////////////////////////////////////////////////////////

def dockerStop(postgres) {
  try {
    dockerExec(postgres, 'powershell Stop-Service postgresql-*')
  } catch (e) {
    println "Exception thrown stopping postgres $postgres.id $e.message"
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

// 	stage('Publish') {
// 	  parallel publishSteps
// 	}

} finally {
	stage('Clean up') {
		parallel cleanupSteps
	}
}