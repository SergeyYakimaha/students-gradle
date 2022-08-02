//////////////////////////////////////////////////////////////////////////////

def createCheckoutStep(platform) {
    return {
           node {
            stage("Checkout on $platform") {
                checkout scm
           }
//         node(label: platform) {
//             stage("Checkout on $platform") {
//                 checkout scm
//         }
    }
}

//////////////////////////////////////////////////////////////////////////////

def createCleanupStep(platform) {
    return {
           node {
              deleteDir()
           }
//         node(label: platform) {
//             deleteDir()
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