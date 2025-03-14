print('START Booking DB Initialization');
db = db.getSiblingDB('booking-service');
db.createUser({
    user: 'admin',
    password: 'password',
    roles: [{ role: 'readWrite', db: 'booking-service' }]
});
db.createCollection('bookings');
print('END Booking DB Initialization');
