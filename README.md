MIT License

Copyright (c) 2024 Judekeyser

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.


# jacjac

Boiler-plate example about integrating Java with C method,
through the foreign API.

Run the `build.sh` script (manually line by line, by adapting the commands).
The script first compiles the C library to a shared object.
Then it compiles the Java classes and run the main method.
The library location is hooked using an environment variable, but some configuration file
could be imagined as well.

## Usage

It is not yet clear how would people want to use this boilerplate.
To our perspective, it would make sense people just stack their C bridges into the `clib` module,
with the policy that every C library corresponds to a Java class in a dedicated (or the same?) Java package.
That way, `MethodSolver` can be just reused without being duplicated accross modules.
It also lighten a bit the build process, as there is a unique module to control and grant security accesses.
