CREATE TABLE IF NOT EXISTS rooms (
                                     roomId BIGSERIAL PRIMARY KEY,          -- Unique identifier for each room
                                     roomName VARCHAR(100) NOT NULL,        -- Name of the room
    capacity INT NOT NULL,                   -- Maximum capacity of the room
    availability BOOLEAN NOT NULL DEFAULT TRUE  -- Availability status of the room
    );

CREATE TABLE IF NOT EXISTS room_features (
                                             id SERIAL PRIMARY KEY,                   -- Unique identifier for each feature
                                             roomId BIGINT NOT NULL,                 -- Foreign key referencing rooms
                                             features VARCHAR(100) NOT NULL,           -- Feature description
    FOREIGN KEY (roomId) REFERENCES rooms(roomId) ON DELETE CASCADE  -- Foreign key constraint
    );