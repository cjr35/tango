# .tangoh
The header file of a project. This file should be named after the automaton being specified, and must share this name with the project folder.
## Contents
A .tangoh file is as series of lists, each with a different purpose. The lists in the current specification are:
- `states`
- `world`
- `neighborhoods`

Any of these lists may be omitted except for `states`. These are crucial to defining an automaton, and so they must be the first thing in a .tangoh file. The other lists do not have to be in any specific order, but there may be only one of each.

Any line in a .tangoh file which is not indented will be considered the header of a list. Encountering any list header not present in this specification (i.e. the list above) will cause Tango to throw an error at transpile-time. There is no requirement for blank lines between lists, but one blank line is recommended to maintain good style and readability.
### states
The `states` keyword is the most important part of a .tangoh file, and it represents the most important part of a cellular automaton. Without having two or more states to switch between, an automaton would just sit there and not do anything.

The syntax for the list of states is simple; it is the `states` keyword followed by an indented list of state names. This is the list that Tango will check against to determine that all state files are present, so state names must be file-safe. To that end, each state name must begin with a capital letter, and may contain only alphanumeric characters, hyphens, and underscores.

In the event that any part of Tango needs a state as input, the first item in this list will be considered the default.
### world
The `world` keyword heads an indented list of attributes for the grid the automaton inhabits, known as world attributes. Each world attribute defines its own set of available values. Each may have any number of sub-attributes, and each world attribute present in a file's `world` must be defined.

A world attribute is defined by adding it to a new line under the `world` keyword, then following it with a space and its value. For example, setting the world attribute `type` to the built in value `flat` looks like this:
```
world
	type flat
```
###
Sub-attributes are defined by the value of their parent attribute. For example, `type flat` has no sub-attributes, but `type sized` has the following:
```
world
	type size
		dim 100 100
		edge-behavior wrap
```
###
World attributes must be defined in the following order:
- `type`, which indicates many general properties of the world
- `cell-init`, which indicates how cells should be initialized

The order of world attributes is critical because any world attribute's values may influence the set of available values for world attributes below it. For example, a `world` with  `type flat` cannot have `cell-init random`. This would cause the state machine to hang before even running a single generation as it calculates an infinite number of random states.

A .tangoh file with no `world` list will be treated as one with the following default settings:
```
world
	type flat
```
### neighborhoods
A neighborhood is a group of other cells that influence a cell's state. Typical cellular automata use only one neighborhood globally, but Tango allows the user to define and use as many as they like.

The neighborhood list for a project is headed by the keyword `neighborhoods`, which is followed by an indented list of neighborhood definitions. These definitions take the form `expr as name`.

`expr` is any valid Tango expression which evaluates to a neighborhood.
`as` is a binary keyword operator that assigns a name to the neighborhood defined by `expr`.
`name` is the name to be assigned to the neighborhood being defined. This name can be used in .tango files to refer to the neighborhood.

The `as name` part of this definition is optional. In the absence of a name clause, Tango will name neighborhoods according to the following strategy:
- The first unnamed neighborhood will be named `default`, unless a different neighborhood is named `default`.
- Subsequent unnamed neighborhoods will be named `nh-1`, `nh-2`, etc.
###

For example, the neighborhood list
```
neighborhoods
	von-neumann 2
	moore 3 as large
	von-neumann 1 as diamond
	moore 1 as default
```
produces
- a vonNeumann neighborhood with range 2 named `nh-1`,
- a Moore neighborhood with range 3 named `large`,
- a von Neumann neighborhood with range 1 named `diamond`,
- and a Moore neighborhood with range 1 named `default`.

###
In the event that any part of Tango needs a neighborhood as input, the neighborhood named `default` will be used. If no neighborhood is named `default`, the first item in the list will be used.

A .tangoh file with no `neighborhood` list will be treated as one with the following default settings:
```
neighborhoods
	moore 1 as default
```
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTE5ODA2NzUwODIsNzc4NjcwODg2XX0=
-->