//////////////////////////////////////////////////////////////////////////////

def createCheckoutStep(platform) {
    return {
//         node(label: platform) {
//             stage("Checkout on $platform") {
//                 checkout scm
//             }
//         }
        node {
            stage("Checkout on $platform") {
                checkout scm
            }
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

def checkoutSteps
def cleanupSteps
def cleanAndBuildSteps

stage('Initialize') {

	checkoutSteps = ['Checkout on Windows' : createCheckoutStep('Vision1')]

	cleanAndBuildSteps = ['Clean and assemble on Windows' : createCleanAndBuild('Vision1')]

	cleanupSteps = ['Clean up on Windows' : createCleanupStep('Vision1')]

}

try {
	stage('Checkout') {
		parallel checkoutSteps
	}

	stage('Clean and assemble') {
	  parallel cleanAndBuildSteps
	}

} finally {
	stage('Clean up') {
		parallel cleanupSteps
	}
}