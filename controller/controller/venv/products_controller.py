import sqlite3
import json


class Products:
    def __init__(self, conn, cursor):
        self.conn = conn
        self.cursor = cursor

    def product_tuple_to_dict_without_id(self, data):
        product_as_dict = {
            'name': [x[0] for x in data],
            'calories_in_100_grams': [x[1] for x in data],
            'protein': [x[2] for x in data],
            'carbs': [x[3] for x in data],
            'fats': [x[4] for x in data],
            'one_portion_in_grams': [x[5] for x in data],
            'user_id': [x[6] for x in data],
            'allergens': [x[7] for x in data],
        }
        return product_as_dict

    def user_tuple_to_dict_with_id(self, data):
        product_as_dict = {
            'id' : [x[0] for x in data],
            'name': [x[1] for x in data],
            'calories_in_100_grams': [x[2] for x in data],
            'protein': [x[3] for x in data],
            'carbs': [x[4] for x in data],
            'fats': [x[5] for x in data],
            'one_portion_in_grams': [x[6] for x in data],
            'user_id': [x[7] for x in data],
            'allergens': [x[8] for x in data],
        }
        return product_as_dict

    def json_to_tuple_without_id(self, json):
        dict = json.loads(json)
        list_of_values = []
        for key in dict:
            list_of_values.append(dict[key])
        tuple = tuple(list_of_values)
        return tuple

    def json_to_tuple_with_id(self, json):
        dict = json.loads(json)
        list_of_values = []
        for key in dict:
            if key != 'id':
                list_of_values.append(dict[key])

        list_of_values.append(dict['id'])
        tuple = tuple(list_of_values)
        return tuple

    def create_product(self, product):
        # product_dict = json.loads(product)
        # list_of_values = []
        # for key in product_dict:
        #     list_of_values.append(product_dict[key])
        # # to tuple
        # product_tuple = tuple(list_of_values)

        product_tuple = self.json_to_tuple_without_id(product)

        query = '''
        INSERT INTO Products(name, calories_in_100_grams, protein, carbs, fats,
        one_portion_in_grams, user_id, allergens)
        VALUES (?,?,?,?,?,?,?,?)
        '''

        self.cursor.execute(query, product_tuple)
        self.conn.commit()

        return self.cursor.lastrowid  # lastrowid returns generated id

    def update_product(self, product):
        # product_dict = json.loads(product)
        # list_of_values = []
        # for key in product_dict:
        #     list_of_values.append(product_dict[key])
        # list_of_values.append(product_dict['id'])
        # # to tuple
        # product_tuple = tuple(list_of_values)

        product_tuple = self.json_to_tuple_with_id(product)

        query = '''
        UPDATE Products
        SET name = ?,
            calories_in_100_grams = ?,
            protein = ?,
            carbs = ?, 
            fats = ?, 
            one_portion_in_grams = ?,
            user_id = ?, 
            allergens = ?
        WHERE id = ?
        '''

        self.cursor.execute(query, product_tuple)
        self.conn.commit()

    def delete_product(self, id):
        query = '''
        DELETE FROM Products where id=?
        '''
        self.cursor.execute(query, (id,))
        self.conn.commit()

    def delete_all_products(self):
        query = '''
        DELETE FROM Products
        '''
        self.cursor.execute(query)
        self.conn.commit()

    def query_products(self, query):
        self.cursor.execute(query)
        data = self.cursor.fetchall()
        return json.dumps(data)

    def get_product_by_id(self, id):
        query = '''
        SELECT * FROM Products where id=?
        '''
        self.cursor.execute(query, (id,))
        data = self.cursor.fetchall()
        return json.dumps(data)

    # check if it works
    def get_product_by_name(self, name):
        query = '''
        SELECT * FROM Products where name=?
        '''
        self.cursor.execute(query, (name,))
        data = self.cursor.fetchall()
        return json.dumps(data)

    # check if it works
    # wyszukiwanie produktów w danym posiłku po dacie i userze (id)
    def get_product_by_user_id_and_date(self, user_id, date):
        query = '''
        SELECT p.name, p.protein, p.carbs, p.fats, p.allergens,
         p.calories_in_100_grams, s.portionInGrams, m.meal_type
        FROM Products p join
        SpecificProductForMeal s on p.id = s.productId join
        Meal m on m.id = s.mealId where
        m.userId =? and m.date =? group by Products
        '''

        self.cursor.execute(query, (user_id, date,))
        data = self.cursor.fetchall()
        return json.dumps(data)

    # check if it works
    def get_product_by_name(self, name):
        pattern = str(name) + '%'
        query = '''
        SELECT * FROM Products where name like ?
        '''
        self.cursor.execute(query, (pattern,))
        data = self.cursor.fetchall()
        return json.dumps(data)

    # check if it works
    def get_product_with_no_given_allergens(self, allergens):
        query = '''
        SELECT * FROM Products WHERE allergens NOT MATCH ?;
        '''
        self.cursor.execute(query, (allergens,))
        data = self.cursor.fetchall()
        return json.dumps(data)


def test():
    conn = sqlite3.connect("skrypt.db")
    cursor = conn.cursor()

    products = Products(conn, cursor)

    # test methods when dump data in db


if __name__ == '__main__':
    test()
