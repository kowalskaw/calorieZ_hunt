import sqlite3

def connect_to_db(sqlite_file):
    conn = None
    conn = sqlite3.connect(sqlite_file)
    # print("conected")
    return conn

def create_user(conn, user):
    query = '''
    INSERT INTO Users(password, first_name, last_name, email, sex, weight,
    height, alergies, caloriesIntakeDaily, weight_goal, user_name, birthDate)
    VALUES (?,?,?,?,?,?,?,?,?,?,?,?)
    '''
    cursor = conn.cursor()
    cursor.execute(query, user)
    return cursor.lastrowid # lastrowid returns generated id

def read(query, conn):
    c = conn.cursor()
    c.execute(query)

def update_to_db(query, conn):
    c = conn.cursor()
    c.execute(query)

def delete_to_db(query, conn):
    c = conn.cursor()
    c.execute(query)

def main():
    database = "skrypt-kopia.sqlite"
    conn = connect_to_db(database)
    with conn:
        user = ('root', 'marlenka', 'twojastara', 'marlenka@gmial.com',
                0, 65, 165, "gluten", 2000, 60, "marlenka123", "1980.01.01")
        user_id = create_user(conn, user)
        print("user added with id " + str(user_id))

if __name__ == '__main__':
    main()


