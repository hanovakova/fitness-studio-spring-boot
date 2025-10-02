CREATE TABLE IF NOT EXISTS users (
                                     id SERIAL PRIMARY KEY,
                                     username VARCHAR(255) UNIQUE NOT NULL,
                                     password VARCHAR(255) NOT NULL,
                                     name VARCHAR(255),
                                     email VARCHAR(255) UNIQUE,
                                     advertisement BOOLEAN DEFAULT FALSE,
                                     avatar_path VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS fitness_classes (
                                               id SERIAL PRIMARY KEY,
                                               name VARCHAR(255) NOT NULL,
                                               description TEXT,
                                               start_time TIMESTAMP,
                                               end_time TIMESTAMP,
                                               instructor_name VARCHAR(255),
                                               price Float NOT NULL,
                                               capacity INT NOT NULL,
                                               image_path VARCHAR(255),
                                               class_type VARCHAR(255),
                                               yoga_level VARCHAR(255),
                                               bike_type VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS user_classes (
                                            id SERIAL PRIMARY KEY,
                                            user_id INTEGER NOT NULL,
                                            class_id INTEGER NOT NULL,
                                            signup_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                            FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                                            FOREIGN KEY (class_id) REFERENCES fitness_classes(id) ON DELETE CASCADE,
                                            UNIQUE (user_id, class_id),
                                            paid BOOLEAN DEFAULT FALSE
);
