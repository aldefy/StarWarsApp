[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![MIT License][license-shield]][license-url]
[![LinkedIn][linkedin-shield]][linkedin-url]


<!-- PROJECT LOGO -->
<br />
<p align="center">
  <a href="Repo">
    <img src="snaps/logo.png" alt="Logo" width="80" height="80">
  </a>
  <h3 align="center">Star Wars Universe</h3>

  <p align="center">
    This is a showcase android application written in Kotlin and follows Clean Code architecture to showcase Characters from the StarWar movies
    <br />
    <a href="/releases/">Get Sample app</a>
  </p>
</p>


<!-- TABLE OF CONTENTS -->
## Table of Contents

* [About the Project](#about-the-project)
  * [Built With](#built-with)
* [Getting Started](#getting-started)
  * [Prerequisites](#prerequisites)
  * [Installation](#installation)
* [Roadmap](#roadmap)
* [Contributing](#contributing)
* [License](#license)
* [Contact](#contact)



<!-- ABOUT THE PROJECT -->
## About The Project

## Star Wars Universe 1.0
Welcome 👋 This repo is a showcase of an android application which uses Clean Architecture and manages states using a FSM(Finite state machine)
This repo implements a small but scalable (!) app which interacts with the open Star Wars API at
https://swapi.dev/

The app contains 2 main areas:
* Character Search (Home Screen)
* Character Details (Details Screen)

The following attributes are displayed for the character details:
* name
* birth_year
* height (in cm and feet/inches)
* name
* language
* homeworld
* population (planets)
* films (movies the character appeared in)
* opening_crawl (detailed description of each movie)

### Built With

* [Clean Arch](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)
* [RxJava2](https://github.com/ReactiveX/RxJava)
* [MVVM](https://bit.ly/2GmZICu)

### Architecture

![Arch](snaps/arch.png)

#### Keywords
* Screen - Represents a layer to react to State presented by domain layer for UI to show or update UI elements
* State - State representation of a finite system based on business logic
* Event - Event representation for UI layer to react to in a finite approach.

## Decision log
* Multi modular architecture and feature module driven architecture
* Choose navigation components to show that we don't need a single fragment however reuse a Custom view if needed as a destination
* Breaking down core deps and structuring a scalable but highly adaptable modular code base.
* Adds a design system module to help with showcasing theming support as well Epoxy driven UI layer where needed
* Showcasing design principles by breaking down core requirements for building the aforementioned design layer.
* Writing BaseJunit test to ensure all tests followed same coding standards.
* Did not have time to invest into UI testing
* Use of Clean architecture helped ensure SOC for each feature and testability
* Core/Common Classes - could not cover any test coverage in these modules for now
* Built custom architecture to break down into unit components in each layer
* Ensured packaging for readability.
* 📝 Despite writing tests at the end - did not follow TDD per say , chose this path for a reason - architecture shown in this app has been used by my team for some time now and that allowed control over components to verify at the end
* Due to bad time crunch over week days , asked for an extension Day to ensure i delivered with satisfaction of completing all checkboxes i had set forth to complete in this submission.
* Added a error view at the end to first solve core challenges and tackle UI towards end.
* Ensured wrote granular commits to help understand thought-process from get-go.

<img src="snaps/git_logs.png" alt="Git Logs" width="300" height="300">


<!-- GETTING STARTED -->
## Getting Started

Clone the project and review the code or simply download the apk.

### Prerequisites

Android Studio 4.2.1
Java 1.8

### Installation

1. Clone the repo
2. Clean build and then Run with Android Studio

<!-- ROADMAP -->
## Roadmap

See the [open issues](/Issues.md) for a list of proposed features (and known issues).

<!-- Tech docs -->
## Tech-stack

* Tech-stack
    * [Kotlin](https://kotlinlang.org/) - a cross-platform, statically typed, general-purpose programming language with type inference.
    * [RxJava 2](https://github.com/ReactiveX/RxJava) - perform background operations.
    * [Dagger Hilt](https://developer.android.com/training/dependency-injection/hilt-android) - a pragmatic lightweight dependency injection framework.
    * [Retrofit](https://square.github.io/retrofit/) - a type-safe REST client for Android.
    * [Jetpack](https://developer.android.com/jetpack)
        * [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - is an observable data holder.
        * [Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle) - perform action when lifecycle state changes.
        * [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - store and manage UI-related data in a lifecycle conscious way.
        * [Navigation](https://developer.android.com/guide/navigation) - Android navigation components helps connect different UI components
    * [Chucker](https://github.com/ChuckerTeam/chucker) - Netowrking debugging tool.
    * [Timber](https://github.com/JakeWharton/timber) - a highly extensible android logger.
    * [Andromeda](andromeda/) - a Design System module to help build components for UI layer
    
* Architecture
    * MVVM - Model View View Model
* Tests
    * [Unit Tests](https://en.wikipedia.org/wiki/Unit_testing) ([JUnit](https://junit.org/junit4/))
    * [Mockito](https://github.com/mockito/mockito) + [Mockito-Kotlin](https://github.com/nhaarman/mockito-kotlin)
* Gradle
    * [Gradle Grovy DSL](https://docs.gradle.org/current/userguide/kotlin_dsl.html) - For reference purposes, here's an [article explaining the migration](https://medium.com/@evanschepsiror/migrating-to-kotlin-dsl-4ee0d6d5c977).
    * Plugins 
        * [Json To Kotlin Data Class](https://github.com/Mighty16/JSONToKotlinClass) - Plugin generates Kotlin data classes from JSON text.

## Design Patterns
* [SOLID](https://en.wikipedia.org/wiki/SOLID) - SOLID principle
* [Design Principles](https://refactoring.guru/design-patterns) - Mostly Builder pattern
    
## Dependencies

All the dependencies (external libraries) are defined in the single place - Gradle [dependencies.gradle](dependencies.gradle) folder. This approach allows to easily manage dependencies and use the same dependency version across all modules. 

## Tests
Details

<img src="snaps/details-unit-test.png" width="400">

Search

<img src="snaps/search-unit-test.png" width="400">


## Some notable mentions for Code review

This sections mentions some of the issues not covered in this code submission:

- Have not covered any UI tests in this project.

- UI layer is barebones but can be improved upon on further enchancements.

- The Error representation on UI is not covered , but is handled in other layers as states.

<!-- CONTRIBUTING -->
## Contributing

Contributions are what make the open source community such an amazing place to be learn, inspire, and create. Any contributions you make are **greatly appreciated**.

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request



<!-- LICENSE -->
## License

Distributed under the Apache License 2.0 License. See `LICENSE` for more information.


<!-- CONTACT -->
## Contact

Adit Lal - [@aditlal](https://twitter.com/aditlal) - https://aditlal.dev

Open calendar : [calendar](https://calendly.com/aditlal)


<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io/github/contributors/aldefy/StarWarsApp.svg?style=flat-square
[contributors-url]: https://github.com/aldefy/StarWarsApp/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/aldefy/StarWarsApp.svg?style=flat-square
[forks-url]: https://github.com/aldefy/StarWarsApp/network/members
[stars-shield]: https://img.shields.io/github/stars/aldefy/StarWarsApp.svg?style=flat-square
[stars-url]: https://github.com/aldefy/StarWarsApp/stargazers
[issues-shield]: https://img.shields.io/github/issues/aldefy/StarWarsApp.svg?style=flat-square
[issues-url]: https://github.com/aldefy/StarWarsApp/issues
[license-shield]: https://img.shields.io/github/license/aldefy/StarWarsApp.svg?style=flat-square
[license-url]: https://github.com/aldefy/StarWarsApp/blob/master/LICENSE.txt
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=flat-square&logo=linkedin&colorB=555
[linkedin-url]: https://linkedin.com/in/aditlal
