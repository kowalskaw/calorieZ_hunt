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
    userId             int
);

create unique index Products_id_uindex
    on Products (id);

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


