import sqlite3
import json


class Products:
    def __init__(self, conn, cursor):
        self.conn = conn
        self.cursor = cursor

    def create_product(self, product):
        product_dict = json.loads(product)
        list_of_values = []
        for key in product_dict:
            list_of_values.append(product_dict[key])
        # to tuple
        product_tuple = tuple(list_of_values)

        query = '''
        INSERT INTO Products(name, calories_in_100_grams, protein, carbs, fats,
        one_portion_in_grams, user_id, allergens)
        VALUES (?,?,?,?,?,?,?,?)
        '''

        self.cursor.execute(query, product_tuple)
        return self.cursor.lastrowid  # lastrowid returns generated id

    def update_product(self, product):
        product_dict = json.loads(product)
        list_of_values = []
        for key in product_dict:
            list_of_values.append(product_dict[key])
        list_of_values.append(product_dict['id'])
        # to tuple
        product_tuple = tuple(list_of_values)

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

    def get_product_by_name(self, name):
        query = '''
        SELECT * FROM Products where name=?
        '''
        self.cursor.execute(query, (name,))
        data = self.cursor.fetchall()
        return json.dumps(data)

    # don't know if it works
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
