from flask import Flask, request
from users_controller import *
from products_controller import *

app = Flask(__name__)

# database management
conn = None
cursor = None
users = None
products = None


@app.route('/')
def home():
    global conn, cursor, users, products
    conn = sqlite3.connect("skrypt.db", check_same_thread=False)
    cursor = conn.cursor()
    # managing users
    users = Users(conn, cursor)
    products = Products(conn, cursor)
    return "Connection to database established. Necessary objects created."


# multiple users -> GET with specified query, DELETE all users
@app.route('/users', methods=['GET', 'DELETE'])
def users():
    if request.method == 'GET':
        query = request.args.get('query')
        queried_users = users.query_users(query)
        return queried_users
    elif request.method == 'DELETE':
        users.delete_all_users()
        return 'All users deleted'
    else:
        return 'Invalid request'


# one user -> GET with id, POST with JSON, PUT with JSON, DELETE with id
@app.route('/user', methods=['GET', 'POST', 'PUT', 'DELETE'])
def user():
    if request.method == 'GET':
        if request.args.get('id'):
            id = int(request.args.get('id'))
            user = users.get_user_by_id(id)
        if request.args.get('user_name'):
            username = request.args.get('user_name')
            user = users.get_user_by_username(username)
        if request.args.get('email'):
            email = request.args.get('email')
            user = users.get_user_by_email(email)
        return user
    elif request.method == 'POST':
        user = request.get_json()
        new_user_id = users.create_user(user)
        return 'User created with id: ' + str(new_user_id)
    elif request.method == 'PUT':
        user = request.get_json()
        users.update_user(user)
        return 'User updated'
    elif request.method == 'DELETE':
        id = int(request.args.get('id'))
        user = users.delete_user(id)
        return 'User with id ' + str(id) + ' deleted from db'
    else:
        return 'Invalid request'


# multiple products -> GET with specified query, DELETE all products
@app.route('/products', methods=['GET', 'POST', 'PUT', 'DELETE'])
def products():
    if request.method == 'GET':
        if request.args.get('query'):
            query = request.args.get('query')
            queried_products = products.query_products(query)
            return queried_products
        if request.args.get('partial_name'):
            partial_name = request.args.get('partial_name')
            queried_products = products.get_product_by_partial_name(partial_name)
            return queried_products
        if request.args.get('user_id') and request.args.get('date'):
            user_id = request.args.get('user_id')
            date = request.args.get('date')
            product = products.get_product_by_user_id_and_date(user_id, date)
            return product
        if request.args.get('allergens'):
            allergens = request.args.get('allergens')
            product = products.get_product_with_no_given_allergens(allergens)
            return product
    elif request.method == 'DELETE':
        products.delete_all_products()
        return 'All products deleted'
    else:
        return 'Invalid request'


# one product -> GET with id, POST with JSON, PUT with JSON, DELETE with id
@app.route('/product', methods=['GET', 'POST', 'PUT', 'DELETE'])
def product():
    if request.method == 'GET':
        if request.args.get('id'):
            id = int(request.args.get('id'))
            product = products.get_product_by_id(id)
            return product
        if request.args.get('name'):
            name = request.args.get('name')
            product = products.get_product_by_name(name)
            return product
    elif request.method == 'POST':
        product = request.get_json()
        new_product_id = products.create_product(product)
        return 'New product created with id: ' + str(new_product_id)
    elif request.method == 'PUT':
        product = request.get_json()
        products.update_product(product)
        return 'Product updated'
    elif request.method == 'DELETE':
        if request.args.get('name'):
            name = request.args.get('name')
            product = products.delete_product_by_name(name)
            return 'Product with name ' + name + ' deleted from db'
        if request.args.get('id'):
            id = request.args.get('id')
            product = products.delete_product(id)
            return 'Product with id ' + str(id) + ' deleted from db'
    else:
        return 'Invalid request'


if __name__ == '__main__':
    app.run(debug=True)
