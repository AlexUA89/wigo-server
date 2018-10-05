
dropdb -U postgres postgres
createdb -U postgres postgres
pg_dump -h vps469073.ovh.net -U postgres postgres | psql -h localhost -U postgres postgres