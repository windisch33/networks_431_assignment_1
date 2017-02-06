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

#define BUFFER_SIZE 1024
#define PORT 4446
#define SERVER_ADDR "127.0.0.1"

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

    char receive_buffer[BUFFER_SIZE] = {0};

    if( (server_socket = socket(AF_INET, SOCK_STREAM, 0)) == -1 )
	{	
        die("client - socket()");
	}

    memset(&server_info, 0, sizeof(server_info));

    server_info.sin_family = AF_INET;
    server_info.sin_addr.s_addr = inet_addr(SERVER_ADDR);
    server_info.sin_port = htons(PORT); 

    if( connect(server_socket, (struct sockaddr*)&server_info, sizeof(server_info)) == -1 )
	{
        die("client - connect()"); 
	}

    int message_len;
    if( (message_len = recv(server_socket, receive_buffer, sizeof(receive_buffer), 0)) == -1 )
	{
        die("client - recv()");
	}
    printf("Message(client): %s\n",receive_buffer);

    printf("strlen(receive): %ld\n",strlen(receive_buffer));
    write(server_socket, receive_buffer, strlen(receive_buffer));
    close(server_socket);

	return EXIT_SUCCESS;
}

