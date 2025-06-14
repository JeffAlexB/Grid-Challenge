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

All of which should be a fun, (<i>or, maybe not fun, but at least interesting</i>) task to try and solve. As a 'DSA challenge', I'll 
try and strive to solve the task while emphasizing the importance of building a correct / functioning and readable 
solution before iterating into optimization. To 

----

## Proposed solution? 

<p>
Looking at the problem-set, while considering the requirements, it seems to be quite possible to use a 'bruteforce' 
solution for the challenge. This would be done by simply checking every possible set within four adjacent numbers in 
the grid through looping over every cell and manually check all four directions where a sequence of four numbers fit; 
calculating their product each time. Simple - <i>yes</i>, efficient - <i>no</i>, elegant - again: <i>nay</i>. But 
would be straight forwards-<i>ish</i> which helps to learn or understand the constraints, logic and constraints of the 
problem at hand. So with-out further ado. 
</p>

----

### Step 1 - Reading the file line-by-line
<p>
To start of with, lets get into reading the test files to see what the provided grid contains, one row pr. line, or it'll
get messy quickly. Each number in a row is separated by spaces to provide a slightly structured format to more easily 
view what's inside the file. As everything depends on working with the grid, actually making it work is important since 
if I mess up reading the <i>input</i>, such as through parsing data incorrectly, every other step will break or return 
the wrong results.
</p>

#### Step 1.1
````
Scanner scanner = new Scanner(new File("resources/test.txt"));
List<int[]> gridList = new ArrayList<>();
````
<p>
'Scanner' is built into Java and works well for reading files line-by-line though it ends up loading the entirety of a 
whatever file you give it directly into memory. If it was a particularly <i>large</i> file, methods like 'BufferedReader'
would probably be better as it uses streams. And as we don't know in advance how many rows there are to the dataset in the 
file, using 'List' the program be dynamically and grow / be flexible in regards to the dataset 
</p>

#### Step 1.2
````
while (scanner.hasNextInt()) {
    String line = scanner.nextLine();
    if (line.trim().isEmpty()) continue;
````
<p>
This initiates the loop to iterate through the file, or data, provided for the challenge. It uses 'hasNextLine()' to 
prevent exception errors and ensures that it doesn't pass malformed or blank lines of data to be parsed. It's not 
uncommon for input files to include accidental blank lines. Moving on to using the .trim() method to eliminate any 
leading or trailing spaces for some simple data cleanup in case of human- or user-generated errors within the dataset. 
</p>

#### Step 1.3
````
    String[] tokens = line.trim().split("\\s+"); 
    int[] row = new int[tokens.length];
    for (int i = 0; i < row.length; i++) {
        row[i] = Integer.parseInt(tokens[i]);
    }
    gridList.add(row);
````

<p>
Using regex allows for some pretty nifty filtering / formatting, and in this case helps perform some basic data-cleaning
where is reformats any data-input with multiple spaces or tabs into singular "chunks" of data formatted the same. As we 
need to perform multiplication on the data parsed, having them converted to 'Integer' with '.parseInt' early on ensures 
they aren't left as strings to avoid errors later on. Finally, each integer found is stored in an 'int[]' array for 
efficient storage and makes the eventual conversion into a 2D array easier to handle. 
</p>

#### Step 1.4
````
    System.out.println(Arrays.toString(row)); 
    }
scanner.close();
````
<p>
A simple print statement to visualize what's being parsed from the file, it also provides som basic sense of debugging 
in case of any issues later on and to help verify exactly what from the input file is being read. Finally, closing the 
scanner lets the system release resources (such open file handles) and is generally "good practice" to avoid memory leaks
or file locking - especially in larger or longer-running programs. 
</p>

----