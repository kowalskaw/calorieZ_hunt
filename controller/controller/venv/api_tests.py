import requests

main_url = "http://127.0.0.1:5000"

def run_db():
    endpoint = '/'
    response = requests.get(url=main_url + endpoint)
    print(response.content)


def get_user_by_id():
    params = {'id' : '2'}
    endpoint = '/user'
    response = requests.get(url=main_url + endpoint, params=params)
    print(response.content)

run_db()
get_user_by_id()