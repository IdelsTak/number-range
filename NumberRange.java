import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NumberRange {
    public static void main(String[] args) {
        boolean sorted = false;
        boolean negative = false;
        boolean unique = false;
        List<String> numrng = new ArrayList<>();
        boolean stdin = false;

        for (int i = 1; i < args.length; i++) {
            String arg = args[i];
            if (arg.startsWith("-")) {
                if (arg.length() == 1) {
                    stdin = true;
                }
                for (char a : arg.substring(1).toCharArray()) {
                    switch (a) {
                        case 'h':
                            printHelp(false);
                            return;
                        case 's':
                            sorted = true;
                            break;
                        case 'n':
                            negative = true;
                            break;
                        case 'u':
                            unique = true;
                            break;
                        default:
                            System.err.println("Invalid Option");
                            printHelp(true);
                            return;
                    }
                }
            } else {
                numrng.add(arg);
            }
        }

        for (String num : numrng) {
            printNumbers(num, negative, unique, sorted);
        }

        if (stdin) {
            try (Scanner scanner = new Scanner(System.in)) {
                while (scanner.hasNextLine()) {
                    printNumbers(scanner.nextLine(), negative, unique, sorted);
                }
            }
        }
    }

    private static void printNumbers(String num, boolean negative, boolean unique, boolean sorted) {
        char sep = (negative || num.contains(":")) ? ':' : '-';
        List<Long> rng;

        try {
            rng = parseNumberRange(num, sep);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            return;
        }

        if (unique) {
            rng = rng.stream().distinct().sorted().collect(Collectors.toList());
        } else if (sorted) {
            Collections.sort(rng);
        }

        rng.forEach(System.out::println);
    }

    private static List<Long> parseNumberRange(String num, char sep) {
        String[] parts = num.split(String.valueOf(sep));
        if (parts.length == 1) {
            return Collections.singletonList(Long.parseLong(parts[0]));
        } else if (parts.length == 2) {
            long start = Long.parseLong(parts[0]);
            long end = Long.parseLong(parts[1]);
            return start <= end ? range(start, end) : range(end, start);
        } else if (parts.length == 3) {
            long start = Long.parseLong(parts[0]);
            long step = Long.parseLong(parts[1]);
            long end = Long.parseLong(parts[2]);
            return range(start, step, end);
        } else {
            throw new IllegalArgumentException("Invalid number range format.");
        }
    }

    private static List<Long> range(long start, long end) {
        return Stream.iterate(start, n -> n + (start <= end ? 1 : -1))
                     .limit(Math.abs(end - start) + 1)
                     .collect(Collectors.toList());
    }

    private static List<Long> range(long start, long step, long end) {
        return Stream.iterate(start, n -> n + step)
                     .limit((Math.abs(end - start) / Math.abs(step)) + 1)
                     .collect(Collectors.toList());
    }

    private static void printHelp(boolean err) {
        String helpMsg = """
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
            """;
        if (err) {
            System.err.println(helpMsg);
        } else {
            System.out.println(helpMsg);
        }
    }
}
