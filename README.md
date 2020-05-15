## General

Tango is a robust language for specifying cellular automata.

Tango does not and cannot run on its own - an implementation of Tango should read in the source files and "transpile" them into a state machine written in the language of the implementer's choice.

This plan for this repository is to provide Java and JavaScript implementations of Tango, as well as a Java user interface for writing and running cellular automata using Tango.

## Usage
After compiling the appropriate java files you can test the lexer by running Tango.java and entering the commands
```
tango Conway
```
or
```
tango -o Conway
```
The latter will show verbose output from the lexer. Feel free to locally edit the current sample projects or create your own.