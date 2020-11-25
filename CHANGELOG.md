### current develop
#### changed
* changed the game loop.
* added scene system. You can now create many scenes and switch between them. A scene has a load and update function. A scene will remove all its content when a different scene is loaded.
* improved ray marching api.
#### bugfixes
* fixed memory leaks.
* ray marching now works with different window resolutions
### 1.1.0
#### features
* added ray marching system. Raymarcher will go through scene of objects and calculate distance. float scene(vec3 point) needs to be created.
*  you can now create functions between uniforms and fragment in shaders.
*  added list of uniform in fbo to be make it posible to add uniforms for post processing shaders.

#### bugfixes
* fixed issue with rayMarching.glsl needed to be included even when it was not used.
* fixed bug with uniforms not working.

### 1.0.0
#### features
* revamped shaders. Added mesh shader and post processing shader for easier shader creation.
* revamped fbo api. Fbo is now Created in the Game class. The game class contains new functions to bind unbind and render fbo.

#### removed
*  removed old shaders.

#### bugfixes
*  fixed issue with getting the fragment code when the code contained nested ifs and loops.


### 1.0.0-alpha.1
#### bugfixes
* fixed bug with object transform not updating on render if there is more then 1 entity.

### 1.0.0-alpha
