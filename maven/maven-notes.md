# Introduction to Maven

Maven is a powerful build automation tool used primarily for Java projects. It is based on the concept of a project object model (POM), which is an XML file that contains information about the project and configuration details used by Maven to build the project.

## Key Features of Maven:

1. **Project Management**: Maven manages a project's build, reporting, and documentation from a central piece of information.
2. **Dependency Management**: Maven automatically downloads required libraries and plug-ins from the central repository.
3. **Build Automation**: It can automate the compilation, testing, packaging, and deployment process.
4. **Convention over Configuration**: Maven uses conventions to minimize the configuration required, allowing developers to focus on writing code.
5. **Extensibility**: It can be extended by plug-ins to support various tasks in the build process.

## Basic Concepts:

1. **POM (Project Object Model)**: The core unit of work in Maven, usually `pom.xml`, which defines the project's dependencies, build settings, and other configurations.
2. **Repository**: A directory where all the project’s dependencies, plug-ins, and project artifacts are stored.
   - **Local Repository**: A directory on the developer's machine.
   - **Central Repository**: A repository provided by the Maven community.
   - **Remote Repository**: Additional repositories configured in `pom.xml`.

## Basic Example of a POM File (`pom.xml`):

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.example</groupId>
    <artifactId>my-app</artifactId>
    <version>1.0-SNAPSHOT</version>
    <dependencies>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
            <scope>test</scope>
        </dependency>
    </dependencies>
</project>
```

## Installation:

1. Download maven.
2. Add it to environment variables, no need of install
3. Windows

   ```
   M2_HOME =`path to root folder`
   path = `add the bin path`
   ```

   Mac

   ```
   brew install maven


   nano ~/.zshrc
   # or
   nano ~/.bash_profile


   export M2_HOME=/usr/local/Cellar/maven/3.x.y
   export PATH=$M2_HOME/bin:$PATH


   source ~/.bash_profile
   # or
   source ~/.zshrc


   mvn -version

   ```

   **`Note:`** All latest IDE's come with maven no need install seperately, install it only when you want to run through CLI

## Create Maven Project

```
mkdir maven-lifecycle-example
cd maven-lifecycle-example
```

```
mvn archetype:generate -DgroupId=com.example -DartifactId=maven-lifecycle-example -DarchetypeGroupId=org.apache.maven.archetypes -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false

mvn versions:use-latest-releases -U -B
```

-DgroupId=com.example: The group ID for your project (you can change this to suit your project).

-DartifactId=my-app: The artifact ID for your project (you can change this to suit your project).

-DarchetypeGroupId=org.apache.maven.archetypes: Specifies the group ID for the archetype.

-DarchetypeArtifactId=maven-archetype-quickstart: Specifies the artifact ID for the archetype.

-DinteractiveMode=false: Disables interactive mode to run the command non-interactively.

## Maven Build Life Cyle

The Maven life cycle is a sequence of phases that are executed in order to build and deploy a Maven project. It provides a well-defined process for building, testing, packaging, and deploying software projects. The main phases of the Maven life cycle are:

1. **validate:** This phase validates the project is correct and all necessary information is available.
2. **compile:** This phase compiles the source code of the project.
3. **test:** This phase runs the unit tests on the compiled source code using a suitable unit testing framework.
4. **package:** This phase packages the compiled code in a distributable format, such as a JAR, WAR, or EAR file, depending on the project type.
5. **integration-test:** This phase processes and deploys the package if necessary into an environment where integration tests can be run.
6. **verify:** This phase runs any checks to verify the package is valid and meets quality criteria.
7. **install:** This phase installs the package into the local repository, for use as a dependency in other projects locally.
8. **deploy:** This phase copies the final package to the remote repository for sharing with other developers and projects.

Each phase is designed to perform a specific task, and when you execute a particular phase, all previous phases in the sequence are also executed. For example, if you run the package phase, Maven will execute the validate, compile, test, and package phases in that order.

`validate -> compile -> test -> package -> integration-test -> verify -> install -> deploy`

You can also pass various options and goals to Maven to customize the build process, such as skipping tests, building with specific profiles, or running individual plugins.

The Maven life cycle provides a standard and consistent way to build, package, and deploy projects, making it easier to manage and maintain software projects, especially in complex or team-based development environments.
