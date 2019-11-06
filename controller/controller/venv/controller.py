import sqlite3

def connect_to_db(sqlite_file):
    conn = None
    conn = sqlite3.connect(sqlite_file)
    print("conected")
    return conn

def create_to_db(query, conn):
    c = conn.cursor()
    c.execute(query)

def read_to_db(query, conn):
    c = conn.cursor()
    c.execute(query)

def update_to_db(query, conn):
    c = conn.cursor()
    c.execute(query)

def delete_to_db(query, conn):
    c = conn.cursor()
    c.execute(query)

if __name__ == '__main__':
    database = "skrypt-kopia.sqlite"
    conn = connect_to_db(database)


