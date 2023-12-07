package ci;

import ci.dependencies.Config;
import ci.dependencies.Emailer;
import ci.dependencies.Logger;
import ci.dependencies.Project;

public class Pipeline {
    private final Config config;
    private final Emailer emailer;
    private final Logger log;

    public Pipeline(Config config, Emailer emailer, Logger log) {
        this.config = config;
        this.emailer = emailer;
        this.log = log;
    }

    public void run(Project project) {
        boolean testsRunResult = tryRunTests(project);
        boolean deploySuccessful = false;
        if (testsRunResult) {
            deploySuccessful = tryDeployment(project);
        }

        trySendEmail(testsRunResult, deploySuccessful);
    }

    private boolean tryRunTests(Project project) {
        boolean testsRunResult = true;
        if (project.hasTests()) {
            testsRunResult = runTests(project);
            if (testsRunResult) {
                log.info("Tests passed");
            } else {
                log.error("Tests failed");
            }
        } else {
            log.info("No tests");
        }
        return testsRunResult;
    }

    private boolean runTests(Project project) {
        return "success".equals(project.runTests());
    }

    private boolean tryDeployment(Project project) {
        boolean deployResult = tryDeploy(project);
        if (deployResult) {
            log.info("Deployment successful");
        } else {
            log.error("Deployment failed");
        }
        return deployResult;
    }

    private boolean tryDeploy(Project project) {
        boolean deploySuccessful;
        String deploymentResult = project.deploy();
        if ("success".equals(deploymentResult)) {
            deploySuccessful = true;
        } else {
            deploySuccessful = false;
        }
        return deploySuccessful;
    }

    private void trySendEmail(boolean testsPassed, boolean deploySuccessful) {
        if (config.sendEmailSummary()) {
            log.info("Sending email");
            sendEmail(testsPassed, deploySuccessful);
        } else {
            log.info("Email disabled");
        }
    }

    private void sendEmail(boolean testsPassed, boolean deploySuccessful) {
        String message;
        if (testsPassed) {
            if (deploySuccessful) {
                message = "Deployment completed successfully";

            } else {
                message = "Deployment failed";
            }
        } else {
            message = "Tests failed";
        }

        emailer.send(message);
    }

}
