const express = require('express');
const app = express();
const port = 3000;

app.get('/', (req, res) => res.send('Hello World!'));
app.post('/user', (req, res) => res.send(res));

app.listen(port, () => console.log(`Example app listening on port ${port}!`));
