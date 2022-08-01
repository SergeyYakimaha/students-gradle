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

def sequential(stageMap) {
    stageMap.each { k, v ->
        println "Executing step $k"

        v.call()
    }
}

//////////////////////////////////////////////////////////////////////////////

def createBuildAndTestStepsForProject(platform, project) {
    def shortName = calcShortName(project)

    return {}

//     return {
//         stage("Build $shortName") {
//             dir ("${project}") {
//                 execGradle('assemble')
//             }
//         }
//
//         stage("Test $shortName") {
//             dir ("${project}") {
//                 try {
//                     execGradle('test jacocoTestReport')
//
// 				} catch (e) {
// 					currentBuild.result = 'UNSTABLE'
//
//                 } finally {
// 					stash name: "$platform-$shortName-testresults", includes: "build/test-results/**/*.xml", allowEmpty: true
// 					stash name: "$platform-$shortName-jacoco", includes: "build/jacoco/*", allowEmpty: true
//                 }
//             }
//         }
//     }
}

//////////////////////////////////////////////////////////////////////////////

def buildAndTest(platform) {

    def steps = projects.collectEntries {
        [(calcShortName(it) + " on $platform") : createBuildAndTestStepsForProject(platform, it)]
    }

    //parallel steps
    sequential steps
}

//////////////////////////////////////////////////////////////////////////////

stage('Initialize') {

	checkoutSteps = ['Checkout on Windows' : createCheckoutStep('windows'),
					 'Checkout on Linux' : createCheckoutStep('linux')]

	buildAndTestSteps = ['Build on Windows' : createBuildStep('windows'),
						 'Build on Linux' : createBuildStep('linux')]
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

	stage('Build and Test') {
		parallel buildAndTestSteps
	}

} finally {
	stage('Clean up') {
		parallel cleanupSteps
	}
}