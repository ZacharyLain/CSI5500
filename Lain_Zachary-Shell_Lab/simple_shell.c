#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/wait.h>

#define MAX_CMD_LEN 1024

void proc_command(char *filename)
{
  char path[MAX_CMD_LEN];
  snprintf(path, sizeof(path), "/proc/%s", filename);

  FILE *file = fopen(path, "r");
  if (file == NULL)
  {
    perror("Error opening file");
    return;
  }

  char line[256];
  while (fgets(line, sizeof(line), file))
  {
    printf("%s", line);
  }

  fclose(file);
}


void execute_command(char *cmd)
{
  char *args[MAX_CMD_LEN / 2 + 1];
  char *token;
  int arg_count = 0;
  
  // grab first token from the input cmd
  token = strtok(cmd, " \t\r\n");

  // loops through all of the tokens and adds them to an array of arguments
  while (token != NULL)
  {
      args[arg_count++] = token;
      
      // testing to see what tokens are printed
      // printf("Token %s\n", token);
      
      // get the next token
      token = strtok(NULL, " \t\r\n");
  }

  // Last element of argv must be a NULL ptr
  // https://www.digitalocean.com/community/tutorials/execvp-function-c-plus-plus
  args[arg_count] = NULL;

  if (arg_count == 0)
  {
      return;  // Empty command
  }

  // Built-in command: proc
  if (strcmp(args[0], "proc") == 0)
  {
    if (arg_count > 1)
    {
      proc_command(args[1]);
    }
    else
    {
      printf("Usage: proc <filename>\n");
    }
  
    return;
  }

  // Built-in command: exit
  if (strcmp(args[0], "exit") == 0)
  {
      int status = 0;
      if (arg_count > 1)
      {
          status = atoi(args[1]);
      }
      exit(status);
  }

  // Fork a child process to execute the command
  pid_t pid = fork();
  if (pid == 0)
  {  
    // Child process
    
    // See what process the cmd is running on
    // printf("Executing command with pid: %d\n", getpid());
    
    if (execvp(args[0], args) == -1)
    {
      perror("Error executing command");
    }
    exit(EXIT_FAILURE);
  }
  else if (pid < 0)
  {
    // Forking error
    perror("Error forking");
  } 
  else
  {
    // Parent process
    wait(NULL);  // Wait for the child process to complete
    
    // Print when fork closes
    // printf("Fork closed.\n");
  }
}

int main()
{
  char cmd[MAX_CMD_LEN];

  // Run until exit cmd
  while (1)
  {
      printf("Shell: ");
      if (fgets(cmd, sizeof(cmd), stdin) == NULL)
      {
          break;
      }

      execute_command(cmd);
  }

  return 0;
}
