# GraphsJ-sdk

*GraphsJ - Scenario Development Kit (SDK)*


## Introduction

**GraphsJ SDK** is a library providing traits and classes to easily create scenarios that can be imported and run within [GraphsJ](http://gianlucacosta.info/GraphsJ/).

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

0. Implement the **ScenarioFactory** trait, which should instantiate the Scenario

Creating a scenario requires knowledge of [EighthBridge](https://github.com/giancosta86/EighthBridge), the ScalaFX library dedicated to graph modeling and interactive rendering.


Interesting examples are available in [GraphsJ - Scenarios](https://bintray.com/giancosta86/Hephaestus/GraphsJ-scenarios), an open source project containing scenarios for both Artificial Intelligence and Operations Research.

For further information, please refer to the related Scaladoc documentation.


## Referencing external libraries

The following libraries are automatically provided by GraphsJ SDK:

* [EighthBridge](https://github.com/giancosta86/EighthBridge): for graph modeling and rendering

* [LambdaPrism](https://github.com/giancosta86/LambdaPrism): for Artificial Intelligence

* [OmniEditor](https://github.com/giancosta86/OmniEditor): providing an editor with syntax highlighting

* [Helios-core](https://github.com/giancosta86/Helios-core): library with shared utilities


Other libraries *cannot* be referenced by a scenario, unless your build tools includes their classes within your jar, making it self-contained.


## Further references

* [Facebook page](https://www.facebook.com/graphsj)

* [GraphsJ](https://github.com/giancosta86/GraphsJ)
