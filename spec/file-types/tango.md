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
The `rules` header indicates a list of definitions for rules obeyed by an automaton. An automaton obeys different rules depending on the state it is in, so each .tango file has its own `rules`. The syntax of a rule is `expr ifState in neighborhood >> newState`.

`expr` is any valid Tango expression which evaluates to a range.
`ifState` is the name of one of the states defined in the project's .tangoh file.
`in` is a ternary keyword operator that determines whether the current rule should be applied.
`neighborhood` is any valid Tango expression that evaluates to a neighborhood.*
`>>` is a binary operator that applies the current rule when appropriate (as determined by `in`).
`newState` is the name of one of the states defined in the project's .tangoh file.

\* In many cases this will just be the name of a neighborhood defined in the project's .tangoh file.

The `in neighborhood` part of a rule definition is optional. In the absence of a neighborhood clause, Tango will assume the user wants to check the default neighborhood as defined in the project's .tangoh file.

The list of rules may be ended by an `otherwise` statement. This statement consists of the keyword `otherwise` followed by a space and a state name or the keyword `stay`. The `otherwise` statement is optional, and in its absence Tango will behave as though the list ended with `otherwise stay`.

The process of checking and applying rules is simple. Tango checks the condition to the left of `>>`. If that condition is met, Tango applies the current rule and exits, not bothering to check any rules listed below. If the condition is not met, Tango looks at the next rule and repeats the process. If Tango finds no applicable rules, it acts according to the `otherwise` statement at the end of the list.
### visual
The `visual` list in a .tango file describes how a state should be visually represented. This is entirely optional, and is only serves to tell an application how to display the grid. It is a list of visual attributes and their values. Tango's state machine does not use these values to calculate anything. Instead, it stores them separately from the state machine and provides them to any app that requests them.

Visual attributes are set by being added to a new line under the `visual` keyword. They are then followed by a space and their value. Visual attributes can be set in any order and include the following:
attribute name | target
- | -
`char` | text-based Tango visualizations
`color` | grid-based Tango visualizations

In the absence of a `visual` section in one or more .tango files, Tango provides a default value for each state, ignoring those that do have `visual` sections.
<!--stackedit_data:
eyJoaXN0b3J5IjpbNDMzNTI0NTQxLDM0MjgwNTkwXX0=
-->