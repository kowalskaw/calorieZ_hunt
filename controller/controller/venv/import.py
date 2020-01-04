import json
import sqlite3

from users_controller import Users
from products_controller import Products


class Import:
    def __init__(self, conn, cursor):
        self.conn = conn
        self.cursor = cursor

    def load_json(self, path):
        with open(path, 'r') as file:
            datastore = file.read()
        return json.loads(datastore)

    def get_users(self, path):
        x = self.load_json(path)
        users = x[0]['Users']
        return users

    def get_products(self, path):
        x = self.load_json(path)
        products = x[1]['Products']
        return products

    def add_users(self, path):
        users = self.get_users(path)

        for i in users:
            user_tuple = Users.json_to_tuple_without_id(self, json.dumps(i))

            check = check = '''
                SELECT * FROM Users where email = ?
            '''
            self.cursor.execute(check, (user_tuple[4],))
            data = self.cursor.fetchall()

            if data == []:
                user_tuple = user_tuple[1:]
                query = '''
                       INSERT INTO Users(password, first_name, last_name, email, sex, weight,
                       height, allergies, calories_intake_daily, weight_goal, user_name, birthDate)
                       VALUES (?,?,?,?,?,?,?,?,?,?,?,?)
                       '''
                self.cursor.execute(query, user_tuple)
                self.conn.commit()
                print("User added")
            else:
                print("User already exists")

    def add_products(self, path):
        products = self.get_products(path)
        for i in products:
            product_tuple = Products.json_to_tuple_without_id(self, json.dumps(i))

            check = '''
                SELECT * FROM Products where name = ?
            '''
            self.cursor.execute(check, (product_tuple[1],))
            data = self.cursor.fetchall()

            if data == []:
                query = '''
                                INSERT INTO Products(name, calories_in_100_grams, protein, carbs, fats,
                                one_portion_in_grams, user_id, allergens)
                                VALUES (?,?,?,?,?,?,null,?)
                                '''
                product_tuple = product_tuple[1:]
                self.cursor.execute(query, product_tuple)
                self.conn.commit()
                print("Product added")
            else:
                print("Product already exists")


def test():
    conn = sqlite3.connect("skrypt.db")
    cursor = conn.cursor()
    toImport = Import(conn, cursor)

    toImport.add_users("insert.json")
    toImport.add_products("insert.json")


# dodawanie wszystkiego z jednego pliku a nie z osobnych

if __name__ == '__main__':
    test()
