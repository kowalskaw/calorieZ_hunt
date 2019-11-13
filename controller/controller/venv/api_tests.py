import requests
import json

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
    q2 = '''
    SELECT * FROM UsersFts WHERE allergies match 'gluten';
    '''
    q3 =     q1 = '''
    SELECT * FROM Users
    '''
    # [w jakim miejscu występuje gluten, gluten]
    params = {'query': q3}
    endpoint = '/users'
    response = requests.get(url=main_url + endpoint, params=params)
    print_respone(response)


def get_user_by_id():
    params = {'id': '2'}
    endpoint = '/user'
    response = requests.get(url=main_url + endpoint, params=params)
    print_respone(response)


def create_user():
    user_dict = {'password': 'supertajnehaslo',
            'first_name': 'Jadzia',
            'last_name': 'Marudka',
            'email': 'superjadzia@gmail.com',
            'sex': 0,
            'weight': 60,
            'height': 165,
            'allergies': 'gluten laktoza',
            'calories_intake_daily': 1900,
            'weight_goal': 55,
            'user_name': 'JadziaMarudka85',
            'birthDate': '10.11.1985',
            }
    user_tuple = ('supertajnehaslo', 'Jadzia', 'Marudka', 'superjadzia@gmail.com',
                  0, 60, 165, 'gluten laktoza', 1900, 55, 'JadziaMarudka85', '10.11.1985')
    endpoint = '/user'

    # sending tuple
    # response_t = requests.post(url=main_url + endpoint, data=user_tuple, )
    # print_respone(response_t)
    # sending dict=json
    response_d = requests.post(url=main_url + endpoint, json=json.dumps(user_dict))
    print_respone(response_d)


if __name__ == '__main__':
    run_db()
    get_user_by_id()
    query_users()
    create_user()
    query_users()
