nodeLabel = "Vision1"

//////////////////////////////////////////////////////////////////////////////

def checkoutStep() {
    return {
//         node(nodeLabel) {
//             stage("Checkout on $platform") {
//                 checkout scm
//             }
//         }
        node {
          checkout scm
        }
    }
}

def createCleanAndBuildSteps() {
    return {
        node {
          bat('gradlew clean assemble')
        }
    }
}

def cleanupStep() {
    return {
//         node(nodeLabel) {
//             deleteDir()
//         }
        node {
            deleteDir()
        }
    }
}

def startPostgresStep() {
    return {
        node {
          bat('gradlew clean assemble')
        }
    }
}

def stopPostgresStep() {
    return {
        node {
          println 'stop Postgres'
        }
    }
}

try {
	stage('Checkout') {
		parallel checkoutStep
	}

	stage('Start Postgres') {
		parallel startPostgresStep
	}

	stage('Clean and assemble') {
	  parallel createCleanAndBuildSteps
	}

	stage('Stop Postgres') {
	  parallel stopPostgresStep
	}

} finally {
	stage('Clean up') {
		parallel cleanupStep
	}
}