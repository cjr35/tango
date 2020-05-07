# .tango
A body file in a project. This file represents and must be named after one of the states declared in the project's .tangoh file. There must be exactly one .tango file for each of these states.

## Contents
A .tango file contains three elements: a simple declaration, a list of ways a state may be visualized, and a list of rules. As with .tangoh files, any non-indented line which is not a valid list header will cause a transpile-time error. The valid list headers in a .tango file are:
- `state`
- `rules`
- `visual`

The `state` and `rules` are required, but `visual` may be omitted.
### declaration
<!--stackedit_data:
eyJoaXN0b3J5IjpbMzQyODA1OTBdfQ==
-->