from flask import Flask, request
from controller import *

app = Flask(__name__)

# database management
conn = None
cursor = None
users = None

@app.route('/')
def home():
    global conn, cursor, users
    conn = sqlite3.connect("skrypt.db",  check_same_thread=False)
    cursor = conn.cursor()
    # managing users
    users = Users(conn, cursor)
    return "Connection to database established. Necessary objects created."

# pass a query later on
@app.route('/users', methods=['GET'])
def users():
    global conn, cursor
    if request.method == 'GET':
        query = request.args.get('query')
        queried_users = users.query_users(query)
        return queried_users

# params: id
@app.route('/user', methods=['GET', 'POST', 'UPDATE', 'DELETE'])
def user():
    if request.method == 'GET':
        id = int(request.args.get('id'))
        user = users.get_user_by_id(id)
        return user
    elif request.method == 'POST':
        print('post request')

    elif request.method == 'UPDATE':
        print('update request')
    else:  # delete
        print('delete request')

    return "user route"


if __name__ == '__main__':
    app.run(debug=True)
