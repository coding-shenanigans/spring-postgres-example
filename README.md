# Spring Boot + PostgreSQL Example

This repository provides a basic example of a Spring Boot app that connects to a PostgreSQL database.

## Local PostgreSQL with Docker

This section walks through the steps needed to set up a PostgreSQL database locally in a docker container.

```bash
# Create a local PostgreSQL docker container
docker run --name local-postgres -e POSTGRES_PASSWORD=your_password -p 5432:5432 -d postgres
```

You can choose to set up the database through the CLI or the pgadmin4 UI. Directions for both methods below.

### Set Up Database with CLI

```bash
# Shell into the PostgreSQL container
docker exec -it local-postgres /bin/bash

# Connect to the PostgreSQL database
psql -U postgres -h localhost -d postgres
```

```sql
-- Set up database
CREATE DATABASE your_database_name;

\c your_database_name

-- Create table
CREATE TABLE todos (
    id SERIAL PRIMARY KEY,
    title TEXT NOT NULL,
    done BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

-- Create function to update the updated_at column
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = NOW();
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Create trigger to update the update_at column on the todos table
CREATE TRIGGER update_todos_updated_at
BEFORE UPDATE ON todos
FOR EACH ROW
EXECUTE FUNCTION update_updated_at_column();

-- Add a row to the todos table
INSERT INTO todos (title) VALUES ('Code some shenanigans');
```

### Set Up Database with pgadmin4 UI

```bash
# Create a local pgadmin4 docker container
docker run --name local-pgadmin4 -e PGADMIN_DEFAULT_EMAIL=your_email -e PGADMIN_DEFAULT_PASSWORD=your_email_password -p 8080:80 -d dpage/pgadmin4
```

- Access pgadmin4 UI
  - Go to http://localhost:8080
    - Sign in with the email and password used when setting up the `local-pgadmin4` container.
- Connect to the PostgreSQL container
  - Right click `Servers` > `Register` > `server...`
  - In the `Connection` tab, enter the information for your `local-postgres` container.
    - You may need to set the `Host name/address` field to `host.docker.internal` when using Docker Desktop.
- Create your database
  - Right click `Databases` > `Create` > `Database...`
    - Enter your_database_name
- Set up database
  - Right click `your_database_name` > `Query Tool`
  - Execute the following SQL queries:

    ```sql
    -- Create table
    CREATE TABLE todos (
    id SERIAL PRIMARY KEY,
    title TEXT NOT NULL,
    done BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
    );
    
    -- Create function to update the updated_at column
    CREATE OR REPLACE FUNCTION update_updated_at_column()
    RETURNS TRIGGER AS $$
    BEGIN
    NEW.updated_at = NOW();
    RETURN NEW;
    END;
    $$ LANGUAGE plpgsql;
    
    -- Create trigger to update the update_at column on the todos table
    CREATE TRIGGER update_todos_updated_at
    BEFORE UPDATE ON todos
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();
    
    -- Add a row to the todos table
    INSERT INTO todos (title) VALUES ('Code some shenanigans');
    ```

## Run Spring Boot App

Update the database configuration in the properties file to match your setup.
- `src/main/resources/application.properties`
  - `spring.datasource.url`
  - `spring.datasource.username`
  - `spring.datasource.password`
- Note: This is for demo purposes only. Please avoid storing sensitive configuration details in `application.properties`.

Start the app:
`src/main/java/com/codingshenanigans/spring_postgres_example/SpringPostgresExampleApplication.java`

Send a request:
http://localhost:9000/todos
