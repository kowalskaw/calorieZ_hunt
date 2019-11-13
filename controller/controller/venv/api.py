from flask import Flask, request
from controller import *

app = Flask(__name__)
conn = None
cursor = None

@app.route('/')
def home():
    global conn, cursor
    conn = sqlite3.connect("skrypt.db")
    cursor = conn.cursor()
    return "Start point"


# params: id
@app.route('/user', methods=['GET', 'POST', 'UPDATE', 'DELETE'])
def user():
    global conn, cursor
    if request.method == 'GET':
        id = int(request.args.get('id'))
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
