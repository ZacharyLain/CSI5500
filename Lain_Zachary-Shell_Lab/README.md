# Simple Shell Project

This project implements a basic shell that rusns within the current terminal. The shell supports most commands you can run in a typical Linux shell and includes a built-in command (proc) for inspecting process files from the /proc directory.

## Features

- Command Execution: The shell can execute all commands available in a standard Linux shell by forking a child process and using the execvp() function.
- Built-in Commands:
  - exit [status]: Exits the shell with the given status code (default is 0).
  - proc <filename>: Reads and displays content from a process file in the /proc directory.
- Loop: The shell runs in a continuous loop, prompting the user for commands until exit is entered.