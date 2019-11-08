import sqlite3
import json


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
    return cursor.lastrowid  # lastrowid returns generated id


def update_user(conn, user):
    query = '''
    UPDATE Users
    SET password = ?,
        first_name = ?,
        last_name = ?,
        email = ?, 
        sex = ?, 
        weight = ?,
        height = ?, 
        alergies = ?, 
        caloriesIntakeDaily = ?, 
        weight_goal = ?, 
        user_name = ?, 
        birthDate = ?
    WHERE id = ?
    '''
    cursor = conn.cursor()
    cursor.execute(query, user)


def delete_user(conn, id):
    query = '''
    DELETE FROM Users where id=?
    '''
    cursor = conn.cursor()
    cursor.execute(query, (id,))
    conn.commit()


def delete_all_users(conn):
    query = '''
    DELETE FROM Users
    '''
    cursor = conn.cursor()
    cursor.execute(query)
    conn.commit()


def query_users(conn, query):
    cursor = conn.cursor()
    cursor.execute(query)
    data = cursor.fetchall()
    return json.dumps(data)


def main():
    database = "skrypt.db"
    conn = connect_to_db(database)
    with conn:
        # creating user
        # user = ('root', 'marlenka', 'twojastara', 'marlenka@gmial.com',
        #         0, 65, 165, "gluten laktoza", 2000, 60, "marlenka123", "1980.01.01")
        # user_id = create_user(conn, user)
        # print("user added with id " + str(user_id))

        # updating user
        # id = 7
        # user = (id, 'marmolada', 'marlenka', 'marlenkowska', 'marlenka@gmial.com',
        #         0, 75, 165, "gluten laktoza", 2000, 65, "marlenka123", "1980.01.01")
        # update_user(conn, user)
        # print("user with id " + str(id) + " updated")

        # deleting user
        # delete_user(conn, id)
        # print("user with id " + str(id) + " deleted")

        # deleting all users
        # delete_all_users(conn)

        # query users
        query1 = '''
        SELECT * FROM Users where id=2 or id=3
        '''
        query2 = '''
        SELECT * FROM Users where id=1
        '''
        result = query_users(conn, query1)
        result2 = query_users(conn, query2)

        print(result)



if __name__ == '__main__':
    main()
