import sqlite3
import json


class Products:
    def __init__(self, conn, cursor):
        self.conn = conn
        self.cursor = cursor

    def product_tuple_to_dict_without_id(self, data):
        list_of_jsons = []
        for one_tuple in data:
            product_as_dict = {
                'name': one_tuple[0],
                'calories_in_100_grams': one_tuple[1],
                'protein': one_tuple[2],
                'carbs': one_tuple[3],
                'fats': one_tuple[4],
                'one_portion_in_grams': one_tuple[5],
                'user_id': one_tuple[6],
                'allergens': one_tuple[7],
            }
            list_of_jsons.append(product_as_dict)
        return list_of_jsons

    def product_tuple_to_dict_with_id(self, data):
        list_of_jsons = []
        for one_tuple in data:
            product_as_dict = {
                'id': one_tuple[0],
                'name': one_tuple[1],
                'calories_in_100_grams': one_tuple[2],
                'protein': one_tuple[3],
                'carbs': one_tuple[4],
                'fats': one_tuple[5],
                'one_portion_in_grams': one_tuple[6],
                'user_id': one_tuple[7],
                'allergens': one_tuple[8],
            }
            list_of_jsons.append(product_as_dict)
        return list_of_jsons

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

        list_of_values.append(dict_obj['id'])
        return tuple(list_of_values)

    def create_product(self, product):
        product_tuple = self.json_to_tuple_without_id(product)

        check_if_exists_query ='''
        SELECT * FROM Products where name = ?
        '''

        self.cursor.execute(check_if_exists_query, (product_tuple[0],))
        data = self.cursor.fetchall()

        if data == []:
            query = '''
            INSERT INTO Products(name, calories_in_100_grams, protein, carbs, fats,
            one_portion_in_grams, user_id, allergens)
            VALUES (?,?,?,?,?,?,?,?)
            '''

            self.cursor.execute(query, product_tuple)
            self.conn.commit()

            return self.cursor.lastrowid
        else:
            return 'Product already exists in db'

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

    def get_product_by_partial_name(self, name):
        pattern = str(name) + '%'
        query = '''
        SELECT * FROM Products where name like ?
        '''
        self.cursor.execute(query, (pattern,))
        data = self.cursor.fetchall()
        return json.dumps(self.user_tuple_to_dict_with_id(data))

    # not works
    def get_product_by_user_id_and_date(self, user_id, date):
        query = '''
        Select p.name, p.protein, p.carbs, p.fats,
        p.allergens, p.calories_in_100_grams, s.portionInGrams, m.meal_type
        from Products p join SpecificProductForMeal s
        on p.id = s.productId join
        Meal m on m.id = s.mealId 
        where m.user_id=? and 
        m.date = ? group by p.id;
        '''

        self.cursor.execute(query, (user_id, date,))
        data = self.cursor.fetchall()
        return json.dumps(self.user_tuple_to_dict_with_id(data))

    # not works
    def get_product_with_no_given_allergens(self, allergens):
        query = '''
        Select * from Products where id != 
        (SELECT id FROM ProductsFts WHERE allergens match ?);
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
    # print(products.get_product_by_name('chlepek'))
    #print(products.get_product_by_name('paprika'))
    # GETTING PRODUCT BY ID
    # print(products.get_product_by_id(39))
    # GETTING PRODUCT BY PARTIAL NAME
    # print(products.get_product_by_partial_name('pa'))
    # GETTING PRODUCT BY USER ID AND DATE - nie działa!
    # print(products.get_product_by_user_id_and_date(4, '01.12.2019'))
    # GETTING PRODUTS WITH NO GIVEN ALLERGENS - nie działa!
    # print(products.get_product_with_no_given_allergens('gluten'))

# if __name__ == '__main__':
#     test()
