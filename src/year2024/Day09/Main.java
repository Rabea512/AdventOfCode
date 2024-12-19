package year2024.Day09;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
  public static void main(String[] args) throws IOException {
        System.out.println(
            part1("/Users/degroot/Privat/AdventOfCode/src/year2024/Day09/input_test.txt"));
    System.out.println(part1("/Users/degroot/Privat/AdventOfCode/src/year2024/Day09/input01.txt"));

    // System.out.println(part2("/Users/degroot/Privat/AdventOfCode/src/year2024/Day09/input_test.txt"));
    //
    // System.out.println(part2("/Users/degroot/Privat/AdventOfCode/src/year2024/Day09/input02.txt"));
  }

  public static int part1(String path) throws IOException {
    final Input input = readInput(path);
    final List<Block> newFileSystem = new ArrayList<>();
    for (Block block : input.blocks()) {
      newFileSystem.add(new Block(block.id(), block.count()));
    }

    int index = input.blocks().size() - 1;
    while (index >= 0) {
      final Block currentBlockToSort = newFileSystem.get(index);
      index = sortFile(currentBlockToSort, index, newFileSystem);
    }

    return calculateChecksum(newFileSystem);
  }

  private static int sortFile(Block currentBlockToSort, int index, List<Block> newFileSystem) {
    if (currentBlockToSort.id() == null) {
      return index - 1;
    }

    int indexOfEmptyBlock = findEmptyBlock(newFileSystem);
    if (indexOfEmptyBlock < 0 || indexOfEmptyBlock >= index) {
      return -1;
    }

    final Block emptyBlock = newFileSystem.get(indexOfEmptyBlock);

    if (currentBlockToSort.count() == emptyBlock.count()) {
      newFileSystem.remove(indexOfEmptyBlock);
      newFileSystem.add(indexOfEmptyBlock, currentBlockToSort);
      newFileSystem.remove(index);
      return index - 1;
    } else if (currentBlockToSort.count() < emptyBlock.count()) {
      newFileSystem.remove(indexOfEmptyBlock);
      newFileSystem.add(
          indexOfEmptyBlock, new Block(null, emptyBlock.count() - currentBlockToSort.count()));
      newFileSystem.add(indexOfEmptyBlock, currentBlockToSort);
      newFileSystem.remove(index + 1);
      return index;
    } else {
      newFileSystem.remove(indexOfEmptyBlock);
      newFileSystem.add(indexOfEmptyBlock, new Block(currentBlockToSort.id(), emptyBlock.count()));
      newFileSystem.remove(index);
      final Block newCurrentBlockToSort =
          new Block(currentBlockToSort.id(), currentBlockToSort.count() - emptyBlock.count());
      newFileSystem.add(newCurrentBlockToSort);
      return sortFile(newCurrentBlockToSort, newFileSystem.size() - 1, newFileSystem);
    }
  }

  private static int calculateChecksum(final List<Block> fileSystem) {
    int result = 0;

    int pointer = 0;
    for (int i = 0; i < fileSystem.size(); i++) {
      Block currentBlock = fileSystem.get(i);
      if (currentBlock.id() == null) {
        continue;
      }

      for (int j = 0; j < currentBlock.count(); j++) {
        result += pointer * currentBlock.id();
        pointer++;
      }
    }

    return result;
  }

  private static int findEmptyBlock(final List<Block> fileSystem) {
    for (int i = 0; i < fileSystem.size(); i++) {
      final Block block = fileSystem.get(i);
      if (block.id() == null && block.count() > 0) {
        return i;
      }
    }

    return -1;
  }

  public static int part2(String path) throws IOException {
    return 0;
  }

  public static Input readInput(String path) throws IOException {
    final List<Block> blocks = new ArrayList<>();
    try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
      String line = reader.readLine();
      for (int i = 0; i < line.length(); i++) {
        final int fileLength = Integer.parseInt(line.substring(i, i + 1));
        if (i % 2 == 0) {
          blocks.add(new Block(i / 2, fileLength));
        } else {
          if (fileLength > 0) {
            blocks.add(new Block(null, fileLength));
          }
        }
      }
    }
    return new Input(blocks);
  }

  public record Input(List<Block> blocks) {}

  public record Block(Integer id, int count) {}
}
