def checkoutSteps
def buildAndTestSteps
def collateSteps
def publishSteps
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
}

//////////////////////////////////////////////////////////////////////////////

// pipeline {
//   agent any
//   tools {
//     gradle '4.10.2'
//   }
//   stages {
//     stage('Checkout on Windows') {
//       steps {
//         checkout scm
//       }
//     }
//   }
// }

stage('Initialize') {

	checkoutSteps = ['Checkout on Windows' : createCheckoutStep('windows')]

// 	buildAndTestSteps = ['Build on Windows' : createBuildStep('windows')]
//
// 	collateSteps = ['Collate test results' : createCollateTestsSteps('windows')]
//
// 	publishSteps = ['Publish on Windows' : createPublishSteps('windows')]
//
// 	cleanupSteps = ['Clean up on Windows' : createCleanupStep('windows')]
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