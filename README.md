# lwjgl_3D_library
Current version: **1.0.0-alpha**

## Core functionality
#### Game loop handling
* The Game class should be inherited, this will controll the flow of the application for you. The Game class contains all functionality for this library.
#### Renderer
 * This libray contains a renderer that can render entites. The renderer will automatically bind **shader** and **entities** but not **geometry** read more in Geometry section.
#### Entities
* Entites is a class that contain a **transform**, **geometry**, **textures** and **material**. Entites can be rendered using the renderer. 
#### Geometry
* The Geometry class contains vertices and methods to add vertices. Geometry can be added by specifying the amount of vertices or by loading an obj file. You need to bind the geometry every time it is updated including on creation. 
#### point Lights
* Lights will be passed on to the gpu once every frame. Lights are used by the shaders to create lighting. The Game class contains all lights and functionality to add, remove and edit them.


## Setup for eclipse
* Download or clone lwjgl_3D_library.
* Create a new java project. in Eclipse
* Right click in Project Explorer Then click import.
* Go to general then choose  File System and click next.
* select the lwjgl_3D_library directory and then click select all.
* Click finish and select Yes To All.
