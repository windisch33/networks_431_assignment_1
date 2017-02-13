/*
 * Nick Martinez, Robert Windisch
 * CSC434 - Computer Networks
 * Client-Server (C Implementation) Client 
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

#define MAX_BUFFER_SIZE 10
#define PORT 4446
#define SERVER_ADDR "127.0.0.1"
#define NUM_MESSAGES 10

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

//    char receive_buffer[MAX_BUFFER_SIZE] = {0};

    if( (server_socket = socket(AF_INET, SOCK_STREAM, 0)) == -1 )
	{	
        die("client - socket()");
	}

    memset(&server_info, 0, sizeof(server_info));

    server_info.sin_family = AF_INET;
    server_info.sin_addr.s_addr = inet_addr(SERVER_ADDR);
    server_info.sin_port = htons(PORT); 

    // establish connection with server
    if( connect(server_socket, (struct sockaddr*)&server_info, sizeof(server_info)) == -1 )
	{
        die("client - connect()"); 
	}

    // send 10 messages to be processed by server
    size_t i;
    for( i = 0; i < NUM_MESSAGES; ++i ) {
      char send_buffer[MAX_BUFFER_SIZE] = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j' };
      if( send(server_socket, send_buffer, sizeof send_buffer, 0) == -1 ) {
        die("client - send()");
      }

      if( recv(server_socket, send_buffer, sizeof send_buffer, 0) == -1 ) {
        die("client - recv()");
      }

      for( i = 0; i < MAX_BUFFER_SIZE; ++i ) {
        printf("%c ", send_buffer[i]);
      }
      printf("\n");
    }
    
    // close connection with server
    close(server_socket);

	return EXIT_SUCCESS;
}

