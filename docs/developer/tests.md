# Automated Tests

[Spring JUnit](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-testing.html) is used to run automated tests on OpenLRW when building the executable.

All the tests are located in the `src/test` directory.

## Running the tests

Execute the following command to execute Unit and Integration tests : `mvn test`


## Continuous Integration with Travis-CI
After each commit pushed on the main repository, unit tests are executed on an Oracle JDK 8 Virtual Machine.

The Travis config file `.travis.yml` is located on the root directory of OpenLRW.