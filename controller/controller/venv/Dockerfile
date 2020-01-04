FROM python:3

WORKDIR /usr/src/app
COPY api.py .
COPY . .

RUN pip install -r requirements.txt

# Add scripts and db file
# ADD /venv/api.py
# ADD /venv/export.py
# ADD /venv/import.py
# ADD /venv/products_controller.py
# ADD /venv/users_controller.py
# ADD /venv/skrypt.db

EXPOSE  8000
CMD ["python", "./api.py", "-p 8000"]