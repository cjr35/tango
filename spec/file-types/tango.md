# .tango
A body file in a project. This file represents and must be named after one of the states declared in the project's .tangoh file. There must be exactly one .tango file for each of these states.

## Contents
A .tango file contains three elements: a simple declaration, a list of ways a state may be visualized, and a list of rules. As with .tangoh files, any non-indented line which is not a valid list header will cause a transpile-time error. The valid list headers in a .tango file are:
- `state`
- `rules`
- `visual`

The `state` and `rules` are required, but `visual` may be omitted. These three keywords must occur in the above order.
### declaration
The declaration of a .tango file can be thought of as the last checkpoint Tango encounters before defining a state. After checking the file name against the `states` list in the .tangoh file, Tango checks for the word `state` followed by a space and the file's name again. Finding `state` identifies the file as one which describes a state, and finding the file's name verifies that the following code defines that state.
### rules
The `rules` header indicates a list of definitions for rules obeyed by an automaton. An automaton obeys different rules depending on the state it is in, so each .tango file has its own `rules`. The syntax of a rule is `expr state in neighborhood >> state`.

`expr` is any valid Tango expression which evaluates to a range.
`state` is the name of one of the states defined in the project's .tangoh file.
`in` is a ternary keyword operator which is used to determine whether the current rule should be applied.
`neighborhood` is any valid Tango expression that evaluates to a neighborhood.
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTExMzY5MzQ4MjIsMzQyODA1OTBdfQ==
-->