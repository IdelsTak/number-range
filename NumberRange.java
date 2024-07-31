import java.util.*;

public class NumberRange {
  public static void main(String[] args) {
    boolean sort = false;
    boolean negativeAllowed = false;
    boolean unique = false;
    List<String> ranges = new ArrayList<>();

    // Parse command-line arguments
    for (String arg : args) {
      switch (arg) {
        case "-h" -> {
          printHelp();
          return;
        }
        case "-s" -> sort = true;
        case "-n" -> negativeAllowed = true;
        case "-u" -> {
          unique = true;
          sort = true;
        }
        default -> ranges.add(arg);
      }
    }

    // If no ranges provided, read from stdin
    if (ranges.isEmpty() || ranges.get(0).equals("-")) {
      try (Scanner scanner = new Scanner(System.in)) {
        while (scanner.hasNextLine()) {
          processLine(scanner.nextLine(), sort, negativeAllowed, unique);
        }
      }
    } else {
      processLine(String.join(",", ranges), sort, negativeAllowed, unique);
    }
  }

  private static void processLine(
      String line, boolean sort, boolean negativeAllowed, boolean unique) {
    List<Integer> numbers = new ArrayList<>();
    String[] ranges = line.split(",");

    for (String range : ranges) {
      numbers.addAll(expandRange(range, negativeAllowed));
    }

    if (sort || unique) {
      Collections.sort(numbers);
    }

    if (unique) {
      numbers = new ArrayList<>(new LinkedHashSet<>(numbers));
    }

    System.out.println(
        String.join(", ", numbers.stream().map(Object::toString).toArray(String[]::new)));
  }

  private static List<Integer> expandRange(String range, boolean negativeAllowed) {
    List<Integer> numbers = new ArrayList<>();
    String separator = negativeAllowed ? ":" : (range.contains(":") ? ":" : "-");
    String[] parts = range.split(separator);

    if (parts.length == 1) {
      numbers.add(Integer.parseInt(parts[0]));
    } else if (parts.length == 2 || parts.length == 3) {
      int start = Integer.parseInt(parts[0]);
      int end = Integer.parseInt(parts[parts.length - 1]);
      int step = parts.length == 3 ? Integer.parseInt(parts[1]) : 1;

      if (start <= end) {
        for (int i = start; i <= end; i += step) {
          numbers.add(i);
        }
      } else {
        for (int i = start; i >= end; i -= step) {
          numbers.add(i);
        }
      }
    }

    return numbers;
  }

  private static void printHelp() {
    System.out.println(
        """
                       Usage: numrng [options] [NUM-RNG]
                       Options:
                         -h: print this help message
                         -s: sort the numbers within a line before printing
                             Note: sort only occurs within a line (from stdin)
                             or within a single argument (from CLI), not globally.
                         -n: input has negative numbers
                             (input must use : instead of - for range separator)
                         -u: print only unique numbers (implies sort)
                       NUM-RNG: Number Range in human readable format. (pass - for stdin)
                                The number range can be a comma separated list of
                                single range numbers, which can be a single number
                                start:end, or start:step:end. For non-negative numbers
                                `-` can be used instead of `:` for range separator.
                                The range separator will be auto detected unless -n
                                (negative) flag is on.
                       Examples:
                         `numrng 1-4`             : 1, 2, 3, 4
                         `numrng 1:4`             : 1, 2, 3, 4
                         `numrng -1:2`            : -1, 0, 1, 2
                         `numrng 5:-1:2`          : 5, 4, 3, 2
                         `numrng 30:32,1:4,31`    : 30, 31, 32, 1, 2, 3, 4, 31
                         `numrng 30:32,1:4 -s`    : 1, 2, 3, 4, 30, 31, 31, 32
                         `numrng 30:32,1:4 -u`    : 1, 2, 3, 4, 30, 31, 32
                       """);
  }
}
