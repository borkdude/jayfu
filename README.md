# jayfu

Learn how to make a native Clojure CLI with GraalVM native-image and SCI!

This example is set up around an example CLI, `jayfu`, that reads JSON from
stdin and transforms it using a function which is dynamically evaluated:

``` clojure
$ echo '{"a": {"b": 2}}' | ./jayfu -f '#(-> % :a :b)' -k keyword
2
```

A talk that guides you through this project is coming soon.

## Prerequisites

- Download [GraalVM](https://www.graalvm.org/downloads/). Just download it to
  your Downloads folder and unzip the archive. No further installation
  required. This tutorial is based on version 21.1.0 JDK11.

- Set the `GRAALVM_HOME` environment variable. E.g.:

  `export GRAALVM_HOME=/Users/borkdude/Downloads/graalvm-ce-java11-21.1.0/Contents/Home`

- To run with Clojure or Java, you will need to have a
  Java 8 or 11 installation available. You can also use the downloaded GraalVM for this.

- Install [babashka](https://github.com/babashka/babashka#installation) (0.4.1 or higher).

### Windows

Always use `cmd.exe` for executing GraalVM compilation, do not use PowerShell.

On Windows, install Visual Studio 2017 or 2019 and in `cmd.exe` load:

```
"C:\Program Files (x86)\Microsoft Visual Studio\2019\Community\VC\Auxiliary\Build\vcvars64.bat"
```

This will set the right environment variables for GraalVM.

After this you should be able to run `bb native-image`.

## Tasks

To see all available tasks in this project, run `bb tasks`:

``` text
$ bb tasks
The following tasks are available:

run-main     Run main
uberjar      Builds uberjar
run-uber     Run uberjar
graalvm      Checks GRAALVM_HOME env var
native-image Builds native image
```

## Run

To run this example using Clojure, run:

``` text
$ bb run-main --help
Usage:
  -f, --func FUNCTION  identity  The function applied to JSON from stdin
  -k, --key-fn KEY-FN  identity  The function applied to keywords
  -h, --help

$ echo '{"a": {"b": 2}}' | bb run-main -f '#(-> % :a :b)' -k keyword
2
```

## Build

To build the native image, run:

``` text
$ bb native-image
```

This should produce a binary called `jayfu`:

``` clojure
$ echo '{"a": {"b": 2}}' | ./jayfu -f '#(-> % :a :b)' -k keyword
2
```

## Talk

A talk that guides you through this project is coming soon.

## Tutorial

Coming soon.

## License

Copyright © 2021 Michiel Borkent

Distributed under the EPL License. See LICENSE.
