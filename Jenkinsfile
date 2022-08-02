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
          bat('gradlew clean assemble')
        }
    }
}

//////////////////////////////////////////////////////////////////////////////

def createStartPostgres(platform) {
    return {
        node {
          println "start Postgres"
        }
    }
}

//////////////////////////////////////////////////////////////////////////////

def createStopPostgres(platform) {
    return {
        node {
          println "stop Postgres"
        }
    }
}

def checkoutSteps
def cleanupSteps
def cleanAndBuildSteps
def startPostgresSteps
def stopPostgresSteps

stage('Initialize') {

	checkoutSteps = ['Checkout on Windows' : createCheckoutStep('Vision1')]

	startPostgresSteps = ['Start postgres' : createStartPostgres('Vision1')]

	cleanAndBuildSteps = ['Clean and assemble on Windows' : createCleanAndBuild('Vision1')]

	stopPostgresSteps = ['Stop postgres' : createStopPostgres('Vision1')]

	cleanupSteps = ['Clean up on Windows' : createCleanupStep('Vision1')]

}

try {
	stage('Checkout') {
		parallel checkoutSteps
	}

	stage('Start Postgres') {
	  parallel startPostgresSteps
	}

	stage('Clean and assemble') {
	  parallel cleanAndBuildSteps
	}

	stage('Stop stopPostgres') {
	  parallel stopPostgresSteps
	}

} finally {
	stage('Clean up') {
		parallel cleanupSteps
	}
}