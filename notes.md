# Truffula Notes
As part of Wave 0, please fill out notes for each of the below files. They are in the order I recommend you go through them. A few bullet points for each file is enough. You don't need to have a perfect understanding of everything, but you should work to gain an idea of how the project is structured and what you'll need to implement. Note that there are programming techniques used here that we have not covered in class! You will need to do some light research around things like enums and and `java.io.File`.

PLEASE MAKE FREQUENT COMMITS AS YOU FILL OUT THIS FILE.

## App.java
- main application that calls the other classes
- takes in command line options (String[] args) and passes those to the truffelaOptions 
- truffelaOptions object is passed to truffulaPrinter

*To Do**
- [ ] Implement public static void main(String[] args) (line 43)


## ConsoleColor.java
- enum
- a list of valid terminal colors with terminal string color codes
- enum used to define a collection of named values, making code more readable 
- To apply a color, prepend the ANSI code to the text. Example : ConsoleColor.GREEN
- append the RESET code ("\033[0m") after the text to reset the color back to default. Example: ConsoleColor.RESET


## ColorPrinter.java / ColorPrinterTest.java

### ColorPrinter.java
- A utility class for printing colored text to a PrintStream
- A PrintStream adds functionality to another output stream, namely the ability to print representations of various data values conveniently.
- You can create your own PrintStream objects to write to files, buffers, etc.  (System.out is a predefined PrintStream that prints to the console.)

**To Do**
 - [ ] Implement the print(String message, boolean reset) method (line 88)

### ColorPrinterTest.java
- testPrintlnWithRedColorAndReset:
 1. ByteArrayOutputStream outputStream = new ByteArrayOutputStream(); *Sets up an in-memory stream (outputStream) to capture the printed output (instead of printing to the terminal).*
 2. PrintStream printStream = new PrintStream(outputStream); *Adds PrintStream functionality to the ByteArrayOutputStream.*
 3. ColorPrinter printer = new ColorPrinter(printStream); *Constructs a ColorPrinter with the specified PrintStream.*
 4. String result = outputStream.toString(); *Converts the buffer's contents (what was printed) into a string.*

**To Do**
-[x] Implement multiple tests to validate ColorPrinter methods/behavior (should be able to validate multiple methods per test).

- Methods:
 - [x] getCurrentColor
 - [x] setCurrentColor - [x] BLACK - [x] RED - [x] GREEN - [ ] YELLOW - [x] BLUE - [x] PURPLE - [x] CYAN - [x] WHITE - [x] RESET
 - [x] println (String message)
 - [x] println (String message, boolean reset)
 - [x] print (String message)
 - [x] print (String message, boolean reset)

- Behaviors: 
- [x] Correct colors applied (default constructor/ parameterized constructor)
- [x] Color reset after message by default
- [x] Color is not reset if reset flag is false
- [x] print / println output correct message


## TruffulaOptions.java / TruffulaOptionsTest.java

### TruffulaOptions.java
- Represents configuration options for controlling how a directory tree is displayed.

- Flags:
 - -h   : Show hidden files (defaults to false).
 - -nc  : Do not use color (color is enabled by default).

**To Do**
 [x] Implement public TruffulaOptions(String[] args) constructor (line 103)

### TruffulaOptionsTest.java

- @TempDir File tempDir JUnit feature that provides a temporary directory that is cleaned up after the test.
- A subdirectory called "subfolder" is created inside that temporary test directory.
- This simulates a valid directory path that would be passed via the command line.
- directory.getAbsolutePath(); Returns the absolute pathname string of this abstract pathname.
- String[] args = {"-nc", "-h", directoryPath}; Simulates command-line arguments 

**To Do**
-[x] Implement multiple tests to validate TruffulaOptions methods/behavior (should be able to validate multiple methods per test).

- Methods:
 - [x] getRoot
 - [x] isShowHidden
 - [x] toString
 - [x] isUseColor

- Behaviors:
- [x] valid path argument with no flag arguments
- [x] valid path argument with only useColor flag argument
- [x] valid path argument with only showHidden flag argument
- [x] valid path argument with both flag arguments
- [x] valid path argument with both flag arguments (arguments reversed)
- [x] valid flag arguments with no path argument
- [x] valid flag arguments with non existent directory path
- [x] valid flag arguments with directory path argument that points to file
- [x] invalid useColor argument with valid path argument
- [x] invalid showHidden argument with valid path argument 
 -[x] invalid whiteSpace arguments with valid path argument 
- [x] valid file path provided in first argument location
- [x] valid file path provided in second argument location
- [x] invalid empty args array

## TruffulaPrinter.java / TruffulaPrinterTest.java

### TruffulaPrinter.java
- TruffulaPrinter is responsible for printing a directory tree structure with optional colored output.

**To Do**
- [ ] Implement printTree method. (line 105)

### TruffulaPrinterTest.java
- Verifies that the directory tree is printed correctly.

**To Do**
- [ ] Implement additional tests?


## AlphabeticalFileSorter.java
- Utility class for sorting an array of files alphabetically by name, ignoring case differences.
- A File object in Java represents a pathname. A reference to a file or directory on your system. It does not contain the file’s data, just information about where the file is and what you can do with it.
- Lamda Function is anonymous function that can be treated as a value and passed as an argument to a method.

- Arrays.sort(files, (f1, f2) -> f1.getName().compareToIgnoreCase(f2.getName()));

 1. Arrays.sort(...)	*Sorts all of the file objects within the array in place*
 2. files	*The array of file objects tp be sorted*
 3. (f1, f2) -> *lambda function that compares two files in the array*
 4. f1.getName()	*Returns the name of the first file as a String*
 5. compareToIgnoreCase(...)	*Compares the two file name strings alphabetically, ignoring case*
 6. f2.getName() *Returns the the name of the second file as a String*