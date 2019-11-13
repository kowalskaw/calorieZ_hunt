from flask import Flask, request
from controller import *

app = Flask(__name__)
conn = None
cursor = None

@app.route('/')
def home():
    global conn, cursor
    conn = sqlite3.connect("skrypt.db",  check_same_thread=False)
    cursor = conn.cursor()
    return "Connecting to database"

# pass a query later on
@app.route('/users', methods=['GET'])
def users():
    global conn, cursor
    if request.method == 'GET':
        query1 = '''
        SELECT * FROM Users where id=2 or id=3
        '''
        users = query_users(conn, cursor, query1)
        return users

# params: id
@app.route('/user', methods=['GET', 'POST', 'UPDATE', 'DELETE'])
def user():
    if request.method == 'GET':
        id = int(request.args.get('id'))
        print(id)
        print(type(id))
        user = get_user_by_id(conn, cursor, id)
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
