//////////////////////////////////////////////////////////////////////////////

def createCheckoutStep(platform) {
    return {
        node(label: platform) {
            stage("Checkout on $platform") {
                checkout scm
            }
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
}

//////////////////////////////////////////////////////////////////////////////

def checkoutSteps
def cleanupSteps

stage('Initialize') {

	checkoutSteps = ['Checkout on Windows' : createCheckoutStep('Vision1')/*,
					 'Checkout on Linux' : createCheckoutStep('WindowsSlave_Optimiza')*/]

	cleanupSteps = ['Clean up on Windows' : createCleanupStep('Vision1')/*,
					'Clean up on Linux' : createCleanupStep('WindowsSlave_Optimiza')*/]
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