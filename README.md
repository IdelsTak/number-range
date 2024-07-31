# NumberRange Utility

This Java application processes number ranges from command-line arguments or standard input, with options for sorting, handling negative numbers, and ensuring uniqueness.

## Usage

```sh
java NumberRange.java [options] [NUM-RNG]
```

### Options

- `-h`: Print this help message.

- `-s`  : Sort the numbers within a line before printing.
  - Note: Sort only occurs within a line (from stdin) or within a single argument (from CLI), not globally.
  
- `-n`: Input has negative numbers (input must use `:` instead of `-` for range separator).

- `-u`: Print only unique numbers (implies sorting).

### NUM-RNG

Number Range in human-readable format. Pass `-` for stdin. The number range can be a comma-separated list of single range numbers, which can be a single number, `start:end`, or `start:step:end`. For non-negative numbers, `-` can be used instead of `:` for range separator. The range separator will be auto-detected unless the `-n` (negative) flag is on.

## Examples

```sh
java NumberRange.java 1-4             # 1, 2, 3, 4
java NumberRange.java 1:4             # 1, 2, 3, 4
java NumberRange.java -1:2            # -1, 0, 1, 2
java NumberRange.java 5:1:2           # 5, 4, 3, 2
java NumberRange.java 30:32,1:4,31    # 30, 31, 32, 1, 2, 3, 4, 31
java NumberRange.java 30:32,1:4 -s    # 1, 2, 3, 4, 30, 31, 31, 32
java NumberRange.java 30:32,1:4 -u    # 1, 2, 3, 4, 30, 31, 32
```

## Running the utility

```sh
java NumberRange.java [options] [NUM-RNG]
```
