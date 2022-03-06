#!/bin/bash
set -e

psql -d postgres -U postgres -c "CREATE USER $BREWCRAFT_ADMIN_USER WITH PASSWORD '$BREWCRAFT_ADMIN_USER_PASSWORD';"
psql -d postgres -U postgres -c "GRANT CONNECT ON DATABASE $POSTGRES_DB TO $BREWCRAFT_ADMIN_USER WITH GRANT OPTION;"
psql -d postgres -U postgres -c "GRANT CREATE ON DATABASE $POSTGRES_DB TO $BREWCRAFT_ADMIN_USER WITH GRANT OPTION;"
psql -d postgres -U postgres -c "ALTER ROLE $BREWCRAFT_ADMIN_USER WITH CREATEROLE;"