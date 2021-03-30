# yet-another-micro-orm
Micro ORM for Java

Used for simple queries against a single table <-> object 

# Entity rules 
-   Entity fields MUST only be primitives and String
-   Fields SHOULD preferably have same name as database column
-   Only one int id (primary key with auto increment), that MUST be marked with annotation @Id
-   Entity fields not of same name as table columns MUST use @Column(name="db_column_name>")
-   Fields that does not have a corresponding db column must be annotaited with @Exclude
-   Entity MAY be annotation with @Entity

# Connection parameters
-   yamo.server
-   yamo.database
-   yamo.driver
-   yamo.username
-   yamo.password

# Setup demo
-   properties file
-   database with emplyee.sql table created