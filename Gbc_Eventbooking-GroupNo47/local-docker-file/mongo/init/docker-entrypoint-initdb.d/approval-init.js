print('START Approval DB Initialization');
db = db.getSiblingDB('approval-service');
db.createUser({
    user: 'admin',
    password: 'password',
    roles: [{ role: 'readWrite', db: 'approval-service' }]
});
db.createCollection('approvals');
print('END Approval DB Initialization');
