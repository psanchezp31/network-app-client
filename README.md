# network-app-client
--ACADEMIC PURPOSE-- Java interface application using sockets to see the state of a variable of network equipment. In this case, you will see the status of the server CPU through a socket connection. 

The application works using the .jar located in the out / artifacts / network_applicaction_jar folder.
Previously it is necessary to deploy the application (.jar) hosted in the repository https://github.com/psanchezp31/network-app-server (out / artifacts / networkApplicationServer_jar).
When deployed, a simple interface opens that contains a graph and
a textField where we will introduce the IP to which we want to connect, step followed by clicking on connect.
The server will be listening with the socket open and the performance of the remote server's CPU will begin to be plotted every second.
