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
  void testValidDirectoryIsSetReverseArgs(@TempDir File tempDir) throws FileNotFoundException {
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
  void testValidDirectoryIsSetColorArgOnly(@TempDir File tempDir) throws FileNotFoundException {
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
  void testValidDirectoryIsSetHiddenArgOnly(@TempDir File tempDir) throws FileNotFoundException {
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
  void testValidDirectoryIsSetNoAdditionalArgs(@TempDir File tempDir) throws FileNotFoundException {
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
  void testPathArgumentMissingThrowsIllegalArgument(){
    // Arrange: Prepare the arguments without a directory
    String[] args = {"-nc", "-h"};
    // Act: Create TruffulaOptions instance
    // Assert: Check that the illegal argument is thrown with no directory set
    assertThrows(IllegalArgumentException.class,
    () -> new TruffulaOptions(args),"Expected method to throw IllegalArgumentException when the path argument is missing.");
  }


  @Test
  void testSpecifiedDirectoryDoesNotExistThrowsFileNotFound(){
    // Arrange: Prepare the arguments
    String[] args = {"-nc", "-h", "/folder/folder/bad_directory"};
    // Act: Create TruffulaOptions instance
    // Assert: Check that the illegal argument is thrown with no directory set
    assertThrows(FileNotFoundException.class,
    () -> new TruffulaOptions(args),"Expected method to throw FileNotFoundException for a path that does not exist.");
  }

  @Test
  void testSpecifiedDirectoryPointsToAFileThrowsFileNotFound(@TempDir File tempDir) throws IOException {
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
  void testValidDirectoryIllegalUseColorArgumentThrowsIllegalArgument(@TempDir File tempDir){
    // Arrange: Prepare the arguments with the temp directory
    File directory = new File(tempDir, "subfolder");
    directory.mkdir();
    String directoryPath = directory.getAbsolutePath();
    String[] args = {"-bad", "-h", directoryPath};
    // Act: Create TruffulaOptions instance
    // Assert: Check that the illegal argument is thrown with bad argument
    assertThrows(IllegalArgumentException.class,
    () -> new TruffulaOptions(args),"Expected method to throw IllegalArgumentException for an invalid color argument.");
  }

  
  @Test
  void testValidDirectoryIllegalShowHiddenArgumentThrowsIllegalArgument(@TempDir File tempDir){
    // Arrange: Prepare the arguments with the temp directory
    File directory = new File(tempDir, "subfolder");
    directory.mkdir();
    String directoryPath = directory.getAbsolutePath();
    String[] args = {"-bad", "-nc", directoryPath};
    // Act: Create TruffulaOptions instance
    // Assert: Check that the illegal argument is thrown with bad argument
    assertThrows(IllegalArgumentException.class,
    () -> new TruffulaOptions(args),"Expected method to throw IllegalArgumentException for an invalid color argument.");
  }

  @Test
  void testValidDirectoryWhiteSpaceArgument(@TempDir File tempDir){
    // Arrange: Prepare the arguments with the temp directory
    File directory = new File(tempDir, "subfolder");
    directory.mkdir();
    String directoryPath = directory.getAbsolutePath();
    String[] args = {" ", "-h", directoryPath};
    // Act: Create TruffulaOptions instance
    // Assert: Check that the illegal argument is thrown with bad argument
    assertThrows(IllegalArgumentException.class,
    () -> new TruffulaOptions(args),"Expected method to throw IllegalArgumentException for an empty argument.");
  }
}