from flask import Flask, request
from controller import *
import sqlite3

app = Flask(__name__)


@app.route('/')
def home():
    return "Start point"


# params: id
@app.route('/user', methods=['GET', 'POST', 'UPDATE', 'DELETE'])
def user():
    global conn
    if request.method == 'GET':
        id = int(request.args.get('id'))
        user = get_user_by_id(conn, id)
        return user
    elif request.method == 'POST':
        print('post request')

    elif request.method == 'UPDATE':
        print('update request')
    else:  # delete
        print('delete request')

    return "user route"


if __name__ == '__main__':
    conn = connect_to_db("skrypt.db")
    app.run(debug=True)
