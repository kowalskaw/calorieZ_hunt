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
            'id': [x[0] for x in data],
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

    def create_product(self, product):
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

    def delete_product_by_name(self, name):
        query = '''
        DELETE FROM Products where name=?
        '''
        self.cursor.execute(query, (name,))
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
        return json.dumps(self.user_tuple_to_dict_with_id(tuple))

    def get_product_by_id(self, id):
        query = '''
        SELECT * FROM Products where id=?
        '''
        self.cursor.execute(query, (id,))
        data = self.cursor.fetchall()
        return json.dumps(self.user_tuple_to_dict_with_id(data))

    def get_product_by_name(self, name):
        query = '''
        SELECT * FROM Products where name=?
        '''
        self.cursor.execute(query, (name,))
        data = self.cursor.fetchall()
        return json.dumps(self.user_tuple_to_dict_with_id(data))

    # not works
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
        return json.dumps(self.user_tuple_to_dict_with_id(data))

    def get_product_by_partial_name(self, name):
        pattern = str(name) + '%'
        query = '''
        SELECT * FROM Products where name like ?
        '''
        self.cursor.execute(query, (pattern,))
        data = self.cursor.fetchall()
        return json.dumps(self.user_tuple_to_dict_with_id(data))

    # not works
    def get_product_with_no_given_allergens(self, allergens):
        query = '''
        SELECT * FROM Products WHERE allergens NOT MATCH ?;
        '''
        self.cursor.execute(query, (allergens,))
        data = self.cursor.fetchall()

        return json.dumps(self.user_tuple_to_dict_with_id(data))


def test():
    conn = sqlite3.connect("skrypt.db")
    cursor = conn.cursor()

    products = Products(conn, cursor)
    p1 = {
        'name': 'paprika',
        'calories_in_100_grams': 50,
        'protein': 1,
        'carbs': 3,
        'fats': 1,
        'one_portion_in_grams': 25,
        'user_id': 4,
        'allergens': '',
    }

    p2 = {
        'name': 'chlepek',
        'calories_in_100_grams': 238,
        'protein': 6.15,
        'carbs': 50,
        'fats': 1.20,
        'one_portion_in_grams': 35,
        'user_id': 4,
        'allergens': 'gluten',
    }

    # ADDING PRODUCT
    # products.create_product(json.dumps(p1))
    # products.create_product(json.dumps(p2))

    # DELETING PRODUCT
    # products.delete_product_by_name('chlepek')
    # products.delete_product_by_name('paprika')

    # GETTING PRODUCT BY NAME
    print(products.get_product_by_name('chlepek'))
    print(products.get_product_by_name('paprika'))
    # GETTING PRODUCT BY ID
    print(products.get_product_by_id(39))
    # GETTING PRODUCT BY PARTIAL NAME
    print(products.get_product_by_partial_name('pa'))
    # GETTING PRODUCT BY USER ID AND DATE - nie działa!
    print(products.get_product_by_user_id_and_date(4, '01.12.2019'))
    # GETTING PRODUTS WITH NO GIVEN ALLERGENS - nie działa!
    print(products.get_product_with_no_given_allergens('gluten'))


if __name__ == '__main__':
    test()
