def checkoutSteps
def cleanupSteps

//////////////////////////////////////////////////////////////////////////////

def createCheckoutStep(platform) {
    return {
        node(label: platform) {
            stage("Checkout on $platform") {
                checkout scm
        }
    }
}

//////////////////////////////////////////////////////////////////////////////

def createCleanupStep(platform) {
    return {
        node(label: platform) {
            deleteDir()
    }
}

//////////////////////////////////////////////////////////////////////////////

stage('Initialize') {

	checkoutSteps = ['Checkout on Windows' : createCheckoutStep('Vision1')]

	cleanupSteps = ['Clean up on Windows' : createCleanupStep('Vision1')]
}

try {
	stage('Checkout') {
		parallel checkoutSteps
	}

} finally {
	stage('Clean up') {
		parallel cleanupSteps
	}
}