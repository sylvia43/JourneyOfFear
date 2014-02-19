##### Setup Instructions
===============

1. Open your IDE of choice. (I'm using Netbeans; I would suggest that).
2. Create a new project (Ctrl-Shift-N in Netbeans) and name it JourneyOfFear.
3. Open git bash (or you can do it from the netbeans plugin).
4. Navigate to the new project folder and type 'git init' or go to Team -> Git -> Initialize Repository.
5. Type 'git remote add origin https://github.com/anubiann00b/JourneyOfFear' (only if you are using command line).
6. Type 'git pull' and wait. If you are using the netbeans plugin, right click on the project and go to Git -> Remote -> Pull. Choose 'Specify Git Repository Location' and supply 'origin' as the remote name, 'https://github.com/anubiann00b/JourneyOfFear.git' as the URL, and enter your username and password in the boxes provided.
7. Right click on the new project and go to Properties then the Libraries tab.
8. Click 'Add JAR/Folder' and add all of the jars in the lib folder.
9. Go to the run tab in the properties menu.
10. In the VM options box, type '-Djava.library.path="[pathtorepo]\lib\native\[osname]"' filling in the path to the repo and the name of your os.
11. Press F6 or click run, select game.Game as the main class, and you have successfully set up Slick! Nice job.
