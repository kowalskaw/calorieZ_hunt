import sqlite3
import json


class Users:
    def __init__(self, conn, cursor):
        self.conn = conn
        self.cursor = cursor

    def user_tuple_to_dict_withut_id(self, data):
        user_as_dict = {
            'password': [x[0] for x in data],
            'first_name': [x[1] for x in data],
            'last_name': [x[2] for x in data],
            'email': [x[3] for x in data],
            'sex': [x[4] for x in data],
            'weight': [x[5] for x in data],
            'height': [x[6] for x in data],
            'allergies': [x[7] for x in data],
            'calories_intake_daily': [x[8] for x in data],
            'weight_goal': [x[9] for x in data],
            'user_name': [x[10] for x in data],
            'birthDate': [x[11] for x in data],
        }
        return user_as_dict

    def user_tuple_to_dict_with_id(self, data):
        print(data)
        user_as_dict = {
            'id' : [x[0] for x in data],
            'password': [x[1] for x in data],
            'first_name': [x[2] for x in data],
            'last_name': [x[3] for x in data],
            'email': [x[4] for x in data],
            'sex': [x[5] for x in data],
            'weight': [x[6] for x in data],
            'height': [x[7] for x in data],
            'allergies': [x[8] for x in data],
            'calories_intake_daily': [x[9] for x in data],
            'weight_goal': [x[10] for x in data],
            'user_name': [x[11] for x in data],
            'birthDate': [x[12] for x in data],
        }
        return user_as_dict

    def json_to_tuple_without_id(self, json_obj):
        dict_obj = json.loads(json_obj)
        list_of_values = []
        for key in dict_obj:
            list_of_values.append(dict_obj[key])
        return tuple(list_of_values)

    def json_to_tuple_with_id(self, json_obj):
        dict_obj = json.loads(json_obj)
        list_of_values = []
        for key in dict_obj:
            if key != 'id':
                list_of_values.append(dict_obj[key])

        list_of_values.append(dict['id'])
        return tuple(list_of_values)

    # data as dict
    def create_user(self, user):
        # user_dict = json.loads(user)
        # list_of_values = []
        # for key in user_dict:
        #     list_of_values.append(user_dict[key])
        #
        # # to tuple
        # user_tuple = tuple(list_of_values)

        user_tuple = self.json_to_tuple_without_id(user)

        query = '''
        INSERT INTO Users(password, first_name, last_name, email, sex, weight,
        height, allergies, calories_intake_daily, weight_goal, user_name, birthDate)
        VALUES (?,?,?,?,?,?,?,?,?,?,?,?)
        '''

        self.cursor.execute(query, user_tuple)
        self.conn.commit()

        return self.cursor.lastrowid  # lastrowid returns generated id

    def update_user(self, user):
        # user_dict = json.loads(user)
        # list_of_values = []
        # for key in user_dict:
        #     if key != 'id':
        #         list_of_values.append(user_dict[key])
        #
        # list_of_values.append(user_dict['id'])
        #
        # # to tuple
        # user_tuple = tuple(list_of_values)

        user_tuple = self.json_to_tuple_with_id(user)

        query = '''
        UPDATE Users
        SET password = ?,
            first_name = ?,
            last_name = ?,
            email = ?, 
            sex = ?, 
            weight = ?,
            height = ?, 
            allergies = ?, 
            calories_intake_daily = ?, 
            weight_goal = ?, 
            user_name = ?, 
            birthDate = ?
        WHERE id = ?
        '''
        self.cursor.execute(query, user_tuple)
        self.conn.commit()

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
        return json.dumps(self.user_tuple_to_dict_with_id(data))

    def get_user_by_username(self, username):
        query = '''
        SELECT * FROM Users where user_name=?
        '''
        self.cursor.execute(query, (username,))
        data = self.cursor.fetchall()
        return json.dumps(self.user_tuple_to_dict_with_id(data))

    def get_user_by_email(self, email):
        query = '''
        SELECT * FROM Users where email=?
        '''
        self.cursor.execute(query, (email,))
        data = self.cursor.fetchall()
        return json.dumps(self.user_tuple_to_dict_with_id(data))


def test():
    conn = sqlite3.connect("skrypt.db")
    cursor = conn.cursor()
    users = Users(conn, cursor)

    u = {
            'password': 'maslo',
            'first_name': 'Jolanta',
            'last_name': 'Marcepanek',
            'email': 'jola245@wp.pl',
            'sex': 0,
            'weight': 60,
            'height': 165,
            'allergies': 'gluten',
            'calories_intake_daily': 2000,
            'weight_goal': 57,
            'user_name': 'twoja_jola',
            'birthDate': '1.02.2000',
        }

    user = users.get_user_by_id(5)
    #users.create_user(json.dumps(u))
    #print(user)
    jola = users.get_user_by_username('twoja_jola')
    #print(jola)
    #users.delete_user(8)

if __name__ == '__main__':
    test()
