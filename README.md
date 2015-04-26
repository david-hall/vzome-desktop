vzome-desktop
=============

[vZome](http://vzome.com/) is a desktop application for creating virtual Zome models.  This project contains the source code for just the controllers and views of the user interface; it requires the [vzome-core](https://github.com/vorth/vzome-core/) library to provide all the models and basic behaviors, including loading and saving files.

Build Instructions
-------------

(These instructions are primarily for Linux or Mac, or any other Unix, but some basic functionality has also been adapted for Windows.)

The build was originally implemented using Maven but now uses Gradle with a local Maven repository.  Most of the dependencies will be pulled from the Maven repositories automatically, but there is one special requirement: the [vzome-core](https://github.com/vorth/vzome-core/) project must be built locally, so that the JAR appears in your local Maven repository to satisfy the dependency.

Having built vzome-core initially, the standard Gradle Wrapper build command should work fine. For Mac or Unix:

    gradlew clean build
	
... or in Windows PowerShell:

	./gradlew clean build

Note that the Gradle Wrapper installs Gradle itself, the first time you run one of these commands.	

Developing with Eclipse
-------------

Gradle has support for IDE integration.  I use Eclipse, so the Gradle command for generating an Eclipse project is extremely helpful to me:

    ./gradlew eclipse

This command must be executed twice. Once from within the vzome-core folder and again in the vzome-desktop folder before the two projects can be opened by Eclipse. An Eclipse launch configuration file (vzome-desktop.launch) is included. It will be used in the generated Eclipse project for vzome-desktop.

Existing projects are opened in Eclipse by using "File | Import... | Existing Projects into Workspace" and selecting the folder where the build command was executed (vzome-core and/or vzome-desktop).

Developing with NetBeans IDE
-------------

The NetBeans IDE supports Gradle projects with the Gradle plug-in available at http://plugins.netbeans.org/plugin/44510/gradle-support. Version 1.3.2 of the plug-in has been used with NetBeans 8.0. The Gradle plug-in should be installed in NetBeans before attempting to load the vzome-desktop project. For the vzome-core project, NetBeans reads the build.gradle or pom.xml and that is the NetBeans project. Simply use File | Open Project and select vzome-desktop/build.gradle and/or vzome-core/pom.xml. Note that if NetBeans finds both a build.gradle and a pom.xml file, it will revert to using pom.xml. This is NOT correct, so be sure that vzome-desktop includes build.gradle and vzome-core uses pom.xml

A NetBeans configuration file (nbactions.xml) is included.


Running vZome from the Command Line
--------------

Once you have successfully built vZome, you can run it as a Gradle task using the command:

	gradlew run
	
... or in Windows PowerShell:

	./gradlew run


Packaging vZome
---------------

On the Mac, you can package a self-contained application for vZome, with an embedded JRE, using this command:

    ./gradlew createApp

The app will appear as build/macApp/vZome.app.

If you want to build a disk image containing the app, you can do:

    ./gradlew createDmg

The disk image will show up under build/distributions.

