# GraphsJ-sdk

*GraphsJ - Scenario Development Kit (SDK)*


## Introduction

GraphsJ SDK is a library providing traits and classes to easily create scenarios that can later be imported and run within GraphsJ.

Unlike the previous releases, the current version of the framework is very minimalist and inspired by Functional Programming - in particular, it employs the [EighthBridge](https://github.com/giancosta86/EighthBridge) toolkit.



## Referencing the SDK

The SDK is available on [Hephaestus](https://bintray.com/giancosta86/Hephaestus) and can be declared as a Gradle or Maven dependency; please refer to [its dedicated page](https://bintray.com/giancosta86/Hephaestus/GraphsJ-sdk).

Alternatively, you could download the JAR file from Hephaestus and manually add it to your project structure.

**IMPORTANT**: the SDK should be referenced as a **provided** dependency, needed only during the compilation of your project - as it is provided by GraphsJ itself at runtime.


## Employing the SDK

Basically speaking, to create a new scenario for GraphsJ, you should:

0. Implement the **Algorithm** trait, to express the algorithm logic

0. Implement the **Scenario** trait, to provide details on:
  * the scenario
  * the graph
  * the controllers handling user interaction

Creating a scenario requires knowledge of [EighthBridge](https://github.com/giancosta86/EighthBridge), the ScalaFX library dedicated to graph manipulation.


An interesting example is the [GraphsJ - Scenarios](https://bintray.com/giancosta86/Hephaestus/GraphsJ-scenarios) open source project, showing how to create basic custom scenarios and algorithms.

For further information, please refer to the related Scaladoc documentation.


## Further references

* [Facebook page](https://www.facebook.com/graphsj)

* [GraphsJ](https://github.com/giancosta86/GraphsJ)

* [EighthBridge](https://github.com/giancosta86/EighthBridge)
