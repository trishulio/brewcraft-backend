# Migration

- Migrations are run on application startup for both master schema and tenant schemas

- Migration scripts for master schema should be placed in brewery-backend\knex\master_migrations

- Migration scripts for tenant schemas should be placed in brewery-backend\knex\tenant_migrations

- To create a new migration script run 'knex migrate:make <filename>', the file will be created at the migrations directory specified in the knexfile.js file (currently brewery-backend\knex)