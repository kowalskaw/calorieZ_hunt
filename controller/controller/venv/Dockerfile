FROM python:3

WORKDIR /usr/src/app
COPY api.py .
COPY . .

RUN pip install -r requirements.txt

CMD ["python", "./api.py"]