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

#define BUFFER_SIZE 1024
#define PORT 4446
#define BACKLOG 10

/**
 Print the error and quit.
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
        int client_len = sizeof client_info;
        int connection_socket = accept(server_socket, (struct sockaddr*)&client_info, 
		(socklen_t*)&client_len); 

		time_t ticks = time(NULL);
        snprintf(send_buffer, sizeof(send_buffer), "%.24s\r\n", ctime(&ticks));
        printf("strlen(send): %ld\n",strlen(send_buffer));
        if( send(connection_socket,send_buffer,strlen(send_buffer),0) == -1 ) 
		{
			// maybe we shouldn't terminate the process here
			die("server - send()");
		} 

        int message_len = 0;
        if( (message_len = recv(connection_socket, receive_buffer, sizeof(receive_buffer)-1, 0)) == -1 )
		{
			// same here
        	die("server - recv()");
		}

        printf("Message(server): %s*\n",receive_buffer);
        close(connection_socket);
     }

	return EXIT_SUCCESS;
}

