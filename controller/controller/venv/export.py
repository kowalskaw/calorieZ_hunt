import json
import sqlite3

from users_controller import Users
from products_controller import Products


class Export:
    def __init__(self, conn, cursor):
        self.conn = conn
        self.cursor = cursor

    def get_users(self):
        query = '''
        SELECT * FROM Users
        '''
        self.cursor.execute(query)
        data = self.cursor.fetchall()
        list = Users.user_tuple_to_dict_with_id(self, data)
        return list

    def get_products(self):
        query = '''
        SELECT * FROM Products
        '''
        self.cursor.execute(query)
        data = self.cursor.fetchall()
        list = Products.product_tuple_to_dict_with_id(self, data)
        return list

    def get_meals(self):
        query = '''
        SELECT * FROM Meal
        '''
        self.cursor.execute(query)
        data = self.cursor.fetchall()
        list = []
        for tuple in data:
            user_as_dict = {
                'id': tuple[0],
                'mealType': tuple[1],
                'date': tuple[2],
                'userId': tuple[3]
            }
            list.append(user_as_dict)
        return list

    def get_specific_products_for_meals(self):
        query = '''
        SELECT * FROM SpecificProductForMeal
        '''
        self.cursor.execute(query)
        data = self.cursor.fetchall()
        list = []
        for tuple in data:
            user_as_dict = {
                'productId': tuple[0],
                'mealId': tuple[1],
                'portionInGrams': tuple[2]
            }
            list.append(user_as_dict)
        return list


def export_data():
    conn = sqlite3.connect("skrypt.db")
    cursor = conn.cursor()
    toExport = Export(conn, cursor)
    list = []

    file = open("data.json", "w")
    list.append({"Users": toExport.get_users()})
    list.append({"Products": toExport.get_products()})
    list.append({"Meal": toExport.get_meals()})
    list.append({"SpecificProductForMeal": toExport.get_specific_products_for_meals()})
    file.write(json.dumps(list, sort_keys=False, indent=4))

    print("Data exported")


def get_generated_json():
    export_data()
    with open('data.json') as json_file:
        data = json.load(json_file)
    return data


if __name__ == '__main__':
    export_data()
