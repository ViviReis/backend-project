# Backend Project

* [Libraries](#libraries)
* [Required Software](#required-software)
* [How to setup project](#how-to-setup-project)
* [How to run the tests](#how-to-run-the-tests)
    * [Running the test suites](#running-the-test-suites)
    * [Generating the test report](#generating-the-test-report)
* [More info](#more-info)


## Libraries
* [RestAssured](http://rest-assured.io/) library to test REST APIs
* [JUnit 5](https://junit.org/junit5/) to support the test creation
* [Owner](http://owner.aeonbits.org/) to manage the property files
* [Log4J2](https://logging.apache.org/log4j/2.x/) as the logging strategy
* [Allure Report](https://docs.qameta.io/allure/) as the testing report strategy


## Required software
* Java JDK 11+
* Maven installed and in your classpath

## How to setup project

Clone the project via the command line:
```
git clone git@github.com:ViviReis/backend-project.git
```

## How to run the tests
You can open each test class on `src/test/java` and execute all of them, but I recommend you run it by the
command line. It enables us to run in different test execution strategies and, also in a pipeline, that is the repo purpose.

### Running the test suites

The test suites can be run directly by your IDE or by command line.
If you run `mvn test` all the tests will execute because it's the regular Maven lifecycle to run all the tests.

To run different suites based on the groups defined for each test you must inform the property `-Dgroups` and the group names.
The example below shows how to run the test for each tag:

| test type | command                               |
|-----|---------------------------------------|
| create card tests | `mvn test -Dgroups="create_card_tag"` |
| create pipe tests | `mvn test -Dgroups="create_pipe_tag"` |

### Generating the test report

This project uses Allure Report to automatically generate the test report.
There are some configuration to make it happen:
* aspectj configuration on `pom.xml` file
* `allure.properties` file on `src/test/resources`

You can use the command line to generate it in two ways:
* `mvn allure:serve`: will open the HTML report into the browser
* `mvn allure:report`: will generate the HTML port at `target/site/allure-maven-plugin` folder
