# Grid-Challenge
### Problem Overview 

>In an 'n' x 'n' grid, find the largest product of four consecutive numbers. In this context, consecutive numbers are 
> next to each other either horizontally, vertically or diagonally. For example a product from the numbers in row 18, 
> column 15, to row 15, column 18 equaling 93128300 

Looking at the provided challenge question and googling around a bit, it seems to be based on version of a problem made 
famous by [Project Euler's Problem 11](https://projecteuler.net/problem=11). <i>Project Euler</i> is a site dedicated to 
challenging mathematical and computational programming problems attempting to teach skills outside <i>just</i> pure
logical insight - to teach new and unfamiliar concepts through stringent challenges and problem-solving ('gam-i-fy', 
as it were). At face value, it seems to try to test the following:

* 2D array traversal 
* Loop control logic 
* Bounds-safe indexing

All of which should be a fun, (<i>or, maybe not fun, but at least interesting</i>) task to try and solve. As a 'DSA 
challenge', I'll try and strive to solve the task while emphasizing the importance of building a correct / functioning 
and readable solution before iterating into optimization.

----

## Table of Contents
- [Proposed bruteforce solution](#proposed-bruteforce-solution)
- - [Step 1 - Reading the file line-by-line](#step-1-reading-the-file)
- - [Step 2 - Converting the List into a 2D array](#step-2-converting-the-list-into-a-2D-array)
- - [Step 3 - Looping over each cell in the grid](#step-3-looping-over-each-cell-in-the-grid)
- - [Step 4 - Directional checking](#step-4-directional-checking)
- - [Step 5 - Printing the final results](#step-5-printing-the-final-results)
- - [Closing thoughts / post-mortem](#closing-thoughts-and-post-mortem)
- - [Next steps or improvements](#next-steps-or-improvements)
- - [Limitations and edge-cases](#limitations-and-edge-cases)

----

## Proposed bruteforce solution

<p>
Looking at the problem-set, while considering the requirements, it seems to be quite possible to use a 'bruteforce' 
solution for the challenge. This would be done by simply checking every possible set within four adjacent numbers in 
the grid through looping over every cell and manually check all four directions where a sequence of four numbers fit; 
calculating their product each time. Simple - <i>yes</i>, efficient - <i>no</i>, elegant - again: <i>nay</i>. But 
would be straight forwards-<i>ish</i> which helps to learn or understand the constraints, logic and constraints of the 
problem at hand. So with-out further ado. 
</p>

----

### How to run

Prerequisites:
* Java 17 
* Terminal or your choice of IDE (f.ex IntelliJ or VS Code)

1. Clone the repo 
````
git clone https://github.com/your-username/grid-challenge.git
cd grid-challenge
````
2. Place / load up your input file
Check that a file names `test.txt` is located inside the `resources/` folder, or edit the code after adding the approriate file into the `resources/` folder on this line:
````
Scanner scanner = new Scanner(new File("resources/test.txt")); <-- edit `test.txt` to the chosen file
````
3. Compile and then run the code
Commands to compile via CLI or IDE:
````
javac -d out src/Main.java
````
Commands to run the program
````
java -cp out Main
````

----

### Step 1 Reading the file
<p>
To start of with, lets get into reading the test files to see what the provided grid contains, one row pr. line, or it'll
get messy quickly. Each number in a row is separated by spaces to provide a slightly structured format to more easily 
view what's inside the file. As everything depends on working with the grid, actually making it work is important since 
if I mess up reading the <i>input</i>, such as through parsing data incorrectly, every other step will break or return 
the wrong results.
</p>

````
Scanner scanner = new Scanner(new File("resources/test.txt"));
List<int[]> gridList = new ArrayList<>();

while (scanner.hasNextInt()) {
    String line = scanner.nextLine();
    if (line.trim().isEmpty()) continue;
    String[] tokens = line.trim().split("\\s+"); 
    int[] row = new int[tokens.length];
    for (int i = 0; i < row.length; i++) {
        row[i] = Integer.parseInt(tokens[i]);
    }
    gridList.add(row);
    System.out.println(Arrays.toString(row)); 
    }
scanner.close();
````

`Scanner` is built into Java and works well for reading files line-by-line though it ends up loading the entirety of a 
whatever file you give it directly into memory. If it was a particularly <i>large</i> file, methods like `BufferedReader`
would probably be better as it uses streams. And as we don't know in advance how many rows there are to the dataset in the 
File, using `List` the program be dynamically and grow / be flexible in terms of how large a dataset can be read.

This initiates the loop to iterate through the file, or data, provided for the challenge. It uses `hasNextLine()` to 
prevent exception errors and ensures that it doesn't pass malformed or blank lines of data to be parsed. It's not 
uncommon for input files to include accidental blank lines. Moving on to using the `.trim()` method to eliminate any 
leading or trailing spaces for some simple data cleanup in case of human- or user-generated errors within the dataset. 

Using regex allows for some pretty nifty filtering / formatting, and in this case helps perform some basic data-cleaning
where is reformats any data-input with multiple spaces or tabs into singular "chunks" of data formatted the same. As we 
need to perform multiplication on the data parsed, having them converted to `Integer` with `.parseInt()` early on ensures 
they aren't left as strings to avoid errors later on. Finally, each integer found is stored in an `int[]` array for 
efficient storage and makes the eventual conversion into a 2D array easier to handle. Finally, closing the 
scanner lets the system release resources (such open file handles) and is generally "good practice" to avoid memory leaks
or file locking - especially in larger or longer-running programs.

----

### Step 2 Converting the List into a 2D array

While just using `List<int[]>` works fine, converting it to a 2D array (in this case `int[][]`) makes for a more intuitive 
solution to the problem as well as making it easier to write loops and access the different elements in the array. This
(hopefully) also makes the logic for the next step, <i>looping over every cell in the grid</i>, a bit easier / simpler 
to write out later.

````
int[][] grid = new int[gridList.size()][];
        for (int i = 0; i < gridList.size(); i++) {
            grid[i] = gridList.get(i);
        }
````

To ask the expensive question: <i>"Why not just combine the parsing and array conversion steps?"</i>. While it's
technically possible to do both in a single pass, the performance gain is negligible for this particular case and not 
worth the added complexity. The brute-force method product scan will likely already dominate the runtime with a 
probable runtime of O($n^2$ x sequence length) due to the need to check every starting point in the grid across four
directions. In contrast, the initial parsing and array conversion steps are only O($n$) and have negligible impact on the 
overall runtime. Given that the dataset is small and the dimensions are known ahead of time, the two-step approach 
simplifies implementation and avoids premature optimization. It also keeps the code simpler, easier to debug and reason.
This approach favors an easier sense of clarity and maintainability instead of micro-optimizations that would likely 
only benefit the software at scale or implemented as a separate tooling. That said, for real-time systems or large-scale
datasets, it would indeed be more efficient to preallocate the array or use a streaming parser to minimize memory usage 
and reduce overall overhead. In context of this challenge, however, the current design reflects a practical trade-off 
leaning into a minimal, though functional, working MVP. 


----

### Step 3 Looping over each cell in the grid

<p>
Asking the remainder of the initial challenge question: <i>"What am I searching for?"</i>. With this in mind, the goal 
is to find the largest product of four consecutive numbers in the grid. Which should be relatively simple, and 
straight-forwards. Non-problematic if it was <i>only</i> forwards, but in making a grid out of the parsed data, theres 
more then one possible direction which means it needs to be able to scan multiple directions.
</p>

- Horizontal 
- Vertical 
- Diagonal (↘)
- Diagonal (↙)

````
int lengthOfSequence = 4;
int maxProduct = 0;

for (int row = 0; row < grid.length; row++) {
    for (int col = 0; col < grid[0].length; col++) {
        // directional checks to be implemented...
    }
}
````

The not-so-secret trick, is to brute-force it. Writing a pair of nested loops that iterate through every single cell in
the grid and treat it as a potential start of a 4-number sequence. At each cell, then checking in all four directions, 
but only after checking to ensure it doesn't iterate out-of-bounds of the edge grid and launching copious unnecessary 
`ArrayIndexOutOfBoundsException` errors. While not sophisticated or elegant, it's rather exhaustive and ensures that 
<i>every</i> possible combination is checked. Algorithmically, this provides an O($n^2$ x sequence length) solution, where 
`n` presents the width/height of the grid. `sequence length` is set at a fixed constant of `4` and is reasonably within 
the bounds of the grid (20 x 20). Giving it a fixed constant also allows this to be configured in the future if the program 
was scaled or to be more user-friendly, to for example allow a user to change the overall length of the sequence checked.
This also helps avoid <i>magic</i> numbers scattered throughout the logic and overall spaghetti-ness of the code. Overall
not clever or optimized, but it does the job with minimal complexity and no need to implement advanced or fancy data 
structures to reach an MVP that just works.


----

### Step 4 Directional checking

Now that each very cell in the grid has been thoroughly looped over, the next step is to check the four directions 
outlined above from each position iterated over, to find the highest product of `sequence length`'s adjacent numbers. 
The <i>heart-and-soul</i> of the brute-force method and approach, so to speak. 

````
// horizontal check W--E
if (col + lengthOfSequence <= grid[0].length) {
    int product = 1;
    List<Integer> sequence = new ArrayList<>();
    for (int k = 0; k < lengthOfSequence; k++) {
        int value = grid[row][col + k];
        product *= value;
        sequence.add(value);
        }
````

Each direction is implemented as its own code block with: a bounds-check (so it doesn't go out of bounds and find those 
unnecessary `ArrayIndexOutOfBoundsException`), a loop to multiply the numbers of the given direction, and a comparison 
against the current `maxProduct` - in case it finds a new largest number. So, no use of advanced or fancy data-structures, 
just the power of `if` statements and index arithmetic. Each direction check performs a constant number of operations 
`4`, so even though the nested loops gives O($n^2$) over the grid, the overall complexity is still O($n^2$ x sequence 
length). Since `sequence length` is a fixed constant, this is effectively O($n^2$). If we were checking variable-length 
sequences from `1` to `n`, each direction check would become O($n$), pushing the total complexity to O($n^3$). Overall, 
the trick lies not in geometric rotations, but in controlled index manipulations. Instead of 'turning' in a direction, 
it just changed how it walks through the grid with each of the other directions following the same template, just with a 
different indexing logic. 

- Horizontal with `grid[row][col + k]`
- Vertical with `grid[row + k][col]`
- Diagonal (↘) with `grid[row + k][col + k]`
- Diagonal (↙)  with `grid[row + k ][col - k]`

So why didn’t I refactor this into a single method with delta values for movement? In a brute-force context, adding 
abstraction would reduce clarity and increase overhead which would result in a net loss. Again, sometimes duplication is 
simply cheaper than abstraction when your goal is correctness, not architectural elegance. That said, if this were part 
of a larger toolkit or performance-critical system, turning this into a general-purpose directional scan with (`rowDelta`,
`colDelta`) pairs would absolutely be the way to go to make it overall more robust.

----

### Step 5 Printing the final results

<p>
To close it all out after nesting loops, <i>less-than-optimal</i> use of the indexing system: it's time to print out the
maximum product found, along with the sequence of integers used in the multiplication, their positions and the overall
position they were heading in. 
</p>

````
// tracks position
List<Integer> maxSequence = new ArrayList<>();
String maxDirection = "";
int maxRow = -1;
int maxCol = -1;

// tracks direction found, and which integers are used
if (product > maxProduct) {
    maxProduct = product;
    maxSequence = sequence;
    maxDirection = "Horizontal";
    maxRow = row;
    maxCol = col;
}

// basic CLI GUI
System.out.println("Maximum product found is: " + maxProduct);
System.out.println("Sequence of integers: " + maxSequence);
System.out.printf("Starting at row %d, column %d\n", maxRow, maxCol);
System.out.println("Direction of sequence found: " + maxDirection);
````

The direction tracking is repeated (with corrected direction labeling) in each of the directional checks, where it updates
the stored max value only when it finds a new or better one. And to avoid fancy uses of `JavaFX` or `Swing` or terminal 
rendering, it simply ends with a basic but functional `System.out` print statement of the above-mentioned values found. 
This allows some basic form of user functionality to provide a trace of where in the grid the <i>mathing</i> occurred and 
to verify that the directional logic actually works and didn't quietly fail somewhere unexpected - making it easy to 
validate, log and / or extend some day.

----

### Closing thoughts and post-mortem

This solution doesn’t rely on sophisticated algorithms or exotic data structures, which is entirely by design. It’s 
a straightforward, brute-force approach that walks through each cell in the grid and checks in all four directions for 
the highest product of a fixed-length sequence. It doesn’t aim to be clever but rather be correct, predictable, and 
easy to understand. There’s value in that kind of simplicity, especially for problems with well-defined constraints. 
With `sequence length` fixed and the input size rather modest, an exhaustive search offers an okay balance of clarity 
(or simplicity) and reliability. The code is also deliberate in its duplication, avoiding unnecessary abstraction in 
favor of transparency. This might not scale to production-level systems, or be technically impressive, but for an MVP 
or one-off code challenge, it reduces complexity without compromising on correctness.

Could this be refactored into a more elegant or generalized utility with directional deltas and modular methods? 
<i>Absolutely</i>, and in a different context, that would probably be the better choice. Similarly, if the input grid 
were significantly larger or the sequence length made variable, performance would need to take re-priority, and some 
different planning. But for a fixed 20×20 grid and a clear objective, this solution delivers a result that’s accurate, 
readable, and easy to follow. In the end, the goal was to solve the problem cleanly, not <i>invent</i> a new one. 
And in that regard, this code achieves just that, exactly what it needs to.

----

### Limitations and edge-cases

If the intention was to add scalability or a more robust / defensive programing paradigm, it's important to consider 
edge cases that could affect the output, especially when dealing with real-world input data, larger problem instances 
or user-generated errors. One such case is the presence of zeroes in the grid. Any sequence that includes a zero will 
result in a product of zero, regardless of how large the other numbers are, <i>and thus a problem</i>. While the current
implementation accounts for this to a certain degree, there's always performance to be eked out for an improved programming 
solution. For example by short-circuiting the multiplication loop whenever a zero is encountered. This avoids 
unnecessary calculations and resources which may improve performance slightly, especially in larger grids with sparse data.

````
// Example of how a skip-calculation culd be implemented to guard agains '0's
int product = 1;
for (int k = 0; k < sequenceLength; k++) {
    int value = grid[row][col + k];
    if (value == 0) {
        product = 0;
        break;
    }
    product *= value;
}
````

Another common concern could be the handling of negative numbers. These should not be filtered or skipped, as multiplying an 
even number of negative integers yields a positive product. For example, a sequence like `-6, -4, 2, 3` produces a 
positive product of 144. It's essential that the logic treats negative numbers as valid components of a sequence and 
includes them in the calculation without bias. Though a sabotaged solution with, say, every second integer being negative, 
or some form of irregular frequency or pattern this will no longer be the case. 

Beyond values, grid shape and size are critical. The solution is designed to work with grids of any size, not just 20×20. 
However, it does assume that the grid is rectangular (So all rows have the same number of columns). If the input file 
contains rows of unequal lengths, this can lead to runtime errors or undefined behavior or as commonly referred to: 
<i>square-peg-round-hole</i> problems. To resolve this, a check should be added during parsing to ensure uniform row lengths. 
If an inconsistency is found, could program can throw an error message to inform the user that the grid is malformed.

````
// Example of a possible error implemenation 
for (int[] row : grid) {
    if (row.length != grid[0].length) {
        throw new IllegalArgumentException("Inconsistent row lengths detected.");
    }
}
````

Making the sequence length configurable could also be another useful enhancement. While this challenge specifically calls for 
groups of four numbers, introducing a constant (or user-input changeable value) for sequence length would improve flexibility. 
It also aligns with good, <i>or better</i>, software engineering practices by avoiding magic numbers and making the program more
scalable / flexible.

And finally, a basic file validation before parsing would help prevent avoidable runtime errors. Confirming that the file exists 
and is readable before attempting to scan its contents ensures that missing or misnamed files are caught early, with 
informative errors rather than stack traces.

````
// Example of a simple file verification parser
File file = new File("resources/test.txt");
if (!file.exists() || !file.canRead()) {
    throw new FileNotFoundException("File not found or unreadable.");
}
````

Altogether, these enhancements may not be required for a coding challenge with well-formed or known input, but it would 
demonstrate foresight and a mature approach to coding. In real-world applications, input is rarely clean or predictable, 
and taking steps to thoroughly handle edge cases can significantly improve the reliability and user experience of the program.

----

### Next steps or improvements

- Refactor the directional logic using `(rowDelta, colDelta)` deltas for better modularity and reusability.
- Add support for dynamic grid input and argument parsing for more flexibility.
- Extract the logic into testable methods and add unit tests.
- Build a simple visual representation or CLI highlight tool to better showcase the grid and sequence.
- Overall refactor into OOP or SOLID principals to for best-practice and avoid monolithic structure.

----
