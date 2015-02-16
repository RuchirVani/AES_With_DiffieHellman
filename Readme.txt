Name: Ruchir Vani                
ID:01416861
E-mail add: ruchir_vani@student.uml.edu


        I have implemented my project in JAVA. 
Classes
(1) ServerKEY: It is the class use for the generation of Diffie-Hellman Key-exchange at server side.
(2) ClientKEY: It is the class use for the generation of Diffie-Hellman Key-exchange at client side.
(3) Serverfinal: It is the class which encrypt the file and send to client.
(4) Clientfinal: It is the class which receives the file from server and then decrypt that file.
(5) AES: this file contains all AES operations.






Stps:(run both server and client file in different directory to see that file has been transferred.)
1. Run ServerKEY program at server-side.
2. Run ClientKEY program at client-side.
3.Comple AES file on both the side.
4. Run Serverfinal on server-side.(Set the file location you want Encrypt and send to the receiver).(LIne 17-18 in Serverfinal file)
5.  Run Clientfinal on client-side.(Set the file location where you want Decrypt and receive and store) .(Line 122 in Clientfinal file)