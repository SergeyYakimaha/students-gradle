def checkoutSteps
def buildAndTestSteps
def collateSteps
def publishSteps
def cleanupSteps

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

stage('Initialize') {

	checkoutSteps = ['Checkout on Windows' : createCheckoutStep('windows'),
					 'Checkout on Linux' : createCheckoutStep('linux')]

// 	buildAndTestSteps = ['Build on Windows' : createBuildStep('windows')]
//
// 	collateSteps = ['Collate test results' : createCollateTestsSteps('windows')]
//
// 	publishSteps = ['Publish on Windows' : createPublishSteps('windows')]
//
	cleanupSteps = ['Clean up on Windows' : createCleanupStep('windows'),
					'Clean up on Linux' : createCleanupStep('linux')]
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