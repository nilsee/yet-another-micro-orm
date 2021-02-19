# yet-another-micro-orm
Micro ORM for Java

Entity rules 
-   Entity fields MUST only be primitives and String
-   Fields SHOULD preferably have same name as database column
-   Only one int id (primary key with auto increment), that MUST be marked with annotation @Id
-   Entity fields not of same name as table must use @Column(name="db_collumn_name>")
-   Fileds that does not have a corresponding db columns must be annotaited with @Exclude
-   Entity MAY be annotation with @Entity