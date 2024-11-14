# Start with the official MySQL image
FROM mysql:latest

# Set environment variables for MySQL configuration
# These will be used to set up MySQL on container startup
ENV MYSQL_ROOT_PASSWORD=root_password
ENV MYSQL_DATABASE=my_database
ENV MYSQL_USER=my_user
ENV MYSQL_PASSWORD=user_password
CMD ["mysqld"]
# Expose MySQL port
EXPOSE 3306

# Optionally, add a custom SQL script to initialize the database
COPY ./init.sql /docker-entrypoint-initdb.d/

# This entrypoint will execute any SQL scripts in the /docker-entrypoint-initdb.d/ folder
# on container startup, allowing for initial database setup.
# You can create an "init.sql" file in the same directory as the Dockerfile to
# run any custom commands (e.g., schema creation, seeding data).

# By default, the MySQL container runs the 'mysqld' command, so we don't need
# to specify an entrypoint or CMD here.

# This Dockerfile sets up MySQL with user-specified values and can be extended with
# custom SQL initialization as needed.
