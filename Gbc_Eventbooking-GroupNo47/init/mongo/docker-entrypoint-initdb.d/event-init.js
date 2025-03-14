print('START Event DB Initialization');
db = db.getSiblingDB('event-service');
db.createUser({
    user: 'admin',
    password: 'password',
    roles: [{ role: 'readWrite', db: 'event-service' }]
});
db.createCollection('events');
print('END Event DB Initialization');
