# SudokuSolver
A basic sudoku solver, with GUI. [WIP, See [Issues](https://github.com/ir-g/SudokuSolver/issues)]

Notes on sudoku:
* Sudoku isn't a number puzzle, so much as a list of fixed values
, which have a set of rules applied
* A 9 by 9 completed grid can be compressed losslessly to 8 by 8
* Any standard sudoku can be rotated 90 degrees without
any loss of meaning.
* There are a fixed number of possible permeations for a given
grid size. 
* Because values don't have to be identical, just repeated
* Given these previous statements, it is possible to compute and 
define every possible permeation, and solve by matching to a 
permeation.
