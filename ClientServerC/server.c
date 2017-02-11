/*
 * Nick Martinez, Robert Windisch
 * CSC434 - Computer Networks
 * Client-Server (C Implementation) Server 
*/
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <errno.h>
#include <string.h>
#include <sys/types.h>
#include <time.h> 
#include <pthread.h>

#define MAX_BUFFER_SIZE 10
#define PORT 4446
#define BACKLOG 10

/**
 * Client request handler.
 *
 * params:
 *  socket - the socket interface to the client connection
 */
void *handle_request_t(void *socket)
{
  char recv_buffer[MAX_BUFFER_SIZE] = {0};
  if( recv(socket, recv_buffer, sizeof recv_buffer, 0) == -1 ) {
    die("server - recv()");
  }
  // reverse(recv_buffer, sizeof recv_buffer);
  if( send(socket, recv_buffer, sizeof recv_buffer, 0) == -1 ) {
    die("server - send()");
  }
}

/**
 * Reverse the characters in the buffer.
 *
 * params:
 *   buffer - an array of characters
 *   len - the length of the array
 */
void reverse(char *buffer, size_t len) 
{
  size_t i, j;
  int temp = 0;
  for( i = 0; j = len - 1; i < j; ++i, --j ) {
    temp = buffer[i];
    buffer[i] = buffer[j];
    buffer[j] = temp;
  } 
}

/**
 * Print the error and quit.
 *
 * params:
 *  s - a string describing error / exception / problem
 */
void die(char* s)
{
    perror(s);
    exit(1);
}

int main(int argc, char *argv[])
{
    int server_socket = 0;

    struct sockaddr_in server_info;
    struct sockaddr_in client_info; 

    char send_buffer[BUFFER_SIZE] = {0};
    char receive_buffer[BUFFER_SIZE] = {0};

	// obtain a socket for the server
    if( (server_socket = socket(AF_INET, SOCK_STREAM, 0)) == -1 )
	{
        die("server - socket()");
	}

    memset(&server_info, 0, sizeof server_info);
    server_info.sin_family = AF_INET;
    server_info.sin_addr.s_addr = htonl(INADDR_ANY);
    server_info.sin_port = htons(PORT);

	// bind the server to port
    if( bind(server_socket, (struct sockaddr*)&server_info, sizeof server_info) == -1 ) 
	{
		die("server - bind()");
	} 

	// listen for incoming connections
    if( listen(server_socket, BACKLOG) == -1 ) 
	{
		die("server - listen()");
	}

    while(1)
    {
        // accept request and create new thread to service that request
        int client_len = sizeof client_info;
        int connection_socket = accept(server_socket, (struct sockaddr*)&client_info, 
            (socklen_t*)&client_len); 

        pthread_t thread;
        int status = 0;
        if( (status = pthread_create(thread, NULL, handle_request_t, (void *)connection_socket)) != 0 ) {
          fprintf(stderr, "server - error creating thread! error code = %d\n", status);
          exit(1);
        }
     }

	return EXIT_SUCCESS;
}

