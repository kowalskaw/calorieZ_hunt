create table Products
(
    id                 integer
        constraint Products_pk
            primary key autoincrement,
    name               String,
    caloriesIn100Grams real,
    protein            String,
    carbs              String,
    fats               String,
    onePortionInGrams  real,
    userId             int,
    allergens          String
);

create unique index Products_id_uindex
    on Products (id);

CREATE TRIGGER ProductsFts_Delete
    AFTER DELETE
    ON Products
BEGIN
    Delete from ProductsFts where id = old.id;
END;

CREATE TRIGGER ProductsFts_Insert
    AFTER INSERT
    ON Products
BEGIN
    INSERT INTO ProductsFts(id, name, caloriesIn100Grams, protein, carbs, fats, onePortionInGrams, userId, allergens)
    VALUES (new.id, new.name, new.caloriesIn100Grams, new.protein, new.carbs, new.fats, new.onePortionInGrams,
            new.userId, new.allergens);
END;

CREATE TRIGGER ProductsFts_Update
    AFTER UPDATE
    ON Products
BEGIN
    Delete from ProductsFts where id = old.id;
    INSERT INTO ProductsFts(id, name, caloriesIn100Grams, protein, carbs, fats, onePortionInGrams, userId, allergens)
    VALUES (new.id, new.name, new.caloriesIn100Grams, new.protein, new.carbs, new.fats, new.onePortionInGrams,
            new.userId, new.allergens);
END;

create table ProductsFts_config
(
    k not null
        primary key,
    v
)
    without rowid;

create table ProductsFts_data
(
    id    INTEGER
        primary key,
    block BLOB
);

create table ProductsFts_docsize
(
    id INTEGER
        primary key,
    sz BLOB
);

create table ProductsFts_idx
(
    segid not null,
    term  not null,
    pgno,
    primary key (segid, term)
)
    without rowid;

create table Users
(
    id                  integer not null
        constraint Users_pk
            primary key autoincrement,
    password            String,
    first_name          String  not null,
    last_name           String  not null,
    email               String,
    sex                 int     not null,
    weight              real    not null,
    height              real    not null,
    alergies            String,
    caloriesIntakeDaily real,
    weight_goal         real,
    user_name           String  not null,
    birthDate           TEXT default '1970-01-01 00:00:00' not null
);

create table Meal
(
    id       integer not null
        constraint Meal_pk
            primary key autoincrement,
    mealType int,
    date     text,
    userId   int
        references Users
);

create table SpecificProductForMeal
(
    productId      int
        references Products,
    portionInGrams int,
    mealId         int
        references Meal,
    constraint SpecificProductForMeal_pk
        primary key (productId, mealId)
);

create virtual table ProductsFts using fts5
(
    id,
    name,
    caloriesIn100Grams,
    protein,
    carbs,
    fats,
    onePortionInGrams,
    userId,
    allergens,
    content=
    'Products'
);


