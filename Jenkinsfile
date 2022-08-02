//////////////////////////////////////////////////////////////////////////////

def createCheckoutStep(platform) {
    return {
        //node(label: platform) {
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
        //node(label: platform) {
        node {
            deleteDir()
        }
    }
}

//////////////////////////////////////////////////////////////////////////////

def checkoutSteps
def cleanupSteps

stage('Initialize') {

	checkoutSteps = ['Checkout on Windows' : createCheckoutStep('windows')/*,
					 'Checkout on Linux' : createCheckoutStep('linux')*/]

	cleanupSteps = ['Clean up on Windows' : createCleanupStep('windows')/*,
					'Clean up on Linux' : createCleanupStep('linux')*/]
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