# yet-another-micro-orm
Micro ORM for Java

# Entity rules 
-   Entity fields MUST only be primitives and String
-   Fields SHOULD preferably have same name as database column
-   Only one int id (primary key with auto increment), that MUST be marked with annotation @Id
-   Entity fields not of same name as table MUST use @Column(name="db_collumn_name>")
-   Fields that does not have a corresponding db column must be annotaited with @Exclude
-   Entity MAY be annotation with @Entity

# Connection parameters
-   yamo.url=
-   yamo.database=
-   yamo.driver=
-   yamo.username=
-   yamo.password=

# Setup