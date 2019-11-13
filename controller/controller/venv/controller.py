import sqlite3
import json


class Users:
    def __init__(self, conn, cursor):
        self.conn = conn
        self.cursor = cursor

    def create_user(self, user):
        query = '''
        INSERT INTO Users(password, first_name, last_name, email, sex, weight,
        height, alergies, caloriesIntakeDaily, weight_goal, user_name, birthDate)
        VALUES (?,?,?,?,?,?,?,?,?,?,?,?)
        '''
        self.cursor.execute(query, user)
        return self.cursor.lastrowid  # lastrowid returns generated id

    def update_user(self, user):
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
        self.cursor.execute(query, user)

    def delete_user(self, id):
        query = '''
        DELETE FROM Users where id=?
        '''
        self.cursor.execute(query, (id,))
        self.conn.commit()

    def delete_all_users(self):
        query = '''
        DELETE FROM Users
        '''
        self.cursor.execute(query)
        self.conn.commit()

    def query_users(self, query):
        self.cursor.execute(query)
        data = self.cursor.fetchall()
        return json.dumps(data)

    def get_user_by_id(self, user_id):
        query = '''
        SELECT * FROM Users where id=?
        '''
        self.cursor.execute(query, (user_id,))
        data = self.cursor.fetchall()
        return json.dumps(data)


def test():
    conn = sqlite3.connect("skrypt.db")
    cursor = conn.cursor()

    users = Users(conn, cursor)

    user = users.get_user_by_id(3)

    print(user)


if __name__ == '__main__':
    test()
