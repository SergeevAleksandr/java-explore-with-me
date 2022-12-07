DROP TABLE IF EXISTS
    users,
    categories,
    compilations,
    locations,
    events,
    requests,
    compilations_events CASCADE;

CREATE TABLE IF NOT EXISTS locations
(
    location_id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    lat FLOAT NOT NULL,
    lon FLOAT NOT NULL
);

CREATE TABLE IF NOT EXISTS categories
(
    category_id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    category_name VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS users
(
    user_id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    user_name VARCHAR(50)  NOT NULL,
    user_email VARCHAR(100)  NOT NULL,
    UNIQUE(user_email)
);

CREATE TABLE IF NOT EXISTS events
(
    event_id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    event_category_id INTEGER REFERENCES categories (category_id) ON DELETE CASCADE,
    event_created TIMESTAMP WITHOUT TIME ZONE,
    event_description VARCHAR(1000)  NOT NULL,
    event_date TIMESTAMP WITHOUT TIME ZONE,
    event_initiator_id INTEGER REFERENCES users (user_id) ON DELETE CASCADE,
    event_location_id INTEGER REFERENCES locations (location_id) ON DELETE CASCADE,
    event_paid BOOLEAN,
    event_participant_limit INTEGER NOT NULL,
    event_published TIMESTAMP WITHOUT TIME ZONE,
    event_request_moderation BOOLEAN,
    event_state VARCHAR(100) NOT NULL,
    event_title VARCHAR(1000) NOT NULL,
    event_annotation VARCHAR(1000) NOT NULL

 );

CREATE TABLE IF NOT EXISTS requests
(
    request_id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    request_status VARCHAR(50)  NOT NULL,
    request_requester_id INTEGER REFERENCES USERS (user_id) ON DELETE CASCADE,
    request_created TIMESTAMP WITHOUT TIME ZONE,
    request_event_id INTEGER REFERENCES events (event_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS compilations
(
    compilation_id INTEGER GENERATED BY DEFAULT AS IDENTITY,
    compilation_title VARCHAR(500)  NOT NULL,
    compilation_pinned BOOLEAN,
    constraint pk_compilation PRIMARY KEY (compilation_id)
);

CREATE TABLE IF NOT EXISTS compilations_events
(
    compilation_id INTEGER REFERENCES compilations (compilation_id),
    event_id INTEGER REFERENCES events (event_id),
    PRIMARY KEY (compilation_id, event_id),
    CONSTRAINT fk_compilations_events_compilations FOREIGN KEY (compilation_id) REFERENCES compilations (compilation_id),
    CONSTRAINT fk_compilations_events_event FOREIGN KEY (event_id) REFERENCES events (event_id)
);

CREATE TABLE IF NOT EXISTS comments
(
    comments_id INTEGER GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    comments_text VARCHAR(10000) NOT NULL,
    comments_event_id INTEGER,
    comments_commentator_id INTEGER,
    comment_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    comments_by_admin BOOLEAN NOT NULL,
    comments_changed BOOLEAN NOT NULL,
    CONSTRAINT pk_comments PRIMARY KEY (comments_id),
    CONSTRAINT fk_comments_events FOREIGN KEY (comments_event_id) REFERENCES events (event_id),
    CONSTRAINT fk_comments_users FOREIGN KEY (comments_commentator_id) REFERENCES users (user_id)
);