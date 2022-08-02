nodeLabel = "Vision1"

//////////////////////////////////////////////////////////////////////////////

def createCheckoutStep() {
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

def createCleanupStep() {
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
          println 'start Postgres'
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
		parallel checkoutStep()
	}

	stage('Start Postgres') {
		parallel startPostgresStep()
	}

	stage('Clean and assemble') {
	  parallel createCleanAndBuildSteps()
	}

	stage('Stop Postgres') {
	  parallel stopPostgresStep()
	}

} finally {
	stage('Clean up') {
		parallel cleanupStep()
	}
}