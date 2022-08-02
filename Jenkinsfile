def checkoutSteps
def cleanupSteps

//////////////////////////////////////////////////////////////////////////////

def createCheckoutStep(platform) {
    return {
        node(label: platform) {
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
        node(label: platform) {
        node {
            deleteDir()
        }
    }
}

//////////////////////////////////////////////////////////////////////////////

stage('Initialize') {

	checkoutSteps = ['Checkout on Windows' : createCheckoutStep('WindowsSlave_Optimiza'),
					 'Checkout on Linux' : createCheckoutStep('linux')]

// 	buildAndTestSteps = ['Build on Windows' : createBuildStep('windows'),
// 						 'Build on Linux' : createBuildStep('linux')]
//
// 	collateSteps = ['Collate test results' : createCollateTestsSteps('windows')]
//
// 	publishSteps = ['Publish on Windows' : createPublishSteps('windows')]
//
	cleanupSteps = ['Clean up on Windows' : createCleanupStep('Vision1'),
					'Clean up on Linux' : createCleanupStep('WindowsSlave_Optimiza')]
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