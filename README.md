# Selenium Framework
A framework to run and validate Selenium UI Tests.
## Components
This is a Hybrid Framework which is a combination of **Data Driven Development** and **Test Driven Development**. And it is written on the concepts of **BDD**.

This Framework key components are:
**Design Pattern**:
**Page Object Model with Page Factory**
All the Waits, actions and any other functionality of WebElement of that particular page is implemented here only
- can be found at: ```src/main/java``` folder under package ```com.factory.pages```
```
e.g SignUpPage.java
```
**CSV Reader**:
This reader reads the required test variables from csv file and provides it to test during execution.
can be found at: ```src/main/java``` folder under package ```com.factory.utils```
```
e.g 
//reads the csv rows and puts in List of Maps(Key:Column Name Value: Column Value)
List<Map<String, Object>> loginPagerows = CSVReader.readResourceCSV("path of csv"); 

// fetches the data by providing row number and name of the column
String validemail = loginPagerows.get(rowNumer).get(Name of Column); 

```
**Log4J**:
Every Test case needs checks as in what steps are going on during execution so that code can be easily debug during fail test scenario and user has a fair information as in how the test cases are flowing.

- After execution of tests the logs are saved in ```application.log``` file, which can be found in the ```root``` folder
- Properties can be found at: ```src/main/resources folder```, name is ```log4j.Properties```
```
e.g Use like this in your test case:
    logger.info("Logging in System"); //can be seen in console output
```
**Extent Report**:
- To generate Human readable Report where you can see clearly see which test cases passed and which one failed.
- Implementation can be found at folder ```src/main/java``` and package ```com.tests.ExtentReportListener```
- After every run an ```Extent.html``` gets created in ```test-output``` folder, go the folder path and double click on it view the report.
- ```ITestReporter``` listener has been used to implement it and this listener is being used in ```testng.xml``` which can be found in the root folder

**config.properties**
To make the framework more flexible which can be run on any **browser** and on different **environments(URL)** I have created this file where you can provide the environment(URL) on which you want to test and the browser name on which you want to test.
**Currently this has been implemented for ```Firefox``` and ```Chrome``` browsers only**

**Failed Test Cases Screenshots**
A screenshot is taken every time any test case fails by any chance. This screenshot gets saved in a folder in **.jpg** format.
- One can find the screenshots at ```root/failedScreenshots```

**Base Class and Base Page**
- There are some functionities such as driver initilization, browser initialiation etc of the framework which needs to be extended to all the test cases for that Base Class (```TestBaseUI```) has been used.
- Similarly some functionalities which are required by all the Page Factory objects are defined in Base Page (```BasePage```)

**Parallel Mode**
Parallel functionality has also been provided to save time in test execution.
- ```TestNG``` will run all the methods in the same class in the same thread, but each class will be run in a different separate thread.

**Constant**
Since many of the variable names are reusable so to avoid the pain of writing it over and over again I have creates a enum class ```Constant.java```

## Getting Started
- Clone this repository.
- Import the project in your IDE as Maven Project

### Prerequisites

```
- You need a IDE such as Eclipse.
- Java v1.8
- Maven should be installed on your system.
- Firefox version 71.0 (64 Bit)
- Chrome version 79.0 (64 bit)
- Other than that every other dependencies are covered in pom.xml (it will automatically download the other dependencies)
```


## Running the tests

Running the tests is very easy In eclipse or any other IDE, one just has to go to ```root``` folder and double click on ```testng.xml``` once it opens right click on the open file and ```Run As -> TestNG Suite```

- If you want to change the environment or browswer first go to ```config.Properties``` and change the browser name.

### How it Works
Once test starts it goes like this:
- It goes to the class on which ```@Test``` is defined
- It checks whether the class has extended some other classs, ```TestBaseUI``` in our case, goes to that class initialises the **Environment**, checks on which **browser** to run and initialises the **driver**.
- The our test checks the context and decides in the test which data to call from the csv this **context** is then passed to the `@Test`
- Now it initialises the `Page Factory Class`, so that it can find the WebElements to work on.

```---GIVEN---```
- Now depending on the data required it reads the **CSV file** corrosponding to it, fetches the data and then stores it in a variable. (if required).

`---WHEN---`
- Once it has all the data requried it starts performing the test actions with the help of those WebElements.

`---THEN---`
- Once all the actions have been performed its time for validating the test case i.e. do the `assertions`. If all the assertion passes the test case is marked as pass otherwise it is marked as failed.

**NOTE:**
For every test case logs are generated and stored in `application.log` and report is generated which is stored in `Extent.html`

## Built With

* [TestNG](https://testng.org/doc/) - The web framework used
* [Maven](https://maven.apache.org/) - Dependency Management
* [Selenium](https://selenium.dev/) - Used to automate Web UI

## Author
* **Rahul Raman** - [rahulraman](https://github.com/rahul-raman)

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details
