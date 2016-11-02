Run the command below:

`brew install node`

### Installing Appium ###

Run the command below:

`npm install -g appium`

### Installing ideviceinstaller ###

Run the command below:

`brew install ideviceinstaller`

### Installing Gradle ###

Run the command below:

`brew install gradle`

## Set up ##

* Clone the project on the machine
* cd into the project directory
* Execute the command `gradle clean testLocal`

To view the code import the project into Eclipse or Intelli J

## Important notes ##

Currently the project has a default test that involves loading a simulator, navigating to the devices settings screen and verifying the settings screen is displayed. This is for demo purposes only.

## Further support details ##

The framework uses cucumber feature files to include the acceptance tests and gradle as a project build tool. 
Therefore for further details for cucumber or gradle, please refer to: 

[Cucumber] `https://www.tutorialspoint.com/cucumber/index.htm`

[Gradle] `https://gradle.org`
