# JDBC Database Console

### Description
* **Objective** - To implement a console interface capable of prompting a user to populate a respective table in a respective database.
* **Purpose** - To establish familiarity with
    * Java Database Connections
    * Error handling & Debugging
    * `.xml` configuration
* **Details**
    * **Milestone 1** - configure this project to connect to `mariadb` rather than `mysql`
    * **Milestone 2** - create a console (or graphical) interface to `add`, `remove`, `update`, and `get` a respective entity from a respective table from a respective database.   
    

    

### Configuring Environment
* Ensure you have `MySQL` installed.
    * This project's current `MySQL` version can be found by viewing its `pom.xml`
    * Your machine's current `MySQL` version can be found by executing `SELECT VERSION()`



## How to Download

#### Part 1 - Forking the Project
* To _fork_ the project, click the `Fork` button located at the top right of the project.


#### Part 2 - Navigating to _forked_ Repository
* Navigate to your github profile to find the _newly forked repository_.
* Copy the URL of the project to the clipboard.

#### Part 3 - Cloning _forked_ repository
* Clone the repository from **your account** into the `~/dev` directory.
  * if you do not have a `~/dev` directory, make one by executing the following command:
    * `mkdir ~/dev`
  * navigate to the `~/dev` directory by executing the following command:
    * `cd ~/dev`
  * clone the project by executing the following command:
    * `git clone https://github.com/MYUSERNAME/NAMEOFPROJECT`

#### Part 4 - Check Build
* Ensure that the tests run upon opening the project.
    * You should see `Tests Failed: 99 of 99 tests`







## How to Submit

#### Part 1 -  _Pushing_ local changes to remote repository
* from a _terminal_ navigate to the root directory of the _cloned_ project.
* from the root directory of the project, execute the following commands:
    * add all changes
      * `git add .`
    * commit changes to be pushed
      * `git commit -m 'I have added changes'`
    * push changes to your repository
      * `git push -u origin master`

#### Part 2 - Submitting assignment
* from the browser, navigate to the _forked_ project from **your** github account.
* click the `Pull Requests` tab.
* select `New Pull Request`
