import requests
import json

main_url = "http://192.168.99.100:5678"


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
    q2 = '''
    SELECT * FROM UsersFts WHERE allergies match 'gluten';
    '''
    q3 = '''
    SELECT * FROM Users
    '''
    # [w jakim miejscu występuje gluten, gluten]
    params = {'query': q3}
    endpoint = '/users'
    response = requests.get(url=main_url + endpoint, params=params)
    print_respone(response)


def get_user_by_id():
    params = {'id': '4'}
    endpoint = '/user'
    response = requests.get(url=main_url + endpoint, params=params)
    print_respone(response)

def get_user_by_username():
    params = {'user_name': 'MadziaWesołek85'}
    endpoint = '/user'
    response = requests.get(url=main_url + endpoint, params=params)
    print_respone(response)

def get_user_by_email():
    params = {'email': 'superjadzia@gmail.com'}
    endpoint = '/user'
    response = requests.get(url=main_url + endpoint, params=params)
    print_respone(response)

def create_user():
    user_dict = {'password': 'elo',
                 'first_name': 'Blabla',
                 'last_name': 'Marta',
                 'email': 'dupadupa@gmail.com',
                 'sex': 0,
                 'weight': 60,
                 'height': 165,
                 'allergies': 'gluten laktoza',
                 'calories_intake_daily': 1900,
                 'weight_goal': 55,
                 'user_name': 'MarmoladaCzekolada',
                 'birthDate': '10.11.1985',
                 }
    endpoint = '/user'
    response_d = requests.post(url=main_url + endpoint, json=json.dumps(user_dict))
    print_respone(response_d)


def update_user():
    user_dict = {'password': 'supertajnehaslo',
                 'first_name': 'Magdalena',
                 'last_name': 'Wesoła',
                 'email': 'superjadzia@gmail.com',
                 'sex': 0,
                 'weight': 60,
                 'height': 165,
                 'allergies': 'gluten laktoza',
                 'calories_intake_daily': 1900,
                 'weight_goal': 55,
                 'user_name': 'MadziaWesołek85',
                 'birthDate': '10.11.1985',
                 'id' : 4
                 }
    endpoint = '/user'
    response_d = requests.put(url=main_url + endpoint, json=json.dumps(user_dict))
    print_respone(response_d)

def delete_user():
    params = {'id': '1'}
    endpoint = '/user'
    response = requests.delete(url=main_url + endpoint, params=params)
    print_respone(response)


if __name__ == '__main__':
    run_db()

    #get_user_by_id()
    #get_user_by_email()
    get_user_by_username()
    # query_users()
    #create_user()
    # query_users()
    # update_user()
    # query_users()
    # delete_user()
    # query_users()

