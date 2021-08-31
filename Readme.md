# Myntra For You (MFY)
This is a dropwizard based service which has following dependencies:
- guava: 23.0
- guice: 4.2.0
- dropwizard: 0.9.2
- drools: 7.38.0.Final
- prometheus simpleclient: 0.9.0


### Project Structure

- **com.myntra.hackathon** - *Contains the webservice related code.*
    - **resources/config** - *Contains configuration files for starting the application. Use -local.yml for running it locally and similarly for stage and production*
    - **configuration** - *Configuration class required by the application. Some of them will be responsible for reading the values from .yml file.*
    - **service** - *Manager classes handle the business logic. Resource classes interact with manager classes which are stored in this package.*
    - **models** - *The POJOs and other models required by the project will be maintained in this package.*
    - **persist** - *This package contains the DAO classes required by the application.*
    - **resources.v1** - *Resources/API classes are maintained here. Swagger picks up resource definitions from here*
    - **utils** - *Service related utility codes are maintained in this package*
    - **MfyServiceModule.java** - *Module class responsible for providing all Guice injections to the application*
    - **MfyServiceMain.java** - *Main class responsible for initializing the service.*

### Compiling
- Go to project home folder and type `mvn clean install`
- After the compilation completes successfully, execute the MfyService using any of the IDEs with following configurations
    - Main Class: `com.myntra.hackathon.MfyServiceMain`
    - Promgram arguments: `server src/main/resources/config/config-local.yml`. You can change the yml file depending on the requirement.
    - JRE: Java 1.8

After executing the service, following endpoints will become active -
* Swagger - http://localhost:10001/api/swagger
* Prometheus Metrics - http://localhost:10005/prometheusMetrics
* Dropwizard Metrics - http://localhost:10005/metrics
* Application Log - /tmp/mfy.log
* Request Log - /tmp/mfy-request.log

