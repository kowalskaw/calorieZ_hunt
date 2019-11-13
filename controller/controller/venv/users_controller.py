import sqlite3
import json

class Users:
    def __init__(self, conn, cursor):
        self.conn = conn
        self.cursor = cursor

    # data as dict
    def create_user(self, user):
        user_dict = json.loads(user)
        list_of_values = []
        for key in user_dict:
            list_of_values.append(user_dict[key])

        # to tuple
        user_tuple = tuple(list_of_values)

        query = '''
        INSERT INTO Users(password, first_name, last_name, email, sex, weight,
        height, allergies, calories_intake_daily, weight_goal, user_name, birthDate)
        VALUES (?,?,?,?,?,?,?,?,?,?,?,?)
        '''

        self.cursor.execute(query, user_tuple)

        return self.cursor.lastrowid  # lastrowid returns generated id

    def update_user(self, user):
        user_dict = json.loads(user)
        list_of_values = []
        for key in user_dict:
            if key != 'id':
                list_of_values.append(user_dict[key])

        list_of_values.append(user_dict['id'])

        # to tuple
        user_tuple = tuple(list_of_values)

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
            calories_intake_daily = ?, 
            weight_goal = ?, 
            user_name = ?, 
            birthDate = ?
        WHERE id = ?
        '''
        self.cursor.execute(query, user_tuple)

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
