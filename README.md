# Twitterclone

Simple web application allowing users to register and create posts.

### Prerequisites

 * Maven
 * MySql
 * npm

### Getting Started

Run database init script to create 'twiterclone' database and associated tables.

```
dbinit.sql
```

Update dbuser, dbpass and dburl application properties as needed to allow application to connect to database.

```
src/main/resources/application.properties
```

Download frontend dependencies

```
cd src/main/webapp/resources
npm install
```

Deploy application

```
mvn jetty:run
```

Once deployed the application can be viewed in a web browser.

```
localhost:8080
```

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management
* [Apache Velocity](http://velocity.apache.org/) - Templating engine
* [MySql](https://www.mysql.com/) - Database
