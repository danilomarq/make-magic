# make-magic

Make magic is a REST Api for managing character registration based on the Harry Potter franchise. The project is available on Heroku through the endpoints:
 GET  - [https://api-make-magic.herokuapp.com/characters](https://api-make-magic.herokuapp.com/characters) for registered characters list
         - This endpoint accept a query string parameters to filter **?house={house hash code}** 
 GET  - [https://api-make-magic.herokuapp.com/characters/{id}](https://api-make-magic.herokuapp.com/characters/{id}) for get a specific character
 POST - [http://api-make-magic.herokuapp.com/characters/saveCharacter](http://api-make-magic.herokuapp.com/characters/saveCharacter) to insert or update a character
 POST - [http://api-make-magic.herokuapp.com/characters/deleteCharacter](http://api-make-magic.herokuapp.com/characters/saveCharacter) to delete a character
 
 To run the project locally, you can follow the steps below:
 
 # steps
  - Open the file **application.resources** at src/main/resources
  - Change the placeholders for your database credentials
  - Run the command "mvn clean package"
  - Run the command "docker build -t springio/gs-spring-boot-docker ." to build the docker container
  - Run the command "docker run -p 8080:8080 springio/gs-spring-boot-docker" to run the container
