from flask import Flask, request
from users_controller import *

app = Flask(__name__)

# database management
conn = None
cursor = None
users = None


@app.route('/')
def home():
    global conn, cursor, users
    conn = sqlite3.connect("skrypt.db", check_same_thread=False)
    cursor = conn.cursor()
    # managing users
    users = Users(conn, cursor)
    return "Connection to database established. Necessary objects created."


# multiple users -> GET with specified query, DELETE all users
@app.route('/users', methods=['GET', 'DELETE'])
def users():
    global conn, cursor
    if request.method == 'GET':
        query = request.args.get('query')
        queried_users = users.query_users(query)
        return queried_users
    if request.method == 'DELETE':
        users.delete_all_users()
        return 'All users deleted'


# one user
@app.route('/user', methods=['GET', 'POST', 'PUT', 'DELETE'])
def user():
    if request.method == 'GET':
        id = int(request.args.get('id'))
        user = users.get_user_by_id(id)
        return user
    elif request.method == 'POST':
        user = request.get_json()
        new_user_id = users.create_user(user)
        return new_user_id
    elif request.method == 'PUT':
        user = request.get_json()
        users.update_user(user)
        return 'User updated'
    else:  # delete
        id = int(request.args.get('id'))
        user = users.delete_user(id)
        return 'User with id ' + str(id) + ' deleted from db'
    return 'Invalid request'


if __name__ == '__main__':
    app.run(debug=True)
