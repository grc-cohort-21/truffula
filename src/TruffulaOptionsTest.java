import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class TruffulaOptionsTest {

  // Tests performance with correct input

  @Test
  void testValidDirectoryIsSet(@TempDir File tempDir) throws FileNotFoundException {
    // Arrange: Prepare the arguments with the temp directory
    File directory = new File(tempDir, "subfolder");
    directory.mkdir();
    String directoryPath = directory.getAbsolutePath();
    String[] args = {"-nc", "-h", directoryPath};

    // Act: Create TruffulaOptions instance
    TruffulaOptions options = new TruffulaOptions(args);

    // Assert: Check that the root directory is set correctly
    assertEquals(directory.getAbsolutePath(), options.getRoot().getAbsolutePath());
    assertTrue(options.isShowHidden());
    assertFalse(options.isUseColor());
  }

  @Test
  void testValidDirectoryPathIsSetWithArgumentsReversed(@TempDir File tempDir) throws FileNotFoundException {
    // Arrange: Prepare the arguments with the temp directory
    File directory = new File(tempDir, "subfolder");
    directory.mkdir();
    String directoryPath = directory.getAbsolutePath();
    String[] args = {"-h", "-nc", directoryPath};
    // Act: Create TruffulaOptions instance
    TruffulaOptions options = new TruffulaOptions(args);
    // Assert: Check that the root directory is set correctly
    assertEquals(directory.getAbsolutePath(), options.getRoot().getAbsolutePath());
    assertTrue(options.isShowHidden());
    assertFalse(options.isUseColor());
  }

  @Test
  void testValidDirectoryPathIsSetWithUseColorArgumentOnly(@TempDir File tempDir) throws FileNotFoundException {
    // Arrange: Prepare the arguments with the temp directory
    File directory = new File(tempDir, "subfolder");
    directory.mkdir();
    String directoryPath = directory.getAbsolutePath();
    String[] args = {"-nc", directoryPath};
    // Act: Create TruffulaOptions instance
    TruffulaOptions options = new TruffulaOptions(args);
    // Assert: Check that the root directory is set correctly
    assertEquals(directory.getAbsolutePath(), options.getRoot().getAbsolutePath());
    assertFalse(options.isShowHidden());
    assertFalse(options.isUseColor());
  }

  @Test
  void testValidDirectoryPathIsSetWithShowHiddenArgumentOnly(@TempDir File tempDir) throws FileNotFoundException {
    // Arrange: Prepare the arguments with the temp directory
    File directory = new File(tempDir, "subfolder");
    directory.mkdir();
    String directoryPath = directory.getAbsolutePath();
    String[] args = {"-h", directoryPath};
    // Act: Create TruffulaOptions instance
    TruffulaOptions options = new TruffulaOptions(args);
    // Assert: Check that the root directory is set correctly
    assertEquals(directory.getAbsolutePath(), options.getRoot().getAbsolutePath());
    assertTrue(options.isShowHidden());
    assertTrue(options.isUseColor());
  }

  @Test
  void testValidDirectoryPathIsSetNoAdditionalArguments(@TempDir File tempDir) throws FileNotFoundException {
    // Arrange: Prepare the arguments with the temp directory
    File directory = new File(tempDir, "subfolder");
    directory.mkdir();
    String directoryPath = directory.getAbsolutePath();
    String[] args = {directoryPath};
    // Act: Create TruffulaOptions instance
    TruffulaOptions options = new TruffulaOptions(args);
    // Assert: Check that the root directory is set correctly
    assertEquals(directory.getAbsolutePath(), options.getRoot().getAbsolutePath());
    assertFalse(options.isShowHidden());
    assertTrue(options.isUseColor());
  }

// Tests performance with Incorrect input

  @Test
  void testDirectoryPathArgumentMissingThrowsIllegalArgument(){
    // Arrange: Prepare the arguments without a directory
    String[] args = {"-nc", "-h"};
    // Act: Create TruffulaOptions instance
    // Assert: Check that the illegal argument is thrown with no directory set
    assertThrows(IllegalArgumentException.class,
    () -> new TruffulaOptions(args),"Expected method to throw IllegalArgumentException when the path argument is missing.");
  }

  @Test
  void testSpecifiedDirectoryPathDoesNotExistThrowsFileNotFound(@TempDir File tempDir) throws FileNotFoundException {
    // Arrange: Prepare the arguments with the temp directory
    File directory = new File(tempDir, "subfolder");
    directory.mkdir();
    String directoryPath = directory.getAbsolutePath();
    String[] args = {"-h", "-nc", directoryPath};
    directory.delete();
    // Act: Create TruffulaOptions instance
    // Assert: Check that the file not found is thrown with invalid directory set
    assertThrows(FileNotFoundException.class,
    () -> new TruffulaOptions(args),"Expected method to throw FileNotFoundException for a path that does not exist.");
  }

  @Test
  void testSpecifiedDirectoryPathPointsToAFileThrowsFileNotFound(@TempDir File tempDir) throws IOException {
    // Arrange: Prepare the arguments and temporary file
    File file = new File(tempDir, "this_is_a_file.txt");
    file.createNewFile();
    String filePath = file.getAbsolutePath();
    String[] args = {"-nc", "-h", filePath};
    // Act: Create TruffulaOptions instance
    // Assert: Check that the illegal argument is thrown with no directory set
    assertThrows(FileNotFoundException.class,
    () -> new TruffulaOptions(args),"Expected method to throw FileNotFoundException for path that points to a file.");
  }

  @Test
  void testValidDirectoryPathWithIllegalUseColorArgumentThrowsIllegalArgument(@TempDir File tempDir){
    // Arrange: Prepare the arguments with the temp directory
    File directory = new File(tempDir, "subfolder");
    directory.mkdir();
    String directoryPath = directory.getAbsolutePath();
    String[] args = {"-bad", "-h", directoryPath};
    // Act: Create TruffulaOptions instance
    // Assert: Check that the illegal argument is thrown with bad argument
    assertThrows(IllegalArgumentException.class,
    () -> new TruffulaOptions(args),"Expected method to throw IllegalArgumentException for invalid useColor argument.");
  }

  @Test
  void testValidDirectoryPathWithIllegalShowHiddenArgumentThrowsIllegalArgument(@TempDir File tempDir){
    // Arrange: Prepare the arguments with the temp directory
    File directory = new File(tempDir, "subfolder");
    directory.mkdir();
    String directoryPath = directory.getAbsolutePath();
    String[] args = {"-bad", "-nc", directoryPath};
    // Act: Create TruffulaOptions instance
    // Assert: Check that the illegal argument is thrown with bad argument
    assertThrows(IllegalArgumentException.class,
    () -> new TruffulaOptions(args),"Expected method to throw IllegalArgumentException for invalid showHidden argument.");
  }

  @Test
  void testValidDirectoryWhiteSpaceArgumentThrowsIllegalArgument(@TempDir File tempDir){
    // Arrange: Prepare the arguments with the temp directory
    File directory = new File(tempDir, "subfolder");
    directory.mkdir();
    String directoryPath = directory.getAbsolutePath();
    String[] args = {"    ", "-h", directoryPath};
    // Act: Create TruffulaOptions instance
    // Assert: Check that the illegal argument is thrown with bad argument
    assertThrows(IllegalArgumentException.class,
    () -> new TruffulaOptions(args),"Expected method to throw IllegalArgumentException for an empty argument.");
  }

  @Test
  void testValidDirectoryPathFirstArgumentThrowsIllegalArgument(@TempDir File tempDir){
    // Arrange: Prepare the arguments with the temp directory
    File directory = new File(tempDir, "subfolder");
    directory.mkdir();
    String directoryPath = directory.getAbsolutePath();
    String[] args = {directoryPath, "-nc", "-h", };
    // Act: Create TruffulaOptions instance
    // Assert: Check that the illegal argument is thrown with bad argument
    assertThrows(IllegalArgumentException.class,
    () -> new TruffulaOptions(args),"Expected method to throw IllegalArgumentException if path is not the last argument.");
  }

  @Test
  void testValidDirectoryPathSecondArgumentThrowsIllegalArgument(@TempDir File tempDir){
    // Arrange: Prepare the arguments with the temp directory
    File directory = new File(tempDir, "subfolder");
    directory.mkdir();
    String directoryPath = directory.getAbsolutePath();
    String[] args = {"h", directoryPath, "-nc"};
    // Act: Create TruffulaOptions instance
    // Assert: Check that the illegal argument is thrown with bad argument
    assertThrows(IllegalArgumentException.class,
    () -> new TruffulaOptions(args),"Expected method to throw IllegalArgumentException if path is not the last argument.");
  }

  @Test
  void testEmptyArgsArrayThrowsIllegalArgument(@TempDir File tempDir){
    // Arrange: Prepare the arguments
    String[] args = {};
    // Act: Create TruffulaOptions instance
    // Assert: Check that the illegal argument is thrown with bad argument
    assertThrows(IllegalArgumentException.class,
    () -> new TruffulaOptions(args),"Expected method to throw IllegalArgumentException if args array is empty.");
  }
}