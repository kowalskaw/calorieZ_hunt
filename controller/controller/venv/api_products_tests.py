import requests
import json
from api_users_tests import print_respone, run_db, main_url


def get_product_by_id():
    params = {'id': '39'}
    endpoint = '/product'
    response = requests.get(url=main_url + endpoint, params=params)
    print_respone(response)


def get_product_by_name():
    params = {'name': 'chlepek'}
    endpoint = '/product'
    response = requests.get(url=main_url + endpoint, params=params)
    print_respone(response)


def get_product_by_partial_name():
    params = {'partial_name': 'pap'}
    endpoint = '/products'
    response = requests.get(url=main_url + endpoint, params=params)
    print_respone(response)


def create_product():
    p1 = {
        'name': 'maselko',
        'calories_in_100_grams': 746,
        'protein': 1,
        'carbs': 1,
        'fats': 82,
        'one_portion_in_grams': 5,
        'user_id': 4,
        'allergens': 'laktoza',
    }
    endpoint = '/product'
    response_d = requests.post(url=main_url + endpoint, json=json.dumps(p1))
    print_respone(response_d)


def update_product():
    p1 = {
        'id': 46,
        'name': 'maselko',
        'calories_in_100_grams': 746,
        'protein': 1,
        'carbs': 1,
        'fats': 82,
        'one_portion_in_grams': 10,
        'user_id': 4,
        'allergens': 'laktoza',
    }
    endpoint = '/product'
    response_d = requests.put(url=main_url + endpoint, json=json.dumps(p1))
    print_respone(response_d)


def delete_product_by_name():
    params = {'name': 'maselko'}
    endpoint = '/product'
    response = requests.delete(url=main_url + endpoint, params=params)
    print_respone(response)

def delete_product_by_id():
    params = {'id': '45'}
    endpoint = '/product'
    response = requests.delete(url=main_url + endpoint, params=params)
    print_respone(response)


if __name__ == '__main__':
    run_db()

    # create_product()
    # get_product_by_partial_name()
    # get_product_by_id()
    # get_product_by_name()
    # delete_product_by_name()

    # create_product()
    # delete_product_by_id()

    # create_product()
    # update_product()
