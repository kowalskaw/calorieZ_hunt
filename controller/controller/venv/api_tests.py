import requests

main_url = "http://127.0.0.1:5000"

def print_respone(response):
    print('Url:')
    print(response.url)
    print('Content:')
    print(response.content)

def run_db():
    endpoint = '/'
    response = requests.get(url=main_url + endpoint)
    print_respone(response)


def query_users():
    q1 = '''
    SELECT * FROM Users where id=2 or id=3
    '''
    params = {'query' : q1}
    endpoint = '/users'
    response = requests.get(url=main_url + endpoint, params=params)
    print_respone(response)

def get_user_by_id():
    params = {'id' : '2'}
    endpoint = '/user'
    response = requests.get(url=main_url + endpoint, params=params)
    print_respone(response)



run_db()
get_user_by_id()
query_users()